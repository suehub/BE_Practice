package community.server.feature;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import org.junit.jupiter.api.Test;

class AccountCheckTest {

  private static final String DB_URL = "jdbc:mysql://localhost:3306/bank_system";
  private static final String USER = "root";
  private static final String PASS = "root";
  private static final Lock lock = new ReentrantLock();
  @Test
  public void testConcurrentRequests() throws InterruptedException {
    ExecutorService executor = Executors.newFixedThreadPool(2);
    CountDownLatch latch = new CountDownLatch(2);
    String sql = "SELECT * FROM account WHERE account_num = ?";
    var ref = new Object() {
      ResultSet rs = null;
    };
    AtomicReference<PreparedStatement> ps = new AtomicReference<>();


    Runnable task1 = () -> {
      try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {
        System.out.println("Task 1 is running");

        ps.set(Objects.requireNonNull(conn).prepareStatement(sql));
        ps.get().setInt(1, 14);
        Thread.sleep(1000);
        lock.lock();
        try{
          ref.rs = ps.get().executeQuery();
          while(ref.rs.next()){
            System.out.println(ref.rs.getInt(1));
          }
        }catch (Exception e){
          e.printStackTrace();
        }
        lock.unlock();


        Thread.sleep(1000);
      } catch (InterruptedException | SQLException e) {
        e.printStackTrace();
      } finally {
        latch.countDown();

      }
    };

    Runnable task2 = () -> {
      try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {
        ps.set(Objects.requireNonNull(conn).prepareStatement(sql));
        ps.get().setInt(1, 14);
        Thread.sleep(1000);
        System.out.println("Task 2 is running");

        lock.lock();
        try{
          ref.rs = ps.get().executeQuery();
          while(ref.rs.next()){
            System.out.println(ref.rs.getInt(1));
          }
        }catch (Exception e){
          e.printStackTrace();
        }
        lock.unlock();

      } catch (SQLException | InterruptedException e) {
        e.printStackTrace();
      } finally {
        latch.countDown();
      }
    };

    executor.submit(task1);
    executor.submit(task2);

    latch.await();

    executor.shutdown();
  }
}

