package cn.xzhao.search_in_box.server;

import cn.xzhao.search_in_box.SIB_MOD;
import cn.xzhao.search_in_box.net.BroadcastServerMessage;
import cn.xzhao.search_in_box.net.NetworkHandler;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.network.NetworkDirection;

@Mod.EventBusSubscriber(modid = SIB_MOD.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ServerEventListener {
    @SubscribeEvent
    public static void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
        NetworkHandler.INSTANCE.sendTo(new BroadcastServerMessage(),
                ((ServerPlayer)event.getEntity()).connection.connection,
                NetworkDirection.PLAY_TO_CLIENT
                );
    }
}
