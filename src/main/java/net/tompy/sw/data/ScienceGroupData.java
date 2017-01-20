package net.tompy.sw.data;

import java.util.HashMap;
import java.util.Map;

public class ScienceGroupData implements Comparable< ScienceGroupData >
{
	private Map< ScienceType, Integer > blob = new HashMap< ScienceType, Integer >();
	private int score = 0;
	
	public ScienceGroupData()
	{
		for ( ScienceType st : ScienceType.values() )
		{
			blob.put( st, 0 );
		}
	}
	
	public ScienceGroupData( ScienceType st )
	{
		blob.put( st, 1 );
	}
	
	public ScienceGroupData( ScienceGroupData sgd )
	{
		for ( ScienceType st : ScienceType.values() )
		{
			blob.put( st, sgd.get( st ) );
		}
	}
	
	public int get( ScienceType st )
	{
		return blob.get( st );
	}
	
	public void increment( ScienceType st )
	{
		blob.put( st, blob.get( st ) + 1 );
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	@Override
	public int compareTo(ScienceGroupData arg0) 
	{
		return score - arg0.getScore();
	}

}
