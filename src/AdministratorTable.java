import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;

// 外码约束没生效
public class AdministratorTable extends JTableDemo
{
    //
    String historyWorker_id = "";
    String historyDepartment_id = "";
    String historyJob = "";
    String historySalary = "";

    public static void main(String[] args) {
        Connector.initialize();
        AdministratorTable frame = new AdministratorTable();
        frame.setVisible(true);
    }
    public AdministratorTable(){
        dbName = "Administrator";
        JLabel lb = new JLabel("管理员信息查询", SwingConstants.CENTER);
        Font f = new Font("新宋体", Font.PLAIN, 40);
        Font f1 = new Font("新宋体", Font.PLAIN, 12);
        lb.setFont(f);
        // p1是左边，p2是右边
        JPanel p1=new JPanel();
        JPanel p2=new JPanel();
        JPanel p3=new JPanel();
        JPanel p4=new JPanel();
        JPanel p5=new JPanel();
        JPanel pALL=new JPanel();
        pALL.setLayout(new GridLayout(7,1,5,5));

        JLabel lbH1 = new JLabel("id： ");
        JLabel lbH2 = new JLabel("name： ");
        JLabel lbH3 = new JLabel("passwd： ");
        JLabel lbH4 = new JLabel("security： ");
        lbH1.setFont(f1);
        lbH2.setFont(f1);
        lbH3.setFont(f1);
        lbH4.setFont(f1);
        JTextField WorkerIdText = new JTextField(20);
        JTextField departmentText = new JTextField(20);
        JTextField jobText = new JTextField(20);
        JTextField salaryText = new JTextField(20);


        JButton buttonFind = new JButton("查询");
        JButton buttonCount = new JButton("插入");
        JButton buttonDelete = new JButton("删除");
        JButton buttonModify = new JButton("修改");
        JButton buttonBack = new JButton("返回");

        p1.add(lbH1);
        p1.add(WorkerIdText);
        p2.add(lbH2);
        p2.add(departmentText);
        p3.add(lbH3);
        p3.add(jobText);
        p4.add(lbH4);
        p4.add(salaryText);

        p5.add(buttonFind);
        p5.add(buttonCount);
        p5.add(buttonDelete);
        p5.add(buttonModify);

        pALL.add(p1);
        pALL.add(p2);
        pALL.add(p3);
        pALL.add(p4);
        pALL.add(p5);

        con.add(pALL, BorderLayout.WEST);
        con.add(lb, BorderLayout.NORTH);
        con.add(buttonBack, BorderLayout.SOUTH);

        try {
            findInfo("select * from "+dbName);
            findInfo("select * from "+dbName);
        } catch (SQLException ex){

        }

        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked (MouseEvent me) {
                // will excute twice, for click on and off
                historyWorker_id = table.getValueAt(table.getSelectedRow(), 0).toString();
                historyDepartment_id = table.getValueAt(table.getSelectedRow(), 1).toString();
                //historySalary = (int) table.getValueAt(table.getSelectedRow(), 1);
                historyJob = table.getValueAt(table.getSelectedRow(), 2).toString();
                //historySalary =  table.getValueAt(table.getSelectedRow(), 3).toString();
                //historyNum =  (int) table.getValueAt(table.getSelectedRow(), 3);
                WorkerIdText.setText(historyWorker_id);
                departmentText.setText(historyDepartment_id);
                jobText.setText(historyJob);
                //salaryText.setText(historySalary);
            }
        });
        buttonBack.addMouseListener(new MouseAdapter() {
            public void mouseClicked (MouseEvent me) {
                new Main();
                Main.main(new String [] {});
                dispose();
                //con.setVisible(false);
            }
        });
        buttonModify.addMouseListener(new MouseAdapter() {
            public void mouseClicked (MouseEvent me) {
                String id = WorkerIdText.getText();
                String name = departmentText.getText();
                String passwd = jobText.getText();
                String security = salaryText.getText();
                int flag = 0;
                String temp = "UPDATE "+dbName;
                if (!id.equals("")) {
                    //new JOptionPane().showMessageDialog(null, "用户名不能为空！");
                    if (flag == 0)
                        temp += " SET ";
                    temp += "id=\""+ id + "\"";
                    flag += 1;
                }
                if (!name.equals("")) {
                    if (flag == 0)
                        temp += " SET ";
                    if (flag > 0)
                        temp += " , ";
                    temp += "name=\""+ name +"\"";
                    flag += 1;
                }
                if (!passwd.equals("")) {
                    if (flag == 0)
                        temp += " SET ";
                    if (flag > 0)
                        temp += " , ";
                    temp += "passwd=\""+ passwd+"\"";
                    flag += 1;
                }
                if (!security.equals("")) {
                    if (flag == 0)
                        temp += " SET ";
                    if (flag > 0)
                        temp += " , ";
                    temp += "security=\""+ security+"\"";
                    flag += 1;
                }
                temp += " WHERE id=\""+historyWorker_id+"\" AND name=\""+ historyDepartment_id+
                        "\" AND passwd=\""+historyJob+"\" AND security=\""+historySalary+"\";";
                try {
                    System.out.println(temp);
                    modifyInfo(temp);
                }
                catch (SQLException ex){

                }
            }
        });
        buttonFind.addMouseListener(new MouseAdapter() {
            public void mouseClicked (MouseEvent me) {
                String id = WorkerIdText.getText();
                String name = departmentText.getText();
                String passwd = jobText.getText();
                String security = salaryText.getText();

                int flag = 0;
                String temp = "SELECT * FROM "+dbName;
                if (!id.equals("")) {
                    //new JOptionPane().showMessageDialog(null, "用户名不能为空！");
                    if (flag == 0)
                        temp += " WHERE ";
                    temp += "id=\""+ id + "\"";
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
                if (!passwd.equals("")) {
                    if (flag == 0)
                        temp += " WHERE ";
                    if (flag > 0)
                        temp += " AND ";
                    temp += "passwd=\""+ passwd+"\"";
                    flag += 1;
                }
                if (!security.equals("")) {
                    if (flag == 0)
                        temp += " WHERE ";
                    if (flag > 0)
                        temp += " AND ";
                    temp += "security=\""+ security+"\"";
                    flag += 1;
                }
                try {
                    System.out.println(temp);
                    findInfo(temp);
                }
                catch (SQLException ex){

                }
            }
        });
        buttonCount.addMouseListener(new MouseAdapter() {
            public void mouseClicked (MouseEvent me) {
                String id = WorkerIdText.getText();
                String name = departmentText.getText();
                String passwd = jobText.getText();
                String security = salaryText.getText();
                String temp = "INSERT INTO "+dbName+" VALUE" +
                        "(\""+id+"\", \""+name+"\", \""+passwd+"\", \""+security+"\");";
                try {
                    System.out.println(temp);
                    insertInfo(temp);
                }
                catch (SQLException ex){
                    System.out.println("Insertion failed!\n");

                }
            }
        });
        buttonDelete.addMouseListener(new MouseAdapter() {
            public void mouseClicked (MouseEvent me) {
                String id = WorkerIdText.getText();
                String name = departmentText.getText();
                String passwd = jobText.getText();
                String security = salaryText.getText();
                int flag = 0;
                String temp = "DELETE FROM "+dbName;
                if (!id.equals("")) {
                    //new JOptionPane().showMessageDialog(null, "用户名不能为空！");
                    if (flag == 0)
                        temp += " WHERE ";
                    temp += "id=\""+ id + "\"";
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
                if (!passwd.equals("")) {
                    if (flag == 0)
                        temp += " WHERE ";
                    if (flag > 0)
                        temp += " AND ";
                    temp += "passwd=\""+ passwd+"\"";
                    flag += 1;
                }
                if (!security.equals("")) {
                    if (flag == 0)
                        temp += " WHERE ";
                    if (flag > 0)
                        temp += " AND ";
                    temp += "security=\""+ security+"\"";
                    flag += 1;
                }
                try {
                    System.out.println(temp);
                    deleteInfo(temp);
                }
                catch (SQLException ex){
                    System.out.println("Deletion failed!\n");
                }
            }
        });

    }
}