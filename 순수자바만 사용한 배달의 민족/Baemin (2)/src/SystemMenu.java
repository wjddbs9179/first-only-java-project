import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
//관리자 로그인 성공시 수행될 매서드들이 포함된 클래스
public class SystemMenu {
	private static final int KOREA = 1;
	private static final int CHINA = 2;
	private static final int JAPAN = 3;
	private static final int DATAOUTPUT = 4;
	private static final int EXIT = 0;

	private static Set<Food> foodMenuSet = new HashSet();
	private static Set<Shop> shopList = new HashSet();

	//관리자 로그인 성공시 생성됨.
	SystemMenu(){
		//foodMenuSet에 기존에 저장되어있는 파일을 불러와서 대입.
		readFoodData();
		//시스템로그인 성공시 화면에 나타날 작업을 수행하기 위한 메서드
		try {
			SystemMenuStart();
		}catch(Exception e) {
			System.out.println("잘못입력하셨습니다.");
			SystemMenuStart();
		}
	}

	void SystemMenuStart() {
		try {
			System.out.println();
			System.out.println("=================================================================");
			System.out.println("                             관리자                    ");
			System.out.println("                                         ");
			System.out.println("                 1        2         3         0     ");
			System.out.println("               음식메뉴    식당관리    회원목록     로그아웃   ");
			System.out.println("                관리                                 ");
			System.out.println("                                         ");
			System.out.println("                                         ");
			System.out.println("=================================================================");
			System.out.print("숫자입력>>");
			int choice = Integer.parseInt(BMmanager.sc.nextLine());
			switch(choice) {
			//메뉴 추가,삭제,전체 메뉴(이때까지 입력했던)출력
			case 1 : 
				try {
					newFoodMenu();
				}catch(Exception e) {
					System.out.println("잘못입력하셨습니다.");
					newFoodMenu();
				}
				break;
				//식당 관리, 프로모션 설정 ,추가 삭제 등
			case 2 :
				try {
					shopManagement();
				}catch(Exception e) {
					System.out.println("잘못입력하셨습니다.");
					shopManagement();
				}
				break;
				//회원가입 되어 있는 목록 출력
			case 3 :
				showUserInfo();
				break;
			case 0 :
				new BMmanager();
			default :
				System.out.println("잘못입력하셨습니다.");
				SystemMenuStart();
			}
		}catch(Exception e) {
			System.out.println("잘못입력하셨습니다.");
			SystemMenuStart();
		}

	}


	//식당 관리에 사용할 매서드
	void shopManagement() {
		try {
			readFoodData();
			System.out.println("=================================================================");
			System.out.println("                              식당관리                  ");
			System.out.println("                                         ");
			System.out.println("                  1         2         3         4      ");
			System.out.println("                식당등록     식당삭제     식당목록    프로모션    ");
			System.out.println("                                               설정      ");
			System.out.println("  0.뒤로가기                                       ");
			System.out.println("=================================================================");
			System.out.print("숫자입력>>");
			int choice = Integer.parseInt(BMmanager.sc.nextLine());
			switch(choice) {
			case 1 :
				try {
					newShop();
				}catch(Exception e) {
					System.out.println("잘못입력하셨습니다.");
					newShop();
				}
				break;
			case 2 : 
				deleteShop();
				break;
			case 3 :
				showShopList();
				break;
			case 4 :
				setPromotion();
				break;
			case 0 :
				SystemMenuStart();
			default :
				System.out.println("잘못입력하셨습니다.");
				shopManagement();
			}
		}catch(Exception e) {
			System.out.println("잘못입력하셨습니다.");
			shopManagement();
		}
	}
	void setPromotion() {
		try {
			Iterator it = shopList.iterator();
			ArrayList sl = new ArrayList();
			while(it.hasNext()) {
				Shop s = (Shop)it.next();
				sl.add(s);
			}
			int shopCnt = 1;
			System.out.println("=================================================================");
			System.out.println("                            프로모션 설정");
			System.out.println("                             ");
			for(int i=0;i<sl.size();i++) {
				System.out.print(shopCnt+". ");
				System.out.println(sl.get(i));
				shopCnt++;
			}
			System.out.println("                             ");
			System.out.println("                    프로모션을 설정할 식당을 선택하세요.");
			System.out.println("=================================================================");
			System.out.print(">>");
			int choice = Integer.parseInt(BMmanager.sc.nextLine());
			Shop s = (Shop)sl.get(choice-1);
			System.out.print("프로모션 설정 기간(현재로 부터 몇일 간) : ");
			int pd = Integer.parseInt(BMmanager.sc.nextLine());
			System.out.print("할인율(몇퍼센트 인지 숫자만) : ");
			int p = Integer.parseInt(BMmanager.sc.nextLine());
			System.out.println("=================================================================");
			System.out.println("                       정말로 설정하시겠습니까?");
			System.out.println("                            ");
			System.out.println("                1. 예                      2. 아니요");
			System.out.println("=================================================================");
			System.out.print(">>");
			choice = Integer.parseInt(BMmanager.sc.nextLine());
			switch(choice) {
			case 1 :
				s.promotionDate = LocalDate.now().plusDays(pd);
				s.promotion = (double)(100-p)/100;
				writeFoodData();
				System.out.println("=================================================================");
				System.out.println();
				System.out.println("                       프로모션 설정 완료입니다.");
				System.out.println();
				System.out.println("=================================================================");
				shopManagement();
			case 2 :
				shopManagement();
			}
		}catch(Exception e) {
			System.out.println("잘못입력하셨습니다.");
			setPromotion();
		}
	}


