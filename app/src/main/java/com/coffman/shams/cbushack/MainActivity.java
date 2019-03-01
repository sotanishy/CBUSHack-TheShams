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

public class MainActivity extends AppCompatActivity {

    //TODO credit images : Icons made by Freepik from www.flaticon.com

    private TextView topText;
    private ConstraintLayout card;
    private ImageView cardImage;
    private TextView cardText, overlayText;
    private TextView dateText;
    private TextView fundsValue, happinessValue, environmentValue, energyValue;

    private Game game;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        topText = findViewById(R.id.topText);

        dateText = findViewById(R.id.date);

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
                        view.setX((event.getRawX() * 2) + dX * 2);

                        if (view.getX() < view.getPaddingLeft()) {
                            overlayText.setText(game.getCurrentSituation().getLeft().getDescription());
                            overlayText.setAlpha(-view.getX() / view.getWidth() - 0.2f);
                            overlayText.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END);
                        } else {
                            overlayText.setText(game.getCurrentSituation().getRight().getDescription());
                            overlayText.setAlpha(view.getX() / view.getWidth() - 0.2f);
                            overlayText.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
                        }

                        lastAction = MotionEvent.ACTION_MOVE;
                        break;

                    case MotionEvent.ACTION_UP:
                        if (view.getX() < view.getWidth() * -0.7) {
                            Log.e("D", game.getCurrentSituation().getDescription() + " -> " + game.getCurrentSituation().getLeft().getDescription());
                            game.getCurrentSituation().getLeft().onChoose(game);
                            nextSituation();
                        } else if (view.getX() > view.getWidth() * 0.7) {
                            Log.e("D", game.getCurrentSituation().getDescription() + " -> " + game.getCurrentSituation().getRight().getDescription());
                            game.getCurrentSituation().getRight().onChoose(game);
                            nextSituation();
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

        fundsValue = findViewById(R.id.fundsValue);
        happinessValue = findViewById(R.id.happinessValue);
        environmentValue = findViewById(R.id.environmentValue);
        energyValue = findViewById(R.id.energyValue);

        game = new Game(getResources());

        nextSituation();
    }


    private void nextSituation() {
        Situation situation = game.getNextSituation();

        // Update Card
        topText.setText(situation.getDescription());
        overlayText.setText(situation.getOverlayText());
        cardImage.setImageDrawable(getResources().getDrawable(situation.getDrawableId()));

        // Update other values
        fundsValue.setText("" + game.getFunds());
        happinessValue.setText("" + game.getHappiness());
        environmentValue.setText("" + game.getEnvironment());
        energyValue.setText("" + game.getEnergy());
        dateText.setText(game.getTime() + " Months");
    }

    /**
     * Change the value by the specified amount
     *
     * @param id    the resource id of the value you want to change
     * @param value how much you want to change the value by
     */
    private void changeValue(int id, double value) {
        TextView tv = findViewById(id);
        double currentValue = Integer.parseInt(tv.getText().toString());
        tv.setText("" + (currentValue + value));
    }
}
