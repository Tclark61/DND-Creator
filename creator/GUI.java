package creator;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import dnd.*;

import javax.swing.JTextField;
import java.awt.BorderLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.Color;
import javax.swing.ScrollPaneConstants;
import java.awt.SystemColor;

public class GUI extends OutputStream {

    private final JTextArea textArea;
    private final JTextField textField;
    static ArrayList<Character> roster;
    static String[] names;
    private static String answer;
    private static volatile boolean pause;
    private static Timer timer;

    private final StringBuilder sb = new StringBuilder();

    public GUI(final JTextArea textArea, final JTextField textField) {
        this.textArea = textArea;
        this.textField = textField;
    }

    @Override
    public void flush() {
    }

    @Override
    public void close() {
    }

    @Override
    public void write(int b) throws IOException {

        if (b == '\r') {
            return;
        }

        if (b == '\n') {
            final String text = sb.toString() + "\n";

            textArea.append(text);
            sb.setLength(0);
        } else {
            sb.append((char) b);
        }
    }
    
    public static ArrayList<Character> textInput(String input, ArrayList<Character> roster, String[] names, boolean isGUI, JTextField tf)
    {
        boolean found = false;
        int buffer = 0, test;
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();
        Character player = CharacterCreate.findCurrent(roster); //Find the current character to work on
        int roll = 0;
        String[] words = input.split(" "); //Split the input into a series of words
        
        if(player == null)
        {
            System.out.println("Error: No current player. Please restart.");
            return roster;
        }
        if(words.length >= 2)
        {
            words[1] = CharacterCreate.shorten(words[1]);
        }

        switch (words[0].toLowerCase()){ //Check what was in the first word given (not case sensitive)
            
            case "info":
                System.out.println(player.getName() + " is a level " + player.getLevel() + " " + player.getGender() + " " + player.getCharClass());
                System.out.println("Health: " + player.getCurrentHealth() + "/" + player.getMaxHealth());
                System.out.println("---------------------------------");
                player.refresh();
                for(int i = 0; i < 6; i++)
                {    
                    System.out.println(Character.variables[i + 1] + ": " + player.stats[i]);
                }
                System.out.println("---------------------------------");
                System.out.println("Proficiencies:");
                for(int i = 0; i < Character.proficiencies.length; i++)
                {
                    if(player.bonuses[i] == 1)
                        System.out.println(Character.proficiencies[i] + ": " + player.getModfier(Character.proficiencies[i]));
                }
                break;
            case "skills":
                for(int i = 0; i < Character.proficiencies.length; i++)
                {
                    System.out.println(Character.proficiencies[i] + ": " + player.getModfier(Character.proficiencies[i]));
                }
                break;
            
            case "give":
                if(words.length == 3 && CharacterCreate.isInteger(words[2]))
                {
                    if(words[1].equalsIgnoreCase("exp"))
                    {
                        player.gainExp(Integer.parseInt(words[2]));
                        System.out.println(player.getName() + " has " + player.getExp() + " and is level " + player.getLevel() + "!");
                    }
                    if(words[1].equalsIgnoreCase("gold"))
                    {
                        if((Integer.parseInt(words[2]) + player.getGold()) >= 0)
                        {
                            player.addGold(Integer.parseInt(words[2]));
                            System.out.println(player.getName() + " gained " + Integer.parseInt(words[2]) + " gold, and now has " + player.getGold() + " gold pieces!");
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
                for(int i = 0; i < roster.size(); i++)
                {
                    System.out.println(roster.get(i).getName() + ", the level " + roster.get(i).getLevel() + " " + roster.get(i).getCharClass());
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
                break;
            case "new":
                if((words[1].equalsIgnoreCase("character") || words[1].equalsIgnoreCase("char")) && words.length == 2)
                {
                    Character backup = new Character();
                    System.out.println("Would you like a pre-made character? If not, character will be blank. Y/N");
                    answer = new String();
                    if(!isGUI)
                    	answer = scanner.nextLine();
                    else
                    {
                    	answer = pauseUntilKey(tf);
                    }
                    if(answer.equalsIgnoreCase("Y") || answer.equalsIgnoreCase("YES"))
                    {
                        backup.setName(names[random.nextInt(names.length)]); //Set the name equal to a random number in between 0 and (length of names.txt - 1)
                        backup.setLevel(1);
                        backup.setClass("Peasant");
                        System.out.println("This character's name will be " + backup.getName());
                        buffer = random.nextInt() % 2;
                        if(buffer == 1)
                            backup.setGender("Male");
                        else
                            backup.setGender("Female");
                        buffer = 0;
                        backup.rollEverything();
                        System.out.println("Rolling " + backup.getName() + "'s stats....");
                        for(int i = 0; i < 6; i++)
                        {
                            System.out.println(Character.variables[i + 1] + ": " + backup.stats[i]); //Prints out the name of stats and their values as located in the Char class
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
                    answer = new String();
                    if(!isGUI)
                    	answer = scanner.nextLine();
                    else
                    {
                    	answer = pauseUntilKey(tf);
                    }
                    if(answer.equalsIgnoreCase("Y") || answer.equalsIgnoreCase("YES")){
                        backup.rollEverything();
                        buffer = random.nextInt() % 2;
                        if(buffer == 1)
                            backup.setGender("Male");
                        else
                            backup.setGender("Female");
                        buffer = 0;
                        System.out.println("Rolling " + backup.getName() + "'s stats....");
                        for(int i = 0; i < 6; i++)
                        {
                        System.out.println(Character.variables[i + 1] + ": " + backup.stats[i]);
                        }
                    }
                    player.setCurrent(false);
                    backup.setCurrent(true);
                    roster.add(backup);
                    System.out.println(backup.getName() + " successfully added to the party!");
                    break;
                }
                else
                {
                    System.out.println("Unable to understand prompt. Are you trying to say 'new character'?");
                }
                break;
            case "classes":
                for(int i = 0; i < Character.classes.length; i++)
                {
                    System.out.println(Character.classes[i]);
                }
                break;
            case "help":
                System.out.println("Currently working commands:\n{" + CharacterCreate.workingCommands + "}");
                System.out.println("Type Variables to list all variables. Type Classes to list all classes. Type Skills to list all skills.");
                break;
            case "variables":
                System.out.println("Currently working variables:\n{" + CharacterCreate.workingVariables + "}");
                break;
            case "get":
                if(words.length == 2 || (words[1].equalsIgnoreCase("skill") && words.length == 3))
                {
                    switch(words[1].toLowerCase()){
                        case "skill":
                            test = player.getModfier(words[2]);
                            if(test != -100)
                            {
                                System.out.println(player.getName() + "'s proficiency in " + words[2] + " is " + test);
                            }
                            break;
                        case "gold":
                            System.out.println(player.getName() + "'s current gold is " + player.getGold());
                            break;                       
                        case "health":
                            System.out.println(player.getName() + "'s current health is " + player.getMaxHealth());
                            break;
                        case "gender":
                            System.out.println(player.getName() + "'s gender is " + player.getGender());
                            break;
                        case "class":
                            System.out.println(player.getName() + "'s class is " + player.getCharClass());
                            break;
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
                if(words.length == 3 && (CharacterCreate.isInteger(words[2]) || words[1].equalsIgnoreCase("name") || words[1].equalsIgnoreCase("class") || words[1].equalsIgnoreCase("gender")))
                {
                    switch(words[1].toLowerCase()){
                        case "gold":
                            if(Integer.parseInt(words[2]) >= 0)
                            {
                                player.setGold(Integer.parseInt(words[2]));
                                System.out.println(player.getName() + " gained " + Integer.parseInt(words[2]) + " gold, and now has " + player.getGold() + " gold pieces!");
                            }
                            else
                                System.out.println("That would result in a negative gold amount, and you can't have negative gold.");
                            break;
                        case "gender":
                            player.setGender(words[2]);
                            System.out.println(player.getName() + " is a " + player.getGender() + ", got it!"); //I'm walking into this being so easily abusable by letting any string be added here.
                            break;
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
                else if (words[1].equalsIgnoreCase("proficiency") && words.length >= 3)
                {
                    if(player.setSkill(words[2]))
                                System.out.println(player.getName() + " is now proficient in " + words[2] + "!");
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
                    roll = CharacterCreate.roll(20, 10);
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
                                System.out.println(Character.variables[i + 1] + ": " + player.stats[i]);
                            }
                            break;
                        case "str":
                            System.out.println("Rolled a " + CharacterCreate.roll(20, player.getStr()) + " with strength!");
                            break;
                        case "dex":
                            System.out.println("Rolled a " + CharacterCreate.roll(20, player.getDex()) + " with dexterity!");
                            break;
                        case "con":
                            System.out.println("Rolled a " + CharacterCreate.roll(20, player.getCon()) + " with constitution!");
                            break;
                        case "intl":
                            System.out.println("Rolled a " + CharacterCreate.roll(20, player.getIntl()) + " with intelligence!");
                            break;
                        case "wis":
                            System.out.println("Rolled a " + CharacterCreate.roll(20, player.getWis()) + " with wisdom!");
                            break;
                        case "cha":
                            System.out.println("Rolled a " + CharacterCreate.roll(20, player.getCha()) + " with charisma!");
                            break;
                        default:
                            if(CharacterCreate.isInteger(words[1]))
                            {
                                System.out.println("Rolled a " + CharacterCreate.roll(Integer.parseInt(words[1]), 10) + "!");
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
        return roster;
    }
    
    public static String pauseUntilKey(JTextField tf)
    {
    	boolean tooLong = false;
    	int waitTime = 0;
    	pause = true;

    	tf.removeKeyListener(tf.getKeyListeners()[0]);
    	KeyAdapter pauseForInput = new KeyAdapter() { //Get rid of the old keyAdapter and put in the new one just for this function
    		@Override
        	public void keyPressed(KeyEvent arg0) {
        		if(arg0.getKeyCode() == KeyEvent.VK_ENTER) //When the enter key is pressed, this should trigger
        		{
        			pause = false; //Set local variable pause to be false to let us know we need to stop the while loop
        			answer = tf.getText();
        			tf.setText("");
        		}
        	}
    	};
    	timer = new Timer(1000, new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
            	if(pause == false)
            		timer.stop();
            }
        });
    	timer.start();
    	
    	
    	KeyAdapter enterMain = new KeyAdapter() { //Put the old key adapter back in
        	@Override
        	public void keyPressed(KeyEvent arg0) {
        		if(arg0.getKeyCode() == KeyEvent.VK_ENTER)
        		{
        			roster = textInput(tf.getText(), roster, names, true, tf); //Analyze the line using textInput function, update the roster with any changes
        			tf.setText("");
        		}
        	}
        };
        tf.addKeyListener(enterMain);
    	if(pause == false) 
    		return answer; //If we left the while loop the way I wanted, then return whatever the user wrote before pressing enter.
    	return "N"; //Otherwise, just return N for No.
    }
    
    public static void main(String[] args) {
        boolean quit = false;
        String nameLine = "Failed";
        Scanner scanner = new Scanner(System.in);
        Character first = new Character();
        String line = new String();
        Random random = new Random();
        int binary;
        first.calculateLevel();
        try //Try to find the text file in, the 'try' and 'catch' test to see if the file is found
        {
            BufferedReader in = new BufferedReader(new FileReader("names.txt"));
            try
            {
                nameLine = in.readLine(); //If it passes both tests, scan in the first line (All of the first names)
                in.close();
                names = nameLine.split(", "); //This splits the single line into multiple smaller strings (individual names) by splitting it at every comma followed by a space
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
        
        binary = random.nextInt() % 2;
        if(binary == 1)
            first.setGender("Male");
        else
            first.setGender("Female");
        first.setCurrent(true);
        first.setName(names[random.nextInt(names.length)]); //random name
        roster = new ArrayList<Character>(); //New ArrayList of characters, ArrayLists are much better than arrays at adding a continuous amount of entries
        roster.add(first); //Add character to roster
        
        WeaponsLibrary library =  new WeaponsLibrary("weapons.txt"); //Calls from weapons.txt for the list of possible weapons
        ArrayList<Weapon> weaponLib = library.weaponLib;
        
        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                JFrame frmDndCreator = new JFrame(GUI.class.getSimpleName());
                frmDndCreator.setTitle("DND Creator");
                frmDndCreator.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                JTextArea ta = new JTextArea(24, 80);
                ta.setBackground(SystemColor.menu);
                ta.setEditable(false);
                JTextField tf = new JTextField();
                KeyAdapter enterMain = new KeyAdapter() {
                	@Override
                	public void keyPressed(KeyEvent arg0) {
                		if(arg0.getKeyCode() == KeyEvent.VK_ENTER)
                		{
                			roster = textInput(tf.getText(), roster, names, true, tf); //Analyze the line using textInput function, update the roster with any changes
                			tf.setText("");
                		}
                	}
                };
                tf.addKeyListener(enterMain);
                	
                System.setOut(new PrintStream(new GUI(ta,tf)));
                JScrollPane scrollPane = new JScrollPane(ta);
                scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
                frmDndCreator.getContentPane().add(scrollPane);
                frmDndCreator.getContentPane().add(tf, BorderLayout.SOUTH);
                frmDndCreator.pack();
                frmDndCreator.setVisible(true);
                
                System.out.println("Welcome to my text based character creator! Type 'HELP' for a list of commands.");
                Timer t = new Timer(1000, new ActionListener() {


                    @Override
                    public void actionPerformed(ActionEvent e) {
                    }
                });
                t.start();
            }
        });
    }
}