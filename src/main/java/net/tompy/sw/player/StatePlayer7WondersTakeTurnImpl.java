package net.tompy.sw.player;

import net.tompy.gameai.fsm.State;

public class StatePlayer7WondersTakeTurnImpl implements State<Player7Wonders> 
{

	@Override
	public void execute(Player7Wonders me) 
	{
		me.setCardToPlay( me.getStrategy().decideCardToPlay( me.getHand(), me ) );

	}

	@Override
	public void enter(Player7Wonders me) {
		// TODO Auto-generated method stub

	}

	@Override
	public void exit(Player7Wonders me) {
		// TODO Auto-generated method stub

	}

}