	//등록된 식당 목록을 출력하는 매서드
	void showShopList() {
		System.out.println("=================================================================");
		System.out.println("                      등록된 식당 리스트 입니다.");
		System.out.println();
		Iterator it = shopList.iterator();
		ArrayList sl = new ArrayList();
		int cnt = 1;
		while(it.hasNext()) {
			sl.add(it.next());
			System.out.print(cnt+". ");
			System.out.println(sl.get(cnt-1));
			cnt++;
		}
		System.out.println();
		System.out.println("=================================================================");
		shopManagement();
	}
	//새로운 식당을 추가 하기 위한 매서드
	void newShop() {
		try {
			System.out.println("=================================================================");
			System.out.println("                            카테고리 선택                ");
			System.out.println("                                        ");
			System.out.println("                      1      2      3      4          ");
			System.out.println("                     한식    중식    일식    양식       ");
			System.out.println("                                        ");
			System.out.println(" 0. 뒤로가기                                       ");
			System.out.println("=================================================================");
			System.out.print("숫자입력>>");
			int choice = Integer.parseInt(BMmanager.sc.nextLine());
			switch(choice) {
			case 1 :
				newKoreaShop();
				break;
			case 2 :
				newChinaShop();
				break;
			case 3 :
				newJapanShop();
				break;
			case 4 :
				newWesternShop();
			case 0 :
				return;
			}
		}catch(Exception e) {
			System.out.println("잘못입력하셨습니다.");
			newShop();
		}
	}
	
	void newShop(int cs) {
		try {
			Shop s = null;
			System.out.print("식당 이름 : ");
			String shopName = BMmanager.sc.nextLine();
			System.out.print("식당 주소 : ");
			String address = BMmanager.sc.nextLine();
			System.out.println("=================================================================");
			System.out.println("                         정말로 추가하시겠습니까?");
			System.out.println("");
			System.out.println("                    1. 예                   2.아니요.");
			System.out.println("=================================================================");
			if(cs==1)
				s = new KoreaShop(shopName,address);
			if(cs==2)
				s = new ChinaShop(shopName,address);
			if(cs==3)
				s = new JapanShop(shopName,address);
			if(cs==4)
				s = new WesternShop(shopName,address);
			int choice = Integer.parseInt(BMmanager.sc.nextLine());
			switch(choice) {
			case 1 :
				if(shopList.add(s)) {
					System.out.println("=================================================================");
					System.out.println();
					System.out.println("                           식당 추가 완료");
					System.out.println();
					System.out.println("=================================================================");
					writeFoodData();
					shopManagement();
				}else {
					System.out.println("=================================================================");
					System.out.println();
					System.out.println("                       중복된 식당으로 인한 실패");
					System.out.println();
					System.out.println("=================================================================");
				}
				shopManagement();
			case 2 :
				shopManagement();
			default :
				System.out.println("잘못입력하셨습니다.");
				if(cs==1)
					newKoreaShop();
				if(cs==2)
					newChinaShop();
				if(cs==3)
					newJapanShop();
				if(cs==4)
					newWesternShop();
			}
		}catch(Exception e) {
			System.out.println("잘못입력하셨습니다.");
			if(cs==1)
				newKoreaShop();
			if(cs==2)
				newChinaShop();
			if(cs==3)
				newJapanShop();
			if(cs==4)
				newWesternShop();
		}
	}
	
		
	
