package stanis.client.utils.sound;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import java.io.File;

public record SoundPlayer(File file) {
    /**
     * Async play.
     *
     * @param volume the volume
     */
    public void asyncPlay(float volume) {
        Thread thread = new Thread(() -> playSound(volume));
        thread.start();
    }

    /**
     * Play sound.
     *
     * @param volume the volume
     */
    public void playSound(float volume) {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);

            FloatControl controller = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            float range = controller.getMaximum() - controller.getMinimum();
            float value = (range * volume) + controller.getMinimum();

            controller.setValue(value);

            clip.start();
        } catch (Exception ex) {
            System.out.println("Error with playing sound.");
        }
    }
}