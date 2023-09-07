import java.util.*;
public class TrainFare {
	
	private int stations;
	private double price;
	public TrainFare(int stations)
  {
    this.stations = stations;
  }
	
	public double computeFare(Scanner scanner) {
		System.out.println("Are You:");
		System.out.println("1.Student");
		System.out.println("2.OKU");
		System.out.println("3.Senior Citizen");
		System.out.println("4.Ordinary Citizen");
		int choice=scanner.nextInt();
		price = stations * 1.50;
		price=getDiscount(choice, price);
		return price;
	}
	
	public double getDiscount(int choice,double price)
	{
		switch(choice)
		{
			case 1: price*=0.9;
						break;
			case 2:price*=0.5;
						break;
			case 3:price *=0.7;
						break;
			case 4:
						break;
			default:
						break;
		}
		return price;
	}
}
