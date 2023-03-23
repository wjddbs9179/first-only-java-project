import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import javax.swing.JOptionPane;

public class BMmanager{
	static Scanner sc = new Scanner(System.in);
	private static final int SYSTEMLOGIN = 1;
	private static final int USERLOGIN = 2;
	private static final int NEWUSER = 3;
	private static final int EXIT = 0;


	private static Map<String,String> idList = new HashMap<>();
	private static Set<UserInfo> userInfoSet = new HashSet<>();

	BMmanager(){
		//idList와 userInfoSet 을 저장되어있는 파일을 불러와서 대입
		readData();
		//관리자 id생성 하고 idList에 추가
		newSystem();
		//BMstart()매서드 호출
		BMStart();
	}

	static void showUserInfo(){
		Iterator it = userInfoSet.iterator();
		ArrayList ua = new ArrayList();
		int cnt = 1;
		while(it.hasNext()) {
			ua.add(it.next());
			UserInfo u = (UserInfo)ua.get(cnt-1);
			System.out.print(cnt+". ");
			System.out.println(u);
			cnt++;
		}
	}


	private void BMStart() {
		//메뉴 선택
		try {
			while(true) {
				System.out.println("=================================================================");
				System.out.println("                        대한민국 1등 배달어플              ");
				System.out.println("                            배달의 민족                  ");
				System.out.println("                                         ");
				System.out.println("                      1        2         3              ");
				System.out.println("                    관리자      사용자     회원가입        ");
				System.out.println("                    로그인      로그인                  ");
				System.out.println("                                        ");
				System.out.println("                                          ");
				System.out.println(" 4. 아이디/비밀번호 찾기                       ");
				System.out.println(" 0. 프로그램 종료");
				System.out.println("=================================================================");
				System.out.print("숫자 입력>>");
				int choice = Integer.parseInt(sc.nextLine());
				switch(choice) {
				//관리자 로그인
				case SYSTEMLOGIN :
					systemLogin();
					break;
					//사용자 로그인
				case USERLOGIN :
					userLogin();
					break;
					//회원가입
				case NEWUSER :
					newUser();
					break;
				case 4 :
					searchIdPw();
					break;

					//종료.
				case EXIT :
					JOptionPane.showMessageDialog(null, "오늘도 배달의민족을 이용해 주셔서 감사합니다.");
					System.exit(EXIT);;
				}
			}
		}catch(Exception e) {
			System.out.println("잘못입력하셨습니다.");
			BMStart();
		}
	}
	void searchIdPw(){
		try {
			System.out.println("=================================================================");
			System.out.println("                         아이디/비밀번호 찾기");
			System.out.println("                             ");
			System.out.println("                 1. 아이디 찾기         2. 비밀번호 찾기");
			System.out.println("                             ");
			System.out.println("0. 뒤로가기                                 ");
			System.out.println("=================================================================");
			System.out.println(">>");
			int choice = Integer.parseInt(sc.nextLine());

			switch(choice) {
			case 1 : 
				System.out.println("=================================================================");
				System.out.println("                             본인인증");
				System.out.println();
				System.out.print("       이름 : ");
				String name = sc.nextLine();
				System.out.print("       주민등록번호 : ");
				String jNum = sc.nextLine();
				System.out.print("       휴대폰 번호(숫자만 입력) : ");
				int phone = Integer.parseInt(sc.nextLine());
				System.out.println("=================================================================");
				if(userInfoSet.contains(new UserInfo(null,null,name,jNum,phone))) {
					Iterator it = userInfoSet.iterator();
					UserInfo user = null;
					while(it.hasNext()) {
						UserInfo u = (UserInfo)it.next();
						if(u.equals(new UserInfo(null,null,name,jNum,phone))) {
							user = u;
						}
					}
					int conNum = (int)(Math.random()*899999)+100000;
					if(new String(phone+"").equals(new String(user.phone+""))) {
						JOptionPane.showMessageDialog(null,"인증번호는 "+ conNum+"입니다.");
						System.out.print("인증번호를 입력하세요 >>");
						choice = Integer.parseInt(sc.nextLine());
						if(choice == conNum) {
							JOptionPane.showMessageDialog(null, "회원님의 아이디는 "+user.id+"입니다.");
						}else {
							System.out.println("=================================================================");
							System.out.println("                   ");
							System.out.println("                        인증번호가 틀렸습니다.");
							System.out.println("                 ");
							System.out.println("=================================================================");
						}
					}else {
						System.out.println("=================================================================");
						System.out.println();
						System.out.println("                       없는 휴대폰 번호입니다.");
						System.out.println();
						System.out.println("=================================================================");
					}
				}else {
					System.out.println("=================================================================");
					System.out.println("                     ");
					System.out.println("                       등록되지 않은 회원입니다.");
					System.out.println("                     ");
					System.out.println("=================================================================");
				}
				break;
			case 2 :
				System.out.println("=================================================================");
				System.out.println("                              본인인증");
				System.out.println();
				System.out.print("         ID : ");
				String id = sc.nextLine();
				System.out.print("         이름 : ");
				name = sc.nextLine();
				System.out.print("         주민등록번호 : ");
				jNum = sc.nextLine();
				System.out.print("         휴대폰 번호(숫자만 입력) : ");
				phone = Integer.parseInt(sc.nextLine());
				System.out.println("=================================================================");
				if(userInfoSet.contains(new UserInfo(null,null,name,jNum,phone))) {
					Iterator it = userInfoSet.iterator();
					UserInfo user = null;
					while(it.hasNext()) {
						UserInfo u = (UserInfo)it.next();
						if(u.equals(new UserInfo(null,null,name,jNum,phone))) {
							user = u;
						}
					}
					int conNum = (int)(Math.random()*899999)+100000;
					if(!(id.equals(user.id))) {
						System.out.println("=================================================================");
						System.out.println("                ");
						System.out.println("                         아이디가 틀렸습니다.");
						System.out.println("                 ");
						System.out.println("=================================================================");
						break;
					}
					if(new String(phone+"").equals(new String(user.phone+""))&&id.equals(user.id)) {
						JOptionPane.showMessageDialog(null,"인증번호는 "+ conNum+"입니다.");
						System.out.print("인증번호를 입력하세요 >>");
						choice = Integer.parseInt(sc.nextLine());
						if(choice == conNum) {
							JOptionPane.showMessageDialog(null, "회원님의 비밀번호는 "+user.password+"입니다.");
						}else {
							System.out.println("=================================================================");
							System.out.println("                   ");
							System.out.println("                      인증번호가 틀렸습니다.");
							System.out.println("                 ");
							System.out.println("=================================================================");
						}
					}else {
						System.out.println("=================================================================");
						System.out.println();
						System.out.println("                       없는 휴대폰 번호입니다.");
						System.out.println();
						System.out.println("=================================================================");
					}
				}else {
					System.out.println("=================================================================");
					System.out.println("                     ");
					System.out.println("                       등록되지 않은 회원입니다.");
					System.out.println("                     ");
					System.out.println("=================================================================");
				}
				break;
			case 0 :
				break;
			}
		}catch(Exception e) {
			System.out.println("잘못입력하셨습니다.");
			searchIdPw();
		}
	}




