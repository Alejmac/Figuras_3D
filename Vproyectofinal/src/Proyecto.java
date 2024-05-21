
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.sound.sampled.*;
import javax.swing.JFrame;


public class Proyecto extends JFrame implements Runnable{
    private BufferedImage bufferPixel, imagenCompleta;

    private int alternadorcolor=0;
    private Color unknown,miColor;
    private Thread hilo;


    private float[][] PUNTOS_GENERICOS=new float[][]{
            {0,0,0,0},
            {0,0,0,0},
            {0,0,0,0},
            {0,0,0,0},
            {0,0,0,0},
            {0,0,0,0}
    };
    private float  cuboX[][];
    private float carasX[][];
    private float centrosX[][];

    private float  cuboExtra[][];
    private float carasExtra[][];
    private float centrosExtra[][];
    private float printCuboExtra[][];
    private float printCuboX[][];



    private float FIGURA_ANGULO_X_2[];
    private float FIGURA_ANGULO_Y_2[];
    private final int X = 0, Y = 1, Z = 2;

    private int ajustador=1;

    int [] centroColicion={410,390};


    public int [] cubo1mov={10,70};
    public int [] cubo2mov={10,700};
    public int [] cubo3mov={800,70};
    public int [] cubo4mov={800,700};

    /*
    private float[][] arbol=new float[][]{
            {0,1,2},
            {2,1,2},
            {2,3,2},
            {0,3,2},
            {1,2,5},

            {1.5F, 1.5F,2},
            {0.5F, 1.5F,2},
            {0.5F, 2.5F,2},
            {1.5F,2.5F,2},

            {1.5F, 1.5F,1},
            {0.5F, 1.5F,1},
            {0.5F,2.5F,1},
            {1.5F, 2.5F,1},
    };
    */

