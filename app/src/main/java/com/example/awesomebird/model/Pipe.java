package com.example.awesomebird.model;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import com.example.awesomebird.R;

public class Pipe extends GameObject {

    private Bitmap topPipe;
    private Bitmap bottomPipe;

    private int score = 0;

    public void setScore(int score) {
        this.score = score;
    }

    public int getScore() {
        return score;
    }

    private static final float X_SPEED = 50;
    private static final float SPACER_SIZE = 300;

    private final float height;
    private final float width;

    public Pipe(Context context, float height, float width) {
        super(width, 0);
        this.height = height;
        this.width = width;
        topPipe = BitmapFactory.decodeResource(context.getResources(), R.drawable.pipe_rotated);
        bottomPipe = BitmapFactory.decodeResource(context.getResources(), R.drawable.pipe);
        generatePipes();
    }

    public void generatePipes() {
        y = random(height / 4f, height * 3 / 4f);
        topPipe = Bitmap.createScaledBitmap(topPipe, 200, (int) (y - SPACER_SIZE), false);
        bottomPipe = Bitmap.createScaledBitmap(bottomPipe, 200, (int) (height - y - SPACER_SIZE), false);
    }

    @Override
    public void update() {
        x -= X_SPEED;
        if (x <= -bottomPipe.getWidth()) {
            x = width;
            generatePipes();
            score+=1;
        }
    }


    public boolean isCollision(GameObject object) {
        if (x - 50 < object.x && x + bottomPipe.getWidth() > object.x) {
            if (object.y < topPipe.getHeight()) return true;
            return object.y - 10 > height - bottomPipe.getHeight();
        }
        return false;
    }


    private float random(float min, float max) {
        return (float) (min + Math.random() * (max - min));
    }

    public Bitmap getBottomPipe() {
        return bottomPipe;
    }

    public Bitmap getTopPipe() {
        return topPipe;
    }
}
