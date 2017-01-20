package net.tompy.sw.function;

import java.util.List;

import net.tompy.common.CommonConstants;
import net.tompy.sw.component.Card7Wonders;
import net.tompy.sw.data.ColorType;
import net.tompy.sw.game.Util7Wonders;
import net.tompy.sw.player.Player7Wonders;

public class CardFunctorCoinsForNeighborsColorImpl implements CardFunctor 
{
	private List< ColorType > colors;
	private int coins;
	private boolean self = false;
	
	@Override
	public String display() 
	{
		String out = "";
		out += "Add " + coins + " coins for each ";
		for ( ColorType ct : colors )
		{
			out += ct + " ";
		}
		return out + "card in play for " + ( self ? "self and " : "" ) + " neighbors." + CommonConstants.LINE_END;
	}

	@Override
	public void functor(Player7Wonders player, Card7Wonders card) 
	{
		for ( ColorType ct : colors )
		{
			player.getScore().addPoints( Util7Wonders.countOfCards( player.getLeft(), ct ) * coins, card.getColor() );
			player.getScore().addPoints( Util7Wonders.countOfCards( player.getRight(), ct ) * coins, card.getColor() );
			if ( self )
			{
				player.getScore().addPoints( Util7Wonders.countOfCards( player, ct ) * coins, card.getColor() );
			}
		}
	}

	public void setSelf(boolean self) {
		this.self = self;
	}

	public void setColors(List<ColorType> colors) {
		this.colors = colors;
	}

	public void setCoins(int coins) {
		this.coins = coins;
	}

}
