import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class GUI extends JFrame {

    private Graphic MyGraphicsInstance;
    private Boolean checkExistance = Boolean.FALSE;

    private BufferedImage buffer;
    public JPanel myJPanel;
    private Key MyKeyInstance;

    public GUI() {
        super("Prespectiva Paralela");
        int width = 600, height = 600;
        setSize(width, height);
        setLocationRelativeTo(null);
        setVisible(true);
        myJPanel = new JPanel();
        add(myJPanel);
        setVisible(true);
        MyGraphicsInstance = new Graphic(this);
        MyKeyInstance = new Key();
        addKeyListener(MyKeyInstance);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void paint(Graphics g) {
        if (checkExistance == Boolean.FALSE) {
            buffer = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);
            buffer.setRGB(0, 0, Color.blue.getRGB());
            this.getGraphics().drawImage(buffer, getWidth() / 2, getHeight() / 2, this);
            checkExistance = Boolean.TRUE;
            super.paint(g);
        }
        super.paint(g);
        this.update(g);
    }

    public void update(Graphics g) {

        MyGraphicsInstance.ResetBuff();
        MyGraphicsInstance.SetCamara(MyKeyInstance.GetX(), MyKeyInstance.GetY(), MyKeyInstance.GetZ());
        MyGraphicsInstance.SetColor(Color.black);
        MyGraphicsInstance.cube(150, 150, 15, 400, 400, 40);
        this.getGraphics().drawImage(MyGraphicsInstance.GetFondo(), 0, 0, this);

    }

    public boolean GetChange() {
        return MyKeyInstance.GetChange();
    }

    public void SetChange(boolean NewChange) {
        MyKeyInstance.SetChange(NewChange);
    }

}


