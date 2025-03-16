package carslo.mods.custom_content_api.UISystem

import carslo.mods.custom_content_api.CustomContentAPI
import carslo.mods.custom_content_api.ItemSystem.CustomItem
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.HandlerList
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.event.inventory.InventoryDragEvent
import org.bukkit.event.inventory.InventoryType
import org.bukkit.event.player.PlayerSwapHandItemsEvent
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack

public class UI : Listener {
    public var preventUnregister = false
    public var container: Inventory
        private set
    private var actions: HashMap<Int, (Player) -> Unit> = hashMapOf()

    public constructor(name: String, uiType: InventoryType) {
        CustomContentAPI.pluginManager.registerEvents(this, CustomContentAPI.instance)
        container = Bukkit.createInventory(null, uiType, name)
    }

    public constructor(name: String, uiSize: Int) {
        if (uiSize % 9 != 0) throw IllegalArgumentException("uiSize must be a multiple of 9.")
        if (uiSize > 54) throw IllegalArgumentException("uiSize must be 54 or smaller.")
        CustomContentAPI.pluginManager.registerEvents(this, CustomContentAPI.instance)
        container = Bukkit.createInventory(null, uiSize, name)
    }

    public fun Fill(item: ItemStack) {
        for (i in 0..container.size - 1) {
            container.setItem(i, item)
        }
    }
    public fun Set(item: ItemStack, slot: Int, action: (Player) -> Unit) {
        container.setItem(slot, item)
        actions.put(slot, action)
    }

    @EventHandler
    public fun OnInventoryClick(e: InventoryClickEvent) {
        if (e.inventory != container) return
        if (e.currentItem == null) return

        val click: CustomItem = CustomItem(e.currentItem!!)
        val slot = e.rawSlot

        if (click.customID == "ui_item") {
            e.setCancelled(true)
            actions.get(slot)?.invoke(e.whoClicked as Player)
        }
    }

    @EventHandler
    public fun OnInventoryDrag(e: InventoryDragEvent) {
        if (e.inventory != container) {
            e.setCancelled(true)
            return
        }
    }

    @EventHandler
    public fun OnInventorySwapHands(e: PlayerSwapHandItemsEvent) {
        if (e.player.getOpenInventory().topInventory != container) return
        if (e.mainHandItem == null || e.offHandItem == null) return

        val mainHandItem: CustomItem = CustomItem(e.mainHandItem!!)
        val offHandItem: CustomItem = CustomItem(e.offHandItem!!)
        if (mainHandItem.customID == "ui_item" || offHandItem.customID == "ui_item") {
            e.setCancelled(true)
        }
    }

    @EventHandler
    public fun OnInventoryClose(e: InventoryCloseEvent) {
        if (e.inventory != container) return
        if (!preventUnregister) HandlerList.unregisterAll(this)
    }
}

public fun CreateGUIItem(item: Material, name: String, lore: Array<String>?): ItemStack {
    var cItem: CustomItem = CustomItem(item)
    cItem.displayName = name
    cItem.lore = lore?.toList()
    cItem.customID = "ui_item"
    return cItem.itemStack
}

public fun CreateGUIItemEmpty(item: Material): ItemStack {
    return CreateGUIItem(item, " ", null)
}
