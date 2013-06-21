import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.FileNotFoundException;

public class Card{
	public int cardNumber;
	public int element;
	public int power;
	public int callCost;
	public int moveCost;
	public int attackCost;
	public int skillCost;
	public int moveUpRange;
	public int moveDownRange;
	public int moveLeftRange;
	public int moveRightRange;
	public int attackUpRange;
	public int attackDownRange;
	public int attackLeftRange;
	public int attackRightRange;
	public int mSkill;
	public String mSkillName;
	public int aSkill;
	public String aSkillName;
	
	public boolean loadCardData(int n){
		String fileName = "card" + n;
		try{
			BufferedReader br = new BufferedReader(new InputStreamReader(
				getClass().getResourceAsStream("data/card/" + fileName + ".card")));
			String line;
			
			line = br.readLine();
			element = Integer.parseInt(line);
			line = br.readLine();
			power = Integer.parseInt(line);
			line = br.readLine();
			callCost = Integer.parseInt(line);
			line = br.readLine();
			moveCost = Integer.parseInt(line);
			line = br.readLine();
			attackCost = Integer.parseInt(line);
			line = br.readLine();
			skillCost = Integer.parseInt(line);
			line = br.readLine();
			moveUpRange = Integer.parseInt(line);
			line = br.readLine();
			moveDownRange = Integer.parseInt(line);
			line = br.readLine();
			moveLeftRange = Integer.parseInt(line);
			line = br.readLine();
			moveRightRange = Integer.parseInt(line);
			line = br.readLine();
			attackUpRange = Integer.parseInt(line);
			line = br.readLine();
			attackDownRange = Integer.parseInt(line);
			line = br.readLine();
			attackLeftRange = Integer.parseInt(line);
			line = br.readLine();
			attackRightRange = Integer.parseInt(line);
			line = br.readLine();
			mSkillName = line;
			line = br.readLine();
			mSkill = Integer.parseInt(line);
			line = br.readLine();
			aSkillName = line;
			line = br.readLine();
			aSkill = Integer.parseInt(line);
			return true;
		}catch (FileNotFoundException ex) {
//			ex.printStackTrace();
			return false;
		}catch (Exception e){
//			e.printStackTrace();
			return false;
		}
	}
}
