package bl;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.IOException;
import javax.swing.JOptionPane;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.AudioDevice;
import javazoom.jl.player.FactoryRegistry;
import javazoom.jl.player.advanced.AdvancedPlayer;

public class AudioPlayer extends Thread {

    private AdvancedPlayer player;
    private Thread playerThread;

    public AudioPlayer() {
        player = null;
    }

    public void starteAbspielen(final File file) {
        try {
            playerVorbereiten(file);
            playerThread = new Thread() {
                public void run() {
                    try {
                        player.play(5000);
                    } catch (JavaLayerException e) {
                        meldeProblem();
                    } finally {
                        killPlayer();
                    }
                }
            };
            playerThread.start();
        } catch (Exception ex) {
            meldeProblem();
        }
    }

    public void stopMusic() {
        killPlayer();
    }

    private void playerVorbereiten(File file) {
        try {
            InputStream is = gibEingabestream(file);
            player = new AdvancedPlayer(is, erzeugeAudiogeraet());
        } catch (IOException e) {
            meldeProblem();
            killPlayer();
        } catch (JavaLayerException e) {
            meldeProblem();
            killPlayer();
        }
    }

    private InputStream gibEingabestream(File file)
            throws IOException {
        return new BufferedInputStream(
                new FileInputStream(file));
    }

    private AudioDevice erzeugeAudiogeraet()
            throws JavaLayerException {
        return FactoryRegistry.systemRegistry().createAudioDevice();
    }

    private void killPlayer() {
        synchronized (this) {
            if (player != null) {
                player.stop();
                player = null;
            }
        }
    }

    private void meldeProblem() {
        JOptionPane.showMessageDialog(null, "Es gab ein Problem beim Abspielen!", "Error!", JOptionPane.ERROR_MESSAGE);
        //System.out.println("Es gab ein Problem beim Abspielen von: " + file.getName());
    }

}
