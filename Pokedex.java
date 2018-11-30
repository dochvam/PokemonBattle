public class Pokedex {
	public final String[] names = 
		{"Spearow", "Fearow", "Kadabra", "Machop", "Machoke"};
	public final int[][] types = {{2, 0}, {2, 0}, {13, -1}, {1, -1}, {1, -1}};
	public int[] ids = {21, 22, 64, 66, 67};

	public Pokedex(){}

	public String getName(int pokeID) {
		int spot = getSpot(pokeID);
		if (spot != -1) {return names[spot];}
		else {return "ID " + pokeID + " not found";}
	}

	public int getSpot(int pokeID) {
		for (int i = 0; i < this.ids.length; i++) {
			if (pokeID == this.ids[i]) {
				return i;
			}
		}
		return -1;
	}

	public int[] getType(int pokeID) {
		int[] backup = {-1,-1};
		int spot = getSpot(pokeID);
		if (spot != -1) {return types[spot];}
		else return backup;
	}

	public static void main(String[] args) {
		Pokedex p = new Pokedex();
		System.out.println(p.getName(66));
	}

}