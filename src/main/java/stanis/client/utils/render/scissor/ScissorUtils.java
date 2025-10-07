package stanis.client.utils.render.scissor;

import lombok.Getter;
import lombok.Setter;
import net.minecraft.client.gui.ScaledResolution;
import stanis.client.utils.access.Access;

import java.awt.*;

import static org.lwjgl.opengl.GL11.*;

@Getter
@Setter
public class ScissorUtils implements Access {

    private static boolean debugScissor;

    public static void enableScissor() {
        glEnable(GL_SCISSOR_TEST);
    }

    public static void disableScissor() {
        glDisable(GL_SCISSOR_TEST);
    }

    public static void scissor(ScaledResolution scaledResolution, double x, double y, double width, double height) {
        if (scaledResolution == null || width <= 0 || height <= 0) {
            return;
        }

        final float scaleFactor = scaledResolution.getScaleFactor();
        final int scaledWidth = scaledResolution.getScaledWidth();
        final int scaledHeight = scaledResolution.getScaledHeight();

        int scissorX = (int) Math.round(x * scaleFactor);
        int scissorY = (int) Math.round((scaledHeight - (y + height)) * scaleFactor);
        int scissorWidth = (int) Math.round(width * scaleFactor);
        int scissorHeight = (int) Math.round(height * scaleFactor);

        if (scissorX < 0) {
            scissorWidth += scissorX;
            scissorX = 0;
        }

        if (scissorY < 0) {
            scissorHeight += scissorY;
            scissorY = 0;
        }

        if (scissorX + scissorWidth > scaledWidth * scaleFactor) {
            scissorWidth = (int) (scaledWidth * scaleFactor - scissorX);
        }

        if (scissorY + scissorHeight > scaledHeight * scaleFactor) {
            scissorHeight = (int) (scaledHeight * scaleFactor - scissorY);
        }

        if (scissorWidth > 0 && scissorHeight > 0) {
            glScissor(scissorX, scissorY, scissorWidth, scissorHeight);
        } else {
            glScissor(0, 0, 0, 0);
        }

//        if (debugScissor) RoundedUtils.drawRect((float) x, (float) y, (float) width, (float) height,0, new Color(1f,1f,1f,0.3f));
    }
}
