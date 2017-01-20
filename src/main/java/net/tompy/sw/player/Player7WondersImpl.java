package net.tompy.sw.player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.tompy.common.CommonConstants;
import net.tompy.gameai.PlayerAbstractImpl;
import net.tompy.gameai.fsm.FSMCreator;
import net.tompy.gameai.fsm.FiniteStateMachine;
import net.tompy.gameai.fsm.State;
import net.tompy.sw.component.Card7Wonders;
import net.tompy.sw.component.Wonder;
import net.tompy.sw.component.WonderBasicImpl;
import net.tompy.sw.data.ColorType;
import net.tompy.sw.data.PlayGroupData;
import net.tompy.sw.data.ResourceGroupData;
import net.tompy.sw.data.ResourceType;
import net.tompy.sw.data.ScienceGroupData;
import net.tompy.sw.data.ScienceType;
import net.tompy.sw.data.ScoreData;
import net.tompy.sw.game.Constants;
import net.tompy.sw.game.Game7Wonders;
import net.tompy.sw.strategy.Strategy;

public class Player7WondersImpl extends PlayerAbstractImpl implements Player7Wonders
{
	//private Log log = MyLog.getLog();

	protected Player7Wonders left;
	protected Player7Wonders right;
	
	private ScoreData score = new ScoreData();

	private int coins = 0;
	
	private List< Card7Wonders > hand = new ArrayList< Card7Wonders >();
	private List< Card7Wonders > nextHand = new ArrayList< Card7Wonders >();
	private PlayGroupData cardToPlay;
	private boolean canPlayLastCard = false;
	private boolean playFreeCardPerAge = false;
	private boolean copyGuildFromNeighbor = false;
	
	private List< Card7Wonders > inPlay = new ArrayList< Card7Wonders >();
	
	private List< Integer > militaryPoints = new ArrayList< Integer >();
	
	private Wonder wonder;
	
	private ResourceGroupData tradeCostRight = new ResourceGroupData();
	private ResourceGroupData tradeCostLeft = new ResourceGroupData();
	
	private Strategy strategy;
	
	private List< ScienceGroupData > scienceList = new ArrayList< ScienceGroupData >();
	
	private int round;
	private Game7Wonders game;
	
	private FiniteStateMachine< Player7Wonders > fsm = null;
	private FSMCreator< Player7Wonders > fsmCreator = null;
	
	@SuppressWarnings("unchecked")
	@Override
	public void update() 
	{
		if ( fsm.isInState( null ) )
		{
			fsm.changeState( (State< Player7Wonders >)stateFactory.createState( "playerTakeTurn" ) );
		}
		fsm.update();
	}
	
	@SuppressWarnings("unchecked")
	@Override 
	public void changeState( String newState )
	{
		fsm.changeState( (State< Player7Wonders >)stateFactory.createState( newState ) );
	}
	
