import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Calculator {

    public static void main(String[] args) {
        Calculator.init();
		
		ArrayList<ArrayList<String>> combs = findMinimumOptimalTypeCombinations();
		for(int i = 0; i < combs.size(); ++i) {
			ArrayList<String> curComb = combs.get(i);
			System.out.println("Minimum Optimal Combination #" + i + ": " + curComb);
		}

		return;
    }

	private static void init() {
		PokemonDatabase.init();
	}

	private static ArrayList<ArrayList<Pokemon>> calcEffectiveness(String[] attackingTypes) {
		ArrayList<ArrayList<Pokemon>> categories = new ArrayList<ArrayList<Pokemon>>();
		categories.add(new ArrayList<Pokemon>());				// superEffective category
		categories.add(new ArrayList<Pokemon>());				// neutral category
		categories.add(new ArrayList<Pokemon>());				// not very effective category
		categories.add(PokemonDatabase.getFullPokemonList());	// no effect category

		return categorize(attackingTypes, attackingTypes.length, categories);
	}

	private static String[] getAttackingTypesFromUser() {
		Scanner userInput = new Scanner(System.in);

		System.out.println("Enter your attacking types, all caps, space delimited:");
		String line = userInput.nextLine();
		String[] attackingTypes = line.split(" ");

		userInput.close();
		return attackingTypes;
	}

	/*private static ArrayList<ArrayList<Pokemon>> calculateResults(String[] attackingTypes, ArrayList<Pokemon> pokemonList) {
		ArrayList<Pokemon> superEffective = new ArrayList<Pokemon>();
		ArrayList<Pokemon> notVeryEffective = new ArrayList<Pokemon>();
		ArrayList<Pokemon> neutral = new ArrayList<Pokemon>();
		ArrayList<Pokemon> noEffect = new ArrayList<Pokemon>();
		
		
		for(int i = 0; i < attackingTypes.length; ++i) {	// for each attacking type...
			String curType = attackingTypes[i];

			for(int j = 0; j < pokemonList.size(); ++j) {	// for each pokemon in the list...
				Pokemon curPokemon = pokemonList.get(j);

				if(curPokemon.isWeakness(curType)) {
					superEffective.add(curPokemon);
				}
				else if(curPokemon.isResistance(curType)) {
					notVeryEffective.add(curPokemon);
				}
				else if(curPokemon.isImmunity(curType)) {
					noEffect.add(curPokemon);
				}
				else {
					neutral.add(curPokemon);
				}
			}
		}

		ArrayList<ArrayList<Pokemon>> results = new ArrayList<ArrayList<Pokemon>>();
		results.add(superEffective);
		results.add(notVeryEffective);
		results.add(neutral);
		results.add(noEffect);
		
		return results;
	}*/

	private static ArrayList<ArrayList<Pokemon>> categorize(String[] attackingTypes, int numAttackingTypes, ArrayList<ArrayList<Pokemon>> categories) {
		// System.out.println("!!!top of calculateResults"); // -----dbg!!!
		if(numAttackingTypes < 1) {
			// System.out.println("!!!returning, numAttackingTypes = " + numAttackingTypes); // -----dbg!!!
			return categories;
		}

		String curType = attackingTypes[numAttackingTypes - 1];
		// System.out.println("!!!curType = " + curType);	//-----dbg!!!

		// System.out.println("!!!entering next recursion...\n"); // -----dbg!!!
		ArrayList<ArrayList<Pokemon>> oldCategories = categorize(attackingTypes, numAttackingTypes - 1, categories);
		// System.out.println("\n!!!returned from recursion"); // -----dbg!!!
		// System.out.println("!!!curType = " + curType);	//-----dbg!!!

		ArrayList<Pokemon> oldSuperEffective = oldCategories.get(0);
		ArrayList<Pokemon> oldNeutral = oldCategories.get(1);
		ArrayList<Pokemon> oldNotVeryEffective = oldCategories.get(2);
		ArrayList<Pokemon> oldNoEffect = oldCategories.get(3);


		ArrayList<Pokemon> newSuperEffective = oldSuperEffective;
		ArrayList<Pokemon> newNeutral = new ArrayList<Pokemon>();
		ArrayList<Pokemon> newNotVeryEffective = new ArrayList<Pokemon>();
		ArrayList<Pokemon> newNoEffect = new ArrayList<Pokemon>();

		// System.out.println("!!!entering checks for old no effect");	//-----dbg!!!
		for(int i = 0; i < oldNoEffect.size(); ++i) {
			Pokemon curPokemon = oldNoEffect.get(i);

			if(curPokemon.isImmunity(curType)) {
				// System.out.println("\t!!!its an immunity");	//-----dbg!!!
				newNoEffect.add(curPokemon);
			}
			else if(curPokemon.isResistance(curType)) {
				// System.out.println("\t!!!its a resistance");	//-----dbg!!!
				newNotVeryEffective.add(curPokemon);
			}
			else if(curPokemon.isWeakness(curType)) {
				// System.out.println("\t!!!its a weakness");	//-----dbg!!!
				newSuperEffective.add(curPokemon);
			}
			else {
				// System.out.println("\t!!!its neutral");	//-----dbg!!!
				newNeutral.add(curPokemon);
			}
		}

		// System.out.println("!!!entering checks for old not very effective");	//-----dbg!!!
		for(int i = 0; i < oldNotVeryEffective.size(); ++i) {
			Pokemon curPokemon = oldNotVeryEffective.get(i);

			if(curPokemon.isResistance(curType)) {
				// System.out.println("\t!!!its a resistance");	//-----dbg!!!
				newNotVeryEffective.add(curPokemon);
			}
			else if(curPokemon.isWeakness(curType)) {
				// System.out.println("\t!!!its a weakness");	//-----dbg!!!
				newSuperEffective.add(curPokemon);
			}
			else {
				// System.out.println("\t!!!its neutral");	//-----dbg!!!
				newNeutral.add(curPokemon);
			}
		}

		// System.out.println("!!!entering checks for old neutral");	//-----dbg!!!
		for(int i = 0; i < oldNeutral.size(); ++i) {
			Pokemon curPokemon = oldNeutral.get(i);

			if(curPokemon.isWeakness(curType)) {
				// System.out.println("\t!!!its a weakness");	//-----dbg!!!
				newSuperEffective.add(curPokemon);
			}
			else {
				// System.out.println("\t!!!its neutral");	//-----dbg!!!
				newNeutral.add(curPokemon);
			}
		}

		ArrayList<ArrayList<Pokemon>> newCategories = new ArrayList<ArrayList<Pokemon>>();
		newCategories.add(newSuperEffective);
		newCategories.add(newNeutral);
		newCategories.add(newNotVeryEffective);
		newCategories.add(newNoEffect);

		return newCategories;
	}

	private static void displayResults(ArrayList<ArrayList<Pokemon>> results) {
		ArrayList<Pokemon> superEffective = results.get(0);
		ArrayList<Pokemon> neutral = results.get(1);
		ArrayList<Pokemon> notVeryEffective = results.get(2);
		ArrayList<Pokemon> noEffect = results.get(3);
		
		System.out.println("No Effect: " + noEffect.size());
		System.out.println("Not Very Effective: " + notVeryEffective.size());
		System.out.println("Neutral: " + neutral.size());
		System.out.println("Super Effective: " + superEffective.size());

		System.out.println("-----NO EFFECT-----");
		for(int i = 0; i < noEffect.size(); ++i) {
			Pokemon curPokemon = noEffect.get(i);

			System.out.println(curPokemon.getInfo());
		}

		System.out.println("-----NOT VERY EFFECTIVE-----");
		for(int i = 0; i < notVeryEffective.size(); ++i) {
			Pokemon curPokemon = notVeryEffective.get(i);

			System.out.println(curPokemon.getInfo());
		}

		System.out.println("-----NEUTRAL-----");
		for(int i = 0; i < neutral.size(); ++i) {
			Pokemon curPokemon = neutral.get(i);

			System.out.println(curPokemon.getInfo());
		}

		/*System.out.println("-----SUPER EFFECTIVE-----");
		for(int i = 0; i < superEffective.size(); ++i) {
			Pokemon curPokemon = superEffective.get(i);

			System.out.println(curPokemon.getInfo());
		}*/
	}

	private static ArrayList<String[]> genCombinations(String[] options, int optionsToChoose, int startIndex, String[] combination, ArrayList<String[]> combinations) {
		if(optionsToChoose == 0) {
			combinations.add(combination.clone());
			// System.out.println("!!! added combination: " + Arrays.toString(combination));	//-----dbg!!!
			return combinations;
		}

		for(int i = startIndex; i < options.length - (optionsToChoose - 1); ++i) {
			combination[combination.length - optionsToChoose] = options[i];
			combinations = genCombinations(options, optionsToChoose - 1, i + 1, combination, combinations);
		}

		return combinations;
	}

	private static ArrayList<ArrayList<String>> findMinimumOptimalTypeCombinations() {
		String[] types = PokemonDatabase.TYPES;
		int bestSECount = 0;
		ArrayList<ArrayList<String>> bestCombinations = new ArrayList<ArrayList<String>>();
		bestCombinations.add(new ArrayList<String>(19));

		for(int i = 0; i < types.length; ++i) {
			int combLength = i + 1;
			ArrayList<String[]> combinations = genCombinations(types, combLength, 0, new String[combLength], new ArrayList<String[]>());

			for(int j = 0; j < combinations.size(); ++j) {
				// System.out.println("!!! checking combination #" + i);	//-----dbg!!!
				String[] curComb = combinations.get(j);
				// System.out.println("!!! curComb = " + Arrays.toString(curComb));	//-----dbg!!!

				ArrayList<ArrayList<Pokemon>> categories = calcEffectiveness(curComb);
				int curCombSECount = categories.get(0).size();
				// System.out.println("!!! curCombSECount = " + curCombSECount);	//-----dbg!!!

				if(curCombSECount > bestSECount) {
					bestSECount = curCombSECount;
					// bestCombinations = new ArrayList<String>(Arrays.asList(curComb));
					bestCombinations = new ArrayList<ArrayList<String>>();
					bestCombinations.add(new ArrayList<String>(Arrays.asList(curComb)));
				}
				else if( curCombSECount == bestSECount && curComb.length == bestCombinations.get(0).size() ) {
					bestCombinations.add(new ArrayList<String>(Arrays.asList(curComb)));
				}
			}
		}

		return bestCombinations;
	}
}