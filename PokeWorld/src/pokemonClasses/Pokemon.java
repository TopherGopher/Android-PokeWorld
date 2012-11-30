package pokemonClasses;

import java.util.Random;

public class Pokemon {


	/**
	 * @param args
	 */
	private int id;
	private String name;
	private PokemonType type;
	private int HP_current;
	private int HP_total;
	private int XP_Current;
	private int XP_total;
	private int level;
	private String gender;
	private AttackType[] attacks;
	
	
	public Pokemon() { //for testing
		Random randomGenerator = new Random();
		int randomInt = randomGenerator.nextInt(1000);
		this.id = randomInt;
		this.name = "TEST" + randomInt;
		HP_current = 100;
		HP_total = 100;
		XP_Current = 0;
		XP_total = 100;
		this.level = 1;
		this.gender = "male";
		AttackType[] myAttacks = new AttackType[4];
		this.attacks = myAttacks;
	}
	

	public Pokemon(String name, PokemonType type, int hP_current, int hP_total,
			int xP_Current, int xP_total, int level, String gender,
			AttackType[] attacks) {
		this.id = 0;//need to find a way for unique id's might link with sql
		this.name = name;
		this.type = type;
		HP_current = hP_current;
		HP_total = hP_total;
		XP_Current = xP_Current;
		XP_total = xP_total;
		this.level = level;
		this.gender = gender;
		this.attacks = attacks;
	}
	
	
	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public PokemonType getType() {
		return type;
	}

	public void setType(PokemonType type) {
		this.type = type;
	}

	public int getHP_current() {
		return HP_current;
	}

	public void setHP_current(Integer hP_current) {
		HP_current = hP_current;
	}

	public int getHP_total() {
		return HP_total;
	}

	public void setHP_total(Integer hP_total) {
		HP_total = hP_total;
	}

	public int getXP_Current() {
		return XP_Current;
	}

	public void setXP_Current(Integer xP_Current) {
		XP_Current = xP_Current;
	}

	public int getXP_total() {
		return XP_total;
	}

	public void setXP_total(Integer xP_total) {
		XP_total = xP_total;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public AttackType[] getAttacks() {
		return attacks;
	}

	public void setAttacks(AttackType[] attacks) {
		this.attacks = attacks;
	}

	
	
	
	public AttackType getAttack(int x) {
		return this.attacks[x];
	}
	
	public void damageCalculation() {
		//what was this for?
	}
	
	
	//changing the input as defined in the UML from String attackName to integer
	public int attack(int attack) {
		int damage;
		int accuracy;
		damage = attacks[attack].getDamage();
		accuracy = attacks[attack].getAccuracy();
		
		Random randomGenerator = new Random();
		int randomInt = randomGenerator.nextInt(100);
		
		if (accuracy > randomInt){
			return damage;
		}
		
		return 0;
	}
	
	public int damaged (int damage, AttackType attack){
		System.out.println("      " + this.name + " is type " + this.type.getName() + " : and was attacked by type  " + attack.getType().getName());
		if(this.type.getWeakAgainst() == attack.getType()){
			damage = damage * 2;
			System.out.println("      It's super effective!" );
		}
		if(this.type.getStrongAgainst() == attack.getType()){
			damage = damage / 2;
			System.out.println("      It's not very effective" );
		}
		this.HP_current -= damage;
		return damage;
	}
}
