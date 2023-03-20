package hu.bme.mit.train.tachograph;

import hu.bme.mit.train.interfaces.TrainController;
import hu.bme.mit.train.interfaces.TrainUser;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;

import java.time.LocalDateTime;

public class Tachograph {
    private TrainController controller;
	private TrainUser user;
    Table<LocalDateTime, Integer, Integer> tachographTable;

    public Tachograph(TrainController controller, TrainUser user)
    {
        this.controller = controller;
		this.user = user;
        tachographTable = HashBasedTable.create();
    }

    public void saveSample()
    {
        tachographTable.put(LocalDateTime.now(), user.getJoystickPosition(), controller.getReferenceSpeed());
    }

    public boolean hasSampleSaved()
    {
        return !tachographTable.isEmpty();
    }

}