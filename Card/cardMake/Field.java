import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Graphics2D;
import java.awt.Font;

import javax.swing.ImageIcon;

public class Field implements Common{
	
	private Image fieldImage;
	private Image elementImage;
	private Image numberImage;
	
	private int MaxMSkill;
	private int MaxASkill;
	
	private MainPanel panel;
	
	private Point mouse;
	private Card card = new Card();
	
	private int moveOrAttack;
	
	public Field(MainPanel panel){
		String s = "";
		int i;
		for(i = 0; !s.equals("Error"); i++){
			s = new MSkill(i).getExplanation();
		}
		MaxMSkill = i-2;
		
		s = "";
		for(i = 0; !s.equals("Error"); i++){
			s = new ASkill(i).getExplanation();
		}
		MaxASkill = i-2;
		
		this.panel = panel;
		loadImage();
	}
	
	private int abs(int x){
		if(x < 0) return -x;
		else return x;
	}
	
	public void output(){
		System.out.println(card.element);
		System.out.println(card.power);
		System.out.println(card.callCost);
		System.out.println(card.moveCost);
		System.out.println(card.attackCost);
		System.out.println(card.skillCost);
		System.out.println(card.moveUpRange);
		System.out.println(card.moveDownRange);
		System.out.println(card.moveLeftRange);
		System.out.println(card.moveRightRange);
		System.out.println(card.attackUpRange);
		System.out.println(card.attackDownRange);
		System.out.println(card.attackLeftRange);
		System.out.println(card.attackRightRange);
		System.out.println(card.mSkillName);
		System.out.println(card.mSkill);
		System.out.println(card.aSkillName);
		System.out.println(card.aSkill);
	}
	
	public void clickField(){
		int outFieldX = 65536;
		int outFieldY = 65536;
		Point p = new Point(outFieldX,outFieldY);
		for(int i = 0; i < ROW; i++){
			if(mouse.y > CENTER_Y - i * CS && mouse.y < CENTER_Y - i * CS + CS){
				p.y = -i;
			}
			if(mouse.y > CENTER_Y + i * CS && mouse.y < CENTER_Y + i * CS + CS){
				p.y = i;
			}
		}
		for(int j = 0; j < COL; j++){
			if(mouse.x > CENTER_X - j * CS && mouse.x < CENTER_X - j * CS + CS){
				p.x = -j;
			}
			if(mouse.x > CENTER_X + j * CS && mouse.x < CENTER_X + j * CS + CS){
				p.x = j;
			}
		}
		if(p.x == outFieldX || p.y == outFieldY) return;
		if(p.x * p.y == 0){
			if(p.y < 0){
				switch(moveOrAttack){
				case 0:
					if(card.moveUpRange == abs(p.y)){
						card.moveUpRange = 0;
					}else{
						card.moveUpRange = abs(p.y);
					}
					break;
				case 1:
					if(card.attackUpRange == abs(p.y)){
						card.attackUpRange = 0;
					}else{
						card.attackUpRange = abs(p.y);
					}
				}
			}
			if(p.y > 0){
				switch(moveOrAttack){
				case 0:
					if(card.moveDownRange == abs(p.y)){
						card.moveDownRange = 0;
					}else{
						card.moveDownRange = abs(p.y);
					}
					break;
				case 1:
					if(card.attackDownRange == abs(p.y)){
						card.attackDownRange = 0;
					}else{
						card.attackDownRange = abs(p.y);
					}
					break;
				}
			}
			if(p.x < 0){
				switch(moveOrAttack){
				case 0:
					if(card.moveLeftRange == abs(p.x)){
						card.moveLeftRange = 0;
					}else{
						card.moveLeftRange = abs(p.x);
					}
					break;
				case 1:
					if(card.attackLeftRange == abs(p.x)){
						card.attackLeftRange = 0;
					}else{
						card.attackLeftRange = abs(p.x);
					}
					break;
				}
			}
			if(p.x > 0){
				switch(moveOrAttack){
				case 0:
					if(card.moveRightRange == abs(p.x)){
						card.moveRightRange = 0;
					}else{
						card.moveRightRange = abs(p.x);
					}
					break;
				case 1:
					if(card.attackRightRange == abs(p.x)){
						card.attackRightRange = 0;
					}else{
						card.attackRightRange = abs(p.x);
					}
					break;
				}
			}
		}
	}
	
	private int power(int x, int n){
		int ans = 1;
		for(int i = 0; i < n; i++){
			ans *= x;
		}
		return ans;
	}
	
