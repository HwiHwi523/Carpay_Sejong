package server;

import java.io.*;
import java.net.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.util.*;

class Login extends JDialog {
   JTextField ID = new JTextField(10);
   JTextField Password = new JTextField(10);
   JButton okButton = new JButton("입력");

   public Login(JFrame frame, String title) { //로그인 버튼 입력시 뜰 팝업창
      super(frame, title, true);
      setLayout(new GridLayout(3, 2));
      JLabel j = new JLabel("ID");
      add(j);
      add(ID);
      j = new JLabel("Password");
      add(j);
      add(Password);
      add(okButton);
      setSize(250, 200);

      okButton.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            setVisible(false);
         }
      });
   }

   public String getId() {
      if (ID.getText().length() == 0)
         return null;
      else
         return ID.getText();
   }

   public String getPassword() {
      if (Password.getText().length() == 0)
         return null;
      else
         return Password.getText();
   }
}

class Select extends JDialog { //결제수단등록 버튼 입력 시 뜰 팝업창
   JButton card = new JButton("카드");
   JButton ac = new JButton("계좌");
   String type = "1";
   public Select(JFrame frame, String title) {
      super(frame, title, true);
      setLayout(new FlowLayout());
      JLabel j = new JLabel("계좌/카드 중 하나를 선택해주세요.");
      add(j);
      
      add(ac);
      add(card);
      setSize(250, 200);

      ac.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            setVisible(false);
            type = "1"; //계좌
         }
      });
      card.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            setVisible(false);
            type = "2"; //계좌
         }
      });
   }
      
      public String gettype() {
         return type;
      }
   }


class New extends JDialog { //회원가입 버튼 입력 시 뜰 팝업창
   JTextField ID = new JTextField(10);
   JTextField Password = new JTextField(10);
   JTextField Password2 = new JTextField(10);
   JTextField name = new JTextField(10);
   JTextField rNum = new JTextField(10);
   JTextField carNum = new JTextField(10);
   JTextField callNum = new JTextField(10);
   JTextField payNum = new JTextField(10);
   JButton okButton = new JButton("입력");

   public New(JFrame frame, String title) {
      super(frame, title, true);
      setLayout(new GridLayout(10, 2));
      JLabel j = new JLabel("이름");
      add(j);
      add(name);

      j = new JLabel("주민등록번호");
      add(j);
      add(rNum);

      j = new JLabel("ID"); 
      add(j);
      add(ID);

      j = new JLabel("비밀번호");
      add(j);
      add(Password);

      j = new JLabel("비밀번호 확인");
      add(j);
      add(Password2);

      j = new JLabel("자동차 번호판");
      add(j);
      add(carNum);

      j = new JLabel("전화번호");
      add(j);
      add(callNum);

      j = new JLabel("계좌 비밀번호");
      add(j);
      add(payNum);
      add(okButton);
      setSize(300, 500);

      okButton.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            setVisible(false);
         }
      });
   }

   public String getId() {
      if (ID.getText().length() == 0)
         return null;
      else
         return ID.getText();
   }

   public String getPassword() {
      if (Password.getText().length() == 0)
         return null;
      else
         return Password.getText();
   }

   public String getName() {
      if (name.getText().length() == 0)
         return null;
      else
         return name.getText();
   }

   public String getrNum() {
      if (rNum.getText().length() == 0)
         return null;
      else
         return rNum.getText();
   }

   public String getPassword2() {
      if (Password2.getText().length() == 0)
         return null;
      else
         return Password2.getText();
   }

   public String getcarNum() {
      if (carNum.getText().length() == 0)
         return null;
      else
         return carNum.getText();
   }

   public String getcallNum() {
      if (callNum.getText().length() == 0)
         return null;
      else
         return callNum.getText();
   }

   public String getpayNum() {
      if (payNum.getText().length() == 0)
         return null;
      else
         return payNum.getText();
   }
}