    public Proyecto(){

        setTitle("Proyecto final graficas ");
        setSize(1000,1000);
        setLayout(null);
        this.setDefaultCloseOperation(3);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(Color.white);

        // metodo para llamar el audio
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("src/conver.wav").getAbsoluteFile());
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        } catch (LineUnavailableException | UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch(Exception e) {
            System.out.println("Error al reproducir el sonido.");
        }

        this.bufferPixel = new BufferedImage(1,1,BufferedImage.TYPE_INT_RGB);
        this.imagenCompleta = new BufferedImage(1000,1000,BufferedImage.TYPE_INT_RGB);
        this.hilo = new Thread(this);
        this.hilo.start();
        int colorDesconocido = imagenCompleta.getRGB(0, 0);

        //COLORES RELEVANTES
        unknown = new Color(colorDesconocido);
        miColor = Color.red;

        //PUNTOS DE FIGURAS GENERICAS
        FIGURA_ANGULO_X_2 = new float[]{0,20,20,0};
        FIGURA_ANGULO_Y_2 = new float[]{0,0,20,20};

        //FIGURAS ESENCIALES
        cuboX = profundidad_figura(FIGURA_ANGULO_X_2, FIGURA_ANGULO_Y_2, 20);
        carasX = PUNTOS_GENERICOS;
        centrosX = centro_tridimensional(cuboX, 20);
        cuboExtra = profundidad_figura(FIGURA_ANGULO_X_2, FIGURA_ANGULO_Y_2, 20);
        carasExtra = PUNTOS_GENERICOS;
        centrosExtra = centro_tridimensional(cuboExtra, 20);
        setVisible(true);


    }

    public void paint(Graphics g){
        // se inicializa el objeto del bufer
        imagenCompleta = new BufferedImage(1000,1000,BufferedImage.TYPE_INT_RGB);

        float minZ, punto = 0;
        float minZExtra, puntoExtra=0;

        // si el valor del cubo1 es menor a 477 entonces se llaman a los metodos de rotacion
        if(cubo1mov[0]<477) {
            cuboX = rotacionX(cuboX, 5);
            centrosX = rotacionX(centrosX,5);
            cuboX = rotacionY(cuboX, 5);
            centrosX = rotacionY(centrosX,5);
            cuboX = rotacionZ(cuboX, 5);
            centrosX = rotacionZ(centrosX,5);

            printCuboX = convertir3Dto2D(cuboX,0,0,1);
            // convierto las coordenadas 3d a 2d utilizando el metodo
            float[][] printCentrosX = convertir3Dto2D(centrosX, 0, 0, 1);

            // se va a buscar la minima coordenada en z  dentro de la matriz  cubox y identifica el punto correspondiente
            minZ = cuboX[0][Z];
            for(int i = 0; i < cuboX.length; i++){
                if(minZ>cuboX[i][Z]){
                    minZ = cuboX[i][Z];
                    punto = i;
                }
            }
            // llamo al metodo rellenar cubo , para rellenar  las caras de los cubos segun sus posiciones
            // sibujar caras sin relleno ,Toma como entrada las coordenadas 2D

            dibujar_profundidad_figura(printCuboX, punto, cubo1mov[0], cubo1mov[1], 3, 3, Color.white);
            dibujar_profundidad_figura(printCuboX, punto, cubo2mov[0], cubo2mov[1], 3, 3, Color.orange);
            dibujar_profundidad_figura(printCuboX, punto, cubo3mov[0], cubo3mov[1], 3, 3, Color.red);
            dibujar_profundidad_figura(printCuboX, punto, cubo4mov[0], cubo4mov[1], 3, 3, Color.blue);

            // rellenar las caras de los cubos , toma las coordenadas 3D y los puntos de referencia
            rellenoCubo(printCentrosX, carasX, punto, cubo1mov[0], cubo1mov[1], 3, 3, new Color[]{Color.blue, Color.blue, Color.blue, Color.blue, Color.blue, Color.blue});
            rellenoCubo(printCentrosX, carasX, punto, cubo2mov[0], cubo2mov[1], 3, 3, new Color[]{Color.green, Color.green, Color.green, Color.green, Color.green, Color.green});
            rellenoCubo(printCentrosX, carasX, punto, cubo3mov[0], cubo3mov[1], 3, 3, new Color[]{Color.orange, Color.orange, Color.orange, Color.orange, Color.orange, Color.orange});
            rellenoCubo(printCentrosX, carasX, punto, cubo4mov[0], cubo4mov[1], 3, 3, new Color[]{Color.white, Color.white, Color.white, Color.white, Color.white, Color.white});

        }
        // dibujar la imagen completa
        g.drawImage(imagenCompleta,0,0,null);

    }

    public void rellenoCubo(float[][] centros, float[][] caras, float omitir, int disminucionX, int disminucionY, int amplificacionX, int amplificacionY, Color[] c){
        // el siguiente ciclo for recorre inversamente todas las caras del cubo y verifica el estado de cada una de las caras
        for(int i = caras.length-1; i >= 0 ; i--){
            // Si la cara actual contiene el punto a omitir, no se hace nada
            if(caras[i][0]==omitir||caras[i][1]==omitir||caras[i][2]==omitir||caras[i][3]==omitir){
            }else {
                // Si la cara no contiene el punto a omitir, se realiza el relleno
                // Calcula las coordenadas 2D ajustadas con amplificación y disminución
                // Llama a la función de relleno con las coordenadas y el color correspondiente
                relleno((int)(centros[i][X]*amplificacionX)+disminucionX, (int)(centros[i][Y]*amplificacionY)+disminucionY, unknown, c[i]);
            }
        }
    }

    public void relleno(int x,int y, Color color, Color color_relleno){
        // Comprueba si el color del pixel en las coordenadas (x, y) coincide con el color original.
        if(color.getRGB()==imagenCompleta.getRGB(x, y)){
            // Si el color coincide, se coloca un nuevo píxel con el color de relleno en las coordenadas (x, y).
            this.putPixel(x, y, color_relleno);
            // Se aplica recursivamente el algoritmo a los píxeles adyacentes en todas las direcciones.
            this.relleno(x-1,y,color,color_relleno);
            this.relleno(x+1,y,color, color_relleno);
            this.relleno(x,y-1,color,color_relleno);
            this.relleno(x,y+1,color,color_relleno);
        }
    }

    public float [][] rotacionX(float figura_en_3d[][], int angulo){
        // Se crea una nueva matriz para almacenar la figura rotada
        float[][] figura = new float[figura_en_3d.length][3];
        // Se aplica la rotación a cada punto de la figura original
        for(int i = 0; i<figura_en_3d.length; i++){
            // La coordenada X permanece sin cambios
            figura[i][X] = figura_en_3d[i][X];
            // Se aplica la rotación alrededor del eje X para las coordenadas Y y Z
            //Se aplican las fórmulas de rotación para las coordenadas
            figura[i][Y] = (float)((figura_en_3d[i][Y]*Math.cos(Math.toRadians(angulo)))-(figura_en_3d[i][Z]*(Math.sin(Math.toRadians(angulo)))));
            figura[i][Z] = (float)((figura_en_3d[i][Y]*Math.sin(Math.toRadians(angulo)))+(figura_en_3d[i][Z]*(Math.cos(Math.toRadians(angulo)))));
        }
        // Se devuelve la matriz con la figura rotada
        return figura;
    }

    public float [][] rotacionY(float figura_en_3d[][], int angulo){
        // Se crea una nueva matriz para almacenar la figura rotada
        float[][] figura = new float[figura_en_3d.length][3];
        for(int i = 0; i<figura_en_3d.length; i++){
            // Se aplica la rotación a cada punto de la figura original.
            figura[i][X] = (float)((figura_en_3d[i][X]*Math.cos(Math.toRadians(angulo)))+(figura_en_3d[i][Z]*(Math.sin(Math.toRadians(angulo)))));
            figura[i][Y] = figura_en_3d[i][Y];
            figura[i][Z] = (float)((-figura_en_3d[i][X]*Math.sin(Math.toRadians(angulo)))+(figura_en_3d[i][Z]*(Math.cos(Math.toRadians(angulo)))));
        }
        return figura;
    }

    public float [][] rotacionZ(float figura_en_3d[][], int angulo){
        float[][] figura = new float[figura_en_3d.length][3];

        //  bucle recorre cada punto (vértice) en la figura tridimensional.
        for(int i = 0; i<figura_en_3d.length; i++){
            // Se aplica la rotación alrededor del eje Z para las coordenadas X e Y
            //La coordenada Z del punto se copia directamente a la nueva matriz figura
            figura[i][Z] = figura_en_3d[i][Z];
            //Esta línea calcula la nueva coordenada X después de aplicar la rotación alrededor del eje Z
            figura[i][X] = (float)((figura_en_3d[i][X]*Math.cos(Math.toRadians(angulo)))-(figura_en_3d[i][Y]*(Math.sin(Math.toRadians(angulo)))));
            // Esta línea calcula la nueva coordenada Y después de aplicar la rotación alrededor del eje Z
            figura[i][Y] = (float)((figura_en_3d[i][X]*Math.sin(Math.toRadians(angulo)))+(figura_en_3d[i][Y]*(Math.cos(Math.toRadians(angulo)))));
        }
        return figura;
    }


    public float [][] convertir3Dto2D(float figura_en_3d[][], int dx, int dy, int dz){
        //Se crea una nueva matriz para almacenar las coordenadas 2D resultantes
        float[][] fugura_2D = new float[figura_en_3d.length][2];

        for(int i = 0; i<figura_en_3d.length; i++){
           // Proyecta la coordenada X 3D en la coordenada X 2D. La proyección tiene en cuenta la distancia
            fugura_2D[i][X] = (figura_en_3d[i][X]-((dx/dz)*figura_en_3d[i][Z]));
            fugura_2D[i][Y] = (figura_en_3d[i][Y]-((dy/dz)*figura_en_3d[i][Z]));
            //visualizo en la consola
            System.out.println("x:"+fugura_2D[i][X]+" y:"+fugura_2D[i][Y]);
        }

        return fugura_2D;
    }

    //Esta función se encarga de dibujar las aristas de una figura en 2D,
    public void dibujar_profundidad_figura(float[][] fugura_2D, float omitir, int disminucionX, int disminucionY, int amplificacionX, int amplificacionY, Color c){
        //Calcula la mitad de la longitud de la matriz
        int n = fugura_2D.length/2;
        int nn = n;
        //  iterar sobre las aristas de la figura y utiliza la función
        //sta función se encarga de dibujar líneas para representar las aristas de un objeto
        for(int i = 0; i<n-1;i++){
            if(i!=omitir&&i+1!=omitir){
                Bresenham((int) ((fugura_2D[i][X]*amplificacionX)+disminucionX), (int) ((fugura_2D[i][Y]*amplificacionY)+disminucionY), (int) ((fugura_2D[i+1][X]*amplificacionX)+disminucionX), (int) ((fugura_2D[i+1][Y]*amplificacionY)+disminucionY), c);
            }
        }
        //Este bucle recorre las primeras n-1 coordenadas del objeto en 2D , verifica si tiene el punto omitir
        //dibuja una línea entre esos dos puntos usando el algoritmo de Bresenham
        if(n-1!=omitir&&0!=omitir)
            Bresenham((int) ((fugura_2D[n-1][X]*amplificacionX)+disminucionX), (int) ((fugura_2D[n-1][Y]*amplificacionY)+disminucionY), (int) ((fugura_2D[0][X]*amplificacionX)+disminucionX), (int) ((fugura_2D[0][Y]*amplificacionY)+disminucionY), c);
        //Este bucle recorre las coordenadas desde nn
        //Similar al primer bucle, dibuja líneas entre los puntos consecutivos que no son el punto a omitir
        for(int i = nn; i<fugura_2D.length-1;i++){
            if(i!=omitir&&i+1!=omitir)
                Bresenham((int) ((fugura_2D[i][X]*amplificacionX)+disminucionX), (int) ((fugura_2D[i][Y]*amplificacionY)+disminucionY), (int) ((fugura_2D[i+1][X]*amplificacionX)+disminucionX), (int) ((fugura_2D[i+1][Y]*amplificacionY)+disminucionY), c);
        }
        //Este condicional maneja la conexión entre el último punto y el punto inicial
        if(fugura_2D.length-1!=omitir&&nn!=omitir)
            Bresenham((int) ((fugura_2D[fugura_2D.length-1][X]*amplificacionX)+disminucionX), (int) ((fugura_2D[fugura_2D.length-1][Y]*amplificacionY)+disminucionY), (int) ((fugura_2D[nn][X]*amplificacionX)+disminucionX), (int) ((fugura_2D[nn][Y]*amplificacionY)+disminucionY), c);
        //Este último bucle se encarga de conectar los puntos en la parte superior con los puntos correspondientes en la parte inferior del objeto
        for(int i = 0; i<n;i++){
            if(i!=omitir&&i+n!=omitir)
                Bresenham((int) ((fugura_2D[i][X]*amplificacionX)+disminucionX), (int) ((fugura_2D[i][Y]*amplificacionY)+disminucionY), (int) ((fugura_2D[i+n][X]*amplificacionX)+disminucionX), (int) ((fugura_2D[i+n][Y]*amplificacionY)+disminucionY), c);
        }
    }


    public float [][] profundidad_figura(float[] x, float[] y, int z){
        // Se duplica la cantidad de puntos para tener espacio para la representación en 3D
        float[][] figura_en_3d = new float[(x.length)*2][3];
        int n = x.length;
        //// Se llena la representación en 3D con las coordenadas de la figura en 2D
        for(int i = 0; i<x.length; i++){
            figura_en_3d[i][X] = x[i];
            figura_en_3d[i+n][X] = x[i];
            figura_en_3d[i][Y] = y[i];
            figura_en_3d[i+n][Y] = y[i];
            figura_en_3d[i][Z] = 0;
            figura_en_3d[i+n][Z] = z;
        }

        return figura_en_3d;
    }

