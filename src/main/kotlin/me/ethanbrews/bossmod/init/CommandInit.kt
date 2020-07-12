package me.ethanbrews.bossmod.init

import com.mojang.brigadier.CommandDispatcher
import me.ethanbrews.bossmod.command.NewGuardianFightCommand
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback
import net.minecraft.server.command.ServerCommandSource

object CommandInit {
    fun init() {
        CommandRegistrationCallback.EVENT.register(CommandRegistrationCallback { dispatcher: CommandDispatcher<ServerCommandSource?>?, dedicated: Boolean ->
            NewGuardianFightCommand.register(dispatcher!!)
        })
    }
}