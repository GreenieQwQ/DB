import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;

// 外码约束没生效
public class SalaryHistoryTable extends JTableDemo
{
    //
    String historyWorker_id = "";
    String historyName = "";
    String historySalary = "";
    String historyBaseSalary = "";
    String historyTotalSalary = "";

    public static void main(String[] args) {
        Connector.initialize();
        SalaryHistoryTable frame = new SalaryHistoryTable();
        frame.setVisible(true);
    }
    public SalaryHistoryTable(){
        dbName = "salaryHistory";
        JLabel lb = new JLabel("历史薪资查询", SwingConstants.CENTER);
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

        JLabel lbH1 = new JLabel("SHid： ");
        JLabel lbH2 = new JLabel("date： ");
        JLabel lbH3 = new JLabel("worker_id： ");
        JLabel lbH4 = new JLabel("worker_name： ");
        JLabel lbH5 = new JLabel("department_name： ");
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
        //JButton buttonSave = new JButton("保存至历史薪资记录表");
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
        //pButton.add(buttonSave);
//        pButton.add(buttonCount);
//        pButton.add(buttonDelete);
//        pButton.add(buttonModify);

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
                historySalary = table.getValueAt(table.getSelectedRow(), 2).toString();
                historyBaseSalary =  table.getValueAt(table.getSelectedRow(), 3).toString();
                historyTotalSalary =  table.getValueAt(table.getSelectedRow(), 4).toString();
                
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
                String SHid = WorkerIdText.getText();
                String date = departmentText.getText();
                String worker_id = jobText.getText();
                String worker_name = salaryText.getText();
                String department_name = totalText.getText();

                int flag = 0;
                String temp = "SELECT * FROM "+dbName;
                if (!SHid.equals("")) {
                    //new JOptionPane().showMessageDialog(null, "用户名不能为空！");
                    if (flag == 0)
                        temp += " WHERE ";
                    temp += "SHid=\""+ SHid + "\"";
                    flag += 1;
                }
                if (!date.equals("")) {
                    if (flag == 0)
                        temp += " WHERE ";
                    if (flag > 0)
                        temp += " AND ";
                    temp += "date=\""+ date+"\"";
                    flag += 1;
                }
                if (!worker_id.equals("")) {
                    if (flag == 0)
                        temp += " WHERE ";
                    if (flag > 0)
                        temp += " AND ";
                    temp += "worker_id=\""+ worker_id+"\"";
                    flag += 1;
                }
                if (!worker_name.equals("")) {
                    if (flag == 0)
                        temp += " WHERE ";
                    if (flag > 0)
                        temp += " AND ";
                    temp += "worker_name=\""+ worker_name+"\"";
                    flag += 1;
                }
                if (!department_name.equals("")) {
                    if (flag == 0)
                        temp += " WHERE ";
                    if (flag > 0)
                        temp += " AND ";
                    temp += "department_name=\""+ department_name+"\"";
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