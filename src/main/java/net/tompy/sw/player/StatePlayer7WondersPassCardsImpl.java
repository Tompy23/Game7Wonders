package net.tompy.sw.player;

import net.tompy.gameai.fsm.State;

public class StatePlayer7WondersPassCardsImpl implements State<Player7Wonders> {

	@Override
	public void execute(Player7Wonders me) 
	{
		if ( me.getRound() % 2 == 0 )
		{
			me.getRight().SetNextHand( me.getHand() );
		}
		else
		{
			me.getLeft().SetNextHand( me.getHand() );
		}

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
