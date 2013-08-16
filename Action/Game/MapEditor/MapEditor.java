import java.awt.Container;
import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JSplitPane;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import java.awt.event.KeyEvent;

public class MapEditor extends JFrame implements ActionListener{
	
	ChipSelector cs;
	StageViewer sv;
	public MapEditor() {
		JMenuBar menuBar = new JMenuBar();
		JMenu menuFile = new JMenu("File");
		JMenuItem save = new JMenuItem("Save (S)");
	  save.setMnemonic(KeyEvent.VK_S);
		save.addActionListener(this);
		JMenuItem exit = new JMenuItem("Exit (E)");
		exit.setMnemonic(KeyEvent.VK_E);
		exit.addActionListener(this);
		menuFile.add(save);
		menuFile.add(exit);
		JMenu menuEdit = new JMenu("Edit");
		JMenuItem newMap = new JMenuItem("New Map (N)");
		newMap.setMnemonic(KeyEvent.VK_N);
		newMap.addActionListener(this);
		JMenuItem openMap = new JMenuItem("Open Map (O)");
		openMap.setMnemonic(KeyEvent.VK_O);
		openMap.addActionListener(this);
		JMenuItem setBack = new JMenuItem("Set BackGround (B)");
		setBack.setMnemonic(KeyEvent.VK_B);
		setBack.addActionListener(this);
		menuEdit.add(newMap);
		menuEdit.add(openMap);
		menuEdit.add(setBack);
		menuBar.add(menuFile);
		menuBar.add(menuEdit);
		JSplitPane split = new JSplitPane();
		setTitle("MapEditor");
		cs = new ChipSelector();
		cs.setMinimumSize(new Dimension(ChipSelector.WIDTH,ChipSelector.HEIGHT));
		sv = new StageViewer(cs);
		sv.setMinimumSize(new Dimension(StageViewer.WIDTH,StageViewer.HEIGHT));
		Container contentPane = getContentPane();
		split.setLeftComponent(sv);
		split.setRightComponent(cs);
		setJMenuBar(menuBar);
		contentPane.add(split,BorderLayout.CENTER);
		pack();
	}
	
	public void actionPerformed(ActionEvent e){
		String selected = e.getActionCommand();
		if(selected == "New Map (N)"){
			sv.createNewMap(20,15);
			sv.update();
		}
		if(selected == "Exit (E)"){
			System.exit(0);
		}
		sv.update();
		cs.update();
	}
	
	public static void main(String[] args) {
		MapEditor frame = new MapEditor();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
}