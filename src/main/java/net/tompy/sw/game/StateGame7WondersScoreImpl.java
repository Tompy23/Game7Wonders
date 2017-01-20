package net.tompy.sw.game;

import java.util.Collections;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.tompy.common.CommonConstants;
import net.tompy.gameai.fsm.State;
import net.tompy.sw.player.Player7Wonders;



public class StateGame7WondersScoreImpl implements State<Game7Wonders> 
{
	private static Logger log = LogManager.getLogger( "net.tompy.sw.game.StateGame7WondersScoreImpl" );

	@Override
	public void execute(Game7Wonders me) 
	{
		// Total score for all players
		for ( Player7Wonders p : me.getPlayers() )
		{
			if ( me.isReal() )
			{
				log.info( p.display() );
				log.info( CommonConstants.LINE_END + CommonConstants.LINE_END );
			}
			else
			{
				log.debug( p.display() );
				log.debug( CommonConstants.LINE_END + CommonConstants.LINE_END );
			}
		}
		for ( Player7Wonders p : me.getPlayers() )
		{
			p.computeScore();
		}
		
		// Place players in sortable list
		Collections.sort( me.getPlayers() );
		
		// Display players and score in order
		log.debug( CommonConstants.LINE_END + "GAME END" + CommonConstants.LINE_END + "Scores..." );
		for ( Player7Wonders p : me.getPlayers() )
		{
			if ( me.isReal() )
			{
				log.info( p.getName() + " [" + p.getWonder().getName() +"]" );
				log.info( p.getScore().display() + CommonConstants.LINE_END );
				log.info( CommonConstants.LINE_END + CommonConstants.LINE_END );
			}
			else
			{
				log.debug( p.getName() + " [" + p.getWonder().getName() +"]" );
				log.debug( p.getScore().display() + CommonConstants.LINE_END );
				log.debug( CommonConstants.LINE_END + CommonConstants.LINE_END );
			}
		}
		
		me.setFinished( true );
	}

	@Override
	public void enter(Game7Wonders me) {
		// TODO Auto-generated method stub

	}

	@Override
	public void exit(Game7Wonders me) {
		// TODO Auto-generated method stub

	}

}
