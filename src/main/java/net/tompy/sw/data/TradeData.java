package net.tompy.sw.data;


public class TradeData 
{
	private ResourceGroupData rgdLeft = new ResourceGroupData();
	private ResourceGroupData rgdRight = new ResourceGroupData();
	public ResourceGroupData getRgdLeft() {
		return rgdLeft;
	}
	public void setRgdLeft(ResourceGroupData rgdLeft) {
		this.rgdLeft = rgdLeft;
	}
	public ResourceGroupData getRgdRight() {
		return rgdRight;
	}
	public void setRgdRight(ResourceGroupData rgdRight) {
		this.rgdRight = rgdRight;
	}
}
