package net.tompy.sw.component;

import java.util.List;

import net.tompy.gameai.deck.Card;
import net.tompy.sw.data.ColorType;
import net.tompy.sw.data.PlayGroupData;
import net.tompy.sw.data.ResourceGroupData;
import net.tompy.sw.data.ScienceGroupData;
import net.tompy.sw.data.ScienceType;
import net.tompy.sw.player.Player7Wonders;

public interface Card7Wonders extends Card 
{
	public int getPlayerCount();
	public int getAge();
	
	public void playCard( Player7Wonders p, PlayGroupData pg );
	
	public ColorType getColor();
	public String getForFree();
	
	public ResourceGroupData getCost();
	public int getCostCoin();
	
	public List< ResourceGroupData > aggregateAvailableResources( List < ResourceGroupData > rgList );
	public List< ResourceGroupData > aggregateTradeableResources( List < ResourceGroupData > rgList );

	public int getMilitaryValue();
	public ScienceType getScience();
	
	public void assignPoints( Player7Wonders p );
	public void scoreScience( Player7Wonders player, List< ScienceGroupData > scienceList );
}
