import java.awt.Color;

import javax.swing.*;
import javax.swing.border.LineBorder;

import java.awt.event.*;

public class PanelRegisterMember extends JPanel {
	FrameBusiness topFrame;
	
	JPanel miniPanel;
	
	JLabel idWarnLbl, pwdWarnLbl, warningLbl;
	
	JLabel nameLbl, pnumLbl, idLbl, pwdLbl, chkPwdLbl, businessNameLbl, cusNumLbl;
	JTextField nameTf, pnumTf, idTf, businessNameTf, cusNumTf;
	JPasswordField pwdTf, chkPwdTf;
	
	JButton regBtn, cancelBtn;
	
	ActionListener btnListener;
	
	int vertical_pad = 10, horizon_pad = 5;
	
	PanelRegisterMember(FrameBusiness paraTopFrame)
	{
		topFrame = paraTopFrame;
		
		setLocation(0, 0);
		setSize(1000, 600);
		setVisible(true);
		setLayout(null);
		
		miniPanel = new JPanel();
		miniPanel.setBorder(new LineBorder(Color.black, 1, true));
		miniPanel.setLocation(250, 125);
		miniPanel.setSize(500, 350);
		miniPanel.setLayout(null);
		miniPanel.setVisible(true);
		
		idLbl = new JLabel("아이디");
		idLbl.setHorizontalAlignment(JLabel.CENTER);
		idLbl.setLocation(10, 10);
		idLbl.setSize(100, 30);
		idLbl.setVisible(true);
		miniPanel.add(idLbl);
		
		idTf = new JTextField();
		idTf.setLocation(idLbl.getLocation().x + idLbl.getWidth() + horizon_pad, idLbl.getLocation().y);
		idTf.setSize(120, 30);
		idTf.setVisible(true);
		miniPanel.add(idTf);
		
		idWarnLbl = new JLabel();
		idWarnLbl.setForeground(Color.red);
		idWarnLbl.setLocation(idTf.getLocation().x + idTf.getWidth() + horizon_pad, idTf.getLocation().y);
		idWarnLbl.setSize(200, 30);
		idWarnLbl.setVisible(true);
		miniPanel.add(idWarnLbl);
		
		pwdLbl = new JLabel("비밀번호");
		pwdLbl.setHorizontalAlignment(JLabel.CENTER);
		pwdLbl.setLocation(idLbl.getLocation().x, idLbl.getLocation().y + idLbl.getHeight() + vertical_pad);
		pwdLbl.setSize(100, 30);
		pwdLbl.setVisible(true);
		miniPanel.add(pwdLbl);
		
		pwdTf = new JPasswordField();
		pwdTf.setEchoChar('*');
		pwdTf.setLocation(pwdLbl.getLocation().x + pwdLbl.getWidth() + horizon_pad, pwdLbl.getLocation().y);
		pwdTf.setSize(120, 30);
		pwdTf.setVisible(true);
		miniPanel.add(pwdTf);

		chkPwdLbl = new JLabel("비밀번호 확인");
		chkPwdLbl.setHorizontalAlignment(JLabel.CENTER);
		chkPwdLbl.setLocation(pwdLbl.getLocation().x, pwdLbl.getLocation().y + pwdLbl.getHeight() + vertical_pad);
		chkPwdLbl.setSize(100, 30);
		chkPwdLbl.setVisible(true);
		miniPanel.add(chkPwdLbl);
		
		chkPwdTf = new JPasswordField();
		chkPwdTf.setEchoChar('*');
		chkPwdTf.setLocation(chkPwdLbl.getLocation().x + chkPwdLbl.getWidth() + horizon_pad, chkPwdLbl.getLocation().y);
		chkPwdTf.setSize(120, 30);
		chkPwdTf.setVisible(true);
		miniPanel.add(chkPwdTf);

		pwdWarnLbl = new JLabel();
		pwdWarnLbl.setForeground(Color.red);
		pwdWarnLbl.setLocation(chkPwdTf.getLocation().x + chkPwdTf.getWidth() + horizon_pad, chkPwdTf.getLocation().y);
		pwdWarnLbl.setSize(200, 30);
		pwdWarnLbl.setVisible(true);
		miniPanel.add(pwdWarnLbl);
		
		nameLbl = new JLabel("이름");
		nameLbl.setHorizontalAlignment(JLabel.CENTER);
		nameLbl.setLocation(chkPwdLbl.getLocation().x, chkPwdLbl.getLocation().y + chkPwdLbl.getHeight() + vertical_pad);
		nameLbl.setSize(100, 30);
		nameLbl.setVisible(true);
		miniPanel.add(nameLbl);
		
		nameTf = new JTextField();
		nameTf.setLocation(nameLbl.getLocation().x + nameLbl.getWidth() + horizon_pad, nameLbl.getLocation().y);
		nameTf.setSize(120, 30);
		nameTf.setVisible(true);
		miniPanel.add(nameTf);
		
		pnumLbl = new JLabel("전화번호");
		pnumLbl.setHorizontalAlignment(JLabel.CENTER);
		pnumLbl.setLocation(nameLbl.getLocation().x, nameLbl.getLocation().y + nameLbl.getHeight() + vertical_pad);
		pnumLbl.setSize(100, 30);
		pnumLbl.setVisible(true);
		miniPanel.add(pnumLbl);

		pnumTf = new JTextField();
		pnumTf.setLocation(pnumLbl.getLocation().x + pnumLbl.getWidth() + horizon_pad, pnumLbl.getLocation().y);
		pnumTf.setSize(120, 30);
		pnumTf.setVisible(true);
		miniPanel.add(pnumTf);

		businessNameLbl = new JLabel("업체명");
		businessNameLbl.setHorizontalAlignment(JLabel.CENTER);
		businessNameLbl.setLocation(pnumLbl.getLocation().x, pnumLbl.getLocation().y + pnumLbl.getHeight() + vertical_pad);
		businessNameLbl.setSize(100, 30);
		businessNameLbl.setVisible(true);
		miniPanel.add(businessNameLbl);

		businessNameTf = new JTextField();
		businessNameTf.setLocation(businessNameLbl.getLocation().x + businessNameLbl.getWidth() + horizon_pad, businessNameLbl.getLocation().y);
		businessNameTf.setSize(200, 30);
		businessNameTf.setVisible(true);
		miniPanel.add(businessNameTf);

		cusNumLbl = new JLabel("사업자등록번호");
		cusNumLbl.setHorizontalAlignment(JLabel.CENTER);
		cusNumLbl.setLocation(businessNameLbl.getLocation().x, businessNameLbl.getLocation().y + businessNameLbl.getHeight() + vertical_pad);
		cusNumLbl.setSize(100, 30);
		cusNumLbl.setVisible(true);
		miniPanel.add(cusNumLbl);

		cusNumTf = new JTextField();
		cusNumTf.setLocation(cusNumLbl.getLocation().x + cusNumLbl.getWidth() + horizon_pad, cusNumLbl.getLocation().y);
		cusNumTf.setSize(200, 30);
		cusNumTf.setVisible(true);
		miniPanel.add(cusNumTf);
		
		btnListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String order = ((JButton)e.getSource()).getText();
				
				if (order.equals("가입"))
				{
					String[] info = new String[] { idTf.getText(), pwdTf.getText(), chkPwdTf.getText(), nameTf.getText(), pnumTf.getText(), businessNameTf.getText(), cusNumTf.getText() };
					
					// 빈칸 유무 확인
					boolean emptyExist = false;
					for (String text : info)
					{
						if (text.equals(""))
						{
							warningLbl.setText("빈칸이 존재합니다.");
							return;
						}
					}
					
					String reg_result = topFrame.registerMemberInfo(info);
					
					if (reg_result.equals("회원가입이 완료되었습니다.")) // 성공
					{
						topFrame.changePanel("BeforeLogin");
					}
					else // 실패
					{
						warningLbl.setText(reg_result);
					}
				}
				
				else if (order.equals("취소"))
				{
					topFrame.changePanel("BeforeLogin");
				}
			}
		};
		
		regBtn = new JButton("가입");
		regBtn.setLocation(165, cusNumLbl.getLocation().y + cusNumLbl.getHeight() + 15);
		regBtn.setSize(80, 30);
		regBtn.setVisible(true);
		regBtn.addActionListener(btnListener);
		miniPanel.add(regBtn);
		
		cancelBtn = new JButton("취소");
		cancelBtn.setLocation(regBtn.getLocation().x + regBtn.getWidth() + 15, regBtn.getLocation().y);
		cancelBtn.setSize(80, 30);
		cancelBtn.setVisible(true);
		cancelBtn.addActionListener(btnListener);
		miniPanel.add(cancelBtn);
		
		add(miniPanel);
		
		warningLbl = new JLabel("");
		warningLbl.setForeground(Color.red);
		warningLbl.setLocation(miniPanel.getLocation().x, miniPanel.getLocation().y + miniPanel.getHeight());
		warningLbl.setSize(500, 30);
		warningLbl.setVisible(true);
		add(warningLbl);
	}
	
}
