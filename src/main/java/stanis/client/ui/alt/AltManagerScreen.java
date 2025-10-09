package stanis.client.ui.alt;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import stanis.client.alt.Account;
import stanis.client.ui.buttons.Button;
import stanis.client.ui.buttons.TextButton;
import stanis.client.utils.animation.Easing;
import stanis.client.utils.animation.EasingAnimation;
import stanis.client.utils.color.ColorUtils;
import stanis.client.utils.gui.GuiUtils;
import stanis.client.utils.render.Rect;
import stanis.client.utils.render.shader.impl.BackgroundUtils;
import stanis.client.utils.render.shader.impl.RoundedUtils;
import java.awt.*;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class AltManagerScreen extends GuiScreen {

    TextButton textButton;

    @Override
    public void initGui() {
        super.initGui();
        ScaledResolution sc = new ScaledResolution(mc);
        textButton = new TextButton(0, 50, sc.getScaledHeight() - 10 - 75, 100, 20);
        buttonList.add(new Button(1,"Login", 50, sc.getScaledHeight() - 10 - 50, 100, 20));
        buttonList.add(new Button(2,"Microsoft", 50, sc.getScaledHeight() - 10 - 25, 100, 20));

        textButton.setFocused(true);
        textButton.setMaxStringLength(16);
    }

    EasingAnimation hoverAnim = new EasingAnimation();

    List<Account> accounts = new CopyOnWriteArrayList<>();

    Account selectedAccount;

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        BackgroundUtils.run();

        ScaledResolution sc = new ScaledResolution(mc);

        Rect rect = Rect.widthHeight(
            (sc.getScaledWidth() - 200 - 10),
            10,
            200,
            (sc.getScaledHeight() - 20)
        );

        boolean hoveredRect = GuiUtils.isHovered(mouseX, mouseY, rect.x(), rect.y(), rect.width(), rect.height());

        hoverAnim.update(0.7f, Easing.OUT_ELASTIC);
        hoverAnim.setEnd(hoveredRect ? 1 : 0);

        Color rectColor = ColorUtils.interpolateColor(new Color(0,0,0,0.5f), new Color(0,0,0,0.6f), hoverAnim.getValue());

        RoundedUtils.drawRect(rect, 10, rectColor);

        Rect buttonsRect = Rect.widthHeight(
            45,
            sc.getScaledHeight() - 5 - 75 - 10,
            110,
            80
        );

        Color buttonsColor = new Color(0,0,0,0.5f);

        RoundedUtils.drawRect(buttonsRect, 15, buttonsColor);

        super.drawScreen(mouseX, mouseY, partialTicks);

        textButton.drawTextBox();
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        super.keyTyped(typedChar, keyCode);
        textButton.textboxKeyTyped(typedChar, keyCode);
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);



    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        super.actionPerformed(button);
        switch (button.id) {
            case 0 -> {

            }
        }
    }
}
