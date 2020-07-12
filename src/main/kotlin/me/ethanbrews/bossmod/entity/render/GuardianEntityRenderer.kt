package me.ethanbrews.bossmod.entity.render

import me.ethanbrews.bossmod.modid
import net.minecraft.client.render.VertexConsumerProvider
import net.minecraft.client.render.entity.EntityRenderDispatcher
import net.minecraft.client.render.entity.MobEntityRenderer
import net.minecraft.client.render.entity.model.PlayerEntityModel
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.util.Identifier

class GuardianEntityRenderer(dispatcher: EntityRenderDispatcher) : MobEntityRenderer<GuardianEntity, PlayerEntityModel<GuardianEntity>>(dispatcher, PlayerEntityModel(1F, false), 0F) {
    override fun getTexture(entity: GuardianEntity?): Identifier {
        return TEXTURE
    }

    override fun render(
        mobEntity: GuardianEntity?,
        f: Float,
        g: Float,
        matrixStack: MatrixStack?,
        vertexConsumerProvider: VertexConsumerProvider?,
        i: Int
    ) {
        super.render(mobEntity, f, g, matrixStack, vertexConsumerProvider, i)
    }

    companion object {
        val TEXTURE = Identifier("$modid:textures/entity/guardian.png")
    }
}