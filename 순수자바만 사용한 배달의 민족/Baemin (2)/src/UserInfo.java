import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
//회원 가입 시 같은 이름과 같은 주민등록 번호를 가진사람은 입력이 되지 않도록
//equals와 hashCode를 오버라이딩 하였음.
public class UserInfo implements Serializable {
	String id;
	String password;
	String name;
	String jNum;
	String address = "주소를 설정해주세요.";
	String Grade = "일반회원";
	String phone;
	int coupon;
	int usedMoney;
	ArrayList<Food> cart = new ArrayList<>();
	List orderList = new ArrayList();
	List reviewList = new ArrayList();
	List myReview = new ArrayList();

	UserInfo(){}
	UserInfo(String id, String password, String name, String jNum,int phone){
		this.id=id;
		this.password = password;
		this.name = name;
		this.jNum = jNum;
		this.phone = new String(phone+"");
	}

	@Override
	public int hashCode() {
		return Objects.hash(name,jNum);
	}

	@Override
	public boolean equals(Object obj) {
		if(obj instanceof UserInfo) {
			UserInfo u = (UserInfo)obj;
			return name.equals(u.name)&&jNum.equals(u.jNum); 
		}
		return false;
	}

	public String toString() {
		return name+", "+id+", "+usedMoney+", "+ Grade;
	}

	void showUserInfo() {
		System.out.print("  ");
		System.out.println("이름 : "+name);
		System.out.print("  ");
		System.out.println("id : "+id);
		System.out.print("  ");
		System.out.println("주민번호 : "+jNum);
		System.out.print("  ");
		System.out.println("휴대폰 번호 : "+phone);
		System.out.print("  ");
		System.out.println("주소 : "+address);
		System.out.print("  ");
		System.out.println("등급포인트 :"+usedMoney);
	}

	void showMyReview() {
		int cnt = 1;

		for(int j=myReview.size()-1; j>=0;j--) {
			Review r = (Review)myReview.get(j);
			System.out.print(cnt+". ");
			System.out.println(r.shopName);
			r.showRiview();
			cnt++;
			System.out.println();
		}
		System.out.println();
	}

}