	public void setMousePoint(Point p){
		mouse = p;
		Point checkPoint = new Point(p.x,p.y);
		int elementCheck;
		if(checkPoint.x > CENTER_X - (COL-1) * CS
			&& checkPoint.x < CENTER_X + COL * CS
			&& checkPoint.y > CENTER_Y - (ROW-1) * CS
			&& checkPoint.y < CENTER_Y + ROW * CS
		) clickField();
		
		checkPoint.x -= (ELEMENT_X + ElementSize/2);
		checkPoint.y -= (ELEMENT_Y + ElementSize/2);
		elementCheck = power(checkPoint.x,2) + power(checkPoint.y,2);
		if(elementCheck < power(ElementSize/2,2)) clickElement();
		
		checkPoint = new Point(p.x,p.y);
		if(checkPoint.x >= MoveX
			&& checkPoint.x <= AttackX + MoveAttackX
			&& checkPoint.y >= MoveY
			&& checkPoint.y <= AttackY + MoveAttackY
		) selectMoveAttack();
		
		if(checkPoint.x >= costX && checkPoint.x <= costX + NumberOffset + CS + CS){
			if(checkPoint.y >= callCostY - NumberOffset - CS && checkPoint.y <= callCostY + NumberOffset + CS + CS){
				clickCallCost();
			}
			if(checkPoint.y >= moveCostY - NumberOffset - CS && checkPoint.y <= moveCostY + NumberOffset + CS + CS){
				clickMoveCost();
			}
			if(checkPoint.y >= attackCostY - NumberOffset - CS && checkPoint.y <= attackCostY + NumberOffset + CS + CS){
				clickAttackCost();
			}
			if(checkPoint.y >= skillCostY - NumberOffset - CS && checkPoint.y <= skillCostY + NumberOffset + CS + CS){
				clickSkillCost();
			}
		}
		if(checkPoint.x >= powerX && checkPoint.x <= powerX + (3 * NumberOffset) + (4 * CS)){
			if(checkPoint.y >= powerY - NumberOffset - CS && checkPoint.y <= powerY + NumberOffset + CS + CS){
				clickPower();
			}
		}
		if(checkPoint.x >= MSkillNameX - CS - NumberOffset && checkPoint.x <= MSkillNameX + (2 * NumberOffset) + (3 * CS)){
			if(checkPoint.y >= MSkillNameY && checkPoint.y <= MSkillNameY + CS){
				clickMSkill();
			}
		}
		if(checkPoint.x >= ASkillNameX - CS - NumberOffset && checkPoint.x <= ASkillNameX + (2 * NumberOffset) + (3 * CS)){
			if(checkPoint.y >= ASkillNameY && checkPoint.y <= ASkillNameY + CS){
				clickASkill();
			}
		}
	}
	
	private void clickMSkill(){
		if(mouse.x <= MSkillNameX - NumberOffset){
			card.mSkill = max(0,card.mSkill-1);
		}
		if(mouse.x >= MSkillNameX + 2 * NumberOffset + 2 * CS){
			card.mSkill = min(MaxMSkill,card.mSkill+1);
		}
	}
	private void clickASkill(){
		if(mouse.x <= ASkillNameX - NumberOffset){
			card.aSkill = max(0,card.aSkill-1);
		}
		if(mouse.x >= ASkillNameX + 2 * NumberOffset + 2 * CS){
			card.aSkill = min(MaxASkill,card.aSkill+1);
		}
	}
	
	
	public int max(int a, int b){
		if(a < b) return b;
		else return a;
	}
	public int min(int a, int b){
		if(a > b) return b;
		else return a;
	}
	public void clickPower(){
		if(mouse.y <= powerY - NumberOffset){
			for(int i = 0; i < 4; i++){
				if(mouse.x >= powerX + i * (NumberOffset+CS) && mouse.x <= powerX + i * (NumberOffset+CS) + CS){
					card.power = min(9999,card.power+power(10,3-i));
				}
			}
		}
		if(mouse.y >= powerY + NumberOffset + CS){
			for(int i = 0; i < 4; i++){
				if(mouse.x >= powerX + i * (NumberOffset+CS) && mouse.x <= powerX + i * (NumberOffset+CS) + CS){
					card.power = max(0000,card.power-power(10,3-i));
				}
			}
		}
	}
	
