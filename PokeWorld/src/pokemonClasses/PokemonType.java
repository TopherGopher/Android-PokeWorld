package pokemonClasses;

public class PokemonType { 

	private String name;
	private String strongAgainst;
	private String weakAgainst;
	
	public PokemonType() { //for testing
		this.name = "testType";
		this.strongAgainst = null;
		this.weakAgainst = null;
	}
	
	public PokemonType(String name, String strongAgainst,
			String weakAgainst) {
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
	public String getStrongAgainst() {
		return strongAgainst;
	}
	public void setStrongAgainst(String strongAgainst) {
		this.strongAgainst = strongAgainst;
	}
	public String getWeakAgainst() {
		return weakAgainst;
	}
	public void setWeakAgainst(String weakAgainst) {
		this.weakAgainst = weakAgainst;
	}



}
