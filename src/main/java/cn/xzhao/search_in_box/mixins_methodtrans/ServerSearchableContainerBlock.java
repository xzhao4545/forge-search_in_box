package cn.xzhao.search_in_box.mixins_methodtrans;

import cn.xzhao.search_in_box.Utils.ItemsCounterMap;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.ItemStack;


public interface ServerSearchableContainerBlock {
    boolean search_in_box$server_findItemInBox(String item);
    NonNullList<ItemStack> search_in_box$getItems();
    ItemsCounterMap search_in_box$get_itemsMap();
    void search_in_box$initItemsList();
}