class FindPW extends JDialog { //비밀번호 찾기 버튼 입력 시 뜰 팝업창
   JTextField ID = new JTextField(10);
   JTextField name = new JTextField(10);
   JTextField callNum = new JTextField(10);
   JButton okButton = new JButton("입력");

   public FindPW(JFrame frame, String title) {
      super(frame, title, true);
      setLayout(new GridLayout(4, 2));
      
      JLabel j = new JLabel("ID");
      add(j);
      add(ID);
      
      j = new JLabel("이름");
      add(j);
      add(name);

      j = new JLabel("전화번호");
      add(j);
      add(callNum);
      add(okButton);
      setSize(300,300);

      okButton.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            setVisible(false);
         }
      });
   }

   public String getId() {
      if (ID.getText().length() == 0)
         return null;
      else
         return ID.getText();
   }


   public String getName() {
      if (name.getText().length() == 0)
         return null;
      else
         return name.getText();
   }

   public String getcallNum() {
      if (callNum.getText().length() == 0)
         return null;
      else
         return callNum.getText();
   }

}



class Information extends JDialog { //회원정보관리 버튼 입력 시 뜰 팝업창
   JLabel ID = new JLabel();
   JTextField Password = new JTextField(10);
   JTextField Password2 = new JTextField(10);
   JTextField name = new JTextField(10);
   JTextField rNum = new JTextField(10);
   JTextField carNum = new JTextField(10);
   JTextField callNum = new JTextField(10);
   JTextField payNum = new JTextField(10);
   JButton okButton = new JButton("입력");

   public Information(JFrame frame, String title, String newPW, String newPW2, String CarNum, String CallNum, String payPW,String csid) {
      super(frame, title, true);
      setLayout(new GridLayout(7, 2));
      JLabel j = new JLabel("ID");
      add(j);
      ID.setText(csid);
      add(ID);

      j = new JLabel("비밀번호");
      add(j);
      Password.setText(newPW);
      add(Password);
      
      j = new JLabel("비밀번호 확인");
      add(j);
      Password2.setText(newPW2);
      add(Password2);
      
      j = new JLabel("자동차 번호판");
      add(j);
      carNum.setText(CarNum);
      add(carNum);
      
      j = new JLabel("전화번호");
      add(j);
      callNum.setText(CallNum);
      add(callNum);
      
      j = new JLabel("계좌 비밀번호");
      add(j);
      payNum.setText(payPW);
      add(payNum);

      add(okButton);
      setSize(400, 500);

      okButton.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            setVisible(false);
         }
      });
   }

   public String getId() {
      if (ID.getText().length() == 0)
         return null;
      else
         return ID.getText();
   }

   public String getPassword() {
      if (Password.getText().length() == 0)
         return null;
      else
         return Password.getText();
   }

   public String getPassword2() {
      if (Password2.getText().length() == 0)
         return null;
      else
         return Password2.getText();
   }

   public String getcarNum() {
      if (carNum.getText().length() == 0)
         return null;
      else
         return carNum.getText();
   }

   public String getcallNum() {
      if (callNum.getText().length() == 0)
         return null;
      else
         return callNum.getText();
   }

   public String getpayNum() {
      if (payNum.getText().length() == 0)
         return null;
      else
         return payNum.getText();
   }
}


class IdFind extends JDialog { //ID찾기 버튼 입력 시 뜰 팝업창 카드 or 계좌 선택
   JTextField name = new JTextField(10);
   JTextField callNum = new JTextField(10);
   JButton okButton = new JButton("입력");

   public IdFind(JFrame frame, String title) {
      super(frame, title, true);
      setLayout(new GridLayout(3, 2));
      JLabel j = new JLabel("이름");
      add(j);
      add(name);
      j = new JLabel("전화번호");
      add(j);
      add(callNum);
      add(okButton);
      setSize(300, 200);

      okButton.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            setVisible(false);
         }
      });
   }

   public String getName() {
      if (name.getText().length() == 0)
         return null;
      else
         return name.getText();
   }

   public String getCallNum() {
      if (callNum.getText().length() == 0)
         return null;
      else
         return callNum.getText();
   }
}

