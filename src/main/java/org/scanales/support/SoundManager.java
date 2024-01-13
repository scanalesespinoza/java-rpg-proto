	package org.scanales.support;

	import javax.sound.sampled.*;
	import java.io.File;
	import java.io.IOException;
	import java.util.Random;

	public class SoundManager {
	    private Clip clip;

	    public void playRandomSound(String[] soundFiles) {
	        if (soundFiles.length == 0) {
	            return;
	        }

	        try {
	            Random rand = new Random();
	            int randomIndex = rand.nextInt(soundFiles.length);
	            String randomSoundFile = soundFiles[randomIndex];

	            File soundFile = new File(randomSoundFile);
	            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(soundFile);

	            clip = AudioSystem.getClip();
	            clip.open(audioInputStream);
	            clip.start();
	        } catch (UnsupportedAudioFileException | LineUnavailableException | IOException e) {
	            e.printStackTrace();
	        }
	    }

	    public void stopSound() {
	        if (clip != null && clip.isRunning()) {
	            clip.stop();
	            clip.close();
	        }
	    }

	}
