package com.coffman.shams.cbushack;

import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.util.Log;

import org.xmlpull.v1.XmlPullParser;

import java.util.LinkedList;
import java.util.List;

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

    public static List<Situation> parse(XmlResourceParser parser, Resources resources) {
        try {
            int eventType = parser.getEventType();
            LinkedList<Situation> situations = new LinkedList<>();
            Situation currentSituation = null;
            Decision currentDecision;

            while (eventType != parser.END_DOCUMENT) {
                String elementName;

                switch (eventType) {
                    case XmlPullParser.START_TAG:
                        elementName = parser.getName();

                        if ("situation".equals(elementName)) {
                            if (currentSituation != null)
                                Log.e("XML Parser", currentSituation.description);

                            currentSituation = new Situation();
                            situations.add(currentSituation);
                        } else if (currentSituation != null) {
                            if ("header".equals(elementName)) {
                                currentSituation.description = parser.nextText();
                            } else if ("body".equals(elementName)) {
                                currentSituation.overlayText = parser.nextText();
                            } else if ("drawable".equals(elementName)) {
                                String res = parser.nextText();
                                currentSituation.drawableId = R.drawable.boy;//resources.getIdentifier(res.substring(res.indexOf("/") + 1), res.substring(res.indexOf(":") + 1, res.indexOf("/")), res.substring(0, res.indexOf(":")));
                            } else if ("decision".equals(elementName)) {
                                parser.next();
                                currentDecision = new Decision(parser.nextText());

                                if (currentSituation.left == null) {
                                    currentSituation.left = currentDecision;
                                } else {
                                    currentSituation.right = currentDecision;
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

    @Override
    public String toString() {
        return "Situation{" +
                "description='" + description + '\'' +
                ", overlayText='" + overlayText + '\'' +
                ", drawableId=" + drawableId +
                ", left=" + left +
                ", right=" + right +
                '}';
    }
}
