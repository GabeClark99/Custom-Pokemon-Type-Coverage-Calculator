import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

public class PokemonDatabase {
	private static final String FILE_NAME = "pokemon.txt";
	private static final int NUM_TYPES = 18;
	private static final double[][] TYPE_TABLE = {
		{1,		1,		1,		1,		1,		1,		1,		1,		1,		1,		1,		1,		0.5,	0,		1,		1,		0.5,	1},
		{1,		0.5,	0.5,	1,		2,		2,		1,		1,		1,		1,		1,		2,		0.5,	1,		0.5,	1,		2,		1},
		{1,		2,		0.5,	1,		0.5,	1,		1,		1,		2,		1,		1,		1,		2,		1,		0.5,	1,		1,		1},
		{1,		1,		2,		0.5,	0.5,	1,		1,		1,		0,		2,		1,		1,		1,		1,		0.5,	1,		1,		1},
		{1,		0.5,	2,		1,		0.5,	1,		1,		0.5,	2,		0.5,	1,		0.5,	2,		1,		0.5,	1,		0.5,	1},
		{1,		0.5,	0.5,	1,		2,		0.5,	1,		1,		2,		2,		1,		1,		1,		1,		2,		1,		0.5,	1},
		{2,		1,		1,		1,		1,		2,		1,		0.5,	1,		0.5,	0.5,	0.5,	2,		0,		1,		2,		2,		0.5},
		{1,		1,		1,		1,		2,		1,		1,		0.5,	0.5,	1,		1,		1,		0.5,	0.5,	1,		1,		0,		2},
		{1,		2,		1,		2,		0.5,	1,		1,		2,		1,		0,		1,		0.5,	2,		1,		1,		1,		2,		1},
		{1,		1,		1,		0.5,	2,		1,		2,		1,		1,		1,		1,		2,		0.5,	1,		1,		1,		0.5,	1},
		{1,		1,		1,		1,		1,		1,		2,		2,		1,		1,		0.5,	1,		1,		1,		1,		0,		0.5,	1},
		{1,		0.5,	1,		1,		2,		1,		0.5,	0.5,	1,		0.5,	2,		1,		1,		0.5,	1,		2,		0.5,	0.5},
		{1,		2,		1,		1,		1,		2,		0.5,	1,		0.5,	2,		1,		2,		1,		1,		1,		1,		0.5,	1},
		{0,		1,		1,		1,		1,		1,		1,		1,		1,		1,		2,		1,		1,		2,		1,		0.5,	1,		1},
		{1,		1,		1,		1,		1,		1,		1,		1,		1,		1,		1,		1,		1,		1,		2,		1,		0.5,	0},
		{1,		1,		1,		1,		1,		1,		0.5,	1,		1,		1,		2,		1,		1,		2,		1,		0.5,	1,		0.5},
		{1,		0.5,	0.5,	0.5,	1,		2,		1,		1,		1,		1,		1,		1,		2,		1,		1,		1,		0.5,	2},
		{1,		0.5,	1,		1,		1,		1,		2,		0.5,	1,		1,		1,		1,		1,		1,		2,		2,		0.5,	1},
	};
	private static final HashMap<Integer, String> INDEX2TYPE_MAP = new HashMap<Integer, String>();
	private static final HashMap<String, Integer> TYPE2INDEX_MAP = new HashMap<String, Integer>();
	private static ArrayList<Pokemon> pokemonList = new ArrayList<Pokemon>();
	
	public static void init() {
		initMaps();
        importPokemon();
        return;
    }

