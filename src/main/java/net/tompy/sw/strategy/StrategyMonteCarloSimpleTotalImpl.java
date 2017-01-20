package net.tompy.sw.strategy;

import net.tompy.sw.game.Game7Wonders;
import net.tompy.sw.player.Player7Wonders;

public class StrategyMonteCarloSimpleTotalImpl extends StrategyMonteCarloImpl implements Strategy 
{

	protected int bestScoreAlgorithm( Game7Wonders game, int thisPlayerId, int bestScoreForCard )
	{
		for ( Player7Wonders p : game.getPlayers() )
		{
			if ( p.getId() == thisPlayerId )
			{
				{
					bestScoreForCard += p.getScore().getTotal();
				}
			}
		}
		
		return bestScoreForCard;
	}

}
