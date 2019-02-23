package com.coffman.shams.cbushack;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.NoSuchElementException;

public class MainActivity extends AppCompatActivity {

    //TODO credit images : Icons made by Freepik from www.flaticon.com

    private TextView topText;
    private ConstraintLayout card;
    private ImageView cardImage;
    private TextView cardText, overlayText;
    private Situation currentSituation;

    private LinkedList<Situation> situationQueue;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        situationQueue = new LinkedList<>(
                Arrays.asList(
                        new Situation(
                                "You have been elected president of the Columbus City Council. The citizens of this great city have trusted you to make the tough decisions, make them proud. Are you ready for your first day?",
                                "",
                                getResources().getDrawable(R.drawable.man),
                                new Decision("No"),
                                new Decision("Yes")),

                        new Situation(
                                "The people want to open a new school to stop the overcrowding. Do you agree?",
                                "",
                                getResources().getDrawable(R.drawable.girl),
                                new Decision("No, they're fine"),
                                new Decision("Yes"))
                )
        );

        topText = findViewById(R.id.topText);

        overlayText = findViewById(R.id.overlayText);
        overlayText.setAlpha(0);
        overlayText.setBackgroundColor(Color.BLACK);
        overlayText.setTextColor(Color.WHITE);

        card = findViewById(R.id.card);
        card.setBackgroundColor(0xF7F6F2FF);

        card.setOnTouchListener(new View.OnTouchListener() {
            float dX;
            int lastAction;

            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                switch (event.getActionMasked()) {
                    case MotionEvent.ACTION_DOWN:
                        dX = view.getX() - event.getRawX();
                        lastAction = MotionEvent.ACTION_DOWN;
                        break;

                    case MotionEvent.ACTION_MOVE:
                        view.setX((event.getRawX() * 2) - view.getWidth());


                        if (view.getX() < view.getPaddingLeft()) {
                            overlayText.setText(currentSituation.getLeft().getDescription());
                            overlayText.setAlpha(-view.getX() / view.getWidth() - 0.2f);
                            overlayText.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END);
                        } else {
                            overlayText.setText(currentSituation.getRight().getDescription());
                            overlayText.setAlpha(view.getX() / view.getWidth() - 0.2f);
                            overlayText.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
                        }

                        lastAction = MotionEvent.ACTION_MOVE;
                        break;

                    case MotionEvent.ACTION_UP:
                        if (view.getX() < view.getWidth() * -0.7) {
                            Log.e("D", "Left");
                            setSituation(getNextSituation());
                        } else if (view.getX() > view.getWidth() * 0.7) {
                            Log.e("D", "Right");
                            setSituation(getNextSituation());
                        }

                        ObjectAnimator animation = ObjectAnimator.ofFloat(view, "translationX", view.getPaddingLeft());
                        animation.setDuration(500);
                        animation.start();

                        ObjectAnimator alphaAnimation = ObjectAnimator.ofFloat(overlayText, "alpha", 0);
                        alphaAnimation.setDuration(500);
                        alphaAnimation.start();

                        break;

                    default:
                        return false;
                }
                return true;

            }
        });

        cardImage = findViewById(R.id.cardImage);

        cardText = findViewById(R.id.cardText);

        setSituation(getNextSituation());
    }

    private Situation getNextSituation() {
        try {
            return situationQueue.pop();
        } catch (NoSuchElementException e) {
            return new Situation(
                    "You have reached the end of the game. Congrats!!! There is more to come soon...",
                    "",
                    getResources().getDrawable(R.drawable.boy),
                    new Decision("Ok"),
                    new Decision("Ok"));
        }
    }

    private void setSituation(Situation situation) {
        currentSituation = situation;
        topText.setText(situation.getDescription());
        overlayText.setText(situation.getOverlayText());
        cardImage.setImageDrawable(situation.getImage());
    }
}
