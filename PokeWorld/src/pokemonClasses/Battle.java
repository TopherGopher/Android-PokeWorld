package pokemonClasses;

import java.util.Random;

import android.widget.ImageView;

import com.sterlingpye.pokeworld.R;


public class Battle {

	Pokemon userPoke;
	Pokemon opponentPoke;
	//private Location location;
	//private image background;
	
	public Battle(Pokemon userPoke, Pokemon opponentPoke) {
		this.userPoke = userPoke;
		this.opponentPoke = opponentPoke;
	}
	
	

	public void userAttack(int attack_num){
		int damage_ammount = userPoke.attack(attack_num);
		if (damage_ammount == -1){
			//missed
		}
		else{
			opponentPoke.damaged(damage_ammount, userPoke.getAttack(attack_num));	
		}
	}
	
	
	public int opponentAttack(){
		Random generator = new Random();
		int attackNum = generator.nextInt(4); //Randomly select an attack to counter with
		int damage_ammount = opponentPoke.attack(attackNum);	
		if (damage_ammount == -1){
			//missed
		}
		userPoke.damaged(damage_ammount, opponentPoke.getAttack(attackNum));	
		return attackNum;
	}
	
	public String getFightResult()
	{
		String textResult = "";
		if (opponentPoke.wasEffective(userPoke.getAttack(0))){
    		textResult = "It's super effective!";
    	}
    	else if (opponentPoke.wasNotEffective(userPoke.getAttack(0))){
    		textResult = "It's not very effective...";
    	}
		return textResult;
	}

	

}