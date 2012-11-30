package pokemonClasses;

import java.util.ArrayList;
import java.util.Random;

public class User {

	private ArrayList<Pokemon> currentArsenal = new ArrayList<Pokemon>();
	//private Location location;

	

	public User(ArrayList<Pokemon> currentArsenal) {
		this.currentArsenal = currentArsenal;
	}

	
	
	public ArrayList<Pokemon> getCurrentArsenal() {
		return currentArsenal;
	}


	public void setCurrentArsenal(ArrayList<Pokemon> currentArsenal) {
		this.currentArsenal = currentArsenal;
	}
	
	public void addPokemon (Pokemon poke){
		currentArsenal.add(poke);
	}
	public void removePokemon (Pokemon poke){
		currentArsenal.remove(poke);
	}
	


	/**Testing runtime
	 * @param args
	 */
	public static void main(String[] args) {
		// testing
		Pokemon pokemon1 = new Pokemon();
		Pokemon pokemon2 = new Pokemon();
		
		PokemonType electric = new PokemonType("Electric", null, null); //SQL?
		PokemonType fire = new PokemonType("Fire", null, null);
		PokemonType water = new PokemonType("Water", null, null);
		PokemonType grass = new PokemonType("Grass", null, null);
		electric.setStrongAgainst(water);
		electric.setWeakAgainst(grass);
		fire.setStrongAgainst(grass);
		fire.setWeakAgainst(water);
		water.setStrongAgainst(fire);
		water.setWeakAgainst(grass);
		grass.setStrongAgainst(electric);
		grass.setWeakAgainst(fire);
		
		Random randomGenerator = new Random();
		
		int randomType = randomGenerator.nextInt(4);
		if (randomType == 0){
			pokemon1.setType(electric);
			pokemon2.setType(grass);
		}
		if (randomType == 1){
			pokemon1.setType(fire);
			pokemon2.setType(water);
		}
		if (randomType == 2){
			pokemon1.setType(water);
			pokemon2.setType(fire);
		}
		if (randomType == 3){
			pokemon1.setType(grass);
			pokemon2.setType(electric);
		}
		
		AttackType zap = new AttackType("Zap", electric, 25, 95);
		AttackType flame = new AttackType("Flame", fire, 40, 80);
		AttackType watergun = new AttackType("Watergun", water, 20, 99);
		AttackType cut = new AttackType("Cut", grass, 10, 99);
		
		AttackType[] myAttacks = new AttackType[4];		
		myAttacks[0] = zap;
		myAttacks[1] = flame;
		myAttacks[2] = watergun;
		myAttacks[3] = cut;
		pokemon1.setAttacks(myAttacks);
		pokemon2.setAttacks(myAttacks);


		Battle War = new Battle(pokemon1, pokemon2);
		War.battleProgram();
		
	}




}
