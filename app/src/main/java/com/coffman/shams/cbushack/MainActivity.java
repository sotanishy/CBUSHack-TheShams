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
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView topText = findViewById(R.id.topText);

        final TextView decisionText = findViewById(R.id.decisionText);
        decisionText.setAlpha(0);
        decisionText.setBackgroundColor(Color.BLACK);
        decisionText.setTextColor(Color.WHITE);

        final ConstraintLayout card = findViewById(R.id.card);
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
                            decisionText.setAlpha(-view.getX() / view.getWidth() - 0.2f);
                            decisionText.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END);
                        } else {
                            decisionText.setAlpha(view.getX() / view.getWidth() - 0.2f);
                            decisionText.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
                        }

                        lastAction = MotionEvent.ACTION_MOVE;
                        break;

                    case MotionEvent.ACTION_UP:
                        if (view.getX() < view.getWidth() * -0.7) {
                            Log.e("D", "Left");
                        } else if (view.getX() > view.getWidth() * 0.7) {
                            Log.e("D", "Right");
                        } else {
                            ObjectAnimator animation = ObjectAnimator.ofFloat(view, "translationX", view.getPaddingLeft());
                            animation.setDuration(500);
                            animation.start();

                            ObjectAnimator alphaAnimation = ObjectAnimator.ofFloat(decisionText, "alpha", 0);
                            alphaAnimation.setDuration(500);
                            alphaAnimation.start();
                        }

                        break;

                    default:
                        return false;
                }
                return true;

            }
        });
    }
}
