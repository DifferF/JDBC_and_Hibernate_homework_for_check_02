package homework.task_02.entity;

/**
 * Created by Asus on 31.01.2018.
 */
//Создаем сущность, т.е. класс, который будет по образу и подобию таблицы cars из БД.
public class Car {

    private long id;
    private String mark;
    private String model;
    private int price;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

}
