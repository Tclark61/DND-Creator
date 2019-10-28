//Class design for Weapons for DND character creation
//Author: Tyler Clark
//Designed in October 2019

package dnd;

class Shield extends Weapon
{
    private int armorBonus;
    
    public Shield()
    {
        this.handed = 1;
        this.weaponType = "Shield";
        
    }
    
    public int getArmorBonus()
    {
        return armorBonus;
    }
    
    public void setArmorBonus(int ac)
    {
        this.armorBonus = ac;
    }
}

public class Weapon
{
    public String name, description;
    private int[] damageDice; //If the weapon does 1d8, then
    private String[] damageType;
    public String weaponType, rarity;
    private String modifier;
    private int range;
    public int handed;
    
    public Weapon()
    {
        this.name = null;
        this.handed = 2;
    }
    
    public Weapon(String name, String description)
    {
        this.name = name;
        this.description = description;
        this.handed = 2;
    }
    
    public Weapon(String name)
    {
        this.name = name;
        this.handed = 2;
    }
    
    public int getHanded()
    {
        return handed;
    }
    
    public void setHanded(int handed) //Make sure if a weapon isn't properly initialized, it just defaults to two handed.
    {
        if(handed == 1)
        {
            this.handed = 1;
        }
        else
        {
            this.handed = 2;
        }
    }
    public String getModifier()
    {
        return modifier;
    }
    
    public void setModifier(String modifier)
    {
        this.modifier = modifier;
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