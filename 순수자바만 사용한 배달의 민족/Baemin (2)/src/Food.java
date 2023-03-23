import java.io.Serializable;
import java.util.Objects;
//Food클래스는 음식이름(foodName)과 식당이름(shopName)이 같으면 foodMenuSet(기존에 저장 해놓은 음식메뉴)에 추가 될 수 없도록
//equals,hashCode를 오버라이딩 해놨음.
//그리고 나중에 사용자가 주문할 메뉴를 찾을 때 한식,중식,일식 으로 구분될 수 있도록 자손 클래스를 나눠놨음.
class Food implements Serializable,Comparable<Food>{
	String foodName;
	String shopName;
	String shopAdress;
	int price;
	int eatCount;
	
	@Override
	public int compareTo(Food o) {
		return this.eatCount-o.eatCount;
	}
	
	Food(String foodName,String shopName,String shopAdress ,int price){
		this.foodName = foodName;
		this.shopName = shopName;
		this.shopAdress = shopAdress;
		this.price = price;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Food) {
			Food f = (Food)obj;
			return this.foodName.equals(f.foodName)&&this.shopName.equals(f.shopName)&&this.shopAdress.equals(f.shopAdress);
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(foodName,shopName,shopAdress);
	}
	
	@Override
	public String toString() {
		return "("+shopName+", "+foodName+", "+price+", "+"주문횟수 : "+eatCount+")";
	}
}


//class KoreaFood extends Food{
//	KoreaFood(String foodName,String shopName,int price){
//		super(foodName,shopName,price);
//	}
//}
//class ChinaFood extends Food{
//	ChinaFood(String foodName,String shopName,int price){
//		super(foodName,shopName,price);
//	}
//
//}
//class JapanFood extends Food{
//	JapanFood(String foodName,String shopName,int price){
//		super(foodName,shopName,price);
//	}
//
//}
//class WesternFood extends Food{
//	WesternFood(String foodName,String shopName,int price){
//		super(foodName,shopName,price);
//	}
//
//}