	//한식 식당 추가
	void newKoreaShop() {
		newShop(1);
	}
	//중식 식당 추가
	void newChinaShop() {
		newShop(2);
	}
	//일식 식당 추가
	void newJapanShop() {
		newShop(3);
	}
	void newWesternShop() {
		newShop(4);
	}





	//등록된 식당을 삭제 할 때 사용
	void deleteShop() {
		try {
			System.out.println("=================================================================");
		System.out.println("                              식당 삭제");
		System.out.println("                                  ");
		Iterator it = shopList.iterator();
		ArrayList sl = new ArrayList();
		int cnt = 1;
		while(it.hasNext()) {
			sl.add(it.next());
			System.out.print(cnt+". ");
			System.out.println(sl.get(cnt-1));
			cnt++;
		}
		System.out.println();
		System.out.println("                      삭제할 숫자(식당번호)를 입력하세요");
		System.out.println("=================================================================");
		System.out.println("0. 뒤로가기");
		System.out.print(">>");
		int choice = Integer.parseInt(BMmanager.sc.nextLine());
		if(choice ==0) {shopManagement();}
		System.out.println("=================================================================");
		System.out.println();
		System.out.println("                        정말로 삭제하시겠습니까?");
		System.out.println();
		System.out.println("                   1. 예                   2. 아니요");
		System.out.println("=================================================================");
		int choice2 = Integer.parseInt(BMmanager.sc.nextLine());
		switch(choice2) {
		case 1 :
			sl.remove(choice-1);
			shopList = new HashSet(sl);
			writeFoodData();
			System.out.println("=================================================================");
			System.out.println();
			System.out.println("                          삭제 되었습니다.");
			System.out.println();
			System.out.println("=================================================================");
			shopManagement();
		case 2 :
			shopManagement();
			default :
				System.out.println("잘못입력하셨습니다.");
				deleteShop();
		}
		}catch(Exception e) {
			System.out.println("잘못입력하셨습니다.");
			deleteShop();
		}
		
		

	}



	//회원목록 출력
	void showUserInfo() {
		System.out.println("=================================================================");
		System.out.println("                         등록된 회원 목록입니다.           ");
		System.out.println();
		BMmanager.showUserInfo();
		System.out.println("=================================================================");
		SystemMenuStart();
	}







	//관리자 로그인 성공후 1번선택시 출력될 내용
	void newFoodMenu(){
		try {
			System.out.println("=================================================================");
			System.out.println("                            음식메뉴관리                  ");
			System.out.println("                                          ");
			System.out.println("                  1        2         3         4                    ");
			System.out.println("                 한식      중식       일식       양식            ");
			System.out.println("                                        ");
			System.out.println(" 5.등록된 메뉴보기                            ");
			System.out.println(" 6.등록된 메뉴삭제");
			System.out.println(" 0.뒤로가기                                  ");
			System.out.println("=================================================================");
			System.out.print("숫자입력>>");
			int choice = Integer.parseInt(BMmanager.sc.nextLine());
			switch(choice) {
			case KOREA :
				newKoreaFood();
				break;
			case CHINA :
				newChinaFood();
				break;
			case JAPAN :
				newJapanFood();
				break;
			case 4 :
				newWesternFood();
				break;
			case 5 :
				DataOutput();
				break;
			case 6 :
				try {
					deleteFood();
				}catch(Exception e) {
					System.out.println("잘못입력하셨습니다.");
					deleteFood();
				}
				break;
			case EXIT :
				SystemMenuStart();
			}
		}catch(Exception e) {
			System.out.println("잘못입력하셨습니다.");
			newFoodMenu();
		}
	}


