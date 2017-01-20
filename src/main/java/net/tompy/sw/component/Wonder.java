package net.tompy.sw.component;

import java.util.Stack;

import net.tompy.gameai.Component;
import net.tompy.sw.data.ResourceGroupData;

public interface Wonder extends Component
{
	public ResourceGroupData getResourceGroup();
	public Card7Wonders playNextWonder();
	public Card7Wonders seeNextWonder();
	public Stack< Card7Wonders > getAdvances();
}
