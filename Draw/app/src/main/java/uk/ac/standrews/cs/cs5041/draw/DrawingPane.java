package uk.ac.standrews.cs.cs5041.draw;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;

public class DrawingPane extends View {

    public DrawingPane(Context context) {
        super(context);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        drawBackground(canvas);
    }

    private void drawBackground(Canvas canvas) {


        int w = getWidth() - (getPaddingLeft() + getPaddingRight());
        int h = getHeight() - (getPaddingTop() + getPaddingBottom());
        int x = getPaddingLeft();
        int y = getPaddingTop();
        Paint paint = new Paint();
        paint.setColor(Color.BLUE);
        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeWidth(5);
        //canvas.drawRect(x, y, w, h, paint);

        //canvas.drawRect(x + 200, y + 200, w / 2, h / 2, paint);

        if(showLine) {
            int size = 200;
            int finger = 30;
            canvas.drawLine(lineX-size, lineY - finger, lineX+size, lineY- finger, paint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getActionMasked();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_POINTER_DOWN: {
                int pointerIndex = event.getActionIndex();
                int pointerID = event.getPointerId(pointerIndex);
                float x = event.getX(pointerIndex);
                float y = event.getY(pointerIndex);
                addNewLine(x, y, pointerID);
                break;
            }
            case MotionEvent.ACTION_MOVE:
            {
                int pointerCount = event.getPointerCount();
                for(int p=0; p<pointerCount; p++) {
                    int pointerID = event.getPointerId(p);
                    float x = event.getX(p);
                    float y = event.getY(p);
                    moveLine(x,y,pointerID);
                }
                break;
            }
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_POINTER_UP:
            case MotionEvent.ACTION_CANCEL:
            {
                int pointerIndex = event.getActionIndex();
                int pointerID = event.getPointerId(pointerIndex);
                removeLine(pointerID);
            }
        }
        invalidate();
        return true;
    }

    boolean showLine = false;
    float lineX, lineY;
    void addNewLine(float x, float y, int pointerID) {
        lineX = x;
        lineY = y;
        showLine = true;
    }

    void moveLine(float x, float y, int pointerID) {
        lineX = x;
        lineY = y;
    }

    void removeLine(int pointerID) {
        //showLine = false;
    }
}
