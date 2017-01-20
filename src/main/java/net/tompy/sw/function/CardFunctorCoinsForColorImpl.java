package net.tompy.sw.function;

import net.tompy.common.CommonConstants;
import net.tompy.sw.component.Card7Wonders;
import net.tompy.sw.data.ColorType;
import net.tompy.sw.game.Util7Wonders;
import net.tompy.sw.player.Player7Wonders;

public class CardFunctorCoinsForColorImpl implements CardFunctor
{
	private int coins;
	private ColorType color;
	
	@Override
	public String display() 
	{
		return "Add " + coins + " coins for each " + color + " card in." + CommonConstants.LINE_END;
	}

	@Override
	public void functor(Player7Wonders player, Card7Wonders card) 
	{
		int count = Util7Wonders.countOfCards( player, color );
		
		player.addCoins( count * coins );
	}

	public void setCoins(int coins) {
		this.coins = coins;
	}

	public void setColor(ColorType color) {
		this.color = color;
	}

}
