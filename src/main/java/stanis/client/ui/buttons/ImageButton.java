package stanis.client.ui.buttons;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.ResourceLocation;
import stanis.client.utils.animation.Easing;
import stanis.client.utils.animation.EasingAnimation;
import stanis.client.utils.color.ColorUtils;
import stanis.client.utils.gui.GuiUtils;
import stanis.client.utils.render.Rect;
import stanis.client.utils.render.RenderUtils;
import stanis.client.utils.render.shader.impl.RoundedUtils;
import java.awt.*;

public class ImageButton extends GuiButton {

    ResourceLocation image;

    float x, y, width, height;
    float prevX, prevY, prevWidth, prevHeight;

    EasingAnimation hoverAnim = new EasingAnimation();

    public ImageButton(int id, ResourceLocation image, float x, float y, float width, float height) {
        super(id, (int) x, (int) y, "");
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.prevX = x;
        this.prevY = y;
        this.prevWidth = width;
        this.prevHeight = height;
        this.image = image;
    }

    @Override
    public void drawButton(Minecraft mc, int mouseX, int mouseY) {
        boolean hovered = GuiUtils.isHovered(mouseX, mouseY, x, y, width, height);

        hoverAnim.update(0.7f, Easing.OUT_ELASTIC);
        hoverAnim.setEnd(hovered ? 1 : 0);

        Rect rect = Rect.widthHeight(
            prevX - hoverAnim.getValue() * 1,
            prevY - hoverAnim.getValue() * 1,
            prevWidth + hoverAnim.getValue() * 2,
            prevHeight + hoverAnim.getValue() * 2
        );

        Color rectColor = ColorUtils.interpolateColor(new Color(0,0,0,0.5f), new Color(0,0,0,0.8f), hoverAnim.getValue());

        RoundedUtils.drawRect(rect, height / 2f, rectColor);

        Color imageColor = ColorUtils.interpolateColor(Color.WHITE, Color.RED, hoverAnim.getValue());

        ColorUtils.glColor(imageColor);
        RenderUtils.drawImage(image, rect.x(), rect.y(), rect.width(), rect.height(), true);
    }
}
