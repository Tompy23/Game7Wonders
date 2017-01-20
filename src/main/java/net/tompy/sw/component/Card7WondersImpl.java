package net.tompy.sw.component;

import java.util.ArrayList;
import java.util.List;

import net.tompy.common.CommonConstants;
import net.tompy.common.log.Log;
import net.tompy.common.log.MyLog;
import net.tompy.gameai.deck.CardAbstractImpl;
import net.tompy.sw.data.ColorType;
import net.tompy.sw.data.PlayGroupData;
import net.tompy.sw.data.ResourceGroupData;
import net.tompy.sw.data.ScienceGroupData;
import net.tompy.sw.data.ScienceType;
import net.tompy.sw.function.CardFunctor;
import net.tompy.sw.player.Player7Wonders;

public class Card7WondersImpl extends CardAbstractImpl implements Card7Wonders 
{
	private Log log = MyLog.getLog();

	private List< ResourceGroupData > availableResources= null;
	private List< ResourceGroupData > tradeableResources= null;
	private List< ResourceGroupData > costResources = null;
	private int costCoin = 0;
	
	private int militaryValue = 0;
	private int victoryPoints = 0;
	
	private ScienceType science = null;
	private ColorType color = null;
	
	private int playerCount = -1;
	private int age = -1;
	
	private String forFree = null;
	
	private CardFunctor whenPlayed = null;
	private CardFunctor whenScored = null;
	private CardFunctor whenScienceScored = null;
	
	 		
	@Override
	public void playCard( Player7Wonders p, PlayGroupData pg ) 
	{
		log.info( "Coins" + CommonConstants.LINE_END + "Bank: " + costCoin + "  Left: " + pg.getCostLeft() + "  Right: " + pg.getCostRight() + CommonConstants.LINE_END );
		p.addCoins( -1 * ( costCoin + pg.getCostLeft() + pg.getCostRight() ) );
		p.getLeft().addCoins( pg.getCostLeft() );
		p.getRight().addCoins( pg.getCostRight() );
		if ( null != whenPlayed )
		{
			whenPlayed.functor( p, this );
		}
	}
	
	@Override
	public List< ResourceGroupData > aggregateAvailableResources( List< ResourceGroupData > rgList ) 
	{
		return aggregateResources( rgList, availableResources );
	}

	@Override
	public List< ResourceGroupData > aggregateTradeableResources( List< ResourceGroupData > rgList) 
	{
		return aggregateResources( rgList, tradeableResources );
	}

	private List< ResourceGroupData > aggregateResources( List< ResourceGroupData > rgList, List< ResourceGroupData > targetList )
	{
		// Return value starts empty.
		List< ResourceGroupData > returnValue = new ArrayList< ResourceGroupData >();
		
		if ( ! rgList.isEmpty() )
		{
			// Loop through the original list of resource groups
			for ( ResourceGroupData rgOrig : rgList )
			{
				// Create a new list for each new resource
				List< ResourceGroupData > tempList = new ArrayList< ResourceGroupData >();
				for ( ResourceGroupData rgCard : targetList )
				{
					ResourceGroupData rgTemp = new ResourceGroupData(  rgCard ); 
					tempList.add( rgTemp );
					rgTemp.add( rgOrig );
				}
				// Add the newly created list.
				returnValue.addAll( tempList );
			}
		}
		else
		{
			returnValue.addAll( targetList );
		}

		return returnValue;
	}
	
	@Override
	public void scoreScience(Player7Wonders player, List<ScienceGroupData> scienceList) 
	{
		if ( null != science )
		{
			for ( ScienceGroupData sgd : scienceList )
			{
				sgd.increment( science );
			}
		}
		if ( null != whenScienceScored )
		{
			whenScienceScored.functor( player, this );
		}
	}
	
	@Override
	public void assignPoints( Player7Wonders p ) 
	{
		p.getScore().addPoints( victoryPoints, color );
		if ( null != whenScored )
		{
			whenScored.functor( p, this );
		}
	}

