package net.tompy.sw.game;

import java.util.List;
import java.util.Map;

import net.tompy.gameai.Game;
import net.tompy.gameai.deck.Deck;
import net.tompy.sw.component.Card7Wonders;
import net.tompy.sw.component.Wonder;
import net.tompy.sw.player.Player7Wonders;

public interface Game7Wonders extends Game 
{
	public void addToDiscard( Card7Wonders card );
	public List< Card7Wonders > getDiscards();
	
	public String showPlayers( String name );
	
	public void setPlayerRelatives();
	public void randomizePlayerOrder();
	public void buildDecks();
	public Map< Integer, Deck< Card7Wonders > > getTheDecks();

	public List< Player7Wonders > getPlayers();
	public List< Player7Wonders > getPlayerOrder();
	
	public void setRound( int round );
	public int getRound();
	public void setTurn( int turn );
	public int getTurn();
	
	public void setFinished( boolean finished );
	
	public List< Wonder > getWonders();
	public int getStartCoins();
	public int getRounds();
	public int getTurns();
	public int getCardsDealt();
	public int getAges();
	public int getGuildAge();
	public boolean isReal();
}
