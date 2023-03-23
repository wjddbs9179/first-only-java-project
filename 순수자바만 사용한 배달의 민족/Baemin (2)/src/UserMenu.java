import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.swing.JOptionPane;

//UserLogin 성공시 수행될 매서드들을 모아놓은 클래스.
public class UserMenu {
	private Set<UserInfo> userInfoSet = new HashSet();
	private static Set<Food> foodMenuSet = new HashSet();
	private static Set<Shop> shopList = new HashSet();
	private static Map<String,String> idList = new HashMap();
	String id;
	//userInfoSet이 저장되어 있는 파일을 불러와서 대입 해야되기 때문에 readUserInfoData()매서드를 사용	
	//user로그인 시 아이디를 가지고 userInfoSet에서 객체를 찾기위해 변수 id를 입력받은 id로 초기화 하였음.
	//생성자를 호출하면 위의 작업 수행 후 userMenuStart()호출
	UserMenu(String id){
		readUserInfoData();
		readFoodData();
		this.id = id;
		userMenuStart();

	}
	//유저 로그인 성공시 수행 할 수 있는 메뉴 선택
	void userMenuStart(){
		readUserInfoData();
		try {
			while(true) {
				System.out.println("=================================================================");
				System.out.println("                                사용자                    ");
				System.out.println(searchUser().address+"                       ");
				System.out.println(searchUser().Grade);
				System.out.println("보유쿠폰 : "+searchUser().coupon);
				System.out.println("                                           ");
				System.out.println("                 1          2         3         4      ");
				System.out.println("               주소설정      주문하기    주문내역     리뷰작성   ");
				System.out.println("                                     보기               ");
				System.out.println("                                          ");
				System.out.println();
				System.out.println("5. 내가 쓴 리뷰보기");
				System.out.println("6. 회원정보보기                                  ");
				System.out.println("7. 회원탈퇴                               ");
				System.out.println("0. 로그아웃                                  ");
				System.out.println("=================================================================");
				System.out.print(">>");
				int choice = Integer.parseInt(BMmanager.sc.nextLine());
				switch(choice) {
				//주소변경
				case 1 :
					newAddress();
					break;
					//주문하기
				case 2 :
					try {
						order();
					}catch(Exception e) {
						order();
					}
					break;
					//주문내역 5개
				case 3 :
					orderList();
					break;
					//회원탈퇴
				case 4 :
					try {
						reviewWrite();
					}catch(Exception e) {
						System.out.println("잘못입력하셨습니다.");
						reviewWrite();
					}
					break;
					//나의 정보 출력(이름,id,password,주민등록번호)
				case 5 :
					showMyReview();
					break;
				case 6 :
					displayUserInfo();
					break;
				case 7 :
					withdraw();
					break;
					//로그아웃.
				case 0 :
					new BMmanager();
				}
			}
		}catch(Exception e) {
			System.out.println("잘못입력하셨습니다.");
			userMenuStart();
		}
	}
	public static ArrayList<Shop> showShop(int choice) {
		System.out.println("=================================================================");
		System.out.println("                             식당목록");
		System.out.println();
		int cnt = 1;
		Iterator it = shopList.iterator();
		ArrayList sl = new ArrayList();
		switch(choice) {
		case 1 :
			while(it.hasNext()) {
				Shop s = (Shop)it.next();
				if(s instanceof KoreaShop) {
					System.out.print(cnt+". ");
					System.out.println(s);
					sl.add(s);
					cnt++;
				}
			}
			break;
		case 2 :
			while(it.hasNext()) {
				Shop s = (Shop)it.next();
				if(s instanceof ChinaShop) {
					System.out.print(cnt+". ");
					System.out.println(s);
					sl.add(s);
					cnt++;
				}
			}
			break;
		case 3 :
			while(it.hasNext()) {
				Shop s = (Shop)it.next();
				if(s instanceof JapanShop) {
					System.out.print(cnt+". ");
					System.out.println(s);
					sl.add(s);
					cnt++;
				}
			}
			break;
		case 4 :
			while(it.hasNext()) {
				Shop s = (Shop)it.next();
				if(s instanceof WesternShop) {
					System.out.print(cnt+". ");
					System.out.println(s);
					sl.add(s);
					cnt++;
				}
			}
			break;
		}
		System.out.println("                      ");
		System.out.println("음식점(식당)번호를 입력하세요.");
		System.out.println("=================================================================");
		System.out.print("숫자 입력(0.뒤로가기)>>");
		return sl;
	}



