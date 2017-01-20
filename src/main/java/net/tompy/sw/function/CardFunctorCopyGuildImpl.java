package net.tompy.sw.function;

import net.tompy.sw.component.Card7Wonders;
import net.tompy.sw.player.Player7Wonders;

public class CardFunctorCopyGuildImpl implements CardFunctor {

	@Override
	public String display() 
	{
		return "During scoring, copy 1 Guild from a neighbor";
	}

	@Override
	public void functor(Player7Wonders player, Card7Wonders card) 
	{
		player.setCopyGuildFromNeighbor( true );
	}

}
