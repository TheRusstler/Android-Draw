package uk.ac.standrews.cs.cs5041.draw;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

public class MainActivity extends ActionBarActivity {

    DrawingPane board;
    Button move, scale, confirm, cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        board = new DrawingPane(this, new Runnable() {
            @Override
            public void run() {
                updateButtons();
            }
        });

        setContentView(R.layout.activity_main);
        RelativeLayout l = (RelativeLayout) findViewById(R.id.drawingLayout);
        l.addView(board);

        move = (Button) findViewById(R.id.moveButton);
        scale = (Button) findViewById(R.id.scaleButton);
        confirm = (Button) findViewById(R.id.confirmButton);
        cancel = (Button) findViewById(R.id.cancelButton);
        updateButtons();
    }

    public void clear(View view) {
        board.clear();
    }

    public void draw(View view) {
        board.setMode(DrawingMode.Draw);
    }

    public void move(View view) {
        board.setMode(DrawingMode.Move);
    }

    public void scale(View view) {
        board.setMode(DrawingMode.Scale);
    }

    public void cancel(View view) {
        board.cancelNewObject();
    }

    public void confirm(View view) {
        board.confirmNewObject();
    }

    void updateButtons() {
        int moveVis = View.GONE, scaleVis = View.GONE, confirmVis = View.GONE, cancelVis = View.GONE;

        switch (board.getMode()) {
            case Draw:
                break;
            case Drawn:
                moveVis = View.VISIBLE;
                scaleVis = View.VISIBLE;
                confirmVis = View.VISIBLE;
                cancelVis = View.VISIBLE;
                break;
            case Move:
            case Scale:
                confirmVis = View.VISIBLE;
                cancelVis = View.VISIBLE;
                break;
        }

        move.setVisibility(moveVis);
        scale.setVisibility(scaleVis);
        confirm.setVisibility(confirmVis);
        cancel.setVisibility(cancelVis);
    }

    public void line(View view) {
        board.setCurrentShape(CurrentShape.Line);
    }

    public void rectangle(View view) {
        board.setCurrentShape(CurrentShape.Rectangle);
    }

    public void pickColour(View view) {
        startActivity(new Intent("color_picker"));
    }
}
