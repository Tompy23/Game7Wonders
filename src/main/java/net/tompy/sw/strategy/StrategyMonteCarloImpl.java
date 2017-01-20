package net.tompy.sw.strategy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.tompy.common.CommonException;
import net.tompy.sw.component.Card7Wonders;
import net.tompy.sw.data.ColorType;
import net.tompy.sw.data.MonteCarloData;
import net.tompy.sw.data.PlayGroupData;
import net.tompy.sw.data.PlayType;
import net.tompy.sw.game.Constants;
import net.tompy.sw.game.Game7Wonders;
import net.tompy.sw.game.GamePlayController;
import net.tompy.sw.game.Util7Wonders;
import net.tompy.sw.player.Player7Wonders;

public abstract class StrategyMonteCarloImpl extends StrategyRandomImpl implements Strategy 
{
	private static Logger log = LogManager.getLogger( "net.tompy.sw.strategy.StrategyMonteCarloImpl" );

	private int iterations;
	private GamePlayController gameControl;
	
	@Override
	public PlayGroupData decideCardToPlay(List<Card7Wonders> hand, Player7Wonders player) 
	{
		PlayGroupData returnValue = null;
		
		// Get cards to play list
		List< PlayGroupData > cardsToPlay = cardsCanPlay( 
				copyHand( player, hand ), 
				player, 
				player.computeAvailableResources(), 
				player.getRight().computeTradeableResources(), 
				player.getLeft().computeTradeableResources() );
		
		returnValue = chooseCard( player, cardsToPlay, hand );
	
		return returnValue;
	}
	
	protected PlayGroupData buildPlayGroupData( Player7Wonders player, List< PlayGroupData > cardsToPlay, List< Card7Wonders > hand )
	{
		PlayGroupData returnValue = null;
		

		int thisPlayerId = player.getId();
		Map< Integer, MonteCarloData > dataMap = new HashMap< Integer, MonteCarloData >();

		try
		{
			// For each card to play (get id)
			for ( PlayGroupData pgd : cardsToPlay )
			{
				int bestScoreForCard = 0;
				// Loop through N times
				for ( int i = 0; i < iterations; i++ )
				{
					// Copy Game state for this player
					Game7Wonders game = Util7Wonders.copyGame( player.getGame() );
					
					// Set all players to Random Strategy
					preparePlayers( game, thisPlayerId, pgd, hand );
					
					playGame( game );

					// Collect the scores for all players
					bestScoreForCard = bestScoreAlgorithm( game, thisPlayerId, bestScoreForCard );
				}
				
				// Choose best score for this card
				MonteCarloData mcd = new MonteCarloData();
				mcd.setScore( bestScoreForCard );
				mcd.setCard( pgd );
				dataMap.put( mcd.getScore(), mcd );
				log.info( player.getName() + " - " + pgd.getCard().getName() + " [" + bestScoreForCard + "]" );
			}
			
			// Compare scores for all cards and chose the best.
			returnValue = bestCard( dataMap );
		}
		catch( CommonException ce )
		{
			ce.printStackTrace();
		}
		
		return returnValue;
	}
	
	private void preparePlayers( Game7Wonders game, int thisPlayerId, PlayGroupData pgd, List< Card7Wonders > hand ) throws CommonException
	{
		// Set all players to Random Strategy
		for ( Player7Wonders p : game.getPlayerOrder() )
		{
			p.setStrategy( new StrategyRandomImpl() );
			if ( p.getId() == thisPlayerId )
			{
				pgd.setType( PlayType.CARD );
				if ( pgd.getCard().getColor() == ColorType.WONDER )
				{
					pgd.setType( PlayType.WONDER );
					pgd.setCardForWonder( hand.get( 0 ) );
				}
				p.setCardToPlay( pgd );
			}
			else
			{
				p.changeState( Constants.PLAYER_TAKE_TURN );
				p.update();
			}
		}
	}
	
	private void playGame( Game7Wonders game ) throws CommonException
	{
		// All players update
		// ... continue with rest of states and update
		Util7Wonders.playerFunction( game, Constants.PLAYER_PLAY_CARD );
		Util7Wonders.playerFunction( game, Constants.PLAYER_CARD_ACTION );
		Util7Wonders.playerFunction( game, Constants.PLAYER_PASS_CARDS );
		Util7Wonders.playerFunction( game, Constants.PLAYER_PICK_UP_CARDS );
		game.setTurn( game.getTurn() + 1 );
		
		// Play rest of turn this round if any.
		gameControl.playTurns( game );

		game.setRound( game.getRound() + 1 );

		// Play rest of rounds
		for ( int k = game.getRound(); k <= game.getRounds(); k++ )
		{
			game.setRound( k );
			gameControl.playRound( game );
		}

		game.changeState( Constants.GAME_SCORE );
		game.update();
	}
	
	protected abstract int bestScoreAlgorithm( Game7Wonders game, int thisPlayerId, int bestScoreForCard );
	
	protected PlayGroupData bestCard( Map< Integer, MonteCarloData > dataMap )
	{
		PlayGroupData returnValue = null;
		
		// Compare scores for all cards and chose the best.
		List< Integer > finalScores = new ArrayList< Integer >();
		for ( Integer i : dataMap.keySet() )
		{
			finalScores.add( i );
		}

		if ( ! finalScores.isEmpty() )
		{
			Collections.sort( finalScores );
			returnValue = dataMap.get( finalScores.get( finalScores.size() - 1 ) ).getCard();
		}

		return returnValue;
	}

	public void setIterations(int iterations) {
		this.iterations = iterations;
	}

	public void setGameControl(GamePlayController gameControl) {
		this.gameControl = gameControl;
	}

}
