package btwr.core.mixin.entity;

import btwr.core.BTWRMod;
import net.minecraft.client.render.entity.CreeperEntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.model.CreeperEntityModel;
import net.minecraft.entity.mob.CreeperEntity;
import net.minecraft.util.Identifier;
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
    @Shadow @Final private static Identifier TEXTURE;
    @Unique private static final Identifier NEUTERED_TEXTURE =
            new Identifier(BTWRMod.MOD_ID, "textures/entity/neutered_creeper.png");

    public CreeperEntityRendererMixin(EntityRendererFactory.Context context,
                                      CreeperEntityModel<CreeperEntity> entityModel, float f)
    {
        super(context, entityModel, f);
    }


    @Inject(method = "getTexture(Lnet/minecraft/entity/mob/CreeperEntity;)Lnet/minecraft/util/Identifier;",
            at = @At("HEAD"), cancellable = true)
    private void injectedGetTexture(CreeperEntity creeperEntity, CallbackInfoReturnable<Identifier> cir)
    {

        if (creeperEntity.isNeutered())
        {
            cir.setReturnValue(NEUTERED_TEXTURE);
        }
        else
        {
            cir.setReturnValue(TEXTURE);
        }

    }

}
