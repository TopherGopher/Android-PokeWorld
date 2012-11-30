package pokemonClasses;

import java.util.Random;

public class Battle {

	Pokemon userPoke;
	Pokemon opponentPoke;
	//private Location location;
	//private image background;
	
	public Battle(Pokemon userPoke, Pokemon opponentPoke) {
		this.userPoke = userPoke;
		this.opponentPoke = opponentPoke;
	}
	
	

	public void battleProgram () {
		  System.out.println("Battle between : " + userPoke.getName() + " and " + opponentPoke.getName());	
		  while (userPoke.getHP_current() > 0 && opponentPoke.getHP_current() > 0){
			  //user turn
			  int user = turn(userPoke, opponentPoke);
			  if (opponentPoke.getHP_current() <= 0){
				  System.out.println(opponentPoke.getName() + " fainted! ");
				  break;
			  }
			  //run away
			  if (user == -1){
				  System.out.println("You got away safely!");
				  break;
			  }
			  //throw pokeball
			  if (user == -2){
				  Random randomGenerator = new Random();
				  int randomInt = randomGenerator.nextInt(100);
				  if (randomInt < 10){
					  System.out.println("You caught the wild " + opponentPoke.getName() + "!" );
					  //add opponent to inventory
					  break;
				  }
				  else{
					  System.out.println("It broke free!" );
				  }
			  }
			  //use attack
			  
			  
			  
			  
			  
			  //opponent turn
			  int opponent = turn(opponentPoke, userPoke);
			  if (userPoke.getHP_current() <= 0){
				  System.out.println("Your " + userPoke.getName() + " fainted! ");
				  break;
			  }
			  
			  
			  if (opponent == -1){
				  System.out.println(opponentPoke.getName() + " ran away!");
				  break;
			  }
			  
			  
		  }
		  


	}
	public int turn (Pokemon turnPoke, Pokemon oppPoke) {
		System.out.println(turnPoke.getName() + "'s turn : ");
		Random randomGenerator = new Random();//replace with interface, this chooses random action
		int randomInt = randomGenerator.nextInt(100);//0-98 = attack 99 = run
		if ((randomInt != 99) && (randomInt != 98)){
			int randomInt1 = randomGenerator.nextInt(4);
			int damage = turnPoke.attack(randomInt1);
			int attackNum = randomInt1 + 1;
			System.out.println(turnPoke.getName() + " used attack " + attackNum + " : " + turnPoke.getAttack(randomInt1).getName());
			damage = oppPoke.damaged(damage, turnPoke.getAttack(randomInt1));
			if (damage > 0){
				System.out.println("      " + turnPoke.getAttack(randomInt1).getName() + " does " + damage + " damage");
			}
			else{
				System.out.println("      " + turnPoke.getAttack(randomInt1).getName() + " missed!");
			}
			System.out.println("      " + oppPoke.getName() + " is at " + oppPoke.getHP_current());
			return damage;			
		}
		if (randomInt == 98){
			System.out.println("User Throws a pokeball!");
			return -2;
		}
		if (randomInt == 99){
			System.out.println(turnPoke.getName() + " Ran! ");
			return -1;
		}
		
	       
	return -1;
	}
	


	
	
	





	
	

}
