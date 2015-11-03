package uk.ac.standrews.cs.cs5041.draw;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;

public class ColourPicker extends ActionBarActivity {

    Intent i;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_colour_picker);
        i = getIntent();
    }

    public void done(View view) {
        i.putExtra("R",200);
        setResult(RESULT_OK, i);
        finish();
    }
}
