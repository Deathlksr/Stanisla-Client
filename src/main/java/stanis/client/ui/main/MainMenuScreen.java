package stanis.client.ui.main;

import net.minecraft.client.gui.*;
import net.minecraft.util.ResourceLocation;
import stanis.client.Client;
import stanis.client.ui.alt.AltManagerScreen;
import stanis.client.ui.buttons.Button;
import stanis.client.ui.buttons.ImageButton;
import stanis.client.utils.render.shader.impl.BackgroundUtils;
import java.io.IOException;

public class MainMenuScreen extends GuiScreen {

    ResourceLocation exitLogo = Client.of("textures/logo/exit.png");

    @Override
    public void initGui() {
        super.initGui();
        ScaledResolution sc = new ScaledResolution(mc);
        buttonList.add(new Button(0,"Single Player", sc.getScaledWidth() / 2f - 75,sc.getScaledHeight() / 2f - 10, 150, 20));
        buttonList.add(new Button(1,"Multi Player", sc.getScaledWidth() / 2f - 75,sc.getScaledHeight() / 2f - 10 + 25, 150, 20));
        buttonList.add(new Button(2,"Minecraft Settings", sc.getScaledWidth() / 2f - 75,sc.getScaledHeight() / 2f - 10 + 25 + 25, 150, 20));
        buttonList.add(new Button(4,"Alt Manager", sc.getScaledWidth() / 2f - 75,sc.getScaledHeight() / 2f - 10 + 25 + 25 + 25, 150, 20));
        buttonList.add(new ImageButton(3, exitLogo, sc.getScaledWidth() - 20,5, 15, 15));
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        BackgroundUtils.run();

//        RenderUtils.drawImage();

        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        super.actionPerformed(button);

        switch (button.id) {
            case 0 -> mc.displayGuiScreen(new GuiSelectWorld(this));
            case 1 -> mc.displayGuiScreen(new GuiMultiplayer(this));
            case 2 -> mc.displayGuiScreen(new GuiOptions(this, mc.gameSettings));
            case 4 -> mc.displayGuiScreen(new AltManagerScreen());
            case 3 -> mc.shutdownMinecraftApplet();
        }
    }

    @Override
    public void onGuiClosed() {
        super.onGuiClosed();
    }


}
