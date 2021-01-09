import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class Main extends JFrame{
    JPanel con;
    public Main() {//构造函数
        JLabel lb = new JLabel("欢迎使用人事工资管理系统", SwingConstants.CENTER);
        Font f = new Font("黑体", Font.PLAIN, 40);
        Font f0 = new Font("新宋体", Font.PLAIN, 25);
        JLabel lb1 = new JLabel("系统管理", SwingConstants.CENTER);
        JLabel lb2 = new JLabel("员工信息查询", SwingConstants.CENTER);
        JLabel lb3 = new JLabel("工资查询", SwingConstants.CENTER);
        Font f1 = new Font("新宋体", Font.PLAIN, 12);
        lb.setFont(f);
        lb1.setFont(f0);
        lb2.setFont(f0);
        lb3.setFont(f0);

        setBounds(100,100,1000,700);
        JPanel con0=new JPanel();
        con=new JPanel();
        con.setBorder(new EmptyBorder(5,5,5,5));
        con.setLayout(new BorderLayout(0,0));
        setContentPane(con);

        JPanel p1=new JPanel();
        JPanel p2=new JPanel();
        JPanel p3=new JPanel();
        //JPanel p4=new JPanel();
        //JPanel p5=new JPanel();
        JPanel pALL=new JPanel();
        con0.setLayout(new GridLayout(1,3,15,15));
        con0.add(p1);
        con0.add(p2);
        con0.add(p3);

        p1.setLayout(new GridLayout(10,1,15,15));
        p2.setLayout(new GridLayout(10,1,15,15));
        p3.setLayout(new GridLayout(10,1,15,15));

        p1.add(lb1);
        p2.add(lb2);
        p3.add(lb3);


        JButton buttonWorker = new JButton("员工基本信息查询");
        JButton buttonDepart = new JButton("部门基本信息查询");
        JButton buttonWorkerAndDepart = new JButton("员工和任职部门查询");
        JButton buttonCheckin = new JButton("考勤信息查询");
        JButton buttonSalary = new JButton("查询本月工资");
        JButton buttonHistorySalary = new JButton("查询历史工资");
        JButton buttonAdministrator = new JButton("管理员账号管理");
        p1.add(buttonAdministrator);
        p2.add(buttonWorker);
        p2.add(buttonDepart);
        p2.add(buttonWorkerAndDepart);
        p2.add(buttonCheckin);
        p3.add(buttonSalary);
        p3.add(buttonHistorySalary);

        con.add(lb, BorderLayout.NORTH);
        con.add(con0, BorderLayout.CENTER);

        buttonWorker.addMouseListener(new MouseAdapter() {
            public void mouseClicked (MouseEvent me) {
                new WorkerJTable();
                WorkerJTable.main(new String [] {});
                dispose();
            }
        });
        buttonDepart.addMouseListener(new MouseAdapter() {
            public void mouseClicked (MouseEvent me) {
                new DepartmentTable();
                DepartmentTable.main(new String [] {});
                dispose();
            }
        });
        buttonWorkerAndDepart.addMouseListener(new MouseAdapter() {
            public void mouseClicked (MouseEvent me) {
                new ManagementTable();
                ManagementTable.main(new String [] {});
                dispose();
            }
        });
        buttonCheckin.addMouseListener(new MouseAdapter() {
            public void mouseClicked (MouseEvent me) {
                new AttendenceTable();
                AttendenceTable.main(new String [] {});
                dispose();
            }
        });
        buttonAdministrator.addMouseListener(new MouseAdapter() {
            public void mouseClicked (MouseEvent me) {
                new AdministratorTable();
                AdministratorTable.main(new String [] {});
                dispose();
            }
        });
        buttonSalary.addMouseListener(new MouseAdapter() {
            public void mouseClicked (MouseEvent me) {
                new SalaryTable();
                SalaryTable.main(new String [] {});
                dispose();
            }
        });

    }
    public void windowClosing(WindowEvent e) {
        System.exit(0);
    }
    public static void main(String[] args) { //主函数
        Main frame = new Main();
        frame.setVisible(true);
    }
}