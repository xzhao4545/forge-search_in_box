package cn.xzhao.search_in_box.mixins.server;

import cn.xzhao.search_in_box.mixins_methodtrans.IServerPlayerMixins;
import net.minecraft.server.level.ServerPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(ServerPlayer.class)
public class ServerPlayerMixins implements IServerPlayerMixins {
    @Unique
    protected long search_in_box$lastSearchTime;

    @Unique
    @Override
    public void search_in_box$setSearchTime(long time) {
        search_in_box$lastSearchTime =time;
    }

    @Unique
    @Override
    public long search_in_box$getSearchTime() {
        return search_in_box$lastSearchTime;
    }
}
