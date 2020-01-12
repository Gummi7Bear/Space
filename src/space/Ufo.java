package space;

public class Ufo extends BaseObject{

    private static int[][] matrix = {
            {0, 0, 1, 0, 0},
            {1, 1, 1, 1, 1},
            {0, 0, 1, 0, 0},
            {0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0},
    };

    public Ufo(double x, double y) {
        super(x, y, 3);
    }

    /**Рисует свой объект в канвасе
     */

    public void draw(Canvas canvas) {
        canvas.drawMatrix(x - radius + 1, y - radius + 1, matrix, 'U');
    }

    /**Движение на 1 ход
     */
    @Override
    public void move() {
        //UFO перемещается по случайной траектории
        double dx = Math.random() * 2 - 1; //-1..1
        double dy = Math.random() * 2 - 1; //-1..1

        x = x + dx;
        y = y + dy;

        //Проверяем столкновение с границами
        //UFO не опускается в нижнюю половину экрана
        checkBorders(radius,Space.game.getWidth() - radius + 1, radius, Space.game.getHeight() - Space.game.getHeight()/2);

        //случайное число от 1 до 100
        int chance = (int) (Math.random() * 100);

        //С вероятностью 10% UFO должен стрелять.
        if(chance <= 10 )
            fire();
    }

    /**Выстрел нло
     * Cоздает одну бомбу по середине под нло
     */
    public void fire(){
        Bomb bomb = new Bomb(getX(), getY()+getRadius());
        Space.game.getBombs().add(bomb);
    }
}
