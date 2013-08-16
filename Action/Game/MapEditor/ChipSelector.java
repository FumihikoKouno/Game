import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;

import javax.swing.JPanel;

class ChipSelector extends JPanel implements MouseListener, MouseMotionListener{
	public final Image image = new ImageIcon(getClass().getResource("./mapImage.gif")).getImage();
	private final int CHIP_SIZE = 32;
	private final int CHIP_COL = 16;
	
	private int col = CHIP_COL;
	private int row = CHIP_COL;
	
	private boolean mouseInThis;
	private int mouseX;
	private int mouseY;
	public int selectedChip;
	private int x;
	private int y;
	
	private Image dbImage;
	private Graphics dbg;
	private ChipSelector cs;
	
	public ChipSelector() {
		x = y = 0;
		addMouseListener(this);
		addMouseMotionListener(this);
		setPreferredSize(new Dimension(col*CHIP_SIZE,row*CHIP_SIZE));
	}
	
	public void update() {
		while(dbImage == null){
			dbImage = createImage(col*CHIP_SIZE,row*CHIP_SIZE);
			if(dbImage == null){
				System.out.println("dbImage is null");
				return;
			}else{
				dbg = dbImage.getGraphics();
			}
		}
		draw();
		paintScreen();
	}
	
	public void draw(){
		/*
		dbg.drawImage(image,
			0,0,WIDTH,HEIGHT,
			x*CHIP_SIZE,y*CHIP_SIZE,
			x*CHIP_SIZE+WIDTH,y*CHIP_SIZE+HEIGHT,
			null);
		*/
		dbg.drawImage(image,0,0,null);
		dbg.setColor(Color.WHITE);
		int ox = (selectedChip%CHIP_COL)*CHIP_SIZE;
		int oy = (selectedChip/CHIP_COL)*CHIP_SIZE;
		for(int i = 0; i < 3; i++){
			dbg.drawRect(ox,oy,CHIP_SIZE+(i<<1),CHIP_SIZE+(i<<1));
		}
	}
	
	public void selectChip(int num){
		selectedChip = num;
		update();
	}
	
	public void selectChip(){
		selectedChip = (x+mouseX)+(y+mouseY)*CHIP_COL;
		update();
	}
	
	public void paintScreen(){
		try{
			Graphics g = getGraphics();
			if((g != null) && (dbImage != null)){
				g.drawImage(dbImage,0,0,null);
			}
			Toolkit.getDefaultToolkit().sync();
			if(g != null){
				g.dispose();
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	
	// こっからマウス関係のメソッド
	public void mousePressed(MouseEvent e){
		if(!mouseInThis) return;
		mouseX = e.getX()/CHIP_SIZE;
		mouseY = e.getY()/CHIP_SIZE;
		if(mouseX < 0 || mouseX >= col) return;
		if(mouseY < 0 || mouseY >= row) return;
		selectChip();
	}
	public void mouseDragged(MouseEvent e){
		if(!mouseInThis) return;
		mouseX = e.getX()/CHIP_SIZE;
		mouseY = e.getY()/CHIP_SIZE;
		if(mouseX < 0 || mouseX >= col) return;
		if(mouseY < 0 || mouseY >= row) return;
		selectChip();
	}
	public void mouseEntered(MouseEvent e){
		mouseInThis = true;
	}
	public void mouseExited(MouseEvent e){
		mouseInThis = false;
	}
	public void mouseReleased(MouseEvent e){
	}
	public void mouseMoved(MouseEvent e){
	}
	public void mouseClicked(MouseEvent e){
	}
}
