package homework.task_02.dao;

import homework.task_02.entity.Car;

import java.util.List;


public interface CarDAO {
    
    void add(Car car);
    
    List<Car> getAll();
    
    Car getById(int id);
    
    void updatePrice(int price, int carId);
    
    void remove(String mark);
    
}
