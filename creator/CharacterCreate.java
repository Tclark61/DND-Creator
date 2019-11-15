package creator;
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
import Objects.*;
import Statics.*;

// rename this to CharacterHelper?
public class CharacterCreate
{
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
    
    public static int roll(int dice)
    {
    	return roll(dice, 10);
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
            word = word.substring(0,3);
            return word;
            
        }
        else if(word.equalsIgnoreCase("Dexterity"))
        {
            word = word.substring(0,3);
            return word;
             
        }
        else if(word.equalsIgnoreCase("Constitution"))
        {
            word = word.substring(0,3);
            return word;
            
        }
        else if(word.equalsIgnoreCase("Intelligence"))
        {
            word = word.substring(0,4);
            return word;
            
        }
        else if(word.equalsIgnoreCase("Wisdom"))
        {
            word = word.substring(0,3);
            return word;
            
        }
        else if(word.equalsIgnoreCase("Charisma"))
        {
            word = word.substring(0,3);
            return word;
            
        }
        return word;
    }
}