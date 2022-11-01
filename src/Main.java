import main.model.Cuenta;

import java.sql.*;

public class Main {

    private static final String SQL_CREATE_TABLE = "DROP TABLE IF EXISTS CUENTA; CREATE TABLE CUENTA "
            +"("
            +"ID INT PRIMARY KEY, "
            +"NOMBRE varchar(100) NOT NULL, "
            +"NRO_CUENTA NUMERIC(10, 2) NOT NULL, "
            +"SALDO INT NOT NULL"
            +")";

            private static final String SQL_INSERT = "INSERT INTO CUENTA (ID, NOMBRE, NRO_CUENTA, SALDO) VALUES (?,?,?,?)";
            private static final String SQL_UPDATE = "UPDATE CUENTA SET SALDO=? WHERE ID=?";

    public static void main(String[] args) throws Exception {
        Cuenta cuenta = new Cuenta(123475, "Corriente", 1000);

        Connection connection = null;

        try{
            connection = getConnection();
            Statement stmt = connection.createStatement();
            stmt.execute(SQL_CREATE_TABLE);

            PreparedStatement preparedStatementInsert = connection.prepareStatement(SQL_INSERT);
            preparedStatementInsert.setInt(1, 1);
            preparedStatementInsert.setString(2, cuenta.getNombre());
            preparedStatementInsert.setInt(3, cuenta.getNroCuenta());
            preparedStatementInsert.setInt(4, cuenta.getSaldo());

            preparedStatementInsert.execute();

            /*----------AutoCommit-----------*/

            connection.setAutoCommit(false);

            PreparedStatement psUpdate = connection.prepareStatement(SQL_UPDATE);
            psUpdate.setInt(1, cuenta.getSaldo() + 750);
            psUpdate.setInt(2, 1);

            psUpdate.execute();

            connection.commit();
            connection.setAutoCommit(true);


            System.out.println("PRIMER CONEXIÓN");
            String select = "SELECT * FROM CUENTA";
            Statement stm = connection.createStatement();
            ResultSet rs = stm.executeQuery(select);
            while (rs.next()) {
                System.out.println(rs.getInt(1) + " - " + rs.getString(2) + " - " + rs.getInt(3) + " - " + rs.getString(4));
            }

        } catch (Exception e) {
            e.printStackTrace();
            connection.rollback();
        } finally {
            connection.close();
        }

        System.out.println("SEGUNDA CONEXIÓN");
        Connection connection2 = getConnection();
        String sql = "SELECT * FROM CUENTA";
        Statement sqlStm = connection2.createStatement();
        ResultSet rs = sqlStm.executeQuery(sql);
        while (rs.next()) {
            System.out.println(rs.getInt(1) + " - " + rs.getString(2) + " - " + rs.getInt(3) + " - " + rs.getString(4));
        }
    }

    private static Connection getConnection() throws Exception {
        Class.forName("org.h2.Driver").getDeclaredConstructor().newInstance();
        return DriverManager.getConnection("jdbc:h2:~/db-cuentas-demo", "user", "pass");
    }

}