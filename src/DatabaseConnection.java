import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseConnection {
    private static final String url="jdbc:mysql://localhost:3306/StudentManagement";
    private static final String user="root";
    private static final String pass="";
    public static Connection getConnection(){
        Connection connection=null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection= DriverManager.getConnection(url,user,pass);

        }catch (Exception e){
            System.out.println(e);
        }
        return connection;
    }
}
