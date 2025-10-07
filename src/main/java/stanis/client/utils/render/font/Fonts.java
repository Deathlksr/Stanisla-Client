package stanis.client.utils.render.font;

import lombok.experimental.UtilityClass;
import stanis.client.Client;
import stanis.client.utils.access.Access;
import stanis.client.utils.file.FileUtils;

import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.List;

@UtilityClass
public class Fonts implements Access {

    public HashMap<String, ClientFontRenderer> fonts = new HashMap<>();

    File fontsDirectory = new File(Client.CLIENT_DIRECTORY + "/fonts");

    private final String GITHUB_FONTS_REPO = "https://github.com/Deathlksr/Stanisla-Fonts";
    private final String GITHUB_RAW_BASE = "https://raw.githubusercontent.com/Deathlksr/Stanisla-Fonts/refs/heads/main/";

    private final List<String> FONT_FILES = List.of(
        "SFPro.ttf",
        "SFProRegular.ttf",
        "SFProRounded.ttf"
    );

    public void initFonts() {
        FileUtils.createDirectory(fontsDirectory);

        downloadFontsFromGitHub();

        File[] fontFiles = fontsDirectory.listFiles((_, name) ->
            name.toLowerCase().endsWith(".ttf") || name.toLowerCase().endsWith(".otf")
        );

        if (fontFiles == null) {
            throw new IllegalStateException("Fonts directory is inaccessible: " + fontsDirectory.getAbsolutePath());
        }

        if (fontFiles.length == 0) {
            throw new RuntimeException("No font files found in directory: " + fontsDirectory.getAbsolutePath());
        }

        for (File fontFile : fontFiles) {
            String fontName = getFileNameWithoutExtension(fontFile);
            Font font = generateFontFromFile(fontFile, 32, true);

            if (font == null) {
                throw new RuntimeException("Failed to load font from file: " + fontFile.getAbsolutePath());
            }

            fonts.put(fontName, new ClientFontRenderer(font));
        }
    }

    private void downloadFontsFromGitHub() {
        boolean downloadedAny = false;

        for (String fontFileName : FONT_FILES) {
            File fontFile = new File(fontsDirectory, fontFileName);

            if (fontFile.exists()) {
                continue;
            }

            try {
                System.out.println("Downloading font: " + fontFileName);
                URL fontUrl = new URL(GITHUB_RAW_BASE + fontFileName);

                try (InputStream in = fontUrl.openStream()) {
                    Files.copy(in, fontFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                    downloadedAny = true;
                    System.out.println("Successfully downloaded: " + fontFileName);
                }

            } catch (Exception e) {
                System.err.println("Failed to download font " + fontFileName + ": " + e.getMessage());
            }
        }

        if (downloadedAny) {
            System.out.println("Fonts download completed from: " + GITHUB_FONTS_REPO);
        }
    }

    /**
     * Проверяет наличие всех необходимых шрифтов
     */
    public boolean checkFontsAvailability() {
        for (String fontFileName : FONT_FILES) {
            File fontFile = new File(fontsDirectory, fontFileName);
            if (!fontFile.exists()) {
                return false;
            }
        }
        return true;
    }

    private String getFileNameWithoutExtension(File file) {
        String name = file.getName();
        int lastIndex = name.lastIndexOf('.');
        if (lastIndex > 0) return name.substring(0, lastIndex);
        return name;
    }

    public Font generateFontFromFile(File fontFile, float size, boolean bold) {
        try {
            InputStream inputStream = new FileInputStream(fontFile);
            Font font = Font.createFont(Font.TRUETYPE_FONT, inputStream);
            return font.deriveFont(bold ? Font.BOLD : Font.PLAIN, size);
        } catch (Exception e) {
            System.out.println("Ошибка создания шрифта из файла " + fontFile.getName() + ": " + e.getMessage());
        }
        return null;
    }
}