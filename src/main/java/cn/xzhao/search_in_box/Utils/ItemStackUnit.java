package cn.xzhao.search_in_box.Utils;

import net.minecraft.world.item.ItemStack;

public class ItemStackUnit {
    public String id;
    public int count;

    public ItemStackUnit(String id, int count) {
        this.id = id;
        this.count = count;
    }
    public ItemStackUnit(ItemStack itemStack){
        set(itemStack);
    }

    public void set(ItemStack itemStack) {
        this.id=itemStack.getDescriptionId();
        this.count=itemStack.getCount();
    }
}