class AC extends JDialog { //결제수단 계좌등록 버튼 입력 시 뜰 팝업창
   JTextField acBank = new JTextField(10);
   JTextField acNum = new JTextField(10);
   JTextField acPW = new JTextField(10);
   JButton okButton = new JButton("입력");

   public AC(JFrame frame, String title) {
      super(frame, title, true);
      setLayout(new GridLayout(4, 2));
      JLabel j = new JLabel("은행 명");
      add(j);
      add(acBank);
      j = new JLabel("계좌번호");
      add(j);
      add(acNum);
      j = new JLabel("계좌비밀번호");
      add(j);
      add(acPW);
      add(okButton);
      setSize(500, 200);

      okButton.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            setVisible(false);
         }
      });
   }

   public String getBank() {
      if (acBank.getText().length() == 0)
         return null;
      else
         return acBank.getText();
   }

   public String getacNum() {
      if (acNum.getText().length() == 0)
         return null;
      else
         return acNum.getText();
   }
   
   public String getacPW() {
      if (acPW.getText().length() == 0)
         return null;
      else
         return acPW.getText();
   }
}

class Card extends JDialog { //결제수단 카드등록 버튼 입력 시 뜰 팝업창
   JTextField cardCompany = new JTextField(10);
   JTextField cardNum = new JTextField(10);
   JTextField cardDay = new JTextField(10);
   JTextField cvc = new JTextField(10);
   JTextField cardPW = new JTextField(10);
   JButton okButton = new JButton("입력");

   public Card(JFrame frame, String title) {
      super(frame, title, true);
      setLayout(new GridLayout(6, 2));
      JLabel j = new JLabel("카드회사 명");
      add(j);
      add(cardCompany);
      j = new JLabel("카드번호");
      add(j);
      add(cardNum);
      j = new JLabel("카드 유효기간");
      add(j);
      add(cardDay);
      j = new JLabel("CVC");
      add(j);
      add(cvc);
      j = new JLabel("카드 비밀번호");
      add(j);
      add(cardPW);
      add(okButton);
      setSize(500, 400);

      okButton.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            setVisible(false);
         }
      });
   }

   public String getcardCompany() {
      if (cardCompany.getText().length() == 0)
         return null;
      else
         return cardCompany.getText();
   }

   public String getcardNum() {
      if (cardNum.getText().length() == 0)
         return null;
      else
         return cardNum.getText();
   }
   
   public String getcardDay() {
      if (cardDay.getText().length() == 0)
         return null;
      else
         return cardDay.getText();
   }
   public String getCVC() {
      if (cvc.getText().length() == 0)
         return null;
      else
         return cvc.getText();
   }
   
   public String getcardPW() {
      if (cardPW.getText().length() == 0)
         return null;
      else
         return cardPW.getText();
   }
}




public class ClientEx extends JFrame implements ActionListener {
   static String csid = ""; // 소비자id
   static String bsid = "";
   static Scanner scanner = new Scanner(System.in); // 키보드에서 읽을 scanner 객체 생성
   static boolean seq = true;
   static String payNum = ""; // 결제수단 계좌번호 or 카드번호
   static String payCom = ""; // 등록된 결제수단의 회사명
   static String lastPay = ""; // 마지막으로 결제한 금액
   static String newPW = "";
   static String newPW2 = "";// 새 비밀번호 확인
   static String carNum = "";
   static String callNum = "";
   static String payPW = ""; // 결제비밀번호
   static boolean mag = true;

   static BufferedReader in = null;
   static BufferedWriter out = null;
   static Socket socket = null;
   static InetAddress ip; // 본인의 ip주소
   static String ip1;

