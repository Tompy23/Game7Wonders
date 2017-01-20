package net.tompy.sw.game;

import net.tompy.common.CommonException;
import net.tompy.sw.component.Card7Wonders;
import net.tompy.sw.data.ColorType;
import net.tompy.sw.player.FSMCreatorPlayerImpl;
import net.tompy.sw.player.Player7Wonders;
import net.tompy.sw.player.Player7WondersImpl;

public class Util7Wonders
{
	public static int countOfCards( Player7Wonders player, ColorType color )
	{
		int cardCount= 0;
		
		for ( Card7Wonders myCard : player.getInPlay() )
		{
			if ( myCard.getColor() == color )
			{
				cardCount++;
			}
		}
		
		return cardCount;
	}
	
	public static Player7WondersImpl copyPlayer( Player7Wonders p )
	{
		Player7WondersImpl newPlayer = new Player7WondersImpl( p );
		newPlayer.setFsmCreator( new FSMCreatorPlayerImpl() );
		
		newPlayer.init();
		
		return newPlayer;
	}
	
	public static Game7WondersImpl copyGame( Game7Wonders g )
	{
		Game7WondersImpl newGame = new Game7WondersImpl( g ); 
		newGame.setFsmCreator( new FSMCreatorGameImpl() );
		
		newGame.init();
		
		return newGame;
	}

	public static void playerFunction( Game7Wonders me, String pState )
	{
		try
		{
			for ( Player7Wonders p : me.getPlayerOrder() )
			{
				p.changeState( pState );
				p.update();
			}
		}
		catch ( CommonException ce )
		{
			ce.printStackTrace();
		}
	}
}
