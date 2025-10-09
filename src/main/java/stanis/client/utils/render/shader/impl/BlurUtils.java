package stanis.client.utils.render.shader.impl;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.shader.Framebuffer;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import stanis.client.utils.access.Access;
import stanis.client.utils.render.shader.GaussianKernel;
import stanis.client.utils.render.shader.Shader;
import stanis.client.utils.render.shader.Shaders;
import java.nio.FloatBuffer;

public class BlurUtils implements Access {

    private static Framebuffer inputFramebuffer = new Framebuffer(mc.displayWidth, mc.displayHeight, true);
    private static Framebuffer outputFramebuffer = new Framebuffer(mc.displayWidth, mc.displayHeight, true);
    private static GaussianKernel gaussianKernel = new GaussianKernel(0);

    public static void addToDraw(Runnable run) {
        inputFramebuffer.bindFramebuffer(true);
        run.run();
        mc.getFramebuffer().bindFramebuffer(true);
    }

    public static void draw() {
        if (!Display.isActive() || !Display.isVisible()) return;

        Shader program = Shaders.getBlur();

        inputFramebuffer.bindFramebuffer(true);

        final int radius = 12;

        outputFramebuffer.bindFramebuffer(true);
        program.start();

        if (gaussianKernel.getSize() != radius) {
            gaussianKernel = new GaussianKernel(radius);
            gaussianKernel.compute();

            final FloatBuffer buffer = BufferUtils.createFloatBuffer(radius);
            buffer.put(gaussianKernel.getKernel());
            buffer.flip();

            program.uniform1f("u_radius", radius);
            program.uniformFB("u_kernel", buffer);
            program.uniform1i("u_diffuse_sampler", 0);
            program.uniform1i("u_other_sampler", 20);
        }

        program.uniform2f("u_texel_size", 1.0F / mc.displayWidth, 1.0F / mc.displayHeight);
        program.uniform2f("u_direction", 2, 0);

        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, GL11.GL_ONE, GL11.GL_ZERO);
        GlStateManager.alphaFunc(GL11.GL_GREATER, 0.0F);
        mc.getFramebuffer().bindFramebufferTexture();
        Shader.drawQuad();

        mc.getFramebuffer().bindFramebuffer(true);
        program.uniform2f("u_direction", 0, 2);
        outputFramebuffer.bindFramebufferTexture();
        GL13.glActiveTexture(GL13.GL_TEXTURE20);
        inputFramebuffer.bindFramebufferTexture();
        GL13.glActiveTexture(GL13.GL_TEXTURE0);
        Shader.drawQuad();
        GlStateManager.disableBlend();

        Shader.stop();

        update();
    }

    public static void update() {
        if (mc.displayWidth != inputFramebuffer.framebufferWidth || mc.displayHeight != inputFramebuffer.framebufferHeight) {
            inputFramebuffer.deleteFramebuffer();
            inputFramebuffer = new Framebuffer(mc.displayWidth, mc.displayHeight, true);

            outputFramebuffer.deleteFramebuffer();
            outputFramebuffer = new Framebuffer(mc.displayWidth, mc.displayHeight, true);
        } else {
            inputFramebuffer.framebufferClear();
            outputFramebuffer.framebufferClear();
        }
    }
}
