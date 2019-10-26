package dnd;

import java.util.ArrayList;
import java.util.Scanner;
import java.io.*;



public class WeaponsLibrary
{
    public ArrayList<Weapon> weaponLib;
    
    private ArrayList<Weapon> parser(String line, ArrayList<Weapon> weaponLib)
    {
        return weaponLib;
    }
    
    public ArrayList<Weapon> WeaponsLibrary()
    {
        String line = new String();
        weaponLib = new ArrayList<Weapon>();
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
        }
        catch (FileNotFoundException ex)
        {
            line = "Failed";
        }
        if(line != null || !line.equals("Failed")) //If it finds the text file weapons.txt and can read from it
        {
            Weapon longsword = new Weapon("longsword");
            weaponLib.add(longsword);
        }
        
        return weaponLib;
        
    }



}