import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.LayoutManager;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javax.swing.JFrame;

public class MiVentana extends JFrame implements Runnable {
    private Image fondo;
    private Image buffer;
    int[] proyecion = new int[]{50, 20, 30};
    private BufferedImage bufferImage;
    private Graphics graPixel;
    public ArrayList<String> puntos_dibujados;
    int[][] puntos = new int[][]{{100, 100, 100}, {200, 100, 100}, {200, 200, 100}, {100, 200, 100}, {100, 200, 200}, {200, 200, 200}, {200, 100, 200}, {100, 100, 200}};
    boolean firstime = true;
    int maxX = 1000;
    int mayY = 100;
    int incX = 0;
    int incY = 0;
    int incZ = 0;
    int[][] resultado;
    int angulo = 1;
    int anguloMax = 90;

    public MiVentana() {
        this.setTitle("Traslacion");
        this.setDefaultCloseOperation(3);
        this.setSize(1000, 800);
        this.setLayout((LayoutManager)null);
        this.bufferImage = new BufferedImage(1, 1, 1);
        this.puntos_dibujados = new ArrayList();
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

        this.resultado = this.tralacion(this.incX, this.incY, this.incZ, this.resultado);
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

    public int[][] tralacion(int incX, int incY, int incZ, int[][] puntos) {
        int[][] resultado = multiply(new int[][]{{1, 0, 0, incX}, {0, 1, 0, incY}, {0, 0, 1, incZ}, {0, 0, 0, 1}}, new int[][]{{puntos[0][0], puntos[0][1], puntos[0][2], 1}, {puntos[1][0], puntos[1][1], puntos[1][2], 1}, {puntos[2][0], puntos[2][1], puntos[2][2], 1}, {puntos[3][0], puntos[3][1], puntos[3][2], 1}, {puntos[4][0], puntos[4][1], puntos[4][2], 1}, {puntos[5][0], puntos[5][1], puntos[5][2], 1}, {puntos[6][0], puntos[6][1], puntos[6][2], 1}, {puntos[7][0], puntos[7][1], puntos[7][2], 1}});
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
        ArrayList var10000 = this.puntos_dibujados;
        String var10001 = String.valueOf(x0);
        var10000.add(var10001 + "," + String.valueOf(y0));
        int p;
        int incE;
        int incNE;
        if (dx > dy) {
            p = 2 * dy - dx;
            incE = 2 * dy;
            incNE = 2 * (dy - dx);

            while(x != x1) {
                x += stepx;
                if (p < 0) {
                    p += incE;
                } else {
                    y += stepy;
                    p += incNE;
                }

                this.putPixel(x, y, Color.red, g);
                var10000 = this.puntos_dibujados;
                var10001 = String.valueOf(x);
                var10000.add(var10001 + "," + String.valueOf(y));
            }
        } else {
            p = 2 * dx - dy;
            incE = 2 * dx;
            incNE = 2 * (dx - dy);

            while(y != y1) {
                y += stepy;
                if (p < 0) {
                    p += incE;
                } else {
                    x += stepx;
                    p += incNE;
                }

                this.putPixel(x, y, Color.red, g);
                var10000 = this.puntos_dibujados;
                var10001 = String.valueOf(x);
                var10000.add(var10001 + "," + String.valueOf(y));
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

    public static int[][] multiply(int[][] a, int[][] b) {
        int[][] c = new int[b.length][a[0].length];
        if (a[0].length == b[0].length) {
            for(int i = 0; i < b.length; ++i) {
                for(int z = 0; z < a.length; ++z) {
                    int aux = 0;

                    for(int j = 0; j < b[0].length; ++j) {
                        aux += b[i][j] * a[z][j];
                    }

                    c[i][z] = aux;
                }
            }
        }

        return c;
    }

    public void run() {
        while(this.incX < this.maxX) {
            try {
                ++this.incX;
                ++this.incY;
                this.incZ = 1;
                this.angulo += 3;
                this.repaint();
                Thread.sleep(180L);
            } catch (InterruptedException var2) {
                var2.printStackTrace();
            }
        }

    }
}

