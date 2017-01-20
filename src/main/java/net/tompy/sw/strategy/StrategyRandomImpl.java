package net.tompy.sw.strategy;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.tompy.common.CommonConstants;
import net.tompy.gameai.util.GameUtil;
import net.tompy.sw.component.Card7Wonders;
import net.tompy.sw.data.CardPayResourceData;
import net.tompy.sw.data.ColorType;
import net.tompy.sw.data.PlayGroupData;
import net.tompy.sw.data.PlayType;
import net.tompy.sw.data.ResourceGroupData;
import net.tompy.sw.data.ResourceType;
import net.tompy.sw.player.Player7Wonders;



public class StrategyRandomImpl implements Strategy 
{
	private static Logger log = LogManager.getLogger( "net.tompy.sw.strategy.StrategyRandomImpl" );


	@Override
	public PlayGroupData decideCardToPlay( List< Card7Wonders > hand, Player7Wonders player ) 
	{
		// Compute availabe resources
		List< ResourceGroupData > available = player.computeAvailableResources();
		log.debug( CommonConstants.LINE_END + "Available" );
		for ( ResourceGroupData rg : available )
		{
			log.debug( rg.display() + CommonConstants.LINE_END + "###" );
		}
		
		// Compute tradeable resources
		List< ResourceGroupData > tradeableRight = player.getRight().computeTradeableResources();
		log.debug( CommonConstants.LINE_END + "Commerce Right" );
		for ( ResourceGroupData rg : tradeableRight )
		{
			log.debug( rg.display() + CommonConstants.LINE_END + "###" );
		}
		
		List< ResourceGroupData > tradeableLeft = player.getLeft().computeTradeableResources();
		log.debug( CommonConstants.LINE_END + "Commerce Left" );
		for ( ResourceGroupData rg : tradeableLeft)
		{
			log.debug( rg.display() + CommonConstants.LINE_END + "###" );
		}
		
		// Determine which cards can be played
		List< PlayGroupData > cardsToPlay = cardsCanPlay( copyHand( player, hand ), player, available, tradeableRight, tradeableLeft );
		
		// Choose card to play
		return chooseCard( player, cardsToPlay, hand );
	}
	
	protected List< Card7Wonders > copyHand( Player7Wonders player, List< Card7Wonders > original )
	{		
		List< Card7Wonders > copyOfHand = new ArrayList< Card7Wonders >();
		copyOfHand.addAll( original );
		if ( null != player.getNextWonder() )
		{
			copyOfHand.add( player.getNextWonder() );
		}
		
		return copyOfHand;
	}
	
	protected List< PlayGroupData > cardsCanPlay( 
			List< Card7Wonders > copyOfHand, 
			Player7Wonders player, 
			List< ResourceGroupData > available,
			List< ResourceGroupData > tradeableRight,
			List< ResourceGroupData > tradeableLeft )
	{
		// Extended data for cards that are playable
		List< PlayGroupData > cardsToPlay = new ArrayList< PlayGroupData >();

		log.debug( CommonConstants.LINE_END + "Cards in hand" );
		for ( Card7Wonders handCard : copyOfHand )
		{
			PlayGroupData cpd = calculate( handCard, player, available, tradeableRight, tradeableLeft );
			if ( null != cpd && player.coins() >= ( handCard.getCostCoin() + cpd.getCostLeft() + cpd.getCostRight() ) )
			{
				log.debug( handCard.getId() + ": " + handCard.getName() + ": PLAYABLE" );
				cardsToPlay.add( cpd );
			}
			else
			{
				log.debug( handCard.getId() + ": " + handCard.getName() );
			}
		}
		
		return cardsToPlay;
	}
	
	protected PlayGroupData chooseCard( Player7Wonders player, List< PlayGroupData > cardsToPlay, List< Card7Wonders > hand )
	{
		
		PlayGroupData toPlay = new PlayGroupData();
		if ( ! cardsToPlay.isEmpty() )
		{
			cardsToPlay = GameUtil.shuffle( cardsToPlay );
			toPlay = buildPlayGroupData( player, cardsToPlay, hand );
			toPlay.setType( PlayType.CARD );
			if ( toPlay.getCard().getColor() == ColorType.WONDER )
			{
				toPlay.setType( PlayType.WONDER );
				toPlay.setCardForWonder( cardsToPlay.get( 0 ).getCard() );
			}
		}
		else
		{
			if ( hand.isEmpty() )
			{
				log.error( "EMPTY  HAND - [" + player.getName() + "]" );
			}
			toPlay.setCard( hand.get( 0 ) );
			toPlay.setType( PlayType.COINS );
		}

		return toPlay;
	}
	
	protected PlayGroupData buildPlayGroupData( Player7Wonders player, List< PlayGroupData > cardsToPlay, List< Card7Wonders > hand )
	{
		return cardsToPlay.get( 0 );
	}
	
