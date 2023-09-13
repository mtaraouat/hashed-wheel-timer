public class Timeout {

    long remainingRounds;
    Runnable task;

    public Timeout(long remainingRounds, Runnable task) {
        this.remainingRounds = remainingRounds;
        this.task = task;
    }


}
