public class Pokemon {
	public final int pokeID;
	public final int trainerID;
	public String pokename;
	public int[] moves;
	public int[] modifiers = {0,0,0,0,0,0,0,0};
	public int exp;
	public int[] type;
	public Pokedex pokedexptr;
	public Movedex movedexptr;
	public Typedex typedexptr;
	public int lvl;
	public String[] statnames = 
			{"HP", "Attack", "Defense", "Special Attack", "Special Defense", 
			 "Speed", "Accuracy", "Evasiveness"};
	public int[] stats = new int[statnames.length];

	public Pokemon(int id, int trainer, int[] moves, int[] stats, 
				   int level, Pokedex pd, Movedex md, Typedex td) {
		this.pokedexptr = pd;
		this.movedexptr = md;
		this.typedexptr = td;
		this.pokeID = id;
		this.trainerID = trainer;
		this.pokename = pd.getName(id);
		this.moves = moves;
		this.stats = stats;
		this.exp = 0;
		this.modifiers = new int[statnames.length];
		this.lvl = level;
		this.type = pd.getType(id);
	}

	public String print(boolean ally) {
		String rtn = "";
		rtn = rtn + "Lvl " + this.lvl + " " + pokename + "\n" +
			  "HP: " + (this.stats[0] + this.modifiers[0]);
		
		if (ally) rtn = rtn + "\nMoves:" + movedexptr.getNames(this.moves);
			  
		return rtn;
	}

	public boolean fainted() {
		return this.stats[0] + this.modifiers[0] <= 0;
	}

	public double getAttack() {
		return this.stats[1] * this.movedexptr.statvals[this.modifiers[1] + 6];
	}
	public double getDefense() {
		return this.stats[2] * this.movedexptr.statvals[this.modifiers[2] + 6];
	}
	public double getSpAttack() {
		return this.stats[3] * this.movedexptr.statvals[this.modifiers[3] + 6];
	}
	public double getSpDefense() {
		return this.stats[4] * this.movedexptr.statvals[this.modifiers[4] + 6];
	}
	public double getSpeed() {
		return this.stats[5] * this.movedexptr.statvals[this.modifiers[5] + 6];
	}
	public double getAccuracy() {
		return this.stats[6] * this.movedexptr.statvals[this.modifiers[6] + 6];
	}

	public String checkStats() {
		String rtn = "";
		for (int i = 0; i < 7; i++) {
			rtn += statnames[i + 1] + ": " + stats[i + 1] * movedexptr.statvals[modifiers[i + 1] + 6] + "\n";
		}
		return rtn;
	}

	public String checkMoves() {
		String rtn = "";
		
		for (int i = 0; i < 4; i++) {
			if (moves[i] != -1) {
				rtn += (i+1) + ". " + (movedexptr.getMoveDeets(moves[i], typedexptr)) + "\n";
			}
		}

		return rtn;
	}

	public void hurtby(Pokemon enemy, int movechoice) {
		int moveID = enemy.moves[movechoice];
		System.out.print("\n" + enemy.pokename + " used " + movedexptr.getName(moveID));
		int movepower = movedexptr.power[moveID];
		int movestyle = movedexptr.style[moveID];
		int movetype = movedexptr.type[moveID];
		int[] statseffect = movedexptr.statseffect[moveID];

		if (100 * Math.random() > enemy.getAccuracy() * movedexptr.accuracy[moveID] * 0.01) {
			System.out.println(".\n" + enemy.pokename + "'s attack missed!");
		} else {
			if (movestyle < 2) {
			// Modifier is crit * random(0.85 to 1) * Same-Type Attack Bonus * type modifier
				double crit = 1;
				if (Math.random() < 0.0625) {
					crit = 1.5;
				}
				double stab = 1;
				if (movetype == enemy.type[0] || movetype == enemy.type[1]) {
					stab = 1.5;
				}

				double typemodifier = this.typedexptr.matchups[this.type[0]][movetype];
				if (this.type[1] != -1) typemodifier *= this.typedexptr.matchups[this.type[1]][movetype];

				double modifier = crit * stab * typemodifier * (0.85 + 0.15 * Math.random());

				int damage = 0;

				if (movestyle == 0)
					damage = (int)Math.floor(modifier * (((2 * enemy.lvl) / 5) * movepower * (enemy.getAttack()/this.getDefense())) / 50);
				else if (movestyle == 1)
					damage = (int)Math.floor(modifier * (((2 * enemy.lvl) / 5) * movepower * (enemy.getSpAttack()/this.getSpDefense())) / 50);

				this.modifiers[0] = this.modifiers[0] - damage;

				System.out.println(" for " + damage + " damage.\n"); 
				if (crit == 1.5) System.out.print("A critical hit! ");
				if (typemodifier > 1) System.out.print("It's super effective! ");
				System.out.println();
				
			} else if (movestyle == 2) {
				for (int i = 0; i < 7; i++) {
					this.modifiers[i + 1] += statseffect[i];
					if (statseffect[i] > 0) System.out.println("! " + this.pokename + "'s " + statnames[i + 1] + " increased!");
					else if (statseffect[i] < 0) System.out.println("! " + this.pokename + "'s " + statnames[i + 1] + " decreased!");
				}
			} else if (movestyle == 3) {
				for (int i = 0; i < 7; i++) {
					this.modifiers[i + 1] += statseffect[i];
					if (statseffect[i] > 0) System.out.println("! " + enemy.pokename + "'s " + statnames[i + 1] + " increased!");
					else if (statseffect[i] < 0) System.out.println("! " + enemy.pokename + "'s " + statnames[i + 1] + " decreased!");

				}
			}
		}
	}
}


// TODO: moves have accuracies and pokemon can miss. Chance of a miss = current accuracy of the Pkmn * move accuracy





