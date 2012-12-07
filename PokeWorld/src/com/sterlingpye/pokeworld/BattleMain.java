package com.sterlingpye.pokeworld;


import pokemonClasses.Pokemon;

import android.os.Bundle;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

@TargetApi(11)
public class BattleMain extends Activity {

    
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.battle_main);
        
        ImageView userPokeImg = (ImageView)findViewById(R.id.user_poke_img);
        Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);
        userPokeImg.startAnimation(shake);
        
        Button fight = (Button)findViewById(R.id.fight_btn);
        fight.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				
				
			}
        	
        });
        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.battle_main, menu);
        return true;
    }
}
