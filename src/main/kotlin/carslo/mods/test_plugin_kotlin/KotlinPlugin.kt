package carslo.mods.test_plugin_kotlin

import carslo.mods.test_plugin_kotlin.Commands.TestCommand
import carslo.mods.test_plugin_kotlin.ItemSystem.ItemList.TestItem
import carslo.mods.test_plugin_kotlin.ItemSystem.CustomItem
import carslo.mods.test_plugin_kotlin.ItemSystem.ActionItem
import java.util.logging.Logger
import org.bukkit.plugin.PluginManager
import org.bukkit.plugin.java.JavaPlugin

public class KotlinPlugin : JavaPlugin() {
    companion object {
        public lateinit var instance: KotlinPlugin
            private set
        public lateinit var log: Logger
            private set
        public lateinit var pluginManager: PluginManager
            private set

        public lateinit var itemList: List<ActionItem>
    }

    override fun onEnable() {
        instance = this
        log = this.logger
        pluginManager = this.server.pluginManager

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
