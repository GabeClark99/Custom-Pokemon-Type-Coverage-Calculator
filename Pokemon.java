import java.util.Set;

public class Pokemon {
    private String name;
    private int primaryType;
    private int secondaryType;
	private Set<Integer> weaknesses;
	private Set<Integer> resistances;
	private Set<Integer> immunities;

    public void setName(String newName) { this.name = newName; return; }
    public String getName() { return this.name; }

    public void setPrimaryType(int newPrimaryType) {
		this.primaryType = newPrimaryType;
		return;
	}
	public void setSecondaryType(int newSecondaryType) {
		this.secondaryType = newSecondaryType;
		return;
	}

    public int getPrimaryType() {
		return this.primaryType;
	}
    public int getSecondaryType() {
		return this.secondaryType;
	}

	public void addWeakness(int newWeakness) {
		this.weaknesses.add(newWeakness);
		return;
	}
	public void addResistance(int newResistance) {
		this.resistances.add(newResistance);
		return;
	}
	public void addImmunity(int newImmunity) {
		this.immunities.add(newImmunity);
		return;
	}

	public boolean isWeakness(int type) {
		return this.weaknesses.contains(type);
	}
	public boolean isResistance(int type) {
		return this.resistances.contains(type);
	}
	public boolean isImmunity(int type) {
		return this.immunities.contains(type);
	}
}
