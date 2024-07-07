package cn.xzhao.search_in_box.net;

import cn.xzhao.search_in_box.SIB_MOD;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class BroadcastServerMessage{

    public static void encoder(BroadcastServerMessage message, FriendlyByteBuf byteBuf){

    }
    public static BroadcastServerMessage decoder(FriendlyByteBuf byteBuf){
        return new BroadcastServerMessage();
    }
    public static void handle(BroadcastServerMessage msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            SIB_MOD.has_remote_server=true;
        });
        ctx.get().setPacketHandled(true);
    }
}
