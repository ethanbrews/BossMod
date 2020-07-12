package me.ethanbrews.bossmod.entity.render

import net.minecraft.entity.EntityType
import net.minecraft.entity.attribute.DefaultAttributeContainer
import net.minecraft.entity.attribute.EntityAttributes
import net.minecraft.entity.mob.HostileEntity
import net.minecraft.world.World


class GuardianEntity(t: EntityType<out GuardianEntity>, w: World) : HostileEntity(t, w) {
    companion object {
        fun createGuardianAttributes(): DefaultAttributeContainer.Builder {
            return createHostileAttributes().add(EntityAttributes.GENERIC_MAX_HEALTH, 600.0)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.24).add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 2.5)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 12.0).add(EntityAttributes.GENERIC_ATTACK_KNOCKBACK, 2.5)
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 38.0)
        }
    }
}