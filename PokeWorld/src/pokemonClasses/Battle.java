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
	
	
	public void opponentAttack(int attack_num){
		int damage_ammount = opponentPoke.attack(attack_num);	
		if (damage_ammount == -1){
			//missed
		}
		userPoke.damaged(damage_ammount, opponentPoke.getAttack(attack_num));		
	}

	

}