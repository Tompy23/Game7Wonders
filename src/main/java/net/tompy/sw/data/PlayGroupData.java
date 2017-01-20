package net.tompy.sw.data;

import net.tompy.sw.component.Card7Wonders;


public class PlayGroupData
{
	private PlayType type = null;
	private Card7Wonders card = null;
	private int costLeft = 0;
	private int costRight = 0;
	private int costBank = 0;
	private Card7Wonders cardForWonder = null;
	
	public PlayGroupData( Card7Wonders c, int cb, int cr, int cl )
	{
		card = c;
		costBank = cb;
		costRight = cr;
		costLeft = cl;
	}
	
	public PlayGroupData()
	{
		
	}
	
	public PlayType getType() {
		return type;
	}
	public void setType(PlayType type) {
		this.type = type;
	}
	public Card7Wonders getCard() {
		return card;
	}
	public void setCard(Card7Wonders card) {
		this.card = card;
	}
	public int getCostLeft() {
		return costLeft;
	}
	public void setCostLeft(int costLeft) {
		this.costLeft = costLeft;
	}
	public int getCostRight() {
		return costRight;
	}
	public void setCostRight(int costRight) {
		this.costRight = costRight;
	}
	public Card7Wonders getCardForWonder() {
		return cardForWonder;
	}
	public void setCardForWonder(Card7Wonders cardForWonder) {
		this.cardForWonder = cardForWonder;
	}
	public int getCostBank() {
		return costBank;
	}
	public void setCostBank(int costBank) {
		this.costBank = costBank;
	}

}
