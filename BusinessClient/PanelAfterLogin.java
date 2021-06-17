import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.filechooser.FileNameExtensionFilter;

public class PanelAfterLogin extends JPanel {
	FrameBusiness topFrame;
	
	JLabel businessName;
	JButton mngInfoBtn;
	JButton logoutBtn;
	
	PanelSell sellRecogPanel;
	JFileChooser jfc;
	
	PanelShowHistory showHistoryPanel;
	
	ActionListener btnListener;
	
	PanelAfterLogin(FrameBusiness paraTopFrame)
	{
		topFrame = paraTopFrame;
		
		setLocation(0, 0);
		setSize(1000, 600);
		setLayout(null);
		setVisible(true);
		
		businessName = new JLabel(topFrame.bsName); // 데베 연동 후 topFrame에서 가져오기
		businessName.setBorder(new LineBorder(Color.black, 1, true));
		businessName.setHorizontalAlignment(JLabel.CENTER);
		businessName.setLocation(10, 10);
		businessName.setSize(200, 30);
		businessName.setVisible(true);
		add(businessName);
		
		btnListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String order = ((JButton)e.getSource()).getText();
				
				if (order.equals("회원 정보 관리"))
					topFrame.changePanel("MngMemInfo");
				
				else if (order.equals("로그아웃"))
					topFrame.logout();
			}
		};
		
		mngInfoBtn = new JButton("회원 정보 관리");
		mngInfoBtn.setLocation(220, 10);
		mngInfoBtn.setSize(120, 30);
		mngInfoBtn.addActionListener(btnListener);
		mngInfoBtn.setVisible(true);
		add(mngInfoBtn);
		
		logoutBtn = new JButton("로그아웃");
		logoutBtn.setLocation(345, 10);
		logoutBtn.setSize(100, 30);
		logoutBtn.addActionListener(btnListener);
		logoutBtn.setVisible(true);
		add(logoutBtn);
		
		sellRecogPanel = new PanelSell(topFrame);
		showHistoryPanel = new PanelShowHistory(topFrame);
		
		add(sellRecogPanel);
		add(showHistoryPanel);
	}
	
}
