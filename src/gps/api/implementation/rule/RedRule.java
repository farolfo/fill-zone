package gps.api.implementation.rule;

public class RedRule extends FillZoneRule {

	private static FillZoneRule instance = new RedRule();

	public static FillZoneRule getInstance() {
		return instance;
	}

	public RedRule() {
		super(3);
	}

}
