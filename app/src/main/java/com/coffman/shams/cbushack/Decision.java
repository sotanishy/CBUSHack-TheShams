package com.coffman.shams.cbushack;

import android.util.Log;
import java.util.Random;

public class Decision {
    private String description;

    public int[] funds, happiness, environment, energy;

    public Decision(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void onChoose(Game game) {
        game.changeTime(3);
        game.changeFunds(getRandomNumber(funds));
        game.changeHappiness(getRandomNumber(happiness));
        game.changeEnvironment(getRandomNumber(environment));
        game.changeEnergy(getRandomNumber(energy));
    }

    private int getRandomNumber(int[] range) {
        if (range == null) return 0;
        int min = Math.min(range[0], range[1]);
        int max = Math.max(range[0], range[1]);
        return new Random().nextInt(max - min + 1) + min;
    }

    @Override
    public String toString() {
        return "Decision{" +
                "description='" + description + '\'' +
                '}';
    }
}
