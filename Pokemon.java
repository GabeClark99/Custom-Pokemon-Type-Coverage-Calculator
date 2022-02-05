import java.util.ArrayList;
import java.util.Set;

public class Pokemon {
    public String name;
	public ArrayList<String> types;
	public Set<String> weaknesses;
	public Set<String> resistances;
	public Set<String> immunities;

	public Pokemon(String name, ArrayList<String> types, 
		Set<String> weaknesses, Set<String> resistances, Set<String> immunities) {
			this.name = name;
			this.types = types;
			this.weaknesses = weaknesses;
			this.resistances = resistances;
			this.immunities = immunities;
			return;
	}

	public boolean isWeakness(String type) { return this.weaknesses.contains(type); }
	public boolean isResistance(String type) { return this.resistances.contains(type); }
	public boolean isImmunity(String type) { return this.immunities.contains(type); }
}
