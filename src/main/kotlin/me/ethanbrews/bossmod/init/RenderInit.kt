package me.ethanbrews.bossmod.init

import me.ethanbrews.bossmod.entity.render.GuardianEntityRenderer
import me.ethanbrews.bossmod.network.EntitySpawnPacket
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry
import net.fabricmc.fabric.api.network.ClientSidePacketRegistry




object RenderInit {
    fun registerClientboundPackets() {
        ClientSidePacketRegistry.INSTANCE.register(EntitySpawnPacket.EntitySpawnPacket.ID, EntitySpawnPacket.EntitySpawnPacket::onPacket)
    }

    fun init() {
        EntityRendererRegistry.INSTANCE.register(EntityInit.GUARDIAN_ENTITY)
            { dispatcher, _ -> GuardianEntityRenderer(dispatcher)}

        // Network
        registerClientboundPackets()

        // Blocks
    }
}