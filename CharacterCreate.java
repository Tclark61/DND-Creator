//Character Creation for DND
//Author: Tyler Clark
//Created in October 2019


import java.util.Scanner;
import java.util.ArrayList;
import java.util.Random;
import java.io.*;

class Character
{
    public boolean current;
    private String name;
    private String charClass;
    private int level;
    public int maxHealth;
    public int currentHealth;
    private int experience;
    private int str;
    private int dex;
    private int con;
    private int intl;
    private int wis;
    private int cha;
    public int[] stats;
    public static String[] variables = {"Name", "Strength", "Dexterity", "Constitution", "Intelligence", "Wisdom", 
    "Charisma", "Class", "Gender","Level", "Experience"};
    public static String[] classes = {"Peasant", "Fighter","Wizard", "Rogue","Cleric","Paladin","Warlock"};
    public static int[] healthDice = {3,10,6,8,8,10,6}; //What dice is used to calculate health for a specific class
    public static int[] expThreshhold = {0,300,900,2700,6500,14000,23000,34000,48000,64000,85000,100000,120000,140000,
    165000, 195000, 225000, 265000, 305000, 355000}; //The set experience threshholds for dnd 5e. They do not follow a formula, which was pretty annoying
    
    
    
    private void refresh()
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
                changeHealth(level);
            }
        }
        if(!foundClass)
        {
            System.out.println("No class with that name could be found. Class is not changed.");
        }
        
    }
    public void changeHealth(int change)
    {
        if(charClass == null)
        {
            this.charClass = classes[0];
        }
        for(int i = 0; i < classes.length; i++)
        {
            if(charClass.equalsIgnoreCase(classes[i]))
            {
                this.maxHealth = Math.max((healthDice[i] + (con/2) - 5),5); //For the sake of my mental health, set minimum level 1 health to 5
                for(int j = 1; j < change; j++)
                {
                    this.maxHealth = maxHealth + Math.max(CharacterCreate.roll(healthDice[i],con),0); //You can't lose health from leveling up
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
            changeHealth(20 - level);
        return 20;
        
    }
    
    public void setLevel(int newLevel)
    {
        this.experience = expThreshhold[newLevel - 1];
        this.level = newLevel;
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
        int min = Math.min(Math.min(a,b),Math.min(a,b)); //We don't know which variable out of a, b, c, d is the biggest but we can find the minimum value
        stat = a + b + c + d - min; //In order to get rid of the lowest value, add all of them up then remove the lowest value
        return stat;
    }

}

class CharacterCreate
{

    public static String workingCommands = "Help, Quit, Get [Variable], Set [Variable] [Value], Variables, Roll Stats, Roll [Number of Sides on Die], Roll [Stat], New Character, New Character [Name], List";
    public static String workingVariables = "Name, Str, Dex, Con, Wis, Intl, Cha";
    
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
        boolean quit = false, found = false;
        int buffer = 0;
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();
        Character player = findCurrent(roster); //Find the current character to work on
        int roll = 0, modifier = 0;
        //Split the input into a series of words
        String[] words = input.split(" ");
        
        if(player == null)
        {
            System.out.println("Error: No current player. Please restart.");
            return roster;
        }
        
        switch (words[0].toLowerCase()){ //Check what was in the first word given (not case sensitive)
            
            case "list":
                System.out.println("Current Party Members:");
                for(int i = 0; i < roster.size(); i++)
                {
                    System.out.println(roster.get(i).getName());
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
            case "new":
                if(words[1].equalsIgnoreCase("character") && words.length == 2){
                    Character backup = new Character();
                    System.out.println("Would you like a pre-made character? If not, character will be blank. Y/N");
                    String answer = new String();
                    answer = scanner.nextLine();
                    if(answer.equalsIgnoreCase("Y") || answer.equalsIgnoreCase("YES")){
                        backup.setName(names[random.nextInt(names.length)]); //Set the name equal to a random number in between 0 and (length of names.txt - 1)
                        backup.setLevel(1);
                        backup.setClass("Peasant");
                        System.out.println("This character's name will be " + backup.getName());
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
                else{
                    System.out.println("Unable to understand prompt. Are you trying to say 'new character'?");
                }
                break;
            
            case "help":
                System.out.println("Currently working commands:\n{" + workingCommands + "}");
                break;
            case "variables":
                System.out.println("Currently working variables:\n{" + workingVariables + "}");
                break;
            case "quit":
                quit = true;
                break;
            case "get":
                if(words.length == 2)
                {
                    switch(words[1].toLowerCase()){
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
                if(words.length == 3 && (isInteger(words[2]) || words[1].equalsIgnoreCase("name") || words[1].equalsIgnoreCase("class")))
                {
                    switch(words[1].toLowerCase()){
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
                            if(isInteger(words[1])){
                                System.out.println("Rolled a " + roll(Integer.parseInt(words[1]), 10) + "!");
                                break;
                            }
                            else
                                System.out.println("Unable to read the number. Please try again.");
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
    
    public static void main(String args[])
    {
        boolean quit = false;
        String[] names;
        String nameLine = "Failed";
        Scanner scanner = new Scanner(System.in);
        Character first = new Character();
        first.calculateLevel();
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
        first.setCurrent(true);
        ArrayList<Character> roster = new ArrayList<Character>(); //New ArrayList of characters, ArrayLists are much better than arrays at adding a continuous amount of entries
        roster.add(first); //Add character to roster
        String line = new String();
        System.out.println("Welcome to my text based character creator! Type 'HELP' for a list of commands.");
        while(!quit)
        {
            line = scanner.nextLine(); //Have the user input a text line
            if(line.equalsIgnoreCase("quit")){
                break;
            }
            roster = textInput(line, roster, names); //Analyze the line using textInput function, update the roster with any changes
        }
    
    
    }


}