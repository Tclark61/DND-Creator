package dnd;

import java.util.ArrayList;
import java.util.Scanner;
import java.io.*;



public class WeaponsLibrary
{
    public ArrayList<Weapon> weaponLib;
    
    private Weapon damageCalc(String line, Weapon weapon)
    {
        return weapon;
    }
    
    
    private ArrayList<Weapon> parser(String file, ArrayList<Weapon> weaponLib)
    {
        Weapon weapon = new Weapon();
        String line = new String();
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
                    System.out.println(line);
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