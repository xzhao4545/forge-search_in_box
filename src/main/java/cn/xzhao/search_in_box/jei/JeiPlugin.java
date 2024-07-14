package cn.xzhao.search_in_box.jei;

import cn.xzhao.search_in_box.SIB_MOD;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.runtime.IJeiRuntime;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

@mezz.jei.api.JeiPlugin
public class JeiPlugin implements IModPlugin {

    @Override
    public @NotNull ResourceLocation getPluginUid() {
        return new ResourceLocation(SIB_MOD.MODID,"jei_get_item_under_mouse");
    }

    @Override
    public void onRuntimeAvailable(IJeiRuntime jeiRuntime) {
        IModPlugin.super.onRuntimeAvailable(jeiRuntime);
        SIB_MOD.jeiIngredientListOverlay =jeiRuntime.getIngredientListOverlay();
    }

}
