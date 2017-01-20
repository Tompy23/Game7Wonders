package net.tompy.sw.game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import net.tompy.gameai.GameAbstractImpl;
import net.tompy.gameai.deck.Deck;
import net.tompy.gameai.fsm.FSMCreator;
import net.tompy.gameai.fsm.FiniteStateMachine;
import net.tompy.gameai.fsm.State;
import net.tompy.gameai.util.GameUtil;
import net.tompy.sw.component.Card7Wonders;
import net.tompy.sw.component.Deck7WondersImpl;
import net.tompy.sw.component.Wonder;
import net.tompy.sw.player.Player7Wonders;

public class Game7WondersImpl extends GameAbstractImpl implements Game7Wonders 
{
	//private Log log = MyLog.getLog();
	private boolean real;

	private List< Player7Wonders > players;
	private List< Player7Wonders > playerOrder;
	
	private List< Wonder > wonders;
	
	private List< Card7Wonders > guilds;
	
	private List< Card7Wonders > allCards;
	private Map< Integer, Deck< Card7Wonders > > theDecks = new HashMap< Integer, Deck< Card7Wonders > >();
	private List< Card7Wonders > discards = new ArrayList< Card7Wonders >();
	
	// Simple
	private int turn = 1;
	private int round = 1;
	
	// Configurable
	private int ages = 3;
	private int guildAge = 2;
	private int startCoins = 3;
	private int rounds = 3;
	private int turns = 6;
	private int cardsDealt = 7;
	
	// Game flow state machine
	private FiniteStateMachine< Game7Wonders > fsm = null;
	private FSMCreator< Game7Wonders > fsmCreator = null;
	
	@SuppressWarnings("unchecked")
	@Override
	public void update() 
	{
		if ( fsm.isInState( null ) )
		{
			fsm.changeState( (State< Game7Wonders >)stateFactory.createState( Constants.GAME_INIT ) );
		}
		fsm.update();
	}
	
	@SuppressWarnings("unchecked")
	@Override 
	public void changeState( String newState )
	{
		fsm.changeState( (State< Game7Wonders >)stateFactory.createState( newState ) );
	}
	
	@Override
	public String showPlayers( String name )
	{
		String returnValue = "";
		for ( Player7Wonders player : playerOrder )
		{
			if ( "ALL".equalsIgnoreCase( name ) || player.getName().equalsIgnoreCase( name ) )
			{
				returnValue += player.display();
			}
		}
		return returnValue;
	}
	public Game7WondersImpl()
	{
	}
	
	public Game7WondersImpl( Game7Wonders copy )
	{
		// Simple references
		ages = copy.getAges();
		guildAge = copy.getGuildAge();
		startCoins = copy.getStartCoins();
		rounds = copy.getRounds();
		turns = copy.getTurns();
		cardsDealt = copy.getCardsDealt();
		turn = copy.getTurn();
		round = copy.getRound();
		stateFactory = copy.getStateFactory();
		
		// Players
		players = new ArrayList< Player7Wonders >();
		playerOrder = new ArrayList< Player7Wonders >();
		
		for ( Player7Wonders p : copy.getPlayerOrder() )
		{
			players.add( Util7Wonders.copyPlayer( p ) );
		}
		for ( Player7Wonders p : players )
		{
			playerOrder.add( p );
		}
		setPlayerRelatives();
		
		// Cards
		for ( Card7Wonders c : copy.getDiscards() )
		{
			discards.add( c );
		}
		for ( Integer i : copy.getTheDecks().keySet() )
		{
			if ( ! theDecks.containsKey( i ) )
			{
				theDecks.put( i, new Deck7WondersImpl() );
			}
			for ( Card7Wonders c : copy.getTheDecks().get( i ).getAll() )
			{
				theDecks.get( i ).add( c );
			}
		}

	}
	
	public void init()
	{
		fsm = fsmCreator.create( this );
	}
	
