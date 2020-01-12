package space;

public class Rocket extends BaseObject{
    public Rocket(double x, double y) {
        super(x, y, 1);
    }

    /**
     * Ракета летит вверх
     */
    @Override
    public void move() {
        y--;
    }

    /**
     * Ставить точку с координатами (x,y) и "цветом" R
     */
    public void draw(Canvas canvas) {
        canvas.setPoint(x,y,'R');
    }
}