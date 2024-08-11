package cn.xzhao.search_in_box.net;

import cn.xzhao.search_in_box.SIB_MOD;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class NetworkHandler {
    private static final String PROTOCOL_VERSION = "1";
    public static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(
            new ResourceLocation(SIB_MOD.MODID, "main"),
            () -> PROTOCOL_VERSION,
            NetworkRegistry.acceptMissingOr(PROTOCOL_VERSION),
            NetworkRegistry.acceptMissingOr(PROTOCOL_VERSION)
    );
    private static int id=0;
    public static void register(){
        registerMessage(SearchRequestMessage.class,SearchRequestMessage::encoder,SearchRequestMessage::decoder,SearchRequestMessage::handle);
        registerMessage(BroadcastServerMessage.class,BroadcastServerMessage::encoder,BroadcastServerMessage::decoder,BroadcastServerMessage::handle);
        registerMessage(ItemBlocksPosMessage.class,ItemBlocksPosMessage::encoder,ItemBlocksPosMessage::decoder,ItemBlocksPosMessage::handle);
        registerMessage(SearchRefuseMessage.class,SearchRefuseMessage::encoder,SearchRefuseMessage::decoder,SearchRefuseMessage::handle);
    }
    protected static <MSG> void registerMessage(Class<MSG> messageType, BiConsumer<MSG, FriendlyByteBuf> encoder, Function<FriendlyByteBuf, MSG> decoder, BiConsumer<MSG, Supplier<NetworkEvent.Context>> messageConsumer){
        INSTANCE.registerMessage(id++,messageType,encoder,decoder,messageConsumer);
    }
}
