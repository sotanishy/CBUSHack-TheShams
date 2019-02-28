package com.coffman.shams.cbushack;

public class Decision {
    private String description;

    public Decision(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void onChoose(Game game) {
        game.changeTime(3);
    }

    @Override
    public String toString() {
        return "Decision{" +
                "description='" + description + '\'' +
                '}';
    }
}