   static JButton btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9;
   static JTextArea txtResult; //결과창
   static JPanel pn1, pn2, pn3;
   static Container c;
   static JScrollPane scrollPane;
   static JTextField tf;
   static JLabel j;
   JFrame frame = this;
   
   
   public void Main() { //처음 실행시켰을 때 메인화면
      c = getContentPane();
      c.setLayout(new BorderLayout());
      txtResult = new JTextArea();
      txtResult.append("로그인 해주세요.\n");
      btn1 = new JButton("로그인");
      btn2 = new JButton("회원가입");
      btn3 = new JButton("ID 찾기");
      btn4 = new JButton("비밀번호 찾기");
      btn5 = new JButton("회원정보 관리");
      btn6 = new JButton("회원 탈퇴");
      btn7 = new JButton("결제수단 등록");
      btn8 = new JButton("결제 내역 확인");
      btn9 = new JButton("결제수단 정보 확인");


      pn1 = new JPanel();
      pn1.setLayout(new FlowLayout());
      pn1.add(btn1);
      pn1.add(btn2);
      pn1.add(btn3);
      pn1.add(btn4);
      pn1.add(btn5);
      pn1.add(btn6);
      pn1.add(btn9);
      pn1.add(btn7);
      pn1.add(btn8);

      c.add(txtResult, BorderLayout.CENTER);
      c.add(pn1, BorderLayout.NORTH);

      btn1.addActionListener(this);
      btn2.addActionListener(this);
      btn3.addActionListener(this);
      btn4.addActionListener(this);
      btn5.addActionListener(this);
      btn6.addActionListener(this);
      btn7.addActionListener(this);
      btn8.addActionListener(this);
      btn9.addActionListener(this);
   }

   public ClientEx() {
      setTitle("소비자용 클라이언트");
      setSize(1000, 600);
      Main();
      setVisible(true);
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      this.setLocation(200,200);
   }

