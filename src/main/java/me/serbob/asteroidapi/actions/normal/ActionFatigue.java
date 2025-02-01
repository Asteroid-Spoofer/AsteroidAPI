package me.serbob.asteroidapi.actions.normal;

/*
 * WORK IN PROGRESS
 * NOT YET ADDED
 */
public class ActionFatigue {
    private int maxFatigue;
    private int currentFatigue;
    private int recoveryRate;
    private int fatigueRate;
    private long lastUpdateTime;

    public ActionFatigue(int maxFatigue, int recoveryRate, int fatigueRate) {
        this.maxFatigue = maxFatigue;
        this.recoveryRate = recoveryRate;
        this.fatigueRate = fatigueRate;
        this.lastUpdateTime = System.currentTimeMillis();
    }

    public void update() {
        long currentTime = System.currentTimeMillis();
        long delta = currentTime - lastUpdateTime;
        lastUpdateTime = currentTime;

        if (currentFatigue > 0) {
            currentFatigue = Math.max(0, currentFatigue - (int)(recoveryRate * delta / 1000));
        }
    }

    public void increaseFatigue() {
        currentFatigue = Math.min(maxFatigue, currentFatigue + fatigueRate);
    }

    public boolean isFatigued() {
        return currentFatigue >= maxFatigue;
    }
}
