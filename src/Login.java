import javax.swing.* ;
import java.awt.* ;
import java.awt.event.*;
import java.sql.*;

public class Login extends JFrame {
    JFrame jf;
    JTextField textName = new JTextField("admin");
    JPasswordField textPassword = new JPasswordField("123456");
    JLabel label = new JLabel("人事工资管理系统");
    JButton buttonEnter = new JButton("登陆");
    JButton buttonCancel = new JButton("清空");

    public Login()
    {
        Connector.initialize();
        jf = this;
        setTitle("登录");
        Font f= new Font ("新宋体", Font.PLAIN, 12);
        Container con= getContentPane ();
        con.setLayout(null);
        label.setBounds(80,10,140,20);
        label.setFont(new Font ("新宋体", Font.BOLD, 16));
        con.add(label);

//        labelName.setBounds (55,45,55,20);
//        labelName.setFont(f);
//        con.add(labelName);
        textName.setBounds (85,45,120,20);
        con.add(textName);

        textPassword.setBounds (85,85,120,20);
        con.add(textPassword);

        // 登录的鼠标监听
        buttonEnter.setBounds (55,115, 60,20);
        buttonEnter.setFont(f);
        buttonEnter.addMouseListener(new MouseAdapter() {
            public void mouseClicked (MouseEvent me) {
                String name = textName.getText();
                String pwd = textPassword.getText();
                if (name.equals("")) {
                    new JOptionPane().showMessageDialog(null, "用户名不能为空！");
                } else if (pwd.equals("")) {
                    new JOptionPane().showMessageDialog(null, "密码不能为空！");
                } else {
                    try {
                        Judge(name, pwd);
                    } catch (SQLException sqlException) {
                        sqlException.printStackTrace();
                    }
                }
            }
        });
        // 登录的键盘监听
        buttonEnter. addKeyListener (new KeyAdapter () {
            public void keyPressed (KeyEvent e) {
            }
        });
        con.add(buttonEnter);

        buttonCancel.setBounds (155,115, 60,20);
        buttonCancel.setFont(f);
        con.add(buttonCancel);
        // 清空按钮的鼠标监听方法
        buttonCancel.addMouseListener (new MouseAdapter () {
            public void mouseClicked (MouseEvent me) {
                    textName.setText("");
                    textPassword. setText ("");
            }
        });
        // 窗口大小不可调
        setResizable (false);
        // 窗口图标
        Image img= Toolkit.getDefaultToolkit () .getImage ("image\\main.gif");
        setIconImage(img);
        Toolkit t= Toolkit.getDefaultToolkit();
        int w= t.getScreenSize().width;
        int h= t.getScreenSize().height;
        setBounds (w/2- 150,h/2- 90,300,180);
        setVisible(true);
        // 获取焦点
        buttonEnter.grabFocus();
        buttonEnter.requestFocusInWindow();
    }

    private void Judge (String user, String passwd) throws SQLException {
        if(!Connector.initialize()) {
            System.out.println("连接不上数据库...");
            new JOptionPane().showMessageDialog(null,
                    "连接不上数据库...",
                    "",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }  // 若连接失败返回
        String sql = "SELECT * FROM Administrator WHERE name=? AND passwd=?";
        // 防注入攻击
        // 预编译
        PreparedStatement stmt = Connector.conn.prepareStatement(sql);

        //设置参数
        stmt.setString(1, user);
        stmt.setString(2, passwd);

        // 执行
        ResultSet rs = stmt.executeQuery();

        if(rs.next())
        {
            System .out. println ("密码正确");
            jf.setVisible(false);
            //关闭数据库连接
            //Connector.conn.close();
            //new Main(); //开始主题运行界面
            System.exit(114514);
        }
        else {
            System.out.println("错误");
            new JOptionPane().showMessageDialog(null,
                    "用户名或密码错误",
                    "",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main (String args []) {
        new Login ();
    }
}


//public class Login {
//
//    public static void main(String[] args) {
//        // 创建 JFrame 实例
//        JFrame frame = new JFrame("Login Example");
//        // Setting the width and height of frame
//        frame.setSize(350, 200);
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//
//        /* 创建面板，这个类似于 HTML 的 div 标签
//         * 我们可以创建多个面板并在 JFrame 中指定位置
//         * 面板中我们可以添加文本字段，按钮及其他组件。
//         */
//        JPanel panel = new JPanel();
//        // 添加面板
//        frame.add(panel);
//        /*
//         * 调用用户定义的方法并添加组件到面板
//         */
//        placeComponents(panel);
//
//        // 设置界面可见
//        frame.setVisible(true);
//    }
//
//    private static void placeComponents(JPanel panel) {
//
//        /* 布局部分我们这边不多做介绍
//         * 这边设置布局为 null
//         */
//        panel.setLayout(null);
//
//        // 创建 JLabel
//        JLabel userLabel = new JLabel("User:");
//        /* 这个方法定义了组件的位置。
//         * setBounds(x, y, width, height)
//         * x 和 y 指定左上角的新位置，由 width 和 height 指定新的大小。
//         */
//        userLabel.setBounds(10,20,80,25);
//        panel.add(userLabel);
//
//        /*
//         * 创建文本域用于用户输入
//         */
//        JTextField userText = new JTextField(20);
//        userText.setBounds(100,20,165,25);
//        panel.add(userText);
//
//        // 输入密码的文本域
//        JLabel passwordLabel = new JLabel("Password:");
//        passwordLabel.setBounds(10,50,80,25);
//        panel.add(passwordLabel);
//
//        /*
//         *这个类似用于输入的文本域
//         * 但是输入的信息会以点号代替，用于包含密码的安全性
//         */
//        JPasswordField passwordText = new JPasswordField(20);
//        passwordText.setBounds(100,50,165,25);
//        panel.add(passwordText);
//
//        // 创建登录按钮
//        JButton loginButton = new JButton("login");
//        loginButton.setBounds(10, 80, 80, 25);
//        panel.add(loginButton);
//    }
//
//}

//import javax.swing.*;
//public class Login {
//    /**{
//     * 创建并显示GUI。出于线程安全的考虑，
//     * 这个方法在事件调用线程中调用。
//     */
//    private static void createAndShowGUI() {
//        // 确保一个漂亮的外观风格
//        JFrame.setDefaultLookAndFeelDecorated(true);
//
//        // 创建及设置窗口
//        JFrame frame = new JFrame("HelloWorldSwing");
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//
//        // 添加 "Hello World" 标签
//        JLabel label = new JLabel("Hello World");
//        frame.getContentPane().add(label);
//
//        // 显示窗口
//        frame.pack();
//        frame.setVisible(true);
//    }
//
//    public static void main(String[] args) {
//        // 显示应用 GUI
//        javax.swing.SwingUtilities.invokeLater(new Runnable() {
//            public void run() {
//                createAndShowGUI();
//            }
//        });
//    }
//}