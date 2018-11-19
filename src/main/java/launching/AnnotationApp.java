package launching;

import configuration.DataBaseConnection;

import java.io.IOException;
import java.sql.*;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Executor;

public class AnnotationApp {
    public static void main(String[] args) throws IOException, SQLException {
        DataBaseConnection.getConnect().close();
        }
    }

