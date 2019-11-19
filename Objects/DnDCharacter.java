package Objects;

//import java.util.Random;

import Statics.Constants;
import creator.CharacterCreate;

public class DnDCharacter
{
    public Item[] backpack;
    public Weapon[] weaponBag;
    public int maxHealth, currentHealth;
    public int[] stats;
    public int[] bonuses;
    public int Str, Dex, Con, Intl, Wis, Cha;
    public String Name, Race, Class;
    public Constants.Gender Gender;
    
    private int experience, profBonus, level, gold;
    private int inventorySpots;
    private Weapon primaryHand;
    private Weapon offHand;
    
    public DnDCharacter() //Happens on initialization of every character
    {
        this.inventorySpots = 30;
        backpack = new Item[30];
        weaponBag = new Weapon[10];
        bonuses = new int[Constants.proficiencies.length];
        Gender = Constants.Gender.Genderless;
        Class = "peasant";
        setLevel(1);
        setBaseStats(); //set all stats to 10
    }
    
    public boolean isBagRoom(int weight)
    {
        int totalWeight = 0;
        for(int i = 0; i < 10; i++)
        {
            if(weaponBag[i] != null)
            {
                totalWeight = totalWeight + 3;
            }
        }
        for(int i = 0; i < 30; i++)
        {
            if(backpack[i] != null)
            {
                totalWeight++;
            }
        }
        if((totalWeight + weight) <= 30)
        {
            return true;
        }
        return false;
    }
    
    public int findBackpackSpace()
    {
        for(int i = 0; i < backpack.length; i++)
        {
            if(backpack[i] == null)
            {
                return i;
            }
        }
        return -1;
    }
    
    public int findWeaponBagSpace()
    {
        for(int i = 0; i < weaponBag.length; i++)
        {
            if(weaponBag[i] == null)
            {
                return i;
            }
        }
        return -1;
    }
    
    public boolean removeItem(Item item)
    {
        
        for(int i = 0; i < weaponBag.length; i++)
        {
            if(item.Name.equals(weaponBag[i].getName()) && item.Description.equals(weaponBag[i].getDescription()))
            {
                weaponBag[i] = null;
                inventorySpots++;
                return true;
            }
            
        }
        return false;
    }
    
    public void addItem(Item item)
    {
        if(isBagRoom(1))
        {
            backpack[findBackpackSpace()] = item;
            inventorySpots--;
        }
        else
        {
            System.out.println("Couldn't add item to bag, there's no room!");
        }
    }
    
    public void addItem(Weapon weapon)
    {
        if(isBagRoom(3))
        {
            weaponBag[10 - inventorySpots] = weapon;
            inventorySpots--;
        }
        else
        {
            System.out.println("Couldn't add item to bag, there's no room!");
        }
    }
    
    public Weapon getPrimaryHand()
    {
        return primaryHand;
    }
    
    public void setPrimaryHand(Weapon weapon)
    {
        this.primaryHand = weapon;
    }
    
    public Weapon getOffHand()
    {
        return offHand;
    }
    
    public void setOffHand(Weapon weapon)
    {
        this.offHand = weapon;
    }
    public int getGold()
    {
        return gold;
    }
    
    public void setGold(int setGold)
    {
        if(setGold >= 0) //You can't have negative gold!
            this.gold = setGold;
        
    }
    
    public void addGold(int add)
    {
        if((gold + add) >= 0) //You can't subtract gold past 0
            this.gold = gold + add;
    }
    
    public int getProfBonus()
    {
        
        for(int i = 0; i < Constants.profBonusChart.length; i++)
        {
            if(Constants.profBonusChart[i] > level)
            {
                if(i == 0)
                {
                    this.profBonus = 0;
                    return profBonus;
                }
                else
                {
                    this.profBonus = i + 1; //Bonus starts at 2 and increases by 1 at 5,9,13,17
                    return profBonus;
                }
                
            }
        }
        this.profBonus = 6;
        
        return profBonus;
    }
    
    public int getModfier(String skill)
    {
        getProfBonus();
        refresh(); //Makes sure there wasn't change to stats. Shouldn't be necessary given how rarely stats change, but reassurance is worth computation
        for(int i = 0; i < Constants.proficiencies.length; i++)
        {
            if(Constants.proficiencies[i].equalsIgnoreCase(skill) || (i == 2 && skill.equalsIgnoreCase("sleight")))
                return ((bonuses[i]*profBonus) + (stats[Constants.proficiencyType[i] - 1])/2 - 5);
            if(Constants.proficiencies[i].equalsIgnoreCase(skill) || (i == 9 && skill.equalsIgnoreCase("animal")))
                return ((bonuses[i]*profBonus) + (stats[Constants.proficiencyType[i] - 1])/2 - 5);
        }
        System.out.println("This is not a known skill, modifier is 0.");
        return -100;
    }
    
