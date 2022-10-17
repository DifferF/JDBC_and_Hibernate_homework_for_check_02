package homework.task_02.dao;


public class DAOFactory implements IDAOFactory {

    // Фабрику делаем синглтон. Через new мы не сможем создавать объекты.
    // Только в этом классе мы можем создать DAOFactory
    // Фабрика - это то, с помощью чего мы обращаемся к БД.
    private static IDAOFactory factory;

    // При вызове конструктора драйвер запускается один раз,
    // чтобы не плодить повторные запуски, отрабатывает логика внутри метода getInstance
    private DAOFactory() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Driver loading success!");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
// Создаем метод, через который мы будем получать экземпляр данной фабрики.
    // Делаем его синхронизированным на случай, если несколько пользователей будут обращаться к этому методу.
    public static synchronized IDAOFactory getInstance() {
        if (factory == null) {
            // factory создается один раз.
            factory = new DAOFactory();
        }
        return factory;
    }

    // Для машин возвращаем экземпляр класса CarJDBCDao
    @Override
    public CarDAO getCarDAO() {
        return new CarJDBCDao();
    }

    // Для клиентов в нашем примере ничего не возвращаем - это домашнее задание
    @Override
    public ClientDAO getClientDAO() {
        return new ClientJDBCDao();
    }
}
