import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

public class Review implements Serializable {
	String shopName;
	String text = "";
	LocalDate nowDate = null;
	LocalTime nowTime = null;
	
	Review(String text,String shopName){
		this.text = text;
		this.shopName = shopName;
		nowDate = LocalDate.now();
		nowTime = LocalTime.now().withNano(0);
	
	}
	void showRiview() {
		System.out.println(nowDate+" "+nowTime);
		System.out.println(text);
	}
	
}
