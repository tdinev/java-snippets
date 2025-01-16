package com.github.tdinev.snippets.threading;

/*
 * Output:
Thread state: NEW
Thread state: RUNNABLE
Thread is running
Thread state: TIMED_WAITING
Thread state: TIMED_WAITING
 */
public class ThreadStatesExample {
    public static void main(String[] args) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("Thread is running");
                try {
                    Thread.sleep(1000); // Moves to Timed Waiting state
                    synchronized (this) {
                        wait(); // Moves to Waiting state
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("Thread is terminated");
            }
        });

        System.out.println("Thread state: " + thread.getState()); // New
        thread.start();
        System.out.println("Thread state: " + thread.getState()); // Runnable

        try {
            Thread.sleep(500);
            System.out.println("Thread state: " + thread.getState()); // Timed Waiting
            synchronized (thread) {
                thread.notify();
            }
            Thread.sleep(100);
            System.out.println("Thread state: " + thread.getState()); // Blocked or Waiting
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