	public void clickCallCost(){
		if(mouse.y <= callCostY - NumberOffset){
			if(mouse.x <= costX + CS){
				card.callCost = min(99,card.callCost+10);
			}
			if(mouse.x >= costX + CS + NumberOffset){
				card.callCost = min(99,card.callCost+1);
			}
		}
		if(mouse.y >= callCostY + NumberOffset + CS){
			if(mouse.x <= costX + CS){
				card.callCost = max(0,card.callCost-10);
			}
			if(mouse.x >= costX + CS + NumberOffset){
				card.callCost = max(0,card.callCost-1);
			}
		}
	}
	public void clickAttackCost(){
		if(mouse.y <= attackCostY - NumberOffset){
			if(mouse.x <= costX + CS){
				card.attackCost = min(99,card.attackCost+10);
			}
			if(mouse.x >= costX + CS + NumberOffset){
				card.attackCost = min(99,card.attackCost+1);
			}
		}
		if(mouse.y >= attackCostY + NumberOffset + CS){
			if(mouse.x <= costX + CS){
				card.attackCost = max(0,card.attackCost-10);
			}
			if(mouse.x >= costX + CS + NumberOffset){
				card.attackCost = max(0,card.attackCost-1);
			}
		}
	}
	public void clickMoveCost(){
		if(mouse.y <= moveCostY - NumberOffset){
			if(mouse.x <= costX + CS){
				card.moveCost = min(99,card.moveCost+10);
			}
			if(mouse.x >= costX + CS + NumberOffset){
				card.moveCost = min(99,card.moveCost+1);
			}
		}
		if(mouse.y >= moveCostY + NumberOffset + CS){
			if(mouse.x <= costX + CS){
				card.moveCost = max(0,card.moveCost-10);
			}
			if(mouse.x >= costX + CS + NumberOffset){
				card.moveCost = max(0,card.moveCost-1);
			}
		}
	}
	public void clickSkillCost(){
		if(mouse.y <= skillCostY - NumberOffset){
			if(mouse.x <= costX + CS){
				card.skillCost = min(99,card.skillCost+10);
			}
			if(mouse.x >= costX + CS + NumberOffset){
				card.skillCost = min(99,card.skillCost+1);
			}
		}
		if(mouse.y >= skillCostY + NumberOffset + CS){
			if(mouse.x <= costX + CS){
				card.skillCost = max(0,card.skillCost-10);
			}
			if(mouse.x >= costX + CS + NumberOffset){
				card.skillCost = max(0,card.skillCost-1);
			}
		}
	}
	
	
	public void selectMoveAttack(){
		if(mouse.x <= MoveX + MoveAttackX){
			moveOrAttack = 0;
		}
		if(mouse.x >= AttackX){
			moveOrAttack = 1;
		}
	}
	
	public void clickElement(){
		card.element++;
		card.element%=5;
	}
	
	public void draw(Graphics g){
		g.drawImage(fieldImage,0,0,panel);
		drawPower(g);
		drawSkillExplanation(g);
		drawElement(g);
		drawSpendCost(g);
		drawSkill(g);
		drawRange(g);
	}
	public void drawSkillExplanation(Graphics g){
		new MSkill(card.mSkill).draw(g);
		new ASkill(card.aSkill).draw(g);
	}
	
	public void drawElement(Graphics g){
		g.drawImage(elementImage,ELEMENT_X,ELEMENT_Y,
			ELEMENT_X + ElementSize, ELEMENT_Y + ElementSize,
			card.element * ElementSize, 0,
			card.element * ElementSize + ElementSize, ElementSize,
			panel);
	}
	
