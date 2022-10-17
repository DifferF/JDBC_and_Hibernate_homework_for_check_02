package homework.task_02.dao;


public interface IDAOFactory {

    CarDAO getCarDAO();

    ClientDAO getClientDAO();
}