	Shop shopMenu(int shopNum, ArrayList sl) {
		if(shopNum==0) {order();}
		Shop s = (Shop)sl.get(shopNum-1);
		System.out.println("=================================================================");
		System.out.println("                               "+s.shopName+"       ");
		System.out.println("                                    ");
		System.out.println("                         1       2       0          ");
		System.out.println("                       메뉴선정   리뷰보기  뒤로가기        ");
		System.out.println("                                    ");
		System.out.println("                                    ");
		System.out.println("=================================================================");
		System.out.print("숫자입력 >>");
		return s;
	}

	void buyFood(int cn) {
		try {
			ArrayList sl = showShop(cn);
			int shopNum = Integer.parseInt(BMmanager.sc.nextLine());
			Shop s = shopMenu(shopNum,sl);
			int choice = Integer.parseInt(BMmanager.sc.nextLine());
			switch(choice) {
			case 1 :
				choiceMenu(s,cn);
				choice = addAfter();
				switch(choice) {
				case 1 :
					charge();
				case 2 :
					order();
				case 3 :
					showCart();
				case 0 :
					order();
				}
			case 2 :
				showReview(s);
			case 0 :
				userMenuStart();
			default :
				System.out.println("잘못입력하셨습니다.");
				if(cn==1)
					buyKoreaFood();
				if(cn==2)
					buyChinaFood();
				if(cn==3)
					buyJapanFood();
				if(cn==4)
					buyWesternFood();
			}
		}catch(Exception e) {
			System.out.println("잘못입력하셨습니다.");
			if(cn==1)
				buyKoreaFood();
			if(cn==2)
				buyChinaFood();
			if(cn==3)
				buyJapanFood();
			if(cn==4)
				buyWesternFood();
		}

	}

