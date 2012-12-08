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
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

@TargetApi(11)
public class BattleMain extends Activity {

    
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.battle_main);
        
       //declarations
        final TextView textOutput = (TextView)findViewById(R.id.battle_feedback);
        textOutput.setText("Encountered a wild Pokemon!");
        
        final ImageView userPokeImg = (ImageView)findViewById(R.id.user_poke_img);
        final ImageView oppPokeImg = (ImageView)findViewById(R.id.opponent_poke_img);
    	
        final Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);
    	final Animation attack = AnimationUtils.loadAnimation(this, R.anim.attack);
    	final Animation death = AnimationUtils.loadAnimation(this, R.anim.death);
               
        final Button attack1 = (Button)findViewById(R.id.attack1);
        final Button attack2 = (Button)findViewById(R.id.attack2);
        final Button attack3 = (Button)findViewById(R.id.attack3);
        final Button attack4 = (Button)findViewById(R.id.attack4);
        
        final Button fight = (Button)findViewById(R.id.fight_btn);
        final Button run = (Button)findViewById(R.id.run_btn);
        final Button usePokeBall = (Button)findViewById(R.id.use_poke_ball_btn);
        final Button changePoke = (Button)findViewById(R.id.change_poke_btn);
    
       
        
        
       
        
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
		
        Log.d("Completed: ","Complete");
        
        final Pokemon user_pokemon = p1;
        final Pokemon opp_pokemon = p2;
        //end db addition
       
        final ProgressBar user_poke_health = (ProgressBar)findViewById(R.id.user_hp_bar);
        user_poke_health.setProgress(100);
        user_poke_health.setMax(user_pokemon.getHP_total());
        
        final ProgressBar opp_poke_health = (ProgressBar)findViewById(R.id.opp_hp_bar);
        opp_poke_health.setProgress(100);
        opp_poke_health.setMax(opp_pokemon.getHP_total());
        
        final Battle battle = new Battle(user_pokemon, opp_pokemon);
		
		
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
        
        
        
        //final int r = 3;
        
        attack1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Random generator = new Random();
				final int r = generator.nextInt(4);
				userPokeImg.startAnimation(attack);				
				textOutput.setText(user_pokemon.getName() + " used " + user_pokemon.getAttack(0).getName() + "!");
				battle.userAttack(0);
				
				v.postDelayed(new Runnable() {
			        @Override
			        public void run() {
			        	oppPokeImg.startAnimation(shake);
			        	opp_poke_health.setProgress(opp_pokemon.getHP_current());	
			        	if (opp_pokemon.wasEffective(user_pokemon.getAttack(0))){
			        		textOutput.setText("It's super effective!");
			        	}
			        	else if (opp_pokemon.wasNotEffective(user_pokemon.getAttack(0))){
			        		textOutput.setText("It's not very effective");
			        	}
			        }
			    }, 1000L);	
				v.postDelayed(new Runnable() {
			        @Override
			        public void run() {
			        	textOutput.setText(opp_pokemon.getName() + " used " + opp_pokemon.getAttack(r).getName() + "!");
			        	battle.opponentAttack(r);
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
        });     
        attack2.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {	
				Random generator = new Random();
				final int r = generator.nextInt(4);
				userPokeImg.startAnimation(attack);				
				textOutput.setText(user_pokemon.getName() + " used " + user_pokemon.getAttack(1).getName() + "!");
				battle.userAttack(0);
				
				v.postDelayed(new Runnable() {
			        @Override
			        public void run() {
			        	oppPokeImg.startAnimation(shake);
			        	opp_poke_health.setProgress(opp_pokemon.getHP_current());	
			        	if (opp_pokemon.wasEffective(user_pokemon.getAttack(1))){
			        		textOutput.setText("It's super effective!");
			        	}
			        	else if (opp_pokemon.wasNotEffective(user_pokemon.getAttack(1))){
			        		textOutput.setText("It's not very effective");
			        	}
			        }
			    }, 1000L);	
				v.postDelayed(new Runnable() {
			        @Override
			        public void run() {
			        	textOutput.setText(opp_pokemon.getName() + " used " + opp_pokemon.getAttack(r).getName() + "!");
			        	battle.opponentAttack(r);
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
			    }, 4000L);					
			}     	
        });
        attack3.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Random generator = new Random();
				final int r = generator.nextInt(4);
				userPokeImg.startAnimation(attack);				
				textOutput.setText(user_pokemon.getName() + " used " + user_pokemon.getAttack(2).getName() + "!");
				battle.userAttack(0);
				
				v.postDelayed(new Runnable() {
			        @Override
			        public void run() {
			        	oppPokeImg.startAnimation(shake);
			        	opp_poke_health.setProgress(opp_pokemon.getHP_current());	
			        	if (opp_pokemon.wasEffective(user_pokemon.getAttack(2))){
			        		textOutput.setText("It's super effective!");
			        	}
			        	else if (opp_pokemon.wasNotEffective(user_pokemon.getAttack(2))){
			        		textOutput.setText("It's not very effective");
			        	}
			        }
			    }, 1000L);	
				v.postDelayed(new Runnable() {
			        @Override
			        public void run() {
			        	textOutput.setText(opp_pokemon.getName() + " used " + opp_pokemon.getAttack(r).getName() + "!");
			        	battle.opponentAttack(r);
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
			    }, 4000L);
			}     	
        });
        attack4.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Random generator = new Random();
				final int r = generator.nextInt(4);
				userPokeImg.startAnimation(attack);				
				textOutput.setText(user_pokemon.getName() + " used " + user_pokemon.getAttack(3).getName() + "!");
				battle.userAttack(0);
				
				v.postDelayed(new Runnable() {
			        @Override
			        public void run() {
			        	oppPokeImg.startAnimation(shake);
			        	opp_poke_health.setProgress(opp_pokemon.getHP_current());	
			        	if (opp_pokemon.wasEffective(user_pokemon.getAttack(3))){
			        		textOutput.setText("It's super effective!");
			        	}
			        	else if (opp_pokemon.wasNotEffective(user_pokemon.getAttack(3))){
			        		textOutput.setText("It's not very effective");
			        	}
			        }
			    }, 1000L);	
				v.postDelayed(new Runnable() {
			        @Override
			        public void run() {
			        	textOutput.setText(opp_pokemon.getName() + " used " + opp_pokemon.getAttack(r).getName() + "!");
			        	battle.opponentAttack(r);
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
			    }, 4000L);
			}     	
        });
        
        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.battle_main, menu);
        return true;
    }
}
