package net.tompy.sw.player;

import net.tompy.gameai.fsm.FSMCreator;
import net.tompy.gameai.fsm.FiniteStateMachine;
import net.tompy.gameai.fsm.FiniteStateMachineBasicImpl;

public class FSMCreatorPlayerImpl implements FSMCreator< Player7Wonders >
{
	@Override
	public FiniteStateMachine< Player7Wonders > create( Player7Wonders player )
	{
		FiniteStateMachine< Player7Wonders > returnValue = new FiniteStateMachineBasicImpl< Player7Wonders >();
		
		returnValue.setOwner( player );
		
		return returnValue;
	}
}
