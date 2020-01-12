package space;

public class Bomb extends BaseObject{

    public Bomb(double x, double y) {
        super(x, y, 1);
    }

    /**
     * Бомба падает вниз
     */
    @Override
    public void move() {
        y++;
    }

    /**
     * Ставить точку с координатами (x,y) и "цветом" B
     */
    public void draw(Canvas canvas) {
        canvas.setPoint(x,y,'B');
    }
}
