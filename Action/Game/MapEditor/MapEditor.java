import java.io.File;
import java.awt.Container;
import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSplitPane;
import javax.swing.JOptionPane;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.ButtonGroup;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.KeyStroke;
import javax.swing.JScrollPane;
import javax.swing.JScrollBar;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.WindowListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowAdapter;

import java.awt.event.KeyEvent;
import java.awt.event.InputEvent;

public class MapEditor extends JFrame implements ActionListener, AdjustmentListener{
	
	public ChipSelector cs;
	public StageViewer sv;
	public MapIO mio;
	
	public int id = -1;
	
	private JLabel axis;
	
	public MapEditor() {
		setTitle("MapEditor");
		
		// close listener
		addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e){
				if(deleteConfirm()) System.exit(0);
			}
		});
		
		// create menu
		JMenuBar menuBar = new JMenuBar();
		
		JMenu menuFile = new JMenu("File");
		JMenuItem newMap = new JMenuItem("New Map");
		newMap.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_DOWN_MASK));
		newMap.addActionListener(this);
		JMenuItem openMap = new JMenuItem("Open Map");
		openMap.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_DOWN_MASK));
		openMap.addActionListener(this);
		JMenuItem save = new JMenuItem("Save");
		save.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK));
		save.addActionListener(this);
		JMenuItem saveAs = new JMenuItem("Save As");
		saveAs.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.SHIFT_DOWN_MASK));
		saveAs.addActionListener(this);
		JMenuItem exit = new JMenuItem("Exit");
		exit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, InputEvent.CTRL_DOWN_MASK));
		exit.addActionListener(this);
		menuFile.add(newMap);
		menuFile.add(openMap);
		menuFile.add(save);
		menuFile.add(saveAs);
		menuFile.add(exit);
		
		JMenu menuEdit = new JMenu("Edit");
		JMenuItem setBack = new JMenuItem("Set BackGround");
		setBack.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_B, InputEvent.CTRL_DOWN_MASK));
		setBack.addActionListener(this);
		JMenuItem setG = new JMenuItem("Set Gravity");
		setG.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_G, InputEvent.CTRL_DOWN_MASK));
		setG.addActionListener(this);
		JMenu mode = new JMenu("Mode");
		JRadioButtonMenuItem graphical = new JRadioButtonMenuItem("Graphic Mode");
		graphical.addActionListener(this);
		JRadioButtonMenuItem pass = new JRadioButtonMenuItem("Pass Mode");
		pass.addActionListener(this);
		graphical.setSelected(true);
		ButtonGroup modeGroup = new ButtonGroup();
		modeGroup.add(graphical);
		modeGroup.add(pass);
		mode.add(graphical);
		mode.add(pass);
		menuEdit.add(mode);
		menuEdit.add(setBack);
		menuEdit.add(setG);
		
		menuBar.add(menuFile);
		menuBar.add(menuEdit);
		
		setJMenuBar(menuBar);
		
		// create ChipSelector
		cs = new ChipSelector(this);
		JScrollPane csScroll = new JScrollPane(cs);
		csScroll.getVerticalScrollBar().addAdjustmentListener(this);
		csScroll.getHorizontalScrollBar().addAdjustmentListener(this);
		
		// create StageViewer
		sv = new StageViewer(cs,this);
		JScrollPane svScroll = new JScrollPane(sv);
		svScroll.getVerticalScrollBar().addAdjustmentListener(this);
		svScroll.getHorizontalScrollBar().addAdjustmentListener(this);

		// create MapIO
		mio = new MapIO(sv);
		
		// create split
		JSplitPane split = new JSplitPane();
		split.setLeftComponent(svScroll);
		split.setRightComponent(csScroll);
		split.setOneTouchExpandable(true);
		
		Container contentPane = getContentPane();
		contentPane.add(split,BorderLayout.CENTER);
		axis = new JLabel("("+sv.mouseX+","+sv.mouseY+")");
		add(axis,BorderLayout.SOUTH);
		setSize(640,480);
	}
	
	public boolean deleteConfirm(){
		if(sv.updated){
			JLabel label = new JLabel("This map data is not saved yet. Really delete this data?");
			int confirm = JOptionPane.showConfirmDialog(this,label);
			if(confirm == JOptionPane.YES_OPTION) return true;
		}else{
			return true;
		}
		return false;
	}
	
	public void overSave(){
		if(!mio.output(id)){
			JLabel label = new JLabel("Cannot save Map"+ id);
			JOptionPane.showMessageDialog(this,label);
		}
	}
	
	public void newSave(){
		int beforeID = id;
		boolean isNum = false;
		while(!isNum){
			String value = JOptionPane.showInputDialog(this, "Input new map id (id >= 0)");
			if(value == null || value == "") return;
			try{
				id = Integer.parseInt(value);
				if(id < 0){
					JLabel label = new JLabel("Map id needs to be 0 or over");
					JOptionPane.showMessageDialog(this,label);
				}else{
					File newMap = new File("./Map"+id+".dat");
					if(newMap.exists()){
						JLabel label = new JLabel("ID:"+id+" map already exists. Will you overwrite ID:"+id+"map?");
						int saveConfirm = JOptionPane.showConfirmDialog(this,label);
						if(saveConfirm == JOptionPane.YES_OPTION){
							if(!mio.output(id)){
								JOptionPane.showMessageDialog(this,new JLabel("Cannot save Map"+ id));
								id = beforeID;
							}
							return;
						}
					}else{
						if(!mio.output(id)){
							JOptionPane.showMessageDialog(this,new JLabel("Cannot save Map"+ id));
							id = beforeID;
						}
						return;
					}
					isNum = true;
				}
			}catch(NumberFormatException ex){
				JLabel label = new JLabel(value + " is not integer. Please input integer number");
				JOptionPane.showMessageDialog(this,label);
			}
		}
		id = beforeID;
	}
	
	
	/**
	 * menu selected
	 */
	public void actionPerformed(ActionEvent e){
		String selected = e.getActionCommand();
		
		if(selected == "New Map"){
			int nmx = 0;
			int nmy = 0;
			boolean isNum = false;
			while(!isNum){
				String value = JOptionPane.showInputDialog(this, "Input map width (20 <= width)");
				if(value == null || value == "") return;
				try{
					nmx = Integer.parseInt(value);
					if(nmx < 20){
						JLabel label = new JLabel("Map width needs to be 20 or over");
						JOptionPane.showMessageDialog(this,label);
					}else{
						isNum = true;
					}
				}catch(NumberFormatException ex){
					JLabel label = new JLabel(value + " is not integer. Please input integer number");
					JOptionPane.showMessageDialog(this,label);
				}
			}
			isNum = false;
			while(!isNum){
				String value = JOptionPane.showInputDialog(this, "Input map height (15 <= height)");
				if(value == null || value == "") return;
				try{
					nmy = Integer.parseInt(value);
					if(nmy < 15){
						JLabel label = new JLabel("Map height needs to be 15 or over");
						JOptionPane.showMessageDialog(this,label);
					}else{
						isNum = true;
					}
				}catch(NumberFormatException ex){
					JLabel label = new JLabel(value + " is not integer. Please input integer number");
					JOptionPane.showMessageDialog(this,label);
				}
			}
			sv.createNewMap(nmx,nmy);
		}
		if(selected == "Open Map"){
			if(deleteConfirm()){
				int beforeID = id;
				boolean isNum = false;
				while(!isNum){
					String value = JOptionPane.showInputDialog(this, "Input read map id (id >= 0)");
					if(value == null || value == "") return;
					try{
						id = Integer.parseInt(value);
						if(id < 0){
							JLabel label = new JLabel("Map id needs to be 0 or over");
							JOptionPane.showMessageDialog(this,label);
						}else{
							File newMap = new File("./Map"+id+".dat");
							if(newMap.exists()){
								if(!mio.input(id)){
									JOptionPane.showMessageDialog(this,new JLabel("Cannot save Map"+ id));
									id = beforeID;
								}
							}else{
								JOptionPane.showMessageDialog(this,new JLabel("Cannot found Map:"+ id));
								id = beforeID;
							}
							isNum = true;
						}
					}catch(NumberFormatException ex){
						JLabel label = new JLabel(value + " is not integer. Please input integer number");
						JOptionPane.showMessageDialog(this,label);
					}
				}
			}
		}
		if(selected == "Save"){
			if(id < 0) newSave();
			else overSave();
		}
		if(selected == "Save As"){
			newSave();
		}
		if(selected == "Set BackGround"){
			JFileChooser fc = new JFileChooser("./");
			int select = fc.showOpenDialog(this);
			if(select == JFileChooser.APPROVE_OPTION){
				File file = fc.getSelectedFile();
				String fileName = fc.getName(file);
				if(fileName.endsWith(".gif") || fileName.endsWith(".jpg")){
					if(!sv.setBackGround(fileName)){
						JLabel label = new JLabel("Cannot open " + fileName);
						JOptionPane.showMessageDialog(this,label);
					}
				}else{
					JLabel label = new JLabel(fileName + " is unsupported file");
					JOptionPane.showMessageDialog(this,label);
				}
			}
		}
		if(selected == "Set Gravity"){
			int newG = 0;
			boolean isNum = false;
			while(!isNum){
				String value = JOptionPane.showInputDialog(this, "Input map gravity (1 <= gravity)  :  now Gravity = " + sv.gravity);
				if(value == null || value == "") return;
				try{
					newG = Integer.parseInt(value);
					if(newG < 1){
						JLabel label = new JLabel("Map gravity needs to be 1 or over");
						JOptionPane.showMessageDialog(this,label);
					}else{
						isNum = true;
					}
				}catch(NumberFormatException ex){
					JLabel label = new JLabel(value + " is not integer. Please input integer number");
					JOptionPane.showMessageDialog(this,label);
				}
			}
			sv.setGravity(newG);
		}
		if(selected == "Graphic Mode"){
			sv.mode = StageViewer.GRAPHIC;
		}
		if(selected == "Pass Mode"){
			sv.mode = StageViewer.PASS;
		}
		if(selected == "Exit"){
			if(deleteConfirm()){
				System.exit(0);
			}
		}
		update();
	}
	
	public void adjustmentValueChanged(AdjustmentEvent e){
		update();
	}
	
	public void update(){
		if(id >= 0){
			if(sv.updated){
				setTitle("MapEditor - *Map "+id);
			}else{
				setTitle("MapEditor - Map "+id);
			}
		}else{
			if(sv.updated){
				setTitle("MapEditor*");
			}else{
				setTitle("MapEditor");
			}
		}
		axis.setText("("+sv.mouseX+","+sv.mouseY+")");
		cs.update();
		sv.update();
	}
	
	public static void main(String[] args) {
		MapEditor frame = new MapEditor();
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.setVisible(true);
	}
}