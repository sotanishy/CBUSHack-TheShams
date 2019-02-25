package com.coffman.shams.cbushack;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.NoSuchElementException;

public class Game {
    private int funds, happiness, environment, energy, time;
    private Situation currentSituation;
    private LinkedList<Situation> situationQueue;

    public Game() {
        funds = happiness = environment = energy = 50;

        situationQueue = new LinkedList<>(
                Arrays.asList(
                        new Situation(
                                "You have been elected president of the Columbus City Council. The citizens of this great city have trusted you to make the tough decisions, make them proud. Are you ready for your first day?",
                                "",
                                R.drawable.man,
                                new Decision("No"),
                                new Decision("Yes")),

                        new Situation(
                                "The people want to open a new school to stop the overcrowding. Do you agree?",
                                "",
                                R.drawable.girl,
                                new Decision("No, they're fine") {
                                    @Override
                                    public void onChoose(Game game) {
                                        super.onChoose(game);
                                        game.changeHappiness(-5);
                                    }
                                },
                                new Decision("Yes") {
                                    @Override
                                    public void onChoose(Game game) {
                                        super.onChoose(game);
                                        game.changeHappiness(10);
                                        game.changeFunds(-10);
                                    }
                                })
                )
        );
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
    }

    public void changeHappiness(int delta) {
        happiness += delta;
    }

    public void changeEnvironment(int delta) {
        environment += delta;
    }

    public void changeEnergy(int delta) {
        energy += delta;
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
