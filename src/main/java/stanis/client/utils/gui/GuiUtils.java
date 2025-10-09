package stanis.client.utils.gui;

import net.minecraft.client.gui.ScaledResolution;
import org.lwjgl.input.Mouse;
import stanis.client.utils.access.Access;
import stanis.client.utils.render.Rect;

public class GuiUtils implements Access {

    public static boolean isHovered(int mouseX, int mouseY, float x, float y, float width, float height) {
        return mouseX > x && mouseX < x + width && mouseY > y && mouseY < y + height;
    }

    public static boolean isHoveredRect(int mouseX, int mouseY, Rect rect) {
        return mouseX > rect.x() && mouseX < rect.x() + rect.width() && mouseY > rect.y() && mouseY < rect.y() + rect.height();
    }

    public static boolean isMouseHoveredRect(Rect rect) {
        final ScaledResolution sc = new ScaledResolution(mc);
        int i1 = sc.getScaledWidth();
        int j1 = sc.getScaledHeight();

        final int mouseX = Mouse.getX() * i1 / mc.displayWidth;
        final int mouseY = j1 - Mouse.getY() * j1 / mc.displayHeight - 1;

        return mouseX > rect.x() && mouseX < rect.x() + rect.width() && mouseY > rect.y() && mouseY < rect.y() + rect.height();
    }

    public static boolean isMouseHovered(float x, float y, float width, float height) {
        final ScaledResolution sc = new ScaledResolution(mc);
        int i1 = sc.getScaledWidth();
        int j1 = sc.getScaledHeight();

        final int mouseX = Mouse.getX() * i1 / mc.displayWidth;
        final int mouseY = j1 - Mouse.getY() * j1 / mc.displayHeight - 1;

        return mouseX > x && mouseX < x + width && mouseY > y && mouseY < y + height;
    }
}
