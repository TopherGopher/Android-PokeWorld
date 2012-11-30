package com.sterlingpye.pokeworld;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class MainMap extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_map);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main_map, menu);
        return true;
    }
}
