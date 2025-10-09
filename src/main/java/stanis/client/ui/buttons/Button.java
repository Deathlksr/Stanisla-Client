package stanis.client.ui.buttons;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import stanis.client.utils.animation.Easing;
import stanis.client.utils.animation.EasingAnimation;
import stanis.client.utils.color.ColorUtils;
import stanis.client.utils.gui.GuiUtils;
import stanis.client.utils.render.Rect;
import stanis.client.utils.render.font.ClientFontRenderer;
import stanis.client.utils.render.font.Fonts;
import stanis.client.utils.render.shader.impl.RoundedUtils;

import java.awt.*;

public class Button extends GuiButton {

    String name;

    float x, y, width, height;
    float prevX, prevY, prevWidth, prevHeight;

    EasingAnimation hoverAnim = new EasingAnimation();

    public Button(int id, String name, float x, float y, float width, float height) {
        super(id, (int) x, (int) y, name);
        this.name = name;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.prevX = x;
        this.prevY = y;
        this.prevWidth = width;
        this.prevHeight = height;
    }

    @Override
    public void drawButton(Minecraft mc, int mouseX, int mouseY) {
        boolean hovered = GuiUtils.isHovered(mouseX, mouseY, x, y, width, height);

        ClientFontRenderer fontRenderer = Fonts.fonts.get("JetBrains");

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

        float textX = rect.x() + rect.width() / 2f;
        float textY = rect.y() + 2 + (rect.height() - 8) / 2f;

        fontRenderer.drawCenteredString(name, textX, textY, Color.WHITE);
    }
}
