package RotacionAutomatica;

public class Line {

    public Point start;
    public Point end;

    public Line(Point start, Point end){
        this.start = start;
        this.end = end;
    }

    public Line(int x1, int y1, int z1, int x2, int y2, int z2){
        this.start = new Point(x1,y1,z1);
        this.end = new Point(x2,y2,z2);
    }

    public void rotateEdgeYZ(float angulo){
        start.rotatePointYZ(angulo);
        end.rotatePointYZ(angulo);
    }

    public void rotateEdgeXZ(float angulo){
        start.rotatePointXZ(angulo);
        end.rotatePointXZ(angulo);
    }

    public void rotateEdgeXY(float angulo){
        start.rotatePointXY(angulo);
        end.rotatePointXY(angulo);
    }
}
