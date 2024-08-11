package cn.xzhao.search_in_box.client;

import cn.xzhao.search_in_box.SIB_MOD;
import cn.xzhao.search_in_box.render.hud.ParticleHUD;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.core.BlockPos;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RenderGuiEvent;
import net.minecraftforge.client.event.ViewportEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.joml.Matrix4f;
import org.joml.Vector3f;

import java.util.Hashtable;
import java.util.Iterator;

@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber(modid = SIB_MOD.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class ScreenRender {
    private static final Hashtable<BlockPos,ParticleHUD> particleHUDSet =new Hashtable<>();
    public static void addParticleHUD(BlockPos blockPos){
        ParticleHUD hud= particleHUDSet.get(blockPos);
        if(hud==null)
            particleHUDSet.put(blockPos,new ParticleHUD(blockPos));
        else
            hud.resetDieTime();
    }
    @SubscribeEvent
    public static void onRenderHUD(RenderGuiEvent.Pre event) {
        if(particleHUDSet.isEmpty())   return;
        Iterator<ParticleHUD> iter = particleHUDSet.values().iterator();
        getProjectionMatrix().mul(getViewMatrix(), tviewMatrix);
        while (iter.hasNext()) {
            ParticleHUD particleHUD = iter.next();
            particleHUD.render(event.getWindow().getGuiScaledWidth(),
                    event.getWindow().getGuiScaledHeight(),
                    event.getGuiGraphics(),
                    tviewMatrix
            );
            if (particleHUD.isDead()) {
                iter.remove();
            }
        }
    }
    private static GameRenderer gameRenderer;
    private static GameRenderer getGameRenderer(){
        if(gameRenderer==null){
            gameRenderer=Minecraft.getInstance().gameRenderer;
        }
        return gameRenderer;
    }
    private static Camera mainCamera;
    private static Camera getMainCamera(){
        if(mainCamera==null){
            mainCamera=getGameRenderer().getMainCamera();
        }
        return mainCamera;
    }
    private static final Vector3f center =new Vector3f();
    private static final Matrix4f tviewMatrix=new Matrix4f();
    private static double fov=90;
    @SubscribeEvent
    public static void getFOV(ViewportEvent.ComputeFov event){
        fov=event.getFOV();
    }
    private static Matrix4f getProjectionMatrix(){
        return getGameRenderer().getProjectionMatrix(fov);
    }
    public static Matrix4f getViewMatrix(){
        Camera camera=getMainCamera();
        Vector3f eyePos = camera.getPosition().toVector3f();
        eyePos.sub(camera.getLookVector(), center);
        return tviewMatrix.setLookAt(eyePos, center, camera.getUpVector());
    }
    public static int tickTime=0;
    @SubscribeEvent
    public static void onTick(TickEvent.ClientTickEvent event){
        tickTime++;
    }
}
