package me.ethanbrews.bossmod.network

import net.minecraft.network.PacketByteBuf

interface SpawnDataEntity {
    fun writeToBuffer(buffer: PacketByteBuf)
    fun readFromBuffer(buffer: PacketByteBuf)
}