	public Player7WondersImpl( Player7Wonders p )
	{
		id = p.getId();
		name = p.getName();
		stateFactory = p.getStateFactory();
		coins = p.getCoins();
		//hand
		for ( Card7Wonders c : p.getHand() )
		{
			hand.add( c );
		}
		canPlayLastCard = p.isCanPlayLastCard();
		playFreeCardPerAge = p.isPlayFreeCardPerAge();
		copyGuildFromNeighbor = p.isCopyGuildFromNeighbor();
		//inPlay
		for ( Card7Wonders c : p.getInPlay() )
		{
			inPlay.add( c );
		}
		for ( Integer i : p.getMilitaryPoints() )
		{
			militaryPoints.add( i );
		}
		wonder = new WonderBasicImpl( p.getWonder() );
	}
	
	
	@Override
	public String display()
	{
		String out = "";
		
		out += "Player: " + name + "  [Left: " + left.getName() + ", Right: " + right.getName() + "]" + CommonConstants.LINE_END;
		out += CommonConstants.LINE_END;
		out += "Wonder" + CommonConstants.LINE_END + wonder.display();
		out += CommonConstants.LINE_END;
		out += "Wonders Played" + CommonConstants.LINE_END;
		out += CommonConstants.LINE_END;
		out += displayCardsInPlay( ColorType.WONDER);
		out += CommonConstants.LINE_END + CommonConstants.LINE_END;
		out += "Military: ";
		for ( Integer i : militaryPoints )
		{
			out += " [" + i + "] ";
		}
		out += CommonConstants.LINE_END;
		out += "Resources: " + CommonConstants.LINE_END;
		for ( ResourceGroupData rgd : this.computeAvailableResources() )
		{
			out += rgd.display() + CommonConstants.LINE_END;
		}
		out += CommonConstants.LINE_END + CommonConstants.LINE_END;
		out += "Played Cards" + CommonConstants.LINE_END;
		out += CommonConstants.LINE_END;
		out += "Resource Cards" + CommonConstants.LINE_END;
		out += CommonConstants.LINE_END;
		out += displayCardsInPlay( ColorType.BROWN );
		out += displayCardsInPlay( ColorType.GREY );
		out += CommonConstants.LINE_END + CommonConstants.LINE_END;
		out += "Market Cards" + CommonConstants.LINE_END;
		out += CommonConstants.LINE_END;
		out += displayCardsInPlay( ColorType.YELLOW );
		out += CommonConstants.LINE_END + CommonConstants.LINE_END;
		out += "Science Cards" + CommonConstants.LINE_END;
		out += CommonConstants.LINE_END;
		out += displayCardsInPlay( ColorType.GREEN );
		out += CommonConstants.LINE_END + CommonConstants.LINE_END;
		out += "Military Cards" + CommonConstants.LINE_END;
		out += CommonConstants.LINE_END;
		out += displayCardsInPlay( ColorType.RED );
		out += CommonConstants.LINE_END + CommonConstants.LINE_END;
		out += "Victory Point Cards" + CommonConstants.LINE_END;
		out += CommonConstants.LINE_END;
		out += displayCardsInPlay( ColorType.BLUE );
		out += CommonConstants.LINE_END + CommonConstants.LINE_END;
		out += "Guild Cards" + CommonConstants.LINE_END;
		out += CommonConstants.LINE_END;
		out += displayCardsInPlay( ColorType.PURPLE );
		
		return out;
		
	}
	
	private String displayCardsInPlay( ColorType color )
	{
		String out = "";
		
		for ( Card7Wonders card : inPlay )
		{
			if ( card.getColor() == color )
			{
				out += card.display();
				out += CommonConstants.LINE_END;
			}
		}
		
		if ( "".equals( out ) )
		{
			out = "No Cards" + CommonConstants.LINE_END;
		}
		
		return out;
	}
	
	public Player7WondersImpl()
	{
		for ( ResourceType rt : ResourceType.values() )
		{
			tradeCostRight.set( rt, 2 );
		}
		for ( ResourceType rt : ResourceType.values() )
		{
			tradeCostLeft.set( rt, 2 );
		}
	}

	public void init()
	{
		// Set up a Finite State Machine "setup factory" class and call it from here.
		fsm = fsmCreator.create( this );
	}	

	@Override
	public int compareTo(Player7Wonders o) 
	{
		int returnValue = 0;
		
		if ( o.getScore().getTotal() > this.getScore().getTotal() )
		{
			returnValue = 1;
		}
		else
		{
			returnValue = -1;
		}
		
		return returnValue;
	}

	@Override
	public void reduceTradeCostRight(ResourceType rt, int newCost) 
	{
		tradeCostRight.set( rt, newCost );
	}

	@Override
	public void reduceTradeCostLeft(ResourceType rt, int newCost) 
	{
		tradeCostLeft.set( rt, newCost );
	}
	
