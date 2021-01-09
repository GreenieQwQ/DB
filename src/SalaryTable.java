import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;

// 外码约束没生效
public class SalaryTable extends JTableDemo
{
    //
    String historyWorker_id = "";
    String historyName = "";
    String historySalary = "";
    String historyBaseSalary = "";
    String historyTotalSalary = "";

    public static void main(String[] args) {
        Connector.initialize();
        SalaryTable frame = new SalaryTable();
        frame.setVisible(true);
    }
    public SalaryTable(){
        dbName = "Salary";
        JLabel lb = new JLabel("薪资查询", SwingConstants.CENTER);
        Font f = new Font("新宋体", Font.PLAIN, 40);
        Font f1 = new Font("新宋体", Font.PLAIN, 12);
        lb.setFont(f);
        // p1是左边，p2是右边
        JPanel p0=new JPanel();
        JPanel p1=new JPanel();
        JPanel p2=new JPanel();
        JPanel p3=new JPanel();
        JPanel p4=new JPanel();
        JPanel p5=new JPanel();
        JPanel pButton=new JPanel();
        JPanel pALL=new JPanel();
        pALL.setLayout(new GridLayout(7,1,5,5));

        JLabel lbH1 = new JLabel("id： ");
        JLabel lbH2 = new JLabel("name： ");
        JLabel lbH3 = new JLabel("salary： ");
        JLabel lbH4 = new JLabel("base_salary： ");
        JLabel lbH5 = new JLabel("total_salary： ");
        lbH1.setFont(f1);
        lbH2.setFont(f1);
        lbH3.setFont(f1);
        lbH4.setFont(f1);
        lbH5.setFont(f1);
        JTextField WorkerIdText = new JTextField(20);
        JTextField departmentText = new JTextField(20);
        JTextField jobText = new JTextField(20);
        JTextField salaryText = new JTextField(20);
        JTextField totalText = new JTextField(20);


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
        p5.add(lbH5);
        p5.add(totalText);


        pButton.add(buttonFind);
        //pButton.add(buttonCount);
        //pButton.add(buttonDelete);
        //pButton.add(buttonModify);

        pALL.add(p1);
        pALL.add(p2);
        pALL.add(p3);
        pALL.add(p4);
        pALL.add(p5);
        pALL.add(pButton);

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
                historyName = table.getValueAt(table.getSelectedRow(), 1).toString();
                //historyBaseSalary = (int) table.getValueAt(table.getSelectedRow(), 1);
                historySalary = table.getValueAt(table.getSelectedRow(), 2).toString();
                historyBaseSalary =  table.getValueAt(table.getSelectedRow(), 3).toString();
                historyTotalSalary =  table.getValueAt(table.getSelectedRow(), 4).toString();
                //historyNum =  (int) table.getValueAt(table.getSelectedRow(), 3);
                WorkerIdText.setText(historyWorker_id);
                departmentText.setText(historyName);
                jobText.setText(historySalary);
                salaryText.setText(historyBaseSalary);
                totalText.setText(historyTotalSalary);
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

        buttonFind.addMouseListener(new MouseAdapter() {
            public void mouseClicked (MouseEvent me) {
                String id = WorkerIdText.getText();
                String name = departmentText.getText();
                String salary = jobText.getText();
                String base_salary = salaryText.getText();
                String total_salary = totalText.getText();

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
                if (!salary.equals("")) {
                    if (flag == 0)
                        temp += " WHERE ";
                    if (flag > 0)
                        temp += " AND ";
                    temp += "salary=\""+ salary+"\"";
                    flag += 1;
                }
                if (!base_salary.equals("")) {
                    if (flag == 0)
                        temp += " WHERE ";
                    if (flag > 0)
                        temp += " AND ";
                    temp += "base_salary=\""+ base_salary+"\"";
                    flag += 1;
                }
                if (!total_salary.equals("")) {
                    if (flag == 0)
                        temp += " WHERE ";
                    if (flag > 0)
                        temp += " AND ";
                    temp += "total_salary=\""+ total_salary+"\"";
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


    }
}