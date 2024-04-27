import client.Pages;
import client.Status;
import controller.*;

public class BankingApp {

    public static void main(String[] args) {
        Service service = new Service();
        Status status = new Status();

        do {
            if (status.getWorkTag() == Tag.MAIN  && !status.getUserId().contains(Flow.GUEST.getFlow())) {
                status = Pages.mainPage(status);
            }
            service.serviceMenu(status);

        } while (status.getWorkFlow() != Flow.STOP);

    }
}
