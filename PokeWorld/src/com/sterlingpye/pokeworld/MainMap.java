package com.sterlingpye.pokeworld;

import java.util.ArrayList;
import java.util.Random;

import pokemonClasses.AttackType;
import pokemonClasses.Pokemon;
import pokemonClasses.PokemonType;



import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;

public class MainMap extends Activity {
	 
	private static final int DIALOG_ALERT = 10;
	private boolean fights = true;
	private DatabaseHandler db;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_map);
       
        Button launchMap = (Button)findViewById(R.id.launchBattle);
        launchMap.setText("Manually Launch a Battle Dialog");
        launchMap.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
		        showDialog(DIALOG_ALERT);
			}
		});        
        
        
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
        Button showInventory = (Button)findViewById(R.id.showInventory);
        showInventory.setVisibility(android.view.View.INVISIBLE);
        
        //hide this button if nothing is in your inventory
        if(db.getCurrentArsenal().isEmpty()) {
        	showInventory.setVisibility(android.view.View.INVISIBLE);
        }
        else
        {
        	ArrayList<Pokemon> temp = db.getCurrentArsenal();
        	String inventory = "You've caught:\n";
        	for(int i=0; i<temp.size(); i++)
        	{
        		inventory += temp.get(i).getName()+"\n";
        	}
        	final String inventoryString = inventory.substring(0, inventory.length()-1);
        	showInventory.setVisibility(android.view.View.VISIBLE);
        	showInventory.setOnClickListener(new View.OnClickListener() {
    			@Override
    			public void onClick(View v) {
    				Toast.makeText(getApplicationContext(), inventoryString,
    				Toast.LENGTH_LONG).show();
    			}
    		});
        }
        
        
        
        
        
        LocationManager lm = (LocationManager)getSystemService(Context.LOCATION_SERVICE); 
        LocationListener ll = new mylocationlistener();
        
        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, ll);
        
        
        //randomly launch a dialog
        Handler handler = new Handler();
        Random r = new Random();
        int waitTime = 0;
        //while(fights)
        //{
        	waitTime = r.nextInt(5); //pick 1 through 20 in order to have a random battle using time delays
        	waitTime = waitTime*1000; //waitTime is in miliseconds, must multiply by 1000 to convert it up to seconds
        	handler.postDelayed(new Runnable() {
        	@Override
	        public void run() {
	        	showDialog(DIALOG_ALERT);
	        }
        	}, waitTime);
        //}
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main_map, menu);
        return true;
    }
    
    public void onWindowFocusChanged (boolean hasFocus)
    {
    	Button showInventory = (Button)findViewById(R.id.showInventory);
        showInventory.setVisibility(android.view.View.INVISIBLE);
        
        //hide this button if nothing is in your inventory
        if(db.getCurrentArsenal().isEmpty()) {
        	showInventory.setVisibility(android.view.View.INVISIBLE);
        }
        else
        {
        	ArrayList<Pokemon> temp = db.getCurrentArsenal();
        	String inventory = "You've caught:\n";
        	for(int i=0; i<temp.size(); i++)
        	{
        		inventory += temp.get(i).getName()+"\n";
        	}
        	final String inventoryString = inventory.substring(0, inventory.length()-1);
        	showInventory.setVisibility(android.view.View.VISIBLE);
        	showInventory.setOnClickListener(new View.OnClickListener() {
    			@Override
    			public void onClick(View v) {
    				Toast.makeText(getApplicationContext(), inventoryString,
    				Toast.LENGTH_LONG).show();
    			}
    		});
        }
    	
    }
  //doing in line class to take advantage of the onchange listeners and the text fields more directly
    class mylocationlistener implements LocationListener
    {

		@Override
		public void onLocationChanged(Location location) {
			// TODO Auto-generated method stub
			if(location!=null)
			{
				double pLong = location.getLongitude();
				double pLat = location.getLatitude();
				
				//textLat.setText(Double.toString(pLat));
				//textLong.setText(Double.toString(pLong));
			}
		}

		@Override
		public void onProviderDisabled(String provider) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onProviderEnabled(String provider) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onStatusChanged(String provider, int status,
				Bundle extras) {
			// TODO Auto-generated method stub
			
		}
    	
    }//end myLocationListener

	  @Override
	  protected Dialog onCreateDialog(int id) {
	    switch (id) {
	    case DIALOG_ALERT:
	      // Create out AlterDialog
	      Builder builder = new AlertDialog.Builder(this);
	      builder.setMessage("You've encounted a wild Pokemon!");
	      builder.setCancelable(true);
	      builder.setPositiveButton("Fight!", new OkOnClickListener());
	      builder.setNegativeButton("Run!", new CancelOnClickListener());
	      AlertDialog dialog = builder.create();
	      dialog.show();
	    }
	    return super.onCreateDialog(id);
	  }

	  private final class CancelOnClickListener implements
	      DialogInterface.OnClickListener {
	    public void onClick(DialogInterface dialog, int which) {
	      Toast.makeText(getApplicationContext(), "You ran away...coward...",
	          Toast.LENGTH_LONG).show();
	      //can flip fights to false here in order to stop the randomizer
	    }
	  }

	  private final class OkOnClickListener implements
	      DialogInterface.OnClickListener {
	    public void onClick(DialogInterface dialog, int which) {
	    	Intent myIntent = new Intent(MainMap.this, BattleMain.class);
	        startActivity(myIntent);
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
