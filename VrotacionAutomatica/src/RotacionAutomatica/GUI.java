package RotacionAutomatica;

import javax.swing.*;
import java.awt.Point;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GUI extends JFrame implements KeyListener {

    private Graphic draw;
    private int x, y, z, size;
    public float angleYZ, angleXZ, angleXY;

    public GUI() {
        setTitle("Rotacion 3D automaica");
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        addKeyListener(this);
        setSize(400, 400);
        draw = new Graphic(this, 400, 400);
        x = 200;
        y = 200;
        z = 0;
        size = 50;
        angleYZ = 0;
        angleXZ = 0;
        angleXY = 0;
    }

    public void paint(Graphics g) {
        Point punt = MouseInfo.getPointerInfo().getLocation();
        draw.setDirection((int) (punt.x-400)/30, (int) (punt.y-400)/30);
        draw.clearBuffer();
        draw.defGraph(g);
        draw.drawCube(x, y, z, size, angleYZ, angleXZ, angleXY, Color.RED);
        draw.dibujar();
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT:
                angleXY -= 0.1;
                break;
            case KeyEvent.VK_RIGHT:
                angleXY += 0.1;;
                break;
            case KeyEvent.VK_UP:
                angleXZ += 0.1;;
                break;
            case KeyEvent.VK_DOWN:
                angleXZ -= 0.1;
                break;
            default:
                break;
        }
        repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }


}
