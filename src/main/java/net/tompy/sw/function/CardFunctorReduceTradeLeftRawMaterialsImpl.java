package net.tompy.sw.function;

import net.tompy.common.CommonConstants;
import net.tompy.sw.component.Card7Wonders;
import net.tompy.sw.data.ResourceType;
import net.tompy.sw.player.Player7Wonders;

public class CardFunctorReduceTradeLeftRawMaterialsImpl implements CardFunctor 
{
	private int newValue;
	
	@Override
	public String display() 
	{
		return "Reduce trade left for Raw Materials to " + newValue + "." + CommonConstants.LINE_END;
	}

	@Override
	public void functor(Player7Wonders player, Card7Wonders card) 
	{
		player.reduceTradeCostLeft( ResourceType.WOOD, newValue );
		player.reduceTradeCostLeft( ResourceType.CLAY, newValue );
		player.reduceTradeCostLeft( ResourceType.ORE, newValue );
		player.reduceTradeCostLeft( ResourceType.STONE, newValue );
	}

	public void setNewValue(int newValue) {
		this.newValue = newValue;
	}

}