	void charge() {
		try {
			int foodCnt = 1;
			System.out.println("=================================================================");
			System.out.println("                            포장/배달 선택");
			System.out.println();
			System.out.println("선택하신 메뉴 리스트 입니다.");
			System.out.println();
			for(int i=0;i<searchUser().cart.size();i++) {
				System.out.print(foodCnt+". ");
				System.out.println(searchUser().cart.get(foodCnt-1));
				foodCnt++;
			}
			System.out.println("                           ");
			System.out.println("                      1.포장              2.배달");
			System.out.println("=================================================================");
			System.out.print("선택>>");
			int choice = Integer.parseInt(BMmanager.sc.nextLine());
			int price = 0;
			Shop cs = null;
			for(int i=0;i<searchUser().cart.size();i++) {
				Food f = (Food)searchUser().cart.get(i);
				Iterator it = shopList.iterator();
				while(it.hasNext()) {
					Shop fs = (Shop)it.next();
					if(fs.shopName.equals(f.shopName))
						cs = fs;
				}
				if(cs.promotion>0) {
					price += (int)((double)f.price*cs.promotion);
				}else {
					price += f.price;
				}
			}
			Delivery delivery = null;
			boolean iscoupon = false;
			switch(choice) {
			case 1 :
				delivery = new Delivery(false);
				System.out.println("포장을 선택하셨습니다.");
				System.out.println("=================================================================");
				System.out.println("                              예약선택");
				System.out.println("                               ");
				System.out.println("                      받아갈 시간을 예약 하시겠습니까?");
				System.out.println("                          ");
				System.out.println("                     1.예                 2.아니요");
				System.out.println("=================================================================");
				System.out.print(">>");
				choice = Integer.parseInt(BMmanager.sc.nextLine());
				switch(choice) {
				case 1 :
					System.out.println("=================================================================");
					System.out.println("                           예약시간 설정 ");
					System.out.println("                         ");
					System.out.println("                     몇 초 뒤에 받아갈지 입력해주세요");
					System.out.println("=================================================================");
					System.out.print(">>");
					delivery.reserveTime = Integer.parseInt(BMmanager.sc.nextLine());
					System.out.println("예약 되었습니다.");
					break;
				case 2 :
					break;
				}
				break;
			case 2 :
				delivery = new Delivery(true);
				System.out.println("배달을 선택하셨습니다.");
				System.out.println("배달비용 2000원이 추가됩니다.");
				if(searchUser().coupon>0) {
					try {
						System.out.println("=================================================================");
						System.out.println("                               쿠폰사용                  ");
						System.out.println("                                   ");
						System.out.println("               쿠폰을 사용 하시겠습니까? 쿠폰 사용시 배달비 무료.");
						System.out.println("               보유쿠폰 : "+searchUser().coupon+"    ");
						System.out.println("                            ");
						System.out.println("                   1.예                      2.아니요         ");
						System.out.println("=================================================================");
						System.out.print(">>");
						choice = Integer.parseInt(BMmanager.sc.nextLine());
					}catch(Exception e) {
						System.out.println("잘못 입력하셨습니다.");
						charge();
					}
					switch(choice) {
					case 1 :
						iscoupon = true;
						break;
					case 2 :
						price +=2000;
						break;
					}
				}else {price+=2000;}
				break;


			}

			Food f = searchUser().cart.get(0);
			System.out.println("===========================최종 결제 내역============================");
			System.out.println("음식점 : "+f.shopName);
			System.out.println("음식명 : "+f.foodName+" 외 "+(searchUser().cart.size()-1)+"개");
			System.out.println("비용    : "+price);
			System.out.println("주문 날짜 : "+LocalDate.now()+" "+LocalTime.now().withNano(0));
			System.out.println("=================================================================");
			System.out.println();
			System.out.println("=================================================================");
			System.out.println("                         결제 하시겠습니까?");
			System.out.println("                                    ");
			System.out.println("                     1.예                2.아니요");
			System.out.println("                                     ");
			System.out.println("=================================================================");
			System.out.print(">>");
			choice = Integer.parseInt(BMmanager.sc.nextLine());
			switch(choice) {
			case 1 :
				if(iscoupon) {searchUser().coupon--;}
				System.out.println("결제 되었습니다.");
				Shop s = null;
				for(int i=0;i<searchUser().cart.size();i++) {
					f = searchUser().cart.get(i);
					f.eatCount++;
					Iterator it = shopList.iterator();
					while(it.hasNext()) {
						Shop tmp = (Shop)it.next();
						if(tmp.shopName.equals(f.shopName)) {
							s = tmp;
						}
					}
					if(s.promotion>0) {
						OrderList ol = new OrderList(f.shopName,f.foodName,(int)(f.price*(s.promotion)));
						searchUser().orderList.add(ol);
						searchUser().reviewList.add(ol);
					}else {
						OrderList ol = new OrderList(f.shopName,f.foodName,f.price);
						searchUser().orderList.add(ol);
						searchUser().reviewList.add(ol);
					}
				}
				searchUser().cart.clear();
				searchUser().usedMoney+=price;
				for(int i=0;i<3;i++) {
					if(searchUser().Grade.equals("VVIP")&&searchUser().usedMoney>=150000) {
						searchUser().Grade = "VVVIP";
						searchUser().coupon++;
						System.out.println("=================================================================");
						System.out.println("                                ");
						System.out.println("                  *****회원님의 등급이 상승하였습니다.*****");
						System.out.println("                                      ");
						System.out.println("=================================================================");
					}else if(searchUser().Grade.equals("VIP")&&searchUser().usedMoney>=100000) {
						searchUser().Grade = "VVIP";
						searchUser().coupon++;
						System.out.println("=================================================================");
						System.out.println("                                ");
						System.out.println("                  *****회원님의 등급이 상승하였습니다.*****");
						System.out.println("                                      ");
						System.out.println("=================================================================");
					}else if(searchUser().Grade.equals("일반회원")&&searchUser().usedMoney>=50000) {
						searchUser().Grade = "VIP";
						searchUser().coupon++;
						System.out.println("=================================================================");
						System.out.println("                                ");
						System.out.println("                  *****회원님의 등급이 상승하였습니다.*****");
						System.out.println("                                      ");
						System.out.println("=================================================================");
					}
				}
				writeFoodData();
				writeUserInfoData();
				delivery.start();
				userMenuStart();
				break;
			case 2 :
				order();
			}
		}catch(Exception e) {
			System.out.println("잘못입력하셨습니다.");
			charge();
		}
	}


