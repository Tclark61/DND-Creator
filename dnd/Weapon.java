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
        this.range = 0;
        this.modifier = "N/A";
        
    }
    
    public Shield(Weapon weapon)
    {
        this.weaponType = "Shield";
        this.range = 0;
        this.rarity = weapon.getRarity();
        this.modifier = "N/A";
        this.name = weapon.getName();
        if(this.name == null)
            this.name = "Shield";
        this.description = weapon.getDescription();
        this.damageType = new String[1];
        this.damageType[0] = "N/A";
        this.damageDice = new int[2];
        this.damageDice[0] = 0;
        this.damageDice[1] = 1;
        
        
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
    public int[] damageDice; //If the weapon does 1d8, then damageDice[0] = 1, damageDice[1] = 8.
    public String[] damageType;
    public String weaponType, rarity;
    public String modifier;
    public int range;
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
    
    public int getDamageDice(int index)
    {
        if(index < damageDice.length)
            return damageDice[index];
        else
            return 0;
    }
    
    public void setDamageDice(int[] dice)
    {
        this.damageDice = dice;
    }
    
    public String[] getDamageType()
    {
        return damageType;
    }
    
    public String getDamageType(int index)
    {
        if(index < damageType.length)
            return damageType[index];
        else
            return "N/A";
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