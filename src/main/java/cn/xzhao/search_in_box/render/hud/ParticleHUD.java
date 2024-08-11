package cn.xzhao.search_in_box.render.hud;

import cn.xzhao.search_in_box.Config;
import cn.xzhao.search_in_box.SIB_MOD;
import cn.xzhao.search_in_box.client.ScreenRender;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.joml.Matrix4f;
import org.joml.Vector4f;

@OnlyIn(Dist.CLIENT)
public class ParticleHUD{
    private int dieTime;
    private final Vector4f tempPos=new Vector4f();
    private final Vector4f screenPos;
    private final ClientLevel level=Minecraft.getInstance().level;
    private final ResourceLocation texture=new ResourceLocation(SIB_MOD.MODID, "textures/particle/generic_sprite_sheet.png");
    public ParticleHUD(BlockPos blockPos) {
        screenPos =new Vector4f(
                (float) blockPos.getX()+0.5f,
                (float) blockPos.getY()+0.5f,
                (float) blockPos.getZ()+0.5f,
                1.0f
        );
        dieTime=ScreenRender.tickTime+Config.PARTICLE_LIVE_TIME.get();
    }
    private static int num=8;
    private static int times=0;
    private static int index=0;
    private static int signalTime=30;
    private static int signalSize=8;
    public void render(int windowWidth,int windowHeight, GuiGraphics guiGraphics, Matrix4f mat) {
        times++;
        if(times==signalTime){
            times=0;
            index++;
            if(index==num){
                index=0;
            }
        }
        int minSize = 8;
        float scale = minSize;
        // 将世界坐标转换为屏幕坐标
        screenPos.mul(mat,tempPos);
        boolean validY=false;
        float x;
        float y = (tempPos.y / tempPos.w + 1) / 2 * windowHeight;
        if(tempPos.z>0){
            x = (1- tempPos.x / tempPos.w) / 2 * windowWidth;
            windowWidth-=minSize;
            windowHeight-=minSize;
            if(y>0&&y<windowHeight){
                y=windowHeight;
            } else if (y<0) {
                y=windowHeight;
            }else {
                y=0;
            }
        }
        else{
            x = (tempPos.x / tempPos.w + 1) / 2 * windowWidth;
            windowWidth-=minSize;
            windowHeight-=minSize;
            if (y > windowHeight) {
                y = windowHeight;
            } else if (y < 0) {
                y = 0;
            }else{
                validY=true;
            }
        }
        if (x > windowWidth) {
            x = windowWidth;
        }
        else if (x < 0) {
            x = 0;
        }else if(validY&&tempPos.z<0){
            scale*=(tempPos.z/tempPos.w-1)*150;
            scale=Math.max(scale,minSize);
            scale=Math.min(scale,150);
            x-=scale/2;
            y-=scale/2;
        }
        Minecraft.getInstance().getTextureManager().bindForSetup(texture);
        guiGraphics.blit(texture, (int) x, (int) y,(int)scale,(int)scale, index*signalSize, 0,8,8,64, 8);
    }
    public void resetDieTime(){
        dieTime=ScreenRender.tickTime+Config.PARTICLE_LIVE_TIME.get();
    }
    public boolean isDead() {
        return dieTime <= ScreenRender.tickTime||Minecraft.getInstance().level!=level;
    }
}
