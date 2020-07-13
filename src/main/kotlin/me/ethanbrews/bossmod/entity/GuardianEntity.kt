package me.ethanbrews.bossmod.entity.render

import me.ethanbrews.bossmod.ConfigLoader.config
import me.ethanbrews.bossmod.logger
import me.ethanbrews.bossmod.modid
import net.minecraft.entity.EntityType
import net.minecraft.entity.attribute.DefaultAttributeContainer
import net.minecraft.entity.attribute.EntityAttributes
import net.minecraft.entity.data.TrackedData
import net.minecraft.entity.mob.CreeperEntity
import net.minecraft.entity.mob.HostileEntity
import net.minecraft.nbt.CompoundTag
import net.minecraft.particle.ParticleTypes
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Vec3d
import net.minecraft.world.World
import kotlin.math.cos
import kotlin.math.sin


class GuardianEntity(t: EntityType<out GuardianEntity>, w: World) : HostileEntity(t, w) {

    private var fightCenterPos: BlockPos? = null
    var counter = 0
    private var fightCenterPosition: TrackedData<BlockPos>? = null

    fun setFightCenterPosition(pos: BlockPos? = null) {
        fightCenterPos = pos ?: this.blockPos
        this.dataTracker.set(fightCenterPosition, pos ?: this.blockPos)
        logger.info("New Position: $fightCenterPos")
    }

    override fun initDataTracker() {
        super.initDataTracker()
        dataTracker.startTracking(fightCenterPosition, null)
    }

    override fun tick() {
        super.tick()
        if (world.isClient)
            drawParticleOnPerimeter()
        else {
            logger.info("On server: $fightCenterPos")
        }
    }

    override fun writeCustomDataToTag(tag: CompoundTag?) {
        val arr = IntArray(3)
        arr[0] = fightCenterPos!!.x
        arr[1] = fightCenterPos!!.y
        arr[2] = fightCenterPos!!.z
        if (fightCenterPos != null)
            tag?.putIntArray("fightCenterPos", arr)
    }

    override fun readCustomDataFromTag(tag: CompoundTag?) {
        val arr = tag?.getIntArray("fightCenterPos")
        if (arr != null) {
            fightCenterPos = BlockPos(arr[0], arr[1], arr[2])
        }
    }

    private fun drawParticleOnPerimeter() {
        fightCenterPos = this.dataTracker.get(fightCenterPosition)
        if (fightCenterPos == null) {
            logger.info("$fightCenterPos")
            return
        }

        counter += (counter+1) % ParticlesTotalPoints
        val perimeterPosition = getPositionOnPerimeter(counter)
        val position = Vec3d.of(fightCenterPos).add(perimeterPosition.first, 0.0, perimeterPosition.second)
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
