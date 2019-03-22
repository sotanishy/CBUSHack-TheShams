package com.coffman.shams.cbushack;

import android.content.res.Resources;
import android.content.res.XmlResourceParser;

import java.util.LinkedList;

import org.xmlpull.v1.XmlPullParser;

public class Situation {

    private String description, overlayText;
    private int drawableId;
    private Decision left, right;

    private Situation() {

    }

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

    public static LinkedList<Situation> parse(XmlResourceParser parser, Resources resources) {
        try {
            int eventType = parser.getEventType();
            LinkedList<Situation> situations = new LinkedList<>();
            Situation currentSituation = null;
            Decision currentDecision = null;

            while (eventType != parser.END_DOCUMENT) {
                String elementName;

                switch (eventType) {
                    case XmlPullParser.START_TAG:
                        elementName = parser.getName();

                        if ("situation".equals(elementName)) {
                            currentSituation = new Situation();
                            situations.add(currentSituation);
                        } else if (currentSituation != null) {
                            if ("header".equals(elementName)) {
                                currentSituation.description = parser.nextText();
                            } else if ("body".equals(elementName)) {
                                currentSituation.overlayText = parser.nextText();
                            } else if ("drawable".equals(elementName)) {
                                currentSituation.drawableId = resources.getIdentifier(parser.nextText(), "drawable", "com.coffman.shams.cbushack");
                            } else if ("decision".equals(elementName)) {
                                parser.next();
                                currentDecision = new Decision(parser.nextText());

                                if (currentSituation.left == null) {
                                    currentSituation.left = currentDecision;
                                } else {
                                    currentSituation.right = currentDecision;
                                }
                            } else if (currentDecision != null) {
                                String[] valuesStr = parser.nextText().split(",");
                                if ("funds".equals(elementName)) {
                                    currentDecision.funds = new int[] {Integer.parseInt(valuesStr[0]), Integer.parseInt(valuesStr[1])};
                                } else if ("happiness".equals(elementName)) {
                                    currentDecision.happiness = new int[] {Integer.parseInt(valuesStr[0]), Integer.parseInt(valuesStr[1])};
                                } else if ("environment".equals(elementName)) {
                                    currentDecision.environment = new int[] {Integer.parseInt(valuesStr[0]), Integer.parseInt(valuesStr[1])};
                                } else if ("energy".equals(elementName)) {
                                    currentDecision.energy = new int[] {Integer.parseInt(valuesStr[0]), Integer.parseInt(valuesStr[1])};
                                }
                            }
                        }
                        break;
                }

                eventType = parser.next();
            }

            return situations;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
