package gps.api.implementation.rule;

public class BlueRule extends FillZoneRule {

	private static FillZoneRule instance = new BlueRule();

	public static FillZoneRule getInstance() {
		return instance;
	}

	public BlueRule() {
		super(0);
	}

}
