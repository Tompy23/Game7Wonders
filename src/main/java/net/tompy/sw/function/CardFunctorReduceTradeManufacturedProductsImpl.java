package net.tompy.sw.function;

import net.tompy.common.CommonConstants;
import net.tompy.sw.component.Card7Wonders;
import net.tompy.sw.data.ResourceType;
import net.tompy.sw.player.Player7Wonders;

public class CardFunctorReduceTradeManufacturedProductsImpl implements CardFunctor
{
	private int newValue;
	
	@Override
	public String display() 
	{
		return "Reduce trade for Manufactured Products to " + newValue + "." + CommonConstants.LINE_END;		
	}

	@Override
	public void functor(Player7Wonders player, Card7Wonders card) 
	{
		player.reduceTradeCostLeft( ResourceType.GLASS, newValue );
		player.reduceTradeCostLeft( ResourceType.LOOM, newValue );
		player.reduceTradeCostLeft( ResourceType.PAPYRUS, newValue );
		player.reduceTradeCostRight( ResourceType.GLASS, newValue );
		player.reduceTradeCostRight( ResourceType.LOOM, newValue );
		player.reduceTradeCostRight( ResourceType.PAPYRUS, newValue );
	}

	public void setNewValue(int newValue) {
		this.newValue = newValue;
	}

}
