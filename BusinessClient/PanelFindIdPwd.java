import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

public class PanelFindIdPwd extends JPanel {
	FrameBusiness topFrame;
	
	JButton cancelBtn;
	
	PanelFindIdPwd(FrameBusiness paraTopFrame)
	{
		topFrame = paraTopFrame;
		
		setLocation(0, 0);
		setSize(1000, 600);
		setVisible(true);
		setLayout(null);
		
		add(new PanelFindId());
		add(new PanelFindPwd());
		
		cancelBtn = new JButton("이전");
		cancelBtn.setLocation(470, 420);
		cancelBtn.setSize(60, 30);
		cancelBtn.setVisible(true);
		cancelBtn.addActionListener(new ActionListener() {	
			@Override
			public void actionPerformed(ActionEvent e) {
				topFrame.changePanel("BeforeLogin");
			}
		});
		add(cancelBtn);
	}
	
	class PanelFindId extends JPanel {
		JLabel nameLbl, pnumLbl;
		JTextField nameTf, pnumTf;
		
		JButton findBtn;
		
		JLabel msgLbl;
		
		PanelFindId()
		{
			setBorder(new TitledBorder(new LineBorder(Color.black, 1, true), "아이디 찾기"));
			setLocation(100, 120);
			setSize(400, 300);
			setLayout(null);
			setVisible(true);
			
			nameLbl = new JLabel("이름");
			nameLbl.setHorizontalAlignment(JLabel.CENTER);
			nameLbl.setLocation(10, 50);
			nameLbl.setSize(100, 30);;
			nameLbl.setVisible(true);
			add(nameLbl);
			
			nameTf = new JTextField();
			nameTf.setLocation(115, 50);
			nameTf.setSize(120, 30);
			nameTf.setVisible(true);
			add(nameTf);
			
			pnumLbl = new JLabel("전화번호");
			pnumLbl.setHorizontalAlignment(JLabel.CENTER);
			pnumLbl.setLocation(10, 90);
			pnumLbl.setSize(100, 30);
			pnumLbl.setVisible(true);
			add(pnumLbl);
			
			pnumTf = new JTextField();
			pnumTf.setLocation(115, 90);
			pnumTf.setSize(120, 30);
			pnumTf.setVisible(true);
			add(pnumTf);
			
			findBtn = new JButton("찾기");
			findBtn.setLocation(33, 130);
			findBtn.setSize(60, 30);
			findBtn.setVisible(true);
			findBtn.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					msgLbl.setText(topFrame.findId(nameTf.getText(), pnumTf.getText()));
				}
			});
			add(findBtn);
			
			msgLbl = new JLabel();
			msgLbl.setLocation(20, 260);
			msgLbl.setSize(380, 30);
			msgLbl.setVisible(true);
			add(msgLbl);
		}
		
	}
	class PanelFindPwd extends JPanel {
		JLabel idLbl, nameLbl, pnumLbl;
		JTextField idTf, nameTf, pnumTf;
		
		JButton findBtn;
		
		JLabel msgLbl;
		
		PanelFindPwd()
		{
			setBorder(new TitledBorder(new LineBorder(Color.black, 1, true), "비밀번호 찾기"));
			setLocation(500, 120);
			setSize(400, 300);
			setLayout(null);
			setVisible(true);
			
			idLbl = new JLabel("아이디");
			idLbl.setHorizontalAlignment(JLabel.CENTER);
			idLbl.setLocation(10, 50);
			idLbl.setSize(100, 30);;
			idLbl.setVisible(true);
			add(idLbl);
			
			idTf = new JTextField();
			idTf.setLocation(115, 50);
			idTf.setSize(120, 30);
			idTf.setVisible(true);
			add(idTf);
			
			nameLbl = new JLabel("이름");
			nameLbl.setHorizontalAlignment(JLabel.CENTER);
			nameLbl.setLocation(10, 90);
			nameLbl.setSize(100, 30);;
			nameLbl.setVisible(true);
			add(nameLbl);
			
			nameTf = new JTextField();
			nameTf.setLocation(115, 90);
			nameTf.setSize(120, 30);
			nameTf.setVisible(true);
			add(nameTf);
			
			pnumLbl = new JLabel("전화번호");
			pnumLbl.setHorizontalAlignment(JLabel.CENTER);
			pnumLbl.setLocation(10, 130);
			pnumLbl.setSize(100, 30);
			pnumLbl.setVisible(true);
			add(pnumLbl);
			
			pnumTf = new JTextField();
			pnumTf.setLocation(115, 130);
			pnumTf.setSize(120, 30);
			pnumTf.setVisible(true);
			add(pnumTf);
			
			findBtn = new JButton("찾기");
			findBtn.setLocation(33, 170);
			findBtn.setSize(60, 30);
			findBtn.setVisible(true);
			findBtn.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					msgLbl.setText(topFrame.findPwd(idTf.getText(), nameTf.getText(), pnumTf.getText()));
				}
			});
			add(findBtn);
			
			msgLbl = new JLabel();
			msgLbl.setLocation(20, 260);
			msgLbl.setSize(380, 30);
			msgLbl.setVisible(true);
			add(msgLbl);
		}
		
	}
}
