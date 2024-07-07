package cn.xzhao.search_in_box.mixins.server.container;

import cn.xzhao.search_in_box.Utils.ItemStackUnit;
import cn.xzhao.search_in_box.Utils.ItemsCounterMap;
import cn.xzhao.search_in_box.mixins_methodtrans.ServerSearchableContainerBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.List;


@Mixin(AbstractFurnaceBlockEntity.class)
public abstract class ServerAbsFurnaceBlockEntityMixin extends BaseContainerBlockEntity implements ServerSearchableContainerBlock {
    @Shadow public abstract int getContainerSize();

    protected ServerAbsFurnaceBlockEntityMixin(BlockEntityType<?> p_155076_, BlockPos p_155077_, BlockState p_155078_) {
        super(p_155076_, p_155077_, p_155078_);
    }
    @Unique
    protected ItemsCounterMap search_in_box$itemsMap=new ItemsCounterMap();
    @Shadow
    protected NonNullList<ItemStack> items;

    @Shadow public abstract ItemStack getItem(int p_58328_);
    @Unique
    @Override
    public NonNullList<ItemStack> search_in_box$getItems(){
        return this.items;
    }
    @Unique
    @Override
    public ItemsCounterMap search_in_box$get_itemsMap() {
        return this.search_in_box$itemsMap;
    }
    @Inject(method = "setItem",at=@At("RETURN"))
    public void setItemEnd(int p_58333_, ItemStack p_58334_, CallbackInfo ci){
        if(search_in_box$itemsList==null)   return;
        ItemStackUnit isu=search_in_box$itemsList.get(p_58333_);
        search_in_box$itemsMap.sub(isu.id,isu.count);
        isu.set(p_58334_);
        ItemStack tis=getItem(p_58333_);
        if(!tis.isEmpty())
            search_in_box$itemsMap.add(tis.getDescriptionId(),tis.getCount());
    }
    @Override
    public boolean search_in_box$server_findItemInBox(String item){
        return search_in_box$itemsMap.containsKey(item);
    }

    @Unique
    List<ItemStackUnit> search_in_box$itemsList;
    @Override
    public void search_in_box$initItemsList(){
        search_in_box$itemsList = new ArrayList<>(this.items.size());
        for(ItemStack itemStack:this.items){
            search_in_box$itemsList.add(new ItemStackUnit(itemStack));
        }
    }
}
