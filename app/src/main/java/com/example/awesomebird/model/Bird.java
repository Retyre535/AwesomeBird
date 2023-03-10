package com.example.awesomebird.model;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.awesomebird.R;

public class Bird extends GameObject {

    private Bitmap sprite;
    private final Bitmap up;
    private final Bitmap down;
    private final Bitmap straight;
    private float ySpeed = 0;
    private static final float Y_ACCELERATION = 9f;

    public Bitmap getSprite() {
        return sprite;
    }

    public Bird(Context context, float x, float y) {
        super(x, y);
        up = BitmapFactory.decodeResource(context.getResources(), R.drawable.bluebird_3);
        straight = BitmapFactory.decodeResource(context.getResources(), R.drawable.bluebird_2);
        down = BitmapFactory.decodeResource(context.getResources(), R.drawable.bluebird_1);

        sprite = straight;
    }

    public void fly(){
        ySpeed = -70;
    }

    @Override
    public void update(){
        y+=ySpeed;
        ySpeed+=Y_ACCELERATION;
        if (ySpeed < -20) sprite = down;
        else if(ySpeed > 20) sprite = up;
        else sprite = straight;
    }

}
