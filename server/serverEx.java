package server;

import java.io.*;
import java.net.*;
import java.util.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;

public class serverEx {
	static HashMap<String, String> clientMap = new HashMap<String, String>();
	static HashMap<String, BufferedWriter> clientStreamMap = new HashMap<String, BufferedWriter>();
	static BufferedReader in = null;
	static BufferedWriter out = null;
	static ServerSocket listener = null;
	static Socket socket = null;
	static Scanner scanner = new Scanner(System.in); // 키보드에서 읽을 scanner 객체 생성
	static Connection con;
	PreparedStatement stmt;
	ResultSet rs;
	String Driver = "";
	static String url = "jdbc:mysql://localhost:3306/carpay?&serverTimezone=Asia/Seoul&useSSL=false";
	static String userid = "carpay_mng1";
	static String pwd = "mng000";

	static int pmAuthor = 1; // 승인번호

	public static void conDB() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			System.out.println("드라이버 로드 성공");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		try { /* 데이터베이스를 연결하는 과정 */
			System.out.println("데이터베이스 연결 준비...");
			con = DriverManager.getConnection(url, userid, pwd);
			System.out.println("데이터베이스 연결 성공");
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}

	public static void main(String[] args) {
		clientMap = new HashMap();
		Collections.synchronizedMap(clientMap);

		conDB();
		try {
			listener = new ServerSocket(9999); // 서버 소켓 생성
			System.out.println("연결을 기다리고 있습니다.....");

			while (true) {
				socket = listener.accept(); // 클라이언트로부터 연결 요청 대기

				Thread thread = new MultiServerRec(socket);
				thread.start();
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			try {
				scanner.close(); // scanner 닫기
				socket.close(); // 통신용 소켓 닫기
				listener.close(); // 서버 소켓 닫기
			} catch (IOException e) {
				System.out.println("클라이언트와 채팅 중 오류가 발생했습니다.");
			}
		}
	}

	static class MultiServerRec extends Thread {
		Socket socket;
		ArrayList<BufferedReader> inList = new ArrayList<BufferedReader>();
		ArrayList<BufferedWriter> outList = new ArrayList<BufferedWriter>();

		// 생성자.
		public MultiServerRec(Socket socket) {
			this.socket = socket;
			try {
				// Socket으로부터 입력스트림을 얻는다.
				in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				inList.add(in);
				// Socket으로부터 출력스트림을 얻는다.
				out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
				outList.add(out);
			} catch (Exception e) {
				System.out.println("예외:" + e);
			}
		}// 생성자 ------------

		@Override
		public void run() { // 쓰레드를 사용하기 위해서 run()메서드 재정의

			String name = null; // 클라이언트로부터 받은 이름을 저장할 변수.
			try {

				System.out.println(socket.getInetAddress() + "연결되었습니다");
				// name = in.readUTF(); //클라이언트에서 처음으로 보내는 메시지는
				// 클라이언트가 사용할 이름이다.

				// clientMap.put(name, out); //해쉬맵에 키를 name으로 출력스트림 객체를 저장.
				// 이거 한 이유는 이름별로 말하는걸 보기 위해서이다.
				// System.out.println("현재 접속자 수는 "+clientMap.size()+"명 입니다.");
				// 몇명이 접속했는지 알기 위해서 만들었다.

				while (in != null) {
					for (int i = 0; i < inList.size(); i++) {
						String msg = inList.get(i).readLine();

						if (msg.equalsIgnoreCase("1")) { // 로그인
							String ip = inList.get(i).readLine();
							System.out.println(ip);
							String id = inList.get(i).readLine();
							String pw = inList.get(i).readLine();
							String payNum = "";
							String payCom = "";
							// 데이터베이스에서의 처리
							String sql = "SELECT * FROM consm_mem";
							PreparedStatement pstmt = con.prepareStatement(sql);
							ResultSet rs = pstmt.executeQuery();

							boolean login = false;
							boolean loginpw = false;

							while (rs.next()) { // 소비자 데이터베이스 훑어보기
								if (rs.getString(1).equals(id)) { // 아이디가 존재할 경우
									if (rs.getString(2).equals(pw)) { // 아이디랑 비밀번호가 일치하면 로그인성공
										login = true;
										loginpw = true;

										clientMap.put(id, ip);
										clientStreamMap.put(ip, outList.get(i));

										outList.get(i).write("login\n");
										outList.get(i).flush();

										sql = "select * from simple_pay";
										pstmt = con.prepareStatement(sql);
										rs = pstmt.executeQuery();

										while (rs.next()) {
											if (rs.getString(2).equals(id)) {
												payNum = rs.getString(1);
												payCom = rs.getString(3);
												break;
											}
										}

										outList.get(i).write(payNum + "\n");
										outList.get(i).flush();

										outList.get(i).write(payCom + "\n");
										outList.get(i).flush();

										String al = "로그인 되었습니다.";
										System.out.println(id + " 님이 " + al);
										outList.get(i).write(al + "\n");
										outList.get(i).flush();
										break;
									} else { // 비밀번호가 일치하지 않는다면
										login = true;
										loginpw = false;
										String al = "비밀번호가 일치하지 않습니다.";
										System.out.println(id + "님의 로그인 시도/실패/비밀번호 불일치.");
										outList.get(i).write(al + "\n");
										outList.get(i).flush();
										break;
									}
								} else {
									login = false;
									loginpw = false;
									continue;
								}
							}

							if (login == false) { // 소비자 데이터에 id가 없다면 사업자 데이터 훑어보기
								sql = "SELECT * FROM busin_mem";
								pstmt = con.prepareStatement(sql);
								rs = pstmt.executeQuery();

								while (rs.next()) { // 사업자 데이터베이스 훑어보기
									if (rs.getString(1).equals(id)) { // 아이디가 존재할 경우
										if (rs.getString(2).equals(pw)) { // 아이디랑 비밀번호가 일치하면 로그인성공
											login = true;
											loginpw = true;

											clientMap.put(id, ip);
											clientStreamMap.put(ip, outList.get(i));

											String al = "로그인 되었습니다.";
											System.out.println(id + " 님이 " + al);
											outList.get(i).write(al + "\n");
											outList.get(i).flush();
											break;
										} else { // 비밀번호가 일치하지 않는다면
											login = false;
											loginpw = true;
											String al = "비밀번호가 일치하지 않습니다.";
											System.out.println(id + "님의 로그인 시도/실패/비밀번호 불일치.");
											outList.get(i).write(al + "\n");
											outList.get(i).flush();
											break;
										}

									} else {
										login = false;
										loginpw = false;
										continue;
									}
								}
							}

							if (login == false && loginpw == false) { // 아이디가 없을 경우
								login = false;
								String al = "ID가 존재하지 않습니다.";
								System.out.println(id + "님의 로그인 시도/실패/존재하지 않는 ID.");
								outList.get(i).write(al + "\n");
								outList.get(i).flush();
							}
						}

						else if (msg.equalsIgnoreCase("2")) { // 소비자 회원가입
							String name1 = inList.get(i).readLine();
							String rNum = inList.get(i).readLine(); // 주민등록번호
							String id = inList.get(i).readLine();
							String pw = inList.get(i).readLine();
							String pw2 = inList.get(i).readLine(); // 비밀번호 확인
							String carNum = inList.get(i).readLine(); // 번호판
							String callNum = inList.get(i).readLine(); // 전화번호
							String payPW = inList.get(i).readLine(); // 결제 비밀번호

							// 데이터베이스에 저장 진행 (ID중복 검사 해야함)
							String sql = "SELECT * FROM consm_mem";
							PreparedStatement pstmt = con.prepareStatement(sql);
							;
							ResultSet rs = pstmt.executeQuery();

							boolean idOver = false; // ID가 중복되는지 확인하기 위한 변수값
							boolean pwOk = false; // 비밀번호와 비밀번호확인값이 일치하는지 확인하는 변수값
							boolean resOver = false; // 주민등록번호가 중복되는지 확인

							// ID중복검사 시작
							while (rs.next()) { // 소비자 데이터베이스 훑어보기
								if (rs.getString(1).equals(id)) { // 아이디가 존재할 경우
									idOver = true;
									break;
								}
								if (rs.getString(4).equals(rNum)) {
									resOver = true;
								} else {
									idOver = false;
									continue;
								}
							}

							if (idOver == false) { // 소비자 데이터에 id가 없다면 사업자 데이터 훑어보기
								sql = "SELECT * FROM busin_mem";
								pstmt = con.prepareStatement(sql);
								rs = pstmt.executeQuery();

								while (rs.next()) { // 사업자 데이터베이스 훑어보기
									if (rs.getString(1).equals(id)) { // 아이디가 존재할 경우
										idOver = true;
										break;
									} else {
										idOver = false;
										continue;
									}
								}
							}
							if (idOver == true) { // 아이디가 중복되는 경우
								String al = "ID가 중복되어 사용할 수 없습니다.";
								System.out.println(al);
								outList.get(i).write(al + "\n");
								outList.get(i).flush();
							}

							if (pw.equals(pw2)) { // 비밀번호란과 비밀번호확인란에 쓴 값이 같을 경우
								pwOk = true;
							} else {// 비밀번호란과 비밀번호확인란에 쓴 값이 다를경우
								pwOk = false;
								String al = "비밀번호와 비밀번호 확인값이 일치하지 않습니다.";
								System.out.println(al);
								outList.get(i).write(al + "\n");
								outList.get(i).flush();
							}

							if (resOver == true) { // 주민등록번호가 중복 될 때 = 이미 ID가 있는 상황
								String al = "이미 등록되어있는 ID 입니다.";
								System.out.println(al);
								outList.get(i).write(al + "\n");
								outList.get(i).flush();
							}

							if (idOver == false && pwOk == true && resOver == false) { // 아이디가 중복되지 않는경우 회원가입 진행
								sql = "INSERT INTO consm_mem(con_id, con_pw, con_name, con_resinum, con_ph, con_car, con_paypw) VALUES(?, ?, ?, ?, ?, ?, ?)";
								pstmt = con.prepareStatement(sql);

								pstmt.setString(1, id);
								pstmt.setString(2, pw);
								pstmt.setString(3, name1); // 회원이름
								pstmt.setString(4, rNum); // 주민등록번호
								pstmt.setString(5, callNum); // 전화번호
								pstmt.setString(6, carNum); // 번호판번호
								pstmt.setString(7, payPW); // 결제비밀번호

								pstmt.executeUpdate();

								String al = "회원가입이 완료되었습니다.";
								System.out.println(al);
								outList.get(i).write(al + "\n");
								outList.get(i).flush();
							}

						}

						else if (msg.equalsIgnoreCase("3")) { // 소비자 ID 찾기
							String name1 = inList.get(i).readLine();
							String callNum = inList.get(i).readLine();
							// 데이터베이스를 통해 등록된 ID확인
							String sql = "SELECT * FROM consm_mem";
							PreparedStatement pstmt = con.prepareStatement(sql);
							;
							ResultSet rs = pstmt.executeQuery();

							boolean idOk = false;

							while (rs.next()) {
								if (rs.getString(3).equals(name1)) {
									if (rs.getString(5).equals(callNum)) { // 입력한 정보가 맞다면 ID 출력
										idOk = true;
										String id = rs.getString(1);
										String al = name1 + "님의 아이디는 " + id + " 입니다.";
										System.out.println(al);
										outList.get(i).write(al + "\n");
										outList.get(i).flush();
										break;
									} else { // 이름이 있으나 전화번호와 다를 경우
										idOk = true;
										String al = "이름과 전화번호가 일치하지 않습니다.";
										System.out.println(al);
										outList.get(i).write(al + "\n");
										outList.get(i).flush();
										break;
									}
								}
							}
							if (idOk == false) { // 해당하는 ID정보가 없을 경우
								String al = "가입된 ID가 존재하지 않습니다.";
								System.out.println(al);
								outList.get(i).write(al + "\n");
								outList.get(i).flush();
							}
						}

						else if (msg.equalsIgnoreCase("4")) { // 소비자 비밀번호 찾기
							String id = inList.get(i).readLine();
							String name1 = inList.get(i).readLine();
							String callNum = inList.get(i).readLine();
							// 임시 비밀번호 난수 뽑기, 데이터베이스에 업데이트
							String sql = "SELECT * FROM consm_mem";
							PreparedStatement pstmt = con.prepareStatement(sql);
							ResultSet rs = pstmt.executeQuery();

							boolean idOk = false;

							while (rs.next()) {
								if (rs.getString(1).equals(id) && rs.getString(3).equals(name1)
										&& rs.getString(5).equals(callNum)) {
									idOk = true;
								}
							}

							if (idOk == false) { // 입력한 정보값이 틀렸을 경우
								String al = "잘못된 정보 입니다.";
								System.out.println(al);
								outList.get(i).write(al + "\n");
								outList.get(i).flush();
							}

							if (idOk == true) {
								String temPW = "";
								for (int j = 0; j < 4; j++) { // 임시비밀번호 4자리 생성
									double dValue = Math.random();
									int iValue = (int) (dValue * 10);
									temPW = temPW + iValue;
								}
								// 임시비밀번호 데이터베이스에 넣기
								sql = "UPDATE consm_mem SET con_pw = ? WHERE con_id = ?";
								pstmt = con.prepareStatement(sql);

								pstmt.setString(1, temPW);
								pstmt.setString(2, id);

								pstmt.executeUpdate();

								String al = name1 + "님의 임시비밀번호는 " + temPW + " 입니다.";
								System.out.println(al);
								outList.get(i).write(al + "\n");
								outList.get(i).flush();

							}
						}

						else if (msg.equalsIgnoreCase("5")) { // 소비자 회원정보 관리
							String id = inList.get(i).readLine();
							String newPW = "???";
							String newPW2 = "???"; // 새 비밀번호 확인
							String carNum = "???";
							String callNum = "???";
							String payPW = "???"; // 결제비밀번호

							// 회원 정보 불러오고 수정된 값 업데이트
							String sql = "SELECT * FROM consm_mem";
							PreparedStatement pstmt = con.prepareStatement(sql);
							ResultSet rs = pstmt.executeQuery();

							outList.get(i).write("Info" + "\n");
							outList.get(i).flush();

							while (rs.next()) {
								if (rs.getString(1).equals(id)) { // id로 정보 불러오기
									newPW = rs.getString(2);
									outList.get(i).write(newPW + "\n");
									outList.get(i).flush();

									newPW2 = rs.getString(2);
									outList.get(i).write(newPW2 + "\n");
									outList.get(i).flush();

									carNum = rs.getString(6);
									outList.get(i).write(carNum + "\n");
									outList.get(i).flush();

									callNum = rs.getString(5);
									outList.get(i).write(callNum + "\n");
									outList.get(i).flush();

									payPW = rs.getString(7);
									outList.get(i).write(payPW + "\n");
									outList.get(i).flush();
								}
							}

							// 클라이언트에 입력된 값 불러오기
							newPW = inList.get(i).readLine();
							newPW2 = inList.get(i).readLine();
							carNum = inList.get(i).readLine();
							callNum = inList.get(i).readLine();
							payPW = inList.get(i).readLine();

							// 데이터베이스 갱신작업
							// id가 중복되지 않는지, pw가 일치하는지 확인하는 작업
							sql = "SELECT * FROM consm_mem";
							pstmt = con.prepareStatement(sql);
							rs = pstmt.executeQuery();

							boolean pwOk = false;

							// 비밀번호 일치하는지 확인
							if (newPW.equals(newPW2)) { // 비밀번호란과 비밀번호확인란에 쓴 값이 같을 경우
								pwOk = true;
							} else {// 비밀번호란과 비밀번호확인란에 쓴 값이 다를경우
								pwOk = false;
								String al = "비밀번호와 비밀번호 확인값이 일치하지 않습니다.";
								System.out.println(al);
								outList.get(i).write(al + "\n");
								outList.get(i).flush();
							}

							if (pwOk == true) {
								sql = "UPDATE consm_mem SET con_pw = ?, con_ph = ?, con_car = ?, con_paypw = ? WHERE con_id = ?";
								pstmt = con.prepareStatement(sql);

								pstmt.setString(1, newPW);
								pstmt.setString(2, callNum);
								pstmt.setString(3, carNum);
								pstmt.setString(4, payPW);
								pstmt.setString(5, id);

								pstmt.executeUpdate();

								String al = "회원정보가 수정되었습니다.";
								System.out.println(al);
								outList.get(i).write(al + "\n");
								outList.get(i).flush();
							}

						}

						else if (msg.equalsIgnoreCase("6")) { // 회원탈퇴
							String type = inList.get(i).readLine();
							if (type.equalsIgnoreCase("0")) {
								String id = inList.get(i).readLine();

								// 데이터베이스에서 회원 삭제
								String sql = "DELETE FROM consm_mem WHERE con_id = ?";
								PreparedStatement pstmt = con.prepareStatement(sql);
								pstmt.setString(1, id);

								pstmt.executeUpdate();

								String al = "회원탈퇴가 완료되었습니다.";
								System.out.println(al);
								outList.get(i).write(al + "\n");
								outList.get(i).flush();
							}

							else if (type.equalsIgnoreCase("1")) {
								String id = inList.get(i).readLine();

								// 데이터베이스에서 회원 삭제
								String sql = "DELETE FROM busin_mem WHERE bs_id = ?";
								PreparedStatement pstmt = con.prepareStatement(sql);
								pstmt.setString(1, id);

								pstmt.executeUpdate();

								String al = "회원탈퇴가 완료되었습니다.";
								System.out.println(al);
								outList.get(i).write(al + "\n");
								outList.get(i).flush();
							}

						}

						else if (msg.equalsIgnoreCase("7")) { // 소비자 결제수단 등록
							// 데이터베이스에 결제수단 등록

							String id = inList.get(i).readLine(); // 등록할 회원 id
							System.out.println(id);

							String borc = inList.get(i).readLine(); // 은행 or 카드 선택 1 입력시 은행 2 입력시 카드
							System.out.println(borc);
							if (borc.equalsIgnoreCase("1")) {
								// 은행 선택시
								String acBank = inList.get(i).readLine(); // 은행명
								System.out.println(acBank);
								String acNum = inList.get(i).readLine(); // 계좌번호
								System.out.println(acNum);
								String acPW = inList.get(i).readLine(); // 계좌 비밀번호
								System.out.println(acPW);

								String acPW1 = null, acBank1 = null;
								// 계좌번호 존재 여부 검사
								String sql = "Select * from bank_account";
								PreparedStatement pstmt = con.prepareStatement(sql);
								ResultSet rs = pstmt.executeQuery();

								boolean acNumExist = false; // 계좌번호가 존재하는지 확인하기 위한 변수값
								boolean acNumOver = false; // 계좌번호가 중복되는지 확인하기 위한 변수값

								while (rs.next()) { // 은행 데이터베이스 훑어보기
									if (rs.getString(1).equals(acNum)) { // 계좌번호가 존재할 경우 체크
										acNumExist = true;
										acPW1 = rs.getString(4); // 일치하는 튜플 비번 저장
										acBank1 = rs.getString(2); // 일치하는 튜플 은행명 저장
										break;
									} else {
										acNumExist = false;
										continue;
									}
								}
								if (acNumExist == false) { // 계좌번호가 존재하지 않을 경우
									String al = "계좌번호가 존재하지않아 등록할 수 없습니다.";
									System.out.println(al);
									outList.get(i).write(al + "\n");
									outList.get(i).flush();
								}

								if (acNumExist == true) { // 계좌번호가 존재할 시 중복 여부 체크(간편결제 데베와)
									sql = "Select * from simple_pay";
									pstmt = con.prepareStatement(sql);
									rs = pstmt.executeQuery();

									while (rs.next()) { // 간편결제 데이터베이스 훑어보기
										if (rs.getString(1).equals(acNum)) { // 계좌번호가 중복될 경우 체크
											acNumOver = true;
											break;
										} else {
											acNumOver = false;
											continue;
										}
									}
									if (acNumOver == true) {

										String al = "계좌번호가 이미 등록되어있어 등록할 수 없습니다.";
										System.out.println(al);
										outList.get(i).write(al + "\n");
										outList.get(i).flush();
									}

									if (acNumOver == false) { // 계좌번호가 중복되지 않을 경우, 계좌번호 비번과 은행명이 일치할 시에만 삽입
										if (acPW1.equals(acPW) && acBank1.equals(acBank)) {
											sql = "INSERT INTO simple_pay(pay_num, con_id, comp_name) VALUES(?, ?, ?)";
											pstmt = con.prepareStatement(sql);

											pstmt.setString(1, acNum); // 계좌번호
											pstmt.setString(2, id); // 회원 id
											pstmt.setString(3, acBank); // 은행명

											pstmt.executeUpdate();

											String al = "결제수단 등록이 완료되었습니다.";
											System.out.println(al);
											outList.get(i).write(al + "\n");
											outList.get(i).flush();
										} else {
											String al = "입력된 계좌번호 정보가 일치하지 않아 등록할 수 없습니다.";
											System.out.println(al);
											outList.get(i).write(al + "\n");
											outList.get(i).flush();
										}
									}
								}
							}
							// 계좌번호 중복 여부(등록된 간편결제 db내에서) 체크 후 등록

							else if (borc.equalsIgnoreCase("2")) {
								// 카드 선택시

								String cardCompany = inList.get(i).readLine(); // 카드사 이름
								System.out.println(cardCompany);
								String cardNum = inList.get(i).readLine(); // 카드번호
								System.out.println(cardNum);
								String cardDay = inList.get(i).readLine(); // 카드유효기간
								System.out.println(cardDay);
								String cvc = inList.get(i).readLine(); // 카드 뒷면 cvc
								System.out.println(cvc);
								String cardPW = inList.get(i).readLine(); // 카드 비밀번호
								System.out.println(cardPW);

								String cardPW1 = null, cardCompany1 = null, cardDay1 = null, cvc1 = null;
								// 카드 존재 여부 검사
								String sql = "Select * from card_info";
								PreparedStatement pstmt = con.prepareStatement(sql);
								ResultSet rs = pstmt.executeQuery();

								boolean cardNumExist = false; // 카드번호가 존재하는지 확인하기 위한 변수값
								boolean cardNumOver = false; // 카드번호가 중복되는지 확인하기 위한 변수값

								while (rs.next()) { // 카드 데이터베이스 훑어보기
									if (rs.getString(1).equals(cardNum)) { // 카드번호가 존재할 경우 체크
										cardNumExist = true;
										cardDay1 = rs.getString(4);
										cardCompany1 = rs.getString(2);
										cvc1 = rs.getString(5);
										cardPW1 = rs.getString(6); // 일치하는 튜플 정보 저장

										break;
									} else {
										cardNumExist = false;
										continue;
									}
								}
								if (cardNumExist == false) { // 카드번호가 존재하지 않을 경우
									String al = "카드번호가 존재하지않아 등록할 수 없습니다.";
									System.out.println(al);
									outList.get(i).write(al + "\n");
									outList.get(i).flush();
								}

								if (cardNumExist == true) { // 카드번호가 존재할 시 중복 여부 체크(간편결제 데베와)
									sql = "Select * from simple_pay";
									pstmt = con.prepareStatement(sql);
									rs = pstmt.executeQuery();

									while (rs.next()) { // 간편결제 데이터베이스 훑어보기
										if (rs.getString(1).equals(cardNum)) { // 계좌번호가 중복될 경우 체크
											cardNumOver = true;
											break;
										} else {
											cardNumOver = false;
											continue;
										}
									}
									if (cardNumOver == true) {

										String al = "카드번호가 이미 등록되어있어 등록할 수 없습니다.";
										System.out.println(al);
										outList.get(i).write(al + "\n");
										outList.get(i).flush();
									}

									if (cardNumOver == false) { // 카드번호가 중복되지 않을 경우, 카드 정보가 일치할 시에만 삽입
										if (cardPW1.equals(cardPW) && cardCompany1.equals(cardCompany)) {
											sql = "INSERT INTO simple_pay(pay_num, con_id, comp_name) VALUES(?, ?, ?)";
											pstmt = con.prepareStatement(sql);

											pstmt.setString(1, cardNum); // 카드번호
											pstmt.setString(2, id); // 회원 id
											pstmt.setString(3, cardCompany); // 카드사명

											pstmt.executeUpdate();

											String al = "결제수단 등록이 완료되었습니다.";
											System.out.println(al);
											outList.get(i).write(al + "\n");
											outList.get(i).flush();
										} else {
											String al = "입력된 카드 정보가 일치하지 않아 등록할 수 없습니다.";
											System.out.println(al);
											outList.get(i).write(al + "\n");
											outList.get(i).flush();
										}
									}
								}
								// 데이터베이스에 저장 진행
							}

						}

						else if (msg.equalsIgnoreCase("8")) { // 소비자 결제내역 조회
							String id = inList.get(i).readLine();

							// 결제내역 조회
							String sql = "SELECT * FROM paymentlist";
							PreparedStatement pstmt = con.prepareStatement(sql);
							ResultSet rs = pstmt.executeQuery();

							String al = "결제승인번호\t소비자id\t결제수단 번호\t가게명(거래처)\t은행(카드사명)\t총 결제금액\t결제날짜\t거래일련번호";
							outList.get(i).write(al + "\n");
							outList.get(i).flush();

							while (rs.next()) {
								if (rs.getString(2).equals(id)) {
									String p_au = rs.getString(1); // 결제승인번호(8)
									String cn_id = rs.getString(2); // 소비자id(12)
									String p_num = rs.getString(3); // 결제수단 번호(20)(카드번호, 계좌번호)
									String w_com = rs.getString(4); // 가게명, 거래처(45)
									String d_com = rs.getString(5); // 은행 혹은 카드사명(15), 결제수단 회사
									String p_amount = rs.getString(6); // 총 결제금액(10)
									String p_day = rs.getString(7); // 결제날짜(10)
									String d_num = rs.getString(8); // 거래일련번호(4)

									al = p_au + "\t" + cn_id + "\t" + p_num + "\t" + w_com + "\t" + d_com + "\t"
											+ p_amount + "\t" + p_day + "\t" + d_num;
									System.out.println(al);
									outList.get(i).write(al + "\n");
									outList.get(i).flush();
								}
							}
							outList.get(i).write("X\n");
							outList.get(i).flush();
						}

						else if (msg.equalsIgnoreCase("9")) { // 사업자 회원가입

							String name1 = inList.get(i).readLine(); // 사업자명
							System.out.println(name1);
							String bsName = inList.get(i).readLine(); // 업체명
							System.out.println(bsName);
							String bsNum = inList.get(i).readLine(); // 사업자등록번호
							System.out.println(bsNum);
							String id = inList.get(i).readLine(); // id
							System.out.println(id);
							String pw = inList.get(i).readLine(); // pw
							System.out.println(pw);
							String pw2 = inList.get(i).readLine(); // 비밀번호 확인
							System.out.println(pw2);
							String callNum = inList.get(i).readLine(); // 전화번호
							System.out.println(callNum);

							// 데이터베이스에 저장 진행 (ID중복 검사 해야함)
							String sql = "SELECT * FROM consm_mem";
							PreparedStatement pstmt = con.prepareStatement(sql);
							;
							ResultSet rs = pstmt.executeQuery();

							boolean idOver = false; // ID가 중복되는지 확인하기 위한 변수값
							boolean pwOk = false;
							boolean bsNameOver = false; // 업체명 중복되는지 확인용 변수값
							// ID중복검사 시작
							while (rs.next()) { // 소비자 데이터베이스 훑어보기
								if (rs.getString(1).equals(id)) { // 아이디가 존재할 경우
									idOver = true;
									break;
								} else {
									idOver = false;
									continue;
								}
							}

							if (idOver == false) { // 소비자 데이터에 id가 없다면 사업자 데이터 훑어보기 && 업체명or아이디 중복 여부 확인
								sql = "SELECT * FROM busin_mem";
								pstmt = con.prepareStatement(sql);
								rs = pstmt.executeQuery();

								while (rs.next()) { // 사업자 데이터베이스 훑어보기
									if (rs.getString(1).equals(id)) { // 아이디가 존재할 경우
										idOver = true;
										break;
									}
									if (rs.getString(6).equals(bsName)) { // 업체명이 존재할 경우
										bsNameOver = true;
										break;
									} else {
										continue;
									}
								}
							}
							if (idOver == true) { // 아이디가 중복되는 경우
								String al = "ID가 중복되어 사용할 수 없습니다.";
								System.out.println(al);
								outList.get(i).write(al + "\n");
								outList.get(i).flush();
							}

							if (pw.equals(pw2)) { // 비밀번호란과 비밀번호확인란에 쓴 값이 같을 경우
								pwOk = true;
							} else {// 비밀번호란과 비밀번호확인란에 쓴 값이 다를경우
								pwOk = false;
								String al = "비밀번호와 비밀번호 확인값이 일치하지 않습니다.";
								System.out.println(al);
								outList.get(i).write(al + "\n");
								outList.get(i).flush();
							}
							if (bsNameOver == true) { // 업체명 중복되는 경우
								String al = "업체명이 중복되어 사용할 수 없습니다.";
								System.out.println(al);
								outList.get(i).write(al + "\n");
								outList.get(i).flush();
							}

							if (idOver == false && bsNameOver == false && pwOk == true) { // 아이디와 업체명이 중복되지 않는경우 회원가입 진행
								sql = "INSERT INTO busin_mem(bs_id, bs_pw, bs_name, bs_ph, bs_num, bs_shopn) VALUES(?, ?, ?, ?, ?, ?)";
								pstmt = con.prepareStatement(sql);

								pstmt.setString(1, id);
								pstmt.setString(2, pw);
								pstmt.setString(3, name1); // 사업자명
								pstmt.setString(4, callNum); // 전화번호
								pstmt.setString(5, bsNum); // 사업자번호
								pstmt.setString(6, bsName); // 매장명

								pstmt.executeUpdate();

								String al = "회원가입이 완료되었습니다.";
								System.out.println(al);
								outList.get(i).write(al + "\n");
								outList.get(i).flush();
							}

							// 데이터베이스에 등록 (ID와 업체명 중복검사)
						}

						else if(msg.equalsIgnoreCase("10")) { //사업자 회원정보 관리
	                         String id = inList.get(i).readLine();
	                         String newPW = "???";
	                         String newPW2 = "???"; //새비밀번호 확인
	                         String callNum = "???";
	                         
	                         // 수정된 값 업데이트
	                          
	                         //클라이언트에 입력된 값 불러오기
	                          newPW =  inList.get(i).readLine();
	                          newPW2 =  inList.get(i).readLine();
	                          callNum =  inList.get(i).readLine();
	                          
	                          boolean pwOk = false;
	                          //데이터베이스 갱신작업
	                          //비밀번호 일치하는지 확인
	                          if(newPW.equals(newPW2)) { //비밀번호란과 비밀번호확인란에 쓴 값이 같을 경우
	                             pwOk = true;
	                          }
	                          else {//비밀번호란과 비밀번호확인란에 쓴 값이 다를경우
	                             pwOk = false;
	                             String al = "비밀번호와 비밀번호 확인값이 일치하지 않습니다.";
	                           System.out.println(al);
	                           outList.get(i).write(al+"\n");
	                           outList.get(i).flush();
	                          }
	                          
	                          if(pwOk == true) {
	                             String sql = "UPDATE busin_mem SET bs_pw = ?, bs_ph = ? WHERE bs_id = ?";
	                             PreparedStatement pstmt = con.prepareStatement(sql);
	                             
	                              pstmt.setString(1, newPW);  
	                              pstmt.setString(2, callNum);
	                              pstmt.setString(3, id);
	                              
	                              pstmt.executeUpdate(); 
	                              
	                              String al = "회원정보가 수정되었습니다.";
	                           System.out.println(al);
	                           outList.get(i).write(al+"\n");
	                           outList.get(i).flush();
	                          }

	                      }

						else if (msg.equalsIgnoreCase("11")) { // 사업자 ID 찾기

							String name1 = inList.get(i).readLine();
							String callNum = inList.get(i).readLine();
							System.out.println(name1 + callNum);
							// 데이터베이스를 통해 등록된 ID확인
							String sql = "SELECT * FROM busin_mem";
							PreparedStatement pstmt = con.prepareStatement(sql);
							ResultSet rs = pstmt.executeQuery();

							boolean idOk = false; // 이름 일치여부 판단

							while (rs.next()) {
								if (rs.getString(3).equals(name1)) {
									if (rs.getString(4).equals(callNum)) { // 입력한 정보가 맞다면 ID 출력
										idOk = true;
										String id = rs.getString(1);
										String al = name1 + "님의 아이디는 " + id + " 입니다.";

										System.out.println(al);
										outList.get(i).write(al + "\n");
										outList.get(i).flush();
										break;
									} else { // 이름이 있으나 전화번호와 다를 경우
										idOk = true;
										String al = "이름과 전화번호가 일치하지 않습니다.";
										System.out.println(al);
										outList.get(i).write(al + "\n");
										outList.get(i).flush();
										break;
									}
								}
							}
							if (idOk == false) { // 해당하는 ID정보가 없을 경우
								String al = "가입된 ID가 존재하지 않습니다.";
								System.out.println(al);
								outList.get(i).write(al + "\n");
								outList.get(i).flush();
							}
							// 데이터베이스에서 데이터 찾기
						}

						else if (msg.equalsIgnoreCase("12")) { // 사업자 비밀번호 찾기

							String id = inList.get(i).readLine();
							String name1 = inList.get(i).readLine();
							String callNum = inList.get(i).readLine();
							// 임시 비밀번호 난수 뽑기, 데이터베이스에 업데이트
							String sql = "SELECT * FROM busin_mem";
							PreparedStatement pstmt = con.prepareStatement(sql);
							ResultSet rs = pstmt.executeQuery();

							boolean idOk = false;

							while (rs.next()) {
								if (rs.getString(1).equals(id) && rs.getString(3).equals(name1)
										&& rs.getString(4).equals(callNum)) {
									idOk = true;
								}
							}

							if (idOk == false) { // 입력한 정보값이 틀렸을 경우
								String al = "잘못된 정보 입니다.";
								System.out.println(al);
								outList.get(i).write(al + "\n");
								outList.get(i).flush();
							}

							if (idOk == true) {
								String temPW = "";
								for (int j = 0; j < 4; j++) { // 임시비밀번호 4자리 생성
									double dValue = Math.random();
									int iValue = (int) (dValue * 10);
									temPW = temPW + iValue;
								}
								// 임시비밀번호 데이터베이스에 넣기
								sql = "UPDATE busin_mem SET bs_pw = ? WHERE bs_id = ?";
								pstmt = con.prepareStatement(sql);

								pstmt.setString(1, temPW);
								pstmt.setString(2, id);

								pstmt.executeUpdate();

								String al = name1 + "님의 임시비밀번호는 " + temPW + " 입니다.";
								System.out.println(al);
								outList.get(i).write(al + "\n");
								outList.get(i).flush();

							}

						} else if (msg.equalsIgnoreCase("13")) { // 사업자 매출이력 조회
							
							String id = inList.get(i).readLine(); // 사업자 회원 id
							String dayFirst = inList.get(i).readLine(); // 조회시작날짜 2020-02-02 형식
							String dayLast = inList.get(i).readLine(); // 조회마지막날짜 2020-02-02 형식

							String sql = "SELECT pm_author, dp_comp, pay_amount, pay_day FROM paymentlist WHERE wd_comp = ("
									+ "SELECT bs_shopn FROM busin_mem WHERE bs_id = ? "
									+ ") AND pay_day >= ? AND pay_day <= ?";
							PreparedStatement pstmt = con.prepareStatement(sql);

							pstmt.setString(1, id);
							pstmt.setString(2, dayFirst);
							pstmt.setString(3, dayLast);

							ResultSet rs = pstmt.executeQuery();

							String al = "결제승인번호\t은행/카드사명(입금처)\t총 결제금액\t결제날짜";
							outList.get(i).write(al + "\n");
							outList.get(i).flush();

							while (rs.next()) {
								String p_au = rs.getString(1); // 결제승인번호(8)
								String d_com = rs.getString(2); // 은행 혹은 카드사명(15), 결제수단 회사
								String p_amount = rs.getString(3); // 총 결제금액(10)
								String p_day = rs.getString(4); // 결제날짜(10) 2020-02-02 형식

								al = p_au + "\t" + d_com + "\t" + p_amount + "\t" + p_day; // 조회마지막날짜 2020-02-02 형식
								System.out.println(al);
								outList.get(i).write(al + "\n");
								outList.get(i).flush();

							} // 데이터베이스에서 정보가져오기

							outList.get(i).write("매출이력 조회가 완료되었습니다.\n");
							outList.get(i).flush();

							outList.get(i).write("fin\n");
							outList.get(i).flush();
						}

						else if (msg.equalsIgnoreCase("14")) { // 소비자에게 결제요청보내기
							String bsId = inList.get(i).readLine();
							String bsIp = inList.get(i).readLine();
							String carNum = inList.get(i).readLine();
							String val = inList.get(i).readLine();
							System.out.println(bsId + " 로부터 결제요청 도착");
							// 데이터베이스에서 해당 번호판을 가진 소비자를 찾음
							String sql = "SELECT * FROM consm_mem";
							PreparedStatement pstmt = con.prepareStatement(sql);
							ResultSet rs = pstmt.executeQuery();

							boolean customer = false;
							boolean login = false;
							String customerId = "";

							while (rs.next()) {
								if (rs.getString(6).equals(carNum)) {
									customer = true;
									customerId = rs.getString(1);
									break;
								}
							}
							if (customer == false) {
								String al = "해당 회원을 찾을 수 없습니다.";
								System.out.println(al);
								outList.get(i).write(al + "\n");
								outList.get(i).flush();
							} else if (customer == true) {
								sql = "SELECT * FROM busin_mem";
								pstmt = con.prepareStatement(sql);
								rs = pstmt.executeQuery();
								String shop = "";
								while (rs.next()) {
									if (rs.getString(1).equals(bsId)) {
										shop = rs.getString(6);
										break;
									}
								}
								String customerIp = clientMap.get(customerId); // 소비자 IP
								BufferedWriter j = clientStreamMap.get(customerIp); // 소비자 IP로 입력스트림 구하기
								j.write("pay\n");
								j.flush();

								j.write(bsId + "\n");
								j.flush();

								j.write(shop + "\n");
								j.flush();

								j.write(val + "\n");
								j.flush();

								System.out.println("소비자 정보 찾기 완료. 결제 요청 전송");
							}

						}
						
						else if (msg.equalsIgnoreCase("15")) { // 고객 정보 가져오기
	                         String name1 = "???"; //사업자명
	                         String bsName = "???"; //업체명
	                         String bsNum = "???"; //사업자등록번호
	                         String id = inList.get(i).readLine();
	                         String newPW = "???";
	                         String newPW2 = "???"; //새비밀번호 확인
	                         String callNum = "???";
	                         
	                         // 회원 정보 불러오고 수정된 값 업데이트
	                         String sql = "SELECT * FROM busin_mem";
	                          PreparedStatement pstmt = con.prepareStatement(sql);
	                          ResultSet rs = pstmt.executeQuery();
	                          
	                          while(rs.next()) {
	                             if(rs.getString(1).equals(id)) { //id로 정보 불러오기
	                                
	                                name1 = rs.getString(3);
	                                outList.get(i).write(name1+"\n");
	                                outList.get(i).flush();
	                                
	                                bsName = rs.getString(6);
	                                outList.get(i).write(bsName+"\n");
	                                outList.get(i).flush();
	                                
	                                bsNum = rs.getString(5);
	                                outList.get(i).write(bsNum+"\n");
	                                outList.get(i).flush();
	                                
	                                newPW = rs.getString(2);
	                                outList.get(i).write(newPW+"\n");
	                                outList.get(i).flush();
	                                
	                                newPW2 = rs.getString(2);
	                                outList.get(i).write(newPW2+"\n");
	                                outList.get(i).flush();
	                                
	                                callNum = rs.getString(4);
	                                outList.get(i).write(callNum+"\n");
	                                outList.get(i).flush();
	                             }
	                          }
						}

						else if (msg.equalsIgnoreCase("Y")) { // 결제 승인 알리기 // 결제이력 저장
							System.out.println("결제 승인");
							// 결제 진행 및 결제이력 저장
							String bsId = inList.get(i).readLine(); // 알림보낼 사업자ip
							String csId = inList.get(i).readLine(); // 알림보낼 소비자ip
							String payNum = inList.get(i).readLine(); // 결제수단 번호
							String payCom = inList.get(i).readLine(); // 결제수단 회사명
							String val = inList.get(i).readLine(); // 결제금액
							System.out.println(bsId);
							String bsIp = clientMap.get(bsId);
							String csIp = clientMap.get(csId);

							String sql = "SELECT * FROM busin_mem";
							PreparedStatement pstmt = con.prepareStatement(sql);
							ResultSet rs = pstmt.executeQuery();
							String shop = ""; // 매장명
							while (rs.next()) {
								if (rs.getString(1).equals(bsId)) {
									shop = rs.getString(6);
									break;
								}
							}
							Calendar cal = Calendar.getInstance();
							int year = cal.get(Calendar.YEAR);
							int month = cal.get(Calendar.MONTH) + 1;
							int day = cal.get(Calendar.DAY_OF_MONTH);

							String date = year + "-" + month + "-" + day; // 결제날짜

							sql = "select count(*) from paymentlist";
							pstmt = con.prepareStatement(sql);
							rs = pstmt.executeQuery();

							while (rs.next()) {
								pmAuthor = rs.getInt(1) + 1;
							}

							sql = "INSERT INTO paymentlist(pm_author, con_id, pay_num, wd_comp, dp_comp, pay_amount, pay_day, deal_num) VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
							pstmt = con.prepareStatement(sql);

							pstmt.setString(1, Integer.toString(pmAuthor));
							pstmt.setString(2, csId);
							pstmt.setString(3, payNum);
							pstmt.setString(4, shop);
							pstmt.setString(5, payCom);
							pstmt.setString(6, val);
							pstmt.setString(7, date);
							pstmt.setString(8, Integer.toString(pmAuthor));
							pstmt.executeUpdate();
							System.out.println("결제이력이 저장되었습니다.");

							BufferedWriter j = clientStreamMap.get(bsIp);
							BufferedWriter k = clientStreamMap.get(csIp);

							j.write("Ok\n"); // 사업자에게 결제성공 알림
							j.flush();

							k.write("Ok\n"); // 소비자에게 결제성공 알림
							k.flush();

							System.out.println("결제 결과 전송 완료");
						}

						else if (msg.equalsIgnoreCase("N")) { // 결제실패 알리기
							String bsId = inList.get(i).readLine(); // 알림보낼 사업자ip
							String csId = inList.get(i).readLine(); // 알림보낼 소비자ip

							String bsIp = clientMap.get(bsId);
							String csIp = clientMap.get(csId);

							BufferedWriter j = clientStreamMap.get(bsIp);
							BufferedWriter k = clientStreamMap.get(csIp);

							j.write("No\n"); // 사업자에게 결제성공 알림
							j.flush();

							k.write("No\n"); // 소비자에게 결제성공 알림
							k.flush();

							System.out.println("결제 결과 전송 완료");
						}

						else {
							continue;
						}
					}
				}

			} catch (Exception e) {
				System.out.println(e + "----> ");

			} finally {

				// 예외가 발생할때 퇴장. 해쉬맵에서 해당 데이터 제거.
				// 보통 종료하거나 나가면 java.net.SocketException: 예외발생
				clientMap.remove(name);
				// System.out.println("현재 접속자 수는 "+clientMap.size()+"명 입니다.");
			}
		}// run()------------
	}// class MultiServerRec-------------

}
