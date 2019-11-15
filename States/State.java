package States;

import java.util.ArrayList;

public abstract class State {
	
	public State() { nextPossibleStates = new ArrayList<State>(); };
	
	public State(ArrayList<State> states)
	{
		nextPossibleStates = states;
	}
	
	public ArrayList<State> nextPossibleStates;
	public Boolean isNextState = false;
	
	public abstract void Action(String input);
	
	public State getNextState()
	{
		for(State state : nextPossibleStates)
		{
			if(state.isNextState == true)
			{
				state.isNextState = false;
				return state;
			}
		}
		
		return this;
	};
	
}
