package me.ethanbrews.bossmod

import me.ethanbrews.bossmod.init.CommandInit
import me.ethanbrews.bossmod.init.EntityInit
import me.ethanbrews.bossmod.init.RenderInit
import me.ethanbrews.bossmod.init.SoundInit
import net.fabricmc.api.ModInitializer
import org.apache.logging.log4j.LogManager

var modid = "bossmod"
var modname = "Boss Mod"
var logger = LogManager.getLogger(modid)

class Main : ModInitializer {
    override fun onInitialize() {
        EntityInit.init()
        SoundInit.init()
        CommandInit.init()
    }

    @Suppress("unused")
    fun initClient() {
        logger.info("Starting on the Client")
        RenderInit.init()
    }

    @Suppress("unused")
    fun initServer() {
        logger.info("Starting on the Server")
    }

}