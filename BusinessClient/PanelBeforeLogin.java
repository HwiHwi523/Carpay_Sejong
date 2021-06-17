import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.*;
import javax.swing.border.*;

public class PanelBeforeLogin extends JPanel {
	FrameBusiness topFrame;
	
	JLabel warningLbl;
	
	JPanel miniPanel;
	
	JLabel idLbl, pwdLbl;
	JTextField idTf;
	JPasswordField pwdTf;
	JButton loginBtn;
	JButton regBtn, findIdPwdBtn;
	
	ActionListener btnListener;
	
	PanelBeforeLogin(FrameBusiness paraTopFrame)
	{
		topFrame = paraTopFrame;
		
		setLocation(0, 0);
		setSize(1000, 600);
		setLayout(null);
		setVisible(true);
		
		warningLbl = new JLabel();
		warningLbl.setForeground(Color.red);
		warningLbl.setLocation(360, 350);
		warningLbl.setSize(200, 30);
		warningLbl.setVisible(true);
		add(warningLbl);
		
		miniPanel = new JPanel();
		miniPanel.setBorder(new LineBorder(Color.black, 1, true));
		miniPanel.setLocation(360, 235);
		miniPanel.setSize(300, 120);
		miniPanel.setLayout(null);
		miniPanel.setVisible(true);
		
		idLbl = new JLabel("아이디");
		idLbl.setHorizontalAlignment(JLabel.CENTER);
		idLbl.setLocation(20, 20);
		idLbl.setSize(60, 25);
		idLbl.setVisible(true);
		miniPanel.add(idLbl);
		
		pwdLbl = new JLabel("비밀번호");
		pwdLbl.setHorizontalAlignment(JLabel.CENTER);
		pwdLbl.setLocation(20, 45);
		pwdLbl.setSize(60, 25);
		pwdLbl.setVisible(true);
		miniPanel.add(pwdLbl);
		
		idTf = new JTextField();
		idTf.setLocation(85, 20);
		idTf.setSize(120, 25);
		idTf.setVisible(true);
		miniPanel.add(idTf);
		
		pwdTf = new JPasswordField();
		pwdTf.setEchoChar('*');
		pwdTf.setLocation(85, 45);
		pwdTf.setSize(120, 25);
		pwdTf.setVisible(true);
		miniPanel.add(pwdTf);
		
		btnListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String order = ((JButton)e.getSource()).getText();
				
				if (order.equals("로그인"))
				{
					String id = idTf.getText(), pwd = pwdTf.getText();
					
					if (id.equals("") || pwd.equals(""))
					{
						warningLbl.setText("빈칸이 존재합니다.");
						return;
					}
					
					String result = topFrame.login(id, pwd);
					
					if (result.equals("로그인 되었습니다."))
						topFrame.changePanel("AfterLogin");
					else
						warningLbl.setText(result);	
				}
				
				else if (order.equals("회원가입"))
					topFrame.changePanel("RegMem");
					
				else if (order.equals("아이디/비밀번호 찾기"))
					topFrame.changePanel("FindIdPwd");
			}
		};
		
		loginBtn = new JButton("로그인");
		loginBtn.setLocation(210, 20);
		loginBtn.setSize(80, 55);
		loginBtn.addActionListener(btnListener);
		loginBtn.setVisible(true);
		miniPanel.add(loginBtn);
		
		regBtn = new JButton("회원가입");
		regBtn.setLocation(20, 80);
		regBtn.setSize(100, 20);
		regBtn.addActionListener(btnListener);
		regBtn.setVisible(true);
		miniPanel.add(regBtn);
		
		findIdPwdBtn = new JButton("아이디/비밀번호 찾기");
		findIdPwdBtn.setLocation(130, 80);
		findIdPwdBtn.setSize(160, 20);
		findIdPwdBtn.addActionListener(btnListener);
		findIdPwdBtn.setVisible(true);
		miniPanel.add(findIdPwdBtn);
		
		add(miniPanel);
	}
	
}
