package net.tompy.sw.player;

import java.util.List;

import net.tompy.gameai.fsm.State;
import net.tompy.sw.component.Card7Wonders;
import net.tompy.sw.component.Wonder;
import net.tompy.sw.data.PlayGroupData;

public class StatePlayer7WondersPlayCardImpl implements State<Player7Wonders> {

	@Override
	public void execute(Player7Wonders me)
	{
		PlayGroupData cardToPlay = me.getCardToPlay();
		Wonder wonder = me.getWonder();
		List< Card7Wonders > hand = me.getHand();
		List< Card7Wonders > inPlay = me.getInPlay();

		//log.info( cardToPlay.getCard().getName() + " [" + cardToPlay.getType() + "]" );
		hand.remove( cardToPlay.getCard() );
		switch ( cardToPlay.getType() )
		{
			case COINS:
				break;
			case WONDER:
				Card7Wonders wCard = wonder.playNextWonder();
				cardToPlay.setCard( wCard );
				inPlay.add( wCard );
				hand.remove( cardToPlay.getCardForWonder() );
				break;
			case CARD:
				inPlay.add( cardToPlay.getCard() );
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
