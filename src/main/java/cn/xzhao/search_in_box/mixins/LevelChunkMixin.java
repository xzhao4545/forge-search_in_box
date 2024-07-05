package cn.xzhao.search_in_box.mixins;

import cn.xzhao.search_in_box.mixins_methodtrans.FindItemLevel;
import cn.xzhao.search_in_box.mixins_methodtrans.SearchableContainerBlock;
import net.minecraft.core.Registry;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.chunk.LevelChunkSection;
import net.minecraft.world.level.chunk.UpgradeData;
import net.minecraft.world.level.levelgen.blending.BlendingData;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(LevelChunk.class)
public abstract class LevelChunkMixin extends ChunkAccess implements FindItemLevel {

    public LevelChunkMixin(ChunkPos p_187621_, UpgradeData p_187622_, LevelHeightAccessor p_187623_, Registry<Biome> p_187624_, long p_187625_, @Nullable LevelChunkSection[] p_187626_, @Nullable BlendingData p_187627_) {
        super(p_187621_, p_187622_, p_187623_, p_187624_, p_187625_, p_187626_, p_187627_);
    }
    @Unique
    @Override
    public int search_in_box$findItemInBox(ItemStack itemStack){
        int i=0;
        for(BlockEntity be: this.blockEntities.values()){
            if(be instanceof SearchableContainerBlock scb){
                if(scb.search_in_box$findItemInBox(itemStack))
                    ++i;
            }
        }
        return i;
    }

}
