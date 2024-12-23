package audio;

import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;
import java.util.*;

import static utils.Constants.Audio.*;

public class AudioPlayer {

    private Map<Integer, Effect> effects;
    private float volume = 0.7f;
    private boolean effectMute;
    private boolean effectMuteMove;

    private Clip idleClip;
    private Clip moveClip;

    public AudioPlayer() {
        loadEffects();
    }

    private void loadEffects() {
        effects = new HashMap<>();

        effects.put(FIRE, new Effect(getClip("fire"), FIRE, false, false) );
        effects.put(LEVEL_INTRO, new Effect(getClip("level-intro"), LEVEL_INTRO, true, false) );
        effects.put(TANK_IDLE, new Effect(getClip("tank-idle"), TANK_IDLE, true, false) );
        effects.put(TANK_MOVE, new Effect(getClip("tank-move"), TANK_MOVE, true, false) );
        effects.put(ENEMY_EXPLOSION, new Effect(getClip("enemy-explosion"), ENEMY_EXPLOSION, false, false) );
        effects.put(GAME_OVER, new Effect(getClip("game-over"), GAME_OVER, false, true) );
        effects.put(HIGH_SCORE, new Effect(getClip("high-score"), HIGH_SCORE, false, true) );
        effects.put(HIT_BRICK, new Effect(getClip("hit-brick"), HIT_BRICK, false, false) );
        effects.put(HIT_ENEMY, new Effect(getClip("hit-enemy"), HIT_ENEMY, false, false) );
        effects.put(HIT_STEEL, new Effect(getClip("hit-steel"), HIT_STEEL, false, false) );
        effects.put(ICE, new Effect(getClip("ice"), ICE, false, false) );
        effects.put(LIFE, new Effect(getClip("life_1"), LIFE, false, false) );
        effects.put(PAUSE, new Effect(getClip("pause_1"), PAUSE, false, true) );
        effects.put(PLAYER_EXPLOSION, new Effect(getClip("player-explosion"), PLAYER_EXPLOSION, false, false) );
        effects.put(POWER_UP_APPEAR, new Effect(getClip("powerup-appear"), POWER_UP_APPEAR, false, false) );
        effects.put(POWER_UP_PICKUP, new Effect(getClip("powerup-pickup_1"), POWER_UP_PICKUP, false, false) );
        effects.put(SCORE, new Effect(getClip("score"), SCORE, false, true) );
        effects.put(SCORE_BONUS, new Effect(getClip("score-bonus"), SCORE_BONUS, false, true) );
        effects.put(UNKNOWN_1, new Effect(getClip("unknown-2"), UNKNOWN_1, false, false) );
        effects.put(UNKNOWN_2, new Effect(getClip("unknown-3"), UNKNOWN_2, false, false) );
        effects.put(VICTORY, new Effect(getClip("victory_1"), VICTORY, false, true) );

        idleClip = effects.get(TANK_IDLE).getClip();
        moveClip = effects.get(TANK_MOVE).getClip();
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
        updateEffectsVolume();
    }


    /**
     * Mute all effectes
     */
    public void toggleEffectMute() {
        this.effectMute = !effectMute;

        for (Map.Entry<Integer, Effect> e : effects.entrySet()) {
            BooleanControl booleanControl = (BooleanControl) e.getValue().getClip().getControl(BooleanControl.Type.MUTE);
            booleanControl.setValue(effectMute);
        }

        // When we turn on effects then we want to let the player know by playing some short effect
        playEffect(HIT_STEEL);
    }


    /**
     * Mute only tank movement effects (move and idle)
     */
    public void toggleEffectMuteMove() {
        this.effectMuteMove = !effectMuteMove;

        BooleanControl booleanControl = (BooleanControl) moveClip.getControl(BooleanControl.Type.MUTE);
        booleanControl.setValue(effectMuteMove);

        booleanControl = (BooleanControl) idleClip.getControl(BooleanControl.Type.MUTE);
        booleanControl.setValue(effectMuteMove);

        // When we turn on effects then we want to let the player know by playing some short effect
        playEffect(HIT_STEEL);
    }

    /**
     * Play effect.
     * Depending on the effect type we should stop other effects.
     * @param effect effect constant
     */
    public void playEffect(int effect) {

        // For UI effects stop any other effects
        if (effects.get(effect).isUI())
            for (Map.Entry<Integer, Effect> e : effects.entrySet()) {
                if (e.getValue().getClip().isActive())
                    e.getValue().getClip().stop();
            }
        else if (!effects.get(effect).isBackground())
                // For all other effects except background effects stop all effects except background effects
                if (!effects.get(effect).isBackground())
                    for (Map.Entry<Integer, Effect> e : effects.entrySet()) {
                        if (!e.getValue().isBackground())
                            if (e.getValue().getClip().isActive())
                                e.getValue().getClip().stop();
                    }


        effects.get(effect).getClip().setMicrosecondPosition(0);
        effects.get(effect).getClip().start();
    }


    /**
     * Stop tank idle effect and start tank move effects
     */
    public void startTankMoveEffect() {
        if (idleClip.isActive())
            idleClip.stop();

        if (!moveClip.isActive()) {
            moveClip.setMicrosecondPosition(0);
            moveClip.loop(Clip.LOOP_CONTINUOUSLY);
            moveClip.start();
        }
    }

    /**
     * Stop tank move effect and start tank idle effects
     */
    public void stopTankIdleEffect() {
        if (moveClip.isActive())
            moveClip.stop();

        if (!idleClip.isActive()) {
            idleClip.setMicrosecondPosition(0);
            idleClip.loop(Clip.LOOP_CONTINUOUSLY);
            idleClip.start();
        }
    }

    private void updateEffectsVolume() {
        for (Map.Entry<Integer, Effect> e : effects.entrySet()) {
            FloatControl gainControl = (FloatControl) e.getValue().getClip().getControl(FloatControl.Type.MASTER_GAIN);
            float range = gainControl.getMaximum() - gainControl.getMinimum();
            float gain = (range * volume) + gainControl.getMinimum();
            gainControl.setValue(gain);
        }
    }

}
