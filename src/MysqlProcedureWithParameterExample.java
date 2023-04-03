import java.lang.reflect.Type;
import java.sql.*;

public class MysqlProcedureWithParameterExample {
    public static void main(String[] args) {
        System.out.println("1");
        try {
            // 1. Register Connection
//            Class.forName("com.mysql.jdbc.Driver");  -- DEPRECATED
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
            CallableStatement callStmt1 = connection.prepareCall("{call GET_EMPLOYEE_DETAILS()}");

            // note - '?' is for parameter that is going to be passed
            CallableStatement callStmt2 = connection.prepareCall("{call GET_EMPLOYEE_DETAILS_BY_NAME_LIST(?)}");
            callStmt2.setString(1,"JOH,JOHA");

            CallableStatement callStmt3 = connection.prepareCall("{call GET_MAX_SALARY(?)}");
            callStmt3.registerOutParameter(1, Types.INTEGER);

            CallableStatement callStmt4 = connection.prepareCall("{call GET_EMPLOYEE_SALARY_BY_NAME(?)}");
            callStmt4.setString(1,"JOH");
            callStmt4.registerOutParameter(1, Types.INTEGER);

            System.out.println("4");

            // 4. Execute Query
            ResultSet resultSet1 = callStmt1.executeQuery();
            ResultSet resultSet2 = callStmt2.executeQuery();
            ResultSet resultSet3 = callStmt3.executeQuery();
//            ResultSet resultSet4 = callStmt4.executeQuery();
            callStmt4.executeQuery();

            System.out.println("5");
            System.out.println("\nResult set of GET_EMPLOYEE_DETAILS");

            while (resultSet1.next()) {
                System.out.println(resultSet1.getString("NAME") + " , " +
                        resultSet1.getString("PHONENUMBER") + " , " +
                        resultSet1.getString("ADDRESS") + " , " +
                        resultSet1.getString("SALARY"));
            }
            System.out.println("\nResult set of GET_EMPLOYEE_DETAILS_BY_NAME_LIST");
            while (resultSet2.next()) {
                System.out.println(resultSet2.getString("NAME") + " , " +
                        resultSet2.getString("PHONENUMBER") + " , " +
                        resultSet2.getString("ADDRESS") + " , " +
                        resultSet2.getString("SALARY"));
            }

            System.out.println("\nResult set of GET_MAX_SALARY");
            while (resultSet3.next()) {
                System.out.println(resultSet3.getInt(1));
            }

            System.out.println("\nResult set of GET_EMPLOYEE_SALARY_BY_NAME");
            System.out.println(callStmt4.getInt(1));

            System.out.println("6");

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
