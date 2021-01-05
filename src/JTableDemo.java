
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

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
        //table=new JTable(tableDate,name);
        table = new JTable();
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
        p4.setLayout(new GridLayout(5,1,5,5));
        Box bV=Box.createVerticalBox();
        Box bH1=Box.createHorizontalBox();
        Box bH2=Box.createHorizontalBox();
        Box bH3=Box.createHorizontalBox();

        JLabel lbH1 = new JLabel("部门编号： ");
        JLabel lbH2 = new JLabel("部门名称： ");
        lbH1.setFont(f1);
        lbH2.setFont(f1);
        JTextField noText = new JTextField(20);
        JTextField nameText = new JTextField(20);

        JButton buttonFind = new JButton("查询");
        JButton buttonCount = new JButton("统计人数");

        buttonFind.addMouseListener(new MouseAdapter() {
            public void mouseClicked (MouseEvent me) {
                String name = lbH1.getText();
                String pwd = lbH2.getText();
                if (name.equals("")) {
                    //new JOptionPane().showMessageDialog(null, "用户名不能为空！");
                } else if (pwd.equals("")) {
                    new JOptionPane().showMessageDialog(null, "密码不能为空！");
                }
                findInfo(name, pwd);
            }
        });
        buttonCount.addMouseListener(new MouseAdapter() {
            public void mouseClicked (MouseEvent me) {
                deleteInfo();
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
        p1.add(noText);
        p2.add(lbH2);
        p2.add(nameText);
        p3.add(buttonFind);
        p3.add(buttonCount);

        p4.add(p1);
        p4.add(p2);
        p4.add(p3);
//        bV.add(bH1);
//        bV.add(bH2);
//        bV.add(bH3);

        //contentPane.add(bV, BorderLayout.WEST);

        //con.add(scrollPane, BorderLayout.EAST);
        setBounds(500,700,450,200);
        con=new JPanel();
        con.setBorder(new EmptyBorder(5,5,5,5));
        con.setLayout(new BorderLayout(0,0));
        setContentPane(con);
        con.add(p4, BorderLayout.WEST);
        con.add(lb, BorderLayout.NORTH);

        JScrollPane scrollPane=new JScrollPane();
        con.add(scrollPane, BorderLayout.EAST);
        scrollPane.setViewportView(table);
        //frame.setVisible(true);
    }
    public void findInfo(String name, String pwd) {
        System.out.println("Hello, World!");
        //rs = executeQuery("SELECT * FROM course");
        DefaultTableModel tableModel=(DefaultTableModel) table.getModel();    //获得表格模型
        tableModel.setRowCount(0);    //清空表格中的数据
        tableModel.setColumnIdentifiers(new Object[]{"书名","出版社","出版时间","丛书类别","定价"});    //设置表头
        tableModel.addRow(new Object[]{"Java从入门到精通（第2版）","清华大学出版社","2010-07-01","软件工程师入门丛书","59.8元"});    //增加列
        tableModel.addRow(new Object[]{"PHP从入门到精通（第2版）","清华大学出版社","2010-07-01","软件工程师入门丛书","69.8元"});
        tableModel.addRow(new Object[]{"Visual Basic从入门到精通（第2版）","清华大学出版社","2010-07-01","软件工程师入门丛书","69.8元"});
        tableModel.addRow(new Object[]{"Visual C++从入门到精通（第2版）","清华大学出版社","2010-07-01","软件工程师入门丛书","69.8元" });
        table.setRowHeight(30);

        table.setModel(tableModel);    //应用表格模型
        System.out.println("Hello, World2!");
    }
    public void deleteInfo() {
        System.out.println("Delete, World!");
        //rs = executeQuery("SELECT * FROM course");
        DefaultTableModel tableModel=(DefaultTableModel) table.getModel();    //获得表格模型
        int[] selectedRows=table.getSelectedRows();
        tableModel.removeRow(0);

        table.setModel(tableModel);    //应用表格模型
        System.out.println("Delete, World2!");
    }
}