   public void actionPerformed(ActionEvent e) {
      try {

         if (e.getSource() == btn1) { // 로그인
            Login dialogue = new Login(this, "로그인");
            dialogue.setVisible(true);
            String id = dialogue.getId();
            String pw = dialogue.getPassword();
            if (!id.equals(null) && !pw.equals(null)) {
               out.write("1\n");
               out.flush();

               out.write(ip1 + "\n");
               out.flush();

               csid = id;
               out.write(id + "\n");
               out.flush();

               out.write(pw + "\n");
               out.flush();
            } else {
               txtResult.append("로그인이 취소되었습니다. 올바른 값을 넣어주세요.\n");
            }
         }

         else if (e.getSource() == btn2) { // 회원가입
            New dialogue = new New(this, "회원가입");
            dialogue.setVisible(true);
            String id = dialogue.getId();
            String pw = dialogue.getPassword();
            String name = dialogue.getName();
            String rNum = dialogue.getrNum();
            String pw2 = dialogue.getPassword2();
            String carNum = dialogue.getcarNum();
            String callNum = dialogue.getcallNum();
            String payNum = dialogue.getpayNum();

            if (!id.equals(null) || !pw.equals(null) || !name.equals(null) || !rNum.equals(null) || !pw2.equals(null) && !carNum.equals(null) || !callNum.equals(null) || !payNum.equals(null)) {

               out.write("2\n");
               out.flush();

               out.write(name + "\n");
               out.flush();

               out.write(rNum + "\n");
               out.flush();

               out.write(id + "\n");
               out.flush();

               out.write(pw + "\n");
               out.flush();

               out.write(pw2 + "\n");
               out.flush();

               out.write(carNum + "\n");
               out.flush();

               out.write(callNum + "\n");
               out.flush();

               out.write(payNum + "\n");
               out.flush();
            } else {
               txtResult.append("회원가입이 취소되었습니다. 올바른 값을 넣어주세요.\n");
            }
         }

         else if (e.getSource() == btn3) { // 소비자ID찾기
            IdFind dialogue = new IdFind(this, "ID 찾기");
            dialogue.setVisible(true);
            String name = dialogue.getName();
            String callNum = dialogue.getCallNum();

            if (!name.equals(null) || !callNum.equals(null)) {
            out.write("3\n");
            out.flush();

            out.write(name + "\n");
            out.flush();

            out.write(callNum + "\n");
            out.flush();
            }
            else {
               txtResult.append("ID 찾기가 취소되었습니다. 올바른 값을 넣어주세요.\n");
            }
         }

         else if (e.getSource() == btn4) { // 비밀번호찾기
            FindPW dialogue = new FindPW(this, "비밀번호 찾기");
            dialogue.setVisible(true);
            String name = dialogue.getName();
            String callNum = dialogue.getcallNum();
            String id = dialogue.getId();
            
            if (!name.equals(null) || !callNum.equals(null) || !id.equals(null)) {
            out.write("4\n"); 
            out.flush();

            out.write(id + "\n");
            out.flush();

            out.write(name + "\n"); 
            out.flush();

            out.write(callNum + "\n"); 
            out.flush(); 
            }
            else {
               txtResult.append("비밀번호 찾기가 취소되었습니다. 올바른 값을 넣어주세요.\n");
            }
         }

         else if (e.getSource() == btn5) { // 회원정보관리
            out.write("5\n"); 
            out.flush();
            
            out.write(csid+"\n"); 
            out.flush();

         }

         else if (e.getSource() == btn6) { //회원 탈퇴
            out.write("6\n");
             out.flush();
             
             out.write("0\n");
             out.flush();
              
             out.write(csid + "\n"); 
             out.flush();
         }
         
         else if (e.getSource() == btn7) { // 결제수단 입력
            out.write("7\n");
            out.flush();
            // 데이터베이스에 결제수단 등록

            out.write(csid + "\n");
            out.flush();

            Select dialogue = new Select(this, "은행 / 계좌 선택");
            dialogue.setVisible(true);
            String borc = dialogue.gettype();

            out.write(borc + "\n");
            out.flush();
            
            if (borc.equalsIgnoreCase("1")) { // 은행 계좌 정보 입력
               
               AC dialogue1 = new AC(this, "계좌 등록하기");
               dialogue1.setVisible(true);
               String acBank = dialogue1.getBank();
               String acNum = dialogue1.getacNum();
               String acPW = dialogue1.getacPW();

               out.write(acBank + "\n");
               out.flush();

               out.write(acNum + "\n");
               out.flush();

               out.write(acPW + "\n");
               out.flush();
               
            } 
            else if (borc.equalsIgnoreCase("2")) { // 카드 정보 입력
               
               Card dialogue1 = new Card(this, "카드 등록하기");
               dialogue1.setVisible(true);
               String cardCompany = dialogue1.getcardCompany();
               String cardNum = dialogue1.getcardNum();
               String cardDay = dialogue1.getcardDay();
               String cvc = dialogue1.getCVC();
               String cardPW = dialogue1.getcardPW();
            
               out.write(cardCompany + "\n");
               out.flush();

               out.write(cardNum + "\n");
               out.flush();

               out.write(cardDay + "\n");
               out.flush();

               out.write(cvc + "\n");
               out.flush();

               out.write(cardPW + "\n");
               out.flush();
         }
            
         }
         
         else if (e.getSource() == btn8) { // 결제내역확인
            txtResult.setText("");
            out.write("8\n");
            out.flush();

            out.write(csid + "\n");
            out.flush();

         }
         else if (e.getSource() == btn9) { //결제수단 정보확인
            if(payCom.equals("") || payNum.equals("")) {
               txtResult.append("등록된 결제수단이 없습니다.\n");
            }
            else {
               txtResult.append("현재 등록되어있는 결제수단은 \n 은행 or 카드사 : " +payCom + "\t계좌번호 or 카드번호 : " + payNum +"\n입니다.\n");
            }
         }
         
      } catch (Exception e1) {
         System.out.println(e1.getMessage());
      }
   }

