/**
 * ����̊�ՃN���X
 */

package Game.Sprite.Weapon;

import Game.Sprite.Sprite;
import Game.Common.*;

import java.awt.Color;
import java.awt.Graphics;

import java.util.Random;

public class Weapon extends Sprite{
	public static final byte SWORD = 0;
	public static final byte ARROW = 1;
	public static final byte SHOT = 2;
	public Weapon(int x, int y){
		super(x,y);
	}
	public void setDirection(int d){
		direction = d;
	}
	// ���ۂɕ�����o��������
	public void appear(){assert(false);}
	
	// ����̍U����
	public int power;
	// �����ID(�K�v���ǂ����͕�����Ȃ����ǁc)
	public int weaponID;
	// ����̑���
	public int element;
	// �G�̕��킩�ǂ���
	protected boolean enemy;
	
	public void draw(Graphics g, int screenX, int screenY){
		Random rand = new Random(spriteID);
		switch(element){
		case Element.NONE:
			return;
		case Element.FIRE:
			g.setColor(new Color(255,0,0,128));
			break;
		case Element.WATER:
			g.setColor(new Color(0,0,255,128));
			break;
		case Element.THUNDER:
			g.setColor(new Color(255,255,0,128));
			break;
		}
		for(int i = -10; i < width; i+=10){
			for(int j = -10; j < height; j+=10){
				if(rand.nextInt(100) < 20){
					int size = rand.nextInt(20);
					g.fillOval(x-screenX+i,y-screenY+j,size,size);
				}
			}
		}
	}
}