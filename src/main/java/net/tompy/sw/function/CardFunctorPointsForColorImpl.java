package net.tompy.sw.function;

import java.util.List;

import net.tompy.common.CommonConstants;
import net.tompy.sw.component.Card7Wonders;
import net.tompy.sw.data.ColorType;
import net.tompy.sw.game.Util7Wonders;
import net.tompy.sw.player.Player7Wonders;

public class CardFunctorPointsForColorImpl implements CardFunctor 
{
	private int points = 0;
	private List< ColorType > colors = null;
	
	@Override
	public String display() 
	{
		String out = "";
		
		for ( ColorType ct : colors )
		{
			out += "Score " + points + " points for each " + ct + " card in play for self and neighbors." + CommonConstants.LINE_END;
		}
		return out;
	}

	@Override
	public void functor(Player7Wonders player, Card7Wonders card) 
	{
		for ( ColorType ct : colors )
		{
			player.getScore().addPoints( Util7Wonders.countOfCards( player, ct ) * points, card.getColor() );
		}
	}

	public void setPoints(int points) {
		this.points = points;
	}

	public void setColors(List<ColorType> colors) {
		this.colors = colors;
	}
}
