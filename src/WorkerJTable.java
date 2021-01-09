import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

public class WorkerJTable extends JTableDemo
{
    //继承后的额外变量
    String historyID = "";
    String historySex = "";
    String historyName = "";

    public static void main(String[] args) {
        Connector.initialize();
        WorkerJTable frame = new WorkerJTable();
        frame.setVisible(true);
    }
    public WorkerJTable(){
        dbName = "Worker";
        JLabel lb = new JLabel("员工信息查询", SwingConstants.CENTER);
        Font f = new Font("新宋体", Font.PLAIN, 40);
        Font f1 = new Font("新宋体", Font.PLAIN, 12);
        lb.setFont(f);
        // p1是左边，p2是右边
        JPanel p1=new JPanel();
        JPanel p2=new JPanel();
        JPanel p3=new JPanel();
        JPanel p4=new JPanel();
        //JPanel p5=new JPanel();
        JPanel pALL=new JPanel();
        pALL.setLayout(new GridLayout(7,1,5,5));

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
        JButton buttonDelete = new JButton("删除");
        JButton buttonModify = new JButton("修改");
        JButton buttonBack = new JButton("返回");

        p1.add(lbH1);
        p1.add(idText);
        p2.add(lbH2);
        p2.add(sexText);
        p3.add(lbH3);
        p3.add(nameText);

        p4.add(buttonFind);
        p4.add(buttonCount);
        p4.add(buttonDelete);
        p4.add(buttonModify);

        pALL.add(p1);
        pALL.add(p2);
        pALL.add(p3);
        pALL.add(p4);

        con.add(pALL, BorderLayout.WEST);
        con.add(lb, BorderLayout.NORTH);
        con.add(buttonBack, BorderLayout.SOUTH);

        try {
            findInfo("select * from worker");
            findInfo("select * from worker");
        } catch (SQLException ex){

        }

        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked (MouseEvent me) {
                // will excute twice, for click on and off
                historyID = table.getValueAt(table.getSelectedRow(), 0).toString();
                historySex = table.getValueAt(table.getSelectedRow(), 1).toString();
                historyName = table.getValueAt(table.getSelectedRow(), 2).toString();
                idText.setText(historyID);
                sexText.setText(historySex);
                nameText.setText(historyName);
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
                String sex = sexText.getText();
                String name = nameText.getText();
                String[] stringArr= {id, sex, name};
                // 在StringArr记得处理空值
                int flag = 0;
                String temp = "UPDATE Worker";
                if (!id.equals("")) {
                    //new JOptionPane().showMessageDialog(null, "用户名不能为空！");
                    if (flag == 0)
                        temp += " SET ";
                    temp += "id=\""+ id + "\"";
                    flag += 1;
                }
                if (!sex.equals("")) {
                    if (flag == 0)
                        temp += " SET ";
                    if (flag > 0)
                        temp += " , ";
                    temp += "sex=\""+ sex+"\"";
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
                temp += " WHERE id=\""+historyID+"\" AND sex=\""+
                        historySex+"\" AND name=\""+historyName+"\";";
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
                String sex = sexText.getText();
                String name = nameText.getText();

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
                    findInfo(temp);
                }
                catch (SQLException ex){

                }
            }
        });
        buttonCount.addMouseListener(new MouseAdapter() {
            public void mouseClicked (MouseEvent me) {
                String id = idText.getText();
                String sex = sexText.getText();
                String name = nameText.getText();
                String temp = "INSERT INTO worker VALUE" +
                        "(\""+id+"\", \""+sex+"\", \""+name+"\");";
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
                String sex = sexText.getText();
                String name = nameText.getText();
                int flag = 0;
                String temp = "DELETE FROM Worker ";
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
                    deleteInfo(temp);
                }
                catch (SQLException ex){
                    System.out.println("Deletion failed!\n");
                }
            }
        });

    }
}