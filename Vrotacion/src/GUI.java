import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import static java.lang.Thread.sleep;

public class GUI extends JFrame implements KeyListener, Runnable{

    private Graphic draw;
    private int x, y, z, size;
    private float angle;
    private String eje;

    Point point;

    public GUI(){
        super("Rotacion 3D");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        draw = new Graphic(this, 400, 400);
        setSize(400, 400);
        setVisible(true);
        addKeyListener(this);
        point = new Point();
        x = 200;
        y = 200;
        z = 0;
        size = 50;
        angle = 0;
        eje = "yz";
        Thread thread = new Thread(this);
        thread.start();
    }

    public void paint(Graphics g) {
        point = MouseInfo.getPointerInfo().getLocation();
        draw.setDirection((int) (point.x-200)/30, (int) (point.y-200)/30);
        draw.definirGraphics(g);
        draw.eje(x,y,z,size,eje,angle,Color.RED);
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT:
                angle-=0.05;
                break;
            case KeyEvent.VK_RIGHT:
                angle+=0.05;
                break;
            case KeyEvent.VK_UP:
                angle+=0.05;
                break;
            case KeyEvent.VK_DOWN:
                angle-=0.05;
                break;
            default:
                break;
        }
        repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void run() {
        while(true){
            repaint();
            try {
                sleep(50);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
