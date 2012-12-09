package com.sterlingpye.pokeworld;
import pokemonClasses.Pokemon;
import java.util.ArrayList;
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
    private static final int DATABASE_VERSION = 2;
 
    // Database Name
    private static final String DATABASE_NAME = "pokeworld";
 
    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
 
    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
    	
        db.execSQL("CREATE TABLE pokemon (id INTEGER PRIMARY KEY, name TEXT, type TEXT, hp_current INTEGER, hp_total INTEGER, xp_current INTEGER, xp_total INTEGER, level INTEGER, gender TEXT)");
        db.execSQL("CREATE TABLE pokemon_type (id INTEGER PRIMARY KEY, name TEXT, weak_against TEXT, strong_against TEXT)");
        db.execSQL("CREATE TABLE attack_type (id INTEGER PRIMARY KEY, pokemon_name TEXT, name TEXT, type TEXT, damage INTEGER, accuracy INTEGER)"); //get rid of pokemon name if possible
        db.execSQL("CREATE TABLE current_arsenal (id INTEGER PRIMARY KEY, pokemon_fk_id INTEGER)");
        
    }
 
    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS pokemon");
        db.execSQL("DROP TABLE IF EXISTS pokemon_type");
        db.execSQL("DROP TABLE IF EXISTS attack_type");
        db.execSQL("DROP TABLE IF EXISTS current_arsenal");
 
        // Create tables again
        onCreate(db);
    }
    
    
    
    public void addPokemon(Pokemon temp)
    {
    	SQLiteDatabase db = this.getWritableDatabase();
    	ContentValues values = new ContentValues();
    	values.put("id", temp.getId());
    	values.put("name", temp.getName());
    	values.put("type", temp.getType().getName());
    	values.put("hp_current", temp.getHP_current());
    	values.put("hp_total", temp.getHP_total());
    	values.put("xp_current", temp.getXP_Current());
    	values.put("xp_total", temp.getXP_total());
    	values.put("level", temp.getLevel());
    	values.put("gender", temp.getGender());
    	db.insert("pokemon", null, values);
    	db.close(); //Close the DB connection
    	addAttackTypes(temp.getAttacks(), temp);
    }
    
    public void addPokemonType(PokemonType temp)
    {
    	SQLiteDatabase db = this.getWritableDatabase();
    	ContentValues values = new ContentValues();
    	values.put("name", temp.getName());
    	values.put("weak_against", temp.getWeakAgainst());
    	values.put("strong_against", temp.getStrongAgainst());
    	db.insert("pokemon_type", null, values);
    	db.close();
    }
    
    public void addAttackType(AttackType tempAttackType, Pokemon tempPoke)
    {
    	SQLiteDatabase db = this.getWritableDatabase();
    	ContentValues values = new ContentValues();
		values.put("pokemon_name", tempPoke.getName()); //remove me if possible
    	values.put("name", tempAttackType.getName());
    	values.put("type", tempAttackType.getType());
    	values.put("damage", tempAttackType.getDamage());
    	values.put("accuracy", tempAttackType.getAccuracy());
    	db.insert("attack_type", null, values);
    	db.close();
    }
    
    public void addAttackTypes(ArrayList<AttackType> tempList, Pokemon tempPoke)
    {
    	SQLiteDatabase db = this.getWritableDatabase();
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
        	db.insert("attack_type", null, values);
    		count++;
    	}
    	
    	db.close();
    }
    
    public Pokemon getPokemon(String name)
    {
    	Pokemon temp = new Pokemon();
    	SQLiteDatabase db = this.getWritableDatabase();
    	Cursor cursor = db.rawQuery("SELECT * FROM pokemon WHERE name='"+name+"'", null);
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
        temp.setAttacks(getAllAttackTypesForPokemon(cursor.getString(1)));
        return temp;
    }
    
    public PokemonType getPokemonType(String name)
    {
    	PokemonType temp = new PokemonType();
    	SQLiteDatabase db = this.getWritableDatabase();
    	Cursor cursor = db.rawQuery("SELECT * FROM pokemon_type WHERE name='"+name+"'", null);
    	 // looping through all rows and adding to list
        cursor.moveToFirst();
        temp.setName(cursor.getString(1));
        temp.setStrongAgainst(cursor.getString(2));
        temp.setWeakAgainst(cursor.getString(3));
        return temp;
    }
    
    public AttackType getAttackType(Pokemon tempPoke)
    {
    	AttackType temp = new AttackType();
    	SQLiteDatabase db = this.getWritableDatabase();
    	Cursor cursor = db.rawQuery("SELECT * FROM attack_type WHERE pokemon_name='"+tempPoke.getName()+"'", null);
    	 // looping through all rows and adding to list
        cursor.moveToFirst();
        temp.setName(cursor.getString(1));
        temp.setType(cursor.getString(2));
        temp.setDamage(cursor.getInt(3));
        temp.setAccuracy(cursor.getInt(4));
        return temp;
    }
    
    public ArrayList<Pokemon> getAllPokemon()
    {
    	ArrayList<Pokemon> pokemonList = new ArrayList<Pokemon>();
    	//Select All Query
    	SQLiteDatabase db = this.getWritableDatabase();
    	Cursor cursor = db.rawQuery("SELECT * FROM pokemon", null);
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
        return pokemonList;
    }
    public ArrayList<PokemonType> getAllPokemonTypes()
    {
    	ArrayList<PokemonType> pokemonTypeList = new ArrayList<PokemonType>();
    	//Select All Query
    	SQLiteDatabase db = this.getWritableDatabase();
    	Cursor cursor = db.rawQuery("SELECT * FROM pokemon_type", null);
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
        return pokemonTypeList;
    }
    
    public ArrayList<AttackType> getAllAttackTypesForPokemon(String pokemonName) 
    {
		ArrayList<AttackType> attackTypeList = new ArrayList<AttackType>();
    	//Select All Query
    	SQLiteDatabase db = this.getWritableDatabase();
    	Cursor cursor = db.rawQuery("SELECT * FROM attack_type WHERE pokemon_name='"+pokemonName+"'", null);
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
        return attackTypeList;
	}
	
}
    

