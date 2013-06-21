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
	
	public static final int ROW = 12;
	public static final int COL = 11;

	public static final int MoveX = 28;
	public static final int MoveY = 57;
	
	public static final int AttackX = 216;
	public static final int AttackY = 57;
	
	public static final int MoveAttackX = 150;
	public static final int MoveAttackY = 50;
	
	public static final int CENTER_X = 192;
	public static final int CENTER_Y = 419;

	public static final int CS = 15;
	
	public static final int ELEMENT_X = 346;
	public static final int ELEMENT_Y = 190;
	
	public static final int ElementSize = 50;
	
	public static final int NumberOffset = 4;
	
	public static final int powerX = 322;
	public static final int powerY = 299;
	
	public static final int mSkillX = 440;
	public static final int mSkillY = 364;
	
	public static final int MSkillNameX = 547;
	public static final int MSkillNameY = 330;
	
	public static final int aSkillX = 440;
	public static final int aSkillY = 521;
	
	public static final int ASkillNameX = 544;
	public static final int ASkillNameY = 487;
	
	public static final int stringYSize = 20;
	public static final int wordCount = 10;
	public static final int wordROW = 5;
	
	public static final int costX = 563;
	public static final int attackCostY = 167;
	
	public static final int moveCostY = 100;
	
	public static final int callCostY = 33;
	public static final int skillCostY = 239;
	
	public static final int UP = 0;
	public static final int DOWN = 1;
	public static final int LEFT = 2;
	public static final int RIGHT = 3;
	public static final int SPACE = 4;
	public static final int SHIFT = 5;
	
}