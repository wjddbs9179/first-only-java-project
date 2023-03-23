import javax.swing.JOptionPane;

public class Main {
	public static void main(String[] args) {
		System.out.print("                        ");
		delay(100);
		System.out.print("대");
		delay(100);
		System.out.print("한");
		delay(100);
		System.out.print("민");
		delay(100);
		System.out.print("국");
		delay(100);
		System.out.print(" 1");
		delay(100);
		System.out.print("등");
		delay(100);
		System.out.print(" 배");
		delay(100);
		System.out.print("달");
		delay(100);
		System.out.print("어");
		delay(100);
		System.out.print("플");

		System.out.println();
		System.out.println();
		System.out.println("            ##         ##             ##          ##    ");
		delay(100);
		System.out.println("  ##  ## ## ##  ###### ##      ###    ##  ######  ##   ##########   ");
		delay(100);
		System.out.println("  ##  ## ## ##  ##     ####   ## ##   ##  ##  ##  ##       ##");
		delay(100);
		System.out.println("  ##  ## ## ##  ##     ##    ##   ##  ##  ##  ##  ##      ####");
		delay(100);
		System.out.println("  ###### #####  ###### ##     ## ##   ##  ######  ##     ##  ##  ");
		delay(100);
		System.out.println("  ##  ## ## ##   ########      ###    ##          ##       ##");
		delay(100);
		System.out.println("  ##  ## ## ##         ##             ##   ##         ############   ");
		delay(100);
		System.out.println("  ###### ## ##   ########   ######### ##   ##           ########");
		delay(100);
		System.out.println("         ## ##   ##                   ##   ##                 ##");
		delay(100);
		System.out.println("            ##   ########             ##   #########          ##");
		delay(100);
		System.out.println();
		System.out.println("                      시작하려면 엔터를 눌러주세요");











		String enter = BMmanager.sc.nextLine();
		//BMmanager 생성자 호출
		try {
			new BMmanager();
		}catch(Exception e){
			System.out.println("잘못입력하셨습니다.");
			new BMmanager();
		}
	}
	public static void delay(long miles) {
		try {
			Thread.sleep(miles);
		}catch(Exception e) {}
	}
}
