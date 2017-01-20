package net.tompy.sw.function;

import net.tompy.sw.component.Card7Wonders;
import net.tompy.sw.data.ColorType;
import net.tompy.sw.player.Player7Wonders;

public class CardFunctorPointsForNeighborMilitaryLossImpl implements CardFunctor 
{
	private int points;
	
	@Override
	public String display() 
	{
		return "Add " + points + " points for each neighbor's military loss.";
	}

	@Override
	public void functor(Player7Wonders player, Card7Wonders card) 
	{
		for ( int i : player.getLeft().getMilitaryAwards() )
		{
			if ( -1 == i )
			{
				player.getScore().addPoints( points, ColorType.PURPLE );
			}
		}
	}

}
