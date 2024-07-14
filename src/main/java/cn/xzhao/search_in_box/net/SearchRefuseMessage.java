package cn.xzhao.search_in_box.net;

import cn.xzhao.search_in_box.client.NetHandler;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class SearchRefuseMessage {
    public int error_code;
    public SearchRefuseMessage(){}

    public SearchRefuseMessage(int i) {
        error_code=i;
    }

    public static void encoder(SearchRefuseMessage message, FriendlyByteBuf byteBuf){
        byteBuf.writeInt(message.error_code);
    }
    public static SearchRefuseMessage decoder(FriendlyByteBuf byteBuf){
        return new SearchRefuseMessage(byteBuf.readInt());
    }
    public static void handle(SearchRefuseMessage msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> NetHandler.showErrorMessage(msg));
        });
        ctx.get().setPacketHandled(true);
    }
}
