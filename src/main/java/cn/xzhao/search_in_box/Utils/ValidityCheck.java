package cn.xzhao.search_in_box.Utils;

import cn.xzhao.search_in_box.Config;
import cn.xzhao.search_in_box.mixins_methodtrans.IServerPlayerMixins;
import net.minecraft.server.level.ServerPlayer;

public final class ValidityCheck {
    private ValidityCheck(){}
    public static final int SUCCESS=0;
    public static final int TIME_ERROR=1;
    public static final int PERMISSION_ERROR=2;
    public static final int UNKNOWN_ERROR =4680;
    public static int isValidity(ServerPlayer player){
        boolean contain=Config.playerList.contains(player.getStringUUID());
        if(Config.isEnableWhiteList() == contain){
            return isTimeValidity(player);
        }
        return PERMISSION_ERROR;
    }
    public static int isTimeValidity(ServerPlayer player){
        if(player instanceof IServerPlayerMixins ispm){
            long last=ispm.search_in_box$getSearchTime();
            long now=System.currentTimeMillis();
            if(now-last< Config.searchInterval){
                return TIME_ERROR;
            }else{
                ispm.search_in_box$setSearchTime(now);
                return SUCCESS;
            }
        }
        return UNKNOWN_ERROR;
    }
}
