import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
//UserInfo 객체의 ArrayList(orderList)에 담을 객체
//사용자의 주문내역을 불러올 때 사용.
public class OrderList implements Serializable{
	String shopName;
	String foodName;
	int price;
	LocalDate nowDate = null;
	LocalTime nowTime = null;
	
	OrderList(String shopName,String foodName,int price){
		this.shopName = shopName;
		this.foodName = foodName;
		this.price = price;
		nowDate = LocalDate.now();
		nowTime = LocalTime.now().withNano(0);
	}
	
	void showOderList() {
		System.out.println("==============================주문 내역=============================");
		System.out.println("음식점 : "+shopName);
		System.out.println("음식명 : "+foodName);
		System.out.println("비용    : "+price);
		System.out.println("주문 날짜 : "+nowDate+" "+nowTime.withNano(0));
		System.out.println("=================================================================");
		System.out.println();
	}
	
}
