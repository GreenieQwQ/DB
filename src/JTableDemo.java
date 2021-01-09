
import java.awt.*;
import java.awt.event.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;


public class JTableDemo extends JFrame
{
    JTable table;
    JPanel con;
    JScrollPane scrollPane;
    String dbName = "";

    public static void main(String[] args) {
        Connector.initialize();
        JTableDemo frame = new JTableDemo();
        frame.setVisible(true);
    }
    public JTableDemo(){
        //设为共同的，继承下来也会执行的构造函数
        table = new JTable();
        table.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);

        setBounds(100,100,1000,700);
        con=new JPanel();
        con.setBorder(new EmptyBorder(5,5,5,5));
        con.setLayout(new BorderLayout(0,0));
        setContentPane(con);

        scrollPane=new JScrollPane();
        con.add(scrollPane, BorderLayout.EAST);
        scrollPane.setViewportView(table);
    }
    // 四个功能都封装为接受一条sql语句，然后更新表格
    // 需要修改数据的语句还会根据自己的tag调用一次findInfo()
    public void findInfo(String sql) throws SQLException {
        if(!Connector.initialize()) {
            System.out.println("连接不上数据库...");
            new JOptionPane().showMessageDialog(null,
                    "连接不上数据库...",
                    "",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }  // 若连接失败返回
        System.out.println("Looking for data...");

        DefaultTableModel tableModel= (DefaultTableModel)table.getModel();    //获得表格模型

        ResultSetMetaData rsmd;
        ResultSet rs;
        // 执行
        try {
            PreparedStatement stmt = Connector.conn.prepareStatement(sql);
            rs = Connector.executeQuery(stmt);
            rsmd = rs.getMetaData();
        }
        catch (SQLException ex){
            return;
        }
        //更新表格
        Object[] title = {};
        tableModel.setRowCount(0);    //清空表格中的数据
        for(int i = 1; i <= rsmd.getColumnCount(); i++) {
            title = appendValue(title, rsmd.getColumnName(i));
        }
        while(rs.next()) {
            Object[] row = {};
            for(int i = 1; i <= rsmd.getColumnCount(); i++) {
                row = appendValue(row, rs.getString(i));
            }
            tableModel.addRow(row);
        }
        tableModel.setColumnIdentifiers(title);
        table.setRowHeight(30);
        table.setModel(tableModel);    //应用表格模型

        System.out.println("Looking for data complete...");
    }
    public void insertInfo(String sql) throws SQLException{
        if(!Connector.initialize()) {
            System.out.println("连接不上数据库...");
            new JOptionPane().showMessageDialog(null,
                    "连接不上数据库...",
                    "",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }  // 若连接失败返回
        System.out.println("Inserting data...");
        PreparedStatement stmt = Connector.conn.prepareStatement(sql);
        try {
            Integer rs = Connector.executeUpdate(sql);
            if (rs == 0) {
                System.out.println("Insertion failed!");
                return;
            }
            System.out.println("Insertion succeed!");
            sql = "select * from " + dbName;
            findInfo(sql);
        }
        catch (SQLException ex){
        }
    }
    public void deleteInfo(String sql) throws SQLException{
        if(!Connector.initialize()) {
            System.out.println("连接不上数据库...");
            new JOptionPane().showMessageDialog(null,
                    "连接不上数据库...",
                    "",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }  // 若连接失败返回
        System.out.println("Deleting data...");
        PreparedStatement stmt = Connector.conn.prepareStatement(sql);
        Integer rs = Connector.executeUpdate(sql);

        if (rs == 0) {
            System.out.println("Deletion failed!");
            return;
        }
        System.out.println("Deletion succeed!");
        sql = "select * from " + dbName;
        findInfo(sql);
    }
    public void modifyInfo(String sql) throws SQLException{
        if(!Connector.initialize()) {
            System.out.println("连接不上数据库...");
            new JOptionPane().showMessageDialog(null,
                    "连接不上数据库...",
                    "",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }  // 若连接失败返回
        System.out.println("Deleting data...");
        PreparedStatement stmt = Connector.conn.prepareStatement(sql);
        Integer rs = Connector.executeUpdate(sql);

        if (rs == 0) {
            System.out.println("Modification failed!");
            return;
        }
        System.out.println("Modification succeed!");
        sql = "select * from " + dbName;
        findInfo(sql);
    }
    public Object[] appendValue(Object[] obj, Object newObj) {
        ArrayList<Object> temp = new ArrayList<Object>(Arrays.asList(obj));
        temp.add(newObj);
        return temp.toArray();
    }
}