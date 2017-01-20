package net.tompy.sw.function;

import net.tompy.sw.component.Card7Wonders;
import net.tompy.sw.player.Player7Wonders;

public class CardFunctorPlayLastCardImpl implements CardFunctor 
{

	@Override
	public String display() 
	{
		return "Player may play last card of an age.";
	}

	@Override
	public void functor(Player7Wonders player, Card7Wonders card) 
	{
		player.setCanPlayLastCard( true );
	}

}
