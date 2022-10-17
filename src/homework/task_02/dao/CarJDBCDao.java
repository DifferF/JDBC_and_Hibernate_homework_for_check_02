package homework.task_02.dao;

import homework.task_02.entity.Car;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Asus on 31.01.2018.
 */
public class CarJDBCDao implements CarDAO {

    // Реализуем метод добавления в БД.
    @Override
    public void add(Car car) {
        Connection connection = null;

        // Создаем соединение и готовим выражение.
        connection = getConnection();
        PreparedStatement statement;

        try {
            // По условию метода в случае отсутствия данных о марке вернет -1
            int markId = getMarkId(car.getMark(), connection);
            // Если результат - отсутствие марки, то мы ее можем добавить в таблицу
            if (markId == -1) {
                statement = connection.prepareStatement("INSERT INTO marks(mark) VALUES (?)");
                // Вытягиваем из объекта марку и записываем ее в таблицу.
                statement.setString(1, car.getMark());
                statement.execute();
                //Т.к. нам нужно знать номер id, мы вытаскиваем, то, что у нас максимального есть в таблице SQL
                statement = connection.prepareStatement("SELECT MAX(id) FROM marks");
                ResultSet rs = statement.executeQuery();
                // Передвигаем курсор на следующую позцицию,
                // которая получится после выполнения добавления марки (добавится следующий id)
                rs.next();
                // Считываем данный id, мы его используем для вставки данных в таблицу с автомобилями, как внешний ключ.
                markId = rs.getInt(1);
            }
            // После этого мы добавляем в табицу cars значения марки, модели, цены. Это мы подготовили запрос, но еше не выполнили.
            // Нам нужно будет заполнить данные вместо знаков ? и дать команду execute
            statement = connection.prepareStatement("INSERT INTO cars(mark_id, model, price) VALUES (?, ?, ?)");

            // Прописываем выражения для вставки в таблицу cars
            statement.setInt(1, markId);
            statement.setString(2, car.getModel());
            statement.setInt(3, car.getPrice());

            statement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private int getMarkId(String markName, Connection connection) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT id FROM marks WHERE mark = ? ");
            preparedStatement.setString(1, markName);

            ResultSet rs = preparedStatement.executeQuery();
            // Проверяем на получение результата. Он должен быть один, тк. мы ищем марку по id
            // Если результат будет ложь (т.е. не будет результата), то сразу вернет -1
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return -1;
    }

    @Override
    public List<Car> getAll() {
        // Создаем промежуточный список куда будут добавляться результаты.
        List<Car> allCars = new ArrayList<>();
        // Объявляем соединение и готовим выражение
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = getConnection();
            // Выбираем набор полей, используя join
            statement = connection.prepareStatement("SELECT c.id, m.mark, c.model, c.price FROM cars as c " +
                    "INNER JOIN marks as m ON m.id = c.mark_id ");

            ResultSet rs = statement.executeQuery();
// Забираем из результирующего набора получившиеся записи. Колонки это порядковые номера нашего запроса.
            while (rs.next()) {
                long id = rs.getLong(1);
                String mark = rs.getString(2);
                String model = rs.getString(3);
                int price = rs.getInt(4);
                // Создаем объекты с выгруженными данными и добавляем в коллекцию.
                Car car = new Car();
                car.setId(id);
                car.setMark(mark);
                car.setModel(model);
                car.setPrice(price);
                allCars.add(car);
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
        // Если не возникает исключительных ситуаций, то список возвращается в качестве результата.
        return allCars;
    }

    @Override
    public Car getById(int id) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        connection = getConnection();
        try {
            // Создаем запрос на получение авто по айди
            preparedStatement = connection.prepareStatement("SELECT m.mark, c.model, c.price FROM cars as c " +
                    "INNER JOIN marks as m ON m.id = c.mark_id WHERE c.id = ? ");

            // выполняем выражение передавая  id из параметра метода
            preparedStatement.setInt(1, id);

            ResultSet rs = preparedStatement.executeQuery();
            //  значения считываем  в объект
            if (rs.next()) {
                String mark = rs.getString(1);
                String model = rs.getString(2);
                int price = rs.getInt(3);
                Car car = new Car();
                car.setId(id);
                car.setModel(model);
                car.setMark(mark);
                car.setPrice(price);
                return car;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null && preparedStatement != null) {

                try {
                    connection.close();
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return null;
    }

    @Override
    public void updatePrice(int price, int carId) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        connection = getConnection();

        try {
            preparedStatement = connection.prepareStatement("UPDATE cars SET price = ? WHERE id = ?");
            // Передаем вместо знаков ? значения из входных параметров: в первое - цену, во второй знак ? - айди авто
            preparedStatement.setInt(1, price);
            preparedStatement.setInt(2, carId);

            // Записываем, сколько значений обновлено
            int updatedValues = preparedStatement.executeUpdate();

            System.out.println("Values updated: " + updatedValues);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null && preparedStatement != null) {

                try {
                    connection.close();
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    @Override
    public void remove(String mark) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        connection = getConnection();

        try {
            // Для начала мы получаем id марки, используя метод из данного класса
            // Даже, если такого значения не будет и метод вернет нам -1, то ничего страшного - с таким id записей нет априори
            int markId = getMarkId(mark, connection);
            preparedStatement = connection.prepareStatement("DELETE FROM cars WHERE mark_id = ? ");
            // Подставляем в наш запрос полученное значение id марки
            preparedStatement.setInt(1, markId);

            // Получаем количество удаленных записей
            int deletedValues = preparedStatement.executeUpdate();

            System.out.println("Values deleted: " + deletedValues);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null && preparedStatement != null) {

                try {
                    connection.close();
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    // Чтобы мы не обращались постоянно к connection, используем метод соединения.
    // В случае ошибки будет возвращаться нулл.
    private Connection getConnection() {
        Connection connection = null;

        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/carsshop?useSSL=false", "root", "root");
            return connection;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

}
