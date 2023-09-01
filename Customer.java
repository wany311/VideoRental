import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Customer {
	private String name;
	private List<Rental> rentals = new ArrayList<Rental>();

	public Customer(String name) {
		this.setName(name);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Rental> getRentals() {
		return rentals;
	}

	public void setRentals(List<Rental> rentals) {
		this.rentals = rentals;
	}

	public void addRental(Rental rental) {
		rentals.add(rental);

	}

	private int calculatePoints(Rental rental, int daysRented) {
		int points = 1;
		if (rental.getVideo().getPriceCode() == PriceCode.NEW_RELEASE) {
			points++;
		}
		if (daysRented > rental.getDaysRentedLimit()) {
			points -= Math.min(points, rental.getVideo().getLateReturnPointPenalty());
		}
		return points;
	}

	private int getDaysRented(Rental rental) {
		long diff;
		if (rental.getStatus() == 1) { // returned Video
			diff = rental.getReturnDate().getTime() - rental.getRentDate().getTime();
		} else { // not yet returned
			diff = new Date().getTime() - rental.getRentDate().getTime();
		}
		return (int) (diff / (1000 * 60 * 60 * 24)) + 1;
	}

	private double calculateCharge(Rental rental, int daysRented) {
		double result = 0;
		switch (rental.getVideo().getPriceCode()) {
			case REGULAR:
				result += 2;
				if (daysRented > 2)
					result += (daysRented - 2) * 1.5;
				break;
			case NEW_RELEASE:
				result = daysRented * 3;
				break;
		}
		return result;
	}

	private void printCouponMessage(int totalPoint) {
		if (totalPoint >= 10) {
			System.out.println("Congrat! You earned one free coupon");
		}
		if (totalPoint >= 30) {
			System.out.println("Congrat! You earned two free coupons");
		}
	}

	public String getReport() {
		String result = "Customer Report for " + getName() + "\n";

		double totalCharge = 0;
		int totalPoint = 0;

		for (Rental each : rentals) {
			int daysRented = getDaysRented(each);
			double eachCharge = calculateCharge(each, daysRented);
			int eachPoint = calculatePoints(each, daysRented);

			result += "\t" + each.getVideo().getTitle() + "\tDays rented: " + daysRented + "\tCharge: " + eachCharge
					+ "\tPoint: " + eachPoint + "\n";

			totalCharge += eachCharge;
			totalPoint += eachPoint;
		}

		result += "Total charge: " + totalCharge + "\tTotal Point:" + totalPoint + "\n";

		printCouponMessage(totalPoint);

		return result;
	}
}