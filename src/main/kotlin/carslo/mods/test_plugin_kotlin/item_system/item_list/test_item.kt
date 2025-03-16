package carslo.mods.test_plugin_kotlin.ItemSystem.ItemList

import carslo.mods.test_plugin_kotlin.ItemSystem.ActionItem
import carslo.mods.test_plugin_kotlin.ItemSystem.CustomItem

import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.Material
import org.bukkit.inventory.EquipmentSlot

public class TestItem : ActionItem {

    public constructor() : super() {
        item = CustomItem(Material.WOODEN_SWORD)
        item.customID = "dev_sword"
        item.itemVersion = 1
        item.displayName = "§4DEV Sword"
        item.lore = listOf("Wields immense power.")
    }

    override fun CanLeftClick(e: PlayerInteractEvent): Boolean = false
    override fun OnLeftClick(e: PlayerInteractEvent) {
        return
    }

    override fun CanRightClick(e: PlayerInteractEvent): Boolean = true
    override fun OnRightClick(e: PlayerInteractEvent) {
        if (e.hand != EquipmentSlot.HAND) return
        e.setCancelled(true)
        var cItem = CustomItem(e.item!!)
        when (cItem.type) {
            Material.WOODEN_SWORD -> {
                cItem.type = Material.STONE_SWORD
                e.player.inventory.setItem(EquipmentSlot.HAND, cItem.itemStack)
            }
            Material.STONE_SWORD -> {
                cItem.type = Material.WOODEN_SWORD
                e.player.inventory.setItem(EquipmentSlot.HAND, cItem.itemStack)
            }
            else -> return
        }
    }
}