   public static void main(String[] args) {
      try {
         int result = JOptionPane.CLOSED_OPTION;
         ip = InetAddress.getLocalHost(); // 본인의 ip주소 가져오기
         System.out.println("ip주소를 입력하세요. (localhost 또는 자신의 IPV4 주소) >>");
         ip1 = scanner.nextLine(); // @@@@@@@@@@@@@@@@ localhost랑 본인의 ip주소를 입력하여 테스트하기 위해서 직접입력으로 했습니다..//
                              // 최종본에서ip주소는 변수 ip를 사용하시면 됩니다.
         socket = new Socket(ip1, 9999); // 클라이언트 소켓 생성. 서버와 바로 연결
         in = new BufferedReader(new InputStreamReader(socket.getInputStream())); // 소켓 입력 스트림
         out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())); // 소켓 출력 스트림
         ClientEx client = new ClientEx();

         while (true) {
            String st = in.readLine();
            if (st.equalsIgnoreCase("pay")) { // 결제요청받았을 경우
               bsid = in.readLine();
               String shop = in.readLine(); // 결제요청이 온 매장명
               lastPay = in.readLine(); // 결제 금액

               result = JOptionPane.showConfirmDialog(null, "\n" + shop + "에서 " + lastPay + "원, 결제요청이 왔습니다. 결제를 승인하시겠습니까?","결제요청",JOptionPane.YES_NO_OPTION);
               System.out.println("\n" + shop + "에서 " + lastPay + "원, 결제요청이 왔습니다. 결제를 승인하시겠습니까? Y/N >>");
               
               if(result == JOptionPane.YES_OPTION) {
                  out.write("Y\n");
                  out.flush();

                  out.write(bsid + "\n");
                  out.flush();

                  out.write(csid + "\n");
                  out.flush();

                  out.write(payNum + "\n");
                  out.flush();

                  out.write(payCom + "\n");
                  out.flush();

                  out.write(lastPay + "\n");
                  out.flush();
               }
               
               else {
                  out.write("N\n");
                  out.flush();

                  out.write(bsid + "\n");
                  out.flush();

                  out.write(csid + "\n");
                  out.flush();
               }
            }
            else if (st.equals("Ok")) { // 결제가 완료되면
               txtResult.append("결제가 완료되었습니다.\n");
               seq = true;
            }
            else if (st.equals("No")) { // 결제에 실패하면
               txtResult.append("결제에 실패하였습니다.\n");
               seq = true;
            }
            else if (st.equals("login")) { // 로그인하는 경우 정보가져오기
               payNum = in.readLine(); // 카드 or 계좌번호
               payCom = in.readLine(); // 카드 or 계좌 회사
               seq = true;
            }
            else if (st.equals("Info")) { // 회원정보관리를 눌렀을 때 가져올 정보들
               newPW = in.readLine();
               newPW2 = in.readLine();// 새 비밀번호 확인
               carNum = in.readLine();
               callNum = in.readLine();
               payPW = in.readLine(); // 결제비밀번호
               JFrame frame = new JFrame();
               
               Information dialogue = new Information(frame, "회원정보 관리", newPW, newPW2, carNum, callNum, payPW, csid);
               dialogue.setVisible(true);
               String callNum = dialogue.getcallNum();
               String id = dialogue.getId();
               String newPW = dialogue.getPassword();
               String newPW2 = dialogue.getPassword2();
               String carNum = dialogue.getcarNum();
               String payPW = dialogue.getpayNum();

               out.write(newPW + "\n"); 
               out.flush();

               out.write(newPW2 + "\n");
               out.flush();
                
               out.write(carNum + "\n"); 
               out.flush();
                
               out.write(callNum + "\n"); 
               out.flush();
                 
               out.write(payPW + "\n"); 
               out.flush();
                 
               
               seq = true;
            } 
            else if (st.equals("reg")) { 
               payCom = in.readLine();
               payNum = in.readLine();
            }
            else {
               txtResult.append(st + "\n");
               seq = true;
            }
            
         }
      } catch (IOException e) {
         System.out.println(e.getMessage());
      } finally {
         try {
            scanner.close();
            if (socket != null)
               socket.close(); // 클라이언트 소켓 닫기
         } catch (IOException e) {
            System.out.println("서버와 채팅 중 오류가 발생했습니다.");
         }
      }
   }

}