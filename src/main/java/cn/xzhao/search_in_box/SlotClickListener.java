package cn.xzhao.search_in_box;

import cn.xzhao.search_in_box.mixins_methodtrans.FindItemLevel;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ScreenEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber(modid = SIB_MOD.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class SlotClickListener {


    @SubscribeEvent
    public static void  onGuiKeyPress(ScreenEvent.KeyPressed.Pre event) {
        // 检查当前屏幕是否为物品栏
        if (event.getScreen() instanceof AbstractContainerScreen<?>) {

            // 检查是否按下绑定的键
            if (CustomKeyBindings.searchKey.getKey().getValue()==event.getKeyCode()) {
                AbstractContainerScreen screen =  (AbstractContainerScreen)event.getScreen();

                // 获取鼠标位置对应的物品槽
                Slot slot=screen.getSlotUnderMouse();
                if(slot==null)  return;
                ItemStack itemStack = slot.getItem();

                // 检查物品槽是否为空
                if (!itemStack.isEmpty()&&Minecraft.getInstance().level!=null) {
                    // 执行自定义逻辑
                    int end=Config.searchDistance;
                    int beg=-1*end;
                    Player player=Minecraft.getInstance().player;
                    int px=player.blockPosition().getX()>>4;
                    int py=player.blockPosition().getZ()>>4;
                    Level level=Minecraft.getInstance().level;
                    int num=0;
                    for(int x=beg;x<=end;++x){
                        for(int y=beg;y<=end;++y){

                            num+=((FindItemLevel)level.getChunk(px + x, py + y)).search_in_box$findItemInBox(itemStack);
                        }
                    }
                    if(num==0)
                        player.sendSystemMessage(Component.translatable(String.format("message.%s.not_find",SIB_MOD.MODID),itemStack.getDisplayName()));
                    else
                        player.sendSystemMessage(Component.translatable(String.format("message.%s.find_result",SIB_MOD.MODID),itemStack.getDisplayName(),num));
                    screen.onClose();
                }
            }
        }
    }

    public static BaseContainerBlockEntity containerBlock;

    @SubscribeEvent
    public static void onContainerChanged(PlayerInteractEvent.RightClickBlock event) {
        // 检查事件是否涉及到箱子的数据更新
        Player player=event.getEntity();
        if(player.isLocalPlayer()){
            if(player.level().getBlockEntity(event.getPos()) instanceof BaseContainerBlockEntity ct){
                containerBlock=ct;
            }else{
                containerBlock=null;
            }
        }
    }
}
