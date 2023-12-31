import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class VRUI {
	private static Scanner scanner = new Scanner(System.in);

	private VideoManager vm = new VideoManager();
	private CustomerManager cm = new CustomerManager();
	private RentalManager rm = new RentalManager();

	public static void main(String[] args) {
		VRUI ui = new VRUI();

		boolean quit = false;
		while (!quit) {
			int command = ui.showCommand();
			switch (command) {
				case 0:
					quit = true;
					break;
				case 1:
					ui.listCustomers();
					break;
				case 2:
					ui.listVideos();
					break;
				case 3:
					ui.register("customer");
					break;
				case 4:
					ui.register("video");
					break;
				case 5:
					ui.rentVideo();
					break;
				case 6:
					ui.returnVideo();
					break;
				case 7:
					ui.getCustomerReport();
					break;
				case 8:
					ui.clearRentals();
					break;
				case -1:
					ui.init();
					break;
				default:
					break;
			}
		}
		System.out.println("Bye");
	}

	public void listCustomers() {
		System.out.println("List of customers");
		cm.listCustomers();
		System.out.println("End of list");
	}

	public void listVideos() {
		System.out.println("List of videos");
		vm.listVideos();
		System.out.println("End of list");
	}

	public void register(String object) {
		if (object.equals("customer")) {
			System.out.println("Enter customer name: ");
			String name = scanner.next();
			cm.register(name);
		} else {
			System.out.println("Enter video title to register: ");
			String title = scanner.next();

			System.out.println("Enter video type( 1 for VHD, 2 for CD, 3 for DVD ):");
			int videoType = scanner.nextInt();

			System.out.println("Enter price code( 1 for Regular, 2 for New Release ):");
			int priceCode = scanner.nextInt();

			vm.register(title, videoType, priceCode);
		}
	}

	public void clearRentals() {
		System.out.println("Enter customer name: ");
		String customerName = scanner.next();

		Customer foundCustomer = cm.getCustomer(customerName);
		rm.clearRentals(foundCustomer);
	}

	public void returnVideo() {
		System.out.println("Enter customer name: ");
		String customerName = scanner.next();

		Customer foundCustomer = cm.getCustomer(customerName);

		if (foundCustomer == null)
			return;

		System.out.println("Enter video title to return: ");
		String videoTitle = scanner.next();

		rm.returnVideo(foundCustomer, videoTitle);
	}

	private void init() {
		Customer james = new Customer("James");
		Customer brown = new Customer("Brown");
		cm.addCustomer(james);
		cm.addCustomer(brown);

		Video v1 = new Video("v1", VideoType.CD, PriceCode.REGULAR, new Date());
		Video v2 = new Video("v2", VideoType.DVD, PriceCode.NEW_RELEASE, new Date());
		vm.addVideo(v1);
		vm.addVideo(v2);

		Rental r1 = new Rental(v1);
		Rental r2 = new Rental(v2);
		james.addRental(r1);
		james.addRental(r2);
	}

	public void getCustomerReport() {
		System.out.println("Enter customer name: ");
		String customerName = scanner.next();

		Customer foundCustomer = cm.getCustomer(customerName);

		if (foundCustomer == null) {
			System.out.println("No customer found");
		} else {
			String result = foundCustomer.getReport();
			System.out.println(result);
		}
	}

	public void rentVideo() {
		System.out.println("Enter customer name: ");
		String customerName = scanner.next();

		Customer foundCustomer = cm.getCustomer(customerName);

		if (foundCustomer == null)
			return;

		System.out.println("Enter video title to rent: ");
		String videoTitle = scanner.next();

		Video foundVideo = vm.getVideo(videoTitle);

		if (foundVideo == null)
			return;

		rm.rentVideo(foundCustomer, foundVideo);
	}

	public int showCommand() {
		System.out.println("\nSelect a command !");
		System.out.println("\t 0. Quit");
		System.out.println("\t 1. List customers");
		System.out.println("\t 2. List videos");
		System.out.println("\t 3. Register customer");
		System.out.println("\t 4. Register video");
		System.out.println("\t 5. Rent video");
		System.out.println("\t 6. Return video");
		System.out.println("\t 7. Show customer report");
		System.out.println("\t 8. Show customer and clear rentals");

		int command = scanner.nextInt();

		return command;

	}
}

class VideoManager {
	private List<Video> videos = new ArrayList<Video>();

	public void register(String title, int videoType, int priceCode) {
		Date registeredDate = new Date();
		Video video = new Video(title, VideoType.valueOfInt(videoType), PriceCode.valueOfInt(priceCode),
				registeredDate);
		videos.add(video);
	}

	public Video getVideo(String videoTitle) {
		Video foundVideo = null;
		for (Video video : videos) {
			if (video.getTitle().equals(videoTitle) && video.isRented() == false) {
				foundVideo = video;
				break;
			}
		}
		return foundVideo;
	}

	public void listVideos() {

		for (Video video : videos) {
			System.out.println("Price code: " + video.getPriceCode() + "\tTitle: " + video.getTitle());
		}
	}

	public void addVideo(Video v) {
		videos.add(v);
	}
}

class RentalManager {
	private static Scanner scanner = new Scanner(System.in);
	private List<Rental> rentals = new ArrayList<Rental>();

	public void clearRentals(Customer foundCustomer) {
		if (foundCustomer == null) {
			System.out.println("No customer found");
		} else {
			System.out.println("Name: " + foundCustomer.getName() +
					"\tRentals: " + foundCustomer.getRentals().size());
			for (Rental rental : foundCustomer.getRentals()) {
				System.out.print("\tTitle: " + rental.getVideo().getTitle() + " ");
				System.out.print("\tPrice Code: " + rental.getVideo().getPriceCode());
			}
			rentals = new ArrayList<>();
			foundCustomer.setRentals(rentals);
		}
	}

	public void returnVideo(Customer foundCustomer, String videoTitle) {
		List<Rental> customerRentals = foundCustomer.getRentals();
		for (Rental rental : customerRentals) {
			if (rental.getVideo().getTitle().equals(videoTitle) && rental.getVideo().isRented()) {
				rental.returnVideo();
				rental.getVideo().setRented(false);
				break;
			}
		}
	}

	public void rentVideo(Customer foundCustomer, Video foundVideo) {
		Rental rental = new Rental(foundVideo);
		foundVideo.setRented(true);

		List<Rental> customerRentals = foundCustomer.getRentals();
		customerRentals.add(rental);
		foundCustomer.setRentals(customerRentals);
	}
}

class CustomerManager {
	private List<Customer> customers = new ArrayList<Customer>();

	public void register(String name) {
		Customer customer = new Customer(name);
		customers.add(customer);
	}

	public Customer getCustomer(String customerName) {
		Customer foundCustomer = null;
		for (Customer customer : customers) {
			if (customer.getName().equals(customerName)) {
				foundCustomer = customer;
				break;
			}
		}

		return foundCustomer;
	}

	public void listCustomers() {
		for (Customer customer : customers) {
			System.out.println("Name: " + customer.getName() +
					"\tRentals: " + customer.getRentals().size());
			for (Rental rental : customer.getRentals()) {
				System.out.print("\tTitle: " + rental.getVideo().getTitle() + " ");
				System.out.print("\tPrice Code: " + rental.getVideo().getPriceCode());
			}
		}
	}

	public void addCustomer(Customer c) {
		customers.add(c);
	}
}