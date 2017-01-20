package net.tompy.sw.player;

import net.tompy.gameai.fsm.State;

public class StatePlayer7WondersPickUpCardsImpl implements State<Player7Wonders> {

	@Override
	public void execute(Player7Wonders me) 
	{
		me.setHand( me.getNextHand() );
	}

	@Override
	public void enter(Player7Wonders me) {
		// TODO Auto-generated method stub

	}

	@Override
	public void exit(Player7Wonders me) {
		// TODO Auto-generated method stub

	}

}