	public void drawPower(Graphics g){
		int figure[] = new int[4];
		int temp = card.power;
		for(int i = 0; i < 4; i++){
			figure[i] = temp/power(10,3-i);
			temp -= figure[i] * power(10,3-i);
		}
		for(int i = 0; i < 4; i++){
			g.drawImage(numberImage,
				powerX + i * (CS + NumberOffset), powerY,
				powerX + i * (CS + NumberOffset) + CS, powerY+CS,
				figure[i] * CS, 0,
				figure[i] * CS + CS, CS, panel
			);
		}
	}
	public void drawSkill(Graphics g){
		g.drawImage(numberImage,
			MSkillNameX, MSkillNameY,
			MSkillNameX + CS, MSkillNameY + CS,
			(card.mSkill/10) * CS, 0,
			(card.mSkill/10) * CS + CS, CS, panel
		);
		g.drawImage(numberImage,
			MSkillNameX + CS + NumberOffset, MSkillNameY,
			MSkillNameX + CS + NumberOffset + CS, MSkillNameY + CS,
			(card.mSkill%10) * CS, 0,
			(card.mSkill%10) * CS + CS, CS, panel
		);
		g.drawImage(numberImage,
			ASkillNameX, ASkillNameY,
			ASkillNameX + CS, ASkillNameY + CS,
			(card.aSkill/10) * CS, 0,
			(card.aSkill/10) * CS + CS, CS, panel
		);
		g.drawImage(numberImage,
			ASkillNameX + CS + NumberOffset, ASkillNameY,
			ASkillNameX + CS + NumberOffset + CS, ASkillNameY + CS,
			(card.aSkill%10) * CS, 0,
			(card.aSkill%10) * CS + CS, CS, panel
		);
	}
	
	
	public void drawSpendCost(Graphics g){
		g.drawImage(numberImage,
			costX, callCostY,
			costX + CS, callCostY + CS,
			(card.callCost/10) * CS, 0,
			(card.callCost/10) * CS + CS, CS, panel
		);
		g.drawImage(numberImage,
			costX + CS + NumberOffset, callCostY,
			costX + CS + NumberOffset + CS, callCostY + CS,
			(card.callCost%10) * CS, 0,
			(card.callCost%10) * CS + CS, CS, panel
		);
		g.drawImage(numberImage,
			costX, moveCostY,
			costX + CS, moveCostY + CS,
			(card.moveCost/10) * CS, 0,
			(card.moveCost/10) * CS + CS, CS, panel
		);
		g.drawImage(numberImage,
			costX + CS + NumberOffset, moveCostY,
			costX + CS + NumberOffset + CS, moveCostY + CS,
			(card.moveCost%10) * CS, 0,
			(card.moveCost%10) * CS + CS, CS, panel
		);
		g.drawImage(numberImage,
			costX, attackCostY,
			costX + CS, attackCostY + CS,
			(card.attackCost/10) * CS, 0,
			(card.attackCost/10) * CS + CS, CS, panel
		);
		g.drawImage(numberImage,
			costX + CS + NumberOffset, attackCostY,
			costX + CS + NumberOffset + CS, attackCostY + CS,
			(card.attackCost%10) * CS, 0,
			(card.attackCost%10) * CS + CS, CS, panel
		);
		g.drawImage(numberImage,
			costX, skillCostY,
			costX + CS, skillCostY + CS,
			(card.skillCost/10) * CS, 0,
			(card.skillCost/10) * CS + CS, CS, panel
		);
		g.drawImage(numberImage,
			costX + CS + NumberOffset, skillCostY,
			costX + CS + NumberOffset + CS, skillCostY + CS,
			(card.skillCost%10) * CS, 0,
			(card.skillCost%10) * CS + CS, CS, panel
		);
	}
	
	public void drawRange(Graphics g){
		Graphics2D g2 = (Graphics2D) g;
		g2.setComposite(clear);
		g2.setColor(Color.red);
		switch(moveOrAttack){
		case 0:
			g2.fillRect(MoveX,MoveY,MoveAttackX,MoveAttackY);
			for(int i = 0; i < card.moveUpRange; i++){
				g2.fillRect(CENTER_X,CENTER_Y-(i+1)*CS,CS,CS);
			}
			for(int i = 0; i < card.moveDownRange; i++){
				g2.fillRect(CENTER_X,CENTER_Y+(i+1)*CS,CS,CS);
			}
			for(int i = 0; i < card.moveLeftRange; i++){
				g2.fillRect(CENTER_X-(i+1)*CS,CENTER_Y,CS,CS);
			}
			for(int i = 0; i < card.moveRightRange; i++){
				g2.fillRect(CENTER_X+(i+1)*CS,CENTER_Y,CS,CS);
			}
			break;
		case 1:
			g2.fillRect(AttackX,AttackY,MoveAttackX,MoveAttackY);
			g2.setColor(Color.blue);
			for(int i = 0; i < card.attackUpRange; i++){
				g2.fillRect(CENTER_X,CENTER_Y-(i+1)*CS,CS,CS);
			}
			for(int i = 0; i < card.attackDownRange; i++){
				g2.fillRect(CENTER_X,CENTER_Y+(i+1)*CS,CS,CS);
			}
			for(int i = 0; i < card.attackLeftRange; i++){
				g2.fillRect(CENTER_X-(i+1)*CS,CENTER_Y,CS,CS);
			}
			for(int i = 0; i < card.attackRightRange; i++){
				g2.fillRect(CENTER_X+(i+1)*CS,CENTER_Y,CS,CS);
			}
			break;
		}
		g2.fillRect(CENTER_X,CENTER_Y,CS,CS);
		g2.setComposite(opaque);
	}
	
	private void loadImage(){
		ImageIcon icon = new ImageIcon(getClass().getResource("image/field.gif"));
		fieldImage = icon.getImage();
		icon = new ImageIcon(getClass().getResource("image/element.gif"));
		elementImage = icon.getImage();
		icon = new ImageIcon(getClass().getResource("image/number.gif"));
		numberImage = icon.getImage();
	}
}
