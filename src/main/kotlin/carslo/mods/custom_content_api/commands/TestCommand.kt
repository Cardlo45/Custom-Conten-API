package carslo.mods.custom_content_api.Commands

import carslo.mods.custom_content_api.ItemSystem.ActionItem
import carslo.mods.custom_content_api.ItemSystem.CustomItem
import carslo.mods.custom_content_api.CustomContentAPI
import carslo.mods.custom_content_api.UISystem.*
import java.util.UUID
import org.bukkit.Material
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.command.ConsoleCommandSender
import org.bukkit.command.TabExecutor
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryType
import org.bukkit.inventory.ItemStack

public class TestCommand : TabExecutor {
    var emptyUI: HashMap<UUID, UI> = hashMapOf()
    public override fun onCommand(
            sender: CommandSender,
            command: Command,
            label: String,
            args: Array<out String>
    ): Boolean {
        if (sender is ConsoleCommandSender) {
            CustomContentAPI.log.info("The Test Command can only be used as a Player.")
            return true
        }
        if (!(sender is Player)) {
            return false
        }
        if (args.size == 0) {
            SendHelp(sender, args)
            return true
        }

        when (args[0]) {
            "help" -> return SendHelp(sender, args)
            "give" -> TestGive(sender, args)
            "ui" -> ShowUI(sender, args)
            else -> return false
        }

        return false
    }

    private fun SendHelp(sender: CommandSender, args: Array<out String>): Boolean {
        var message: String
        when (args.size) {
            1 -> message = "Usage: /test [ help | give | ui ] <args>"
            2 ->
                    when (args[1].lowercase()) {
                        "give" -> message = "Give a test item\nUsage: /test give <custom_id>"
                        "ui" -> message = "Open a test UI\nUsage: /test ui <ui_name>"
                        else -> message = "Usage: /test help"
                    }
            else -> message = "Usage: /test help"
        }

        sender.sendMessage(message)
        return true
    }
    // TODO
    private fun TestGive(sender: CommandSender, args: Array<out String>): Boolean {
        if (args.size < 2) {
            return SendHelp(sender, arrayOf("help", "give"))
        }
        when (args[1]) {
            "diamond" -> {
                var item: ItemStack =
                        CreateGUIItem(
                                Material.DIAMOND,
                                "§eShiny",
                                arrayOf("This could provide usefull.")
                        )
                (sender as Player).inventory.addItem(item)
                return true
            }
            else -> {
                var cItem: CustomItem? =
                        CustomContentAPI.itemList.find { it.item.customID == args[1].lowercase() }?.item
                if (cItem == null) {
                    return SendHelp(sender, arrayOf("help", "give"))
                }
                (sender as Player).inventory.addItem(cItem.itemStack)
                return true
            }
        }
    }
    // TODO
    private fun ShowUI(sender: CommandSender, args: Array<out String>): Boolean {
        if (args.size < 2) {
            return SendHelp(sender, arrayOf("help", "ui"))
        }
        when (args[1]) {
            "single_row" -> {
                var ui: UI = UI("Single Row", 9)
                ui.Fill(CreateGUIItemEmpty(Material.BLACK_STAINED_GLASS_PANE))
                ui.Set(
                        CreateGUIItem(Material.BARRIER, "Close", null),
                        8,
                        { player: Player -> player.closeInventory() }
                )
                (sender as Player).openInventory(ui.container)
                return true
            }
            "workbench" -> {
                var ui: UI = UI("Workbench", InventoryType.WORKBENCH)
                ui.Fill(CreateGUIItemEmpty(Material.BLACK_STAINED_GLASS_PANE))
                ui.Set(
                        CreateGUIItem(
                                Material.DIAMOND,
                                "Shiny Diamond.",
                                arrayOf("50 / 50 chance.")
                        ),
                        5,
                        { player: Player -> player.health = 0.0 }
                )
                ui.Set(
                        CreateGUIItem(Material.BARRIER, "Close", null),
                        0,
                        { player: Player -> player.closeInventory() }
                )
                (sender as Player).openInventory(ui.container)
                return true
            }
            "empty_slots" -> {
                val uuid: UUID = (sender as Player).uniqueId
                if (emptyUI.get(uuid) == null) {
                    var newUi: UI = UI("Empty Slots", 54)
                    newUi.Set(
                            CreateGUIItem(Material.BARRIER, "Close", null),
                            0,
                            { player: Player -> player.closeInventory() }
                    )
                    emptyUI.put(uuid, newUi)
                }
                var ui: UI = emptyUI.get(uuid)!!
                sender.openInventory(ui.container)
                return true
            }
            else -> return SendHelp(sender, arrayOf("help", "ui"))
        }
    }

    public override fun onTabComplete(
            sender: CommandSender,
            command: Command,
            label: String,
            args: Array<out String>
    ): List<String> {
        var completion: List<String> = listOf()
        when (args.size) {
            1 -> {
                completion += "help"
                completion += "give"
                completion += "ui"
            }
            2 ->
                    when (args[0].lowercase()) {
                        "help" -> {
                            completion += "give"
                            completion += "ui"
                        }
                        "give" -> {
                            completion += "diamond"
                            for (i: ActionItem in CustomContentAPI.itemList) {
                                completion += i.item.customID
                            }
                        }
                        "ui" -> {
                            completion += "single_row"
                            completion += "workbench"
                            completion += "empty_slots"
                        }
                    }
        }

        return completion
    }
}
