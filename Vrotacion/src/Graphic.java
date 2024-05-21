import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

import static java.lang.Math.*;

public class Graphic {

    private JFrame source;
    private BufferedImage buffer;
    private Graphics graPixel;
    private int xp, yp, zp, width, height;

    public Graphic(JFrame source, int width, int height) {
        this.source = source;
        xp = 10;
        yp = 10;
        zp = 10;

        buffer = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        this.width = width;
        this.height = height;
    }

    public void setDirection(int x, int y){
        xp = x;
        yp = y;
    }

    public void limpiarBuffer() {
        buffer = new BufferedImage(this.width, this.height, BufferedImage.TYPE_INT_RGB);
    }

    public void dibujarPixel(int x, int y, Color c) {
        buffer.setRGB(x, y, c.getRGB());
    }
    public void drawer() {
        graPixel.drawImage(buffer, 0, 0, null);
    }

    public void definirGraphics(Graphics g) {
        this.graPixel = g;
        graPixel.setColor(Color.white);
        graPixel.fillRect(0, 0, buffer.getWidth(), buffer.getHeight());
    }

    public void line3D(int x, int y, int z, int x1, int y1, int z1, Color color){
        int xVal = x3D(x,z);
        int x1Val = x3D(x1,z1);
        int yVal = y3D(y, z);
        int y1Val = y3D(y1, z1);
        Linea_DDA(xVal, x1Val, yVal, y1Val, color);
    }

    public int x3D(int x, int z){
        return round(x-xp*z/zp);
    }

