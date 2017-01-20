package net.tompy.sw.data;

import net.tompy.common.CommonConstants;

public class ScoreData
{
	private int red;
	private int coins;
	private int wonders;
	private int blue;
	private int yellow;
	private int purple;
	private int green;
	
	public String display()
	{
		String out = "";
		
		out += "Military (red): " + red + CommonConstants.LINE_END;
		out += "Market (yellow): " + yellow + CommonConstants.LINE_END;
		out += "Coins: " + coins + CommonConstants.LINE_END;
		out += "Science (green): " + green + CommonConstants.LINE_END;
		out += "Buildings (blue): " + blue + CommonConstants.LINE_END;
		out += "Guilds (purple): " + purple + CommonConstants.LINE_END;
		out += "Wonder: " + wonders+ CommonConstants.LINE_END;
		out += "TOTAL:            " + getTotal() + CommonConstants.LINE_END;
		
		return out;
	}
	
	public int getTotal()
	{
		return red + coins + wonders + blue + yellow + purple + green;
	}

	public void addCoinPoints( int i )
	{
		coins += i;
	}
	
	public void addPoints( int i, ColorType c )
	{
		switch ( c )
		{
			case YELLOW:
				yellow += i;
				break;
			case RED:
				red += i;
				break;
			case BLUE:
				blue += i;
				break;
			case GREEN:
				green += i;
				break;
			case PURPLE:
				purple += i;
				break;
			case WONDER:
				wonders += i;
				break;
			case BROWN:
			case GREY:
				break;
		}
		
	}
	
	public void reset()
	{
		red = 0;
		coins = 0;
		wonders = 0;
		blue = 0;
		yellow = 0;
		purple = 0;
		green = 0;		
	}
}