	void showMyReview() {
		if(searchUser().myReview.isEmpty()) {
			System.out.println("=================================================================");
			System.out.println();
			System.out.println("                       작성한 리뷰가 없습니다.");
			System.out.println();
			System.out.println("=================================================================");
			userMenuStart();
		}
		try {
			System.out.println("=================================================================");
			System.out.println("                      내가 쓴 리뷰 목록입니다.");
			System.out.println();
			searchUser().showMyReview();
			System.out.println("=================================================================");
			System.out.print("0.뒤로가기>>");
			int choice = Integer.parseInt(BMmanager.sc.nextLine());
			if(choice==0) {userMenuStart();}
		}catch(Exception e) {
			System.out.println("잘못입력하셨습니다.");
			showMyReview();
		}

	}







	void reviewWrite() {
		try {
			Iterator it = userInfoSet.iterator();
			ArrayList ol = new ArrayList();
			if(searchUser().reviewList.isEmpty()) {
				System.out.println("=================================================================");
				System.out.println();
				System.out.println("                    리뷰를 작성할 주문내역이 없습니다."); 
				System.out.println();
				System.out.println("=================================================================");
				userMenuStart();
			}
			ol = (ArrayList)searchUser().reviewList;
			for(int i =0;i<ol.size();i++) {
				System.out.println(i+1);
				if(ol.isEmpty()) {System.out.println("주문내역이 없습니다."); userMenuStart();}
				OrderList o = (OrderList)ol.get(i);
				o.showOderList();
			}
			System.out.println("리뷰를 작성할 주문내역을 선택하세요.");
			System.out.print("숫자 입력(0.뒤로가기) >>");
			int choice = Integer.parseInt(BMmanager.sc.nextLine());
			if(choice==0) {
				userMenuStart();
			}
			OrderList o = (OrderList)ol.get(choice-1);
			Shop s = null;
			it = shopList.iterator();
			while(it.hasNext()) {
				Shop stmp = (Shop)it.next();
				if(stmp.shopName.equals(o.shopName)) {
					s = stmp;
				}
			}
			String review = JOptionPane.showInputDialog("리뷰 내용을 입력하세요.");
			if(review==null) {}
			else {
				Review r = new Review(id+" "+review,s.shopName);
				s.reviewList.add(r);
				searchUser().reviewList.remove(o);
				searchUser().myReview.add(r);
				writeFoodData();
				writeUserInfoData();
			}

		}catch(Exception e) {
			System.out.println("잘못입력하셨습니다.");
			reviewWrite();
		}

	}
	//주소변경하는 매서드
	//현재 주소를 출력하고 새로운 주소를 입력받도록 설계
	//자신의 객체를 불러오기 위해 searchUser()매서드를 작성하여 사용하였음.
	void newAddress() {
		try {
			if(searchUser().address.equals("주소를 설정해주세요.")) {
				System.out.println("=================================================================");
				System.out.println("                            주소설정");
				System.out.println("                    ");
				System.out.println("                         설정하시겠습니까?");
				System.out.println("                  1.예                   2.아니요");
				System.out.println("=================================================================");
				System.out.print(">>");
				int choice = Integer.parseInt(BMmanager.sc.nextLine());
				switch(choice) {
				case 1 :
					System.out.print("주소를 입력하세요>>");
					String address = BMmanager.sc.nextLine();
					searchUser().address = address;
					System.out.println("주소가 변경되었습니다.");
					writeUserInfoData();
					userMenuStart();
				case 2 : 
					userMenuStart();
				}

			}
			System.out.println("=================================================================");
			System.out.println("                            주소변경");
			System.out.println();
			System.out.println("               현재주소 : "+searchUser().address);
			System.out.println("                      ");
			System.out.println("                         변경하시겠습니까?");
			System.out.println("                  1.예                    2.아니요");
			System.out.println("=================================================================");
			System.out.print(">>");
			int choice = Integer.parseInt(BMmanager.sc.nextLine());
			switch(choice) {
			case 1 :
				System.out.print("주소를 입력하세요>>");
				String address = BMmanager.sc.nextLine();
				searchUser().address = address;
				System.out.println("주소가 변경되었습니다.");
				writeUserInfoData();
			case 2 : 
				break;
			}
		}catch(Exception e) {
			System.out.println("잘못입력하셨습니다.");
			newAddress();
		}
	}
	//주문하기
	void order() {
		try {
			if(searchUser().address.equals("주소를 설정해주세요.")) {
				System.out.println("주소를 설정해주세요.");
				newAddress();
			}
			System.out.println("=================================================================");
			System.out.println("                               주문하기                  ");
			System.out.println("                                           ");
			System.out.println("                  1         2          3         4     ");
			System.out.println("                 한식        중식        일식       양식      ");
			System.out.println("                                            ");
			System.out.println("5.추천메뉴보기");
			System.out.println("6.장바구니 보기                                 ");
			System.out.println("0.뒤로가기      ");
			System.out.println("=================================================================");
			System.out.print("숫자입력>>");
			int choice = Integer.parseInt(BMmanager.sc.nextLine());
			switch(choice) {
			case 1 :
				buyKoreaFood();
				break;
			case 2 :
				buyChinaFood();
				break;
			case 3 :
				buyJapanFood();
				break;
			case 4 :
				buyWesternFood();
				break;
			case 5 :
				recommend();
				break;
			case 6 :
				showCart();
				break;
			case 0 :
				userMenuStart();
			}
		}catch(Exception e) {
			System.out.println("잘못입력하셨습니다.");
			order();
		}
	}

