import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


class Key implements KeyListener {

    public static int X = 10;
    public static int Y = 20;
    public static int Z = -20;

    public int GetX() {
        return X;
    }

    public int GetY() {
        return Y;
    }

    public int GetZ() {
        return Z;
    }

    private boolean Change = false;

    public boolean GetChange() {
        return Change;
    }

    public void SetChange(boolean NewChange) {
        Change = NewChange;
    }

    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_UP) {
            Y = Y - 1;
            Change = true;
        }
        if (key == KeyEvent.VK_DOWN) {
            X = X - 1;
            Change = true;
        }
        if (key == KeyEvent.VK_LEFT) {
            Y = Y + 1;
            Change = true;
        }
        if (key == KeyEvent.VK_RIGHT) {
            X = X + 1;
            Change = true;
        }

        if (key == KeyEvent.VK_LEFT) {
            X = X - 1;
            Change = true;
        }
        if (key == KeyEvent.VK_RIGHT) {
            X = X + 1;
            Change = true;
        }
        if (key == KeyEvent.VK_UP) {
            Y = Y - 1;
            Change = true;
        }
        if (key == KeyEvent.VK_DOWN) {
            Y = Y + 1;
            Change = true;
        }
        if (key == KeyEvent.VK_ADD || key == KeyEvent.SHIFT_DOWN_MASK) {
            Z = Z + 1;
            Change = true;
        }
        if (key == KeyEvent.VK_MINUS) {
            Z = Z - 1;
            Change = true;
        }

    }

    public void keyReleased(KeyEvent e) {
    }

    public void keyTyped(KeyEvent e) {
    }
}