import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class HashedWheelTimer {

    private List<LinkedList<Timeout>> wheel ;

    private int tickDuration = 1; // 1 unit
    private int wheelCursor = 0;
    private int wheelSize = 8;

    public HashedWheelTimer(int wheelSize, int tickDuration) {
        this.wheel = new LinkedList<>();
        this.tickDuration = tickDuration;
        this.wheelSize = wheelSize;

        for (int i = 0; i < wheelSize; i++) {
            wheel.add(new LinkedList<Timeout>());
        }

    }

    /*
    *  Initialize
    * * */
    void init() {
        this.createTimer(5);
        this.start();

    }

    /*
     *
     * * */
     private void start() {

        Executors.newSingleThreadScheduledExecutor()
                .scheduleAtFixedRate(timeManager(), 0, tickDuration, TimeUnit.SECONDS);

    }



    /*
    * */
    Runnable timeManager () {
        return () -> {

            if (this.wheelCursor == 0) {
                System.out.println("-------------------------------");
            }


            System.out.println("Timer Book Keeper Bucket : " + this.wheelCursor );
            LinkedList<Timeout> timeouts = wheel.get(this.wheelCursor);

            List expiredTimeouts = new ArrayList();

            for (Timeout timeout : timeouts) {

                if (timeout.remainingRounds <= 0) {
                    timeout.task.run();
                    expiredTimeouts.add(timeout);
                }
                timeout.remainingRounds--;

            }
            timeouts.removeAll(expiredTimeouts);

            this.wheelCursor = (this.wheelCursor== wheelSize-1) ? 0 : (this.wheelCursor+1); //reset it after wheel completes cycle



        };

    }




    /*
    *
    * * */
    void createTimer(int delay) {

       int remainingRounds = delay / this.wheel.size();
       int wheelIndex = (delay % this.wheel.size() + this.wheelCursor ) % this.wheel.size();

       this.wheel.get(wheelIndex).add(new Timeout(remainingRounds, () -> {
            System.out.println("Timeout at " + delay + " seconds");
        })) ;

    }


}


