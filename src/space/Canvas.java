package space;

/**
 * Отрисовка объектов
 */
public class Canvas {
    //ширина и высота матрицы
    private int width, height;

    //матрица
    private char[][] matrix;

    public Canvas(int width, int height) {
        this.width = width;
        this.height = height;
        matrix = new char[height + 2][width + 2];
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public char[][] getMatrix() {
        return matrix;
    }
    /**Ставит точку в координатах x,y цветом c
     */
    public void setPoint(double x, double y, char c) {
        if(x < 0 || y < 0 || y >= matrix.length || x >=matrix[0].length) {
            //ничего не делать
        }
        else
            matrix[(int)Math.round(y)][(int)Math.round(x)] = c;
    }

    /**Копирует переданную ему картинку (матрицу) в матрицу Canvas, начиная с координат x, y
     */
    public void drawMatrix(double x, double y, int[][] matrix, char c) {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                if(matrix[i][j] != 0) {
                    setPoint(x+j, y+i, c);
                }
            }
        }
    }

    /**
     * Будет очищать матрицу, чтобы на ней снова можно было рисовать
     */
    public void clear() {
        this.matrix = new char[height + 2][width + 2];
    }

    /**
     * Отрисовывает матрицу на экран
     */
    public void print() {
        System.out.println();

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                System.out.print(" ");
                System.out.print(matrix[i][j]);
                System.out.print(" ");
            }
            System.out.println();
        }
        System.out.println();
        System.out.println();
        System.out.println();
    }
}