	@Override
	public String display()
	{
		String returnValue = super.display();
		
		returnValue += "Color: " + color + CommonConstants.LINE_END;
		
		if ( null != forFree )
		{
			returnValue += "For free with " + forFree + CommonConstants.LINE_END;
		}

		returnValue += "Cost" + CommonConstants.LINE_END;
		if ( costCoin > 0 )
		{
			returnValue += "Coins: " + costCoin + CommonConstants.LINE_END;
		}
		for ( ResourceGroupData rg : costResources )
		{
			returnValue += rg.display() + CommonConstants.LINE_END;
			returnValue += "###" + CommonConstants.LINE_END;
		}
		returnValue += "Available" + CommonConstants.LINE_END;
		for ( ResourceGroupData rg : availableResources )
		{
			returnValue += rg.display() + CommonConstants.LINE_END;
			returnValue += "###" + CommonConstants.LINE_END;
		}
		
		returnValue += "Tradeable" + CommonConstants.LINE_END;
		for ( ResourceGroupData rg : tradeableResources )
		{
			returnValue += rg.display() + CommonConstants.LINE_END;
			returnValue += "###" + CommonConstants.LINE_END;
		}
		
		if ( militaryValue > 0 )
		{
			returnValue += "Military Value: " + militaryValue + CommonConstants.LINE_END;
		}
		
		if ( victoryPoints > 0 )
		{
			returnValue += "Victory Points: " + victoryPoints + CommonConstants.LINE_END;
		}
		
		if ( null != science )
		{
			returnValue += "Science: " + science.name() + CommonConstants.LINE_END;
		}
		
		if ( null != whenPlayed )
		{
			returnValue += "When played" + CommonConstants.LINE_END;
			returnValue += whenPlayed.display();
		}
		
		if ( null != whenScored )
		{
			returnValue += "When scored" + CommonConstants.LINE_END;
			returnValue += whenScored.display();
		}

		if ( null != whenScienceScored )
		{
			returnValue += "When Science scored" + CommonConstants.LINE_END;
			returnValue += whenScienceScored.display();
		}

		return returnValue;
	}

	@Override
	public ScienceType getScience() 
	{
		return science;
	}

	@Override
	public ResourceGroupData getCost()
	{
		return costResources.get( 0 );
	}

	public void setAvailableResources(List<ResourceGroupData> availableResources) 
	{
		this.availableResources = availableResources;
	}

	public void setTradeableResources(List<ResourceGroupData> tradeableResources) {
		this.tradeableResources = tradeableResources;
	}

	public void setCostResources(List<ResourceGroupData> costResources) {
		this.costResources = costResources;
	}

	public void setCostCoin(int costCoin) {
		this.costCoin = costCoin;
	}

	public void setWhenPlayed(CardFunctor whenPlayed) {
		this.whenPlayed = whenPlayed;
	}

	public void setForFree(String forFree) {
		this.forFree = forFree;
	}

	public void setMilitaryValue(int militaryValue) {
		this.militaryValue = militaryValue;
	}

	public void setVictoryPoints(int victoryPoints) {
		this.victoryPoints = victoryPoints;
	}

	public void setScience(ScienceType science) {
		this.science = science;
	}

	public void setColor(ColorType color) {
		this.color = color;
	}

	public ColorType getColor() {
		return color;
	}

	public void setWhenScored(CardFunctor whenScored) {
		this.whenScored = whenScored;
	}

	public void setWhenScienceScored(CardFunctor whenScienceScored) {
		this.whenScienceScored = whenScienceScored;
	}

	public String getForFree() {
		return forFree;
	}

	public int getCostCoin() {
		return costCoin;
	}

	public int getMilitaryValue() {
		return militaryValue;
	}

	public int getPlayerCount() {
		return playerCount;
	}

	public void setPlayerCount(int playerCount) {
		this.playerCount = playerCount;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}
}
