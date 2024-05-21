package RotacionAutomatica;

public class Point {

    public int x,y,z;
    public Point(int x, int y, int z){
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public void rotatePointYZ(float angle){
        int y = this.y;
        int z = this.z;
        int finaly = (int) (y*Math.cos(angle) - z*Math.sin(angle));
        int finalz = (int) (y*Math.sin(angle) + z*Math.cos(angle));
        this.y = finaly;
        this.z = finalz;
    }

    public void rotatePointXZ(float angle){
        int x = this.x;
        int z = this.z;
        int finalx = (int) (z*Math.sin(angle) + x*Math.cos(angle));
        int finalz = (int) (z*Math.cos(angle) - x*Math.sin(angle));
        this.x = finalx;
        this.z = finalz;
    }

    public void rotatePointXY(float angle){
        int x = this.x;
        int y = this.y;
        int finalx = (int) (x*Math.cos(angle) - y*Math.sin(angle));
        int finaly = (int) (x*Math.sin(angle) + y*Math.cos(angle));
        this.x = finalx;
        this.y = finaly;
    }
}
