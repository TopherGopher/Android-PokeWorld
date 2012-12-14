package com.sterlingpye.pokeworld;


import java.util.ArrayList;
import java.util.Random;

import pokemonClasses.AttackType;
import pokemonClasses.Battle;
import pokemonClasses.Pokemon;
import pokemonClasses.PokemonType;

import android.os.Bundle;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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

	Battle battle;
	Pokemon user_pokemon;
    Pokemon opp_pokemon;
    private DatabaseHandler db;
    ImageView userPokeImg;
    ImageView oppPokeImg;
    
	@Override
    public void onCreate(Bundle savedInstanceState) {
        Log.d("BattleMain.onCreate()", "Successfully got to function");
		super.onCreate(savedInstanceState);
        setContentView(R.layout.battle_main);
        
      //declarations
        final TextView textOutput = (TextView)findViewById(R.id.battle_feedback);
        userPokeImg = (ImageView)findViewById(R.id.user_poke_img);
        oppPokeImg = (ImageView)findViewById(R.id.opponent_poke_img);
        
        final Button attack1 = (Button)findViewById(R.id.attack1);
        final Button attack2 = (Button)findViewById(R.id.attack2);
        final Button attack3 = (Button)findViewById(R.id.attack3);
        final Button attack4 = (Button)findViewById(R.id.attack4);
        
        final Button fight = (Button)findViewById(R.id.fight_btn);
        final Button run = (Button)findViewById(R.id.run_btn);
       // final Button usePokeBall = (Button)findViewById(R.id.use_poke_ball_btn);
       // final Button changePoke = (Button)findViewById(R.id.change_poke_btn);
        
        final ProgressBar user_poke_health = (ProgressBar)findViewById(R.id.user_hp_bar);
        final ProgressBar opp_poke_health = (ProgressBar)findViewById(R.id.opp_hp_bar);
        
        Log.d("BattleMain.onCreate()","About to call Database Handler");
        db = new DatabaseHandler(this);
  
        try
        {
        	Pokemon temp = db.getPokemon("Pikachu");
        	//don't play god...please?
        	Log.d("End of try","End of try");
        }
        catch(RuntimeException e)
        {
        	//couldn't grab an object from the database - CREATE A DATABASE!
        	playGod(); //only on first run
        	Log.d("caught",e.toString());
        }
        
        Log.d("BattleMain.onCreate()", "Got past DB caller");
        
        ArrayList<Pokemon> pokes = db.getAllPokemon();
        Log.d("pokesList", pokes.get(0).getName());
        Log.d("pokeslist", pokes.get(1).getName());
        Log.d("pokeslist", pokes.get(2).getName());
        Log.d("pokeslist", pokes.get(3).getName());
        
        Random generator = new Random();
		int randPokemon = generator.nextInt(3);
		if(randPokemon==2){randPokemon=3;}
		
        opp_pokemon = pokes.get(randPokemon);
        Log.d("randPokemon", randPokemon+" "+opp_pokemon.getName());
        user_pokemon = pokes.get(2); //this is supposed to be pikachu!
        Log.d("userPokemon", user_pokemon.getName());
        //set opp_pokemon image
        switch(randPokemon)
        {
        case 0:
        	oppPokeImg.setImageResource(R.drawable.bulbasaur);
        	break;
        case 1:
        	oppPokeImg.setImageResource(R.drawable.charmander);
        	break;
        case 2:
        	oppPokeImg.setImageResource(R.drawable.pikachu);
        	break;
        case 3:
        	oppPokeImg.setImageResource(R.drawable.squirtle);
        	break;
        default:
        	break;
        }
        
        //set user_pokemon image
        userPokeImg.setImageResource(R.drawable.pikachu);
        
        
        battle = new Battle(user_pokemon, opp_pokemon);
        Log.d("Got past the battle function","YES!");
        
        
        
        //Begin the battle
        textOutput.setText("Encountered a wild Pokemon!");
        
 
        //set the HP
        user_poke_health.setProgress(user_pokemon.getHP_current());
        user_poke_health.setMax(user_pokemon.getHP_total());
        
        opp_poke_health.setProgress(opp_pokemon.getHP_current());
        opp_poke_health.setMax(opp_pokemon.getHP_total());
        
        
        
    }

	public OnClickListener runButtonClickListener(View v) {
		final Context context = this;
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
 
			// set title
			alertDialogBuilder.setTitle("You ran!");
 
			// set dialog message
			alertDialogBuilder
				.setMessage("Got away safely!")
				.setCancelable(false)
				.setPositiveButton("Oh good",new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog,int id) {
						// if this button is clicked, close
						// current activity
						BattleMain.this.finish();
					}
				  });
				// create alert dialog
				AlertDialog alertDialog = alertDialogBuilder.create();
 
				// show it
				alertDialog.show();
				return null;
	}
	
    public OnClickListener attackButtonClickListener(View v) {
		hideAttacks(v);		
		fightSequence(v);
    	return null;
	}
    
    public OnClickListener fightButtonClickListener (View v){
    	hideMenu(v);
    	revealAttacks(v);
    	return null;
    }
    
    
   private void hideMenu (View v){
	   Button fight = (Button)findViewById(R.id.fight_btn);
       Button run = (Button)findViewById(R.id.run_btn);
      // Button usePokeBall = (Button)findViewById(R.id.use_poke_ball_btn);
      // Button changePoke = (Button)findViewById(R.id.change_poke_btn);
       
       run.setVisibility(android.view.View.INVISIBLE);
      // usePokeBall.setVisibility(android.view.View.INVISIBLE);
       //changePoke.setVisibility(android.view.View.INVISIBLE);
       fight.setVisibility(android.view.View.INVISIBLE);
   }
   
   private void revealMenu (View v){
	   Button fight = (Button)findViewById(R.id.fight_btn);
       Button run = (Button)findViewById(R.id.run_btn);
       //Button usePokeBall = (Button)findViewById(R.id.use_poke_ball_btn);
       //Button changePoke = (Button)findViewById(R.id.change_poke_btn);
       
       TextView textOutput = (TextView)findViewById(R.id.battle_feedback);
       
       run.setVisibility(android.view.View.VISIBLE);
      // usePokeBall.setVisibility(android.view.View.VISIBLE);
       //changePoke.setVisibility(android.view.View.VISIBLE);
       fight.setVisibility(android.view.View.VISIBLE);
       
       textOutput.setText("Choose an action");
   }
    
   private void hideAttacks (View v){
       Button attack1 = (Button)findViewById(R.id.attack1);
       Button attack2 = (Button)findViewById(R.id.attack2);
       Button attack3 = (Button)findViewById(R.id.attack3);
       Button attack4 = (Button)findViewById(R.id.attack4);
       attack1.setVisibility(android.view.View.INVISIBLE);
       attack2.setVisibility(android.view.View.INVISIBLE);
       attack3.setVisibility(android.view.View.INVISIBLE);
       attack4.setVisibility(android.view.View.INVISIBLE);
   }
    
	private void revealAttacks(View v) {
		Button attack1 = (Button)findViewById(R.id.attack1);
        Button attack2 = (Button)findViewById(R.id.attack2);
        Button attack3 = (Button)findViewById(R.id.attack3);
        Button attack4 = (Button)findViewById(R.id.attack4);
        
        TextView textOutput = (TextView)findViewById(R.id.battle_feedback);       
        
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

	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.battle_main, menu);
        return true;
    }
	//TODO - database imports here
	private void onLose(){
		user_pokemon.setHP_current(user_pokemon.getHP_total());
		db.updatePokemon(user_pokemon);
		final Context context = this;
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
 
			// set title
			alertDialogBuilder.setTitle("You lost!");
 
			// set dialog message
			alertDialogBuilder
				.setMessage(user_pokemon.getName() + " fainted!"+" ...Crap...")
				.setCancelable(false)
				.setPositiveButton("Run!",new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog,int id) {
						// if this button is clicked, close
						// current activity
						BattleMain.this.finish();
					}
				  });
				// create alert dialog
				AlertDialog alertDialog = alertDialogBuilder.create();
 
				// show it
				alertDialog.show();
	}
	
	private void onWin(){
		db.addToArsenal(opp_pokemon);
		final Context context = this;
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
 
			// set title
			alertDialogBuilder.setTitle("You won!");
 
			// set dialog message
			alertDialogBuilder
				.setMessage(opp_pokemon.getName() + " has been added to your inventory")
				.setCancelable(false)
				.setPositiveButton("Continue",new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog,int id) {
						// if this button is clicked, close
						// current activity
						BattleMain.this.finish();
					}
				  });
				// create alert dialog
				AlertDialog alertDialog = alertDialogBuilder.create();
 
				// show it
				alertDialog.show();
	}

   
	
	public void fightSequence(final View v)
	{
		//this is running the animation for us for right now for the battle sequence
        
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
		db.updatePokemon(opp_pokemon);
    	db.updatePokemon(user_pokemon);
    	
		v.postDelayed(new Runnable() {
	        @Override
	        public void run() {
	        	if (opp_pokemon.getHP_current() <= 0){
	        		opp_poke_health.setProgress(opp_pokemon.getHP_current());
	        		oppPokeImg.startAnimation(death);
	        		onWin();
	        	}
	        	else{
	        		oppPokeImg.startAnimation(shake);
	        		opp_poke_health.setProgress(opp_pokemon.getHP_current());
	        		db.updatePokemon(opp_pokemon);
		        	db.updatePokemon(user_pokemon);
	        	}	        		
	        	textOutput.setText(battle.getFightResult());
	        	
	        }
	    }, 1000L);	
		if (opp_pokemon.getHP_current() >= 0){
			v.postDelayed(new Runnable() {
		        @Override
		        public void run() {
		        	
		        	int attackNum = battle.opponentAttack();
		        	textOutput.setText(opp_pokemon.getName() + " used " + opp_pokemon.getAttack(attackNum).getName() + "!");
		        	oppPokeImg.startAnimation(attack);
		        	db.updatePokemon(opp_pokemon);
		        	db.updatePokemon(user_pokemon);
		        }
		    }, 3000L);
			
			v.postDelayed(new Runnable() {
		        @Override
		        public void run() {
		        	if (user_pokemon.getHP_current() <= 0){
			        	user_poke_health.setProgress(user_pokemon.getHP_current());
			        	userPokeImg.startAnimation(death);
			        	onLose();
		        	}
		        	else{
		        		userPokeImg.startAnimation(shake);
			        	user_poke_health.setProgress(user_pokemon.getHP_current());
			        	db.updatePokemon(opp_pokemon);
			        	db.updatePokemon(user_pokemon);
		        	}
		        	
		        	revealMenu(v);
		        }
		    }, 5000L);
		}
			

	}
	private void playGod()
	{
		//create the pokemon
        Pokemon squirtle = new Pokemon("Squirtle");
        Pokemon charmander = new Pokemon("Charmander");
        Pokemon bulbasaur = new Pokemon("Bulbasaur");
        Pokemon pikachu = new Pokemon("Pikachu");
        
        //creating attack types
        AttackType zap = new AttackType("Zap", "Electric", 25, 95);
        AttackType thunderbolt = new AttackType("Thunderbolt", "Electric", 35, 80);
		AttackType flame = new AttackType("Flame", "Fire", 40, 80);
		AttackType ember = new AttackType("Ember", "Fire", 25, 90);
		AttackType watergun = new AttackType("Watergun", "Water", 20, 99);
		AttackType splash = new AttackType("Splash", "Water", 5, 99);
		AttackType cut = new AttackType("Cut", "Grass", 10, 99);
		AttackType whip = new AttackType("Whip", "Grass", 35, 90);
		AttackType tackle = new AttackType("Tackle", "Normal", 20, 90);
		AttackType quickAttack = new AttackType("Quick Attack", "Normal", 15, 99);
		
		//link the attack types to each pokemon
        ArrayList<AttackType> bulbAttacks = new ArrayList<AttackType>();
        ArrayList<AttackType> sqrAttacks = new ArrayList<AttackType>();
        ArrayList<AttackType> charAttacks = new ArrayList<AttackType>();
        ArrayList<AttackType> pikAttacks = new ArrayList<AttackType>();
        bulbAttacks.add(whip);
		bulbAttacks.add(tackle);
		bulbAttacks.add(quickAttack);
		bulbAttacks.add(cut);
		
		sqrAttacks.add(tackle);
		sqrAttacks.add(splash);
		sqrAttacks.add(watergun);
		sqrAttacks.add(quickAttack);
		
		charAttacks.add(ember);
		charAttacks.add(flame);
		charAttacks.add(quickAttack);
		charAttacks.add(tackle);	
					
		pikAttacks.add(zap);
		pikAttacks.add(quickAttack);
		pikAttacks.add(tackle);
		pikAttacks.add(thunderbolt);
		
		squirtle.setAttacks(sqrAttacks);
		charmander.setAttacks(charAttacks);
        bulbasaur.setAttacks(bulbAttacks);
        pikachu.setAttacks(pikAttacks);
        

        //save the new pokemon types
        Log.d("Inserting PokemonType: ", "Inserting...");
        PokemonType electric = new PokemonType("Electric", "Water", "Grass");
        PokemonType fire = new PokemonType("Fire", "Grass", "Water");
        PokemonType water = new PokemonType("Water", "Fire", "Electric");
        PokemonType grass = new PokemonType("Grass", "Electric", "Fire");
        PokemonType normal = new PokemonType("Normal", "", "");
        db.addPokemonType(electric);
        db.addPokemonType(fire);
        db.addPokemonType(water);
        db.addPokemonType(grass);
        db.addPokemonType(normal);
        
        pikachu.setType(electric);
        squirtle.setType(water);
        charmander.setType(fire);
        bulbasaur.setType(grass);
        
        //save the pokemon
        Log.d("Inserting Pokemon: ", "Inserting...");
        db.addPokemon(squirtle);
        db.addPokemon(charmander);
        db.addPokemon(bulbasaur);
        db.addPokemon(pikachu);
	}
}
    

