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
	public static final int WIDTH = 640;
	public static final int HEIGHT = 480;
	private final Image image = new ImageIcon(getClass().getResource("./mapImage.gif")).getImage();
	private final int CHIP_SIZE = 32;
	private final int CHIP_COL = 16;
	
	public static final int GRAPHIC = 0;
	public static final int PASS = 1;
	public static final int SPRITE = 2;
	
	public int mode;
	
	private boolean mouseInThis;
	private int mouseX;
	private int mouseY;
	private int row;
	private int col;
	private int[][] data;
	private int x;
	private int y;
	
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
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		update();
	}
	
	public void update() {
		while(dbImage == null){
			dbImage = createImage(WIDTH,HEIGHT);
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
		x = 0;
		y = 0;
		this.row = row;
		this.col = col;
		data = new int[row][col];
	}
	
	public void setBackGround(String file){
		back = new ImageIcon(getClass().getResource("./"+file)).getImage();
	}
	
	public void draw(){
		if(back != null){
			dbg.drawImage(back,0,0,null);
		}
		for(int i = 0; i < row; i++){
			for(int j = 0; j < col; j++){
				int ix = (data[y+i][x+j]%CHIP_COL);
				int iy = (data[y+i][x+j]/CHIP_COL);
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
		data[y+mouseY][x+mouseX] = cs.selectedChip;
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
		if ((e.getModifiers() & MouseEvent.BUTTON1_MASK) != 0) {
			// 左クリック
			setChip();
		}
		if ((e.getModifiers() & MouseEvent.BUTTON3_MASK) != 0) {
			// 右クリック
			cs.selectChip(data[y+mouseY][x+mouseX]);
		}
	}
	public void mouseDragged(MouseEvent e){
		if(!mouseInThis) return;
		mouseX = e.getX()/CHIP_SIZE;
		mouseY = e.getY()/CHIP_SIZE;
		if ((e.getModifiers() & MouseEvent.BUTTON1_MASK) != 0) {
			// 左クリック
			setChip();
		}
		if ((e.getModifiers() & MouseEvent.BUTTON3_MASK) != 0) {
			// 右クリック
			cs.selectChip(data[y+mouseY][x+mouseX]);
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