    public int y3D(int y, int z){
        return round(y-yp*z/zp);
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

    public void rotacionXZ(int x, int y, int z, int size, float angle, Color c) {
        int xPlus_zPlus = x + this.rotar(size, size, angle, false);
        int xPlus_zMinus = x + this.rotar(size, -size, angle, false);
        int xMinus_zMinus = x + this.rotar(-size, -size, angle, false);
        int xMinus_zPlus = x + this.rotar(-size, size, angle, false);

        int yPlus_2D = y + size;
        int yMinus_2D = y - size;

        int zPlus_xMinus = z + this.rotar(size, -size, angle, true);
        int zMinus_xMinus = z + this.rotar(-size, -size, angle, true);
        int zMinus_xPlus = z + this.rotar(-size, size, angle, true);
        int zPlus_xPlus = z + this.rotar(size, size, angle, true);

        line3D(xMinus_zMinus, yPlus_2D, zMinus_xMinus, xPlus_zMinus, yPlus_2D, zMinus_xPlus, c);
        line3D(xMinus_zMinus, yPlus_2D, zMinus_xMinus, xMinus_zPlus, yPlus_2D, zPlus_xMinus, c);
        line3D(xPlus_zMinus, yPlus_2D, zMinus_xPlus, xPlus_zPlus, yPlus_2D, zPlus_xPlus, c);
        line3D(xPlus_zPlus, yPlus_2D, zPlus_xPlus, xMinus_zPlus, yPlus_2D, zPlus_xMinus, c);

        line3D(xMinus_zMinus, yMinus_2D, zMinus_xMinus, xPlus_zMinus, yMinus_2D, zMinus_xPlus, c);
        line3D(xMinus_zMinus, yMinus_2D, zMinus_xMinus, xMinus_zPlus, yMinus_2D, zPlus_xMinus, c);
        line3D(xPlus_zMinus, yMinus_2D, zMinus_xPlus, xPlus_zPlus, yMinus_2D, zPlus_xPlus, c);
        line3D(xPlus_zPlus, yMinus_2D, zPlus_xPlus, xMinus_zPlus, yMinus_2D, zPlus_xMinus, c);

        line3D(xMinus_zMinus, yPlus_2D, zMinus_xMinus, xMinus_zMinus, yMinus_2D, zMinus_xMinus, c);
        line3D(xPlus_zMinus, yPlus_2D, zMinus_xPlus, xPlus_zMinus, yMinus_2D, zMinus_xPlus, c);
        line3D(xMinus_zPlus, yPlus_2D, zPlus_xMinus, xMinus_zPlus, yMinus_2D, zPlus_xMinus, c);
        line3D(xPlus_zPlus, yPlus_2D, zPlus_xPlus, xPlus_zPlus, yMinus_2D, zPlus_xPlus, c);
    }

    public void rotacionXY(int x, int y, int z, int size, float angle, Color c){

        int xPlus_yPlus = x + this.rotar(size, size, angle, false);
        int xPlus_yMinus = x + this.rotar(size, -size, angle, false);
        int xMinus_yMinus = x + this.rotar(-size, -size, angle, false);
        int xMinus_yPlus = x + this.rotar(-size, size, angle, false);

        int yPlus_xMinus = y + this.rotar(size, -size, angle, true);
        int yMinus_xMinus = y + this.rotar(-size, -size, angle, true);
        int yMinus_xPlus = y + this.rotar(-size, size, angle, true);
        int yPlus_xPlus = y + this.rotar(size, size, angle, true);

        int zPlus_2D = z + size;
        int zMinus_2D = z - size;

        line3D(xMinus_yPlus, yPlus_xMinus, zMinus_2D, xPlus_yPlus, yPlus_xPlus, zMinus_2D, c);
        line3D(xMinus_yPlus, yPlus_xMinus, zMinus_2D, xMinus_yPlus, yPlus_xMinus, zPlus_2D, c);
        line3D(xPlus_yPlus, yPlus_xPlus, zMinus_2D, xPlus_yPlus, yPlus_xPlus, zPlus_2D, c);
        line3D(xPlus_yPlus, yPlus_xPlus, zPlus_2D, xMinus_yPlus, yPlus_xMinus, zPlus_2D, c);

        line3D(xMinus_yMinus, yMinus_xMinus, zMinus_2D, xPlus_yMinus, yMinus_xPlus, zMinus_2D, c);
        line3D(xMinus_yMinus, yMinus_xMinus, zMinus_2D, xMinus_yMinus, yMinus_xMinus, zPlus_2D, c);
        line3D(xPlus_yMinus, yMinus_xPlus, zMinus_2D, xPlus_yMinus, yMinus_xPlus, zPlus_2D, c);
        line3D(xPlus_yMinus, yMinus_xPlus, zPlus_2D, xMinus_yMinus, yMinus_xMinus, zPlus_2D, c);

        line3D(xMinus_yPlus, yPlus_xMinus, zMinus_2D, xMinus_yMinus, yMinus_xMinus, zMinus_2D, c);
        line3D(xPlus_yPlus, yPlus_xPlus, zMinus_2D, xPlus_yMinus, yMinus_xPlus, zMinus_2D, c);
        line3D(xMinus_yPlus, yPlus_xMinus, zPlus_2D, xMinus_yMinus, yMinus_xMinus, zPlus_2D, c);
        line3D(xPlus_yPlus, yPlus_xPlus, zPlus_2D, xPlus_yMinus, yMinus_xPlus, zPlus_2D, c);

    }

    public void rotacionYZ(int x, int y, int z, int size, float angle, Color c){

        int xPlus_2D = x + size;
        int xMinus_2D = x - size;

        int yPlus_zPlus = y + this.rotar(size, size, angle, false);
        int yPlus_zMinus = y + this.rotar(size, -size, angle, false);
        int yMinus_zMinus = y + this.rotar(-size, -size, angle, false);
        int yMinus_zPlus = y + this.rotar(-size, size, angle, false);

        int zPlus_yMinus = z + this.rotar(size, -size, angle, true);
        int zMinus_yMinus = z + this.rotar(-size, -size, angle, true);
        int zMinus_yPlus = z + this.rotar(-size, size, angle, true);
        int zPlus_yPlus = z + this.rotar(size, size, angle, true);

        line3D(xMinus_2D, yPlus_zMinus, zMinus_yPlus, xPlus_2D, yPlus_zMinus, zMinus_yPlus, c);
        line3D(xMinus_2D, yPlus_zMinus, zMinus_yPlus, xMinus_2D, yPlus_zPlus, zPlus_yPlus, c);
        line3D(xPlus_2D, yPlus_zMinus, zMinus_yPlus, xPlus_2D, yPlus_zPlus, zPlus_yPlus, c);
        line3D(xPlus_2D, yPlus_zPlus, zPlus_yPlus, xMinus_2D, yPlus_zPlus, zPlus_yPlus, c);

        line3D(xMinus_2D, yMinus_zMinus, zMinus_yMinus, xPlus_2D, yMinus_zMinus, zMinus_yMinus, c);
        line3D(xMinus_2D, yMinus_zMinus, zMinus_yMinus, xMinus_2D, yMinus_zPlus, zPlus_yMinus, c);
        line3D(xPlus_2D, yMinus_zMinus, zMinus_yMinus, xPlus_2D, yMinus_zPlus, zPlus_yMinus, c);
        line3D(xPlus_2D, yMinus_zPlus, zPlus_yMinus, xMinus_2D, yMinus_zPlus, zPlus_yMinus, c);

        line3D(xMinus_2D, yPlus_zMinus, zMinus_yPlus, xMinus_2D, yMinus_zMinus, zMinus_yMinus, c);
        line3D(xPlus_2D, yPlus_zMinus, zMinus_yPlus, xPlus_2D, yMinus_zMinus, zMinus_yMinus, c);
        line3D(xMinus_2D, yPlus_zPlus, zPlus_yPlus, xMinus_2D, yMinus_zPlus, zPlus_yMinus, c);
        line3D(xPlus_2D, yPlus_zPlus, zPlus_yPlus, xPlus_2D, yMinus_zPlus, zPlus_yMinus, c);

    }

    public int rotar(int axis, int axis2, float angle, boolean tipo) {
        double sin = sin(angle);
        double cos = cos(angle);
        int rotated = 0;
        if (tipo) {
            rotated = (int)(axis * cos + axis2 * sin);
        } else {
            rotated = (int)(axis * cos - axis2 * sin);
        }
        return rotated;
    }

    public void eje(int x, int y, int z, int size, String eje, float angle, Color c) {
        limpiarBuffer();
        if (eje.equals("xz"))
            rotacionXZ(x, y, z, size, angle, c);
        if (eje.equals("xy"))
            rotacionXY(x, y, z, size, angle, c);
        if (eje.equals("yz"))
            rotacionYZ(x, y, z, size, angle, c);

        drawer();
    }

}
