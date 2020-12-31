import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;

public class Connector {
    public static Connection conn = null;
    public static Statement st2 = null;

    public static ResultSet executeQuery (String sql) {
        ResultSet rs= null;
        try {
            st2 = conn. createStatement (ResultSet. TYPE_SCROLL_SENSITIVE,
                    ResultSet. CONCUR_READ_ONLY);
                    rs= st2.executeQuery (sql);
        }catch(Exception e) {
            e.printStackTrace();
        }//End try
        return rs;
    }

    public static void main(String[] args) {
        try {
            LoadDriver.load();
            // The newInstance() call is a work around for some
            // broken Java implementations
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306" + "/" + ConfigIni.DBName +
                    "?" + "user=" + ConfigIni.passwd + "&password=" + ConfigIni.user + "&serverTimezone=UTC");

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
                System.out.println( "SQLState: " + ex.getSQLState());
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