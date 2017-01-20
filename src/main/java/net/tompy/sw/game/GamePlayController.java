package net.tompy.sw.game;






import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.tompy.common.CommonConstants;
import net.tompy.common.CommonException;
import net.tompy.gameai.deck.Deck;
import net.tompy.sw.component.Card7Wonders;
import net.tompy.sw.player.Player7Wonders;

public class GamePlayController
{
	//private static Logger log = Logger.( "net.tompy.sw.game.GamePlayController" );
	private static Logger log = LogManager.getLogger("net.tompy.sw.game.GamePlayController");

	public void playRound( Game7Wonders me )
	{
		// Deal cards
		dealCards( me, me.getTheDecks().get( me.getRound() - 1 ) );
		
		if ( me.isReal() )
		{
			log.info( "Round: " + me.getRound() + CommonConstants.LINE_END );
		}
		else
		{
			log.debug( "Round: " + me.getRound() + CommonConstants.LINE_END );
		}
		
		playTurns( me );
	}
	
	public void playTurns( Game7Wonders me )
	{
		// Play turns
		for ( int i = me.getTurn(); i <= me.getTurns(); i++ )
		{
			me.setTurn( i );
			playTurn( me );
		}
		endOfRound( me );
		conductMilitary( me );
		me.setTurn( 1 );
	}
	
	private void dealCards( Game7Wonders me, Deck< Card7Wonders > deck )
	{
		for ( Player7Wonders p : me.getPlayerOrder() )
		{
			p.emptyHand();
		}
	
		// deal 7 cards
		for ( int i = 0; i < me.getCardsDealt(); i++ )
		{
			for ( Player7Wonders p : me.getPlayerOrder() )
			{
				p.dealCard( deck.get() );
			}
		}
	}		

	private void playTurn( Game7Wonders me )
	{
		if ( me.isReal() )
		{
			log.info( "Turn: " + me.getTurn() + CommonConstants.LINE_END );
		}
		else
		{
			log.debug( "Turn: " + me.getTurn() + CommonConstants.LINE_END );
		}

		for ( Player7Wonders p : me.getPlayerOrder() )
		{
			p.setRound( me.getRound() );
			p.setGame( me );
		}
		
		Util7Wonders.playerFunction( me, Constants.PLAYER_TAKE_TURN );
		Util7Wonders.playerFunction( me, Constants.PLAYER_PLAY_CARD );
		Util7Wonders.playerFunction( me, Constants.PLAYER_CARD_ACTION );
		Util7Wonders.playerFunction( me, Constants.PLAYER_PASS_CARDS );
		Util7Wonders.playerFunction( me, Constants.PLAYER_PICK_UP_CARDS );

		log.debug( "END TURN" );
		
	}
	
	
	public void endOfRound( Game7Wonders me )
	{
		for ( Player7Wonders p : me.getPlayerOrder() )
		{
			if ( p.isCanPlayLastCard() )
			{
				try
				{
					p.setRound( 0 );
					p.changeState( Constants.PLAYER_TAKE_TURN );
					p.update();
					p.changeState( Constants.PLAYER_PLAY_CARD );
					p.update();
					p.changeState( Constants.PLAYER_CARD_ACTION );
					p.update();
				}
				catch ( CommonException ce )
				{
					ce.printStackTrace();
				}

				// Put hand in discard pile
				for ( Card7Wonders card : p.getHand() )
				{
					me.addToDiscard( card );
				}
			}
		}
	}
	
	public void conductMilitary( Game7Wonders me )
	{
		for ( Player7Wonders player : me.getPlayerOrder() )
		{
			if ( player.calculateMilitaryStrength() > player.getRight().calculateMilitaryStrength() )
			{
				player.addMilitaryPoints( Constants.MILITARY_POINTS[ me.getRound() ] );
				player.getRight().addMilitaryPoints( Constants.MILITARY_POINTS[ 0 ] );
			}
			else if ( player.calculateMilitaryStrength() < player.getRight().calculateMilitaryStrength() )
			{
				player.addMilitaryPoints( Constants.MILITARY_POINTS[ 0 ] );
				player.getRight().addMilitaryPoints( Constants.MILITARY_POINTS[ me.getRound() ] );
			}
			else
			{
				player.addMilitaryPoints( 0 );
				player.getRight().addMilitaryPoints( 0 );
			}
		}
	}
}
