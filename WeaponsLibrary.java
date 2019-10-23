package dnd;

import java.util.ArrayList;
import java.util.Scanner;
import java.io.*;


class Weapon
{
    public String name, description;
    private int[] damageDice; //If the weapon does 1d8, then
    private String damageType, weaponType;
    
    public Weapon()
    {
        
    }
    
    public Weapon(String name, String desc)
    {
        this.name = name;
        this.description = desc;
    }
    
    public String getName()
    {
        return name;
    }
    
    
}

public class WeaponsLibrary
{
    public ArrayList<Weapon> weaponLib;
    
    public WeaponsLibrary()
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
            Weapon longsword = new Weapon();
            weaponLib.add(longsword);
        }
        
        
        
    }



}