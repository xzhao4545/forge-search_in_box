package cn.xzhao.search_in_box.mixins.client;

import cn.xzhao.search_in_box.Config;
import cn.xzhao.search_in_box.client.SlotClickListener;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.world.inventory.Slot;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractContainerScreen.class)
public abstract class AbsContainerScreenMixin {


    @Inject(method = "renderSlot",at=@At("HEAD"))
    private void addRenderHeightLight(GuiGraphics p_281607_, Slot slot, CallbackInfo ci){
        if(SlotClickListener.isHeightLight&&SlotClickListener.beSearchedItem!=null){
            if(slot.getItem().getDescriptionId().equals(SlotClickListener.beSearchedItem))
                renderSlotHighlight(p_281607_,slot.x,slot.y,0, Config.slotHeightLightColor);
        }
    }
    @Shadow
    public static void renderSlotHighlight(GuiGraphics p281607, int x, int y, int i, int slotHeightLightColor) {}
}
