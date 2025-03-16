package carslo.mods.custom_content_api.ItemSystem

import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.block.Action
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.PlayerInventory
import org.bukkit.inventory.EquipmentSlot
import org.bukkit.Material
import carslo.mods.custom_content_api.ItemSystem.CustomItem
import carslo.mods.custom_content_api.CustomContentAPI

public abstract class ActionItem : Listener {
    public lateinit var item: CustomItem

    public constructor() {
        CustomContentAPI.itemList += this
    }

    public abstract fun CanLeftClick(e: PlayerInteractEvent): Boolean
    public abstract fun OnLeftClick(e: PlayerInteractEvent)

    public abstract fun CanRightClick(e: PlayerInteractEvent): Boolean
    public abstract fun OnRightClick(e: PlayerInteractEvent)

    public fun giveItem(player: Player) {
        player.inventory.addItem(item.itemStack)
    }

    public fun UpdateItem(player: Player) {
        val inv: PlayerInventory = player.inventory
        for (i: Int in 0..inv.size - 1) {
            var invItem: ItemStack = inv.getItem(i) ?: continue
            if (invItem.type.isAir) continue
            var cItem: CustomItem = CustomItem(invItem)
            if (cItem.customID == item.customID && cItem.itemVersion != item.itemVersion) {
                inv.setItem(i, item.itemStack)
            }
        }
    }

    @EventHandler
    public fun OnPlayerInteract(e: PlayerInteractEvent) {
        if (e.item == null) return

        var interactItem: CustomItem = CustomItem(e.item!!)

        // Left/Right Click Detection
        if (interactItem.customID == item.customID) {
            when (e.action) {
                Action.LEFT_CLICK_BLOCK, Action.LEFT_CLICK_AIR -> {
                    if (CanLeftClick(e)) OnLeftClick(e)
                }
                Action.RIGHT_CLICK_BLOCK, Action.RIGHT_CLICK_AIR -> {
                    if (CanRightClick(e)) OnRightClick(e)
                }
                else -> {
                    return
                }
            }
        }
    }
    @EventHandler
    public fun OnPlayerJoin(e: PlayerJoinEvent) {
        // Update old Items.
		UpdateItem(e.player)
    }
}
