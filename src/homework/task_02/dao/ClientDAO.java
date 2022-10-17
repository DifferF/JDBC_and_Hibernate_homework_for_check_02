package homework.task_02.dao;

import homework.task_02.entity.Client;

import java.util.List;

public interface ClientDAO {

    void addClient(Client client);

    List<Client > getAllClient ();

    Client getById(int id);

     void updateAge(int age, int id);

     void remove(String name);

}