	private PlayGroupData calculate( Card7Wonders handCard, 
			Player7Wonders player, 
			List< ResourceGroupData > available,
			List< ResourceGroupData > tradeableRight,
			List< ResourceGroupData > tradeableLeft )
	{
		// Determine For Free
		PlayGroupData cpdForFree = isCardForFree( handCard, player );
		if ( null != cpdForFree )
		{
			//cardsToPlay.add( cpdForFree );
			return cpdForFree;
		}
		
		// Determine "fully paid with available resources"
		PlayGroupData cpdResource = isFullyPaid( handCard, available );
		if ( null != cpdResource )
		{
			if ( player.coins() >= cpdResource.getCostBank() )
			{
				//cardsToPlay.add( cpdResource );
				return cpdResource;
			}
		}
		else
		{
			PlayGroupData cpdTrade = isPayWithTrade( player, handCard, available, tradeableRight, tradeableLeft  );
			if ( null != cpdTrade )
			{
				return cpdTrade;
			}
		}

		return null;
	}
	
	private PlayGroupData isCardForFree( Card7Wonders handCard, Player7Wonders player )
	{
		PlayGroupData returnValue = null;
		
		for ( Card7Wonders playCard : player.getInPlay() )
		{
			if ( null != handCard.getForFree() && handCard.getForFree().equals( playCard.getName() ) )
			{
				returnValue = new PlayGroupData( handCard, 0, 0, 0 );
				break;
			}
		}

		return returnValue;
	}
	
	private PlayGroupData isFullyPaid( Card7Wonders handCard, List< ResourceGroupData > available )
	{
		PlayGroupData returnValue = null;
		
		for ( ResourceGroupData rg : available )
		{
			if ( rg.compareTo( handCard.getCost() ) >= 0 )
			{
				if ( null == returnValue )
				{
					returnValue = new PlayGroupData( handCard, handCard.getCostCoin(), 0, 0 );
					break;
				}
			}
		}
				
		return returnValue;
	}

	private PlayGroupData isPayWithTrade( 
			Player7Wonders player,
			Card7Wonders card, 
			List< ResourceGroupData > available, 
			List< ResourceGroupData > tradeableRight,
			List< ResourceGroupData > tradeableLeft )
	{
		PlayGroupData returnValue = null;
		
		List< CardPayResourceData > cprdList = new ArrayList< CardPayResourceData >();

		for ( ResourceGroupData rga : available )
		{
			ResourceGroupData rgDiff = new ResourceGroupData( card.getCost() );
			rgDiff.subtract( rga );
			ResourceGroupData rgLeft = new ResourceGroupData();
			for ( ResourceGroupData rgtl : tradeableLeft )
			{
				rgLeft.add( rgtl );
			}
			ResourceGroupData rgRight = new ResourceGroupData();
			for ( ResourceGroupData rgtr : tradeableRight )
			{
				rgRight.add( rgtr );
			}
			cprdList.add( new CardPayResourceData( rgDiff, rgRight, rgLeft ) );
		}
		
		// This list will hold all legitimate plays of the card.
		// Then the final act will be to choose the best from this list.
		List< PlayGroupData > cpdList = new ArrayList< PlayGroupData >();
		for ( CardPayResourceData cprd : cprdList )
		{
			for ( ResourceType rt : ResourceType.values() )
			{
				if ( cprd.getAvailable().get( rt ) > 0 
						&& cprd.getAvailable().get( rt ) <= cprd.getTradeableLeft().get( rt ) + cprd.getTradeableRight().get( rt ) )
				{
					if ( player.getTradeCostLeft( rt ) < player.getTradeCostRight( rt ) )
					{
						cpdList.add( tradeLeftThenRight( card, cprd, rt, player.getTradeCostLeft( rt ), player.getTradeCostRight( rt ) ) );
					}
					else
					{
						cpdList.add( tradeRightThenLeft( card, cprd, rt, player.getTradeCostLeft( rt ), player.getTradeCostRight( rt ) ) );
					}
				}
			}
		}
		
		// Choose the best cpd from the cpdList and assign to returnValue
		if ( ! cpdList.isEmpty() )
		{
			cpdList = GameUtil.shuffle( cpdList );
			returnValue = cpdList.get( 0 );
		}
				
		return returnValue;
	}
	
	private PlayGroupData tradeLeftThenRight( Card7Wonders card, CardPayResourceData cprd, ResourceType rt, int costLeft, int costRight )
	{
		PlayGroupData cpd = null;
		
		if ( cprd.getAvailable().get( rt ) <= cprd.getTradeableLeft().get( rt ) )
		{
			cpd = new PlayGroupData( card, 
					card.getCostCoin(), 
					0,
					cprd.getAvailable().get( rt ) * costLeft );
		}
		else
		{
			cpd = new PlayGroupData( 
					card, 
					card.getCostCoin(),
					( cprd.getAvailable().get( rt ) - cprd.getTradeableLeft().get( rt ) ) * costRight,
					cprd.getTradeableLeft().get( rt ) * costLeft );
		}
		
		return cpd;
	}
	
	private PlayGroupData tradeRightThenLeft( Card7Wonders card, CardPayResourceData cprd, ResourceType rt, int costLeft, int costRight )
	{
		PlayGroupData cpd = null;
		
		if ( cprd.getAvailable().get( rt ) <= cprd.getTradeableRight().get( rt ) )
		{
			cpd = new PlayGroupData( card, 
					card.getCostCoin(), 
					cprd.getAvailable().get( rt ) * costRight, 
					0 );
		}
		else
		{
			cpd = new PlayGroupData( 
					card, 
					card.getCostCoin(), 
					cprd.getTradeableRight().get( rt ) * costRight, 
					( cprd.getAvailable().get( rt ) - cprd.getTradeableRight().get( rt ) ) * costLeft);
		}
		
		return cpd;
	}

}
