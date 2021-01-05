import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class Main extends JFrame implements Runnable{
    Thread t = new Thread(this);
    JFrame jf = new JFrame("Another Java GUI program");
    JDesktopPane deskpane = new JDesktopPane();//在窗体里建立虚拟桌面并实例化
    JPanel p = new JPanel();//创建一个面板并实例化
    Label lp1 = new Label("welcome to this system！本系统纯属练习!");
    JMenuBar mb = new JMenuBar();
    //菜单上的图标创建并实例化----------------------------------------------------
    //ImageIcon icon1=new ImageIcon("image//tjsc.gif");
    //其他略-----------------------------------------------
    public Main() {//构造函数

        setTitle("企业员工管理系统"); //设置窗体标题
        Container con = getContentPane();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        con.setLayout(new BorderLayout()); //创建一个布局
        con.add(deskpane,BorderLayout.CENTER);//实例虚拟桌面的布局
        Font f = new Font("新宋体", Font.PLAIN, 12); //设置一个字体，以后都使用这种字体


        //实例化菜单开始
        JMenu systemM=new JMenu("系统管理");
        systemM.setFont(f);
//        systemM.setBounds(50, 50, 700, 20);
        systemM.setForeground(Color.red);
        mb.add(systemM);
        JMenu manageM = new JMenu("信息管理");//实例化菜单栏
        manageM.setFont(f);
//        manageM.setBounds(70, 650, 700, 20);
        manageM.setBackground(Color.blue);
        mb.add(manageM);

        //其他略
        //退出窗体事件
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                    System.exit(0);
        }});
        //为系统管理菜单加事件----------------------------------------------------------
        //其他事件略
        p.setLayout(new BorderLayout());
        p.add(lp1,BorderLayout.EAST);
        p.setBackground(Color.pink);
        p.add(mb, BorderLayout.NORTH);
        //p.add(systemM);

        //t.start();
        //这里用的是con，不是jf，getContentPane返回ContentPane
//        jf.setBounds(300, 100, 400, 200);
//        jf.setBackground(Color.blue);
//        jf.add(p);
//        jf.setVisible(true);

        con.add(p,BorderLayout.SOUTH);

        Toolkit t=Toolkit.getDefaultToolkit();
        int width=t.getScreenSize().width-120;
        int height=t.getScreenSize().height-100;
        // 先width再height
        setSize(width,height);
        setLocation(50,25);
        setVisible(true);
        setResizable(false);
    }
    //线程的方法//用来做字幕滚动
    public void run(){
        System.out.println("线程启动了!");
        Toolkit t=Toolkit.getDefaultToolkit();
        int x=t.getScreenSize().width;
        lp1.setForeground(Color.red);
        while(true) {
            if (x < -600) {
                x = t.getScreenSize().width;
            }
            lp1.setBounds(x, 0, 700, 20);
            x -= 10;
            try{Thread.sleep(100);}catch(Exception e){}
        }
        //退出窗体事件
    }
    public void windowClosing(WindowEvent e) {
        System.exit(0);
    }
    public static void main(String[]args) { //主函数
        new Main();
    }
}