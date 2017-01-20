package net.tompy.sw.game;

import java.sql.Timestamp;
import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.tompy.gameai.fsm.State;
import net.tompy.gameai.fsm.StateAbstractImpl;
import net.tompy.sw.function.WonderAssignment;
import net.tompy.sw.player.Player7Wonders;


public class StateGame7WondersInitializeImpl extends StateAbstractImpl<Game7Wonders> implements State<Game7Wonders> 
{
	private static Logger log = LogManager.getLogger("net.tompy.sw.game.StateGame7WondersInitializeImpl" );

	private WonderAssignment wonderAssignment;
	

	@Override
	public void execute(Game7Wonders me) 
	{
		log.debug( "Initializing: " + new Timestamp( new Date().getTime() ) );

		// Initialize players
		for ( Player7Wonders p : me.getPlayers() )
		{
			p.addCoins( me.getStartCoins() );
		}
		me.randomizePlayerOrder();
		me.setPlayerRelatives();
		
		// Assign Wonders
		wonderAssignment.assignWonders( me.getPlayers(), me.getWonders() );

		// Initialize cards
		me.buildDecks();
		
		me.changeState( Constants.GAME_PLAY );
	}

	public void setWonderAssignment(WonderAssignment wonderAssignment) {
		this.wonderAssignment = wonderAssignment;
	}

}
