import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

import static java.lang.Math.round;

public class Metodos {

    private JFrame source;
    private BufferedImage buffer;
    private Graphics graPixel;
    private int xp, yp, zp;
    private int Width, Height;

    public Metodos(JFrame source, int Width, int Height) {
        this.source = source;
        buffer = new BufferedImage(Width, Height, BufferedImage.TYPE_INT_ARGB);
        this.Width = Width;
        this.Height = Height;
    }

    public void limpiarBuffer() {
        buffer = new BufferedImage(this.Width, this.Height, BufferedImage.TYPE_INT_ARGB);
    }
    public void dibujarPixel(int x, int y, Color c) {
        buffer.setRGB(x, y, c.getRGB());
    }
    public void dibujar() {
        graPixel.drawImage(buffer, 0, 0, null);
    }
    public void definirGraphics(Graphics g) {
        graPixel = g;
        graPixel.fillRect(0, 0, buffer.getWidth(), buffer.getHeight());
    }

    public void Linea_DDA(int x1, int x2, int y1, int y2, Color c) {
        int dx = x2 - x1;
        int dy = y2 - y1;
        int pasos;

        if (Math.abs(dx) > Math.abs(dy))
            pasos = Math.abs(dx);
        else
            pasos = Math.abs(dy);

        float xinc = (float) dx / pasos;
        float yinc = (float) dy / pasos;
        float x = x1;
        float y = y1;
        dibujarPixel(round(x), round(y), c);

        for (int i = 1; i < pasos; i++) {
            x = x + xinc;
            y = y + yinc;
            dibujarPixel(round(x), round(y), c);
        }
    }

    public void direccion(int x, int y, int z) {
        this.xp = x;
        this.yp = y;
        this.zp = z;
    }

    public void escala(int x, int y, int z, int size, Color c){
        limpiarBuffer();

        linea3D(x-size, y-size, z+size, x+size, y-size, z+size, c);
        linea3D(x+size, y-size, z+size, x+size, y+size, z+size, c);
        linea3D(x+size, y+size, z+size, x-size, y+size, z+size, c);
        linea3D(x-size, y+size, z+size, x-size, y-size, z+size, c);

        linea3D(x-size, y-size, z-size, x+size, y-size, z-size, c);
        linea3D(x+size, y-size, z-size, x+size, y+size, z-size, c);
        linea3D(x+size, y+size, z-size, x-size, y+size, z-size, c);
        linea3D(x-size, y+size, z-size, x-size, y-size, z-size, c);

        linea3D(x-size, y-size, z-size, x-size, y-size, z+size, c);
        linea3D(x+size, y-size, z-size, x+size, y-size, z+size, c);
        linea3D(x+size, y+size, z-size, x+size, y+size, z+size, c);
        linea3D(x-size, y+size, z-size, x-size, y+size, z+size, c);
        dibujar();
    }

    public void linea3D(int x, int y, int z, int x1, int y1, int z1, Color color){
        int xVal = x3d(x,z);
        int x1Val = x3d(x1,z1);
        int yVal = y3d(y, z);
        int y1Val = y3d(y1, z1);
        Linea_DDA(xVal, x1Val, yVal, y1Val, color);
    }

    public int x3d(int x, int z){
        return round(x-xp*z/zp);
    }

    public int y3d(int y, int z){
        return round(y-yp*z/zp);
    }
}
