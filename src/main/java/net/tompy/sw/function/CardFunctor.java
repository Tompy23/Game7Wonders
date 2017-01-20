package net.tompy.sw.function;

import net.tompy.sw.component.Card7Wonders;
import net.tompy.sw.player.Player7Wonders;

public interface CardFunctor 
{
	public String display();
	public void functor( Player7Wonders player, Card7Wonders card );
}
