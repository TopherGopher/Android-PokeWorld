package com.sterlingpye.pokeworld;
import pokemonClasses.Pokemon;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import pokemonClasses.AttackType;
import pokemonClasses.PokemonType;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHandler extends SQLiteOpenHelper {
	 
    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 15;
 
    // Database Name
    private static final String DATABASE_NAME = "pokeworld";
 
    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
 
    //SQLiteDatabase db = this.getWritableDatabase();
    
    
    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase tempdb) {
    	SQLiteDatabase db = tempdb;
    	Log.d("DatabaseHandler.onCreate()", "Started..");
    	db.execSQL("CREATE TABLE pokemon (id INTEGER PRIMARY KEY, name TEXT, type TEXT, hp_current INTEGER, hp_total INTEGER, xp_current INTEGER, xp_total INTEGER, level INTEGER, gender TEXT)");
        db.execSQL("CREATE TABLE pokemon_type (id INTEGER PRIMARY KEY, name TEXT, weak_against TEXT, strong_against TEXT)");
        db.execSQL("CREATE TABLE attack_type (id INTEGER PRIMARY KEY, pokemon_name TEXT, name TEXT, type TEXT, damage INTEGER, accuracy INTEGER)"); //get rid of pokemon name if possible
        db.execSQL("CREATE TABLE current_arsenal (id INTEGER PRIMARY KEY, pokemon_fk_name TEXT)");
        Log.d("DatabaseHandler.onCreate()", "Table creation worked!");
        
        
		
        Log.d("DatabaseHandler.onCreate()","Completed Successfullly");
    }
 
    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase tempdb, int oldVersion, int newVersion) {
        Log.d("DatabaseHandler.onUpgrade()", "Started...");
    	SQLiteDatabase db = tempdb;
    	
    	// Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS pokemon");
        db.execSQL("DROP TABLE IF EXISTS pokemon_type");
        db.execSQL("DROP TABLE IF EXISTS attack_type");
        db.execSQL("DROP TABLE IF EXISTS current_arsenal");
        Log.d("DatabaseHandler.onUpgrade()", "Tables successfully dropped");
        // Create tables again
        onCreate(db);
    }
    
    
    
    public void addPokemon(Pokemon temp)
    {
    	Log.d("addPokemon", "adding "+temp.getName());
    	SQLiteDatabase tempdb = this.getWritableDatabase();
    	Log.d("Writable DB", "Successfully acquired");
    	ContentValues values = new ContentValues();
    	values.put("id", temp.getId());
    	values.put("name", temp.getName());
    	values.put("type", temp.getType().getName()); //string reference to type
    	values.put("hp_current", temp.getHP_current());
    	values.put("hp_total", temp.getHP_total());
    	values.put("xp_current", temp.getXP_Current());
    	values.put("xp_total", temp.getXP_total());
    	values.put("level", temp.getLevel());
    	values.put("gender", temp.getGender());
    	tempdb.insert("pokemon", null, values);
    	tempdb.close(); //Close the DB connection
    	Log.d("Inserting - ", temp.getName());
    	addAttackTypes(temp.getAttacks(), temp);
    }
    
    public void addPokemonType(PokemonType temp)
    {
    	SQLiteDatabase tempdb = this.getWritableDatabase();
    	ContentValues values = new ContentValues();
    	values.put("name", temp.getName());
    	values.put("weak_against", temp.getWeakAgainst());
    	values.put("strong_against", temp.getStrongAgainst());
    	tempdb.insert("pokemon_type", null, values);
    	tempdb.close();
    }
    
    public void addAttackType(AttackType tempAttackType, Pokemon tempPoke)
    {
    	SQLiteDatabase tempdb = this.getWritableDatabase();
    	ContentValues values = new ContentValues();
		values.put("pokemon_name", tempPoke.getName()); //remove me if possible
    	values.put("name", tempAttackType.getName());
    	values.put("type", tempAttackType.getType());
    	values.put("damage", tempAttackType.getDamage());
    	values.put("accuracy", tempAttackType.getAccuracy());
    	tempdb.insert("attack_type", null, values);
    	tempdb.close();
    	
    }
    
    public void addAttackTypes(ArrayList<AttackType> tempList, Pokemon tempPoke)
    {
    	SQLiteDatabase tempdb = this.getWritableDatabase();
    	Log.d("addAttackTypes", "Got past the DB");
    	ContentValues values = new ContentValues();
    	int count=0;
    	while(tempList.size()>count)
    	{
    		AttackType temp = tempList.get(count);
    		values.put("pokemon_name", tempPoke.getName()); //remove me if possible
        	values.put("name", temp.getName());
        	values.put("type", temp.getType());
        	values.put("damage", temp.getDamage());
        	values.put("accuracy", temp.getAccuracy());
        	tempdb.insert("attack_type", null, values);
    		count++;
    	}
    	tempdb.close();
    }
    
    public Pokemon getPokemon(String name)
    {
    	Pokemon temp = new Pokemon();
    	SQLiteDatabase tempdb = this.getWritableDatabase();
    	Cursor cursor = tempdb.rawQuery("SELECT * FROM pokemon WHERE name='"+name+"'", null); 
    	// looping through all rows and adding to list
		cursor.moveToFirst();
		temp.setName(cursor.getString(1));
		temp.setType(getPokemonType(cursor.getString(2)));
		temp.setHP_current(cursor.getInt(3));
		temp.setHP_total(cursor.getInt(4));
		temp.setXP_Current(cursor.getInt(5));
		temp.setXP_total(cursor.getInt(6));
		temp.setLevel(cursor.getInt(7));
		temp.setGender(cursor.getString(8));
		tempdb.close();
		cursor.close();
		temp.setAttacks(getAllAttackTypesForPokemon(temp.getName()));
		return temp;
    }
    
    
    public PokemonType getPokemonType(String name)
    {
    	PokemonType temp = new PokemonType();
    	SQLiteDatabase tempdb = this.getWritableDatabase();
    	Cursor cursor = tempdb.rawQuery("SELECT * FROM pokemon_type WHERE name='"+name+"'", null);
    	 // looping through all rows and adding to list
        cursor.moveToFirst();
        temp.setName(cursor.getString(1));
        temp.setStrongAgainst(cursor.getString(2));
        temp.setWeakAgainst(cursor.getString(3));
        tempdb.close();
        return temp;
    }
    
    public AttackType getAttackType(Pokemon tempPoke)
    {
    	AttackType temp = new AttackType();
    	SQLiteDatabase tempdb = this.getWritableDatabase();
    	Cursor cursor = tempdb.rawQuery("SELECT * FROM attack_type WHERE pokemon_name='"+tempPoke.getName()+"'", null);
    	 // looping through all rows and adding to list
        cursor.moveToFirst();
        temp.setName(cursor.getString(1));
        temp.setType(cursor.getString(2));
        temp.setDamage(cursor.getInt(3));
        temp.setAccuracy(cursor.getInt(4));
        tempdb.close();
        return temp;
    }
    
    public ArrayList<Pokemon> getAllPokemon()
    {
    	ArrayList<Pokemon> pokemonList = new ArrayList<Pokemon>();
    	SQLiteDatabase tempdb = this.getWritableDatabase();
    	//Select All Query
    	Cursor cursor = tempdb.rawQuery("SELECT * FROM pokemon ORDER BY name ASC", null);
    	 // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Pokemon temp = new Pokemon();
                temp.setName(cursor.getString(1));
                temp.setType(getPokemonType(cursor.getString(2)));
                Log.d("Successfully retrieved type of",cursor.getString(2));
                temp.setHP_current(cursor.getInt(3));
                temp.setHP_total(cursor.getInt(4));
                temp.setXP_Current(cursor.getInt(5));
                temp.setXP_total(cursor.getInt(6));
                temp.setLevel(cursor.getInt(7));
                temp.setGender(cursor.getString(8));
                temp.setAttacks(getAllAttackTypesForPokemon(cursor.getString(1)));
                Log.d("Successfully retrieved attacks for", cursor.getString(1));
                
                // Adding contact to list
                pokemonList.add(temp);
            } while (cursor.moveToNext());
        }
        cursor.close();
        tempdb.close();
        return pokemonList;
    }
    public ArrayList<PokemonType> getAllPokemonTypes()
    {
    	ArrayList<PokemonType> pokemonTypeList = new ArrayList<PokemonType>();
    	SQLiteDatabase tempdb = this.getWritableDatabase();
    	Cursor cursor = tempdb.rawQuery("SELECT * FROM pokemon_type", null);
    	 // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                PokemonType temp = new PokemonType();
                temp.setName(cursor.getString(1));
                temp.setStrongAgainst(cursor.getString(2));
                temp.setWeakAgainst(cursor.getString(3));
                // Adding contact to list
                pokemonTypeList.add(temp);
            } while (cursor.moveToNext());
        }
        tempdb.close();
        return pokemonTypeList;
    }
    
    public ArrayList<AttackType> getAllAttackTypesForPokemon(String pokemonName) 
    {
		ArrayList<AttackType> attackTypeList = new ArrayList<AttackType>();
    	//Select All Query
		SQLiteDatabase tempdb = this.getWritableDatabase();
    	Cursor cursor = tempdb.rawQuery("SELECT * FROM attack_type WHERE pokemon_name='"+pokemonName+"'", null);
    	 // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                AttackType temp = new AttackType();
                temp.setName(cursor.getString(2)); //this is name, pokemon_name is (1)
                temp.setType(cursor.getString(3));
                temp.setDamage(cursor.getInt(4));
                temp.setAccuracy(cursor.getInt(5));
                
                // Adding contact to list
                attackTypeList.add(temp);
            } while (cursor.moveToNext());
        }
        tempdb.close();
        cursor.close();
        return attackTypeList;
	}
    
    public void updatePokemon(Pokemon temp)
    {
    	SQLiteDatabase tempdb = this.getWritableDatabase();
    	ContentValues values = new ContentValues();
    	values.put("hp_current", temp.getHP_current());
    	values.put("xp_current", temp.getXP_Current());
    	values.put("level", temp.getLevel());
    	tempdb.update("pokemon", values, "name='"+temp.getName()+"'", null);
    	tempdb.close();
    }
    
    public void addToArsenal(Pokemon temp)
    {
    	ContentValues values = new ContentValues();
    	SQLiteDatabase tempdb = this.getWritableDatabase();
    	//add this Pokemon to the arsenal
    	values.put("pokemon_fk_name",temp.getName());
    	tempdb.insert("current_arsenal", null, values);
    	Log.d("DatabaseHandler.addToArsenal()", "Inserted "+temp.getName()+" into the arsenal");
    	//reset the health of the Pokemon before updating the record in the main DB
    	temp.setHP_current(100);
    	tempdb.close();
    	updatePokemon(temp);
    }
    
    public ArrayList<Pokemon> getCurrentArsenal()
    {
    	SQLiteDatabase tempdb = this.getWritableDatabase();
    	ArrayList<Pokemon> arsenalList = new ArrayList<Pokemon>();
    	//Select All Query
    	Cursor cursor = tempdb.rawQuery("SELECT * FROM current_arsenal", null);
    	 // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
            	Log.d("DatabaseHandler.getCurrentArsenal()", "Fetching "+cursor.getString(1));
                Pokemon temp = getPokemon(cursor.getString(1));
                Log.d("DatabaseHandler.getCurrentArsenal()", "Successfullly fetched "+temp.getName());
                // Adding pokemon to list
                arsenalList.add(temp);
            } while (cursor.moveToNext());
        }
        tempdb.close();
    	return arsenalList;
    }
	
}
    

