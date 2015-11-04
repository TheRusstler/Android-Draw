package uk.ac.standrews.cs.cs5041.draw;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

public class MainActivity extends ActionBarActivity {

    DrawingPane board;
    Button move, scale, rotate, confirm, cancel;
    View colourPickerButton;

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
        rotate = (Button) findViewById(R.id.rotateButton);
        confirm = (Button) findViewById(R.id.confirmButton);
        cancel = (Button) findViewById(R.id.cancelButton);
        colourPickerButton = findViewById(R.id.colourPickerButton);

        updateButtons();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(data.getExtras().containsKey("R")) {
            int r = data.getIntExtra("R", Color.BLACK);
            colourPickerButton.setBackground(new ColorDrawable(r));
            colourPickerButton.invalidate();
            board.setColor(r);
        }
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

    public void rotate(View view) {
        board.setMode(DrawingMode.Rotate);
    }

    public void cancel(View view) {
        board.cancelNewObject();
    }

    public void confirm(View view) {
        board.confirmNewObject();
    }

    void updateButtons() {
        int moveVis = View.GONE, scaleVis = View.GONE, rotateVis = View.GONE, confirmVis = View.GONE, cancelVis = View.GONE;

        switch (board.getMode()) {
            case Draw:
                break;
            case Drawn:
                moveVis = View.VISIBLE;
                scaleVis = View.VISIBLE;
                rotateVis = View.VISIBLE;
                confirmVis = View.VISIBLE;
                cancelVis = View.VISIBLE;
                break;
            case Move:
            case Rotate:
            case Scale:
                confirmVis = View.VISIBLE;
                cancelVis = View.VISIBLE;
                break;
        }

        move.setVisibility(moveVis);
        scale.setVisibility(scaleVis);
        rotate.setVisibility(rotateVis);
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
        startActivityForResult(new Intent(MainActivity.this, ColourPicker.class), 0);
    }

    public void circle(View view) {
        board.setCurrentShape(CurrentShape.Circle);
    }
}