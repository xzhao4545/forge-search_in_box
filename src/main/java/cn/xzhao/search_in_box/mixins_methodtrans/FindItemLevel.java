package cn.xzhao.search_in_box.mixins_methodtrans;

import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Unique;

import java.util.List;

public interface FindItemLevel {
    @Unique
    int search_in_box$findItemInBox(String item);

    int search_in_box$findItemInBox(ItemStack itemStack);

    @Unique
    void search_in_box$serverFindItemInBox(String item, List<BlockPos> blocksPos);
}
