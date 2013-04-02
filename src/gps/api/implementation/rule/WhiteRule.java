package gps.api.implementation.rule;

public class WhiteRule extends FillZoneRule {

	private static FillZoneRule instance = new WhiteRule();

	public static FillZoneRule getInstance() {
		return instance;
	}

	public WhiteRule() {
		super(4);
	}

}