	@Override
	public List< ResourceGroupData > computeAvailableResources() 
	{
		List< ResourceGroupData > resourceList = new ArrayList< ResourceGroupData >();
		resourceList.add( new ResourceGroupData( wonder.getResourceGroup() ) );
		for ( Card7Wonders card : inPlay )
		{
			resourceList = card.aggregateAvailableResources( resourceList );
		}
		return resourceList;
	}

	@Override
	public List< ResourceGroupData > computeTradeableResources() 
	{
		List< ResourceGroupData > resourceList = new ArrayList< ResourceGroupData >();
		resourceList.add( new ResourceGroupData( wonder.getResourceGroup() ) );
		for ( Card7Wonders card : inPlay )
		{
			resourceList = card.aggregateTradeableResources( resourceList );
		}
		return resourceList;
	}	

	@Override
	public void computeScore()
	{
		if ( copyGuildFromNeighbor )
		{
			findGuildToCopy();
		}

		doScoring();
	}
	
	private void findGuildToCopy()
	{
		int bestScore = 0;
		Card7Wonders copiedGuild = null;
		
		for ( Card7Wonders c : getLeft().getInPlay() )
		{
			if ( c.getColor() == ColorType.PURPLE )
			{
				doScoring();
				if ( this.score.getTotal() > bestScore )
				{
					bestScore = score.getTotal();
					copiedGuild = c;
				}
			}
		}
		for ( Card7Wonders c : getRight().getInPlay() )
		{
			if ( c.getColor() == ColorType.PURPLE )
			{
				doScoring();
				if ( this.score.getTotal() > bestScore )
				{
					bestScore = score.getTotal();
					copiedGuild = c;
				}
			}
		}
		
		if ( null != copiedGuild )
		{
			inPlay.add( copiedGuild );
		}
	}
	
	
	
	private void doScoring()
	{
		score.reset();
		for ( Card7Wonders card : inPlay )
		{
			card.assignPoints( this );
		}
		for ( int i : this.militaryPoints )
		{
			score.addPoints( i, ColorType.RED );
		}
		score.addCoinPoints( coins / 3 );
		
		
		scoreScience();
	}
	

	private void scoreScience()
	{
		scienceList.add( new ScienceGroupData() );
		
		for ( Card7Wonders card : inPlay )
		{
			card.scoreScience( this, scienceList );
		}
		
		// 
		for ( ScienceGroupData sgd : scienceList )
		{
			// Calculate Score and set in sgd
			sgd.setScore( calculateScience( sgd ) );
		}
		
		// Sort the list
		Collections.sort( scienceList );
		
		score.addPoints( scienceList.get( 0 ).getScore(), ColorType.GREEN );
	}
	
	private int calculateScience( ScienceGroupData sgd )
	{
		int returnValue = 0;
		int combos = 99;
		for ( ScienceType st : ScienceType.values() )
		{
			if ( sgd.get( st ) < combos )
			{
				combos = sgd.get( st );
			}
			
			returnValue += ( sgd.get( st ) ^ 2 );
		}
		
		returnValue += ( 7 * combos );
		
		return returnValue;	
	}

	@Override
	public int calculateMilitaryStrength() 
	{
		int returnValue = 0;
		
		for ( Card7Wonders card : inPlay )
		{
			returnValue += card.getMilitaryValue();
		}
		
		return returnValue;
	}	

	@Override
	public void playExtraHand(List<Card7Wonders> extraHand) 
	{
		if ( ! extraHand.isEmpty() )
		{
			List< Card7Wonders > originalHand = new ArrayList< Card7Wonders >();
			originalHand.addAll( hand );
			
			// put in new cards and take a turn
			hand = extraHand;

			setRound( 0 );
			changeState( Constants.PLAYER_TAKE_TURN );
			update();
			changeState( Constants.PLAYER_PLAY_CARD );
			update();
			changeState( Constants.PLAYER_CARD_ACTION );
			update();
			
			// Put original hand back
			hand = originalHand;
		}
	}
	
