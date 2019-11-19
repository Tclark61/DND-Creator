package creator;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import Objects.DnDCharacter;
import Objects.Weapon;
import Objects.WeaponsLibrary;

public class TextGame {
	public static void main(String args[])
    {

        boolean quit = false;
        String[] names;
        String nameLine = "Failed";
        Scanner scanner = new Scanner(System.in);
        DnDCharacter first = new DnDCharacter();
        String line = new String();
        Random random = new Random();
        //int binary;
        first.calculateLevel();
        try //Try to find the text file in, the 'try' and 'catch' test to see if the file is found
        {
            BufferedReader in = new BufferedReader(new FileReader("names.txt"));
            try
            {
                nameLine = in.readLine(); //If it passes both tests, scan in the first line (All of the first names)
                in.close();
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
        //binary = random.nextInt() % 2;
        //first.Gender
        //first.Current ?
        first.Name = names[random.nextInt(names.length)]; //random name
        ArrayList<DnDCharacter> roster = new ArrayList<DnDCharacter>(); //New ArrayList of characters, ArrayLists are much better than arrays at adding a continuous amount of entries
        roster.add(first); //Add character to roster
        
        WeaponsLibrary library =  new WeaponsLibrary("weapons.txt"); //Calls from weapons.txt for the list of possible weapons
        ArrayList<Weapon> weaponLib = library.weaponLib;
        for(int i = 0; i < weaponLib.size(); i++)
        {
            System.out.print(weaponLib.get(i).getName() + " has damage ");
            for(int j = 0; j < weaponLib.get(i).getDamageType().length; j++)
            {
                System.out.print(weaponLib.get(i).getDamageDice(2*j) + "d" + weaponLib.get(i).getDamageDice(2*j + 1) + " " + weaponLib.get(i).getDamageType(j) + " ");
            }
            System.out.print("\n");
        }
        System.out.println("Welcome to my text based character creator! Type 'HELP' for a list of commands.");
        while(!quit)
        {
            line = scanner.nextLine(); //Have the user input a text line
            if(line.equalsIgnoreCase("quit"))
            {
                break;
            }
            //roster = GUI.textInput(line, roster, names, false, null); //Analyze the line using textInput function, update the roster with any changes
        }
        scanner.close();
    }
}
