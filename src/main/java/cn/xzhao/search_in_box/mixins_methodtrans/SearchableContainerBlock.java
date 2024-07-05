package cn.xzhao.search_in_box.mixins_methodtrans;

import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;


public interface SearchableContainerBlock {
    void search_in_box$updateLocalItems(Container items);
    boolean search_in_box$findItemInBox(ItemStack itemStack);
}
