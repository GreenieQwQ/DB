import java.sql.*;
import javax. swing. JComboBox;

public class Connector {
    public static Connection conn = null;
    public static Statement stmt = null;
    public static ResultSet rs = null;

    private static void handleSQLException(SQLException ex)
    {
        System.out.println("SQLException: " + ex.getMessage());
        System.out.println( "SQLState: " + ex.getSQLState());
        System.out.println("VendorError: " + ex.getErrorCode());
    }


    // 执行查询SQL命令，返回记录集对象函数
    public static ResultSet executeQuery (PreparedStatement stmt) {
        try {
            rs= stmt.executeQuery();
        }catch(SQLException ex) {
            handleSQLException(ex);
        }
        // 注意：free statement 会也会close result
        return rs;
    }

    // TODO: 这个executeQuery 为什么要这样封装？
    // 执行查询SQL命令，返回记录集对象函数
    public static ResultSet executeQuery (String sql) {
        try {
            stmt = conn.createStatement (ResultSet. TYPE_SCROLL_SENSITIVE,
                    ResultSet. CONCUR_READ_ONLY);
                    rs= stmt.executeQuery (sql);
        }catch(Exception e) {
            e.printStackTrace();
        }
        // 注意：free statement 会也会close result
        return rs;
    }

    // TODO: SQL防注入
    // 执行更新类SQL命令的函数
    public static int executeUpdate (String sql) {
        int i= 0;
        try {
            stmt = conn.createStatement (ResultSet. TYPE_SCROLL_SENSITIVE,
            ResultSet. CONCUR_READ_ONLY);
            i = stmt.executeUpdate (sql);
            conn.commit ();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        finally {
            free_Stmt(stmt);
        }
        return i;
    }

    // 执行查询SQL命令，返回是否成功的函数
    public static boolean query (String sqlString) {
        try {
            rs = stmt.executeQuery(sqlString);
        }catch(Exception Ex) {
            System.out.println ("sql exception: " + Ex);
            return false;
        }
        finally{
            free_Res_Stmt(rs, stmt);
        }
        return true;
    }

    // 执行更新类SQL命令，返回是否成功的函数
    public static boolean executeSQL (String sqlString) {
        boolean executeFlag;
        try {
            stmt.execute(sqlString);
            executeFlag = true;
        } catch (Exception e) {
            executeFlag = false;
            System.out.println("sql exception: " + e.getMessage());
        }
        return executeFlag;
    }

    //执行SQL查询命令，初始化到组合框的函数
    public static void initJComboBox (JComboBox jComboBox, String sqlCode) {
        jComboBox.removeAllItems();
        try {
            ResultSet rs = executeQuery(sqlCode);
            // TODO: 将元数据如何换行加入JcomboBox
            // 打印列名（元数据）
            ResultSetMetaData rsmd = rs.getMetaData();
            for(int i = 1; i <= rsmd.getColumnCount(); i++) {
                System.out.println(rsmd.getColumnName(i));
                System.out.println(rsmd.getColumnTypeName(i));
            }
            int row = getRowCnt(rs);
            // 从结果集中取出工tem加入JComboBox中
            // TODO: 如何换行加入？ 将全部数据加入
            if (row != 0) rs.beforeFirst();
            for (int i = 0; i < row; i++) {
                rs.next();
                jComboBox.addItem(rs.getString(1));
            }
            jComboBox.addItem("");
            // TODO: 如何测试？
        }
        catch (Exception ex) {
            System.out.println("sunsql.initJComboBox(): false");
        }
    }

    public static boolean initialize() {
        if (conn != null) return true;
        try {
            LoadDriver.load();
            // The newInstance() call is a work around for some
            // broken Java implementations
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306" + "/" + ConfigIni.DBName +
                    "?" + "user=" + ConfigIni.user + "&password=" + ConfigIni.passwd + "&serverTimezone=UTC");
        }catch (SQLException ex) {
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
            return false;
        }
        return true;
    }

    public static void close() {
        conn = null;
        free_Res_Stmt(rs, stmt);
    }

    public static void main(String[] args) {
        initialize();
        // Do something with the Connection
        rs = executeQuery("SELECT * FROM course");
        try {
            ResultSetMetaData rsmd = rs.getMetaData();
            for(int i = 1; i <= rsmd.getColumnCount(); i++) {
                System.out.println(rsmd.getColumnName(i));
                System.out.println(rsmd.getColumnTypeName(i));
            }
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
            handleSQLException(ex);
        }
        finally{
            free_Res(rs);
        }

    }

    private static void free_Res(ResultSet rs)
    {
        // it is a good idea to release
        // resources in a finally{} block
        // in reverse-order of their creation
        // if they are no-longer needed
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException sqlEx)
            {
                handleSQLException(sqlEx);
            } // ignore

            rs = null;
        }
    }

    public static void free_Stmt(Statement stmt)
    {
        // it is a good idea to release
        // resources in a finally{} block
        // in reverse-order of their creation
        // if they are no-longer needed
        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException sqlEx)
            {
                handleSQLException(sqlEx);
            } // ignore

            stmt = null;
        }
    }

    public static void free_Res_Stmt(ResultSet rs, Statement stmt)
    {
        // it is a good idea to release
        // resources in a finally{} block
        // in reverse-order of their creation
        // if they are no-longer needed
        free_Res(rs);
        free_Stmt(stmt);
    }

    public static int getRowCnt(ResultSet rs)
    {
        int rowCount = -1;
        try {
            rs.last(); //移到最后一行
            rowCount = rs.getRow(); //得到当前行号，也就是记录数
            rs.beforeFirst(); //如果还要用结果集，就把指针再移到初始化的位置
        }
        catch (SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return rowCount;
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