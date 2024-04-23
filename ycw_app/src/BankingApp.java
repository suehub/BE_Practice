import client.Pages;
import client.Status;

public class BankingApp {

    public static void main(String[] args) {
        Service service = new Service();
        Status status = new Status("run");

        do {
            if (status.getWorkName().equals("main")  && !status.getUserId().contains("guest")) {
                status = Pages.mainPage(status);
            }
            service.serviceMenu(status);
        } while (!status.getWorkFlow().equals("stop"));

    }
}
