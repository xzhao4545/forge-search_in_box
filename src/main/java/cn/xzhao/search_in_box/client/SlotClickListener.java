package cn.xzhao.search_in_box.client;

import cn.xzhao.search_in_box.Config;
import cn.xzhao.search_in_box.CustomKeyBindings;
import cn.xzhao.search_in_box.SIB_MOD;
import cn.xzhao.search_in_box.mixins_methodtrans.FindItemLevel;
import cn.xzhao.search_in_box.net.NetworkHandler;
import cn.xzhao.search_in_box.net.SearchRequestMessage;
import mezz.jei.api.ingredients.IIngredientType;
import mezz.jei.api.ingredients.ITypedIngredient;
import mezz.jei.api.runtime.IRecipesGui;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ClientPlayerNetworkEvent;
import net.minecraftforge.client.event.ScreenEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Optional;

@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber(modid = SIB_MOD.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class SlotClickListener {


    @SubscribeEvent
    public static void onClientPlayerNetworkLoggingOut(ClientPlayerNetworkEvent.LoggingOut event) {
        SIB_MOD.has_remote_server = false;
    }

    @SubscribeEvent
    public static void onGuiKeyPress(ScreenEvent.KeyPressed.Pre event) {
        // 检查是否按下绑定的键
        if (CustomKeyBindings.searchKey.getKey().getValue() == event.getKeyCode()) {
            ItemStack itemStack = null;
            Screen screen = event.getScreen();
            // 检查当前屏幕是否为物品栏
            if (screen instanceof AbstractContainerScreen<?> abs) {
                // 获取鼠标位置对应的物品槽
                Slot slot = abs.getSlotUnderMouse();
                if (slot == null) return;
                itemStack = slot.getItem();
                Minecraft.getInstance().setScreen(null);
            }
            //检查是否启用jei
            if (itemStack == null && SIB_MOD.jeiIngredientListOverlay != null) {
                Optional<ITypedIngredient<?>> ingredientUnderMouse = SIB_MOD.jeiIngredientListOverlay.getIngredientUnderMouse();
                if (ingredientUnderMouse.isPresent() && ingredientUnderMouse.get().getIngredient() instanceof ItemStack is) {
                    itemStack = is;
                    Minecraft.getInstance().setScreen(null);
                }
                if (itemStack == null && screen instanceof IRecipesGui irg) {
                    itemStack = irg.getIngredientUnderMouse(() -> ItemStack.class).get();
                    Minecraft.getInstance().setScreen(null);
                }
            }
            // 检查物品槽是否为空
            if (itemStack != null && !itemStack.isEmpty() && Minecraft.getInstance().level != null) {
                Player player = Minecraft.getInstance().player;
                Level level = Minecraft.getInstance().level;
                int px = player.blockPosition().getX() >> 4;
                int py = player.blockPosition().getZ() >> 4;
                beSearchedItem = itemStack.getDescriptionId();
                if (SIB_MOD.has_remote_server) {
                    NetworkHandler.INSTANCE.sendToServer(new SearchRequestMessage(level.dimension(), px, py, Config.searchDistance, itemStack.getDescriptionId()));
                } else {
                    // 执行自定义逻辑
                    int end = Config.searchDistance;
                    int beg = -1 * end;
                    int num = 0;
                    for (int x = beg; x <= end; ++x) {
                        for (int y = beg; y <= end; ++y) {
                            num += ((FindItemLevel) level.getChunk(px + x, py + y)).search_in_box$findItemInBox(itemStack);
                        }
                    }
                    if (num == 0)
                        player.sendSystemMessage(Component.translatable(String.format("message.%s.not_find", SIB_MOD.MODID), itemStack.getDisplayName()));
                    else {
                        player.sendSystemMessage(Component.translatable(String.format("message.%s.find_result", SIB_MOD.MODID), itemStack.getDisplayName(), num));
                        startHeightLightSlotClock();
                    }
                }
            }

        }
    }

    private static int nowTick = 0;
    public static boolean isHeightLight = false;
    private static boolean isBegin = false;
    public static String beSearchedItem;

    public static void startHeightLightSlotClock() {
        nowTick = 0;
        isHeightLight = true;
        isBegin = true;
    }

    public static void closeHeightLightSlot() {
        isBegin = false;
        nowTick = 0;
        isHeightLight = false;
        beSearchedItem = null;
    }

    @SubscribeEvent
    public static void playerTick(TickEvent.ClientTickEvent event) {
        if (isBegin && nowTick < Config.slotHeightLightTime) {
            ++nowTick;
            if (nowTick >= Config.slotHeightLightTime) {
                closeHeightLightSlot();
            }
        }
    }

    public static BaseContainerBlockEntity containerBlock;

    @SubscribeEvent
    public static void updateTraceBlock(PlayerInteractEvent.RightClickBlock event) {
        if (SIB_MOD.has_remote_server) return;
        Player player = event.getEntity();
        if (player.isLocalPlayer()) {
            if (player.level().getBlockEntity(event.getPos()) instanceof BaseContainerBlockEntity ct) {
                containerBlock = ct;
            } else {
                containerBlock = null;
            }
        }
    }
}
