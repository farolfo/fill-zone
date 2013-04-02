package gps.api.implementation.rule;

public class GreenRule extends FillZoneRule {
	
	private static FillZoneRule instance = new GreenRule();

	public static FillZoneRule getInstance() {
		return instance;
	}
	
	public GreenRule() {
		super(1);
	}

}
