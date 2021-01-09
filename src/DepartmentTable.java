import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;

public class DepartmentTable extends JTableDemo
{
    //继承后的额外变量
    String historyID = "";
    String historySalary = "";
    String historyName = "";
    String historyNum = "";

    public static void main(String[] args) {
        Connector.initialize();
        DepartmentTable frame = new DepartmentTable();
        frame.setVisible(true);
    }
    public DepartmentTable(){
        dbName = "department";
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
        JPanel pALL=new JPanel();
        pALL.setLayout(new GridLayout(7,1,5,5));

        JLabel lbH1 = new JLabel("id： ");
        JLabel lbH2 = new JLabel("base salary： ");
        JLabel lbH3 = new JLabel("name： ");
        JLabel lbH4 = new JLabel("member num： ");
        lbH1.setFont(f1);
        lbH2.setFont(f1);
        lbH3.setFont(f1);
        lbH4.setFont(f1);
        JTextField idText = new JTextField(20);
        JTextField salaryText = new JTextField(20);
        JTextField nameText = new JTextField(20);
        JTextField memberNumText = new JTextField(20);


        JButton buttonFind = new JButton("查询");
        JButton buttonCount = new JButton("插入");
        JButton buttonDelete = new JButton("删除");
        JButton buttonModify = new JButton("修改");
        JButton buttonBack = new JButton("返回");

        p1.add(lbH1);
        p1.add(idText);
        p2.add(lbH2);
        p2.add(salaryText);
        p3.add(lbH3);
        p3.add(nameText);
        p4.add(lbH4);
        p4.add(memberNumText);

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
                historyID = table.getValueAt(table.getSelectedRow(), 0).toString();
                historySalary = table.getValueAt(table.getSelectedRow(), 1).toString();
                //historySalary = (int) table.getValueAt(table.getSelectedRow(), 1);
                historyName = table.getValueAt(table.getSelectedRow(), 2).toString();
                historyNum =  table.getValueAt(table.getSelectedRow(), 3).toString();
                //historyNum =  (int) table.getValueAt(table.getSelectedRow(), 3);
                idText.setText(historyID);
                salaryText.setText(historySalary);
                nameText.setText(historyName);
                memberNumText.setText(historyNum);
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
                String id = idText.getText();
                String salary = salaryText.getText();
                String name = nameText.getText();
                String memberNum = memberNumText.getText();
                int flag = 0;
                String temp = "UPDATE "+dbName;
                if (!id.equals("")) {
                    //new JOptionPane().showMessageDialog(null, "用户名不能为空！");
                    if (flag == 0)
                        temp += " SET ";
                    temp += "id=\""+ id + "\"";
                    flag += 1;
                }
                if (!salary.equals("")) {
                    if (flag == 0)
                        temp += " SET ";
                    if (flag > 0)
                        temp += " , ";
                    temp += "base_salary=\""+ salary +"\"";
                    flag += 1;
                }
                if (!name.equals("")) {
                    if (flag == 0)
                        temp += " SET ";
                    if (flag > 0)
                        temp += " , ";
                    temp += "name=\""+ name+"\"";
                    flag += 1;
                }
                if (!memberNum.equals("")) {
                    if (flag == 0)
                        temp += " SET ";
                    if (flag > 0)
                        temp += " , ";
                    temp += "member_num=\""+ memberNum+"\"";
                    flag += 1;
                }
                temp += " WHERE id=\""+historyID+"\" AND base_salary=\""+ historySalary+
                        "\" AND name=\""+historyName+"\" AND member_num=\""+historyNum+"\";";
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
                String id = idText.getText();
                String salary = salaryText.getText();
                String name = nameText.getText();
                String memberNum = memberNumText.getText();

                int flag = 0;
                String temp = "SELECT * FROM "+dbName;
                if (!id.equals("")) {
                    //new JOptionPane().showMessageDialog(null, "用户名不能为空！");
                    if (flag == 0)
                        temp += " WHERE ";
                    temp += "id=\""+ id + "\"";
                    flag += 1;
                }
                if (!salary.equals("")) {
                    if (flag == 0)
                        temp += " WHERE ";
                    if (flag > 0)
                        temp += " AND ";
                    temp += "base_salary=\""+ salary+"\"";
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
                if (!memberNum.equals("")) {
                    if (flag == 0)
                        temp += " WHERE ";
                    if (flag > 0)
                        temp += " AND ";
                    temp += "member_num=\""+ memberNum+"\"";
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
                String id = idText.getText();
                String salary = salaryText.getText();
                String name = nameText.getText();
                String memberNum = memberNumText.getText();
                String temp = "INSERT INTO "+dbName+" VALUE" +
                        "(\""+id+"\", \""+salary+"\", \""+name+"\", \""+memberNum+"\");";
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
                String id = idText.getText();
                String salary = salaryText.getText();
                String name = nameText.getText();
                String memberNum = memberNumText.getText();
                int flag = 0;
                String temp = "DELETE FROM "+dbName;
                if (!id.equals("")) {
                    //new JOptionPane().showMessageDialog(null, "用户名不能为空！");
                    if (flag == 0)
                        temp += " WHERE ";
                    temp += "id=\""+ id + "\"";
                    flag += 1;
                }
                if (!salary.equals("")) {
                    if (flag == 0)
                        temp += " WHERE ";
                    if (flag > 0)
                        temp += " AND ";
                    temp += "base_salary=\""+ salary+"\"";
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
                if (!memberNum.equals("")) {
                    if (flag == 0)
                        temp += " WHERE ";
                    if (flag > 0)
                        temp += " AND ";
                    temp += "member_num=\""+ memberNum+"\"";
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