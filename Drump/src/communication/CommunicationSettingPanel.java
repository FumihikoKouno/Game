package communication;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.sun.prism.Graphics;

public class CommunicationSettingPanel extends JPanel{
	public SocketHandler sh;
	public JTextField textField = new JTextField();
	JDialog hostDialog = new JDialog();
	
	public CommunicationSettingPanel(){
		init();
	}
	
	public void init(){
		hostDialog.setLayout(new BorderLayout());
		JButton ok = new JButton("OK");
		ok.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				sh = new SocketHandler(SocketHandler.Mode.CLIANT,textField.getText());
				hostDialog.setVisible(false);
			}
		});
		hostDialog.add(new JLabel("Input host name"), BorderLayout.NORTH);
		hostDialog.add(textField, BorderLayout.CENTER);
		hostDialog.add(ok, BorderLayout.SOUTH);
		hostDialog.pack();
		
		JButton serverMode = new JButton("Server Mode");
		serverMode.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				sh = new SocketHandler(SocketHandler.Mode.SERVER,null);
			}
		});
		JButton cliantMode = new JButton("Cliant Mode");
		cliantMode.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				hostDialog.setVisible(true);
			}
		});
		
		setLayout(new BorderLayout());
		add(serverMode,BorderLayout.NORTH);
		add(new JPanel(),BorderLayout.CENTER);
		add(cliantMode,BorderLayout.SOUTH);
	}
	
	public SocketHandler getSocketHandler(){
		return sh;
	}
	
}
