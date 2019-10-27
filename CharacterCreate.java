//Character Creation for DND
//Author: Tyler Clark
//Created in October 2019

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import dnd.*;


class Character
{
    public boolean current;
    private String name, race, gender, charClass;
    public int maxHealth, currentHealth;
    public int[] stats;
    private int experience, profBonus, level, str, dex, con, intl, wis, cha, gold;
    private static int[] profBonusChart = {1,5,9,13,17};
    public static int[] proficiencyType = {1,2,2,2,4,4,4,4,4,5,5,5,5,5,6,6,6,6};
    public static String[] proficiencies = {"Athletics", "Acrobatics", "Sleight of Hand", "Stealth", 
    "Arcana", "History","Investigation","Nature",
    "Religion","Animal Handling","Insight","Medicine","Perception",
    "Survival","Deception","Intimidation","Performance","Persuasion"};
    public int[] bonuses;
    public static String[] variables = {"Name", "Strength", "Dexterity", "Constitution", "Intelligence", "Wisdom", 
    "Charisma", "Class", "Gender","Level", "Experience", "Proficiencies"};
    public static String[] classes = {"Peasant", "Fighter","Wizard", "Rogue","Cleric","Paladin","Warlock","Barbarian"};
    public static int[] healthDice = {3,10,6,8,8,10,6,12}; //Dice used to calculate Health for a specific class.
    public static int[] expThreshhold = {0,300,900,2700,6500,14000,23000,34000,48000,64000,85000,100000,120000,140000,
    165000, 195000, 225000, 265000, 305000, 355000}; //The set experience threshholds for dnd 5e. They do not follow a formula, which was pretty annoying
    
    
    public Character() //Happens on initialization of every character
    {
        bonuses = new int[proficiencies.length];
        this.gender = "Genderless";
        setLevel(1);
        setClass("Peasant");
        
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
        
        for(int i = 0; i < profBonusChart.length; i++)
        {
            if(profBonusChart[i] > level)
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
        for(int i = 0; i < proficiencies.length; i++)
        {
            if(proficiencies[i].equalsIgnoreCase(skill) || (i == 2 && skill.equalsIgnoreCase("sleight")))
                return ((bonuses[i]*profBonus) + (stats[proficiencyType[i] - 1])/2 - 5);
        }
        System.out.println("This is not a known skill, modifier is 0.");
        return -100;
    }
    
    public void refresh()
    {
        stats = new int[6];
        stats[0] = str;
        stats[1] = dex;
        stats[2] = con;
        stats[3] = intl;
        stats[4] = wis;
        stats[5] = cha;
    }
    
    
    public String getCharClass()
    {
        return charClass;
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
        for(int i = 0; i < proficiencies.length; i++)
        {
            if(proficiencies[i].equalsIgnoreCase(prof)|| (i == 2 && prof.equalsIgnoreCase("sleight")) || (i == 9 && prof.equalsIgnoreCase("Animal")))
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
        for(int i = 0; i < classes.length; i++)
        {
            if(newClass.equalsIgnoreCase(classes[i]))
            {
                foundClass = true;
                charClass = classes[i];
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
        if(charClass == null)
        {
            this.charClass = classes[0];
        }
        for(int i = 0; i < classes.length; i++)
        {
            if(charClass.equalsIgnoreCase(classes[i]))
            {
                if(level == 0 || level == 1)
                {
                    this.maxHealth = Math.max((healthDice[i] + (con/2) - 5),5); //For the sake of my mental health, set minimum level 1 health to 5
                    this.currentHealth = maxHealth;
                }
                else
                {
                    for(int j = 0; j < Math.abs(change); j++) //Absolute value of change because change can be negative
                    {
                        if(change > 0)
                        {
                            gain = CharacterCreate.roll(healthDice[i],con);
                            System.out.println(getName() + " gained " + Math.max(gain,0) + " health!");
                            this.maxHealth = maxHealth + Math.max(CharacterCreate.roll(healthDice[i],con),0); //You can't lose health from leveling up
                            this.currentHealth = maxHealth;
                        }
                        else
                        {
                            gain = CharacterCreate.roll(healthDice[i],con);
                            System.out.println(getName() + " lost " + Math.max(gain,0) + " health!");
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
            if(experience < expThreshhold[i])
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
        this.experience = expThreshhold[newLevel - 1];
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
    
    public String getGender()
    {
        return gender;
    }
    
    public void setGender(String newGender)
    {
        this.gender = newGender;
    }
    
    public void setCurrent(boolean curr)
    {
        this.current = curr;
    }
    
    public String getName()
    {
        return name;
    }
    
    public void setName(String newName)
    {
        this.name = newName;
        
    }
    
    public int getStr()
    {
        return str;
    }
    
    public void setStr(int newVar)
    {
        this.str = newVar;
    }
    
    public int getDex()
    {
        return dex;
    }
    
    public void setDex(int newVar)
    {
        this.dex = newVar;
    }
    
    public int getCon()
    {
        return con;
    }
    
    public void setCon(int newVar)
    {
        this.con = newVar;
    }
    
    public int getIntl()
    {
        return intl;
    }
    
    public void setIntl(int newVar)
    {
        this.intl = newVar;
    }
    
    public int getWis()
    {
        return wis;
    }
    
    public void setWis(int newVar)
    {
        this.wis = newVar;
    }
    
    public int getCha()
    {
        return cha;
    }
    
    public void setCha(int newVar)
    {
        this.cha = newVar;
    }
    
    public void rollEverything()
    {
        
        setStr(statRoller());
        setDex(statRoller());
        setCon(statRoller());
        setIntl(statRoller());
        setWis(statRoller());
        setCha(statRoller());
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

}

class CharacterCreate
{
 
    public static String workingCommands = "Help, Quit, Get [Variable], Set [Variable] [Value], Set [Class], Variables, Roll Stats, Roll [Number of Sides on Die], Roll [Stat/Skill]," 
    + "\nNew Character, New Character [Name], List, Give Exp [Value], Skills, Info, Switch [Character Name]";
    public static String workingVariables = "Name, Str, Dex, Con, Wis, Intl, Cha, Class, Level, Exp, Proficiency, Gender";
    
    public static boolean isInteger(String str) {
    if (str == null) {
        return false;
    }
    int length = str.length();
    if (length == 0) {
        return false;
    }
    int i = 0;
    if (str.charAt(0) == '-') {
        if (length == 1) {
            return false;
        }
        i = 1;
    }
    for (; i < length; i++) {
        char c = str.charAt(i);
        if (c < '0' || c > '9') {
            return false;
        }
    }
    return true;
    }
    
    public static Character findCurrent(ArrayList<Character> roster)
    {
        Character temp;
        for(int i = 0; i < roster.size(); i++)
        {
            temp = roster.get(i);
            if(temp.current)
            {
                return temp;
            }
        }
        return null;
    }
    
    public static ArrayList<Character> textInput(String input, ArrayList<Character> roster, String[] names)
    {
        boolean found = false;
        int buffer = 0, test;
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();
        Character player = findCurrent(roster); //Find the current character to work on
        int roll = 0, modifier = 0;
        String[] words = input.split(" "); //Split the input into a series of words
        
        if(player == null)
        {
            System.out.println("Error: No current player. Please restart.");
            return roster;
        }
        
        switch (words[0].toLowerCase()){ //Check what was in the first word given (not case sensitive)
            
            case "info":
                System.out.println(player.getName() + " is a level " + player.getLevel() + " " + player.getGender() + " " + player.getCharClass());
                System.out.println("Health: " + player.getCurrentHealth() + "/" + player.getMaxHealth());
                System.out.println("---------------------------------");
                player.refresh();
                for(int i = 0; i < 6; i++)
                {    
                    System.out.println(player.variables[i + 1] + ": " + player.stats[i]);
                }
                System.out.println("---------------------------------");
                System.out.println("Proficiencies:");
                for(int i = 0; i < player.proficiencies.length; i++)
                {
                    if(player.bonuses[i] == 1)
                        System.out.println(player.proficiencies[i] + ": " + player.getModfier(player.proficiencies[i]));
                }
                break;
            case "skills":
                for(int i = 0; i < player.proficiencies.length; i++)
                {
                    System.out.println(player.proficiencies[i] + ": " + player.getModfier(player.proficiencies[i]));
                }
                break;
            
            case "give":
                if(words.length == 3 && isInteger(words[2]))
                {
                    if(words[1].equalsIgnoreCase("exp"))
                    {
                        player.gainExp(Integer.parseInt(words[2]));
                        System.out.println(player.getName() + " has " + player.getExp() + " and is level " + player.getLevel() + "!");
                    }
                    if(words[1].equalsIgnoreCase("gold"))
                    {
                        if((Integer.parseInt(words[2]) + player.getGold()) >= 0)
                        {
                            player.addGold(Integer.parseInt(words[2]));
                            System.out.println(player.getName() + " gained " + Integer.parseInt(words[2]) + " gold, and now has " + player.getGold() + " gold pieces!");
                        }
                        else
                            System.out.println("That would result in a negative gold amount, and you can't have negative gold.");
                        
                    }
                }
                else
                {
                    System.out.println("Could not understand. Did you mean 'Give exp' or 'Give gold'? ");
                }
                break;
                
            
            case "list":
                System.out.println("Current Party Members:");
                for(int i = 0; i < roster.size(); i++)
                {
                    System.out.println(roster.get(i).getName() + ", the level " + roster.get(i).getLevel() + " " + roster.get(i).getCharClass());
                }
                break;
            case "switch":
                if(words.length == 2 || words.length == 3)
                {
                    if(words[1].equalsIgnoreCase("to"))
                    {
                        //if the word 'to' is added, don't penalize the user. Just shift the index by 1.
                        buffer = 1;
                    }
                    else
                    {
                        //This should be completely unnecessary but I'm being overly careful.
                        buffer = 0;
                    }
                    Character temp = new Character();
                    for(int i = 0; i < roster.size(); i++){
                        temp = roster.get(i);
                        if(temp.getName().equalsIgnoreCase(words[1 + buffer])){
                            player.setCurrent(false);
                            temp.setCurrent(true);
                            System.out.println("Switched to " + temp.getName());
                            found = true;
                            break;
                        }

                    }
                    if(!found)
                    {
                        System.out.println("No party member with the name " + words[1 + buffer] + " was found.");
                        break;
                    }
                    
                }
                else
                {
                    System.out.println("Unable to switch, please specify the name of character to switch to.");
                }
                break;
            case "new":
                if((words[1].equalsIgnoreCase("character") || words[1].equalsIgnoreCase("char")) && words.length == 2)
                {
                    Character backup = new Character();
                    System.out.println("Would you like a pre-made character? If not, character will be blank. Y/N");
                    String answer = new String();
                    answer = scanner.nextLine();
                    if(answer.equalsIgnoreCase("Y") || answer.equalsIgnoreCase("YES"))
                    {
                        backup.setName(names[random.nextInt(names.length)]); //Set the name equal to a random number in between 0 and (length of names.txt - 1)
                        backup.setLevel(1);
                        backup.setClass("Peasant");
                        System.out.println("This character's name will be " + backup.getName());
                        buffer = random.nextInt() % 2;
                        if(buffer == 1)
                            backup.setGender("Male");
                        else
                            backup.setGender("Female");
                        buffer = 0;
                        backup.rollEverything();
                        System.out.println("Rolling " + backup.getName() + "'s stats....");
                        for(int i = 0; i < 6; i++)
                        {
                            System.out.println(backup.variables[i + 1] + ": " + backup.stats[i]); //Prints out the name of stats and their values as located in the Char class
                        }
                    }
                    player.setCurrent(false); //Make the current player no longer current
                    backup.setCurrent(true); //Set the newly created character to current instead
                    roster.add(backup);
                    System.out.println(backup.getName() + " successfully added to the party!");
                    break;
                }
                if(words[1].equalsIgnoreCase("character") && words.length == 3){
                    Character backup = new Character();
                    backup.setName(words[2]);
                    System.out.println("Would you like a pre-made character with this name? If not, character's stats will be blank. Y/N");
                    String answer = new String();
                    answer = scanner.nextLine();
                    if(answer.equalsIgnoreCase("Y") || answer.equalsIgnoreCase("YES")){
                        backup.rollEverything();
                        buffer = random.nextInt() % 2;
                        if(buffer == 1)
                            backup.setGender("Male");
                        else
                            backup.setGender("Female");
                        buffer = 0;
                        System.out.println("Rolling " + backup.getName() + "'s stats....");
                        for(int i = 0; i < 6; i++)
                        {
                        System.out.println(backup.variables[i + 1] + ": " + backup.stats[i]);
                        }
                    }
                    player.setCurrent(false);
                    backup.setCurrent(true);
                    roster.add(backup);
                    System.out.println(backup.getName() + " successfully added to the party!");
                    break;
                }
                else
                {
                    System.out.println("Unable to understand prompt. Are you trying to say 'new character'?");
                }
                break;
            case "classes":
                for(int i = 0; i < player.classes.length; i++)
                {
                    System.out.println(player.classes[i]);
                }
                break;
            case "help":
                System.out.println("Currently working commands:\n{" + workingCommands + "}");
                System.out.println("Type Variables to list all variables. Type Classes to list all classes. Type Skills to list all skills.");
                break;
            case "variables":
                System.out.println("Currently working variables:\n{" + workingVariables + "}");
                break;
            case "get":
                if(words.length == 2 || (words[1].equalsIgnoreCase("skill") && words.length == 3))
                {
                    switch(words[1].toLowerCase()){
                        case "skill":
                            test = player.getModfier(words[2]);
                            if(test != -100)
                            {
                                System.out.println(player.getName() + "'s proficiency in " + words[2] + " is " + test);
                            }
                            break;
                        case "gold":
                            System.out.println(player.getName() + "'s current gold is " + player.getGold());
                            break;                       
                        case "health":
                            System.out.println(player.getName() + "'s current health is " + player.getMaxHealth());
                            break;
                        case "gender":
                            System.out.println(player.getName() + "'s gender is " + player.getGender());
                            break;
                        case "class":
                            System.out.println(player.getName() + "'s class is " + player.getCharClass());
                            break;
                        case "level":
                            System.out.println(player.getName() + "'s level is " + player.getLevel());
                            break;
                        case "exp":
                            System.out.println(player.getName() + "'s experience is " + player.getExp());
                            break;
                        case "name":
                            System.out.println("Current character name is " + player.getName() + ".");
                            break;
                        case "str":
                            System.out.println("Current character strength is " + player.getStr() + ".");
                            break;
                        case "dex":
                            System.out.println("Current character dexterity is " + player.getDex() + ".");
                            break;
                        case "con":
                            System.out.println("Current character constitution is " + player.getCon() + ".");
                            break; 
                        case "wis":
                            System.out.println("Current character wisdom is " + player.getWis() + ".");
                            break;
                        case "intl":
                            System.out.println("Current character intelligence is " + player.getIntl() + ".");
                            break; 
                        case "cha":
                            System.out.println("Current character charisma is " + player.getCha() + ".");
                            break;                            
                        default:
                            System.out.println("Unable to understand variable for get command.\n" +
                                "For a list of working variables, please enter 'variables'");
                            break;
                        
                    }
                }
                else
                {
                    System.out.println("Unable to read variable for get command, please try again.");
                }
                break;
            case "set":
                //This only goes off if you can parse the second word or if second word is name/class
                if(words.length == 3 && (isInteger(words[2]) || words[1].equalsIgnoreCase("name") || words[1].equalsIgnoreCase("class") || words[1].equalsIgnoreCase("gender")))
                {
                    switch(words[1].toLowerCase()){
                        case "gold":
                            if(Integer.parseInt(words[2]) >= 0)
                            {
                                player.setGold(Integer.parseInt(words[2]));
                                System.out.println(player.getName() + " gained " + Integer.parseInt(words[2]) + " gold, and now has " + player.getGold() + " gold pieces!");
                            }
                            else
                                System.out.println("That would result in a negative gold amount, and you can't have negative gold.");
                            break;
                        case "gender":
                            player.setGender(words[2]);
                            System.out.println(player.getName() + " is a " + player.getGender() + ", got it!"); //I'm walking into this being so easily abusable by letting any string be added here.
                            break;
                        case "class":
                            player.setClass(words[2]);
                            System.out.println(player.getName() + " is class " + player.getCharClass() + " and has " + player.getMaxHealth() + " health.");
                            break;
                        case "level":
                            player.setLevel(Integer.parseInt(words[2]));
                            System.out.println(player.getName() + " is now level " + player.getLevel() + "!");
                            break;
                        case "exp":
                            player.setExp(Integer.parseInt(words[2]));
                            System.out.println(player.getName() + " now has " + player.getExp() + " experience!" );
                            break;
                        case "name":
                            player.setName(words[2]); //Use the third word as the name of the character
                            System.out.println("Current character name set to " + player.getName() + ".");
                            break;
                        case "str":
                            player.setStr(Integer.parseInt(words[2]));
                            System.out.println("Current character strength set to " + player.getStr() + ".");
                            break;
                        case "dex":
                            player.setDex(Integer.parseInt(words[2]));
                            System.out.println("Current character dexterity set to " + player.getDex() + ".");
                            break;
                        case "con":
                            player.setCon(Integer.parseInt(words[2]));
                            System.out.println("Current character constitution set to " + player.getCon() + ".");
                            break; 
                        case "wis":
                            player.setWis(Integer.parseInt(words[2]));
                            System.out.println("Current character wisdom set to " + player.getWis() + ".");
                            break;
                        case "intl":
                            player.setIntl(Integer.parseInt(words[2]));
                            System.out.println("Current character intelligence set to " + player.getIntl() + ".");
                            break; 
                        case "cha":
                            player.setCha(Integer.parseInt(words[2]));
                            System.out.println("Current character charisma set to " + player.getCha() + ".");
                            break;                            
                        default:
                            System.out.println("Unable to understand variable for set command.\n" +
                                "For a list of working variables, please enter 'variables'");
                            break;
                        
                    }
                }
                else if (words[1].equalsIgnoreCase("proficiency") && words.length >= 3)
                {
                    if(player.setSkill(words[2]))
                                System.out.println(player.getName() + " is now proficient in " + words[2] + "!");
                            else
                                System.out.println("Could not find that proficiency. Try typing 'proficiency' for a list of possible skills.");
                }
                else
                {
                    System.out.println("No variable given to set, please try again.");
                    break;
                }
                break;
            case "roll":
                if(words.length == 1) //If the user just typed roll and that's it, roll a default d20
                {
                    roll = roll(20, 10);
                    System.out.println("Rolled a " + roll + "!");
                    break;
                }
                if(words.length == 2 )
                {
                    switch (words[1].toLowerCase())
                    {
                        case "stats":
                            player.rollEverything();
                            System.out.println("Rolling " + player.getName() + "'s stats....");
                            for(int i = 0; i < 6; i++)
                            {
                                System.out.println(player.variables[i + 1] + ": " + player.stats[i]);
                            }
                            break;
                        case "str":
                            System.out.println("Rolled a " + roll(20, player.getStr()) + " with strength!");
                            break;
                        case "dex":
                            System.out.println("Rolled a " + roll(20, player.getDex()) + " with dexterity!");
                            break;
                        case "con":
                            System.out.println("Rolled a " + roll(20, player.getCon()) + " with constitution!");
                            break;
                        case "intl":
                            System.out.println("Rolled a " + roll(20, player.getIntl()) + " with intelligence!");
                            break;
                        case "wis":
                            System.out.println("Rolled a " + roll(20, player.getWis()) + " with wisdom!");
                            break;
                        case "cha":
                            System.out.println("Rolled a " + roll(20, player.getCha()) + " with charisma!");
                            break;
                        default:
                            if(isInteger(words[1]))
                            {
                                System.out.println("Rolled a " + roll(Integer.parseInt(words[1]), 10) + "!");
                            }
                            else
                            {
                                int mod = player.getModfier(words[1]);
                                if(mod != -100)
                                {
                                    System.out.println("Rolled a " + (roll(20,10) + mod) + " with " + words[1] + "!");
                                }
                            }
                            break;
                    }
                    break;
                }
            default:
                System.out.println("Command could not be understood. Please try again.");
                break;
            
        }
        return roster;
    }
    
    
    public static int roll(int dice, int modifier)
    {
        Random random = new Random();
        int roll = random.nextInt(dice) + 1;
        if(roll == 1 && dice == 20)
            System.out.println("Critical failure!");
        if(roll == 20 && dice == 20)
            System.out.println("Critical Success!");
        roll = roll + (modifier/2) - 5;
        return roll;
    }
    //written by MAM131
    public static String shorten(String word) //shortens the word so you can use shortened and long version of the stats.
    {
        if(word.equalsIgnoreCase("Strength"))
        {
            word = "str";
            return word;
            
        }
        else if(word.equalsIgnoreCase("Dexterity"))
        {
            word = "dex";
            return word;
            
        }
        else if(word.equalsIgnoreCase("Consitution"))
        {
            word = "con";
            return word;
            
        }
        else if(word.equalsIgnoreCase("Intelligence"))
        {
            word = "intl";
            return word;
            
        }
        else if(word.equalsIgnoreCase("Wisdom"))
        {
            word = "wis";
            return word;
            
        }
        else if(word.equalsIgnoreCase("Charisma"))
        {
            word = "cha";
            return word;
            
        }
        return word;
    }

    public static void main(String args[])
    {
        boolean quit = false;
        String[] names;
        String nameLine = "Failed";
        Scanner scanner = new Scanner(System.in);
        Character first = new Character();
        String line = new String();
        Random random = new Random();
        int binary;
        first.calculateLevel();
        first.setStr(10);//set all stats to 10
        first.setDex(10);
        first.setCon(10);
        first.setIntl(10);
        first.setWis(10);
        first.setCha(10);
        first.setLevel(1); //set level
        try //Try to find the text file in, the 'try' and 'catch' test to see if the file is found
        {
            BufferedReader in = new BufferedReader(new FileReader("names.txt"));
            try
            {
                nameLine = in.readLine(); //If it passes both tests, scan in the first line (All of the first names)
            }
            catch (IOException io)
            {
                names = new String[1]; //If it fails either one, just put the default name in instead so that there's still a name to add
                names[0] = "John";
            }
        }
        catch (FileNotFoundException ex)
        {
            names = new String[1];
            names[0] = "John";
        }
        names = nameLine.split(", "); //This splits the single line into multiple smaller strings (individual names) by splitting it at every comma followed by a space
        binary = random.nextInt() % 2;
        if(binary == 1)
            first.setGender("Male");
        else
            first.setGender("Female");
        first.setCurrent(true);
        first.setName(names[random.nextInt(names.length)]); //random name
        ArrayList<Character> roster = new ArrayList<Character>(); //New ArrayList of characters, ArrayLists are much better than arrays at adding a continuous amount of entries
        roster.add(first); //Add character to roster
        
        dnd.WeaponsLibrary library =  new dnd.WeaponsLibrary("weapons.txt");
        ArrayList<dnd.Weapon> weaponLib = library.weaponLib;
        dnd.Weapon longsword = new dnd.Weapon("longsword");
        weaponLib.add(longsword);
        System.out.println("Welcome to my text based character creator! Type 'HELP' for a list of commands.");
        while(!quit)
        {
            line = scanner.nextLine(); //Have the user input a text line
            if(line.equalsIgnoreCase("quit"))
            {
                break;
            }
            roster = textInput(line, roster, names); //Analyze the line using textInput function, update the roster with any changes
        }
        scanner.close();
    }
}