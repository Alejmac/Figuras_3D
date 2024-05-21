package RotacionAutomatica;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

import static java.lang.Math.round;

public class Graphic {

    Line[] cube = new Line[12];
    private JFrame source;
    private BufferedImage buffer;
    private Graphics graPixel;
    private int width, height;
    int xp, yp, zp;

    public Point start;
    public Point end;

    public Graphic(JFrame source, int width, int height) {
        xp = 15;
        yp = 15;
        zp = 10;

        this.source = source;
        buffer = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        this.width = width;
        this.height = height;
    }

    public void clearBuffer() {
        buffer = new BufferedImage(this.width, this.height, BufferedImage.TYPE_INT_RGB);
    }

    public void drawPixel(int x, int y, Color c) {
        buffer.setRGB(x, y, c.getRGB());
    }
    public void dibujar() {
        graPixel.drawImage(buffer, 0, 0, null);
    }

    public void defGraph(Graphics g) {
        this.graPixel = g;
    }

    public void drawCube(int x, int y, int z, int size, float rYZ, float rXZ, float rXY, Color c) {
        cube = genCube(0,0,0,size);
        rotateEdge(cube, "yz", rYZ);
        rotateEdge(cube, "xz", rXZ);
        rotateEdge(cube, "xy", rXY);
        positions(cube, x,y,z);
        drawEde(cube, c);
    }

    public void drawEde(Line[] edges, Color c){
        for(int i=0; i<edges.length; i++){
            this.draw3DLine(edges[i], c);
        }
    }

    public void rotateEdge(Line[] edges, String eje, float angle){
        for(int i=0; i<edges.length; i++){
            if (eje.equals("yz"))
                edges[i].rotateEdgeYZ(angle);
            if(eje.equals("xz"))
                edges[i].rotateEdgeXZ(angle);
            if(eje.equals("xy"))
                edges[i].rotateEdgeXY(angle);
        }
    }

    public void positions(Line[] edges, int x, int y, int z){
        for(int i=0; i<edges.length; i++){
            edges[i].start.x += x;
            edges[i].start.y += y;
            edges[i].start.z += z;
            edges[i].end.x += x;
            edges[i].end.y += y;
            edges[i].end.z += z;
        }
    }

    public Line[] genCube(int x, int y, int z, int size){
        Line[] result = new Line[12];

        result[0] = new Line(x-size, y-size, z+size, x+size, y-size, z+size);
        result[1] = new Line(x+size, y-size, z+size, x+size, y+size, z+size);
        result[2] = new Line(x+size, y+size, z+size, x-size, y+size, z+size);
        result[3] = new Line(x-size, y+size, z+size, x-size, y-size, z+size);

        result[4] = new Line(x-size, y-size, z-size, x+size, y-size, z-size);
        result[5] = new Line(x+size, y-size, z-size, x+size, y+size, z-size);
        result[6] = new Line(x+size, y+size, z-size, x-size, y+size, z-size);
        result[7] = new Line(x-size, y+size, z-size, x-size, y-size, z-size);

        result[8] = new Line(x-size, y-size, z-size, x-size, y-size, z+size);
        result[9] = new Line(x+size, y-size, z-size, x+size, y-size, z+size);
        result[10] = new Line(x+size, y+size, z-size, x+size, y+size, z+size);
        result[11] = new Line(x-size, y+size, z-size, x-size, y+size, z+size);

        return result;
    }

    public void setDirection(int x, int y){
        xp = x;
        yp = y;
    }

    public void draw3DLine(Line linea, Color c){
        this.draw3DLine(linea.start.x, linea.start.y, linea.start.z, linea.end.x, linea.end.y, linea.end.z, c);
    }

    public void draw3DLine(int x, int y, int z, int x1, int y1, int z1, Color color){
        int xVal = x3d(x,z);
        int x1Val = x3d(x1,z1);
        int yVal = y3d(y, z);
        int y1Val = y3d(y1, z1);
        DDA(xVal, x1Val, yVal, y1Val, color);
    }

    public int x3d(int x, int z){
        return round(x-xp*z/zp);
    }

    public int y3d(int y, int z){
        return round(y-yp*z/zp);
    }

    public void DDA(int x1, int x2, int y1, int y2, Color c) {
        int dx = x2 - x1;
        int dy = y2 - y1;
        int steps;

        if (Math.abs(dx) > Math.abs(dy))
            steps = Math.abs(dx);
        else
            steps = Math.abs(dy);

        float xinc = (float) dx / steps;
        float yinc = (float) dy / steps;
        float x = x1;
        float y = y1;
        drawPixel(round(x), round(y), c);

        for (int i = 1; i < steps; i++) {
            x = x + xinc;
            y = y + yinc;
            drawPixel(round(x), round(y), c);
        }
    }
}
