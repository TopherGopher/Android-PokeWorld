package com.sterlingpye.pokeworld;


import java.util.ArrayList;
import java.util.Random;

import pokemonClasses.AttackType;
import pokemonClasses.Pokemon;
import pokemonClasses.PokemonType;

import android.os.Bundle;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
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
        
        DatabaseHandler db = new DatabaseHandler(this);
        
        Pokemon p1 = new Pokemon();
        Pokemon p2 = new Pokemon();
        Pokemon p3 = new Pokemon();
        Pokemon p4 = new Pokemon();
        
        AttackType zap = new AttackType("Zap", "Electric", 25, 95);
		AttackType flame = new AttackType("Flame", "Fire", 40, 80);
		AttackType watergun = new AttackType("Watergun", "Water", 20, 99);
		AttackType cut = new AttackType("Cut", "Grass", 10, 99);
		
        ArrayList<AttackType> myAttacks = new ArrayList<AttackType>();		
		myAttacks.add(zap);
		myAttacks.add(flame);
		myAttacks.add(watergun);
		myAttacks.add(cut);
		p1.setAttacks(myAttacks);
		p2.setAttacks(myAttacks);
        p3.setAttacks(myAttacks);
        p4.setAttacks(myAttacks);
        
        
        db.addPokemon(p1);
        db.addPokemon(p2);
        db.addPokemon(p3);
        db.addPokemon(p4);
        		
        
        db.addPokemonType(new PokemonType("Electric", "Water", "Grass"));
        db.addPokemonType(new PokemonType("Fire", "Grass", "Water"));
        db.addPokemonType(new PokemonType("Water", "Fire", "Grass"));
        db.addPokemonType(new PokemonType("Grass", "Electric", "Fire"));
		  
        
        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.battle_main, menu);
        return true;
    }
}
