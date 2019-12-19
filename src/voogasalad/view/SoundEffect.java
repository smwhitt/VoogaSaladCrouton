package voogasalad.view; /**
 * @Author Angel Huizar
 * purpose-The purpose of this class is to create an object that contains the audio file I wish to play and have
 * that object readily available to play its sound.
 */
import javax.sound.sampled.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.function.Consumer;

public class SoundEffect {
    private  Clip mySystem;
    /**
     * This constructor get the audio file from fileName
     * @param fileName the name of the audio file you wish to play
     */
    public SoundEffect(String fileName){
        setFile(fileName);
    }

    private void setFile(String fName){
        try {
            AudioInputStream stream = AudioSystem.getAudioInputStream(new File(fName));
            mySystem = AudioSystem.getClip();
            mySystem.open(stream);
        }
        catch (Exception e){
            mySystem = null;
        }
    }

    /**
     * This method plays the audio from the specified audio file passed in the constructor
     */
    public void play(){
        if(mySystem!=null) {
            mySystem.setFramePosition(0);
            mySystem.start();
        }
    }

    /**
     * This method plays the audio from the specified file in a continuous loop
     */
    public void startLoop(){
        if(mySystem!=null) {
            mySystem.loop(Clip.LOOP_CONTINUOUSLY);
        }
    }

    /**
     * This method ends any loop that may be playing
     */
    public void endLoop(){
        if(mySystem!=null) {
            mySystem.stop();
        }
    }
}
