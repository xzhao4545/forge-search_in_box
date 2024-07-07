package cn.xzhao.search_in_box.mixins;

import cn.xzhao.search_in_box.client.SlotClickListener;
import cn.xzhao.search_in_box.mixins_methodtrans.SearchableContainerBlock;
import net.minecraft.core.NonNullList;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@OnlyIn(Dist.CLIENT)
@Mixin(AbstractContainerMenu.class)
public class AbstractContainerMenuMixin {
    @Shadow @Final public NonNullList<Slot> slots;

    @Inject(method = "removed",at = @At("RETURN"))
    public void initializeContents(Player p_38940_, CallbackInfo ci){
        if(SlotClickListener.containerBlock!=null){
            ((SearchableContainerBlock) SlotClickListener.containerBlock).search_in_box$updateLocalItems(this.slots.get(0).container);
            SlotClickListener.containerBlock=null;
        }
    }
}
