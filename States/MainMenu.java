package States;

import Objects.DnDCharacter;
import Objects.GlobalVars;
import Statics.Constants;
import creator.CharacterCreate;

public class MainMenu extends State {
	
	@Override
	public void Action(String input) {
		String[] words = input.split(" "); //Split the input into a series of words
        DnDCharacter player = GlobalVars.roster.get(GlobalVars.currentCharInRoster);

        // ?? this will run you into trouble in the future
        if(words.length >= 2)
        {
            words[1] = CharacterCreate.shorten(words[1]);
        }

        switch (words[0].toLowerCase()){ //Check what was in the first word given (not case sensitive)
            
            case "info":
                System.out.println(player.Name + " is a level " + player.getLevel() + " " + player.Gender + " " + player.Class);
                System.out.println("Health: " + player.getCurrentHealth() + "/" + player.getMaxHealth());
                System.out.println("---------------------------------");
                player.refresh();
                for(int i = 0; i < 6; i++)
                {    
                    System.out.println(Constants.variables[i + 1] + ": " + player.stats[i]);
                }
                System.out.println("---------------------------------");
                System.out.println("Proficiencies:");
                for(int i = 0; i < Constants.proficiencies.length; i++)
                {
                    if(player.bonuses[i] == 1)
                        System.out.println(Constants.proficiencies[i] + ": " + player.getModfier(Constants.proficiencies[i]));
                }
                break;
            case "skills":
                for(int i = 0; i < Constants.proficiencies.length; i++)
                {
                    System.out.println(Constants.proficiencies[i] + ": " + player.getModfier(Constants.proficiencies[i]));
                }
                break;
            
            case "give":
                if(words.length == 3 && CharacterCreate.isInteger(words[2]))
                {
                    if(words[1].equalsIgnoreCase("exp"))
                    {
                        player.gainExp(Integer.parseInt(words[2]));
                        System.out.println(player.Name + " has " + player.getExp() + " and is level " + player.getLevel() + "!");
                    }
                    if(words[1].equalsIgnoreCase("gold"))
                    {
                        if((Integer.parseInt(words[2]) + player.getGold()) >= 0)
                        {
                            player.addGold(Integer.parseInt(words[2]));
                            System.out.println(player.Name + " gained " + Integer.parseInt(words[2]) + " gold, and now has " + player.getGold() + " gold pieces!");
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
                for(int i = 0; i < GlobalVars.roster.size(); i++)
                {
                    System.out.println(GlobalVars.roster.get(i).Name + ", the level " + GlobalVars.roster.get(i).getLevel() + " " + GlobalVars.roster.get(i).Class);
                }
                break;
            case "switch":
                if(words.length == 2 || words.length == 3)
                {
                	String name = words[1];
                	
                    if(words[1].equalsIgnoreCase("to"))
                    {
                        //if the word 'to' is added, don't penalize the user. Just shift the index by 1.
                        name = words[2];
                    }
                    
                    DnDCharacter temp = new DnDCharacter();
                    Boolean found = false;
                    
                    for(int i = 0; i < GlobalVars.roster.size(); i++){
                        temp = GlobalVars.roster.get(i);
                        if(temp.Name.equalsIgnoreCase(name)){
                        	GlobalVars.currentCharInRoster = i;
                            System.out.println("Switched to " + temp.Name);
                            found = true;
                            break;
                        }

                    }
                    if(!found)
                    {
                        System.out.println("No party member with the name " + name + " was found.");
                        break;
                    }
                    
                }
                else
                {
                    System.out.println("Unable to switch, please specify the name of DnDCharacter to switch to.");
                }
                break;
            case "new":
                if((words[1].equalsIgnoreCase("Character") || words[1].equalsIgnoreCase("char")) && (words.length == 2 || words.length == 3))
                {
                    DnDCharacter newChar = GlobalVars.InitializeRandomCharacter();
                    if(words.length == 3)
                    	newChar.Name = words[2];
                    
    		        GlobalVars.roster.add(newChar);
    		        GlobalVars.currentCharInRoster = GlobalVars.roster.indexOf(newChar);
                    System.out.println("Would you like a pre-made DnDCharacter? If not, DnDCharacter will be blank. Y/N");
                    nextPossibleStates.get(0).isNextState = true;
                    break;
                }
                else
		        {
		            System.out.println("Unable to understand prompt. Are you trying to say 'new DnDCharacter'?");
		        }
		        break;
            case "classes":
                for(int i = 0; i < Constants.classes.length; i++)
                {
                    System.out.println(Constants.classes[i]);
                }
                break;
            case "help":
                System.out.println("Currently working commands:\n{" + Constants.globalCommands + "}");
                System.out.println("Type Variables to list all variables. Type Classes to list all classes. Type Skills to list all skills.");
                break;
            case "variables":
                System.out.println("Currently working variables:\n{" + Constants.workingVariables + "}");
                break;
            case "get":
                if(words.length == 2 || (words[1].equalsIgnoreCase("skill") && words.length == 3))
                {
                    switch(words[1].toLowerCase()){
                        case "skill":
                            int test = player.getModfier(words[2]);
                            if(test != -100)
                            {
                                System.out.println(player.Name + "'s proficiency in " + words[2] + " is " + test);
                            }
                            break;
                        case "gold":
                            System.out.println(player.Name + "'s current gold is " + player.getGold());
                            break;                       
                        case "health":
                            System.out.println(player.Name + "'s current health is " + player.getMaxHealth());
                            break;
                        case "gender":
                            System.out.println(player.Name + "'s gender is " + player.Gender);
                            break;
                        case "class":
                            System.out.println(player.Name + "'s class is " + player.Class);
                            break;
                        case "level":
                            System.out.println(player.Name + "'s level is " + player.getLevel());
                            break;
                        case "exp":
                            System.out.println(player.Name + "'s experience is " + player.getExp());
                            break;
                        case "name":
                            System.out.println("Current DnDCharacter name is " + player.Name + ".");
                            break;
                        case "str":
                            System.out.println("Current DnDCharacter strength is " + player.Str + ".");
                            break;
                        case "dex":
                            System.out.println("Current DnDCharacter dexterity is " + player.Dex + ".");
                            break;
                        case "con":
                            System.out.println("Current DnDCharacter constitution is " + player.Con + ".");
                            break; 
                        case "wis":
                            System.out.println("Current DnDCharacter wisdom is " + player.Wis + ".");
                            break;
                        case "intl":
                            System.out.println("Current DnDCharacter intelligence is " + player.Intl + ".");
                            break; 
                        case "cha":
                            System.out.println("Current DnDCharacter charisma is " + player.Cha + ".");
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
                if(words.length == 3 && (CharacterCreate.isInteger(words[2]) || words[1].equalsIgnoreCase("name") || words[1].equalsIgnoreCase("class") || words[1].equalsIgnoreCase("gender")))
                {
                    switch(words[1].toLowerCase()){
                        case "gold":
                            if(Integer.parseInt(words[2]) >= 0)
                            {
                                player.setGold(Integer.parseInt(words[2]));
                                System.out.println(player.Name + " gained " + Integer.parseInt(words[2]) + " gold, and now has " + player.getGold() + " gold pieces!");
                            }
                            else
                                System.out.println("That would result in a negative gold amount, and you can't have negative gold.");
                            break;
                        case "gender":
                        	if(words[2] == "Male")
                        	{
                        		player.Gender = Constants.Gender.Male;
                        	}
                        	else if(words[2] == "Female")
                        	{
                        		player.Gender = Constants.Gender.Female;
                        	}
                        	else
                        	{
                        		player.Gender = Constants.Gender.Genderless;
                        	}
                            System.out.println(player.Name + " is a " + player.Gender + ", got it!"); //I'm walking into this being so easily abusable by letting any string be added here.
                            break;
                        case "class":
                            player.setClass(words[2]);
                            System.out.println(player.Name + " is class " + player.Class + " and has " + player.getMaxHealth() + " health.");
                            break;
                        case "level":
                            player.setLevel(Integer.parseInt(words[2]));
                            System.out.println(player.Name + " is now level " + player.getLevel() + "!");
                            break;
                        case "exp":
                            player.setExp(Integer.parseInt(words[2]));
                            System.out.println(player.Name + " now has " + player.getExp() + " experience!" );
                            break;
                        case "name":
                            player.Name = words[2]; //Use the third word as the name of the DnDCharacter
                            System.out.println("Current DnDCharacter name set to " + player.Name + ".");
                            break;
                        case "str":
                            player.Str = Integer.parseInt(words[2]);
                            System.out.println("Current DnDCharacter strength set to " + player.Str + ".");
                            break;
                        case "dex":
                            player.Dex = Integer.parseInt(words[2]);
                            System.out.println("Current DnDCharacter dexterity set to " + player.Dex + ".");
                            break;
                        case "con":
                            player.Con = Integer.parseInt(words[2]);
                            System.out.println("Current DnDCharacter constitution set to " + player.Con + ".");
                            break; 
                        case "wis":
                            player.Wis = Integer.parseInt(words[2]);
                            System.out.println("Current DnDCharacter wisdom set to " + player.Wis + ".");
                            break;
                        case "intl":
                            player.Intl = Integer.parseInt(words[2]);
                            System.out.println("Current DnDCharacter intelligence set to " + player.Intl + ".");
                            break; 
                        case "cha":
                            player.Cha = Integer.parseInt(words[2]);
                            System.out.println("Current DnDCharacter charisma set to " + player.Cha + ".");
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
                                System.out.println(player.Name + " is now proficient in " + words[2] + "!");
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
                    int roll = CharacterCreate.roll(20);
                    System.out.println("Rolled a " + roll + "!");
                    break;
                }
                if(words.length == 2)
                {
                    switch (words[1].toLowerCase())
                    {
                        case "stats":
                            player.rollEverything();
                            System.out.println("Rolling " + player.Name + "'s stats....");
                            for(int i = 0; i < 6; i++)
                            {
                                System.out.println(Constants.variables[i + 1] + ": " + player.stats[i]);
                            }
                            break;
                        case "str":
                            System.out.println("Rolled a " + CharacterCreate.roll(20, player.Str) + " with strength!");
                            break;
                        case "dex":
                            System.out.println("Rolled a " + CharacterCreate.roll(20, player.Dex) + " with dexterity!");
                            break;
                        case "con":
                            System.out.println("Rolled a " + CharacterCreate.roll(20, player.Con) + " with constitution!");
                            break;
                        case "intl":
                            System.out.println("Rolled a " + CharacterCreate.roll(20, player.Intl) + " with intelligence!");
                            break;
                        case "wis":
                            System.out.println("Rolled a " + CharacterCreate.roll(20, player.Wis) + " with wisdom!");
                            break;
                        case "cha":
                            System.out.println("Rolled a " + CharacterCreate.roll(20, player.Cha) + " with charisma!");
                            break;
                        default:
                            if(CharacterCreate.isInteger(words[1]))
                            {
                                System.out.println("Rolled a " + CharacterCreate.roll(Integer.parseInt(words[1])) + "!");
                            }
                            else
                            {
                                int mod = player.getModfier(words[1]);
                                if(mod != -100)
                                {
                                    System.out.println("Rolled a " + (CharacterCreate.roll(20,10) + mod) + " with " + words[1] + "!");
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
	}

}
