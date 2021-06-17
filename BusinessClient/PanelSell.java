import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.*;

public class PanelSell extends JPanel {
	FrameBusiness topFrame;
	
	JLabel imgLbl;

	JButton openBtn;
	JLabel pathLbl;
	
	JLabel carNumLbl;
	JTextField carNumTf;
	JButton recogBtn;
	
	JLabel priceLbl;
	JTextField priceTf;
	JButton transmitBtn;
	
	JLabel warningLbl;
	
	ActionListener btnListener;
	
	JFileChooser jfc;

	PanelSell(FrameBusiness paraTopFrame)
	{
		topFrame = paraTopFrame;

		setBorder(new TitledBorder(new LineBorder(Color.black, 1, true), "판매"));
		setLocation(10, 50);
		setSize(475, 500);
		setLayout(null);
		setVisible(true);
		
		jfc = new JFileChooser();
		jfc.setMultiSelectionEnabled(false);
		jfc.setCurrentDirectory(new File("."));
		
		btnListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String order = ((JButton)e.getSource()).getText();
				
				if (order.equals("열기"))
				{
					if (jfc.showOpenDialog(topFrame) == jfc.APPROVE_OPTION)
					{
						pathLbl.setText(jfc.getSelectedFile().toString());

						load_image(pathLbl.getText());
					}
				}
				
				else if (order.equals("인식"))
				{
					try {
						String result = execPython(pathLbl.getText());
						
						System.out.println("Result : " + result);
						
						carNumTf.setText(result);
						
						load_image("cropped_hr_image.jpg");
					} catch(Exception e1) {
						System.out.println(e1.getMessage());
					}
				}
				
				else if (order.equals("전송"))
				{
					String result = topFrame.transmit_payment(carNumTf.getText(), priceTf.getText());
					
					warningLbl.setText(result);
				}
			}
		};
		
		imgLbl = new JLabel();
		imgLbl.setHorizontalAlignment(JLabel.CENTER);
		imgLbl.setBorder(new LineBorder(Color.black, 1, false));
		imgLbl.setLocation(15, 20);
		imgLbl.setSize(445, 255);
		imgLbl.setVisible(true);
		add(imgLbl);
		
		openBtn = new JButton("열기");
		openBtn.setLocation(15, 280);
		openBtn.setSize(60, 20);
		openBtn.addActionListener(btnListener);
		openBtn.setVisible(true);
		add(openBtn);
		
		pathLbl = new JLabel();
		pathLbl.setLocation(80, 280);
		pathLbl.setSize(370, 20);
		pathLbl.setVisible(true);
		add(pathLbl);
		
		carNumLbl = new JLabel("차량번호");
		carNumLbl.setHorizontalAlignment(JLabel.CENTER);
		carNumLbl.setLocation(15, 325);
		carNumLbl.setSize(60, 30);
		carNumLbl.setVisible(true);
		add(carNumLbl);
		
		carNumTf = new JTextField();
		carNumTf.setHorizontalAlignment(JLabel.CENTER);
		carNumTf.setLocation(80, 325);
		carNumTf.setSize(80, 30);
		carNumTf.setVisible(true);
		add(carNumTf);
		
		recogBtn = new JButton("인식");
		recogBtn.setLocation(165, 325);
		recogBtn.setSize(60, 30);
		recogBtn.addActionListener(btnListener);
		recogBtn.setVisible(true);
		add(recogBtn);
		
		priceLbl = new JLabel("가격");
		priceLbl.setHorizontalAlignment(JLabel.CENTER);
		priceLbl.setLocation(15, 360);
		priceLbl.setSize(60, 30);
		priceLbl.setVisible(true);
		add(priceLbl);
		
		priceTf = new JTextField();
		priceTf.setLocation(80, 360);
		priceTf.setSize(80, 30);
		priceTf.setVisible(true);
		add(priceTf);
		
		transmitBtn = new JButton("전송");
		transmitBtn.setLocation(165, 360);
		transmitBtn.setSize(60, 30);
		transmitBtn.addActionListener(btnListener);
		transmitBtn.setVisible(true);
		add(transmitBtn);
		
		warningLbl = new JLabel();
		warningLbl.setLocation(5, 220);
		warningLbl.setSize(435, 30);
		warningLbl.setVisible(true);
		add(warningLbl);
	}
	
	String execPython(String environ) throws IOException, InterruptedException
	{
		Process p;
		
		ProcessBuilder pb = new ProcessBuilder("python", "SR.py", environ);
		p = pb.start();
		p.waitFor();
		
		pb = new ProcessBuilder("python", "Recognition.py");
		p = pb.start();
		p.waitFor();

		String result = "";
		
		FileInputStream input=new FileInputStream("result.txt");
        InputStreamReader reader=new InputStreamReader(input,"MS949");
        BufferedReader br = new BufferedReader(reader);
		
		String line = br.readLine();
		if (line != null)
			result = line;
		
		br.close();
		reader.close();
		input.close();
		
		return result;
	}
	
	void load_image(String path)
	{
		try {
			Image img = new ImageIcon(path).getImage();
			img = img.getScaledInstance(444, 254, Image.SCALE_SMOOTH);
			
			imgLbl.setIcon(new ImageIcon(img));
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
}