//Esta función podría ser utilizada para encontrar el centro tridimensional de una figura en 3D
//La función retorna un array 2D que representa seis puntos que forman el centro tridimensional de la figura
    public float [][] centro_tridimensional(float[][] figura_en_3d, float z){
        float n = z/(float)2;
        return new float[][]{
                //Los puntos 0 y 1 son desplazamientos de los puntos originales en la dirección positiva de los ejes X e Y
                //Los puntos 2 y 3 son desplazamientos de los puntos originales en la dirección positiva de los ejes X e Y, así como en la dirección positiva del eje z
                {figura_en_3d[0][X]+n,figura_en_3d[0][Y]+n,figura_en_3d[0][Z]},
                {figura_en_3d[4][X]+n,figura_en_3d[4][Y]+n,figura_en_3d[4][Z]},
                {figura_en_3d[0][X]+n,figura_en_3d[0][Y], figura_en_3d[0][Z]+n},
                {figura_en_3d[3][X]+n,figura_en_3d[3][Y], figura_en_3d[3][Z]+n},
                {figura_en_3d[0][X],figura_en_3d[0][Y]+n, figura_en_3d[0][Z]+n},
                {figura_en_3d[1][X],figura_en_3d[0][Y]+n, figura_en_3d[0][Z]+n},
        };
    }

    public void putPixel(int x, int y, Color c){
        bufferPixel.setRGB(0, 0, c.getRGB());
        imagenCompleta.getGraphics().drawImage(bufferPixel, x, y, this);

    }


    public void Bresenham(int x0, int y0, int x1, int y1, Color color) {
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
        this.putPixel(x0, y0, color);
        int p;
        int incE;
        int incNE;
        if (dx > dy) {
            p = 2 * dy - dx;
            incE = 2 * dy;

            for(incNE = 2 * (dy - dx); x != x1; this.putPixel(x, y, color)) {
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

            for(incNE = 2 * (dx - dy); y != y1; this.putPixel(x, y, color)) {
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

    @Override
    public void run() {
        while(true){
            try{
                repaint();

                cubo1mov[0]+=1;
                cubo1mov[1]+=1;
                cubo2mov[0]+=1;
                cubo2mov[1]-=1;
                cubo3mov[0]-=1;
                cubo3mov[1]+=1;
                cubo4mov[0]-=1;
                cubo4mov[1]-=1;

                System.out.println("x: "+cubo1mov[0]+ ", "+"y: "+cubo1mov[1] );

                hilo.sleep(30);
            }catch(Exception e){
                System.out.println(e);
            }
        }
    }
}