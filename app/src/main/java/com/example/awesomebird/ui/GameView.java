package com.example.awesomebird.ui;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;

import com.example.awesomebird.MainActivity;
import com.example.awesomebird.R;
import com.example.awesomebird.model.Bird;
import com.example.awesomebird.model.Pipe;

public class GameView extends SurfaceView implements SurfaceHolder.Callback {

    private final Bitmap background;
    private SurfaceHolder surfaceHolder;
    private final DrawThread drawThread;
    private Bird bird;
    private Pipe pipe;

    float t_x = 0, t_y = 0;

    Paint paintText = new Paint();

    public GameView(Context context) {
        super(context);
        background = BitmapFactory.decodeResource(context.getResources(), R.drawable.back);
        drawThread = new DrawThread();
        getHolder().addCallback(this);
    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder surfaceHolder) {
        this.surfaceHolder = surfaceHolder;
        bird = new Bird(getContext(), 200, getHeight() / 2);
        drawThread.start();
        pipe = new Pipe(getContext(), getHeight(), getWidth());
    }

    private void drawFrames(Canvas canvas) {
        Rect backgroundRect = new Rect(0, 0, getWidth(), getHeight());
        canvas.drawBitmap(background, null, backgroundRect, null);
        canvas.drawBitmap(bird.getSprite(), bird.x, bird.y, null);
        canvas.drawBitmap(pipe.getTopPipe(), pipe.x, 0, null);
        canvas.drawBitmap(pipe.getBottomPipe(), pipe.x, getHeight() - pipe.getBottomPipe().getHeight(), null);
        paintText.setTextSize(100);
        canvas.drawText("Score: " + pipe.getScore(), getWidth() - 500, 120, paintText);
    }

    private void update(Canvas canvas) {
        bird.update();
        pipe.update();
        if (pipe.isCollision(bird) || bird.y <= 0 || bird.y >= getHeight()) {
            Paint paintRect = new Paint();
            paintRect.setColor(Color.parseColor("#DFFFF27B"));
            paintText.setTextSize(100);
            canvas.drawRect(getWidth() / 2 - 500, getHeight() / 2 - 300, getWidth() / 2 + 500, getHeight() / 2 + 300, paintRect);
            canvas.drawText("Score: " + pipe.getScore(), getWidth() / 2 - 200, getHeight() / 2 - 100, paintText);
            paintRect.setColor(Color.WHITE);
            canvas.drawRect(getWidth() / 2 - 300, getHeight() / 2, getWidth() / 2 + 300, getHeight() / 2 + 200, paintRect);
            canvas.drawText("Restart", getWidth() / 2 - 170, getHeight() / 2 + 140, paintText);
            drawThread.running = false;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.i("RRR","Fly");
        bird.fly();
        t_x = event.getX();
        t_y = event.getX();
        if ((drawThread.running==false)&&((t_x>getWidth() / 2 - 300)||(t_x<getWidth() / 2 + 300))&&((t_x>getHeight() / 2)||(t_x<getHeight() / 2 + 200))) {
            pipe.setScore(0);
            drawThread.running = true;
            bird.setX(200);
            bird.setY(getHeight()/2);
            pipe.generatePipes();
        }
        return super.onTouchEvent(event);
    }

    private class DrawThread extends Thread {

        private volatile boolean running = true;

        @Override
        public void run() {
            Canvas canvas;
            while (running) {
                canvas = surfaceHolder.lockCanvas();
                try {
                    drawFrames(canvas);
                    update(canvas);
                } catch (Exception e) {
                    Log.e("RRR", "run: ");
                } finally {
                    surfaceHolder.unlockCanvasAndPost(canvas);
                }
            }
        }
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder surfaceHolder) {

    }
}
