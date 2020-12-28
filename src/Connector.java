import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;


public class Connector {
    public static void main(String[] args) {
        String DBName = "jxgl";
        try {
            LoadDriver.load();
            // The newInstance() call is a work around for some
            // broken Java implementations
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306" + "/" + DBName +
                    "?" + "user=root&password=None" + "&serverTimezone=UTC");

            // Do something with the Connection
            Statement stmt = null;
            ResultSet rs = null;

            try {
                stmt = conn.createStatement();
                rs = stmt.executeQuery("SELECT * FROM course");

                // or alternatively, if you don't know ahead of time that
                // the query will be a SELECT...
                while(rs.next()){
                    String pass = rs.getString(2) ; // 此方法比较高效
                    System.out.println(pass);
                }
//                if (stmt.execute("SELECT foo FROM sc")) {
//                    rs = stmt.getResultSet();
//                }

                // Now do something with the ResultSet ....

            }
            catch (SQLException ex){
                // handle any errors
                System.out.println("SQLException: " + ex.getMessage());
                System.out.println("SQLState: " + ex.getSQLState());
                System.out.println("VendorError: " + ex.getErrorCode());
            }
            finally {
                // it is a good idea to release
                // resources in a finally{} block
                // in reverse-order of their creation
                // if they are no-longer needed

                if (rs != null) {
                    try {
                        rs.close();
                    } catch (SQLException sqlEx) { } // ignore

                    rs = null;
                }

                if (stmt != null) {
                    try {
                        stmt.close();
                    } catch (SQLException sqlEx) { } // ignore

                    stmt = null;
                }
            }
        } catch (SQLException ex) {
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
    }
}

class LoadDriver {
    public static void load() {
        try {
            // The newInstance() call is a work around for some
            // broken Java implementations

            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (Exception ex) {
            // handle the error
        }
    }
}