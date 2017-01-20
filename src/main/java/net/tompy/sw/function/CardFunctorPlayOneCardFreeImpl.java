package net.tompy.sw.function;

import net.tompy.sw.component.Card7Wonders;
import net.tompy.sw.player.Player7Wonders;

public class CardFunctorPlayOneCardFreeImpl implements CardFunctor {

	@Override
	public String display() 
	{
		return "Once per Age, play 1 card free";
	}

	@Override
	public void functor(Player7Wonders player, Card7Wonders card) {
		// TODO Auto-generated method stub

	}

}
