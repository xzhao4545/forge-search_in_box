package cn.xzhao.search_in_box.render.particle;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.opengl.GL11;

public class TopRenderParticle extends TextureSheetParticle {
    public static ParticleRenderType TOP_RENDER=new ParticleRenderType() {
        @Override
        public void begin(BufferBuilder bufferBuilder, TextureManager textureManager) {
            RenderSystem.depthFunc(GL11.GL_ALWAYS);  // 设置深度测试为总是通过
            RenderSystem.disableBlend();
            RenderSystem.setShader(GameRenderer::getParticleShader);
            RenderSystem.setShaderTexture(0, TextureAtlas.LOCATION_PARTICLES);
            bufferBuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.PARTICLE);
        }

        @Override
        public void end(Tesselator tesselator) {
            tesselator.end();  // 结束渲染
            RenderSystem.enableBlend();
            RenderSystem.defaultBlendFunc();
            RenderSystem.depthFunc(GL11.GL_LEQUAL);  // 恢复深度测试为默认值
        }
        @Override
        public String toString() {
            return "TOP_PARTICLE_RENDER_TYPE";
        }
    };
    protected SpriteSet sprites;
    protected TopRenderParticle(ClientLevel p_108323_, double p_108324_, double p_108325_, double p_108326_, SpriteSet p_105525_) {
        super(p_108323_, p_108324_, p_108325_, p_108326_);
        this.lifetime = 200;   //存活时间
        this.xd = 0;
        this.yd = 0;
        this.zd = 0;
        this.sprites=p_105525_;
        this.setSpriteFromAge(p_105525_);
        this.quadSize = 0.4f;  // 粒子大小
        this.hasPhysics = false; // 粒子是否可以被碰撞
    }

    @Override
    public @NotNull ParticleRenderType getRenderType() {
        return TOP_RENDER;
    }
    protected int cycleTick=40;
    @Override
    public void tick() {
        super.tick();
        this.setSprite(this.sprites.get(this.age%cycleTick,cycleTick));
    }

    @OnlyIn(Dist.CLIENT)
    public static class Provider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet sprites;

        public Provider(SpriteSet p_105525_) {
            this.sprites = p_105525_;
        }

        public Particle createParticle(@NotNull SimpleParticleType p_105536_, @NotNull ClientLevel p_105537_, double p_105538_, double p_105539_, double p_105540_, double p_105541_, double p_105542_, double p_105543_) {
            return new TopRenderParticle(p_105537_, p_105538_+0.5, p_105539_+0.5, p_105540_+0.5,this.sprites);
        }
    }
}
