package uk.ac.standrews.cs.cs5041.draw;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

public class DrawingPane extends View {

    public DrawingPane(Context context) {
        super(context);
    }

    ArrayList<Line> lines = new ArrayList<Line>();

    @Override
    protected void onDraw(Canvas canvas) {
        drawBackground(canvas);
        drawObjects(canvas);
    }

    private void drawBackground(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(Color.GRAY);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(5);

        int w = getWidth() - (getPaddingLeft() + getPaddingRight());
        int h = getHeight() - (getPaddingTop() + getPaddingBottom());
        int x = getPaddingLeft();
        int y = getPaddingTop();
        canvas.drawRect(x, y, w, h, paint);
    }

    void drawObjects(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(Color.BLUE);
        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeWidth(5);

        for(Line line : lines) {
            canvas.drawLine(line.x1, line.y1, line.x2, line.y2, paint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action, pointerIndex;
        float x, y;

        action = event.getActionMasked();
        pointerIndex = event.getActionIndex();

        x = event.getX(pointerIndex);
        y = event.getY(pointerIndex);

        switch (action) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_POINTER_DOWN: {
                addNewLine(x, y);
                break;
            }
            case MotionEvent.ACTION_MOVE:
            {
                moveLine(x,y);
                break;
            }
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_POINTER_UP:
            case MotionEvent.ACTION_CANCEL:
            {
                break;
            }
        }
        invalidate();
        return true;
    }

    boolean showLine = false;
    float lineX, lineY;
    void addNewLine(float x, float y) {
        int size = 200;
        lines.add(new Line(x-size, y, x+size, y));
    }

    void moveLine(float x, float y) {
        addNewLine(x, y);
//        lineX = x;
//        lineY = y;
    }

    void removeLine(int pointerID) {
        //showLine = false;
    }
}
