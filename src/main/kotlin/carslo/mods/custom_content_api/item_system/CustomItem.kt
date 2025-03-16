package carslo.mods.custom_content_api.ItemSystem

import carslo.mods.custom_content_api.CustomContentAPI
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta
import org.bukkit.persistence.PersistentDataType

public class CustomItem : Item {

    public var customID: String
        get() =
                persistentDataContainer.get(
                        NamespacedKey(CustomContentAPI.instance, "custom_id"),
                        PersistentDataType.STRING
                )
                        ?: ""
        set(value: String) {
            var meta: ItemMeta = itemMeta
            meta.persistentDataContainer.set(
                    NamespacedKey(CustomContentAPI.instance, "custom_id"),
                    PersistentDataType.STRING,
                    value
            )
            itemMeta = meta
        }

    public var itemVersion: Int
        get() =
                persistentDataContainer.get(
                        NamespacedKey(CustomContentAPI.instance, "item_version"),
                        PersistentDataType.INTEGER
                )
                        ?: -1
        set(value: Int) {
            var meta: ItemMeta = itemMeta
            meta.persistentDataContainer.set(
                    NamespacedKey(CustomContentAPI.instance, "item_version"),
                    PersistentDataType.INTEGER,
                    value
            )
			itemMeta = meta
        }

    public constructor(item: Material) : super(item)
    public constructor(item: ItemStack) : super(item)
}
