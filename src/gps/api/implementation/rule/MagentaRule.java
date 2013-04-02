package gps.api.implementation.rule;

public class MagentaRule extends FillZoneRule {

	private static FillZoneRule instance = new MagentaRule();

	public static FillZoneRule getInstance() {
		return instance;
	}

	public MagentaRule() {
		super(2);
	}

}
