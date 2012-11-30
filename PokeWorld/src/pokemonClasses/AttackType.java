package pokemonClasses;

public class AttackType {

	private String name;
	private PokemonType type;
	private int damage;
	private int accuracy;
	
	public AttackType() { //for testing
		super();
		this.name = "slam";
		this.type = new PokemonType();
		this.damage = 10;
		this.accuracy = 50;
	}
	
	public AttackType(String name, PokemonType type, int damage, int accuracy) {
		this.name = name;
		this.type = type;
		this.damage = damage;
		this.accuracy = accuracy;
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
	public int getDamage() {
		return damage;
	}
	public void setDamage(Integer damage) {
		this.damage = damage;
	}
	public int getAccuracy() {
		return accuracy;
	}
	public void setAccuracy(Integer accuracy) {
		this.accuracy = accuracy;
	}


}
