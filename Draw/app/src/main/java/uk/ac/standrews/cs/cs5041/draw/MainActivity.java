package uk.ac.standrews.cs.cs5041.draw;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

public class MainActivity extends ActionBarActivity {

    DrawingPane drawingPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        drawingPane = new DrawingPane(this);
        setContentView(drawingPane);
    }
}
