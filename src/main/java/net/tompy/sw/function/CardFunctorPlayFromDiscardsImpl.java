package net.tompy.sw.function;

import net.tompy.sw.component.Card7Wonders;
import net.tompy.sw.game.Game7Wonders;
import net.tompy.sw.player.Player7Wonders;

public class CardFunctorPlayFromDiscardsImpl implements CardFunctor 
{
	private Game7Wonders game;
	
	@Override
	public String display() 
	{
		return "Play a card from discards pile.";
	}

	@Override
	public void functor(Player7Wonders player, Card7Wonders card) 
	{
		player.playExtraHand( game.getDiscards() );
	}

	public void setGame(Game7Wonders game) {
		this.game = game;
	}

}
