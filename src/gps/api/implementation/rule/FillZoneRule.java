package gps.api.implementation.rule;

import gps.api.GPSRule;
import gps.api.GPSState;
import gps.api.implementation.FillZoneState;
import gps.exception.NotAppliableException;

public abstract class FillZoneRule implements GPSRule {

	private int color;
	
	protected FillZoneRule(int color) {
		this.color = color;
	}
	
	@Override
	public Integer getCost() {
		return 1;
	}

	@Override
	public String getName() {
		return "Regla " + color;
	}

	@Override
	public GPSState evalRule(GPSState state) throws NotAppliableException {
		FillZoneState fzstate = null;
		try {
			fzstate = (FillZoneState)((FillZoneState) state).clone();
		} catch (CloneNotSupportedException e) {
			System.err.print("Fatal: FillZoneState clone failed");
			System.exit(1);
		}
		fzstate.changeColor(color);
		if ( fzstate.hasLost() ) {
			throw new NotAppliableException();
		}
		return fzstate;
	}

}
