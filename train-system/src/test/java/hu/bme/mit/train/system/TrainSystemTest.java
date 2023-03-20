package hu.bme.mit.train.system;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import hu.bme.mit.train.interfaces.TrainController;
import hu.bme.mit.train.interfaces.TrainSensor;
import hu.bme.mit.train.interfaces.TrainUser;
import hu.bme.mit.train.system.TrainSystem;
import hu.bme.mit.train.tachograph.Tachograph;

public class TrainSystemTest {

	TrainController controller;
	TrainSensor sensor;
	TrainUser user;
	Tachograph tachograph;
	
	@Before
	public void before() {
		TrainSystem system = new TrainSystem();
		controller = system.getController();
		sensor = system.getSensor();
		user = system.getUser();
		tachograph = new Tachograph(controller, user);

		sensor.overrideSpeedLimit(50);
	}
	
	@Test
	public void OverridingJoystickPosition_IncreasesReferenceSpeed() {
		sensor.overrideSpeedLimit(10);

		Assert.assertEquals(0, controller.getReferenceSpeed());
		
		user.overrideJoystickPosition(5);

		controller.followSpeed();
		Assert.assertEquals(5, controller.getReferenceSpeed());
		controller.followSpeed();
		Assert.assertEquals(10, controller.getReferenceSpeed());
		controller.followSpeed();
		Assert.assertEquals(10, controller.getReferenceSpeed());
	}

	@Test
	public void OverridingJoystickPositionToNegative_SetsReferenceSpeedToZero() {
		user.overrideJoystickPosition(4);
		controller.followSpeed();
		user.overrideJoystickPosition(-5);
		controller.followSpeed();
		Assert.assertEquals(0, controller.getReferenceSpeed());
	}

	@Test
	public void OverridingJoystickPosition_IncreasesReferenceSpeed_DoEmergencyBreak(){
		sensor.overrideSpeedLimit(50);

		Assert.assertEquals(0, controller.getReferenceSpeed());

		user.overrideJoystickPosition(5);
		controller.followSpeed();
		controller.followSpeed();
		controller.followSpeed();

		Assert.assertEquals(15, controller.getReferenceSpeed());

		user.setEmergencyBreakMode(true);
		controller.followSpeed();

		Assert.assertEquals(0, controller.getReferenceSpeed());

		controller.followSpeed();

		Assert.assertEquals(0, controller.getReferenceSpeed());

		user.setEmergencyBreakMode(false);
		controller.followSpeed();

		Assert.assertEquals(5, controller.getReferenceSpeed());
	}

	@Test
	public void OverridingJoystickPosition_IncreasesReferenceSpeed_TestTachograph()
	{
		sensor.overrideSpeedLimit(50);
		user.overrideJoystickPosition(5);
		controller.followSpeed();

		tachograph.saveSample();

		Assert.assertEquals(true, tachograph.hasSampleSaved());
	}

	
}
