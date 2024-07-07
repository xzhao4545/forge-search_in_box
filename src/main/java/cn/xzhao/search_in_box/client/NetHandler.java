package cn.xzhao.search_in_box.client;

import cn.xzhao.search_in_box.SIB_MOD;
import cn.xzhao.search_in_box.net.ItemBlocksPosMessage;
import cn.xzhao.search_in_box.render.ParticleRegister;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;


@OnlyIn(Dist.CLIENT)
public class NetHandler {
    public static void showParticles(ItemBlocksPosMessage message){
        Player player= Minecraft.getInstance().player;
        ParticleOptions pr=ParticleRegister.TOP_RENDER_PARTICLE_TYPE.get();
        if (player==null)   return;
        Level level=player.level();
        String displayName=Component.translatable(message.itemId).getString();
        if(!level.dimension().equals(message.level)){
            player.sendSystemMessage(Component.translatable(String.format("message.%s.leaved_dimension", SIB_MOD.MODID),displayName,message.blockPosList.size()));
            return;
        }
        if (message.blockPosList.isEmpty())
            player.sendSystemMessage(Component.translatable(String.format("message.%s.not_find", SIB_MOD.MODID), displayName));
        else
            player.sendSystemMessage(Component.translatable(String.format("message.%s.find_result", SIB_MOD.MODID), displayName, message.blockPosList.size()));

        for(BlockPos pos:message.blockPosList){
            level.addParticle(pr,pos.getX(),pos.getY(),pos.getZ(),
                    0,0,0);
        }
    }
}
