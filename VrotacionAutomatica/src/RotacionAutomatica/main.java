package RotacionAutomatica;

import static java.lang.Thread.sleep;

public class main {

    public static void main(String[] args) {

        Runnable intialFrame = new Runnable() {
            @Override
            public void run() {
                GUI main = new GUI();
                while (true) {
                    main.angleYZ += 0.01;
                    main.angleXZ += 0.02;
                    main.angleXY += 0.03;
                    main.repaint();
                    try {
                        sleep(50);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        };

        Thread thread = new Thread(intialFrame);
        thread.start();
    }
}
