package me.ethanbrews.bossmod.network

import io.netty.buffer.Unpooled
import me.ethanbrews.bossmod.modid
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.fabricmc.fabric.api.network.PacketContext
import net.fabricmc.fabric.api.network.ServerSidePacketRegistry
import net.minecraft.client.MinecraftClient
import net.minecraft.entity.Entity
import net.minecraft.entity.EntityType
import net.minecraft.network.Packet
import net.minecraft.network.PacketByteBuf
import net.minecraft.util.Identifier
import net.minecraft.util.math.MathHelper
import net.minecraft.util.registry.Registry


class EntitySpawnPacket {
    object EntitySpawnPacket {
        val ID = Identifier(modid, "${modid}spawn_entity")
        fun createPacket(entity: Entity): Packet<*> {
            val buf = PacketByteBuf(Unpooled.buffer())
            buf.writeVarInt(Registry.ENTITY_TYPE.getRawId(entity.type))
            buf.writeUuid(entity.uuid)
            buf.writeVarInt(entity.entityId)
            buf.writeDouble(entity.x)
            buf.writeDouble(entity.y)
            buf.writeDouble(entity.z)
            buf.writeByte(MathHelper.floor(entity.pitch * 256.0f / 360.0f))
            buf.writeByte(MathHelper.floor(entity.yaw * 256.0f / 360.0f))
            if (entity is SpawnDataEntity) {
                (entity as SpawnDataEntity).writeToBuffer(buf)
            }
            return ServerSidePacketRegistry.INSTANCE.toPacket(ID, buf)
        }

        @Environment(EnvType.CLIENT)
        fun onPacket(context: PacketContext, byteBuf: PacketByteBuf) {
            val type: EntityType<*> = Registry.ENTITY_TYPE.get(byteBuf.readVarInt())
            val entityUUID = byteBuf.readUuid()
            val entityID = byteBuf.readVarInt()
            val x = byteBuf.readDouble()
            val y = byteBuf.readDouble()
            val z = byteBuf.readDouble()
            val pitch = byteBuf.readByte() * 360 / 256.0f
            val yaw = byteBuf.readByte() * 360 / 256.0f
            val world = MinecraftClient.getInstance().world
            val entity: Entity? = type.create(world)
            if (entity is SpawnDataEntity) {
                entity.readFromBuffer(byteBuf)
            }
            context.taskQueue.execute {
                if (entity != null) {
                    entity.updatePosition(x, y, z)
                    entity.updateTrackedPosition(x, y, z)
                    entity.pitch = pitch
                    entity.yaw = yaw
                    entity.entityId = entityID
                    entity.uuid = entityUUID
                    world!!.addEntity(entityID, entity)
                }
            }
        }
    }
}