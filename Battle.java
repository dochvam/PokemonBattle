import java.util.*;

public class Battle {
	public static Typedex td = new Typedex();
	public static Pokedex pd = new Pokedex();
	public static Movedex md = new Movedex();
	public static Scanner input = new Scanner(System.in);

	public static String[] cheers = 
		{"Go! ", "You can do it, ", "Go get 'em, ", "I choose you, "};

	public static void main(String[] args) {
	// Initialize the wild Pokemon
		int[] spearowMoves = {0, 1, 3, -1};
		int[] spearowStats = {30, 15, 15, 6, 14, 16, 100, 0};
		Pokemon wildSpearow = new Pokemon(21, 0, spearowMoves, spearowStats,
								   7, pd, md, td);
		int[] fearowMoves = {7, 1, 0, 3};
		int[] fearowStats = {50, 23, 20, 6, 18, 22, 100, 0};
		Pokemon wildFearow = new Pokemon(22, 0, fearowMoves, fearowStats,
								   10, pd, md, td);

		int[] machopMoves = {0, 2, 6, 4};
		int[] machopStats = {34, 25, 19, 10, 15, 14, 100, 0};
		Pokemon wildMachop = new Pokemon(66, 0, machopMoves, machopStats,
								   8, pd, md, td);

		int[] machokeMoves = {0, 2, 6, -1};
		int[] machokeStats = {45, 27, 24, 13, 20, 18, 100, 0};
		Pokemon wildMachoke = new Pokemon(67, 0, machokeMoves, machokeStats,
								   12, pd, md, td);

		Pokemon[] wildPokemon = {wildSpearow, wildFearow, wildMachop, wildMachoke};

	// Initialize the player's team
		Pokemon[] team = new Pokemon[6];

		int[] kadabraMoves = {5, 4, 0, -1};
		int[] kadabraStats = {40, 10, 10, 20, 24, 19, 100, 0};
		team[0] = new Pokemon(64, 0, kadabraMoves, kadabraStats,
							  10, pd, md, td);

	// A wild pokemon appears!
		Pokemon activeEnemy = wildPokemon[(int)(wildPokemon.length * Math.random())];

		System.out.println("A wild " + activeEnemy.pokename + " appeared!\n");
		
		System.out.println(cheers[(int)(cheers.length*Math.random())] + 
						   team[0].pokename + "!\n");
		Pokemon activeAlly = team[0];

		while (!out(team) && !activeEnemy.fainted()) {

			System.out.println("================");

			System.out.println("\nEnemy Pokemon: \n" + activeEnemy.print(false));

			System.out.println("\nAlly Pokemon: \n" + activeAlly.print(true));

			System.out.println("\nType 1, 2, 3, or 4 to choose a move and press ENTER.");
			System.out.println("\nType 8 to check stats and 9 to check moves.");

			boolean unchosen = true;
			int movechoice = -1;
			while (unchosen) {
				movechoice = input.nextInt();
				if (movechoice < 5 && movechoice > 0) {
					if (activeAlly.moves[movechoice-1] == -1) {
						System.out.println("That moveslot is empty.");
					} else unchosen = false;
				} else if (movechoice == 8) {
					System.out.println(activeAlly.checkStats());
				} else if (movechoice == 9) {
					System.out.println(activeAlly.checkMoves());
				} else {
					System.out.println("\nType 1, 2, 3, or 4 to choose a move and press ENTER.");
					System.out.println("\nType 8 to check stats and 9 to check moves.");
				}
			}

			int enemyMoveChoice = -1;
			while (enemyMoveChoice == -1) {
				int attempt = (int)Math.floor(4 * (Math.random()));
				if (activeEnemy.moves[attempt] != -1) {
					enemyMoveChoice = attempt;
				}
			}

			System.out.println("================");
			if (activeAlly.getSpeed() - activeEnemy.getSpeed() >= 0) {
				activeEnemy.hurtby(activeAlly, movechoice-1);
				if (!activeEnemy.fainted()) activeAlly.hurtby(activeEnemy, enemyMoveChoice);
			} else {
				activeAlly.hurtby(activeEnemy, enemyMoveChoice);
				if (!activeAlly.fainted()) activeEnemy.hurtby(activeAlly, movechoice-1);
			}
			// activeEnemy.stats[0] = 0;
		}
		if (activeAlly.fainted()) System.out.println("Your " + activeAlly.pokename + " fainted!");
		if (activeEnemy.fainted()) System.out.println("The wild " + activeEnemy.pokename + " fainted!");

	}

// Check if the whole team is depleted
	public static boolean out(Pokemon[] team) {
		for (int i = 0; i < 6; i++) {
			if (team[i] instanceof Pokemon && !team[i].fainted()) return false;
		}
		return true;
	}

}