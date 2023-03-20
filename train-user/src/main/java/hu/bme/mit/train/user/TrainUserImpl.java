package hu.bme.mit.train.user;

import hu.bme.mit.train.interfaces.TrainController;
import hu.bme.mit.train.interfaces.TrainUser;

public class TrainUserImpl implements TrainUser {

	private TrainController controller;
	private int joystickPosition;

	public TrainUserImpl(TrainController controller) {
		this.controller = controller;
	}

	@Override
	public boolean getAlarmFlag() {
		return false;
	}

	@Override
	public int getJoystickPosition() {
		return joystickPosition;
	}

	@Override
	public void overrideJoystickPosition(int joystickPosition) {
		this.joystickPosition = joystickPosition;
		if(!controller.getEmergencyBreakMode())
		{
			controller.setJoystickPosition(joystickPosition);
		}
	}

	@Override
	public void setEmergencyBreakMode(boolean bDoEmergencyBreak)
	{
		controller.setEmergencyBreakMode(bDoEmergencyBreak);
		if(bDoEmergencyBreak)
		{
			controller.setJoystickPosition(0);
		}
		else
		{
			controller.setJoystickPosition(joystickPosition);
		}

	}

}
