import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Ventana extends JFrame implements KeyListener {

    private Metodos draw;
    private int x, y, z, size;
    Color color;

    public Ventana(){

        super("escalacion ");
        x = 375;
        y = 400;
        z = 300;
        size = 50;
        color = Color.blue;
        int width = 600, height = 600;
        draw = new Metodos (this, width, height);
        setSize(width, height);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        addKeyListener(this);
    }

    public void paint(Graphics g) {
        g.setColor(Color.white);
        draw.definirGraphics(g);
        draw.direccion(7, 7, 25);
        draw.escala(x, y, z, size, color);
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getExtendedKeyCode() == KeyEvent.VK_UP){
            if (size < 175) {
                size *= 1.25;
                draw.escala(x, y, z, size, color);
                repaint();
            }
    }
            if (e.getExtendedKeyCode() ==KeyEvent.VK_DOWN) {
                if (size > 4) {
                    size /= 1.25;
                    draw.escala(x, y, z, size, color);
                    repaint();
                }
            }
        }


    @Override
    public void keyReleased(KeyEvent e) {
    }

    public static void main(String[] args) {
        new Main();
    }

}
