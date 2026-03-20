package org.btwr.core.mixin.client;

import net.minecraft.client.render.entity.CreeperEntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.model.CreeperEntityModel;
import net.minecraft.entity.mob.CreeperEntity;
import net.minecraft.util.Identifier;
import org.btwr.core.api.NeuteredCreeperTextures;
import org.btwr.core.data.BTWRDataAttachments;
import org.btwr.core.tag.BTWRTags;
import org.btwr.shared_library.util.utils.IdUtils;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(CreeperEntityRenderer.class)
public abstract class CreeperEntityRendererMixin extends MobEntityRenderer<CreeperEntity, CreeperEntityModel<CreeperEntity>>
{
    @Unique
    private static final Identifier NEUTERED_TEXTURE = IdUtils.ofBTWR("textures/entity/neutered_creeper.png");

    public CreeperEntityRendererMixin(EntityRendererFactory.Context context, CreeperEntityModel<CreeperEntity> entityModel, float f)
    {
        super(context, entityModel, f);
    }

    @Inject(method = "getTexture(Lnet/minecraft/entity/mob/CreeperEntity;)Lnet/minecraft/util/Identifier;",
            at = @At("HEAD"), cancellable = true)
    private void injectedGetTexture(CreeperEntity creeperEntity, CallbackInfoReturnable<Identifier> cir) {
        var data = creeperEntity.getAttached(BTWRDataAttachments.CREEPER_DATA);

        if (data == null || !data.isNeutered()) return;

        // Only do this if the type belongs to the tag
        var type = creeperEntity.getType();
        if (!type.isIn(BTWRTags.EntityTypes.NEUTERABLE_CREEPERS)) return;

        // External override?
        var override = NeuteredCreeperTextures.get(type);
        if (override != null) {
            cir.setReturnValue(override);
            return;
        }

        // Fallback to the default neutered texture
        cir.setReturnValue(NEUTERED_TEXTURE);
    }

}