package com.coffman.shams.cbushack;

import android.graphics.drawable.Drawable;

public class Situation {
    private String description, overlayText, date;
    private Drawable image;
    private Decision left, right;

    public Situation(String description, String overlayText, String date, Drawable image, Decision left, Decision right) {
        this.description = description;
        this.overlayText = overlayText;
        this.date = date;
        this.image = image;
        this.left = left;
        this.right = right;
    }

    public String getDescription() {
        return description;
    }

    public String getOverlayText() {
        return overlayText;
    }

    public String getDate() {
        return date;
    }

    public Drawable getImage() {
        return image;
    }

    public Decision getLeft() {
        return left;
    }

    public Decision getRight() {
        return right;
    }

}
