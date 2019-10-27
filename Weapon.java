//Class design for Weapons for DND character creation
//Author: Tyler Clark
//Designed in October 2019

package dnd;

public class Weapon
{
    public String name, description;
    private int[] damageDice; //If the weapon does 1d8, then
    private String[] damageType;
    private String weaponType, rarity;
    private int range;
    
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
    
    public void setName(String name)
    {
        this.name = name;
    }
    
    public String getDescription()
    {
        return description;
    }
    
    public void setDescription(String newDescript)
    {
        this.description = newDescript;
    }
    
    public String getWeaponType()
    {
        return weaponType;
    }
    
    public void setWeaponType(String type)
    {
        this.weaponType = type;
        if(name == null)
            this.name = type;
    }
    
    public String getRarity()
    {
        return rarity;
    }
    
    public void setRarity(String rarity)
    {
        this.rarity = rarity;
    }
    
    public int[] getDamageDice()
    {
        return damageDice;
    }
    
    public void setDamageDice(int[] dice)
    {
        this.damageDice = dice;
    }
    
    public String[] getDamageType()
    {
        return damageType;
    }
    
    public void setDamageType(String[] damageType)
    {
        this.damageType = damageType;
    }
    
    public int getRange()
    {
        return range;
    }
    
    public void setRange(int range)
    {
        this.range = range;
    }
}