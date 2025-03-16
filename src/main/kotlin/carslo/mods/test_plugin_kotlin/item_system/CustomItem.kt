package carslo.mods.test_plugin_kotlin.ItemSystem

import carslo.mods.test_plugin_kotlin.KotlinPlugin
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta
import org.bukkit.persistence.PersistentDataType

public class CustomItem : Item {

    public var customID: String
        get() =
                persistentDataContainer.get(
                        NamespacedKey(KotlinPlugin.instance, "custom_id"),
                        PersistentDataType.STRING
                )
                        ?: ""
        set(value: String) {
            var meta: ItemMeta = itemMeta
            meta.persistentDataContainer.set(
                    NamespacedKey(KotlinPlugin.instance, "custom_id"),
                    PersistentDataType.STRING,
                    value
            )
            itemMeta = meta
        }

    public var itemVersion: Int
        get() =
                persistentDataContainer.get(
                        NamespacedKey(KotlinPlugin.instance, "item_version"),
                        PersistentDataType.INTEGER
                )
                        ?: -1
        set(value: Int) {
            var meta: ItemMeta = itemMeta
            meta.persistentDataContainer.set(
                    NamespacedKey(KotlinPlugin.instance, "item_version"),
                    PersistentDataType.INTEGER,
                    value
            )
			itemMeta = meta
        }

    public constructor(item: Material) : super(item)
    public constructor(item: ItemStack) : super(item)
}