    public void refresh()
    {
        stats = new int[6];
        stats[0] = Str;
        stats[1] = Dex;
        stats[2] = Con;
        stats[3] = Intl;
        stats[4] = Wis;
        stats[5] = Cha;
    }
    
    public int getMaxHealth()
    {
        return maxHealth;
    }
    
    public int getCurrentHealth()
    {
        return currentHealth;
    }
    
    public boolean setSkill(String prof)
    {
        for(int i = 0; i < Constants.proficiencies.length; i++)
        {
            if(Constants.proficiencies[i].equalsIgnoreCase(prof)|| (i == 2 && prof.equalsIgnoreCase("sleight")) || (i == 9 && prof.equalsIgnoreCase("Animal")))
            {
                this.bonuses[i] = 1;
                return true;
            }
        }
        return false;
    }
    
    public void setClass(String newClass)
    {
        boolean foundClass = false;
        for(int i = 0; i < Constants.classes.length; i++)
        {
            if(newClass.equalsIgnoreCase(Constants.classes[i]))
            {
                foundClass = true;
                Class = Constants.classes[i];
                this.maxHealth = 0;
                changeHealth(level - 1);
                this.currentHealth = maxHealth;
            }
        }
        if(!foundClass)
        {
            System.out.println("No class with that name could be found. Class is not changed.");
        }
        
    }
    
    public void changeHealth(int change)
    {
        int gain = 0;
        if(Class == null)
        {
            Class = Constants.classes[0];
        }
        for(int i = 0; i < Constants.classes.length; i++)
        {
            if(Class.equalsIgnoreCase(Constants.classes[i]))
            {
                if(level == 0 || level == 1)
                {
                    this.maxHealth = Math.max((Constants.healthDice[i] + (Con/2) - 5),5); //For the sake of my mental health, set minimum level 1 health to 5
                    this.currentHealth = maxHealth;
                }
                else
                {
                    for(int j = 0; j < Math.abs(change); j++) //Absolute value of change because change can be negative
                    {
                        if(change > 0)
                        {
                            gain = CharacterCreate.roll(Constants.healthDice[i], Con);
                            System.out.println(Name + " gained " + Math.max(gain,0) + " health!");
                            this.maxHealth = maxHealth + Math.max(gain,0); //You can't lose health from leveling up
                            this.currentHealth = maxHealth;
                        }
                        else
                        {
                            gain = CharacterCreate.roll(Constants.healthDice[i], Con);
                            System.out.println(Name + " lost " + Math.max(gain,0) + " health!");
                            this.maxHealth = maxHealth - Math.max(gain,0); //You can't gain health from leveling down
                            this.currentHealth = maxHealth;
                        }
                        
                    }
                }
                break;
            }
        }
        
    }
    
    public int getLevel()
    {
        return level;
    }
    
    public int calculateLevel()
    {
        for(int i = 0; i < 20; i++)
        {
            if(experience < Constants.expThreshhold[i])
            {
                if((i + 1)!= level)
                    changeHealth((i + 1) - level);
                return i + 1;
            }
        }
        if(level != 20)
            changeHealth(20 - level); //I've stared at health gains so long that I'm not even sure this is correct. But it works. I must be psyching myself out.
        return 20;
        
    }
    
    public void setLevel(int newLevel)
    {
        int oldLevel = level;
        if(newLevel > 20)
            newLevel = 20;
        if(newLevel < 1)
            newLevel = 1;
        this.experience = Constants.expThreshhold[newLevel - 1];
        this.level = newLevel;
        changeHealth(newLevel - oldLevel);
    }
    
    public int getExp()
    {
        return experience;
    }
    
    public void setExp(int newExp)
    {
        this.experience = newExp;
        this.level = calculateLevel();
    }
    
    public void gainExp(int newExp)
    {
        this.experience = experience + newExp;
        this.level = calculateLevel();
    }
	
	public void rollEverything()
    {
        
        Str = statRoller();
        Dex = statRoller();
        Con = statRoller();
        Intl = statRoller();
        Wis = statRoller();
        Cha = statRoller();
        refresh();
    }
    
    private int statRoller() //Each stat is made by rolling 4 d6 dice and adding together the largest 3.
    {
        int a, b, c, d, stat;
        a = CharacterCreate.roll(6, 10);
        b = CharacterCreate.roll(6, 10);
        c = CharacterCreate.roll(6, 10);
        d = CharacterCreate.roll(6, 10);
        int min = Math.min(Math.min(a,b),Math.min(c,d)); //We don't know which variable out of a, b, c, d is the biggest but we can find the minimum value
        stat = a + b + c + d - min; //In order to get rid of the lowest value, add all of them up then remove the lowest value
        return stat;
    }
    
    private void setBaseStats()
    {
    	Str = 10;
    	Cha = 10;
    	Con = 10;
    	Intl = 10;
    	Dex = 10;
    	Wis = 10;
    }

}