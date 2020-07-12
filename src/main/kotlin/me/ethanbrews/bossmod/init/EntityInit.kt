package me.ethanbrews.bossmod.init

import me.ethanbrews.bossmod.entity.render.GuardianEntity
import me.ethanbrews.bossmod.modid
import net.fabricmc.fabric.api.`object`.builder.v1.entity.FabricDefaultAttributeRegistry
import net.fabricmc.fabric.api.`object`.builder.v1.entity.FabricEntityTypeBuilder
import net.minecraft.entity.SpawnGroup
import net.minecraft.item.Item
import net.minecraft.item.ItemGroup
import net.minecraft.item.SpawnEggItem
import net.minecraft.util.Identifier
import net.minecraft.util.registry.Registry


object EntityInit {
    val GUARDIAN_ENTITY = FabricEntityTypeBuilder
        .create<GuardianEntity>(SpawnGroup.MISC) { e, w -> GuardianEntity(e, w) }
        .fireImmune()
        .trackable(74, 2)
        .build()

    fun init() {
        Registry.register(Registry.ENTITY_TYPE, Identifier(modid, "guardian"), GUARDIAN_ENTITY)

        // Set attributes here, not really sure why but it stops it crashing
        FabricDefaultAttributeRegistry.register(GUARDIAN_ENTITY, GuardianEntity.createGuardianAttributes())

        // Spawn Eggs
        Registry.register(
            Registry.ITEM,
            Identifier(modid, "spawn_guardian"),
            SpawnEggItem(
                GUARDIAN_ENTITY, 0xf5f2eb, 0xFF55FF, Item.Settings().group(ItemGroup.MISC)
            )
        )
    }
}