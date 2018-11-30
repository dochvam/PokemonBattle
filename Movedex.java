public class Movedex {
	public String[] movenames = 
		{"Tackle", "Peck", "Karate Chop", "Sand Attack", "Growl", 
		 "Confusion", "Leer", "Wing Attack"};
	public int[] power = 
		{20, 30, 50, 0, 0,
		 40, 0, 40};
	public int[] type = 
		{0, 2, 1, 0, 0,
		 13, 0, 2};
	public int[] style =  // 0 = physical, 1 = special, 2 = stat debuff, 3 = stat buff
		{0, 0, 0, 2, 2, 
		 1, 2, 0};
	public int[][] statseffect = 
		{{0,0,0,0,0,0,0}, {0,0,0,0,0,0,0}, {0,0,0,0,0,0,0}, {0,0,0,0,0,-1,0}, {-1,0,0,0,0,0,0},
		 {0,0,0,0,0,0,0}, {0,-1,0,0,0,0,0}, {0,0,0,0,0,0,0}};

	public int[] accuracy =  // 0 = physical, 1 = special, 2 = stat debuff, 3 = stat buff
		{95, 100, 100, 95, 95, 
		 100, 100, 90};

	public String[] stylenames = {"Physical", "Special", "Debuff", "Buff"};

// This item corresponds to the multiplier for each stat change from -6 to 6.
// For example, if I've been hit by a defense debuff twice, I get 0.50x my defense
	public final double[] statvals = {0.25, 0.28, 0.33, 0.40, 0.50, 0.66,
								   1, 1.5, 2, 2.5, 3, 3.5, 4};

	public Movedex() {}

	public String getNames(int[] moves) {
		String rtn = " 1." + getName(moves[0]) +
					 " 2." + getName(moves[1]) +
					 " 3." + getName(moves[2]) +
					 " 4." + getName(moves[3]);
		return rtn;
	}

	public String getName(int moveID) {
		if (moveID == -1) return "";
		return movenames[moveID];
	}

	public String getMoveDeets(int moveID, Typedex td) {
		return getName(moveID) + ". Power = " + power[moveID] + ". Accuracy = " + accuracy[moveID] + 
			   ". Style = " + stylenames[style[moveID]] + ". Type = " + td.typenames[type[moveID]];
	}
}



