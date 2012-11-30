package pokemonClasses;

public class PokemonType { 

	private String name;
	private PokemonType strongAgainst;
	private PokemonType weakAgainst;
	
	public PokemonType() { //for testing
		this.name = "testType";
		this.strongAgainst = null;
		this.weakAgainst = null;
	}
	
	public PokemonType(String name, PokemonType strongAgainst,
			PokemonType weakAgainst) {
		this.name = name;
		this.strongAgainst = strongAgainst;
		this.weakAgainst = weakAgainst;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public PokemonType getStrongAgainst() {
		return strongAgainst;
	}
	public void setStrongAgainst(PokemonType strongAgainst) {
		this.strongAgainst = strongAgainst;
	}
	public PokemonType getWeakAgainst() {
		return weakAgainst;
	}
	public void setWeakAgainst(PokemonType weakAgainst) {
		this.weakAgainst = weakAgainst;
	}



}
