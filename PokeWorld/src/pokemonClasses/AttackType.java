package pokemonClasses;

public class AttackType {

	private String name;
	private String type;
	private int damage;
	private int accuracy;
	
	public AttackType() { //for testing
		super();
		this.name = "Slam";
		this.type = "Physical";
		this.damage = 10;
		this.accuracy = 50;
	}
	
	public AttackType(String name, String type, int damage, int accuracy) {
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
	public String getType() {
		return type;
	}
	public void setType(String type) {
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
