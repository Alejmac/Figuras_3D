

public class Main implements Runnable{

    private GUI window;

    public static void main(String[] args) {
        new Main().start();
    }

    void start(){
        window = new GUI();
        Thread hilo = new Thread(this);
        hilo.start();
    }

    public void run() {
        boolean exit = false;
        while (!exit) {
            try {
                if(window.GetChange() == true) {
                    window.repaint();
                    window.SetChange(false);
                }
                Thread.sleep(50);
            } catch (InterruptedException ignored) {
                exit = true;
            }
        }
    }
}
