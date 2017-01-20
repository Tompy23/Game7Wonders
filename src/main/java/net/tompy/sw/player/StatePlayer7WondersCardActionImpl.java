package net.tompy.sw.player;

import net.tompy.gameai.fsm.State;
import net.tompy.sw.data.PlayGroupData;

public class StatePlayer7WondersCardActionImpl implements State<Player7Wonders> {

	@Override
	public void execute(Player7Wonders me) 
	{
		PlayGroupData cardToPlay = me.getCardToPlay();
		
		switch ( cardToPlay.getType() )
		{
			case COINS:
				me.addCoins( 3 );
				break;
			case WONDER:
			case CARD:
				cardToPlay.getCard().playCard( me, cardToPlay );
				break;
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
