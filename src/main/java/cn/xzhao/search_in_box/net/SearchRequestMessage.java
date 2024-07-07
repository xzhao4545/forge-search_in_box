package cn.xzhao.search_in_box.net;

import cn.xzhao.search_in_box.Config;
import cn.xzhao.search_in_box.SIB_MOD;
import cn.xzhao.search_in_box.mixins_methodtrans.FindItemLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Supplier;

public class SearchRequestMessage {
    public int ChunkX;
    public int ChunkY;
    public int distance=2;
    public String item;
    public ResourceKey<Level> level;
    public SearchRequestMessage(ResourceKey<Level> level,int chunkX, int chunkY, int distance, String item) {
        this.level=level;
        ChunkX = chunkX;
        ChunkY = chunkY;
        this.distance = distance;
        this.item = item;
    }

    public SearchRequestMessage() {
    }
    public static void encoder(SearchRequestMessage message, FriendlyByteBuf byteBuf){
        byteBuf.writeResourceKey(message.level);
        byteBuf.writeInt(message.ChunkX);
        byteBuf.writeInt(message.ChunkY);
        byteBuf.writeInt(message.distance);
        byteBuf.writeUtf(message.item);
    }
    public static SearchRequestMessage decoder(FriendlyByteBuf byteBuf){
        SearchRequestMessage message=new SearchRequestMessage();
        message.level =byteBuf.readResourceKey(Registries.DIMENSION);
        message.ChunkX =byteBuf.readInt();
        message.ChunkY =byteBuf.readInt();
        message.distance =byteBuf.readInt();
        message.item =byteBuf.readUtf();
        return message;
    }
    public static void handle(SearchRequestMessage msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            ServerPlayer sender=ctx.get().getSender();
            // 处理接收到的消息
            ServerLevel level= SIB_MOD.server.getLevel(msg.level);
            if(level!=null){
                int end = Math.min(Config.searchDistance,msg.distance);
                int beg = -1 * end;
                int px = msg.ChunkX;
                int py = msg.ChunkY;
                List<BlockPos> blocksPos=new LinkedList<>();
                for (int x = beg; x <= end; ++x) {
                    for (int y = beg; y <= end; ++y) {
                        ((FindItemLevel) level.getChunk(px + x, py + y)).
                                search_in_box$serverFindItemInBox(msg.item,blocksPos);
                    }
                }
                if (sender != null) {
                    NetworkHandler.INSTANCE.sendTo(new ItemBlocksPosMessage(blocksPos,msg.level,msg.item),
                            sender.connection.connection,
                            NetworkDirection.PLAY_TO_CLIENT
                    );
                }
            }
        });
        ctx.get().setPacketHandled(true);
    }
}
