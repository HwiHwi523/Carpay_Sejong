import java.awt.Container;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

public class FrameBusiness extends JFrame {
	JPanel beforeLoginPanel;
	JPanel afterLoginPanel;
	JPanel findIdPwdPanel;
	JPanel mngMemInfoPanel;
	
	// 서버 연결 변수
	InetAddress ip;
	Socket socket;
	BufferedReader in;
	BufferedWriter out;
	
	// 사업자 ID
	public String bsId = "";
	public String bsPwd = "";
	public String name = "";
	public String callNum = "";
	public String bsNum = "";
	public String bsName = "";

	FrameBusiness()
	{		
		connect_to_server();
		
		beforeLoginPanel = new PanelBeforeLogin(this);
		afterLoginPanel = null;
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocation(100, 100);
		setSize(1000, 600);
		setVisible(true);
		setLayout(null);
		setResizable(false);

		// 초기 화면
		add(beforeLoginPanel);
	}
	
	// 서버 연결
	void connect_to_server()
	{
		try {
			ip = InetAddress.getLocalHost(); // 본인의 ip주소 가져오기
			socket = new Socket(ip, 9999); // 클라이언트 소켓 생성. 서버와 바로 연결
			in = new BufferedReader(new InputStreamReader(socket.getInputStream())); // 소켓 입력 스트림
			out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())); // 소켓 출력 스트림
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	void changePanel(String panelName)
	{
		getContentPane().removeAll();
		
		if (panelName.equals("BeforeLogin"))
			add(beforeLoginPanel);
		
		else if (panelName.equals("AfterLogin"))
		{
			if (afterLoginPanel == null)
				afterLoginPanel = new PanelAfterLogin(this);
			add(afterLoginPanel);
		}
		
		else if (panelName.equals("FindIdPwd"))
			add(new PanelFindIdPwd(this));
		
		else if (panelName.equals("MngMemInfo"))
			add(new PanelManageMemberInfo(this));
		
		else if (panelName.equals("RegMem"))
			add(new PanelRegisterMember(this));
	
		getContentPane().repaint();
	}
	
