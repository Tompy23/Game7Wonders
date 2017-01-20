package net.tompy.sw.function;

import java.util.ArrayList;
import java.util.List;

import net.tompy.sw.component.Card7Wonders;
import net.tompy.sw.data.ScienceGroupData;
import net.tompy.sw.data.ScienceType;
import net.tompy.sw.player.Player7Wonders;

public class CardFunctorScienceOptionImpl implements CardFunctor {

	@Override
	public String display() 
	{
		return "Add either Compass/Gear/Tablet to science";
	}

	@Override
	public void functor(Player7Wonders player, Card7Wonders card) 
	{
		List< ScienceGroupData > newList = new ArrayList< ScienceGroupData >();
		for ( ScienceGroupData sgd : player.getScienceList() )
		{
			for ( ScienceType st : ScienceType.values() )
			{	
				ScienceGroupData newSgd = new ScienceGroupData( sgd );
				newSgd.increment( st );
				newList.add( new ScienceGroupData( newSgd ) );
			}
		}

		player.setScienceList( newList );
	}

}
