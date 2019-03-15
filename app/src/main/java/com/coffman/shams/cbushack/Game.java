package com.coffman.shams.cbushack;

import android.content.res.Resources;

import java.util.LinkedList;
import java.util.NoSuchElementException;

public class Game {
    private int funds, happiness, environment, energy, time;
    private Situation currentSituation;
    private LinkedList<Situation> situationQueue;

    public Game(Resources resources) {
        funds = happiness = environment = energy = 50;

        situationQueue = Situation.parse(resources.getXml(R.xml.situations), resources);
    }

    public Situation getNextSituation() {
        try {
            return currentSituation = situationQueue.pop();
        } catch (NoSuchElementException e) {
            return currentSituation = new Situation(
                    "You have reached the end of the game. Congrats!!! There is more to come soon...",
                    "",
                    R.drawable.boy,
                    new Decision("Ok"),
                    new Decision("Ok"));
        }
    }

    public Situation getCurrentSituation() {
        return currentSituation;
    }

    public void changeFunds(int delta) {
        funds += delta;
        if (funds < 0) funds = 0;
        if (funds > 100) funds = 100;
    }

    public void changeHappiness(int delta) {
        happiness += delta;
        if (happiness < 0) happiness = 0;
        if (happiness > 100) happiness = 100;
    }

    public void changeEnvironment(int delta) {
        environment += delta;
        if (environment < 0) environment = 0;
        if (environment > 100) environment = 100;
    }

    public void changeEnergy(int delta) {
        energy += delta;
        if (energy < 0) energy = 0;
        if (energy > 100) energy = 100;
    }

    public void changeTime(int delta) {
        time += delta;
    }

    public int getFunds() {
        return funds;
    }

    public int getHappiness() {
        return happiness;
    }

    public int getEnvironment() {
        return environment;
    }

    public int getEnergy() {
        return energy;
    }

    public int getTime() {
        return time;
    }
}
