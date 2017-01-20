package net.tompy.sw.function;

import java.util.List;
import java.util.Random;

import net.tompy.sw.component.Wonder;
import net.tompy.sw.player.Player7Wonders;

public class WonderAssignmentRandomImpl implements WonderAssignment 
{
	public void assignWonders( List< Player7Wonders > players, List< Wonder > wonders )
	{
		Random rand = new Random( System.currentTimeMillis() );
		int wonderCount = wonders.size();

		boolean[] wonderUsed = new boolean[ wonderCount ];
		for ( int i = 0; i < wonderCount; i++ )
		{
			wonderUsed[ i ] = false;
		}
		for ( Player7Wonders player : players )
		{
			boolean done = false;
			while ( ! done )
			{
				int i = rand.nextInt( wonderCount );
				if ( ! wonderUsed[ i ] )
				{
					player.setWonder( wonders.get( i ) );
					done = true;
					wonderUsed[ i ] = true;
				}
			}
		}
	}
}
