package stanis.client.utils.render;

import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import stanis.client.utils.access.Access;
import stanis.client.utils.color.ColorUtils;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.GL_ZERO;

public class RenderUtils implements Access {

    public static void drawImage(ResourceLocation image, int x, int y, int width, int height) {
        drawImage(image, x,y,width,height,false);
    }

    public static void drawImage(ResourceLocation image, float x, float y, float width, float height, boolean resetColor) {
        GlStateManager.disableDepth();
        GlStateManager.enableBlend();
        GlStateManager.depthMask(false);
        GlStateManager.tryBlendFuncSeparate(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA, GL_ONE, GL_ZERO);
        if (!resetColor) ColorUtils.resetColor();
        mc.getTextureManager().bindTexture(image);
        Gui.drawScaledCustomSizeModalRect(x, y, 0f, 0f, width, height, width, height, width, height);
        GlStateManager.depthMask(true);
        GlStateManager.disableBlend();
        GlStateManager.enableDepth();
        if (resetColor) ColorUtils.resetColor();
    }

}
