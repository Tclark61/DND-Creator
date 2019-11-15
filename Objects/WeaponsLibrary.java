//Creates the list of weapons from a hardcoded text file
//Author: Tyler Clark
//Created in October 2019

package Objects;

import java.util.ArrayList;
import java.util.Scanner;
import java.io.*;



public class WeaponsLibrary
{
    public ArrayList<Weapon> weaponLib;
    
    private Weapon damageCalc(String[] words, Weapon weapon)
    {
        int[] dice;
        String[] damageType;
        int numDice = 0;
        for(int i = 0; i < words.length; i++)
        {
            if(Character.isDigit(words[i].charAt(0)))
            {
                numDice++;
            }
        }
        dice = new int[2*numDice];
        damageType = new String[numDice];
        numDice = 0; //Reset the counter after we max the initializations
        for(int i = 0; i < words.length; i++)
        {
            if(Character.isDigit(words[i].charAt(0)))
            {
                for(int j = 1; j < words[i].length(); j++)
                {
                    if(!Character.isDigit(words[i].charAt(j)) && !(words[i].charAt(j) == 'd' || words[i].charAt(j) == 'D') ) //If it's not a number or a d, then it's not a die.
                        break;
                    if(words[i].charAt(j) == 'd' || words[i].charAt(j) == 'D')
                    {
                        dice[numDice*2] = Integer.parseInt(words[i].substring(0,j));
                        dice[(numDice*2) + 1] = Integer.parseInt(words[i].substring(j + 1, words[i].length()));
                        if((i+1) < words.length)
                            damageType[numDice] = words[i + 1];
                        else
                        {
                            damageType[numDice] = "N/A";
                            break;
                        }
                        numDice++;
                    }
                }
            }
        }
        weapon.setDamageDice(dice);
        weapon.setDamageType(damageType);
        return weapon;
    }
    
    public static boolean isInteger(String str) { //This is a duplicate from CharacterCreate, this is bad coding practice but I'm my own man
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
    
    public static String shorten(String word) //shortens the word so you can use shortened and long version of the stats. Also stolen from CharacterCreate
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
        else if(word.equalsIgnoreCase("Constitution"))
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
    
    private ArrayList<Weapon> parser(String file, ArrayList<Weapon> weaponLib)
    {
        Weapon weapon = new Weapon();
        String line = new String();
        Shield shield = new Shield();
        int ac = 0;
        String[] words;
        boolean isShield = false;
        String rest = new String();
        try //Try to find the text file in, the 'try' and 'catch' test to see if the file is found
        {
            BufferedReader in = new BufferedReader(new FileReader("weapons.txt"));
            try
            {
                line = in.readLine(); //If it passes both tests, scan in the first line (All of the first names)
            }
            catch (IOException io)
            {
                line = "Failed";
            }
            if(!line.equals("Failed")) //If it finds the text file weapons.txt and can read from it
            {
                while(line != null)
                {
                    words = line.split(" ");
                    if(words.length > 0)
                    {
                        switch(words[0])
                        {
                            case "//": //Skip all comments
                                break;
                            case "{": //Initialize a new weapon because { is the start of a new item
                                weapon = new Weapon();
                                break;
                            case "Name:":
                                if(words.length > 1)
                                    rest = words[1];
                                for(int i = 2; i < words.length; i++)
                                {
                                    rest = rest + " " + words[i]; //Add the space and then the following word to form however long of a name they like
                                }
                                weapon.setName(rest);
                                rest = null;
                                break;
                            case "Rarity:":
                                if(words.length > 1)
                                    weapon.setRarity(words[1]);
                                break;
                            case "Type:":
                                if(words.length > 1)
                                {
                                    if(words[1].equalsIgnoreCase("Shield")) //Check if the type is shield so that we can return all the info into a shield class
                                    {
                                        isShield = true;
                                    }
                                    weapon.setWeaponType(words[1]);
                                }
                                break;
                            case "Armor:":
                                if(words.length > 1 && isInteger(words[1]))
                                    ac = Integer.parseInt(words[1]);
                                break;
                            case "Range:":
                                if(words.length > 1)
                                {
                                    if(words[1].equalsIgnoreCase("melee"))
                                        weapon.setRange(5);
                                    else if(words[1].equalsIgnoreCase("ranged"))
                                    {
                                        if(words.length > 2 && isInteger(words[2]))
                                        {
                                            weapon.setRange(Integer.parseInt(words[2]));
                                        }
                                        else
                                            weapon.setRange(30); //Arbitrarily chosen range if no range was defined for the ranged weapon
                                    }
                                    else if(isInteger(words[1]))
                                        weapon.setRange(Integer.parseInt(words[1]));                                    
                                }
                                break;
                            case "Modifier:":
                                if(words.length > 1)
                                {
                                    words[1] = shorten(words[1]); //Using Jeanne's function
                                    if(words[1].equalsIgnoreCase("str") || words[1].equalsIgnoreCase("dex") || words[1].equalsIgnoreCase("intl") || words[1].equalsIgnoreCase("wis") || 
                                    words[1].equalsIgnoreCase("con") || words[1].equalsIgnoreCase("finesse") || words[1].equalsIgnoreCase("cha"))
                                        weapon.setModifier(words[1]);
                                }
                                break;

                            case "Description:":
                                if(words.length > 1)
                                    rest = words[1];
                                for(int i = 2; i < words.length; i++)
                                {
                                    rest = rest + " " + words[i]; //Add the space and then the following word to form however long of a name they like
                                }
                                weapon.setDescription(rest);
                                rest = null;
                                break;    
                            case "Damage:":
                                weapon = damageCalc(words, weapon); //I made this one its own function since it's slightly more complicated than the others
                                break;
                            case "Handed:":
                                if(words.length > 1 && isInteger(words[1]))
                                    weapon.setHanded(Integer.parseInt(words[1]));
                                break;
                            case "}":
                                if(isShield) //Special case for if the weapon is a shield
                                {
                                    shield = new Shield(weapon);
                                    shield.setArmorBonus(ac);
                                    weaponLib.add(shield);
                                    isShield = false;
                                    ac = 0;
                                }

                                else  
                                    weaponLib.add(weapon); //If we reach the } then we know we've finished getting all the data from a weapon.
                        }
                    }
                    try
                    {
                        line = in.readLine();
                    }
                    catch(IOException e)
                    {
                        line = null;
                    }
                }
            }
        }
        catch (FileNotFoundException ex)
        {
            line = "Failed";
        }

        return weaponLib;
    }
    
    public WeaponsLibrary(String file)
    {
        String line = new String();
        this.weaponLib = parser(file, new ArrayList<Weapon>());
        
    }



}