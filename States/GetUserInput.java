package States;

import java.util.ArrayList;

import Objects.DnDCharacter;
import Objects.GlobalVars;
import Statics.Constants;

public class GetUserInput extends State {
	
	public GetUserInput(State previousState)
	{
		nextPossibleStates = new ArrayList<State>();
		nextPossibleStates.add(previousState);
	}
	
	@Override
	public void Action(String input) {
		// set player's name to input
		// this can probably be more generic in the future somehow...
		// This state specifically sets the player's name to input, not just get user input
    	DnDCharacter curChar = GlobalVars.roster.get(GlobalVars.currentCharInRoster);
    	
	    switch(input.toLowerCase())
	    {
			case "y":
		    case "yes":
		    	curChar.Class = "Peasant";
		    	curChar.rollEverything();
	            
	            System.out.println("This DnDCharacter's name will be " + curChar.Name);
	            System.out.println("Rolling " + curChar.Name + "'s stats....");
	            for(int i = 0; i < 6; i++)
	            {
	                System.out.println(Constants.variables[i + 1] + ": " + curChar.stats[i]); //Prints out the name of stats and their values as located in the Char class
	            }
	            
		        System.out.println(curChar.Name + " successfully added to the party!");
		        break;
		    case "n":
		    default:
		    	System.out.println(curChar.Name + " successfully added to the party! They currently are naked though.");
	    }
    
		nextPossibleStates.get(0).isNextState = true;
	}
	
}
