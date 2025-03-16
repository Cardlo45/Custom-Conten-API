package carslo.mods.custom_content_api.ItemSystem

import org.bukkit.Material
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta
import org.bukkit.persistence.PersistentDataContainer

public open class Item {
    public var itemStack: ItemStack
        get
        private set

    public var itemMeta: ItemMeta
        get() = itemStack.itemMeta!!
        protected set(value: ItemMeta) {
            itemStack.itemMeta = value
        }

    public val persistentDataContainer: PersistentDataContainer
        get() = itemMeta.persistentDataContainer

    public var displayName: String
        get() = itemMeta.displayName
        set(value: String) {
            var meta: ItemMeta = itemMeta
            // meta.displayName = value
            meta.setDisplayName(value) // HACK LSP Fix
            itemMeta = meta
        }

    public var lore: List<String>?
        get() = itemMeta.lore
        set(value: List<String>?) {
            var meta: ItemMeta = itemMeta
            meta.lore = value
            itemMeta = meta
        }

    public var type: Material
        get() = itemStack.type
        set(value: Material) {
            itemStack.type = value
        }

    public constructor(item: Material) {
        itemStack = ItemStack(item)
    }
    public constructor(item: ItemStack) {
        itemStack = item
    }
}
