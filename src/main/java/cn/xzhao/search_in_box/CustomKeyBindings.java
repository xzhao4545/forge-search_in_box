package cn.xzhao.search_in_box;

import net.minecraft.client.KeyMapping;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import org.lwjgl.glfw.GLFW;
@OnlyIn(Dist.CLIENT)
public class CustomKeyBindings {

    // 定义键绑定的分类和名称
    public static final String CATEGORY = String.format("key.categories.%s",SIB_MOD.MODID);
    public static final String SEARCH_KEY = String.format("key.%s.search_in_box",SIB_MOD.MODID);

    // 创建一个新的键绑定实例
    public static final KeyMapping searchKey = new KeyMapping(SEARCH_KEY, GLFW.GLFW_KEY_Y, CATEGORY);

    // 在客户端初始化时注册键绑定
    public static void register(RegisterKeyMappingsEvent event) {
        event.register(searchKey);
    }
}