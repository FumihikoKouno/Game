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

class StageViewer extends JPanel implements MouseListener, MouseMotionListener{
	private final Image image = new ImageIcon(getClass().getResource("./mapImage.gif")).getImage();
	private final int CHIP_SIZE = 32;
	private final int CHIP_COL = 16;
	
	public static final int GRAPHIC = 0;
	public static final int PASS = 1;
	
	public String backGroundName;
	
	public int mode = 1;
	
	private boolean mouseInThis;
	private int mouseX;
	private int mouseY;
	private int row;
	private int col;
	private int[][] data;
	private boolean[][] notPass;
	
	private boolean setPass;
	
	private Image back;
	private Image dbImage;
	private Graphics dbg;
	private ChipSelector cs;
	
	public StageViewer(ChipSelector cs) {
		this.cs = cs;
		back = null;
		createNewMap(20,15);
		addMouseListener(this);
		addMouseMotionListener(this);
		setPreferredSize(new Dimension(col*CHIP_SIZE, row*CHIP_SIZE));
	}
	
	public void makeNewDBImage(){
		dbImage = createImage(col*CHIP_SIZE,row*CHIP_SIZE);
		if(dbImage == null){
			System.out.println("dbImage is null");
			return;
		}else{
			dbg = dbImage.getGraphics();
		}
		setPreferredSize(new Dimension(col*CHIP_SIZE, row*CHIP_SIZE));
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
	
	public void createNewMap(int col, int row){
		this.row = row;
		this.col = col;
		data = new int[row][col];
		notPass = new boolean[row][col];
		back = null;
		makeNewDBImage();
		mode = 1-mode;
	}
	
	public boolean setBackGround(String file){
		try{
			back = new ImageIcon(getClass().getResource("./"+file)).getImage();
			backGroundName = file;
			return true;
		}catch(NullPointerException e){
			return false;
		}
	}
	
	public void draw(){
		if(back == null){
			dbg.setColor(Color.GRAY);
			dbg.fillRect(0,0,col*CHIP_SIZE,row*CHIP_SIZE);
		}else{
			dbg.drawImage(back,0,0,null);
		}
		for(int i = 0; i < row; i++){
			for(int j = 0; j < col; j++){
				if(mode == PASS){
					dbg.setColor(Color.WHITE);
					if(!notPass[i][j]){
						dbg.drawOval(j*CHIP_SIZE+8,i*CHIP_SIZE+8,16,16);
					}
				}
				int ix = (data[i][j]%CHIP_COL);
				int iy = (data[i][j]/CHIP_COL);
				if(ix == 0 && iy == 0) continue;
				dbg.drawImage(image,
					j*CHIP_SIZE,i*CHIP_SIZE,
					(j+1)*CHIP_SIZE, (i+1)*CHIP_SIZE,
					ix*CHIP_SIZE,iy*CHIP_SIZE,
					(ix+1)*CHIP_SIZE,(iy+1)*CHIP_SIZE,
					null);
			}
		}
	}
	
	public void setChip(){
		data[mouseY][mouseX] = cs.selectedChip;
		update();
	}
	
	public void changePass(){
		notPass[mouseY][mouseX] = setPass;
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
		if ((e.getModifiers() & MouseEvent.BUTTON1_MASK) != 0) {
			// 左クリック
			switch(mode){
			case GRAPHIC:
				setChip();
				break;
			case PASS:
				setPass = !notPass[mouseY][mouseX];
				changePass();
				break;
			}
		}
		if ((e.getModifiers() & MouseEvent.BUTTON3_MASK) != 0) {
			// 右クリック			
			switch(mode){
			case GRAPHIC:
				cs.selectChip(data[mouseY][mouseX]);
				break;
			case PASS:
				setPass = !notPass[mouseY][mouseX];
				changePass();
				break;
			}
		}
	}
	public void mouseDragged(MouseEvent e){
		if(!mouseInThis) return;
		mouseX = e.getX()/CHIP_SIZE;
		mouseY = e.getY()/CHIP_SIZE;
		if(mouseX < 0 || mouseX >= col) return;
		if(mouseY < 0 || mouseY >= row) return;
		if ((e.getModifiers() & MouseEvent.BUTTON1_MASK) != 0) {
			// 左クリック
			switch(mode){
			case GRAPHIC:
				setChip();
				break;
			case PASS:
				changePass();
				break;
			}
		}
		if ((e.getModifiers() & MouseEvent.BUTTON3_MASK) != 0) {
			// 右クリック
			switch(mode){
			case GRAPHIC:
				cs.selectChip(data[mouseY][mouseX]);
				break;
			case PASS:
				changePass();
				break;
			}
		}
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
