package stanis.client.ui.main;

import net.minecraft.client.gui.*;
import stanis.client.ui.main.buttons.Button;
import stanis.client.utils.render.shader.impl.BackgroundUtils;

import java.io.IOException;

public class MainMenuScreen extends GuiScreen {

    @Override
    public void initGui() {
        super.initGui();
        ScaledResolution sc = new ScaledResolution(mc);
        buttonList.add(new Button(0,"Single Player", sc.getScaledWidth() / 2f - 75,sc.getScaledHeight() / 2f - 10, 150, 20));
        buttonList.add(new Button(1,"Multi Player", sc.getScaledWidth() / 2f - 75,sc.getScaledHeight() / 2f - 10 + 20, 150, 20));
        buttonList.add(new Button(2,"Minecraft Settings", sc.getScaledWidth() / 2f - 75,sc.getScaledHeight() / 2f - 10 + 20, 150, 20));
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        BackgroundUtils.run();

        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        super.actionPerformed(button);

        switch (button.id) {
            case 0 -> mc.displayGuiScreen(new GuiSelectWorld(this));
            case 1 -> mc.displayGuiScreen(new GuiMultiplayer(this));
            case 2 -> mc.displayGuiScreen(new GuiOptions(this, mc.gameSettings));
//            case 3 -> mc.displayGuiScreen(Client.INST.getAltManagerGui());
            case 5 -> mc.shutdownMinecraftApplet();
        }
    }

    @Override
    public void onGuiClosed() {
        super.onGuiClosed();
    }


}
