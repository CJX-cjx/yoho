package com.bawei.youhuo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class LettersView extends View {
    private Paint paint;
    private String letters[] = new String[27];
    private int textLocation[] = new int[27];
    private int index;
    private int currentHeight;
    private Point point;
    private int h;
    private Canvas canvas;
    private int width;
    private  int selColor = Color.BLACK;
    private  int unSelColor = Color.GRAY;
    private String letter;

    public LettersView(Context context, AttributeSet attrs) {
        super(context, attrs);
        for(int i = 65;i < 91;i++) {
            char c = (char) i;
            letters[index++] = c + "";
        }
        letters[index] = "0";
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setAntiAlias(true);
        paint.setTextSize(30);
        paint.setTextAlign(Paint.Align.CENTER);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        this.canvas = canvas;
        currentHeight = 0;
        width = getMeasuredWidth();
        int height = getMeasuredHeight();
        h = height / letters.length;
        for(int i = 0;i < letters.length;i++) {
            paint.setColor(unSelColor);
            currentHeight += h;
            textLocation[i] = currentHeight;
            if(letters[i].equals(letter))
                paint.setColor(selColor);
            canvas.drawText(letters[i], width / 2,currentHeight,paint);
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        point = new Point();
        letter = null;
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN :
                point.y = (int) event.getY();
                letter = getLetters();
                break;
            case MotionEvent.ACTION_MOVE :
                point.y = (int) event.getY();
                letter = getLetters();
                break;
        }
        invalidate();
        return true;
    }

    private String getLetters() {
        for(int i = 0;i < letters.length;i++) {
            if(point.y >= h * i && point.y < h * (i + 1)) {
                return letters[i];
            }
        }
        return null;
    }
}
