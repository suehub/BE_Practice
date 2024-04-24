import client.Pages;
import client.Status;
import controller.*;

public class BankingApp {

    public static void main(String[] args) {
        Service service = new Service();
        Status status = new Status(Flow.RUN);

        do {
            if (status.getWorkName().equals(Tag.MAIN.getTag())  && !status.getUserId().contains("guest")) {
                status = Pages.mainPage(status);
            }
            service.serviceMenu(status);


        } while (!status.getWorkFlow().equals(Flow.STOP.getFlow()));

    }
}
