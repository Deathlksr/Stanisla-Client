package stanis.client;

import lombok.experimental.UtilityClass;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.Display;
import stanis.client.func.Funcs;
import stanis.client.utils.file.FileUtils;
import stanis.client.utils.icon.IconUtils;
import stanis.client.utils.render.font.Fonts;
import stanis.client.utils.render.shader.Shaders;

import java.io.File;

@UtilityClass
public class Client {

    public final String NAME = "Stanislas";
    public final String VERSION = "1.0";

    private final String RESOURCES_ID = "minecraft";
    private final String RESOURCES_CLIENT_ID = "stanis/";

    private final ResourceLocation ICON16X16 = of("logo/logo16x16.png");
    private final ResourceLocation ICON32X32 = of("logo/logo32x32.png");

    public final File CLIENT_DIRECTORY = new File(NAME);

    public void start() {
        System.out.println(NAME + " " + VERSION);
        setDisplayInfo();

        FileUtils.createDirectoriesIfNotExists(CLIENT_DIRECTORY);

        Shaders.init();

        Fonts.initFonts();

        Funcs.init();
    }

    public void close() {

    }

    private void setDisplayInfo() {
        Display.setTitle(getFullName());
        IconUtils.setWindowIcon(ICON16X16, ICON32X32);
    }

    public ResourceLocation of(String path) {
        return new ResourceLocation(RESOURCES_ID, RESOURCES_CLIENT_ID + path);
    }

    public String getFullName() {
        return NAME + " " + VERSION;
    }
}
