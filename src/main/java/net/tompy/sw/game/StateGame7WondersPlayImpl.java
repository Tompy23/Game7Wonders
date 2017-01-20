package net.tompy.sw.game;

import net.tompy.gameai.fsm.State;

public class StateGame7WondersPlayImpl implements State<Game7Wonders> 
{
	//private static Logger log = Logger.getLogger( "net.tompy.sw.game.StateGame7WondersPlayImpl" );
	private GamePlayController gameControl;
	
	@Override
	public void execute(Game7Wonders me) 
	{
		// Play all rounds
		for ( int i = me.getRound(); i <= me.getRounds(); i++ )
		{
			me.setRound( i );
			gameControl.playRound( me );
		}
		
		me.changeState( Constants.GAME_SCORE );
	}

	
	

	
	
	@Override
	public void enter(Game7Wonders me) {
		// TODO Auto-generated method stub

	}

	@Override
	public void exit(Game7Wonders me) {
		// TODO Auto-generated method stub

	}


	public void setGameControl(GamePlayController gameControl) {
		this.gameControl = gameControl;
	}
}