	//아이디와 비밀번호를 입력받고 관리자 계정의 아이디와 비밀번호랑 일치하면 SystemMenu클래스의 생성자 호출
	public void systemLogin() {
		System.out.print("id : ");
		String id = sc.nextLine();
		System.out.print("passWord : ");
		String password = sc.nextLine();
		if(id.equals("system")&&password.equals(idList.get("system"))) {
			System.out.println();
			System.out.println("로그인 성공!!");
			System.out.println();
			new SystemMenu();
		}else {
			System.out.println();
			System.out.println("로그인 실패");
			System.out.println();
			return;
		}
	}
	//아이디와 비밀번호를 입력받고 idList에 있는 key,value중에 일치하는게 있으면 로그인 성공
	//로그인 성공시 UserMenu생성자 호출
	public void userLogin() {
		System.out.print("id : ");
		String id = sc.nextLine();
		System.out.print("password : ");
		String password = sc.nextLine();
		if(password.equals(idList.get(id))&&!(id.equals("system"))) {
			System.out.println();
			System.out.println("로그인 성공!!");
			System.out.println();
			new UserMenu(id);
		}else {
			System.out.println();
			System.out.println("로그인 실패");
			System.out.println();
			return;
		}
	}
	//아이디를 입력받아 idList에 key값에 있는지 확인 이미 있는 key값이면 중복된 아이디 입니다. 출력
	//아니면 password 이름 주민등록번호를 입력받음. 
	//입력받은 id,password,name,jNum을 가지고 새로운 userInfo객체를 생성하여 userInfoSet에 저장
	//저장실패(기존에 저장되어 있는 userInfo객체와  같은이름,주민등록번호를 가진 사람인 경우 추가안됨.)인 경우 "이미 가입된 회원입니다." 출력
	public void newUser() {
		try {
			System.out.println("=================================================================");
			System.out.println("                              회원가입");
			System.out.println();
			System.out.print("       New Id : ");
			String id = sc.nextLine();
			if(idList.containsKey(id)) {
				System.out.println("=================================================================");
				System.out.println();
				System.out.println("                          중복된 아이디 입니다.");
				System.out.println();
				System.out.println("=================================================================");
			}else { 
				System.out.print("       password : ");
				String password = sc.nextLine();
				System.out.print("       이름 : ");
				String name = sc.nextLine();
				System.out.print("       주민등록번호 : ");
				String jNum = sc.nextLine();
				int phone;
				while(true) {
					try {
						System.out.print("       휴대폰 번호(숫자만 입력) : ");
						phone = Integer.parseInt(BMmanager.sc.nextLine());
						break;
					}catch(Exception e) {System.out.println("         잘못입력하셨습니다.");}
				}System.out.println("=================================================================");
				if(userInfoSet.add(new UserInfo(id,password,name,jNum,phone))){
					idList.put(id, password);
					System.out.println("=================================================================");
					System.out.println();
					System.out.println("                        회원가입이 완료되었습니다.");
					System.out.println();
					System.out.println("=================================================================");
					writeData();
				}else {
					System.out.println("=================================================================");
					System.out.println();
					System.out.println("                         이미 가입된 회원입니다.");
					System.out.println();
					System.out.println("=================================================================");
				}
			}
		}catch(Exception e) {
			System.out.println("잘못입력하셨습니다.");
			newUser();
		}
	}