	void showCart() {
		if(searchUser().cart.isEmpty()) {
			System.out.println("=================================================================");
			System.out.println();
			System.out.println("                        장바구니가 비어있습니다.");
			System.out.println();
			System.out.println("=================================================================");
			order();
		}
		try {
			System.out.println("=================================================================");
			System.out.println("                             장바구니");
			System.out.println();
			int foodCnt = 1;
			for(int i=0;i<searchUser().cart.size();i++) {
				System.out.print(foodCnt+". ");
				System.out.println(searchUser().cart.get(foodCnt-1));
				foodCnt++;
			}
			System.out.println();
			System.out.println("               1. 결제하기                  2. 목록삭제       ");
			System.out.println();
			System.out.println("0.뒤로가기");
			System.out.println("=================================================================");
			System.out.print(">>");
			int choice = Integer.parseInt(BMmanager.sc.nextLine());
			switch(choice) {
			case 1 :
				charge();
			case 2 :
				while(true) {
				System.out.println("=================================================================");
				System.out.println("                           장바구니 삭제");
				System.out.println();
				foodCnt = 1;
				for(int i=0;i<searchUser().cart.size();i++) {
					System.out.print(foodCnt+". ");
					System.out.println(searchUser().cart.get(foodCnt-1));
					foodCnt++;
				}
				System.out.println();
				System.out.println("삭제할 음식을 선택하세요.");
				System.out.println("=================================================================");
				System.out.print("선택(0.뒤로가기)>>");
				choice = Integer.parseInt(BMmanager.sc.nextLine());
				if(choice==0)
					showCart();
				searchUser().cart.remove(choice-1);
				writeUserInfoData();
				}
			case 0 :
				order();
			default :
				showCart();
			}
		}catch(Exception e) {
			System.out.println("잘못입력하셨습니다.");
			showCart();
		}
	}


	void recommend() {
		Iterator it = foodMenuSet.iterator();
		ArrayList fl = new ArrayList();
		while(it.hasNext()) {
			Food f = (Food)it.next();
			fl.add(f);
		}
		Collections.sort(fl);
		int foodcnt = 1;
		System.out.println("=================================================================");
		System.out.println("                        추천메뉴 리스트 입니다.");
		System.out.println("                                      ");
		for(int i=fl.size()-1;i>=0;i--) {
			System.out.print(foodcnt+". ");
			System.out.println(fl.get(i));
			foodcnt++;
			if(foodcnt==6) {break;}
		}
		System.out.println("                                     ");
		System.out.println("                      뒤로가려면 엔터를 눌러주세요");
		System.out.println("=================================================================");
		String enter = BMmanager.sc.nextLine();
		order();
	}

