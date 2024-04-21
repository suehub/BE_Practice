import page.Pages;
import page.Status;

public class BankingApp {

    public static void main(String[] args) {
        Service service = new Service();
        Status status = new Status("run");

        do {
            if (status.getWorkName() == "main" && !status.getUserId().contains("guest")) {
                status = Pages.mainPage(status);
            }
            service.serviceMenu(status);
        } while (status.getWorkFlow() != "stop");

    }
}
