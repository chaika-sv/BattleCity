package entities;

import gamestates.Playing;

import java.awt.*;

import static utils.Constants.Audio.FIRE;
import static utils.Constants.Audio.PLAYER_EXPLOSION;
import static utils.Constants.DEBUG_MODE;
import static utils.Constants.DirConstants.UP;
import static utils.Constants.LevelConstants.PLAYER_1_SPAWN_X;
import static utils.Constants.LevelConstants.PLAYER_1_SPAWN_Y;
import static utils.Constants.TankColorConstants.PLAYER_YELLOW;
import static utils.Constants.TankTypeConstants.MAX_TANK_TYPE;


public class Player extends Tank{

    private int points;

    public Player(TankType tankType, float x, float y, Playing playing) {
        super(tankType, x, y, playing);
        tankColor = PLAYER_YELLOW;
    }

    @Override
    public void resetAll() {
        super.resetAll();
        active = true;
        points = 0;
    }

    @Override
    protected void updatePosition() {
        boolean movingPrev = moving;

        super.updatePosition();

        // If tank have just started moving then start tank move effect (and stop tank idle)
        if (moving && !movingPrev)
            playing.getGame().getAudioPlayer().startTankMoveEffect();

        // If tank is not moving then stop tank moving and start tank idle
        if (!moving)
            playing.getGame().getAudioPlayer().stopTankIdleEffect();

    }

    @Override
    public void killTheTank(Tank killerTank) {
        super.killTheTank(killerTank);

        playing.gameOver();
    }

    @Override
    public void injureTheTank() {
        super.injureTheTank();

        curDir = UP;
        hitbox.x = PLAYER_1_SPAWN_X;
        hitbox.y = PLAYER_1_SPAWN_Y;
        syncHitboxWithSprite();
        playing.getObjectManager().createShield(this);
        tankType = TankType.T_BASE;

        playing.getGame().getAudioPlayer().playEffect(PLAYER_EXPLOSION);
    }

    public void levelUp() {
        if (tankType.getId() < MAX_TANK_TYPE) {
            tankType = TankType.getTankTypeByCode(tankType.getId() + 1, Player.class);
            applyTankCharacteristics(tankType);
        }
    }

    @Override
    protected void shoot() {
        super.shoot();
        playing.getGame().getAudioPlayer().playEffect(FIRE);
    }

    @Override
    public void draw(Graphics g) {
        super.draw(g);
        if (DEBUG_MODE)
            drawInfo(g);
    }

    private void drawInfo(Graphics g) {
        String msg = curDir + "; " + String.valueOf(moveInOneDir);

        g.setColor(Color.WHITE);
        g.drawString(msg, (int)(hitbox.x - 5), (hitbox.y - 5 > 0) ? (int)(hitbox.y - 5) : (int)(hitbox.y + hitbox.height + 10));
    }


    public void addPoints(int v) {
        points += v;
    }

    public int getPoints() {
        return points;
    }
}
