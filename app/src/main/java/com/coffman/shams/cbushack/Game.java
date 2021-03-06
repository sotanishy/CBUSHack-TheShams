package com.coffman.shams.cbushack;

import android.content.res.Resources;

import java.util.LinkedList;

public class Game {

    private boolean gameOver = false;
    private int funds, happiness, environment, energy, time;

    private Situation currentSituation;
    private LinkedList<Situation> situationQueue;

    public Game(Resources resources) {
        funds = happiness = environment = energy = 50;

        situationQueue = Situation.parse(resources.getXml(R.xml.situations), resources);
    }

    public Situation getNextSituation() {
        if (!gameOver) {
            if (situationQueue.size() > 0) {
                currentSituation = situationQueue.pop();
            } else {
                currentSituation = new Situation(
                        "You have reached the end of the game. Congrats!!!",
                        "",
                        R.drawable.boy,
                        new Decision("Ok"),
                        new Decision("Ok")
                );
            }
        }
        return currentSituation;
    }

    public Situation getCurrentSituation() {
        return currentSituation;
    }

    public void endGame(String s) {
        currentSituation = new Situation(
                "Your " + s + " level fell below zero. You got impeached! Game Over!",
                "",
                R.drawable.boy,
                new Decision("Ok"),
                new Decision("Ok")
        );
        gameOver = true;
    }

    public void changeFunds(int delta) {
        funds += delta;
        if (funds < 0) {
            funds = 0;
            endGame("funds");
        }
        if (funds > 100) funds = 100;
    }

    public void changeHappiness(int delta) {
        happiness += delta;
        if (happiness < 0) {
            happiness = 0;
            endGame("happiness");
        }
        if (happiness > 100) happiness = 100;
    }

    public void changeEnvironment(int delta) {
        environment += delta;
        if (environment < 0) {
            environment = 0;
            endGame("environment");
        }
        if (environment > 100) environment = 100;
    }

    public void changeEnergy(int delta) {
        energy += delta;
        if (energy < 0) {
            energy = 0;
            endGame("energy");
        }
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
