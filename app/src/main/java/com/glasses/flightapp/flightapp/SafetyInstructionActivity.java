package com.glasses.flightapp.flightapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import com.jsibbold.zoomage.ZoomageView;

public class SafetyInstructionActivity extends AppCompatActivity {
    public static final String EXTRA_AIRCRAFT = "aircraft";

    private String aircraft;
    private ZoomageView image;
    private int page = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_safety_instruction);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = this.getIntent();
        aircraft = intent.getStringExtra(EXTRA_AIRCRAFT);

        setTitle(getString(R.string.title_activity_safety_instruction_suffix, aircraft));

        image = findViewById(R.id.safety_instruction);
        image.setImageResource(getResources().getIdentifier(aircraft.toLowerCase() + "_1", "drawable", getPackageName()));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.safety_instructions, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.safety_toggle_page:
                if(page == 1) page = 2;
                else page = 1;

                image.setImageResource(getResources().getIdentifier(aircraft.toLowerCase() + "_" + page, "drawable", getPackageName()));
                image.reset(false);

                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
