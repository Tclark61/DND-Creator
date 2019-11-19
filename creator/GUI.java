package creator;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.JTextField;
import java.awt.BorderLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ScrollPaneConstants;
import java.awt.SystemColor;

import Objects.*;
import States.*;

public class GUI extends OutputStream {

    private final JTextArea textArea;
    private static State currentState = null;
    private final StringBuilder sb = new StringBuilder();

    public GUI(final JTextArea textArea, final JTextField textField) {
        this.textArea = textArea;
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
        DnDCharacter first = GlobalVars.InitializeRandomCharacter();
    	
        GlobalVars.roster = new ArrayList<DnDCharacter>(); //New ArrayList of DnDCharacters, ArrayLists are much better than arrays at adding a continuous amount of entries
        GlobalVars.roster.add(first); //Add DnDCharacter to roster
        GlobalVars.currentCharInRoster = 0;
        InitializeStates();
        
       // WeaponsLibrary library =  new WeaponsLibrary("weapons.txt"); //Calls from weapons.txt for the list of possible weapons
        
        // startup GUI
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame frmDndCreator = new JFrame(GUI.class.getSimpleName());
                frmDndCreator.setTitle("DND Creator");
                frmDndCreator.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
                frmDndCreator.addWindowListener(new WindowAdapter()
                		{
                			
                			public void windowClosing(WindowEvent e)
                			{
                				int x = JOptionPane.showConfirmDialog(frmDndCreator,"Are you ready to quit the game?","", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
                				if(x == JOptionPane.YES_OPTION)
                				{
                					e.getWindow().dispose();
                				}
                			}
                		});
                JTextArea ta = new JTextArea(24, 80);
                ta.setBackground(SystemColor.menu);
                ta.setEditable(false);
                JTextField tf = new JTextField();
                KeyAdapter enterMain = new KeyAdapter() {
                	@Override
                	public void keyPressed(KeyEvent arg0) {
                		if(arg0.getKeyCode() == KeyEvent.VK_ENTER)
                		{
                			currentState = getNextState(tf.getText()); //Analyze the line using textInput function, update the roster with any changes
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
                frmDndCreator.setLocationRelativeTo(null);
                frmDndCreator.setVisible(true);
                
                System.out.println("Welcome to my text based Character Creator! Type 'HELP' for a list of commands.");
                Timer t = new Timer(1000, new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent e) {
                    }
                });
                t.start();
            }
        });
    }

    public static State getNextState(String input)
    {
    	currentState.Action(input);
    	return currentState.getNextState();
    }
    
    public static void InitializeStates()
    {
    	currentState = new MainMenu();
    	State nextState = new GetUserInput(currentState);
    	currentState.nextPossibleStates.add(nextState);
    }
}