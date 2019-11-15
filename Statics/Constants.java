package Statics;

public class Constants {
	// Character constants 
    public static String[] proficiencies = {"Athletics", "Acrobatics",
		"Sleight of Hand", "Stealth", "Arcana", "History","Investigation","Nature",
	    "Religion","Animal Handling","Insight","Medicine","Perception",
	    "Survival","Deception","Intimidation","Performance","Persuasion"};
    public static String[] variables = {"Name", "Strength", "Dexterity", "Constitution",
		"Intelligence", "Wisdom", "Charisma", "Class", "Gender","Level", 
		"Experience", "Proficiencies"};
    public static String[] classes = {"Peasant", "Fighter","Wizard", "Rogue","Cleric","Paladin","Warlock","Barbarian"};
    public static int[] healthDice = {3,10,6,8,8,10,6,12}; //Dice used to calculate Health for a specific class.
    public static int[] expThreshhold = {0,300,900,2700,6500,14000,23000,34000,48000,64000,85000,100000,120000,140000,
		165000, 195000, 225000, 265000, 305000, 355000}; //The set experience thresholds for dnd 5e. They do not follow a formula, which was pretty annoying
    public static int[] proficiencyType = {1,2,2,2,4,4,4,4,4,5,5,5,5,5,6,6,6,6};
    public static int[] profBonusChart = {1,5,9,13,17};
    public enum Gender { Male, Female, Genderless };
    
    //CharacterCreate constants
    public static String globalCommands = "Help, Quit, Get [Variable], Set [Variable] [Value], Set [Class], Variables, Roll Stats, Roll [Number of Sides on Die], Roll [Stat/Skill]," 
    	    + "\nNew Character, New Character [Name], List, Give Exp [Value], Skills, Info, Switch [Character Name]";
	public static String workingVariables = "Name, Str, Dex, Con, Wis, Intl, Cha, Class, Level, Exp, Proficiency, Gender";
    	    
}
