package stanis.client.utils.render.shader.impl;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.shader.Framebuffer;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import stanis.client.utils.access.Access;
import stanis.client.utils.render.shader.Shader;
import stanis.client.utils.render.shader.Shaders;

public class BackgroundUtils implements Access {

    private static Framebuffer tempFBO = new Framebuffer(mc.displayWidth, mc.displayHeight, true);

    public static void run() {
        if (!Display.isVisible() || !Display.isActive()) return;
        Shader program = Shaders.getBackground();

        update();

        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GlStateManager.disableAlpha();

        mc.getFramebuffer().bindFramebuffer(true);
        program.start();
        program.uniform2f("resolution", mc.displayWidth, mc.displayHeight);
        program.uniform1f("time", (System.currentTimeMillis() - mc.getStartTime()) / 1000F);
        Shader.drawQuad();
        Shader.stop();
    }

    public static void update() {
        if (mc.displayWidth != tempFBO.framebufferWidth || mc.displayHeight != tempFBO.framebufferHeight) {
            tempFBO.deleteFramebuffer();
            tempFBO = new Framebuffer(mc.displayWidth, mc.displayHeight, true);
        } else {
            tempFBO.framebufferClear();
        }
    }
}

