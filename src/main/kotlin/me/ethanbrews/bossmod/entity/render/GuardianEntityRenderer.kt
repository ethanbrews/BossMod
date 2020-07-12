package me.ethanbrews.bossmod.entity.render

import me.ethanbrews.bossmod.modid
import net.minecraft.client.render.entity.EntityRenderDispatcher
import net.minecraft.client.render.entity.MobEntityRenderer
import net.minecraft.client.render.entity.model.PlayerEntityModel
import net.minecraft.util.Identifier

class GuardianEntityRenderer(dispatcher: EntityRenderDispatcher) : MobEntityRenderer<GuardianEntity, PlayerEntityModel<GuardianEntity>>(dispatcher, PlayerEntityModel(1F, false), 1.7F) {
    override fun getTexture(entity: GuardianEntity?): Identifier {
        return TEXTURE
    }

    companion object {
        val TEXTURE = Identifier("$modid:textures/entity/guardian.png")
    }
}