package homework.task_02;


import homework.task_02.dao.ClientDAO;
import homework.task_02.dao.DAOFactory;
import homework.task_02.dao.IDAOFactory;
import homework.task_02.entity.Client;

import java.util.List;


public class Main {

    public static void main(String[] args) {

        IDAOFactory factory = DAOFactory.getInstance();


        ClientDAO clientsDao = factory.getClientDAO();

        Client client = new Client();
        client.setName("Тест Клиент");
        client.setAge(20);
        client.setPhone("80934552500");
        clientsDao.addClient(client);

        List<Client> clients = clientsDao.getAllClient();
        for (Client client1 : clients) {
            System.out.println(client1.getId() + " " + client1.getName() + " " + client1.getAge() + " " + client1.getPhone());
        }

        Client client_2 = clientsDao.getById(3);
        System.out.println(client_2.getId() + " " + client_2.getName() + " " + client_2.getAge() + " " + client_2.getPhone());

        clientsDao.updateAge(60,1);

        clientsDao.remove("Тест Клиент");
    }
}
