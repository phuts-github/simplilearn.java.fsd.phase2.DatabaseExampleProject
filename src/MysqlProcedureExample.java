import java.sql.*;

public class MysqlProcedureExample {
    public static void main(String[] args) {
        System.out.println("1");
        try {
            // 1. Register Connection
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("2");

            // 2. Get Connection
            Connection connection = DriverManager.getConnection(
//                    "jdbc:mysql://127.0.0.1:3306/employee?serverTimezone=GMT",
                    "jdbc:mysql://localhost:3306/employee?serverTimezone=GMT",
                    "root",
                    "97257"
            );
            System.out.println("3");

            // 3. Create Statement
            CallableStatement callStmt = connection.prepareCall("{call GET_EMPLOYEE_DETAILS()}");
            System.out.println("4");

            // 4. Execute Query
            ResultSet resultSet = callStmt.executeQuery();
            System.out.println("5");
            while (resultSet.next()) {
                System.out.println(resultSet.getString("NAME") + " , " +
                        resultSet.getString("PHONENUMBER") + " , " +
                        resultSet.getString("ADDRESS") + " , " +
                        resultSet.getString("SALARY"));
            }

            System.out.println("6");
            //update return int value
            int resultUpdateCount=0;
            resultUpdateCount = callStmt.executeUpdate(
                    "update employee_details " +
                            "set name='name1' " +
                            "where name='JOHN'"
            );
            System.out.println("resultUpdateCount " + resultUpdateCount);

            // 5. Close connection
            System.out.println("7");
            connection.close();

        } catch (Exception e){
            System.out.println("8e");
            System.out.println(e);

        };
        System.out.println("9");
    }
}
