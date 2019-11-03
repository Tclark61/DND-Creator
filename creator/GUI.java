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

public class GUI extends OutputStream {

    private final JTextArea textArea;
    private final JTextField textField;
    static ArrayList<Character> roster;
    static String[] names;

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
        roster = new ArrayList<Character>(); //New ArrayList of characters, ArrayLists are much better than arrays at adding a continuous amount of entries
        roster.add(first); //Add character to roster
        
        WeaponsLibrary library =  new WeaponsLibrary("weapons.txt"); //Calls from weapons.txt for the list of possible weapons
        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                JFrame frmDndCreator = new JFrame(GUI.class.getSimpleName());
                frmDndCreator.setTitle("DND Creator");
                frmDndCreator.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                JTextArea ta = new JTextArea(24, 80);
                ta.setEditable(false);
                JTextField tf = new JTextField();
                tf.addKeyListener(new KeyAdapter() {
                	@Override
                	public void keyPressed(KeyEvent arg0) {
                		if(arg0.getKeyCode() == KeyEvent.VK_ENTER)
                		{
                			
                			roster = CharacterCreate.textInput(tf.getText(), roster, names); //Analyze the line using textInput function, update the roster with any changes
                			tf.setText("");
                		}
                	}
                });
                System.setOut(new PrintStream(new GUI(ta,tf)));
                frmDndCreator.getContentPane().add(new JScrollPane(ta));
                frmDndCreator.getContentPane().add(tf, BorderLayout.SOUTH);
                frmDndCreator.pack();
                frmDndCreator.setVisible(true);
                System.out.println("Welcome to my text based character creator! Type 'HELP' for a list of commands.");
                Timer t = new Timer(1000, new ActionListener() {

                    int count = 1;

                    @Override
                    public void actionPerformed(ActionEvent e) {
                    }
                });
                t.start();
            }
        });
    }
}