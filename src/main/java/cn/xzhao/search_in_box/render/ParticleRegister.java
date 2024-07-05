package cn.xzhao.search_in_box.render;

import cn.xzhao.search_in_box.SIB_MOD;
import cn.xzhao.search_in_box.render.particle.TopRenderParticleType;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.registries.Registries;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ParticleRegister {
    public static final DeferredRegister <ParticleType<?>> PARTICLE_TYPES = DeferredRegister.create(Registries.PARTICLE_TYPE, SIB_MOD.MODID);
    public static final RegistryObject<TopRenderParticleType> TOP_RENDER_PARTICLE_TYPE = register(TopRenderParticleType.NAME, () -> new TopRenderParticleType(false));

    public static <T extends ParticleType<?>> RegistryObject<T> register(String name, Supplier<T> particleType){
        return PARTICLE_TYPES.register(name, particleType);

    }
    // 别忘了注册到总线
    public static void register(IEventBus eventBus){
        PARTICLE_TYPES.register(eventBus);
    }
}