	@Override
	public void pickUpCards() 
	{
		hand = nextHand;
	}

	@Override
	public void SetNextHand(List<Card7Wonders> cards) 
	{
		nextHand = cards;
	}

	@Override
	public void addCoins(int i) 
	{
		coins += i;
	}

	@Override
	public int coins() 
	{
		return coins;
	}

	@Override
	public void dealCard( Card7Wonders card ) 
	{
		hand.add( card );
	}

	@Override
	public void addMilitaryPoints(int points) 
	{
		militaryPoints.add( points );
	}

	@Override
	public void setWonder(Wonder wonder) {
		this.wonder = wonder;
	}

	public void setStrategy(Strategy strategy) {
		this.strategy = strategy;
	}

	public Player7Wonders getLeft() {
		return left;
	}

	public void setLeft(Player7Wonders left) {
		this.left = left;
	}

	public Player7Wonders getRight() {
		return right;
	}

	public void setRight(Player7Wonders right) {
		this.right = right;
	}

	public ScoreData getScore() {
		return score;
	}

	public void setScore(ScoreData score) {
		this.score = score;
	}

	public List<Card7Wonders> getInPlay() {
		return inPlay;
	}

	@Override
	public Wonder getWonder() {
		return wonder;
	}

	@Override
	public Card7Wonders getNextWonder() 
	{
		return wonder.seeNextWonder();
	}

	@Override
	public int getTradeCostRight(ResourceType rt) 
	{
		return tradeCostRight.get( rt );
	}

	@Override
	public int getTradeCostLeft(ResourceType rt) 
	{
		return tradeCostLeft.get( rt );
	}

	@Override
	public void emptyHand() 
	{
		hand.removeAll( hand );
	}

	@Override
	public List<Integer> getMilitaryAwards() 
	{
		return militaryPoints;
	}

	public List<ScienceGroupData> getScienceList() {
		return scienceList;
	}

	public void setScienceList(List<ScienceGroupData> scienceList) {
		this.scienceList = scienceList;
	}

	public void setCanPlayLastCard(boolean canPlayLastCard) {
		this.canPlayLastCard = canPlayLastCard;
	}

	public void setPlayFreeCardPerAge(boolean playFreeCardPerAge) {
		this.playFreeCardPerAge = playFreeCardPerAge;
	}

	public void setCopyGuildFromNeighbor(boolean copyGuildFromNeighbor) {
		this.copyGuildFromNeighbor = copyGuildFromNeighbor;
	}

	public boolean isPlayFreeCardPerAge() {
		return playFreeCardPerAge;
	}

	@Override
	public PlayGroupData getCardToPlay() {
		return cardToPlay;
	}

	public List<Card7Wonders> getHand() {
		return hand;
	}

	public Strategy getStrategy() {
		return strategy;
	}

	public void setCardToPlay(PlayGroupData cardToPlay) {
		this.cardToPlay = cardToPlay;
	}

	public void setHand(List<Card7Wonders> hand) {
		this.hand = hand;
	}

	public List<Card7Wonders> getNextHand() {
		return nextHand;
	}

	public boolean isCanPlayLastCard() {
		return canPlayLastCard;
	}

	@Override
	public void setRound(int r) 
	{
		round = r;
	}

	@Override
	public int getRound() 
	{
		return round;
	}

	public void setFsmCreator(FSMCreator<Player7Wonders> fsmCreator) {
		this.fsmCreator = fsmCreator;
	}

	public int getCoins() {
		return coins;
	}

	public boolean isCopyGuildFromNeighbor() {
		return copyGuildFromNeighbor;
	}

	public List<Integer> getMilitaryPoints() {
		return militaryPoints;
	}

	@Override
	public void setGame(Game7Wonders game) 
	{
		this.game = game;
	}

	public Game7Wonders getGame() {
		return game;
	}

	public FSMCreator<Player7Wonders> getFsmCreator() {
		return fsmCreator;
	}

}
