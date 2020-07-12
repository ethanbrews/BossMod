package me.ethanbrews.bossmod

import com.typesafe.config.ConfigFactory
import java.io.File

object ConfigLoader {

    private val ResourceBasename = "defaults.conf"
    private val EditableConfigFile = File("config/$modid.conf")

    val config = ConfigFactory.parseFile(EditableConfigFile).withFallback(
        ConfigFactory.load(ResourceBasename)
    )
}