	@Override
	public void setPlayerRelatives()
	{
		
		if ( ! playerOrder.isEmpty() )
		{
			Iterator< Player7Wonders > playerIter = playerOrder.iterator();
			Player7Wonders p1 = playerIter.next();
			while ( playerIter.hasNext() )
			{
				Player7Wonders p2 = playerIter.next();
				p1.setRight( p2 );
				p2.setLeft( p1 );
				p1 = p2;
			}
			playerOrder.get( 0 ).setLeft( playerOrder.get( playerOrder.size() - 1 ) );
			playerOrder.get( playerOrder.size() - 1 ).setRight( playerOrder.get( 0 ) );
		}
	}
	
	@Override
	public void randomizePlayerOrder()
	{
		playerOrder = GameUtil.shuffle( players );		
	}

	@Override
	public void buildDecks()
	{
		Random rand = new Random( System.currentTimeMillis() );

		for ( int i = 0; i < ages; i++ )
		{
			theDecks.put( i, new Deck7WondersImpl() );
		}
		
		for ( Card7Wonders card : allCards )
		{
			if ( card.getPlayerCount() <= players.size() )
			{
				theDecks.get( card.getAge() ).add( card );
			}
		}
		
		for ( int i = 0; i < players.size() + 2; i++ )
		{
			int index = rand.nextInt( guilds.size() );
			theDecks.get( guildAge ).add( guilds.get( index ) );
			guilds.remove( index );
		}

		for ( int i = 0; i < 3; i++ )
		{
			theDecks.get( i ).shuffle();
		}
	}

	
	@Override
	public void addToDiscard(Card7Wonders card) 
	{
		discards.add( card );
	}

	@Override
	public List<Card7Wonders> getDiscards() {
		return discards;
	}

	public void setPlayers( List< Player7Wonders > players ) {
		this.players = players;
	}


	public void setGuilds(List<Card7Wonders> guilds) {
		this.guilds = guilds;
	}

	public void setAges(int ages) {
		this.ages = ages;
	}

	public void setGuildAge(int guildAge) {
		this.guildAge = guildAge;
	}

	public void setAllCards(List<Card7Wonders> allCards) {
		this.allCards = allCards;
	}

	public List<Player7Wonders> getPlayers() {
		return players;
	}

	public void setFsm(FiniteStateMachine<Game7Wonders> fsm) {
		this.fsm = fsm;
	}

	public List<Player7Wonders> getPlayerOrder() {
		return playerOrder;
	}

	public Map<Integer, Deck<Card7Wonders>> getTheDecks() {
		return theDecks;
	}

	public List<Wonder> getWonders() {
		return wonders;
	}

	public void setWonders(List<Wonder> wonders) {
		this.wonders = wonders;
	}

	public int getStartCoins() {
		return startCoins;
	}

	public void setStartCoins(int startCoins) {
		this.startCoins = startCoins;
	}

	public int getRounds() {
		return rounds;
	}

	public void setRounds(int rounds) {
		this.rounds = rounds;
	}

	public int getTurns() {
		return turns;
	}

	public void setTurns(int turns) {
		this.turns = turns;
	}

	public int getCardsDealt() {
		return cardsDealt;
	}

	public void setCardsDealt(int cardsDealt) {
		this.cardsDealt = cardsDealt;
	}

	public int getAges() {
		return ages;
	}

	public int getGuildAge() {
		return guildAge;
	}

	public int getTurn() {
		return turn;
	}

	public void setTurn(int turn) {
		this.turn = turn;
	}

	public int getRound() {
		return round;
	}

	public void setRound(int round) {
		this.round = round;
	}

	public FSMCreator<Game7Wonders> getFsmCreator() {
		return fsmCreator;
	}

	public void setFsmCreator(FSMCreator<Game7Wonders> fsmCreator) {
		this.fsmCreator = fsmCreator;
	}

	public boolean isReal() {
		return real;
	}

	public void setReal(boolean real) {
		this.real = real;
	}
}
