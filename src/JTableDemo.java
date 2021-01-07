
import java.awt.*;
import java.awt.event.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

public class JTableDemo extends JFrame
{
    JTable table;
    JPanel con;

    public static void main(String[] agrs) {
        JTableDemo frame = new JTableDemo();
        frame.setVisible(true);
    }
    public JTableDemo(){

        JFrame frame=new JFrame("学生成绩表");
        frame.setSize(500,700);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container contentPane=frame.getContentPane();
        Object[][] tableDate=new Object[5][8];
        for(int i=0;i<5;i++)
        {
            tableDate[i][0]="1000"+i;
            for(int j=1;j<8;j++)
            {
                tableDate[i][j]=0;
            }
        }
        String[] name={"学号","软件工程","Java","网络","数据结构","数据库","总成绩","平均成绩"};
        table=new JTable(new DefaultTableModel(tableDate,name));
        //table = new JTable();
        table.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        JLabel lb = new JLabel("部门信息查询", SwingConstants.CENTER);
        Font f = new Font("新宋体", Font.PLAIN, 40);
        Font f1 = new Font("新宋体", Font.PLAIN, 12);
        lb.setFont(f);
        // p1是左边，p2是右边
        JPanel p1=new JPanel();
        JPanel p2=new JPanel();
        JPanel p3=new JPanel();
        JPanel p4=new JPanel();
        JPanel p5=new JPanel();
        p5.setLayout(new GridLayout(6,1,5,5));
        Box bV=Box.createVerticalBox();
        Box bH1=Box.createHorizontalBox();
        Box bH2=Box.createHorizontalBox();
        Box bH3=Box.createHorizontalBox();

        JLabel lbH1 = new JLabel("id： ");
        JLabel lbH2 = new JLabel("sex： ");
        JLabel lbH3 = new JLabel("name： ");
        lbH1.setFont(f1);
        lbH2.setFont(f1);
        lbH3.setFont(f1);
        JTextField idText = new JTextField(20);
        JTextField sexText = new JTextField(20);
        JTextField nameText = new JTextField(20);

        JButton buttonFind = new JButton("查询");
        JButton buttonCount = new JButton("插入");

        buttonFind.addMouseListener(new MouseAdapter() {
            public void mouseClicked (MouseEvent me) {
                String id = idText.getText();
                String sex = sexText.getText();
                String name = nameText.getText();
                String[] stringArr= {id, sex, name};
                // 在StringArr记得处理空值
                int flag = 0;
                String temp = "SELECT * FROM Worker ";
                if (!id.equals("")) {
                    //new JOptionPane().showMessageDialog(null, "用户名不能为空！");
                    if (flag == 0)
                        temp += " WHERE ";
                    temp += "id=\""+ id + "\"";
                    flag += 1;
                }
                if (!sex.equals("")) {
                    if (flag == 0)
                        temp += " WHERE ";
                    if (flag > 0)
                        temp += " AND ";
                    temp += "sex=\""+ sex+"\"";
                    flag += 1;
                }
                if (!name.equals("")) {
                    if (flag == 0)
                        temp += " WHERE ";
                    if (flag > 0)
                        temp += " AND ";
                    temp += "name=\""+ name+"\"";
                    flag += 1;
                }
                try {
                    System.out.println(temp);
                    findInfo(stringArr, temp);
                }
                catch (SQLException ex){

                }
            }
        });
        buttonCount.addMouseListener(new MouseAdapter() {
            public void mouseClicked (MouseEvent me) {
                insertInfo();
            }
        });
//        bH1.add(Box.createVerticalStrut(20));
//        bH2.add(Box.createVerticalStrut(20));
//        bH1.add(lbH1);
//        bH1.add(noText);
//        bH2.add(lbH2);
//        bH2.add(nameText);
//        bH3.add(buttonFind);
//        bH3.add(buttonCount);

        bH1.add(Box.createVerticalStrut(20));
        bH2.add(Box.createVerticalStrut(20));
        p1.add(lbH1);
        p1.add(idText);
        p2.add(lbH2);
        p2.add(sexText);
        p3.add(lbH3);
        p3.add(nameText);

        p4.add(buttonFind);
        p4.add(buttonCount);

        p5.add(p1);
        p5.add(p2);
        p5.add(p3);
        p5.add(p4);
//        bV.add(bH1);
//        bV.add(bH2);
//        bV.add(bH3);

        //contentPane.add(bV, BorderLayout.WEST);

        //con.add(scrollPane, BorderLayout.EAST);
        setBounds(100,100,1300,700);
        con=new JPanel();
        con.setBorder(new EmptyBorder(5,5,5,5));
        con.setLayout(new BorderLayout(0,0));
        setContentPane(con);
        con.add(p5, BorderLayout.WEST);
        con.add(lb, BorderLayout.NORTH);

        JScrollPane scrollPane=new JScrollPane();
        con.add(scrollPane, BorderLayout.EAST);
        scrollPane.setViewportView(table);
        //frame.setVisible(true);
    }
    // findInfo希望能传递参数object
    public void findInfo(String[] args, String sql) throws SQLException {
        if(!Connector.initialize()) {
            System.out.println("连接不上数据库...");
            new JOptionPane().showMessageDialog(null,
                    "连接不上数据库...",
                    "",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }  // 若连接失败返回
        System.out.println("Looking for data...");
        for (int i = 0; i < args.length; i++) {
            System.out.println(args[i]);
        }
        DefaultTableModel tableModel= (DefaultTableModel)table.getModel();    //获得表格模型

//        tableModel.setColumnIdentifiers(new Object[]{"书名","出版社","出版时间","丛书类别","定价"});    //设置表头
//        tableModel.addRow(new Object[]{"Java从入门到精通（第2版）","清华大学出版社","2010-07-01","软件工程师入门丛书","59.8元"});    //增加列
//        tableModel.addRow(new Object[]{"PHP从入门到精通（第2版）","清华大学出版社","2010-07-01","软件工程师入门丛书","69.8元"});
//        tableModel.addRow(new Object[]{"Visual Basic从入门到精通（第2版）","清华大学出版社","2010-07-01","软件工程师入门丛书","69.8元"});
//        tableModel.addRow(new Object[]{"Visual C++从入门到精通（第2版）","清华大学出版社","2010-07-01","软件工程师入门丛书","69.8元" });
//        table.setRowHeight(30);

//        String sql = "SELECT * FROM worker Where id=\"004\" ";
//        String sql = "SELECT * FROM worker";
        // 防注入攻击
        // 预编译
        PreparedStatement stmt = Connector.conn.prepareStatement(sql);
//        for (int i = 0; i < args.length; i++) {
//            stmt.setString(i, args[i]);
//        }
        // 执行
        ResultSet rs = Connector.executeQuery(stmt);
        ResultSetMetaData rsmd = rs.getMetaData();
        // error在executeQuery会被catch到，能返回大概是空表，此时不出错
//        if(rs!=null) {
//            System.out.println("cannot find result?");
//            return ;
//        }
//        Object[][] tableDate=new Object[5][8];
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

    }
    public void insertInfo() {
        System.out.println("Delete, World!");
        //rs = executeQuery("SELECT * FROM course");
        DefaultTableModel tableModel=(DefaultTableModel) table.getModel();    //获得表格模型
        int[] selectedRows=table.getSelectedRows();
        tableModel.removeRow(0);

        table.setModel(tableModel);    //应用表格模型
        System.out.println("Delete, World2!");
    }
    private Object[] appendValue(Object[] obj, Object newObj) {

        ArrayList<Object> temp = new ArrayList<Object>(Arrays.asList(obj));
        temp.add(newObj);
        return temp.toArray();

    }
}