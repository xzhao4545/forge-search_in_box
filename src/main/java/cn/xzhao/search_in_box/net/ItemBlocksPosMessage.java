package cn.xzhao.search_in_box.net;

import cn.xzhao.search_in_box.client.NetHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Supplier;

public class ItemBlocksPosMessage {
    public List<BlockPos> blockPosList;
    public ResourceKey<Level> level;
    public String itemId;
    public ItemBlocksPosMessage(List<BlockPos> blockPosList,ResourceKey<Level> level,String itemId) {
        this.blockPosList = blockPosList;
        this.level=level;
        this.itemId=itemId;
    }

    public ItemBlocksPosMessage() {
    }

    public static void encoder(ItemBlocksPosMessage message, FriendlyByteBuf byteBuf){
        if(message.blockPosList==null){
            return;
        }
        byteBuf.writeResourceKey(message.level);
        byteBuf.writeUtf(message.itemId);
        byteBuf.writeInt(message.blockPosList.size());
        for(BlockPos bp: message.blockPosList){
            byteBuf.writeInt(bp.getX());
            byteBuf.writeInt(bp.getY());
            byteBuf.writeInt(bp.getZ());
        }
    }
    public static ItemBlocksPosMessage decoder(FriendlyByteBuf byteBuf){
        ItemBlocksPosMessage message=new ItemBlocksPosMessage();
        message.blockPosList=new LinkedList<>();
        message.level =byteBuf.readResourceKey(Registries.DIMENSION);
        message.itemId=byteBuf.readUtf();
        int len=byteBuf.readInt();
        for(int i=0;i<len;i++){
            message.blockPosList.add(new BlockPos(
                    byteBuf.readInt(),
                    byteBuf.readInt(),
                    byteBuf.readInt()
            ));
        }

        return message;
    }
    public static void handle(ItemBlocksPosMessage msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> NetHandler.showParticles(msg));
        });
        ctx.get().setPacketHandled(true);
    }
}
