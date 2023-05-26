package com.codecool.dungeoncrawl.logic.util;

import com.codecool.dungeoncrawl.Main;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class SoundUtils {

    public static final String EAT = "/sounds/eat.wav";
    public static final String HIT = "/sounds/punch1.wav";
    public static final String SWORD_HIT = "/sounds/sword_hit.wav";
    public static final String GAME_OVER = "/sounds/game_over.wav";
    public static final String BACKGROUND = "/sounds/background.wav";
    public static final String MENU_BACKGROUND = "/sounds/Glory-Eternal.wav";
    public static final String STEP = "/sounds/step.wav";
    public static final String OPEN_DOOR = "/sounds/door-1-open.wav";
    public static final String TELEPORT = "/sounds/teleport.wav";
    public static final String SUMMON = "/sounds/darkness.wav";
    public static final String GAME_WON = "/sounds/mixkit-medieval-show-fanfare-announcement-226.wav";
    private static final List<Clip> playingClips = new ArrayList<>();

    private SoundUtils() {
    }

    public static Clip playSound(String fileName, float volume) {
        try {
            Clip clip = AudioSystem.getClip();
            URL url = Main.class.getResource(fileName);
            AudioInputStream inputStream = AudioSystem.getAudioInputStream(url);
            clip.open(inputStream);
            setVolume(volume, clip);
            clip.start();
            return clip;
        } catch (Exception e) {
            System.err.println(e.getMessage());
            System.out.println(e);
        }
        return null;
    }

    public static void playContinuously(String fileName, float volume) {
        Clip clip = playSound(fileName, volume);
        playingClips.add(clip);
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public static void stopAll() {
        for (int i = playingClips.size() - 1; i >= 0; --i) {
            playingClips.get(i).close();
            playingClips.remove(i);
        }
    }

    public static void setVolume(float volume, Clip clip) {
        if (volume < 0f || volume > 1f)
            throw new IllegalArgumentException("Volume not valid: " + volume);
        FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        gainControl.setValue(20f * (float) Math.log10(volume));
    }

}
