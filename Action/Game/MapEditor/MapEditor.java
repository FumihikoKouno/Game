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
import javax.swing.KeyStroke;
import javax.swing.JScrollPane;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import java.awt.event.KeyEvent;
import java.awt.event.InputEvent;

public class MapEditor extends JFrame implements ActionListener{
	
	public ChipSelector cs;
	public StageViewer sv;
	
//	public int CHIP_SIZE = 32;
	
	public MapEditor() {
		setTitle("MapEditor");
		// create split
		JSplitPane split = new JSplitPane();
		
		// create menu
		JMenuBar menuBar = new JMenuBar();
		JMenu menuFile = new JMenu("File");
		JMenuItem save = new JMenuItem("Save");
		save.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK));
		save.addActionListener(this);
		JMenuItem exit = new JMenuItem("Exit");
		exit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, InputEvent.CTRL_DOWN_MASK));
		exit.addActionListener(this);
		menuFile.add(save);
		menuFile.add(exit);
		JMenu menuEdit = new JMenu("Edit");
		JMenuItem newMap = new JMenuItem("New Map");
		newMap.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_DOWN_MASK));
		newMap.addActionListener(this);
		JMenuItem openMap = new JMenuItem("Open Map");
		openMap.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_DOWN_MASK));
		openMap.addActionListener(this);
		JMenuItem setBack = new JMenuItem("Set BackGround");
		setBack.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_B, InputEvent.CTRL_DOWN_MASK));
		setBack.addActionListener(this);
		menuEdit.add(newMap);
		menuEdit.add(openMap);
		menuEdit.add(setBack);
		menuBar.add(menuFile);
		menuBar.add(menuEdit);
		
		// create ChipSelector
		cs = new ChipSelector();
//		cs.setMinimumSize(new Dimension(5*CHIP_SIZE,15*CHIP_SIZE));
		JScrollPane csScroll = new JScrollPane(cs);
		
		// create StageViewer
		sv = new StageViewer(cs);
//		sv.setMinimumSize(new Dimension(20*CHIP_SIZE,15*CHIP_SIZE));
		JScrollPane svScroll = new JScrollPane(sv);
		
		
		Container contentPane = getContentPane();
		split.setLeftComponent(svScroll);
		split.setRightComponent(csScroll);
		setJMenuBar(menuBar);
		contentPane.add(split,BorderLayout.CENTER);
		setSize(640,480);
//		pack();
	}
	
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
		if(selected == "Exit"){
			System.exit(0);
		}
		update();
	}
	
	public void update(){
		cs.update();
		sv.update();
	}
	
	public static void main(String[] args) {
		MapEditor frame = new MapEditor();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
}