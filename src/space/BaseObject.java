package space;

/**Класс родитель для всех движущихся объектов.
 *
 * Для простоты, считать все объекты у нас в космосе круглыми.
 * Отрисовывать их мы будем фигурными.
 * А вот при расчетах их взаимодействия исходить из того, что они круглые.
 */
public abstract class BaseObject {
    /**координаты объектов и радиус
     */
    protected double x, y, radius;

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    /**Жив объект или уже нет?
     */
    protected boolean isAlive;

    public boolean isAlive() {
        return isAlive;
    }

    public BaseObject(double x, double y, double radius) {
        this.x = x;
        this.y = y;
        this.radius = radius;
        isAlive = true;
    }

    /**
     * Проверяем - не выходит ли (x,y) за границы.
     */
    public void checkBorders(double minx, double maxx, double miny, double maxy) {
        if (x < minx) x = minx;
        if (x > maxx) x = maxx;
        if (y < miny) y = miny;
        if (y > maxy) y = maxy;
    }

    /**
     * Метод рисует объект на "канвасе".
     */
    public void draw(Canvas canvas) {
    }

    /**
     * Двигаем на один ход.
     */
    public void move() {
    }

    /**Объект умирает.
     */
    public void die() {
        isAlive = false;
    }

    /**
     * Нужно будет определять попала бомба в корабль или ракета в НЛО
     * "Пересеклись" объекты или нет?
     * @return Если пересеклись - возвращать true, если нет - false
     */
    public boolean isIntersect(BaseObject o) {
        if (Math.sqrt(Math.pow((this.x - o.x), 2.0) + Math.pow((this.y - o.y), 2.0)) < Math.max(this.radius, o.radius)) {
            return true;
        }
        else
            return false;
    }
}
