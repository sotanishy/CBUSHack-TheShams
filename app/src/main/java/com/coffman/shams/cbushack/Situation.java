package com.coffman.shams.cbushack;

public class Situation {
    private String description, overlayText;
    private int drawableId;
    private Decision left, right;

    public Situation(String description, String overlayText, int drawableId, Decision left, Decision right) {
        this.description = description;
        this.overlayText = overlayText;
        this.drawableId = drawableId;
        this.left = left;
        this.right = right;
    }

    public String getDescription() {
        return description;
    }

    public String getOverlayText() {
        return overlayText;
    }

    public int getDrawableId() {
        return drawableId;
    }

    public Decision getLeft() {
        return left;
    }

    public Decision getRight() {
        return right;
    }
}
