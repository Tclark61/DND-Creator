package creator;

import java.awt.EventQueue;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import javax.swing.JFrame;
import dnd.*;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class GUI {

	private JFrame frame;
	private JTextField textField;

	/**
	 * Launch the application.
	 */
	ArrayList<Character> roster = new ArrayList<Character>();
	String[] names;
	
	public static void main(String[] args) {
        
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI window = new GUI();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public GUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		boolean quit = false;
        String nameLine = "Failed";
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
        binary = random.nextInt() % 2;
        if(binary == 1)
            first.setGender("Male");
        else
            first.setGender("Female");
        first.setCurrent(true);
        first.setName(names[random.nextInt(names.length)]); //random name
        roster.add(first); //Add character to roster
        
        WeaponsLibrary library =  new WeaponsLibrary("weapons.txt"); //Calls from weapons.txt for the list of possible weapons
        ArrayList<Weapon> weaponLib = library.weaponLib;
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		textField = new JTextField();
		textField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent arg0) {
				if(arg0.getKeyCode() == KeyEvent.VK_ENTER)
				{
					String var = textField.getText();
					textField.setText("");
					roster = CharacterCreate.textInput(var, roster, names);
					
				}
				
			}
		});
		textField.setBounds(0, 241, 434, 20);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		JTextArea textArea = new JTextArea();
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
		textArea.setEditable(false);
		textArea.setBounds(0, 0, 434, 224);
		frame.getContentPane().add(textArea);
	}
}
