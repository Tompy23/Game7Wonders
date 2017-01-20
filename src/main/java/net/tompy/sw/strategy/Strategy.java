package net.tompy.sw.strategy;

import java.util.List;

import net.tompy.sw.component.Card7Wonders;
import net.tompy.sw.data.PlayGroupData;
import net.tompy.sw.player.Player7Wonders;

public interface Strategy 
{
	public PlayGroupData decideCardToPlay( List< Card7Wonders > hand, Player7Wonders player );
	//public PlayGroupData buildPlayGroupData( Player7Wonders player, List< PlayGroupData > cardsToPlay );
}
