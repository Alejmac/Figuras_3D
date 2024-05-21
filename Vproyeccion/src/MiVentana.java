import java.awt.Color;
import java.awt.Graphics;
import java.awt.LayoutManager;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;

public class MiVentana extends JFrame {
    private BufferedImage buffer;
    private Graphics graPixel;
    int[] proyecion = new int[]{50, 20, 30};
    int[][] puntos = new int[][]{{100, 100, 100}, {200, 100, 100}, {200, 200, 100}, {100, 200, 100}, {100, 200, 200}, {200, 200, 200}, {200, 100, 200}, {100, 100, 200}};
    int factor = 2;

    public MiVentana() {
        this.setTitle("Malla");
        this.setDefaultCloseOperation(3);
        this.setSize(600, 600);
        this.setLayout((LayoutManager)null);
        this.buffer = new BufferedImage(1, 1, 1);
        this.graPixel = this.buffer.createGraphics();
    }

    public void putPixel(int x, int y, Color c, Graphics g) {
        this.buffer.setRGB(0, 0, c.getRGB());
        this.getGraphics().drawImage(this.buffer, x, y, this);
    }

    public void paint(Graphics g) {
        super.paint(g);
        this.cubo(this.proyecion, this.puntos, g);
    }

    public void cubo(int[] proyecion, int[][] puntos, Graphics g) {
        int[][] finales = new int[puntos.length][2];

        for(int i = 0; i < puntos.length; ++i) {
            int x = puntos[i][0] + proyecion[0] * (-puntos[i][2] / proyecion[2]);
            int y = puntos[i][1] + proyecion[1] * (-puntos[i][2] / proyecion[2]);
            finales[i] = new int[]{x, y};
        }

        this.Bresenham(finales[0][0] + 300, finales[0][1] + 300, finales[1][0] + 300, finales[1][1] + 300, g);
        this.Bresenham(finales[1][0] + 300, finales[1][1] + 300, finales[2][0] + 300, finales[2][1] + 300, g);
        this.Bresenham(finales[2][0] + 300, finales[2][1] + 300, finales[3][0] + 300, finales[3][1] + 300, g);
        this.Bresenham(finales[3][0] + 300, finales[3][1] + 300, finales[0][0] + 300, finales[0][1] + 300, g);
        this.Bresenham(finales[3][0] + 300, finales[3][1] + 300, finales[4][0] + 300, finales[4][1] + 300, g);
        this.Bresenham(finales[4][0] + 300, finales[4][1] + 300, finales[5][0] + 300, finales[5][1] + 300, g);
        this.Bresenham(finales[5][0] + 300, finales[5][1] + 300, finales[6][0] + 300, finales[6][1] + 300, g);
        this.Bresenham(finales[6][0] + 300, finales[6][1] + 300, finales[7][0] + 300, finales[7][1] + 300, g);
        this.Bresenham(finales[5][0] + 300, finales[5][1] + 300, finales[2][0] + 300, finales[2][1] + 300, g);
        this.Bresenham(finales[7][0] + 300, finales[7][1] + 300, finales[6][0] + 300, finales[6][1] + 300, g);
        this.Bresenham(finales[0][0] + 300, finales[0][1] + 300, finales[7][0] + 300, finales[7][1] + 300, g);
        this.Bresenham(finales[4][0] + 300, finales[4][1] + 300, finales[7][0] + 300, finales[7][1] + 300, g);
        this.Bresenham(finales[1][0] + 300, finales[1][1] + 300, finales[6][0] + 300, finales[6][1] + 300, g);
    }

    public void Bresenham(int x0, int y0, int x1, int y1, Graphics g) {
        int dx = x1 - x0;
        int dy = y1 - y0;
        byte stepy;
        if (dy < 0) {
            dy = -dy;
            stepy = -1;
        } else {
            stepy = 1;
        }

        byte stepx;
        if (dx < 0) {
            dx = -dx;
            stepx = -1;
        } else {
            stepx = 1;
        }

        int x = x0;
        int y = y0;
        this.putPixel(x0, y0, Color.red, g);
        int p;
        int incE;
        int incNE;
        if (dx > dy) {
            p = 2 * dy - dx;
            incE = 2 * dy;

            for(incNE = 2 * (dy - dx); x != x1; this.putPixel(x, y, Color.green, g)) {
                x += stepx;
                if (p < 0) {
                    p += incE;
                } else {
                    y += stepy;
                    p += incNE;
                }
            }
        } else {
            p = 2 * dx - dy;
            incE = 2 * dx;

            for(incNE = 2 * (dx - dy); y != y1; this.putPixel(x, y, Color.green, g)) {
                y += stepy;
                if (p < 0) {
                    p += incE;
                } else {
                    x += stepx;
                    p += incNE;
                }
            }
        }

    }
}
