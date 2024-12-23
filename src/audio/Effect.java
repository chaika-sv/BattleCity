package audio;

import javax.sound.sampled.Clip;

public class Effect {

    private Clip clip;
    private int name;
    private boolean isBackground;
    private boolean isUI;

    public Effect(Clip clip, int name, boolean isBackground, boolean isUI) {
        this.clip = clip;
        this.name = name;
        this.isBackground = isBackground;
        this.isUI = isUI;
    }

    public Clip getClip() {
        return clip;
    }

    public boolean isBackground() {
        return isBackground;
    }

    public boolean isUI() {
        return isUI;
    }
}
