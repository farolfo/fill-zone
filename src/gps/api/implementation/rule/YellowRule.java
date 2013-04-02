package gps.api.implementation.rule;

public class YellowRule extends FillZoneRule {

	private static FillZoneRule instance = new YellowRule();

	public static FillZoneRule getInstance() {
		return instance;
	}

	public YellowRule() {
		super(5);
	}

}
