package net.tompy.sw.strategy;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import net.tompy.sw.component.Card7Wonders;
import net.tompy.sw.data.PlayGroupData;
import net.tompy.sw.data.PlayType;
import net.tompy.sw.data.ResourceGroupData;
import net.tompy.sw.data.ResourceType;
import net.tompy.sw.data.TradeData;
import net.tompy.sw.game.Game7Wonders;
import net.tompy.sw.player.Player7Wonders;

public class StrategyHumanImpl implements Strategy 
{

	//private Log log = MyLog.getLog();
	private Game7Wonders game;

	@Override
	public PlayGroupData decideCardToPlay(List<Card7Wonders> hand, Player7Wonders player) 
	{
		PlayGroupData returnValue = new PlayGroupData();
		List< Card7Wonders > copyOfHand = new ArrayList< Card7Wonders >();
		
		copyOfHand.addAll( hand );
		copyOfHand.add( player.getNextWonder() );
		
		System.out.println( "First trade, then choose a card to play." );
		
		displayHand( copyOfHand );
		displayAvailableResources( player );
		displayPossibleCommerce( player );
		
		TradeData tradeData = new TradeData();
		getTrades( returnValue, player, tradeData );
			
		returnValue = getCard( returnValue, player, hand );
		
		reconcile( returnValue, tradeData, player );
		
		if ( ! isLegal( returnValue, tradeData, player ) )
		{
			returnValue = decideCardToPlay( hand, player );
		}
		
		return returnValue;
	}
	
	private TradeData getTrades( PlayGroupData pgd, Player7Wonders player, TradeData tradeData )
	{
		Scanner scanIn = new Scanner( System.in );
		String nextLine = "";
		while ( ! "done".equals( nextLine ) )
		{
			System.out.println( "Trade: " );
			nextLine = scanIn.nextLine();
			parseTrade( nextLine, pgd, player, tradeData );
		}
		scanIn.close();
		return tradeData;
	}
	
	private PlayGroupData getCard( PlayGroupData pgd, Player7Wonders player, List< Card7Wonders > hand )
	{
		Scanner scanIn = new Scanner( System.in );
		String nextLine = "";
		while ( ! "done".equals( nextLine ) )
		{
			System.out.println( "Play Card: " );
			nextLine = scanIn.nextLine();
			parseCard( nextLine, pgd, player, hand );
		}
		scanIn.close();
		return pgd;
	}
	
	private void parseCard( String line, PlayGroupData pgd, Player7Wonders player, List< Card7Wonders > hand )
	{
		String[] bits = line.split( " " );
		// For now, let's just assume this is only a card command
		try
		{
			Card7Wonders theCard = null;
			for( Card7Wonders card : hand )
			{
				if ( Integer.valueOf( bits[ 0 ] ) == card.getId() )
				{
					theCard = card;
					break;
				}
			}
			
			if ( bits[ 1 ].equalsIgnoreCase( "WONDER" ) )
			{
				pgd.setCard( player.getNextWonder() );
				pgd.setCardForWonder( theCard );
			}
			else
			{
				pgd.setCard( theCard );
			}
			
			pgd.setType( PlayType.valueOf( bits[ 1 ] ) );
		}
		catch( NumberFormatException nfe )
		{
			parseShow( line, player );
		}
	}
	
	private TradeData parseTrade( String line, PlayGroupData pgd, Player7Wonders player, TradeData td )
	{
		String[] bits = line.split( " " );
		if ( bits.length == 3 && ( "L".equalsIgnoreCase( bits[ 0 ] ) || "R".equalsIgnoreCase( bits[ 0 ] ) ) )
		{
			if ( "L".equalsIgnoreCase( bits[ 0 ] ) )
			{
				td.setRgdLeft( new ResourceGroupData() );
				td.getRgdLeft().increment( ResourceType.valueOf( bits[ 1 ] ), Integer.valueOf( bits[ 2 ] ) );
			}
			else
			{
				td.setRgdRight( new ResourceGroupData() );
				td.getRgdRight().increment( ResourceType.valueOf( bits[ 1 ] ), Integer.valueOf( bits[ 2 ] ) );
			}
		}
		else
		{
			parseShow( line, player );
		}
		
		return td;
	}

	private void parseShow( String line, Player7Wonders player )
	{
		String[] bits = line.split( " " );
		if ( "SHOW".equalsIgnoreCase( bits[ 0 ] ) )
		{
			if ( "R".equalsIgnoreCase( bits[ 1 ] ) )
			{
				game.showPlayers( player.getRight().getName() );
			}
			else if ( "L".equalsIgnoreCase( bits[ 1 ] ) )
			{
				game.showPlayers( player.getLeft().getName() );
			}
			else
			{
				System.out.println( game.showPlayers( bits[ 1 ] ) );
			}
		}
	}
	
	private void displayHand( List< Card7Wonders > hand )
	{
		for ( Card7Wonders card : hand )
		{
			System.out.println( card.display() );
		}
		for ( Card7Wonders card : hand )
		{
			System.out.println( card.getId() + ": " + card.getName() );
		}
	}
	
	private void displayAvailableResources( Player7Wonders player )
	{
		System.out.println( "Available Resources" );
		for ( ResourceGroupData rgd : player.computeAvailableResources() )
		{
			System.out.println( rgd.display() );
			System.out.println( "###" );
		}
	}

	private void displayPossibleCommerce( Player7Wonders player )
	{
		System.out.println( "Available Commerce" );
		System.out.println( "<-Left<-" );
		for ( ResourceGroupData rgdLeft : player.getLeft().computeTradeableResources() )
		{
			System.out.println( rgdLeft.display() );
			System.out.println( "###" );
		}
		
		System.out.println( "->Right->" );
		for ( ResourceGroupData rgdRight : player.getRight().computeTradeableResources() )
		{
			System.out.println( rgdRight.display() );
			System.out.println( "###" );
		}
	}
	
	private void reconcile( PlayGroupData pgd, TradeData td, Player7Wonders player )
	{
		for ( ResourceType rt : ResourceType.values() )
		{
			pgd.setCostLeft( pgd.getCostLeft() + ( td.getRgdLeft().get( rt ) * player.getTradeCostLeft( rt ) ) );
			pgd.setCostRight( pgd.getCostRight() + ( td.getRgdRight().get( rt ) * player.getTradeCostRight( rt ) ) );
			if ( pgd.getType() != PlayType.COINS )
			{
				pgd.setCostBank( pgd.getCard().getCostCoin() );
			}
		}
		
	}
	
	private boolean isLegal( PlayGroupData pgd, TradeData td, Player7Wonders player )
	{
		return true;
	}

	public void setGame(Game7Wonders game) {
		this.game = game;
	}
}
