package stanis.client.utils.render.shader.impl;

import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.opengl.GL11;
import stanis.client.utils.access.Access;
import stanis.client.utils.render.shader.Shader;
import stanis.client.utils.render.shader.Shaders;

import java.awt.*;

public class RoundedUtils implements Access {

    private static void draw(final float x, final float y, final float width, final float height, final float radius1, final float radius2, final float radius3, float radius4, Color color) {
        Shader program = Shaders.getRounded();
        program.start();

        program.uniform2f("u_size", width, height);
        program.uniform4f("u_radius", radius1, radius2, radius3, radius4);
        program.uniform1f("u_smooth", 1f);
        program.uniform4f("u_color", color.getRed() / 255.0F, color.getGreen() / 255.0F, color.getBlue() / 255.0F, color.getAlpha() / 255.0F);
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GlStateManager.enableAlpha();
        GlStateManager.alphaFunc(GL11.GL_GREATER, 0.0F);
        program.renderShader(x, y, width, height);
        GlStateManager.disableBlend();

        Shader.stop();
    }

    public static void drawRect(final float x, final float y, final float width, final float height, final float radius, final Color color) {
        draw(x - 1f, y - 1f, width + 2, height + 2, radius, radius, radius, radius, color);
    }

    public static void drawRect(final float x, final float y, final float width, final float height, final float radius1, final float radius2, final float radius3, final float radius4, final Color color) {
        draw(x - 1f, y - 1f, width + 2, height + 2, radius1, radius2, radius3, radius4, color);
    }

    public static void drawCenteredRect(final float x, final float y, final float width, final float height, final float radius, final Color color) {
        draw(x - 1f - width / 2f, y - 1f, width + 2, height + 2, radius, radius, radius, radius, color);
    }

}
