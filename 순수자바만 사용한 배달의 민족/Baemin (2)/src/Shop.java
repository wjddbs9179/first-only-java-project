import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;


//사용자가 메뉴를 주문할 때 식당별로 나오는 메뉴리스트가 다르게 나오게 하기 위해서 작성.
public class Shop<T> implements Serializable{
	String shopName;
	String address;
	double promotion;
	LocalDate promotionDate = null;
	ArrayList<Review> reviewList = new ArrayList<>();
	
	
	
	@Override
	public int hashCode() {
		return Objects.hash(shopName,address);
	}
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Shop) {
			Shop s = (Shop)obj;
			return this.shopName.equals(s.shopName)&&this.address.equals(s.address);
		}
		return false;
	}
	@Override
	public String toString() {
		if(promotionDate==null||Math.round((double)(1-promotion)*100)==0||promotionDate.isBefore(LocalDate.now())) {
			return "("+shopName+", "+address+")";
		}else {
			return "("+shopName+", "+address+", "+"현재 "+Math.round((double)(1-promotion)*100)+"% 할인행사중)";
		}
	}
	Shop(){}
	
	Shop(String shopName,String address){
		this.shopName = shopName;
		this.address = address;
	}
}

class KoreaShop extends Shop{
	KoreaShop(String shopName,String address){
		this.shopName = shopName;
		this.address = address;
	}
}
class ChinaShop extends Shop{
	ChinaShop(String shopName,String address){
		this.shopName = shopName;
		this.address = address;
	}
}

class JapanShop extends Shop{
	JapanShop(String shopName,String address){
		this.shopName = shopName;
		this.address = address;
	}
}
class WesternShop extends Shop{
	WesternShop(String shopName,String address){
		this.shopName = shopName;
		this.address = address;
	}
}