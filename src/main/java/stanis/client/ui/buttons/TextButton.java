package stanis.client.ui.buttons;

import net.minecraft.client.gui.GuiTextField;
import stanis.client.utils.animation.Easing;
import stanis.client.utils.animation.EasingAnimation;
import stanis.client.utils.color.ColorUtils;
import stanis.client.utils.gui.GuiUtils;
import stanis.client.utils.render.Rect;
import stanis.client.utils.render.font.ClientFontRenderer;
import stanis.client.utils.render.font.Fonts;
import stanis.client.utils.render.shader.impl.RoundedUtils;
import java.awt.*;

public class TextButton extends GuiTextField {

    float x, y, width, height;
    float prevX, prevY, prevWidth, prevHeight;

    boolean forward;

    EasingAnimation hoverAnim = new EasingAnimation();
    EasingAnimation keyTypedAnim = new EasingAnimation();

    public TextButton(int id, float x, float y, float width, float height) {
        super(id, null, (int) x, (int) y, (int) width, (int) height);
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
    public void drawTextBox() {
        boolean hovered = GuiUtils.isMouseHovered(x, y, width, height) || !"".equals(getText());

        ClientFontRenderer fontRenderer = Fonts.fonts.get("JetBrains");

        hoverAnim.update(0.7f, Easing.OUT_ELASTIC);
        hoverAnim.setEnd(hovered ? 1 : 0);

        keyTypedAnim.update(6, Easing.OUT_BACK);

        if (keyTypedAnim.getValue() == 1 && forward) {
            keyTypedAnim.setEnd(0);
            forward = false;
        }

        Rect rect = Rect.widthHeight(
            prevX - hoverAnim.getValue() * 1 - keyTypedAnim.getValue() * 0.5f,
            prevY - hoverAnim.getValue() * 1 - keyTypedAnim.getValue() * 0.5f,
            prevWidth + hoverAnim.getValue() * 2 + keyTypedAnim.getValue() * 1,
            prevHeight + hoverAnim.getValue() * 2 + keyTypedAnim.getValue() * 1
        );

        float textX = rect.x() + rect.width() / 2f;
        float textY = rect.y() + 2 + (rect.height() - 8) / 2f;

        Rect cursorRect = Rect.widthHeight(
             (float) (x + width / 2f + fontRenderer.getStringWidth(getText()) / 2f + 1),
            textY - 2 - 4.5f * hoverAnim.getValue() + 4.5f - keyTypedAnim.getValue() * 2 / 2,
            0.5f,
            9 * hoverAnim.getValue() + keyTypedAnim.getValue() * 2
        );

        Color rectColor = ColorUtils.interpolateColor(new Color(0,0,0,0.5f), new Color(0,0,0,0.8f), hoverAnim.getValue());
        Color cursorColor = ColorUtils.interpolateColor(new Color(0,0,0,0), Color.WHITE, hoverAnim.getValue());

        RoundedUtils.drawRect(rect, height / 2f, rectColor);
        RoundedUtils.drawRect(cursorRect, 0.5f * hoverAnim.getValue(), cursorColor);
        fontRenderer.drawCenteredString(getText(), textX, textY, Color.WHITE);
    }

    @Override
    public boolean textboxKeyTyped(char p_146201_1_, int p_146201_2_) {
        keyTypedAnim.setEnd(1);
        forward = true;
        return super.textboxKeyTyped(p_146201_1_, p_146201_2_);
    }
}
