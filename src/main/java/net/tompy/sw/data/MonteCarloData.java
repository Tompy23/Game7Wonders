package net.tompy.sw.data;

public class MonteCarloData 
{
	private boolean win;
	private int score;
	private PlayGroupData card;
	
	public boolean isWin() {
		return win;
	}
	public void setWin(boolean win) {
		this.win = win;
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	public PlayGroupData getCard() {
		return card;
	}
	public void setCard(PlayGroupData card) {
		this.card = card;
	}
}
