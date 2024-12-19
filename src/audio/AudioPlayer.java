package audio;

import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;
import java.util.Random;

public class AudioPlayer {

    public static int MENU_1 = 0;
    public static int LEVEL_1 = 1;
    public static int LEVEL_2 = 2;

    public static int FIRE = 0;
    public static int LEVEL_INTRO = 1;
    public static int TANK_IDLE = 2;
    public static int TANK_MOVE = 3;
    public static int ENEMY_EXPLOSION = 4;
    public static int GAME_OVER = 5;
    public static int HIGH_SCORE = 6;
    public static int HIT_BRICK = 7;
    public static int HIT_ENEMY = 8;
    public static int HIT_STEEL = 9;
    public static int ICE = 10;
    public static int LIFE = 11;
    public static int PAUSE = 12;
    public static int PLAYER_EXPLOSION = 13;
    public static int POWER_UP_APPEAR = 14;
    public static int POWER_UP_PICKUP = 15;
    public static int SCORE = 16;
    public static int SCORE_BONUS = 17;
    public static int UNKNOWN_1 = 18;
    public static int UNKNOWN_2 = 19;
    public static int VICTORY = 20;


    private Clip[] songs, effects;
    private int currentSongId;
    private float volume = 0.7f;
    private boolean songMute, effectMute;
    private Random random = new Random();

    public AudioPlayer() {
        loadEffects();
    }

    private void loadSongs() {
        String[] names = { "menu", "level1", "level2" };
        songs = new Clip[names.length];
        for (int i = 0; i < songs.length; i++) {
            songs[i] = getClip(names[i]);
        }
    }

    private void loadEffects() {
        String[] effectNames = { 
                "fire", 
                "level-intro", 
                "tank-idle", 
                "tank-move",
                "enemy-explosion",
                "game-over",
                "high-score",
                "hit-brick",
                "hit-enemy",
                "hit-steel",
                "ice",
                "life_1",
                "pause_1",
                "player-explosion",
                "powerup-appear",
                "powerup-pickup_1",
                "score",
                "score-bonus",
                "unknown-2",
                "unknown-3",
                "victory_1"
        };
        effects = new Clip[effectNames.length];
        for (int i = 0; i < effects.length; i++) {
            effects[i] = getClip(effectNames[i]);
        }
    }

    /**
     * Get sound clip by name
     * @param name file name w/o path and .wav
     * @return the clip itself
     */
    private Clip getClip(String name) {
        URL url = getClass().getResource("/audio/" + name + ".wav");
        AudioInputStream audio;

        try {
            audio = AudioSystem.getAudioInputStream(url);
            Clip c = AudioSystem.getClip();
            c.open(audio);

            return c;
        } catch (UnsupportedAudioFileException | LineUnavailableException | IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Set volume for both song and sound effects
     */
    public void setVolume(float volume) {
        this.volume = volume;
        updateSongVolume();
        updateEffectsVolume();
    }

    /**
     * Stop the song that is currently playing
     */
    public void stopSong() {
        if (songs[currentSongId].isActive())
            songs[currentSongId].stop();
    }

    /**
     * We have only two level songs for now so we are just rotating them
     */
    public void setLevelSong(int lvlIndex) {
        if (lvlIndex % 2 == 0)
            playSong(LEVEL_1);
        else
            playSong(LEVEL_2);
    }

    /**
     * Play this when level completed
     */
//    public void lvlCompleted() {
//        stopSong();
//        playEffect(LVL_COMPLETED);
//    }

    /**
     * We have three different sounds for attack ("attack1", "attack2", "attack3")
     * So when player attacks we play one of them randomly
     */
    public void playAttackSound() {
        // Index of the first attack effect in the effects array
        int start = 4;
        start += random.nextInt(3);
        playEffect(start);
    }

    public void playEffect(int effect) {
        effects[effect].setMicrosecondPosition(0);      // Play from the begging
        effects[effect].start();
    }

    public void playSong(int song) {
        stopSong();            // Stop current song

        currentSongId = song;
        updateSongVolume();
        songs[currentSongId].setMicrosecondPosition(0);
        songs[currentSongId].loop(Clip.LOOP_CONTINUOUSLY);
    }


    public void toggleSongMute() {
        this.songMute = !songMute;
        for (Clip c : songs) {
            BooleanControl booleanControl = (BooleanControl) c.getControl(BooleanControl.Type.MUTE);
            booleanControl.setValue(songMute);
        }
    }

    public void toggleEffectMute() {
        this.effectMute = !effectMute;
        for (Clip c : effects) {
            BooleanControl booleanControl = (BooleanControl) c.getControl(BooleanControl.Type.MUTE);
            booleanControl.setValue(effectMute);
        }

        // When we turn on effects then we want to let the player know by playing some short effect
//        if (!effectMute)
//            playEffect(JUMP);
    }

    private void updateSongVolume() {
        FloatControl gainControl = (FloatControl) songs[currentSongId].getControl(FloatControl.Type.MASTER_GAIN);
        float range = gainControl.getMaximum() - gainControl.getMinimum();
        float gain = (range * volume) + gainControl.getMinimum();
        gainControl.setValue(gain);
    }

    private void updateEffectsVolume() {
        for (Clip c : effects) {
            FloatControl gainControl = (FloatControl) c.getControl(FloatControl.Type.MASTER_GAIN);
            float range = gainControl.getMaximum() - gainControl.getMinimum();
            float gain = (range * volume) + gainControl.getMinimum();
            gainControl.setValue(gain);
        }
    }

    public void shootEffect() {
        effects[FIRE].stop();
        effects[currentSongId].setMicrosecondPosition(0);
        effects[FIRE].start();
    }

    public void startTankMoveEffect() {
        if (effects[TANK_IDLE].isActive())
            effects[TANK_IDLE].stop();

        if (!effects[TANK_MOVE].isActive()) {
            effects[TANK_MOVE].setMicrosecondPosition(0);
            effects[TANK_MOVE].loop(Clip.LOOP_CONTINUOUSLY);
        }
    }

    public void stopTankIdleEffect() {
        if (effects[TANK_MOVE].isActive())
            effects[TANK_MOVE].stop();

        if (!effects[TANK_IDLE].isActive()) {
            effects[TANK_IDLE].setMicrosecondPosition(0);
            effects[TANK_IDLE].loop(Clip.LOOP_CONTINUOUSLY);
        }
    }

    private void stopTankMoveEffects() {
        if (effects[TANK_IDLE].isActive())
            effects[TANK_IDLE].stop();
        if (effects[TANK_MOVE].isActive())
            effects[TANK_MOVE].stop();
    }

    public void playMenuEffect() {
        stopTankMoveEffects();
        playEffect(PAUSE);
    }

}