	String[] load_mem_info()
	{
		String name1 = "", bsName = "", bsNum = "", newPW = "", newPW2 = "", callNum = "";
		
		try {
			out.write("15\n");
	        out.flush();
	
	        out.write(bsId+"\n");
	        out.flush();
	        
	        name1 = in.readLine(); //사업자명
	        bsName = in.readLine(); //업체명
	        bsNum = in.readLine(); //사업자등록번호
	        newPW = in.readLine(); 
	        newPW2 = in.readLine(); //새비밀번호 확인
	        callNum = in.readLine(); //전화번호
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
        
        return new String[] { newPW, newPW2, name1, callNum, bsName, bsNum };
	}
	String[][] load_history(String ymdStart, String ymdEnd)
	{
		List<String[]> history_set = new ArrayList<String[]>();
		
		try {
			out.write("13\n");
			out.flush();

			out.write(bsId + "\n"); 
			out.flush();

			System.out.println("조회 시작 날짜 입력>> ");
			String dayFirst = ymdStart;
			out.write(dayFirst + "\n");
			out.flush();

			System.out.println("조회 마지막 날짜 입력>> ");
			String dayLast = ymdEnd;
			out.write(dayLast + "\n");
			out.flush();

			if (in != null)
				in.readLine(); // 열 이름 제거
			
			while (in != null) {
				String al = in.readLine();
				history_set.add(al.split("\t"));
				if (al.equals("fin")) {
					break;
				}
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		history_set.remove(history_set.size() - 1);
		history_set.remove(history_set.size() - 1);
		
		String[][] result = new String[history_set.size()][];
		
		history_set.toArray(result);
		
		return result;
	}
	
	String login(String id, String pwd)
	{
		String proc_result = "";
		
		try {
			out.write("1\n");
			out.flush();
	
			out.write(ip + "\n");
			out.flush();
	
			System.out.println("ID입력 >> " + id);
			bsId = id;
			out.write(id + "\n");
			out.flush();
			System.out.println("PW입력>> ");
			String pw = pwd;
			out.write(pw + "\n");
			out.flush();
	
			if (in != null) {// 서버가 전송한 String 출력
				proc_result = in.readLine();
				System.out.println(proc_result);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		if (proc_result.equals("로그인 되었습니다."))
		{
			String[] mem_info = load_mem_info();
			bsPwd = mem_info[0];
			name = mem_info[2];
			callNum = mem_info[3];
			bsName = mem_info[4];
			bsNum = mem_info[5];		
		}
		
		return proc_result;
	}
	void logout() 
	{
		// 현재 패널 변경
		beforeLoginPanel = new PanelBeforeLogin(this);
		changePanel("BeforeLogin");
		
		// 모든 패널 삭제
		afterLoginPanel = null;
		findIdPwdPanel = null;
		mngMemInfoPanel = null;
	}
	String findId(String name, String pnum)
	{
		String result = "";
		
		try {
			out.write("11\n");
			out.flush();
	
			System.out.println("이름 입력>> ");
			String name1 = name;
			out.write(name1 + "\n");
			out.flush();
	
			System.out.println("전화번호 입력>> ");
			String callNum = pnum;
			out.write(callNum + "\n");
			out.flush();
	
			if (in != null) {
				result = in.readLine();
				System.out.println(result);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		return result;
	}
	String findPwd(String id, String name, String pnum)
	{
		String result = "";
		
		try {
			out.write("12\n");
			out.flush();

			System.out.println("ID 입력>> ");
			out.write(id + "\n");
			out.flush();

			System.out.println("이름 입력>> ");
			String name1 = name;
			out.write(name1 + "\n");
			out.flush();

			System.out.println("전화번호 입력>> ");
			String callNum = pnum;
			out.write(callNum + "\n");
			out.flush();
			
			if (in != null) {
				result = in.readLine();
				System.out.println(result);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		return result;
	}
	
	String modifyMemberInfo(String[] info)
	{		
		String proc_result = "";

		try {
          	out.write("10\n");
            out.flush();

            out.write(bsId+"\n");
            out.flush();

			String newPW, newPW2, callNum;
     
			System.out.println("수정할 PW >> " + info[0]);
            newPW =  info[0];
            out.write(newPW+"\n");
            out.flush();
            
            System.out.println("PW확인 >> " + info[1]);
            newPW2 =  info[1];
            out.write(newPW2+"\n");
            out.flush();
          
            System.out.println("수정할 전화번호 >> " + info[2]);
            callNum =  info[2];
            out.write(callNum+"\n");
            out.flush();
            
            if (in != null) {//서버가 전송한 String 출력
                proc_result = in.readLine();
                System.out.println(proc_result);
            }
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		if (proc_result.equals("회원정보가 수정되었습니다."))
		{
			String[] mem_info = load_mem_info();
			bsPwd = mem_info[0];
			name = mem_info[2];
			callNum = mem_info[3];
			bsName = mem_info[4];
			bsNum = mem_info[5];
		}
		
		return proc_result;
	}
	String registerMemberInfo(String[] info) // { 아이디, 비밀번호, 비밀번호 확인, 이름, 전화번호, 업체명, 사업자등록번호 }
	{
		String proc_result = "";
		
		try {
			out.write("9\n");
			out.flush();
	
			System.out.println("사업자명 >> " + info[3]);
			String name1 = info[3];
			out.write(name1 + "\n");
			out.flush();
	
			System.out.println("업체명 입력 >> " + info[5]);
			String bsName = info[5];
			out.write(bsName + "\n");
			out.flush();
	
			System.out.println("사업자등록번호 입력 >> " + info[6]);
			String bsNum = info[6];
			out.write(bsNum + "\n");
			out.flush();
	
			System.out.println("id 입력 >> " + info[0]); 
			String id = info[0];
			out.write(id + "\n");
			out.flush();
	
			System.out.println("비밀번호 입력 >> " + info[1]);
			String pw = info[1];
			out.write(pw + "\n");
			out.flush();
	
			System.out.println("비밀번호확인 입력 >> " + info[2]);
			String pw2 = info[2];
			out.write(pw2 + "\n");
			out.flush();
	
			System.out.println("전화번호 입력 >> " + info[4]);
			String callNum = info[4];
			out.write(callNum + "\n");
			out.flush();
	
			if (in != null) {
				proc_result = in.readLine();
				System.out.println(proc_result);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		return proc_result;
	}
	
	String removeMemberInfo()
	{
		String result = "";
		
		try {
			out.write("6\n");
			out.flush();

			out.write("1\n"); // 사업자 탈퇴 진행
			out.flush();

			out.write(bsId + "\n");
			out.flush();

			if (in != null) { // 서버가 전송한 String 출력
				result = in.readLine();
				System.out.println(result);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		return result;
	}
	
	String transmit_payment(String carNum, String price)
	{
		String result = "";
		
		try {
			out.write("14\n");
			out.flush();

			out.write(bsId + "\n");
			out.flush();

			String ipAdress = ip.toString();
			out.write(ipAdress + "\n");
			out.flush();

			System.out.println("요청을 보낼 번호판을 적어주세요. >>");
			out.write(carNum + "\n");
			out.flush();

			System.out.println("결제 금액을 적어주세요. >>");
			String val = price;
			out.write(val + "\n");
			out.flush();

			System.out.println("결제를 기다리는 중...");

			while (in != null) { // 서버가 전송한 String 출력
				String al = in.readLine();
				if (al.equals("Ok")) {
					result = "결제가 완료되었습니다.";
				}
				if (al.equals("No")) {
					result = "결제에 실패하였습니다.";
				}
				break;
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		return result;
	}
}
