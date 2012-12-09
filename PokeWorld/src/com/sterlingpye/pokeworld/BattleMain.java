package com.sterlingpye.pokeworld;


import java.util.ArrayList;
import java.util.Random;

import pokemonClasses.AttackType;
import pokemonClasses.Battle;
import pokemonClasses.Pokemon;
import pokemonClasses.PokemonType;
import pokemonClasses.User;

import android.os.Bundle;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

@TargetApi(11)
public class BattleMain extends Activity {

	//Sterling - when poke variables change, they change in the database
	//Work on geolocation randomizer integration
	//Pye - Finish out Fight sequence
	Battle battle;
	Pokemon user_pokemon;
    Pokemon opp_pokemon;
    
	@Override
    public void onCreate(Bundle savedInstanceState) {
        Log.d("In onCreate()", "Successfully got to function");
		super.onCreate(savedInstanceState);
        setContentView(R.layout.battle_main);
        
      //declarations
        final TextView textOutput = (TextView)findViewById(R.id.battle_feedback);
        
        
        
    	
        
               
        final Button attack1 = (Button)findViewById(R.id.attack1);
        final Button attack2 = (Button)findViewById(R.id.attack2);
        final Button attack3 = (Button)findViewById(R.id.attack3);
        final Button attack4 = (Button)findViewById(R.id.attack4);
        
        final Button fight = (Button)findViewById(R.id.fight_btn);
        final Button run = (Button)findViewById(R.id.run_btn);
        final Button usePokeBall = (Button)findViewById(R.id.use_poke_ball_btn);
        final Button changePoke = (Button)findViewById(R.id.change_poke_btn);
        
        final ProgressBar user_poke_health = (ProgressBar)findViewById(R.id.user_hp_bar);
        final ProgressBar opp_poke_health = (ProgressBar)findViewById(R.id.opp_hp_bar);
        
        DatabaseHandler db;
        db = new DatabaseHandler(this);
        ArrayList<Pokemon> pokes = db.getAllPokemon();
        Log.d("pokes", pokes.toString());        
        user_pokemon = pokes.get(1);
        opp_pokemon = pokes.get(2);

        
        Log.d("Got past the DB handler","Hooray!");
        
        battle = new Battle(user_pokemon, opp_pokemon);
        Log.d("Got past the battle function","YES!");
        
        
        
        //Begin the battle
        textOutput.setText("Encountered a wild Pokemon!");
        
        //set the HP
        user_poke_health.setProgress(100);
        user_poke_health.setMax(user_pokemon.getHP_total());
        
        opp_poke_health.setProgress(100);
        opp_poke_health.setMax(opp_pokemon.getHP_total());
        
        /*
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
        
        Log.d("Inserting Pokemon: ", "Inserting...");
        
        
        db.addPokemon(p1);
        db.addPokemon(p2);
        db.addPokemon(p3);
        db.addPokemon(p4);
        		
        Log.d("Inserting PokemonType: ", "Inserting...");
        db.addPokemonType(new PokemonType("Electric", "Water", "Grass"));
        db.addPokemonType(new PokemonType("Fire", "Grass", "Water"));
        db.addPokemonType(new PokemonType("Water", "Fire", "Grass"));
        db.addPokemonType(new PokemonType("Grass", "Electric", "Fire"));
		
        Log.d("Completed: ","Complete");*/
        
        
        
        //end db addition
       
        
        
        
		
		
        fight.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				run.setVisibility(android.view.View.INVISIBLE);
				usePokeBall.setVisibility(android.view.View.INVISIBLE);
				changePoke.setVisibility(android.view.View.INVISIBLE);
				fight.setVisibility(android.view.View.INVISIBLE);
				attack1.setVisibility(android.view.View.VISIBLE);
		        attack2.setVisibility(android.view.View.VISIBLE);
		        attack3.setVisibility(android.view.View.VISIBLE);
		        attack4.setVisibility(android.view.View.VISIBLE);
		        attack1.setText(user_pokemon.getAttack(0).getName());
		        attack2.setText(user_pokemon.getAttack(1).getName());
		        attack3.setText(user_pokemon.getAttack(2).getName());
		        attack4.setText(user_pokemon.getAttack(3).getName());
		        textOutput.setText("Choose your attack");
		        
			}     	
        });
        
        
    }

    public OnClickListener attackButtonClickListener(View v) {
				
		fightSequence(v);				
    	return null;
	}

   
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.battle_main, menu);
        return true;
    }
	public void fightSequence(View v)
	{
		//this is running the animation for us for right now for the battle sequence
		final ImageView userPokeImg = (ImageView)findViewById(R.id.user_poke_img);
        final ImageView oppPokeImg = (ImageView)findViewById(R.id.opponent_poke_img);
        
        final Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);
    	final Animation attack = AnimationUtils.loadAnimation(this, R.anim.attack);
    	final Animation death = AnimationUtils.loadAnimation(this, R.anim.death);
    	
    	final TextView textOutput = (TextView)findViewById(R.id.battle_feedback);
    	final ProgressBar user_poke_health = (ProgressBar)findViewById(R.id.user_hp_bar);
        final ProgressBar opp_poke_health = (ProgressBar)findViewById(R.id.opp_hp_bar);
       
    	userPokeImg.startAnimation(attack);	
    	int userAttackNumber = Integer.parseInt(((Button)v).getContentDescription().toString())-1;
    	textOutput.setText(user_pokemon.getName() + " used " + user_pokemon.getAttack(userAttackNumber).getName() + "!"); 
		battle.userAttack(userAttackNumber);
		
		v.postDelayed(new Runnable() {
	        @Override
	        public void run() {
	        	
	        	oppPokeImg.startAnimation(shake);
	        	opp_poke_health.setProgress(opp_pokemon.getHP_current());	
	        	textOutput.setText(battle.getFightResult());
	        }
	    }, 1000L);	
		
		v.postDelayed(new Runnable() {
	        @Override
	        public void run() {
	        	
	        	int attackNum = battle.opponentAttack();
	        	textOutput.setText(opp_pokemon.getName() + " used " + opp_pokemon.getAttack(attackNum).getName() + "!");
	        	oppPokeImg.startAnimation(attack);
	        }
	    }, 3000L);
		
		v.postDelayed(new Runnable() {
	        @Override
	        public void run() {
	        	if (user_pokemon.getHP_current() <= 0){
	        		userPokeImg.startAnimation(death);
	        	}
	        	else{
	        		userPokeImg.startAnimation(shake);
	        	}
	        	user_poke_health.setProgress(user_pokemon.getHP_current());	
	        }
	    }, 5000L);
			

	}
}
    