	void showReview(Shop s){
		if(s.reviewList.size()==0) {
			System.out.println("=================================================================");
			System.out.println();
			System.out.println("                        작성된 리뷰가 없습니다.");
			System.out.println();
			System.out.println("=================================================================");
			if(s instanceof KoreaShop)
				buyKoreaFood();
			if(s instanceof ChinaShop)
				buyChinaFood();
			if(s instanceof JapanShop)
				buyJapanFood();
			if(s instanceof WesternShop)
				buyWesternFood();
		}
		System.out.println("=================================================================");
		System.out.println("                      "+s.shopName+"의 리뷰");
		System.out.println();
		for(int i=s.reviewList.size()-1;i>=0;i--) {
			Review r = (Review)s.reviewList.get(i);
			r.showRiview();
			System.out.println();
		}
		System.out.println("=================================================================");
		if(s instanceof KoreaShop)
			buyKoreaFood();
		if(s instanceof ChinaShop)
			buyChinaFood();
		if(s instanceof JapanShop)
			buyJapanFood();
		if(s instanceof WesternShop)
			buyWesternFood();
	}
	int addAfter() {
		System.out.println("=================================================================");
		System.out.println();
		System.out.println("                  선택하신 메뉴가 장바구니에 추가되었습니다.");
		System.out.println("                                          ");
		System.out.println("                   1            2            3");
		System.out.println("                 결제하기      추가주문하기     장바구니보기 ");
		System.out.println();
		System.out.println("0.뒤로가기");
		System.out.println("=================================================================");
		System.out.print(">>");
		int choice = 0;
		do {
			try {
				choice = Integer.parseInt(BMmanager.sc.nextLine());
			}catch(Exception e) {
				System.out.println("잘못입력하셨습니다.");
				return addAfter();
			}
		}
		while(!(choice==1||choice==2||choice==3||choice==0));
		return choice;
	}


	void choiceMenu(Shop s,int cs){
		try {
			int cnt = 1;
			Iterator it = foodMenuSet.iterator();
			ArrayList fl = new ArrayList();
			System.out.println("=================================================================");
			System.out.println("              "+s.shopName+"의 메뉴 리스트입니다.");
			System.out.println();
			while(it.hasNext()) {
				Food f = (Food)it.next();
				if(f.shopName.equals(s.shopName)&&f.shopAdress.equals(s.address)) {
					System.out.print(cnt+". ");
					System.out.println(f);
					fl.add(f);
					cnt++;
				}
			}
			if(fl.isEmpty()){System.out.println("메뉴가 없습니다."); 
			if(cs==1)
				buyKoreaFood();
			if(cs==2)
				buyChinaFood();
			if(cs==3)
				buyJapanFood();
			if(cs==4)
				buyWesternFood();
			}
			System.out.println("                    ");
			System.out.println("메뉴를 선택하세요.");
			System.out.println("=================================================================");
			System.out.print("메뉴 선택(번호) : ");
			int foodNum = Integer.parseInt(BMmanager.sc.nextLine());
			Food f = (Food)fl.get(foodNum-1);
			searchUser().cart.add(f);
			writeUserInfoData();
		}catch(Exception e) {
			System.out.println("잘못입력하셨습니다.");
			choiceMenu(s,cs);
		}
	}



	//한국 식당을 선택하여 음식주문
	void buyKoreaFood() {
		buyFood(1);
	}
	//중국식당을 선택하여 음식주문
	void buyChinaFood() {
		buyFood(2);
	}
	//일본식당을 선택하여 음식주문
	void buyJapanFood() {
		buyFood(3);
	}
	void buyWesternFood() {
		buyFood(4);
	}

	void showOrderList(LocalDate now) {
		for(int i=searchUser().orderList.size()-1;i>=0;i--) {
			OrderList o = (OrderList)searchUser().orderList.get(i);
			if(o.nowDate.isAfter(now))
				o.showOderList();	
		}
		System.out.print("0.뒤로가기>>");
		int choice =Integer.parseInt(BMmanager.sc.nextLine());
		if(choice==0) {
			orderList();
		}
	}


