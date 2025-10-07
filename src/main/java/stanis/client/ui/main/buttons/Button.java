package stanis.client.ui.main.buttons;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import stanis.client.utils.gui.GuiUtils;
import stanis.client.utils.render.font.ClientFontRenderer;
import stanis.client.utils.render.font.Fonts;
import stanis.client.utils.render.shader.impl.RoundedUtils;

import java.awt.*;

public class Button extends GuiButton {

    String name;

    float x, y, width, height;

    public Button(int id, String name, float x, float y, float width, float height) {
        super(id, (int) x, (int) y, name);
        this.name = name;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    @Override
    public void drawButton(Minecraft mc, int mouseX, int mouseY) {
        boolean hovered = GuiUtils.isHovered(mouseX, mouseY, x, y, width, height);

        Color hoveredColor = !hovered ? new Color(0,0,0,0.5f) : new Color(0.1f,0.1f,0.1f,0.7f);

        ClientFontRenderer fontRenderer = Fonts.fonts.get("SFPro");

        RoundedUtils.drawRect(x, y, width, height, height / 2f, hoveredColor);

        fontRenderer.drawCenteredString(name, x + width / 2f, y + 2 + (height - 8) / 2f, hovered ? Color.WHITE.darker() : Color.WHITE);
    }
}
