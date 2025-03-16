package carslo.mods.custom_content_api

import carslo.mods.custom_content_api.Commands.TestCommand
import carslo.mods.custom_content_api.ItemSystem.ActionItem
import carslo.mods.custom_content_api.ItemSystem.ItemList.TestItem
import java.util.logging.Logger
import org.bukkit.plugin.PluginManager
import org.bukkit.plugin.java.JavaPlugin

public class CustomContentAPI : JavaPlugin() {
    companion object {
        public lateinit var instance: CustomContentAPI
            private set
        public lateinit var log: Logger
            private set
        public lateinit var pluginManager: PluginManager
            private set

        public lateinit var itemList: List<ActionItem>
    }

    override fun onEnable() {
        instance = this
        log = logger
        pluginManager = server.pluginManager

        itemList = listOf()

        log.info("Loading plugin...")
        getCommand("test")?.setExecutor(TestCommand())
        InitItems()
        log.info("Plugin loaded.")
    }

    override fun onDisable() {
        log.info("Shutting down...")
    }

    private fun InitItems() {
        pluginManager.registerEvents(TestItem(), this)
    }
}
