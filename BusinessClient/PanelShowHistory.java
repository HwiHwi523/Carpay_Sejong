
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.DefaultTableModel;

public class PanelShowHistory extends JPanel {
	FrameBusiness topFrame;
	
	JLabel ymdLbl;
	JTextField ymdStartTf, ymdEndTf;
	JButton showBtn;
	
	JLabel fromToLbl;
	
	JScrollPane historySp;
	JTable historyTb;
	DefaultTableModel dtm;
	String[] column_name;
	
	PanelShowHistory(FrameBusiness paraTopFrame)
	{
		topFrame = paraTopFrame;

		setBorder(new TitledBorder(new LineBorder(Color.black, 1, true), "판매내역 및 매출"));
		setLocation(495, 50);
		setSize(475, 500);
		setLayout(null);
		setVisible(true);

		ymdLbl = new JLabel("날짜");
		ymdLbl.setHorizontalAlignment(JLabel.CENTER);
		ymdLbl.setLocation(10, 30);
		ymdLbl.setSize(60, 30);
		ymdLbl.setVisible(true);
		add(ymdLbl);
		
		ymdStartTf = new JTextField();
		ymdStartTf.setHorizontalAlignment(JLabel.CENTER);
		ymdStartTf.setLocation(75, 30);
		ymdStartTf.setSize(120, 30);
		ymdStartTf.setVisible(true);
		add(ymdStartTf);
		
		fromToLbl = new JLabel("~");
		fromToLbl.setHorizontalAlignment(JLabel.CENTER);
		fromToLbl.setLocation(200, 30);
		fromToLbl.setSize(30, 30);
		fromToLbl.setVisible(true);
		add(fromToLbl);
		
		ymdEndTf = new JTextField();
		ymdEndTf.setHorizontalAlignment(JLabel.CENTER);
		ymdEndTf.setLocation(235, 30);
		ymdEndTf.setSize(120, 30);
		ymdEndTf.setVisible(true);
		add(ymdEndTf);
		
		showBtn = new JButton("조회");
		showBtn.setLocation(380, 30);
		showBtn.setSize(60, 30);
		showBtn.setVisible(true);
		showBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String[][] data = topFrame.load_history(ymdStartTf.getText(), ymdEndTf.getText());
				
				dtm.setDataVector(data, column_name);
				historyTb.setModel(dtm);
				historySp.repaint();
			}
		});
		add(showBtn);
		
		column_name = new String[] { "승인번호", "카드사", "금액", "일시" };
		dtm = new DefaultTableModel();
		dtm.setColumnIdentifiers(column_name);
		historyTb = new JTable(dtm);
		historySp = new JScrollPane(historyTb);
		historySp.setLocation(10, 65);
		historySp.setSize(455, 425);
		historySp.setVisible(true);
		add(historySp);
	}
	
}
