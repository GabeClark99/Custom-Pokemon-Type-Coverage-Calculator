import java.util.Set;

public class Type {
    private String name;
    private Set<Type> targetsSE;
    private Set<Type> targetsNVE;

    public void setName(String newName) { this.name = newName; return; }
    public String getName() { return this.name; }

    public void addTargetSE(Type newTargetSE) { this.targetsSE.add(newTargetSE); return; }
    public boolean isTargetSE(Type target) {
        if(this.targetsSE.contains(target)) {
            return true;
        }
        else {
            return false;
        }
    }

    public void addTargetNVE(Type newTargetNVE) { this.targetsNVE.add(newTargetNVE); return; }
    public boolean isTargetNVE(Type target) {
        if(this.targetsNVE.contains(target)) {
            return true;
        }
        else {
            return false;
        }
    }

}
