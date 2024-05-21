import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.LayoutManager;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;

public class MiVentana extends JFrame implements Runnable {
    private Image fondo;
    private Image buffer;
    int[] proyecion = new int[]{50, 20, 30};
    private BufferedImage bufferImage;
    private Graphics graPixel;
    int[][] puntos = new int[][]{{100, 100, 100}, {200, 100, 100}, {200, 200, 100}, {100, 200, 100}, {100, 200, 200}, {200, 200, 200}, {200, 100, 200}, {100, 100, 200}};
    int[] punto1 = new int[]{900, 100};
    int[] punto2 = new int[]{950, 100};
    int[] punto3 = new int[]{950, 150};
    int[] punto4 = new int[]{900, 150};
    boolean firstime = true;
    int[][] resultado;
    int angulo = 1;
    int anguloMax = 40;

    public MiVentana() {
        this.setTitle("Rotacion");
        this.setDefaultCloseOperation(3);
        this.setSize(1000, 1000);
        this.setLayout((LayoutManager)null);
        this.bufferImage = new BufferedImage(1, 1, 1);
    }

    public void putPixel(int x, int y, Color c, Graphics g) {
        this.bufferImage.setRGB(0, 0, c.getRGB());
        g.drawImage(this.bufferImage, x, y, this);
    }

    public void paint(Graphics g) {
        if (this.fondo == null) {
            this.fondo = this.createImage(this.getWidth(), this.getHeight());
            Graphics gfondo = this.fondo.getGraphics();
            gfondo.setClip(0, 0, this.getWidth(), this.getHeight());
        }

        this.update(g);
    }

    public void update(Graphics g) {
        g.setClip(0, 0, this.getWidth(), this.getHeight());
        this.buffer = this.createImage(this.getWidth(), this.getHeight());
        Graphics gbufer = this.buffer.getGraphics();
        gbufer.setClip(0, 0, this.getWidth(), this.getHeight());
        gbufer.drawImage(this.fondo, 0, 0, this);
        if (this.firstime) {
            this.cubo(this.proyecion, this.puntos, g);
            this.firstime = false;
            this.resultado = this.puntos;
        }

        this.resultado = this.rotacion(this.angulo, this.resultado);
        this.cubo(this.proyecion, this.resultado, g);
        g.drawImage(this.buffer, 0, 0, this);
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

    public int[][] rotacion(int angulo, int[][] puntos) {
        int[][] resultado = multiply(new double[][]{{1.0, 0.0, 0.0, 0.0}, {0.0, Math.cos(Math.toRadians((double)angulo)), Math.sin(Math.toRadians((double)angulo)), 0.0}, {0.0, -Math.sin(Math.toRadians((double)angulo)), Math.cos(Math.toRadians((double)angulo)), 0.0}, {0.0, 0.0, 0.0, 1.0}}, new int[][]{{puntos[0][0], puntos[0][1], puntos[0][2], 1}, {puntos[1][0], puntos[1][1], puntos[1][2], 1}, {puntos[2][0], puntos[2][1], puntos[2][2], 1}, {puntos[3][0], puntos[3][1], puntos[3][2], 1}, {puntos[4][0], puntos[4][1], puntos[4][2], 1}, {puntos[5][0], puntos[5][1], puntos[5][2], 1}, {puntos[6][0], puntos[6][1], puntos[6][2], 1}, {puntos[7][0], puntos[7][1], puntos[7][2], 1}});
        return resultado;
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

            for(incNE = 2 * (dy - dx); x != x1; this.putPixel(x, y, Color.red, g)) {
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

            for(incNE = 2 * (dx - dy); y != y1; this.putPixel(x, y, Color.red, g)) {
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

    public static int[][] multiply(double[][] a, int[][] b) {
        int[][] c = new int[b.length][a[0].length];
        if (a[0].length == b[0].length) {
            for(int i = 0; i < b.length; ++i) {
                for(int z = 0; z < a.length; ++z) {
                    int aux = 0;

                    for(int j = 0; j < b[0].length; ++j) {
                        aux = (int)((double)aux + (double)b[i][j] * a[z][j]);
                    }

                    c[i][z] = aux;
                }
            }
        }

        return c;
    }

    public void run() {
        while(this.angulo < this.anguloMax) {
            try {
                ++this.angulo;
                this.repaint();
                Thread.sleep(300L);
            } catch (InterruptedException var2) {
                var2.printStackTrace();
            }
        }

    }
}
