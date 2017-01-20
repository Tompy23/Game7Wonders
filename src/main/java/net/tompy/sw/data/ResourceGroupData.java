package net.tompy.sw.data;

import java.util.HashMap;
import java.util.Map;

public class ResourceGroupData implements Comparable< ResourceGroupData >
{
	Map< ResourceType, Integer > blob = new HashMap< ResourceType, Integer >();
	
	public ResourceGroupData()
	{
		for ( ResourceType rt : ResourceType.values() )
		{
			blob.put( rt, 0 );
		}
	}
	
	public ResourceGroupData( ResourceGroupData rgd )
	{
		for ( ResourceType rt : ResourceType.values() )
		{
			blob.put( rt, rgd.get( rt ) );
		}
	}
	
	public void init()
	{
		for ( ResourceType rt : ResourceType.values() )
		{
			if ( ! blob.containsKey( rt ) || null == blob.get( rt ) )
			{
				blob.put( rt, 0 );
			}
		}
	}
	
	public int get( ResourceType rt )
	{
		return blob.get( rt );
	}
	
	public void set( ResourceType rt, int i )
	{
		blob.put( rt, i );
	}
	

	public void increment( ResourceType rt )
	{
		increment( rt, 1 );
	}
	

	public void increment( ResourceType rt, int incr )
	{
		blob.put( rt, blob.get( rt ) + incr );
	}
	

	public void reset( ResourceType rt )
	{
		blob.put( rt, 0 );
	}
	

	public void add( ResourceGroupData rg )
	{
		for ( ResourceType rt : ResourceType.values() )
		{
			blob.put( rt, blob.get( rt ) + rg.get( rt ) );
		}
	}

	// Example
	// this == available resources
	// rg == cost of something
	// Returns
	// -1 if any rg > this
	// 0 if all rg == this 
	// 1 if all rg <= this AND all rg != this
	@Override
	public int compareTo( ResourceGroupData rg )
	{
		int returnValue = 0;
		
		for ( ResourceType rt : ResourceType.values() )
		{
			if ( rg.get( rt ) > get( rt ) )
			{
				returnValue = -1;
				break;
			}
			if ( rg.get( rt ) < get( rt ) )
			{
				returnValue++;
			}
		}
		
		return ( returnValue > 0 ) ? 1 : returnValue;
	}

	// this - rg
	public void subtract(ResourceGroupData rg) 
	{
		for ( ResourceType rt : ResourceType.values() )
		{
			int i = get( rt ) - rg.get( rt );
			if ( i < 0 )
			{
				i = 0;
			}
			set( rt, i );
		}
	}

	public String display() 
	{
		String returnValue = "";
		
		for ( ResourceType rt : ResourceType.values() )
		{
			if ( null != blob.get( rt ) && blob.get( rt ) > 0 )
			{
				returnValue += ( rt.name() + ": " + blob.get( rt ) + "   " );
			}
		}
		
		if ( "".equals( returnValue ) )
		{
			returnValue = "No resources";
		}
		
		return returnValue;
	}

	public void setBlob(Map<ResourceType, Integer> blob) {
		this.blob = blob;
	}

}
