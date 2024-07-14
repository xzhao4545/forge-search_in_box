package cn.xzhao.search_in_box.client;

import cn.xzhao.search_in_box.SIB_MOD;
import cn.xzhao.search_in_box.Utils.ValidityCheck;
import cn.xzhao.search_in_box.net.ItemBlocksPosMessage;
import cn.xzhao.search_in_box.net.SearchRefuseMessage;
import cn.xzhao.search_in_box.render.ParticleRegister;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import static cn.xzhao.search_in_box.client.SlotClickListener.startHeightLightSlotClock;


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
        else{
            player.sendSystemMessage(Component.translatable(String.format("message.%s.find_result", SIB_MOD.MODID), displayName, message.blockPosList.size()));
            startHeightLightSlotClock();

        }

        for(BlockPos pos:message.blockPosList){
            level.addParticle(pr,pos.getX(),pos.getY(),pos.getZ(),
                    0,0,0);
        }
    }
    public static void showErrorMessage(SearchRefuseMessage message){
        LocalPlayer player=Minecraft.getInstance().player;
        if(player!=null){
            String send;
            switch (message.error_code){
                case ValidityCheck.TIME_ERROR:send=String.format("message.%s.error.frequently",SIB_MOD.MODID);
                break;
                case ValidityCheck.PERMISSION_ERROR:send=String.format("message.%s.error.permission",SIB_MOD.MODID);
                break;
                default: send=String.format("message.%s.error.unknown",SIB_MOD.MODID);
                    break;

            }
            player.sendSystemMessage(Component.translatable(send));
        }
    }
}