	//등록된 음식을 삭제하는 매서드
	void deleteFood() {
		try {
			System.out.println("=================================================================");
			System.out.println("                      등록된 식당 리스트 입니다.");
			System.out.println();
			Iterator it = shopList.iterator();
			ArrayList sl = new ArrayList();
			int cnt = 1;
			while(it.hasNext()) {
				sl.add(it.next());
				System.out.print(cnt+". ");
				System.out.println(sl.get(cnt-1));
				cnt++;
			}
			System.out.println();
			System.out.println("식당을 선택하세요.");
			System.out.println("=================================================================");
			System.out.print("숫자(식당번호)입력>>");
			int choice = Integer.parseInt(BMmanager.sc.nextLine());
			Shop s = (Shop)sl.get(choice-1);
			System.out.println("=================================================================");
			System.out.println("           "+s+"에 등록된 음식 리스트 입니다.");
			System.out.println();
			it = foodMenuSet.iterator();
			ArrayList fl = new ArrayList();
			cnt = 1;
			while(it.hasNext()) {
				Food f = (Food)it.next();
				if(s.shopName.equals(f.shopName)&&s.address.equals(f.shopAdress)) {
					fl.add(f);
					System.out.print(cnt+". ");
					System.out.println(fl.get(cnt-1));
					cnt++;
				}
			}
			System.out.println();
			System.out.println("삭제할 음식(숫자)을 선택하세요.");
			System.out.println("=================================================================");
			System.out.println("0. 뒤로가기");
			System.out.print(">>");
			choice = Integer.parseInt(BMmanager.sc.nextLine());
			if(choice==0) {newFoodMenu();}
			System.out.println("=================================================================");
			System.out.println("                        정말로 삭제하시겠습니까?");
			System.out.println();
			System.out.println("                   1. 예                     2. 아니요   ");
			System.out.println("=================================================================");
			System.out.print(">>");
			choice = Integer.parseInt(BMmanager.sc.nextLine());
			switch(choice) {
			case 1 :
				foodMenuSet.remove(fl.get(choice-1));
				System.out.println("=================================================================");
				System.out.println();
				System.out.println("                          삭제되었습니다.");
				System.out.println();
				System.out.println("=================================================================");
				writeFoodData();
				newFoodMenu();
			case 2 :
				newFoodMenu();
			default :
				deleteFood();
			}
		}catch(Exception e) {
			System.out.println("잘못입력하셨습니다.");
			deleteFood();
		}
	}


