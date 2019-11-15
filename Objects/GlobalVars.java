package Objects;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import Statics.Constants;

public class GlobalVars {
	public static int currentCharInRoster;
    public static ArrayList<DnDCharacter> roster;
    
    public static DnDCharacter findCurrent(ArrayList<DnDCharacter> roster)
    {
        return roster.get(currentCharInRoster);
    }
    
    public static DnDCharacter InitializeRandomCharacter()
    {
    	Random random = new Random();
        String names[] = readFromFile("names.txt");
        
    	DnDCharacter dndChar = new DnDCharacter();
    	dndChar.calculateLevel();
        dndChar.Gender = random.nextInt() % 2 == 1 ? Constants.Gender.Male : Constants.Gender.Female;
    	dndChar.Name = (names[random.nextInt(names.length)]); //random name
    	    	
    	return dndChar;
    }
    
    // making the output static maybe useful
    private static String[] readFromFile(String fileName)
    {
    	try //Try to find the text file in, the 'try' and 'catch' test to see if the file is found
        {
            BufferedReader in = new BufferedReader(new FileReader(fileName));
            String nameLine = in.readLine(); //If it passes both tests, scan in the first line (All of the first names)
            in.close();
            return nameLine.split(", "); //This splits the single line into multiple smaller strings (individual names) by splitting it at every comma followed by a space
        }
        catch (IOException ex)
        {
            String names[] = new String[1];
            names[0] = "John";
            return names;
        }
    }
}
