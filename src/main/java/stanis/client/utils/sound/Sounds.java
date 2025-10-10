package stanis.client.utils.sound;

import lombok.Getter;
import lombok.experimental.UtilityClass;
import stanis.client.Client;
import stanis.client.utils.file.FileUtils;

import java.io.File;
import java.io.IOException;

@Getter
@UtilityClass
public class Sounds {

    SoundPlayer enableSound;

    public final File CLIENT_SOUND_DIRECTORY = new File(Client.NAME + "/sounds");

    public void init() throws IOException {
        FileUtils.createDirectoriesIfNotExists(CLIENT_SOUND_DIRECTORY);

        File enableSoundFile = new File(CLIENT_SOUND_DIRECTORY, "enable.wav");

        unpackIfNeeded(enableSoundFile, "assets/minecraft/autotool/sounds/enable.wav");

        enableSound = new SoundPlayer(enableSoundFile);
    }

    private void unpackIfNeeded(File file, String assetPath) throws IOException {
        if (!file.exists()) {
            FileUtils.unpackFile(file, assetPath);
        }
    }
}
