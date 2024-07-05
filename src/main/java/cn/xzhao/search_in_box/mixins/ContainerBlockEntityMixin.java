package cn.xzhao.search_in_box.mixins;

import cn.xzhao.search_in_box.SIB_MOD;
import cn.xzhao.search_in_box.mixins_methodtrans.SearchableContainerBlock;
import cn.xzhao.search_in_box.render.ParticleRegister;
import cn.xzhao.search_in_box.render.particle.TopRenderParticleType;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

import java.util.Set;
import java.util.TreeSet;

@Mixin(BaseContainerBlockEntity.class)
public abstract class ContainerBlockEntityMixin extends BlockEntity implements Container, SearchableContainerBlock {
    public ContainerBlockEntityMixin(BlockEntityType<?> p_155228_, BlockPos p_155229_, BlockState p_155230_) {
        super(p_155228_, p_155229_, p_155230_);
    }

    @Unique
    Set<String> search_in_box$itemsId =new TreeSet<>();
    @Unique
    @Override
    public void search_in_box$updateLocalItems(Container items){
        int len=this.getContainerSize();
        ItemStack item;
        TreeSet<String> temp=new TreeSet<>();
        for(int i=0;i<len;++i){
            item=items.getItem(i);
            if(!item.isEmpty()){
                temp.add(item.getItem().getDescriptionId());
            }
        }
        search_in_box$itemsId =temp;
    }
    public TopRenderParticleType topParticle= ParticleRegister.TOP_RENDER_PARTICLE_TYPE.get();
    @Unique
    @Override
    public boolean search_in_box$findItemInBox(ItemStack itemStack){
        if(!itemStack.isEmpty()&&search_in_box$itemsId.contains(itemStack.getItem().getDescriptionId())){
            level.addAlwaysVisibleParticle(topParticle,this.worldPosition.getX(),this.worldPosition.getY(),this.worldPosition.getZ(),
                    0,0,0);
            return true;
        }
        return false;
    }
}