	//전체 메뉴 출력
	void DataOutput(){
		readFoodData();
		ArrayList fl = new ArrayList();
		ArrayList sl = new ArrayList();
		int foodCnt = 1;
		int shopCnt = 1;
		Iterator it = foodMenuSet.iterator();
		if(foodMenuSet.isEmpty()) {
			System.out.println("=================================================================");
			System.out.println();
			System.out.println("                      입력된 음식데이터가 없습니다."); 
			System.out.println();
			System.out.println("=================================================================");
			newFoodMenu();
		}else {
			while(it.hasNext()) {
				fl.add(it.next());
			}
		}
		it = shopList.iterator();
		if(shopList.isEmpty()) {
			System.out.println("=================================================================");
			System.out.println();
			System.out.println("                         등록된 식당이 없습니다.");
			System.out.println();
			System.out.println("=================================================================");
			newFoodMenu();
		}else {
			while(it.hasNext()) {
				sl.add(it.next());
			}
		}
		System.out.println("=================================================================");
		System.out.println("");
		for(int i=0;i<sl.size();i++) {
			Shop s = (Shop)sl.get(i);
			System.out.println(shopCnt+". "+s+"에 등록된 음식리스트 입니다.");
			for(int j=0;j<fl.size();j++) {
				Food f = (Food)fl.get(j);
				if(f.shopName.equals(s.shopName)&&f.shopAdress.equals(s.address)) {
					System.out.print("    "+foodCnt+". ");
					System.out.println(f);
					foodCnt++;
				}
			}
			System.out.println();
			foodCnt=1;
			shopCnt++;
		}
		System.out.println("=================================================================");

		newFoodMenu();
	}


	
	void createFood(int css) {
		try {
			readFoodData();
			System.out.println();
			System.out.println("=================================================================");
			System.out.println("                         등록된 식당목록입니다.           ");
			System.out.println();
			Iterator it = shopList.iterator();
			ArrayList sl = new ArrayList();

			int cnt = 1;
			if(css==1)
			while(it.hasNext()) {
				Shop s = (Shop)it.next();
				if(s instanceof KoreaShop) {
					sl.add(s);
					System.out.print(cnt+". ");
					System.out.println(sl.get(cnt-1));
					cnt++;
				}
			}
			if(css==2)
			while(it.hasNext()) {
				Shop s = (Shop)it.next();
				if(s instanceof ChinaShop) {
					sl.add(s);
					System.out.print(cnt+". ");
					System.out.println(sl.get(cnt-1));
					cnt++;
				}
			}
			if(css==3)
			while(it.hasNext()) {
				Shop s = (Shop)it.next();
				if(s instanceof JapanShop) {
					sl.add(s);
					System.out.print(cnt+". ");
					System.out.println(sl.get(cnt-1));
					cnt++;
				}
			}
			if(css==4)
			while(it.hasNext()) {
				Shop s = (Shop)it.next();
				if(s instanceof WesternShop) {
					sl.add(s);
					System.out.print(cnt+". ");
					System.out.println(sl.get(cnt-1));
					cnt++;
				}
			}
			System.out.println();
			System.out.println("음식을 등록할 식당을 선택하세요.");
			System.out.println("=================================================================");

			System.out.print("숫자(0.뒤로가기)입력>>");
			int choice = Integer.parseInt(BMmanager.sc.nextLine());
			if(choice==0)
				newFoodMenu();
			Shop s = (Shop)sl.get(choice-1);
			System.out.println("=================================================================");
			System.out.println("               "+s + "에 등록된 음식리스트 입니다.");
			System.out.println();
			cnt = 1;
			it = foodMenuSet.iterator();
			while(it.hasNext()) {
				Food f = (Food)it.next();
				if(s.shopName.equals(f.shopName)&&s.address.equals(f.shopAdress)) {
					System.out.print(cnt+". ");
					System.out.println(f);
					cnt++;
				}
			}
			System.out.println();
			System.out.println("=================================================================");
			Shop cs=(Shop)sl.get(choice-1);
			System.out.print("추가 할 음식 이름 : ");
			String foodName = BMmanager.sc.nextLine();
			System.out.print("가격 : ");
			int price = Integer.parseInt(BMmanager.sc.nextLine());
			//Set는 중복허용이 안되기 때문에 음식이름과 음식점 이름이 같으면 추가 안됨.
			System.out.println("=================================================================");
			System.out.println("                        정말로 추가하시겠습니까?");
			System.out.println();
			System.out.println("                  1.예                     2.아니요");
			System.out.println("=================================================================");
			System.out.print("숫자입력 >>");
			choice = Integer.parseInt(BMmanager.sc.nextLine());
			switch(choice) {
			case 1 : 
				if(foodMenuSet.add(new Food(foodName,cs.shopName,cs.address,price))) {
					System.out.println("=================================================================");
					System.out.println();
					System.out.println("                             입력성공.");
					System.out.println();
					System.out.println("=================================================================");
					writeFoodData();
					newFoodMenu();
				}else
					System.out.println("중복으로 인한 입력실패");
				newFoodMenu();
			case 2 :
				newFoodMenu();
			}
		}catch(Exception e) {
			System.out.println("잘못입력하셨습니다.");
			if(css==1)
				newKoreaFood();
			if(css==2)
				newChinaFood();
			if(css==3)
				newJapanFood();
			if(css==4)
				newWesternFood();
		}
	}
	// foodMenuSet에 한식 메뉴 추가하는 매서드
	void newKoreaFood(){
		createFood(1);
	}
	//foodMenuSet에 중식 메뉴 추가하는 매서드
	void newChinaFood() {
		createFood(2);
	}
	//foodMenuSet에 일식 메뉴 추가하는 매서드
	void newJapanFood() {
		createFood(3);
	}
	void newWesternFood(){
		createFood(4);
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
