package net.tompy.sw.function;

import net.tompy.common.CommonConstants;
import net.tompy.sw.component.Card7Wonders;
import net.tompy.sw.player.Player7Wonders;

public class CardFunctorAddCoinsImpl implements CardFunctor 
{
	private int coins;
	
	@Override
	public String display()
	{
		return "Add " + coins + " coins." + CommonConstants.LINE_END;
	}

	@Override
	public void functor(Player7Wonders player, Card7Wonders card) 
	{
		player.addCoins( coins );
	}

	public void setCoins(int coins) {
		this.coins = coins;
	}

}
