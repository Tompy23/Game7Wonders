package net.tompy.sw.player;

import java.util.List;

import net.tompy.gameai.Player;
import net.tompy.gameai.fsm.FSMCreator;
import net.tompy.sw.component.Card7Wonders;
import net.tompy.sw.component.Wonder;
import net.tompy.sw.data.PlayGroupData;
import net.tompy.sw.data.ResourceGroupData;
import net.tompy.sw.data.ResourceType;
import net.tompy.sw.data.ScienceGroupData;
import net.tompy.sw.data.ScoreData;
import net.tompy.sw.game.Game7Wonders;
import net.tompy.sw.strategy.Strategy;

public interface Player7Wonders extends Player, Comparable< Player7Wonders >
{
	public void dealCard( Card7Wonders card );
	public void emptyHand();
	
	public Strategy getStrategy();
	public List< Card7Wonders > getHand();
	public void setHand( List< Card7Wonders > h );
	public PlayGroupData getCardToPlay();
	public void setCardToPlay( PlayGroupData pgd );
	
	//public void playLastCard( Game7Wonders game );
	public void setCanPlayLastCard( boolean b );
	public boolean isCanPlayLastCard();
	public void playExtraHand( List< Card7Wonders > extraHand );
	public void setPlayFreeCardPerAge( boolean b );
	public boolean isPlayFreeCardPerAge();
	public void setCopyGuildFromNeighbor( boolean b );
	
	public void addCoins( int i );
	public int coins();
	
	public int getTradeCostRight( ResourceType rt );
	public int getTradeCostLeft( ResourceType rt );
	
	public void computeScore();
	public ScoreData getScore();
	public List< Card7Wonders > getInPlay();
	public List<ScienceGroupData> getScienceList();
	public void setScienceList( List<ScienceGroupData> newList );

	
	public void setWonder( Wonder w );
	public Wonder getWonder();
	public Card7Wonders getNextWonder();
	
	public void reduceTradeCostRight( ResourceType rt, int newCost );
	public void reduceTradeCostLeft( ResourceType rt, int newCost );
	
	public void setRight( Player7Wonders p );
	public void setLeft( Player7Wonders p );
	public Player7Wonders getRight();
	public Player7Wonders getLeft();
	
	public void addMilitaryPoints( int points );
	public int calculateMilitaryStrength();
	public List< Integer > getMilitaryAwards();
	
	public List< ResourceGroupData > computeAvailableResources();
	public List< ResourceGroupData > computeTradeableResources();
	
	public void SetNextHand( List< Card7Wonders > cards );
	public List< Card7Wonders > getNextHand();
	public void pickUpCards();
	
	public void setRound( int round );
	public int getRound();
	
	public void setGame( Game7Wonders game );
	public Game7Wonders getGame();
	
	public void setStrategy( Strategy s );
	
	// Copy
	public int getCoins();
	public boolean isCopyGuildFromNeighbor();
	public List< Integer > getMilitaryPoints();
	public FSMCreator<Player7Wonders> getFsmCreator();
}
