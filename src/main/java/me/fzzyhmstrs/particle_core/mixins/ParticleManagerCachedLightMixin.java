package me.fzzyhmstrs.particle_core.mixins;

import it.unimi.dsi.fastutil.objects.Object2IntLinkedOpenCustomHashMap;
import it.unimi.dsi.fastutil.objects.Object2IntLinkedOpenHashMap;
import me.fallenbreath.conditionalmixin.api.annotation.Condition;
import me.fallenbreath.conditionalmixin.api.annotation.Restriction;
import me.fzzyhmstrs.particle_core.interfaces.CachedLightProvider;
import me.fzzyhmstrs.particle_core.plugin.PcConditionTester;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.LightmapTextureManager;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.CLIENT)
@Restriction(
        require = {
                @Condition(type = Condition.Type.TESTER, tester = PcConditionTester.class)
        }
)
@Mixin(ParticleManager.class)
public class ParticleManagerCachedLightMixin implements CachedLightProvider {

    @Unique
    private final Object2IntLinkedOpenHashMap<BlockPos> cachedLightMap = new Object2IntLinkedOpenHashMap<>(64, 0.75f);

    @Override
    public Object2IntLinkedOpenHashMap<BlockPos> particle_core_getCache() {
        return cachedLightMap;
    }

    @Inject(method = "renderParticles", at = @At("HEAD"))
    private void particle_core_setupDefaultRotations(LightmapTextureManager lightmapTextureManager, Camera camera, float tickDelta, CallbackInfo ci){
        cachedLightMap.clear();
    }
}