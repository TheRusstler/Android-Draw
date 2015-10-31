package uk.ac.standrews.cs.cs5041.draw;

import android.app.ActionBar;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.ViewGroup.LayoutParams;
import android.view.View;
import android.widget.RelativeLayout;

public class MainActivity extends ActionBarActivity {

    DrawingPane drawingPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        drawingPane = new DrawingPane(this);

        setContentView(R.layout.activity_main);

        RelativeLayout l = (RelativeLayout)findViewById(R.id.drawingLayout);
        l.addView(drawingPane);
    }

    public void clear(View view) {
        drawingPane.lines.clear();
        drawingPane.invalidate();
    }
}