	//주문내역(최근 5개)
	void orderList() {
		try {
			if(searchUser().orderList.isEmpty()) {
				System.out.println("=================================================================");
				System.out.println("                             주문 내역                ");
				System.out.println("                                       ");
				System.out.println("                            ");
				System.out.println("                          주문내역이 없습니다.      ");
				System.out.println("                                       ");
				System.out.println("                                       ");
				System.out.println("                               ");
				System.out.println("=================================================================");
				userMenuStart();
			}
			Iterator it = userInfoSet.iterator();
			System.out.println("=================================================================");
			System.out.println("                               주문 내역                ");
			System.out.println("                                       ");
			System.out.println("                      1     2     3     4     5        ");
			System.out.println("                     오늘    3일   일주일   한달   3개월       ");
			System.out.println("                                       ");
			System.out.println("                                       ");
			System.out.println(" 0.뒤로가기                              ");
			System.out.println("=================================================================");
			System.out.print(">>");
			int choice = Integer.parseInt(BMmanager.sc.nextLine());
			switch(choice){
			case 1 :
				LocalDate now = LocalDate.now().minusDays(1);
				showOrderList(now);
			case 2 :
				now = LocalDate.now().minusDays(4);
				showOrderList(now);
			case 3 :
				now = LocalDate.now().minusWeeks(1);
				showOrderList(now);
			case 4 :
				now = LocalDate.now().minusMonths(1);
				showOrderList(now);
			case 5 :
				now = LocalDate.now().minusMonths(3);
				showOrderList(now);
			case 0 :
				userMenuStart();
			}
		}catch(Exception e) {
			System.out.println("잘못입력하셨습니다.");
			orderList();
		}
	}
	//	//회원탈퇴
	void withdraw() {
		try {
			System.out.println("=================================================================");
			System.out.println("                              회원탈퇴              ");
			System.out.println("                                 ");
			System.out.println("                        정말로 탈퇴 하시겠습니까?               ");
			System.out.println("                                    ");
			System.out.println("                  1.예                        2.아니요       ");
			System.out.println("                                               ");
			System.out.println("=================================================================");
			System.out.print("숫자 선택>>");
			int choice = Integer.parseInt(BMmanager.sc.nextLine());
			switch(choice) {
			case 1 :
				idList.remove(id);
				userInfoSet.remove(searchUser());
				writeUserInfoData();
				System.out.println("아이디가 삭제되었습니다.");
				new BMmanager();
			case 2 :
				return;
			default :
				System.out.println("잘못입력하셨습니다.");
				withdraw();
			}
		}catch(Exception e) {
			System.out.println("잘못입력하셨습니다.");
			withdraw();
		}

	}
	//UserInfoSet에 파일을 불러와 대입. 현재 회원의 객체를 가져오기 위해 사용.
	void readUserInfoData(){
		try {
			String fileName = "IdListInfo.ser";
			ObjectInputStream in = new ObjectInputStream(new FileInputStream(fileName));
			String fileName2 = "UserListInfo.ser";
			ObjectInputStream in2 = new ObjectInputStream(new FileInputStream(fileName2));

			idList = (HashMap)in.readObject();
			userInfoSet = (HashSet)in2.readObject();
			in.close();
			in2.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	//수정사항을 파일에 저장 하는 매서드
	void writeUserInfoData(){
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

	//자기자신의 객체를 userInfoSet에서 찾아오는 매서드
	UserInfo searchUser() {
		Iterator ui = userInfoSet.iterator();
		UserInfo myId = null;
		while(ui.hasNext()) {
			UserInfo u = (UserInfo)ui.next();
			if(this.id.equals(u.id)) {
				myId = u;
			}
		}
		return myId;
	}


	//자기자신의 정보를 출력.
	void displayUserInfo() {
		try {
			System.out.println("=================================================================");
			System.out.println("                            내 정보");
			System.out.println("                 ");
			searchUser().showUserInfo();
			System.out.println("                               ");
			System.out.println("             1. 비밀번호 변경              2. 휴대폰번호 변경");
			System.out.println("                               ");
			System.out.println("  3. 등급포인트 안내");
			System.out.println("  0. 뒤로가기");
			System.out.println("=================================================================");
			System.out.print(">>");
			int choice = Integer.parseInt(BMmanager.sc.nextLine());
			switch(choice) {
			case 1 :
				System.out.print("변경할 비밀번호 : ");
				String password = BMmanager.sc.nextLine();
				idList.put(id, password);
				UserInfo u = searchUser();
				u.password=password;
				userInfoSet.remove(searchUser());
				userInfoSet.add(u);
				System.out.println("=================================================================");
				System.out.println("             ");
				System.out.println("                           변경되었습니다.");
				System.out.println("             ");
				System.out.println("=================================================================");
				writeUserInfoData();
				displayUserInfo();
			case 2 :
				System.out.print("변경할 휴대폰번호 : ");
				int phone = Integer.parseInt(BMmanager.sc.nextLine());
				u = searchUser();
				u.phone = new String(phone+"");
				userInfoSet.remove(searchUser());
				userInfoSet.add(u);
				System.out.println("=================================================================");
				System.out.println("             ");
				System.out.println("                           변경되었습니다.");
				System.out.println("             ");
				System.out.println("=================================================================");
				writeUserInfoData();
				displayUserInfo();
			case 3 :
				System.out.println("=================================================================");
				System.out.println("                           등급포인트 안내");
				System.out.println("");
				System.out.println("- 등급포인트는 음식을 주문하고 최종결재 할 때 사용된 금액으로 계산됩니다.");
				System.out.println();
				System.out.println("- 등급포인트가 50000(200000)이상이 되면 회원등급이 일반회원에서 VIP로 변경됩니다.");
				System.out.println("  등급포인트가 100000(400000)이상이 되면 회원등급이 VIP에서 VVIP로 변경됩니다.");
				System.out.println("  등급포인트가 150000(600000)이상이 되면 회원등급이 VVIP에서 VVVIP로 변경됩니다.");
				System.out.println("");
				System.out.println("- 회원등급이 변경되면 쿠폰을 얻게되고 다음 주문할 때 배달을 선택할 시");
				System.out.println("  쿠폰을 사용할 수 있습니다.");
				System.out.println("  쿠폰을 사용하면 배달비가 1회 무료이고 쿠폰은 1개 차감됩니다.");
				System.out.println("                         ");
				System.out.println("- 회원등급은 하루에 한번씩 초기화 되며(원래는 한달에 한번), 쿠폰은 초기화 되지 않습니다.");
				System.out.println();
				System.out.println("                      뒤로가려면 엔터를 눌러주세요");
				System.out.println("=================================================================");
				String enter = BMmanager.sc.nextLine();
				displayUserInfo();
			case 0 :
				userMenuStart();
			}
		}catch(Exception e) {
			System.out.println("잘못입력하셨습니다.");
			displayUserInfo();
		}
	}

	//파일에 수정된 foodMenuSet을 입력하는 매서드
	void writeFoodData() {
		try {
			String fileName = "FoodListInfo.ser";
			String fileName2 = "ShopListInfo.ser";
			ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(fileName));
			ObjectOutputStream out2 = new ObjectOutputStream(new FileOutputStream(fileName2));


			out.writeObject(foodMenuSet);
			out2.writeObject(shopList);
			out.close();
			out2.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	//foodMenuSet에 파일을 불러와 대입
	void readFoodData() {
		try {
			String fileName = "FoodListInfo.ser";
			String fileName2 = "ShopListInfo.ser";
			ObjectInputStream in = new ObjectInputStream(new FileInputStream(fileName));
			ObjectInputStream in2 = new ObjectInputStream(new FileInputStream(fileName2));

			foodMenuSet = (HashSet)in.readObject();
			shopList = (HashSet)in2.readObject();
			in.close();
			in2.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

}


class Delivery extends Thread{
	boolean delivery = false;
	int reserveTime;
	@Override
	public void run() {
		try {
			Thread.sleep((int)(Math.random()*2000)+reserveTime*1000);
		}catch(Exception e) {}
		JOptionPane.showMessageDialog(null, "주문이 접수 되었습니다. 조리가 시작됩니다.");
		if(delivery) {
			try{
				Thread.sleep((int)(Math.random()*5000+10000));
			}catch(Exception e) {}
			JOptionPane.showMessageDialog(null, "조리가 완료 되었습니다. 배달을 시작합니다.");
			try{
				Thread.sleep((int)(Math.random()*5000+8000));
			}catch(Exception e) {}
			JOptionPane.showMessageDialog(null, "배달이 도착하였습니다.");
		}else {
			try{
				Thread.sleep((int)(Math.random()*5000+10000));
			}catch(Exception e) {}
			JOptionPane.showMessageDialog(null, "조리가 완료되었습니다. 음식을 받아가세요");

		}
	}
	Delivery(boolean delivery){
		this.delivery=delivery;
	}
}