	private static void initMaps() {
		INDEX2TYPE_MAP.put(0, "NORMAL");
		INDEX2TYPE_MAP.put(1, "FIRE");
		INDEX2TYPE_MAP.put(2, "WATER");
		INDEX2TYPE_MAP.put(3, "ELECTRIC");
		INDEX2TYPE_MAP.put(4, "GRASS");
		INDEX2TYPE_MAP.put(5, "ICE");
		INDEX2TYPE_MAP.put(6, "FIGHTING");
		INDEX2TYPE_MAP.put(7, "POISON");
		INDEX2TYPE_MAP.put(8, "GROUND");
		INDEX2TYPE_MAP.put(9, "FLYING");
		INDEX2TYPE_MAP.put(10, "PSYCHIC");
		INDEX2TYPE_MAP.put(11, "BUG");
		INDEX2TYPE_MAP.put(12, "ROCK");
		INDEX2TYPE_MAP.put(13, "GHOST");
		INDEX2TYPE_MAP.put(14, "DRAGON");
		INDEX2TYPE_MAP.put(15, "DARK");
		INDEX2TYPE_MAP.put(16, "STEEL");
		INDEX2TYPE_MAP.put(17, "FAIRY");

		TYPE2INDEX_MAP.put("NORMAL", 0);
		TYPE2INDEX_MAP.put("FIRE", 1);
		TYPE2INDEX_MAP.put("WATER", 2);
		TYPE2INDEX_MAP.put("ELECTRIC", 3);
		TYPE2INDEX_MAP.put("GRASS", 4);
		TYPE2INDEX_MAP.put("ICE", 5);
		TYPE2INDEX_MAP.put("FIGHTING", 6);
		TYPE2INDEX_MAP.put("POISON", 7);
		TYPE2INDEX_MAP.put("GROUND", 8);
		TYPE2INDEX_MAP.put("FLYING", 9);
		TYPE2INDEX_MAP.put("PSYCHIC", 10);
		TYPE2INDEX_MAP.put("BUG", 11);
		TYPE2INDEX_MAP.put("ROCK", 12);
		TYPE2INDEX_MAP.put("GHOST", 13);
		TYPE2INDEX_MAP.put("DRAGON", 14);
		TYPE2INDEX_MAP.put("DARK", 15);
		TYPE2INDEX_MAP.put("STEEL", 16);
		TYPE2INDEX_MAP.put("FAIRY", 17);
		
		return;
	}

    private static void importPokemon() {
		File pokemonFile = new File(PokemonDatabase.FILE_NAME);
		try {
			Scanner fileIn = new Scanner(pokemonFile);

			while(fileIn.hasNextLine()) {
				String line = fileIn.nextLine();
				// System.out.println(line);	//-----dbg!!!
				Pokemon p = PokemonDatabase.getPokemonFromText(line);
				PokemonDatabase.pokemonList.add(p);

				// displayPokemonTypeStuff(p);	//-----dbg!!!
			}

			fileIn.close();
		}
		catch(FileNotFoundException e) {
			System.out.println("Could not find file \"" + PokemonDatabase.FILE_NAME + "\"!");
			System.exit(1);
		}

        return;
    }

	private static Pokemon getPokemonFromText(String text) {
		String[] tokens = text.split("\t");
		
		String name = tokens[0];
		String primaryType = tokens[1];
		String secondaryType = tokens[2];

		Set<String> weaknesses = new LinkedHashSet<String>();
		Set<String> resistances = new LinkedHashSet<String>();
		Set<String> immunities = new LinkedHashSet<String>();

		ArrayList<String> types = new ArrayList<String>();
		types.add(primaryType);
		if(!secondaryType.equals("NONE")) {
			types.add(secondaryType);
		}

		ArrayList<Integer> typeIndexes = new ArrayList<Integer>();
		for(int i = 0; i < types.size(); ++i) {
			typeIndexes.add(TYPE2INDEX_MAP.get(types.get(i)));
		}

		// step through each type in the table and figure out if its an immunity, resistance, or weakness
		for(int curTypeIndex = 0; curTypeIndex < PokemonDatabase.NUM_TYPES; ++curTypeIndex) {
			String curType = INDEX2TYPE_MAP.get(curTypeIndex);

			// for each of this pokemon's types, get the multiplier and combine
			double damageMult = 1.0;
			for(int j = 0; j < typeIndexes.size(); ++j) {
				damageMult *= TYPE_TABLE[curTypeIndex][typeIndexes.get(j)];
			}

			if(damageMult == 0.0) {
				immunities.add(curType);
			}
			else if(damageMult < 1.0) {
				resistances.add(curType);
			}
			else if(damageMult > 1.0) {
				weaknesses.add(curType);
			}
		}

		Pokemon p = new Pokemon(name, types, weaknesses, resistances, immunities);
		return p;
	}

	private static void displayPokemonTypeStuff(Pokemon p) {
		System.out.println("\tweaknesses: " + p.weaknesses);
		System.out.println("\tresistances: " + p.resistances);
		System.out.println("\timmunities: " + p.immunities);
		return;
	}

	public static ArrayList<Pokemon> getFullPokemonList() { return pokemonList; }
}
