import java.awt.Font;
import java.awt.AlphaComposite;
public interface Common{

	public static final AlphaComposite clear = 
	AlphaComposite.getInstance
	(AlphaComposite.SRC_OVER, 0.3f);
	
	public static final AlphaComposite opaque = 
	AlphaComposite.getInstance
	(AlphaComposite.SRC_OVER, 1.0f);
	
	public static final Font font = new Font("Dialog",Font.BOLD,16);
	
	public static final int MAX_DAMAGE = 9999;
	
	public static final int DAMAGE = 1;
	public static final int ATTACK = 2;
	public static final int FRIEND_SUPPORT = 3;
	public static final int TARGET_SUPPORT = 4;
	public static final int RED_TURN = 5;
	public static final int BLUE_TURN = 6;
	public static final int BATTLE_START = 7;
	public static final int RED_WIN = 8;
	public static final int BLUE_WIN = 9;
	
	public static final int ROW = 12;
	public static final int COL = 11;
	
	public static final int MAX_DECK_NUMBER = 40;
	public static final int MAX_HAND_NUMBER = 8;
	
	public static final int FIELD_X = 35;
	public static final int FIELD_Y = 140;

	public static final int CS = 30;
	
	public static final int MENU_X_SIZE = 74;
	public static final int MENU_Y_SIZE = 15;
	
	
	public static final int MENU_Y_OFFSET = 16;
	
	public static final int ELEMENT_X = 587;
	public static final int ELEMENT_Y = 73;
	
	public static final int ELEMENT_SIZE = 50;
	
	public static final int HAND1_X = 25;
	public static final int HAND1_Y = 547;
	
	public static final int DECK1_X = 355;
	public static final int DECK1_Y = 547;
	
	public static final int GRAVE1_X = 305;
	public static final int GRAVE1_Y = 547;
	
	
	public static final int HAND2_X = 145;
	public static final int HAND2_Y = 63;
	
	public static final int DECK2_X = 25;
	public static final int DECK2_Y = 63;
	
	public static final int GRAVE2_X = 75;
	public static final int GRAVE2_Y = 63;
	
	public static final int MENU_NUMBER = 5;
	
	public static final int IMAGE_X = 440;
	public static final int IMAGE_Y = 90;
	
	public static final int POWER_X = 510;
	public static final int POWER_Y = 302;
	
	public static final int M_SKILL_X = 440;
	public static final int M_SKILL_Y = 364;
	
	public static final int M_SKILL_NAME_X = 528;
	public static final int M_SKILL_NAME_Y = 343;
	
	public static final int A_SKILL_X = 440;
	public static final int A_SKILL_Y = 521;
	
	public static final int A_SKILL_NAME_X = 528;
	public static final int A_SKILL_NAME_Y = 500;
	
	public static final int STRING_Y_SIZE = 20;
	public static final int WORD_COUNT = 10;
	public static final int WORD_ROW = 5;
	
	public static final int REST_COST_X = 540;
	public static final int REST_COST_Y = 27;
	
	public static final int SPEND_COST_X = 547;
	public static final int SPEND_COST_Y = 68;
	
	public static final int UP = 0;
	public static final int DOWN = 1;
	public static final int LEFT = 2;
	public static final int RIGHT = 3;
	public static final int SPACE = 4;
	public static final int SHIFT = 5;
	
}