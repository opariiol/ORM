package launching;

import classes.Client;
import configuration.DataBaseConnection;
import configuration.TableCreatorIntoDB;

import java.sql.*;

public class AnnotationApp {
    public static void main(String[] args) throws Exception {

        DataBaseConnection connector = new DataBaseConnection();
        Connection connection = connector.connect();


        Client client = new Client();
        client.id = 2;
        client.name = "Helga";
        client.orderNumber = 4124126;
        client.price = 1020.44;
        client.surnameClient = "Hello";
        String query =  new TableCreatorIntoDB(connection, Client.class).toSQLString();
        System.out.println(query);
        //connector.createTableInDb(Client.class);
        connector.insertCOlumns(client);

    }
}