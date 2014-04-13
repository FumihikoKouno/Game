package communication;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.sun.prism.Graphics;

public class CommunicationSettingPanel extends JPanel{
	public SocketHandler sh;
	public JTextField hostNameArea = new JTextField();
	
	public CommunicationSettingPanel(){
		init();
	}
	
	public void init(){
		JButton serverMode = new JButton("Server Mode");
		serverMode.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				sh = new SocketHandler(SocketHandler.Mode.SERVER,null);
			}
		});
		JButton cliantMode = new JButton("Cliant Mode");
		cliantMode.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				String str = hostNameArea.getText();
				if(!(str==null && str.equals(""))){
					sh = new SocketHandler(SocketHandler.Mode.CLIANT,str);
				}
			}
		});
		
		setLayout(new BorderLayout());
		JPanel cliantPanel = new JPanel();
		cliantPanel.setLayout(new GridLayout(1,3));
		cliantPanel.add(new JLabel("Host Name"));
		cliantPanel.add(hostNameArea);
		cliantPanel.add(cliantMode);
		
		add(serverMode,BorderLayout.NORTH);
		add(new JPanel(),BorderLayout.CENTER);
		add(cliantPanel,BorderLayout.SOUTH);
	}
	
	public SocketHandler getSocketHandler(){
		return sh;
	}
	
}
