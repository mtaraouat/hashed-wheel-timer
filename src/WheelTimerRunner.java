import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class WheelTimerRunner {

    public static void main(String[] args) throws InterruptedException {
        HashedWheelTimer hashedWheelTimer = new HashedWheelTimer(100, 1);
        hashedWheelTimer.init();

        scheduleAlarm(hashedWheelTimer);

    }




    /*
    *
    *
    * * * */
    static void scheduleAlarm(HashedWheelTimer hashedWheelTimer) {
       Executors.newScheduledThreadPool(1).schedule(() -> {
           System.out.println("Adding Alarm ...");
           hashedWheelTimer.createTimer(20);

       }, 4, TimeUnit.SECONDS);
    }

}
