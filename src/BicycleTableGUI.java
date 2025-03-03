import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class BicycleTableGUI extends JFrame {
    private JButton showButton;
    private JTable dataTable;

    // กำหนดข้อมูลสำหรับการเชื่อมต่อฐานข้อมูล
    static String db_name = "project1";
    static String user = "root";
    static String pass = ""; // แก้ไขรหัสผ่านตามที่คุณใช้
    static String hostname = "localhost";
    static String db_driver = "com.mysql.cj.jdbc.Driver";

    // Constructor
    public BicycleTableGUI() {
        setTitle("Bicycle Table"); // กำหนดชื่อหน้าต่าง
        setSize(600, 300); // กำหนดขนาดหน้าต่าง
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // กำหนดการปิดโปรแกรมเมื่อปิดหน้าต่าง

        JPanel panel = new JPanel(); // สร้าง Panel เพื่อเรียงวางองค์ประกอบ
        panel.setLayout(new BorderLayout()); // กำหนด Layout ให้เป็น BorderLayout

        showButton = new JButton("Show"); // สร้างปุ่ม "Show"
        showButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                displayDataFromDatabase(); // เมื่อคลิกปุ่ม "Show" จะเรียกเมธอด displayDataFromDatabase()
            }
        });

        dataTable = new JTable(); // สร้าง JTable เพื่อแสดงข้อมูล
        JScrollPane scrollPane = new JScrollPane(dataTable); // สร้าง JScrollPane เพื่อให้สามารถเลื่อนได้ในกรณีที่ข้อมูลมีมาก

        panel.add(showButton, BorderLayout.NORTH); // เพิ่มปุ่ม "Show" ไว้ที่ด้านบนของ Panel
        panel.add(scrollPane, BorderLayout.CENTER); // เพิ่ม JScrollPane ที่มี JTable ไว้ที่ส่วนกลางของ Panel
        panel.add(dataTable);

        add(panel); // เพิ่ม Panel เข้าไปใน JFrame
    }

    // เมธอดสำหรับดึงข้อมูลจากฐานข้อมูลและแสดงบน JTable
    private void displayDataFromDatabase() {
        String jdbcURL = "jdbc:mysql://localhost/project1"; // URL สำหรับเชื่อมต่อฐานข้อมูล
        String username = "root"; // ชื่อผู้ใช้ฐานข้อมูล
        String password = ""; // รหัสผ่านฐานข้อมูล

        DefaultTableModel model = new DefaultTableModel(); // สร้าง DefaultTableModel เพื่อเก็บข้อมูลสำหรับ JTable
        model.addColumn("T_ID"); // เพิ่มคอลัมน์ T_ID ในตาราง
        model.addColumn("T_NAME"); // เพิ่มคอลัมน์ T_NAME ในตาราง

        try (Connection connection = DriverManager.getConnection(jdbcURL, username, password)) { // เชื่อมต่อกับฐานข้อมูล
            String sql = "SELECT T_ID, T_NAME FROM typebcc"; // สร้างคำสั่ง SQL เพื่อดึงข้อมูลจากตาราง
            Statement statement = connection.createStatement(); // สร้าง Statement เพื่อสร้างคำสั่ง SQL
            ResultSet result = statement.executeQuery(sql); // ส่งคำสั่ง SQL ไปทำงานและรับผลลัพธ์

            while (result.next()) { // วนลูปผลลัพธ์ที่ได้จากการสั่ง SQL
                String id = result.getString("T_ID"); // อ่านค่า T_ID จากผลลัพธ์
                String name = result.getString("T_NAME"); // อ่านค่า T_NAME จากผลลัพธ์
                model.addRow(new Object[]{id, name}); // เพิ่มข้อมูลลงในตาราง
            }
            dataTable.setModel(model); // กำหนด DefaultTableModel ให้กับ JTable เพื่อแสดงข้อมูล
        } catch (SQLException e) { // รับข้อผิดพลาดในการเชื่อมต่อหรือดึงข้อมูล
            JOptionPane.showMessageDialog(this, "Error fetching data from database: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE); // แสดงข้อความแจ้งเตือนในกรณีเกิดข้อผิดพลาด
            e.printStackTrace(); // แสดง Stack Trace เพื่อช่วยในการตรวจสอบปัญหา
        }
    }

    // เมธอด main สำหรับเริ่มต้นโปรแกรม
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new BicycleTableGUI().setVisible(true); // สร้าง GUI และแสดงขึ้นมา
            }
        });
    }
}
