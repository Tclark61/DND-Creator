package dnd;

public class Weapon
{
    public String name, description;
    private int[] damageDice; //If the weapon does 1d8, then
    private String damageType, weaponType;
    
    public Weapon()
    {
        this.name = null;
    }
    
    public Weapon(String name, String description)
    {
        this.name = name;
        this.description = description;
    }
    
    public Weapon(String name)
    {
        this.name = name;
    }
    
    public String getName()
    {
        return name;
    }
    
    
}