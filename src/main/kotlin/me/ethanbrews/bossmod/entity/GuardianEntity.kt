package me.ethanbrews.bossmod.entity.render

import com.ibm.icu.text.MessagePattern
import me.ethanbrews.bossmod.ConfigLoader.config
import me.ethanbrews.bossmod.logger
import me.ethanbrews.bossmod.modid
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.minecraft.client.particle.FireSmokeParticle
import net.minecraft.entity.EntityType
import net.minecraft.entity.attribute.DefaultAttributeContainer
import net.minecraft.entity.attribute.EntityAttributes
import net.minecraft.entity.mob.HostileEntity
import net.minecraft.particle.DefaultParticleType
import net.minecraft.particle.ParticleEffect
import net.minecraft.particle.ParticleTypes
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Vec3d
import net.minecraft.world.World
import kotlin.math.cos
import kotlin.math.sin


class GuardianEntity(t: EntityType<out GuardianEntity>, w: World) : HostileEntity(t, w) {

    var FightCenterPosition: BlockPos? = null
    var counter = 0

    override fun mobTick() {
        super.mobTick()
        drawParticleOnPerimeter()
    }

    @Environment(EnvType.CLIENT)
    private fun drawParticleOnPerimeter() {
        if (FightCenterPosition == null)
            return
        counter += (counter+1) % ParticlesTotalPoints
        val perimeterPosition = getPositionOnPerimeter(counter)
        val position = Vec3d.of(FightCenterPosition).add(perimeterPosition.first, 0.0, perimeterPosition.second)
        logger.info("Spawning particle at: $position")
        world.addParticle(ParticleTypes.CLOUD, position.x, position.y, position.z, 0.0, 1.0, 0.0)
    }

    private fun getPositionOnPerimeter(current: Int): Pair<Double, Double> {
        val theta = ((2 * Math.PI) / ParticlesTotalPoints)
        val angle = theta * current

        return Pair(
            FightRadius * cos(angle),
            FightRadius * sin(angle)
        )
    }

    companion object {

        val FightRadius = config.getInt("$modid.fight.radius")
        val ParticlesTotalPoints = config.getInt("$modid.fight.particles.total")

        fun createGuardianAttributes(): DefaultAttributeContainer.Builder {
            return createHostileAttributes().add(EntityAttributes.GENERIC_MAX_HEALTH, 600.0)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.24).add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 2.5)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 12.0).add(EntityAttributes.GENERIC_ATTACK_KNOCKBACK, 2.5)
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 38.0)
        }
    }
}