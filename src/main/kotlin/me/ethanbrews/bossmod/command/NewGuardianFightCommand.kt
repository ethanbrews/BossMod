package me.ethanbrews.bossmod.command

import com.mojang.brigadier.CommandDispatcher
import com.mojang.brigadier.arguments.ArgumentType
import com.mojang.brigadier.context.CommandContext
import me.ethanbrews.bossmod.entity.render.GuardianEntity
import me.ethanbrews.bossmod.init.EntityInit
import me.ethanbrews.bossmod.modid
import net.minecraft.command.arguments.EntityArgumentType
import net.minecraft.entity.EntityType
import net.minecraft.server.command.CommandManager
import net.minecraft.server.command.ServerCommandSource
import net.minecraft.text.TranslatableText

object NewGuardianFightCommand {
    fun register(dispatcher: CommandDispatcher<ServerCommandSource?>) {
        dispatcher.register(
            CommandManager.literal(modid).then(CommandManager.literal("fight").then(CommandManager.literal("guardian").then(
                CommandManager.argument("target", EntityArgumentType.player()).requires {
                    it.hasPermissionLevel(2)
                }.executes {
                    return@executes execute(it)
                }
            )))
        )
    }

    fun execute(ctx: CommandContext<ServerCommandSource>): Int {
        ctx.source.sendFeedback(TranslatableText("commands.fight.start"), false)
        val target = EntityArgumentType.getPlayer(ctx, "target")
        val guardian = GuardianEntity(EntityInit.GUARDIAN_ENTITY, target.world)
        target.world.spawnEntity(guardian)
        guardian.teleport(target.x, target.y, target.z)
        guardian.setFightCenterPosition(target.blockPos)
        return 0
    }
}