	//관리자 계정을 저장하는 매서드 BMmanager 생성자 호출시 자동으로 호출 되도록 해놨음.
	private void newSystem() {
		idList.put("system","1234");
	}
	//수정된 idList와 userInfoSet을 파일에 저장하는 매서드
	static void writeData() {
		try {
			String fileName = "IdListInfo.ser";
			ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(fileName));
			String fileName2 = "UserListInfo.ser";
			ObjectOutputStream out2 = new ObjectOutputStream(new FileOutputStream(fileName2));

			out.writeObject(idList);
			out2.writeObject(userInfoSet);
			out.close();
			out2.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	//파일에 저장되어 있는 객체(idList,userInfoSet)을 불러 오는 매서드
	static void readData() {
		try {
			String fileName = "IdListInfo.ser";
			ObjectInputStream in = new ObjectInputStream(new FileInputStream(fileName));
			String fileName2 = "UserListInfo.ser";
			ObjectInputStream in2 = new ObjectInputStream(new FileInputStream(fileName2));

			idList = (HashMap)in.readObject();
			userInfoSet = (HashSet)in2.readObject();
			Iterator it = userInfoSet.iterator();
			while(it.hasNext()) {
				UserInfo u = (UserInfo)it.next();
				u.usedMoney=0;
				for(int i=0;i<u.orderList.size();i++) {
					OrderList o = (OrderList)u.orderList.get(i);
					if(o.nowDate.isEqual(LocalDate.now())){
						u.usedMoney+=o.price;
						if(u.usedMoney>=150000) {
							u.Grade="VVVIP";
						}else if(u.usedMoney>=100000) {
							u.Grade="VVIP";
						}else if(u.usedMoney>=50000) {
							u.Grade="VIP";
						}else {
							u.Grade="일반회원";
						}
					}
				}
			}
			writeData();
			in.close();
			in2.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}
