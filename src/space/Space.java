package space;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Хранит объекты и управляет их взаимодействием
 */
public class Space {
    private int width;
    private int height;

    private SpaceShip ship;

    private List<Ufo> ufos = new ArrayList<>();
    private List<Bomb> bombs = new ArrayList<>();
    private List<Rocket> rockets = new ArrayList<>();

    public Space(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public List<Ufo> getUfos() {
        return ufos;
    }

    public SpaceShip getShip() {
        return ship;
    }

    public List<Rocket> getRockets() {
        return rockets;
    }

    public void setShip(SpaceShip ship) {
        this.ship = ship;
    }

    public List<Bomb> getBombs() {
        return bombs;
    }


    /**Наша игра
     */
    public static Space game;

    public static void main(String[] args) {
        game = new Space(20, 20);
        game.setShip(new SpaceShip(10, 18));
        game.run();
    }

    /**Управляет всей логикой игры
     */
    public void run() {
        //Создаем холст для отрисовки.
        Canvas canvas = new Canvas(width, height);

        //Создаем объект "наблюдатель за клавиатурой" и стартуем его.
        KeyboardObserver keyboardObserver = new KeyboardObserver();
        keyboardObserver.start();

        //Игра работает, пока корабль жив
        while (ship.isAlive()) {
            //"наблюдатель" содержит события о нажатии клавиш?
            if (keyboardObserver.hasKeyEvents()) {
                KeyEvent event = keyboardObserver.getEventFromTop();
                //Если "стрелка влево" - сдвинуть фигурку влево
                System.out.print(event.getKeyCode());
                if (event.getKeyCode() == KeyEvent.VK_LEFT)
                    ship.moveLeft();
                    //Если "стрелка вправо" - сдвинуть фигурку вправо
                else if (event.getKeyCode() == KeyEvent.VK_RIGHT)
                    ship.moveRight();
                    //Если "пробел" - стреляем
                else if (event.getKeyCode() == KeyEvent.VK_SPACE)
                    ship.fire();
            }

            //двигаем все объекты игры
            moveAllItems();

            //проверяем столкновения
            checkBombs();
            checkRockets();
            //удаляем умершие объекты из списков
            removeDead();

            //Создаем НЛО (1 раз в 10 ходов)
            createUfo();

            //Отрисовываем все объекты на холст, а холст выводим на экран
            canvas.clear();
            draw(canvas);
            canvas.print();

            //Пауза 300 миллисекунд
            Space.sleep(300);
        }

        //Выводим сообщение "Game Over"
        System.out.println("Game Over!");
    }

    /**Отрисовка всех объектов игры:
     * а) заполняем весь холст точками.
     * б) отрисовываем все объекты на холст.
     */
    public void draw(Canvas canvas) {
        for (int i = 0; i < width + 2; i++) {
            for (int j = 0; j < height + 2; j++) {
                canvas.setPoint(i, j, '.');
            }
        }

        for (int i = 0; i < width + 2; i++) {
            canvas.setPoint(i, 0, '-');
            canvas.setPoint(i, height + 1, '-');
        }

        for (int i = 0; i < height + 2; i++) {
            canvas.setPoint(0, i, '|');
            canvas.setPoint(width + 1, i, '|');
        }

        for (BaseObject object : getAllItems()) {
            object.draw(canvas);
        }
    }

    /**Метод делает паузу длинной delay миллисекунд.
     */
    public static void sleep(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
        }
    }

    /**Двигаем все объекты игры
     */
    public void moveAllItems() {
        //нужно получить список всех игрвых объектов и у каждого вызвать метод move().
        List<BaseObject> allItems = getAllItems();
        for (BaseObject item: allItems) {
            item.move();
        }
    }

    /**Метод возвращает общий список, который содержит все объекты игры
     */
    public List<BaseObject> getAllItems() {
        //нужно создать новый список и положить в него все игровые объекты.
        List<BaseObject> allItems = new ArrayList<>();
        allItems.addAll(getBombs());
        allItems.addAll(getRockets());
        allItems.add(getShip());
        allItems.addAll(getUfos());
        return allItems;
    }

    /**Создаем новый НЛО. 1 раз на 10 вызовов.
     */
    public void createUfo() {
        //Если список НЛО пуст - создать один корабль в центре сверху
        if(getUfos().isEmpty()) {
            getUfos().add(new Ufo(width/2, 0));
        }
    }

    /**Проверяем бомбы.
     * а) столкновение с кораблем (бомба и корабль умирают)
     * б) падение ниже края игрового поля (бомба умирает)
     */
    public void checkBombs() {
        for(Bomb bomb : getBombs()) {
            if(bomb.isIntersect(ship)) {
                bomb.die();
                ship.die();
            }

            if(bomb.getY()> height) {
                bomb.die();
            }
        }
    }

    /**Проверяем рокеты.
     * а) столкновение с НЛО (ракета и НЛО умирают)
     * б) вылет выше края игрового поля (ракета умирает)
     */
    public void checkRockets() {
        for (Rocket rocket : getRockets()) {
            for(Ufo ufo : getUfos()) {
                if (rocket.isIntersect(ufo)) {
                    rocket.die();
                    ufo.die();
                    break;
                }
            }
            if(rocket.getY() < 0)
                rocket.die();
        }
    }

    /**Удаляем умершие объекты (бомбы, ракеты, НЛО) из списков
     */
    public void removeDead() {
        Iterator<Ufo> iterUfo = getUfos().iterator();
        while (iterUfo.hasNext()) {
            Ufo ufo = iterUfo.next();

            if (!ufo.isAlive()) {
                iterUfo.remove();
            }
        }

        Iterator<Rocket> iterRocket = getRockets().iterator();
        while (iterRocket.hasNext()) {
            Rocket rocket = iterRocket.next();

            if (!rocket.isAlive()) {
                iterRocket.remove();
            }
        }

        Iterator<Bomb> iterBomb = getBombs().iterator();
        while (iterBomb.hasNext()) {
            Bomb bomb = iterBomb.next();

            if (!bomb.isAlive()) {
                iterBomb.remove();
            }
        }
    }

}
