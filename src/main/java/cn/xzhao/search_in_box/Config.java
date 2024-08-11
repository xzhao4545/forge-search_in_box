package cn.xzhao.search_in_box;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;

import java.util.*;


// An example config class. This is not required, but it's a good idea to have one to keep your config organized.
// Demonstrates how to use Forge's config APIs
@Mod.EventBusSubscriber(modid = SIB_MOD.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class Config
{
    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();


    private static final ForgeConfigSpec.IntValue SEARCH_DISTANCE = BUILDER
            .comment("Search distance.")
            .comment("Unit:Chunk")
            .defineInRange(List.of("COMMON","distance"), 2, 0, Integer.MAX_VALUE);
    public static final ForgeConfigSpec.IntValue PARTICLE_LIVE_TIME = BUILDER
            .comment("How long the particle lives")
            .comment("Unit:Tick")
            .defineInRange(List.of("CLIENT","particle_live_time"), 400, 20, 50000);
    private static final ForgeConfigSpec.IntValue SLOT_HEIGHT_LIGHT_TIME = BUILDER
        .comment("How long the slot height light.")
            .comment("Unit:Tick")
        .defineInRange(List.of("CLIENT","slot_height_light_time"), 400, 20, 5000);
    private static final ForgeConfigSpec.IntValue SLOT_HEIGHT_LIGHT_COLOR = BUILDER
            .comment("The slot height light color,you should convert the ARGB value to int.")
            .defineInRange(List.of("CLIENT","slot_height_light_color"), 0xffcc5757, Integer.MIN_VALUE, Integer.MAX_VALUE);
    private static final ForgeConfigSpec.IntValue SEARCH_INTERVAL=BUILDER
            .comment("The minimum search interval per player on the server side.This parameter is valid only on the server.")
            .comment("Unit:ms")
            .defineInRange(List.of("SERVER","server_search_interval"),500,0,Integer.MAX_VALUE);
    private static final ForgeConfigSpec.BooleanValue ENABLE_WHITE_LIST=BUILDER
            .comment("Whether to enable the whitelist. The default value is blacklist.")
            .define(List.of("SERVER","enable_white_list"),false);
    private static final ForgeConfigSpec.ConfigValue<List<?>> SEARCH_BLACK_LIST=BUILDER
            .comment("The blacklist of players. You need to fill in the uuid of the player.")
            .defineListAllowEmpty(List.of("SERVER","black_list"), ArrayList::new,o->o instanceof String);
    private static final ForgeConfigSpec.ConfigValue<List<?>> SEARCH_WHITE_LIST=BUILDER
            .comment("The white of players. You need to fill in the uuid of the player.")
            .defineListAllowEmpty(List.of("SERVER","white_list"), ArrayList::new,o->o instanceof String);
    static final ForgeConfigSpec SPEC = BUILDER.build();

    public static int searchDistance;
    public static int particleLiveTime;
    public static int slotHeightLightTime;
    public static int slotHeightLightColor;
    public static int searchInterval;
    private static boolean enableWhiteList=false;
    public static Set<String> playerList;
    @SubscribeEvent
    static void onLoad(final ModConfigEvent event)
    {
        searchDistance = SEARCH_DISTANCE.get();
        particleLiveTime=PARTICLE_LIVE_TIME.get();
        slotHeightLightTime=SLOT_HEIGHT_LIGHT_TIME.get();
        slotHeightLightColor=SLOT_HEIGHT_LIGHT_COLOR.get();
        searchInterval=SEARCH_INTERVAL.get();
        enableWhiteList=ENABLE_WHITE_LIST.get();
        if(enableWhiteList){
            playerList= (Set<String>) new TreeSet<>(SEARCH_WHITE_LIST.get() );
        }else {
            playerList= (Set<String>) new TreeSet<>(SEARCH_BLACK_LIST.get());
        }
    }

    public static boolean isEnableWhiteList() {
        return enableWhiteList;
    }

    public static boolean changeEnableList(boolean enableWhiteList){
        if(enableWhiteList==Config.enableWhiteList) return false;
        if(Config.enableWhiteList){
            SEARCH_WHITE_LIST.set(Arrays.asList(playerList.toArray()));
            playerList= (Set<String>) new TreeSet<>(SEARCH_BLACK_LIST.get());
        }else {
            SEARCH_BLACK_LIST.set(Arrays.asList(playerList.toArray()));
            playerList= (Set<String>) new TreeSet<>(SEARCH_WHITE_LIST.get() );
        }
        Config.enableWhiteList=enableWhiteList;
        Config.SPEC.save();
        return true;
    }
    public static void saveCurrentList(List<?> list){
        if(enableWhiteList){
            SEARCH_WHITE_LIST.set(list);
        }else {
            SEARCH_BLACK_LIST.set(list);
        }
        SPEC.save();
    }
}
