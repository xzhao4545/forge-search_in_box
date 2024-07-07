package cn.xzhao.search_in_box;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;

// An example config class. This is not required, but it's a good idea to have one to keep your config organized.
// Demonstrates how to use Forge's config APIs
@Mod.EventBusSubscriber(modid = SIB_MOD.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class Config
{
    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();


    private static final ForgeConfigSpec.IntValue SEARCH_DISTANCE = BUILDER
            .comment("Search distance,Unit:Chunk")
            .defineInRange("distance", 2, 0, Integer.MAX_VALUE);
    private static final ForgeConfigSpec.IntValue PARTICLE_LIVE_TIME = BUILDER
            .comment("How long the particle lives")
            .defineInRange("particle_live_time", 200, 20, 5000);
    static final ForgeConfigSpec SPEC = BUILDER.build();

    public static int searchDistance;
    public static int particleLiveTime;
    @SubscribeEvent
    static void onLoad(final ModConfigEvent event)
    {
        searchDistance = SEARCH_DISTANCE.get();
        particleLiveTime=PARTICLE_LIVE_TIME.get();
    }
}
