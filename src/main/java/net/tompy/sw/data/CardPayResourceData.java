package net.tompy.sw.data;


public class CardPayResourceData 
{
	private ResourceGroupData available;
	private ResourceGroupData tradeableRight;
	private ResourceGroupData tradeableLeft;
	
	public CardPayResourceData( ResourceGroupData a, ResourceGroupData tr, ResourceGroupData tl )
	{
		available = a;
		tradeableRight = tr;
		tradeableLeft = tl;
	}
	
	public ResourceGroupData getAvailable() {
		return available;
	}

	public ResourceGroupData getTradeableRight() {
		return tradeableRight;
	}

	public ResourceGroupData getTradeableLeft() {
		return tradeableLeft;
	}
	
}
