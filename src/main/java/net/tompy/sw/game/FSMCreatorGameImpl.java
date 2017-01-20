package net.tompy.sw.game;

import net.tompy.gameai.fsm.FSMCreator;
import net.tompy.gameai.fsm.FiniteStateMachine;
import net.tompy.gameai.fsm.FiniteStateMachineBasicImpl;

public class FSMCreatorGameImpl implements FSMCreator<Game7Wonders> 
{
	@Override
	public FiniteStateMachine<Game7Wonders> create( Game7Wonders owner ) 
	{
		FiniteStateMachine< Game7Wonders > returnValue = new FiniteStateMachineBasicImpl< Game7Wonders >();
		
		returnValue.setOwner( owner );
		
		return returnValue;
	}

}
