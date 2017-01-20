package net.tompy.sw.component;

import java.util.Stack;

import net.tompy.common.CommonConstants;
import net.tompy.gameai.ComponentAbstractImpl;
import net.tompy.sw.data.ResourceGroupData;

public class WonderBasicImpl extends ComponentAbstractImpl implements Wonder 
{
	private ResourceGroupData resource;
	
	private Stack< Card7Wonders > advances;
	
	public WonderBasicImpl()
	{	
	}
	
	public WonderBasicImpl( Wonder copy )
	{
		name = copy.getName();
		id = copy.getId();
		resource = copy.getResourceGroup();
		advances = new Stack< Card7Wonders >();
		for ( Card7Wonders c : copy.getAdvances() )
		{
			advances.add( c );
		}
	}
	
	@Override
	public String display()
	{
		String returnValue = "";
		
		returnValue += "Name: " + name + CommonConstants.LINE_END;
		returnValue += "Resource: " + resource.display() + CommonConstants.LINE_END;
		returnValue += CommonConstants.LINE_END;

		returnValue += "Wonders Not Played" + CommonConstants.LINE_END;
		returnValue += CommonConstants.LINE_END;
		for ( Card7Wonders card : advances )
		{
			returnValue += card.display() + CommonConstants.LINE_END;
		}

		return returnValue;
	}

	@Override
	public Card7Wonders playNextWonder() 
	{
		Card7Wonders card = null;
		
		if ( ! advances.empty() )
		{
			card = advances.pop();
		}
		
		return card;
	}
	
	@Override
	public Card7Wonders seeNextWonder()
	{
		Card7Wonders returnValue = null;
		
		if ( ! advances.isEmpty() )
		{
			returnValue = advances.peek();
		}
		
		return returnValue;
	}

	@Override
	public ResourceGroupData getResourceGroup() 
	{
		return resource;
	}

	public void setResource(ResourceGroupData resource) {
		this.resource = resource;
	}

	public void setAdvances(Stack<Card7Wonders> advances) {
		this.advances = advances;
	}

	public Stack<Card7Wonders> getAdvances() {
		return advances;
	}
}
