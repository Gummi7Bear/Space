package space;

public class SpaceShip extends BaseObject{

    //картинка корабля для отрисовки
    private static int[][] matrix = {
            {0, 0, 0, 0, 0},
            {0, 0, 1, 0, 0},
            {0, 0, 1, 0, 0},
            {1, 0, 1, 0, 1},
            {1, 1, 1, 1, 1},
    };

    public SpaceShip(double x, double y)
    {
        super(x, y, 3);
    }

    /**Хранить направление движения корабля
     */
    private double dx;

    /**Движение влево
     */
    public void moveLeft() {
        dx = -1;
    }

    /**Движение вправо
     */
    public void moveRight() {
        dx = 1;
    }

    /**Рисует свой объект в канвасе
     */
    public void draw(Canvas canvas) {
        canvas.drawMatrix(x - radius + 1, y - radius + 1, matrix, 'M');
    }

    /**Движение на 1 ход
     */
    @Override
    public void move() {
        x = x +dx;
        //проверить, не вылез ли корабль за границы космоса
        //ширина корабля равна двум его радиусам
        checkBorders(0+radius, Space.game.getWidth()-radius, 0+radius, Space.game.getHeight()-radius);
    }

    /**Выстрел корабля
     */
    public void fire(){
        //установить им координаты левого края корабля и правого края корабля
        //пушки находятся на расстоянии 2 от центра корабля
        Rocket leftRocket = new Rocket(getX() -2, getY());
        Rocket rightRocket = new Rocket(getX() + 2, getY());

        //добавить эти ракеты в список ракет объекта game
        Space.game.getRockets().add(leftRocket);
        Space.game.getRockets().add(rightRocket);
    }
}
