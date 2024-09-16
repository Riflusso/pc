package me.fzzyhmstrs.particle_core.mixins;

import me.fallenbreath.conditionalmixin.api.annotation.Condition;
import me.fallenbreath.conditionalmixin.api.annotation.Restriction;
import me.fzzyhmstrs.particle_core.PcConfig;
import me.fzzyhmstrs.particle_core.plugin.PcConditionTester;
import net.minecraft.client.option.ParticlesMode;
import net.minecraft.client.option.SimpleOption;
import net.minecraft.client.render.WorldRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Restriction(
        require = {
                @Condition(type = Condition.Type.TESTER, tester = PcConditionTester.class)
        }
)
@Mixin(WorldRenderer.class)
public class WorldRendererDecreaseMixin {

    @Redirect(method = "getRandomParticleSpawnChance", at = @At(value = "INVOKE", target = "net/minecraft/client/option/SimpleOption.getValue ()Ljava/lang/Object;"))
    private <T> T particle_core_reduceParticleSpawnType(SimpleOption<T> instance) {
        T value = instance.getValue();
        if (value instanceof ParticlesMode) {
            return (T)PcConfig.INSTANCE.getImpl().getReducedParticleSpawnType((ParticlesMode) value);
        }
        return value;
    }

}