import page.PagePrinter;
import page.Status;

public class BankingApp {

    public static void main(String[] args) {
        Service service = new Service();
        Status status = new Status(true);

        do {
            if (status.getUserId() != "guest") {
                PagePrinter.mainPage();
            }
            service.serviceMenu(status);
        } while (true);

    }
}
