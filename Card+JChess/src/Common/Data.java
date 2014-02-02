package Common;

import java.util.HashMap;
import java.util.Map;

public class Data {
	public static final int BOX_SIZE = 32;
	public static final int FIELD_X = 9;
	public static final int FIELD_Y = 9;
	public static final int FPS = 30;
	public enum ELEMENT{
		NONE,
		FIRE,
		AQUA,
		ELEC,
		LEAF,
		LIGHT,
		DARK,
	}
	public static final Map<ELEMENT, ELEMENT> STRONGER =
			new HashMap<ELEMENT, ELEMENT>(){{
				put(ELEMENT.FIRE,ELEMENT.LEAF);
				put(ELEMENT.AQUA,ELEMENT.FIRE);
				put(ELEMENT.ELEC,ELEMENT.AQUA);
				put(ELEMENT.LEAF,ELEMENT.ELEC);
				put(ELEMENT.LIGHT,ELEMENT.DARK);
				put(ELEMENT.DARK,ELEMENT.LIGHT);
			}};
	public static final Map<ELEMENT, ELEMENT> WEAKER =
			new HashMap<ELEMENT, ELEMENT>(){{
				put(ELEMENT.FIRE,ELEMENT.AQUA);
				put(ELEMENT.AQUA,ELEMENT.ELEC);
				put(ELEMENT.ELEC,ELEMENT.LEAF);
				put(ELEMENT.LEAF,ELEMENT.FIRE);
				put(ELEMENT.LIGHT,ELEMENT.DARK);
				put(ELEMENT.DARK,ELEMENT.LIGHT);
			}};
	public static int HAND_NUMBER = 5;
}
