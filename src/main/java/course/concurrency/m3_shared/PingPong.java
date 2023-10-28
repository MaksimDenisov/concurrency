package course.concurrency.m3_shared;

public class PingPong {
    public static volatile boolean isPing = true;
    private static final Object lock = new Object();

    public static void ping() {
        try {
            while (true) {
                synchronized (lock) {
                    if (!isPing) {
                        lock.wait();
                    }
                    System.out.println("Ping");
                    Thread.sleep(1000);
                    isPing = false;
                    lock.notify();
                }
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static void pong() {
        try {
            while (true) {
                synchronized (lock) {
                    if (isPing) {
                        lock.wait();
                    }
                    System.out.println("Pong");
                    Thread.sleep(1000);
                    isPing = true;
                    lock.notify();
                }
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        Thread t1 = new Thread(() -> ping());
        Thread t2 = new Thread(() -> pong());
        t1.start();
        t2.start();
    }
}
