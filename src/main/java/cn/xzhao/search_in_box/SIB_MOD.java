package cn.xzhao.search_in_box;

import cn.xzhao.search_in_box.command.PlayerListCommand;
import cn.xzhao.search_in_box.net.NetworkHandler;
import cn.xzhao.search_in_box.render.ParticleRegister;
import cn.xzhao.search_in_box.render.particle.TopRenderParticle;
import com.mojang.logging.LogUtils;
import mezz.jei.api.runtime.IIngredientListOverlay;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.server.ServerStartedEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

@Mod(SIB_MOD.MODID)
public class SIB_MOD
{
    // Define mod id in a common place for everything to reference
    public static final String MODID = "search_item_in_box";
    // Directly reference a slf4j logger
    public static final Logger LOGGER = LogUtils.getLogger();
    public static boolean has_remote_server=false;
    public static IIngredientListOverlay jeiIngredientListOverlay;
    public SIB_MOD()
    {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
        NetworkHandler.register();
        ParticleRegister.register(modEventBus);
        // Register our mod's ForgeConfigSpec so that Forge can create and load the config file for us
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }
    public static MinecraftServer server;
    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartedEvent event)
    {
        server=event.getServer();
        // Do something when the server starts
        LOGGER.info(SIB_MOD.MODID+" started.");
    }
    @SubscribeEvent
    public void onCommandRegister(RegisterCommandsEvent event){
        PlayerListCommand.register(event.getDispatcher());
    }
    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents
    {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event)
        {

        }
        @SubscribeEvent
        public static void registerKeyMapping(RegisterKeyMappingsEvent event){
            CustomKeyBindings.register(event);
        }
        @SubscribeEvent
        public static void onParticleFactoryRegistration(RegisterParticleProvidersEvent event) {
            event.registerSpriteSet(ParticleRegister.TOP_RENDER_PARTICLE_TYPE.get(), TopRenderParticle.Provider::new);
        }
    }
}
