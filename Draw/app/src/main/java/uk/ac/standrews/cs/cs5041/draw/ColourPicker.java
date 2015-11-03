package uk.ac.standrews.cs.cs5041.draw;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;

public class ColourPicker extends ActionBarActivity {

    Intent i;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_colour_picker);
        i = getIntent();
    }

    public int color = Color.BLACK;

    public void selectColour(View view) {
        ColorDrawable background = (ColorDrawable)view.getBackground();
        color = background.getColor();

        i.putExtra("R", color);
        setResult(RESULT_OK, i);
        finish();
    }

}
