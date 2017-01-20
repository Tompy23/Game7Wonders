package net.tompy.sw.function;

import net.tompy.common.CommonConstants;
import net.tompy.sw.component.Card7Wonders;
import net.tompy.sw.player.Player7Wonders;

public class CardFunctorNoOpImpl implements CardFunctor {

	@Override
	public String display() 
	{
		return "NA" + CommonConstants.LINE_END;
	}

	@Override
	public void functor(Player7Wonders player, Card7Wonders card)
	{
		// No op
	}
}
