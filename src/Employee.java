import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.*;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;

public class Employee extends JFrame implements ActionListener {
    // กำหนดข้อมูลสำหรับการเชื่อมต่อฐานข้อมูล MySQL
    static String db_name = "product1"; // ชื่อฐานข้อมูล
    static String user = "root"; // ชื่อผู้ใช้ MySQL
    static String pass = ""; // รหัสผ่าน MySQL
    static String hostname = "localhost"; // โฮสต์ของ MySQL
    static String db_driver = "com.mysql.cj.jdbc.Driver"; // Driver สำหรับเชื่อมต่อ MySQL

    // กำหนดตัวแปรสำหรับข้อมูลพนักงาน
    private JTextField IDField, NameField, PhonField; // ช่องใส่ข้อมูล ID, Name, Phone
    private JButton btninsert, btnupdate, btndelete, btnReport, btnback, btnCancel; // ปุ่ม INSERT, UPDATE, DELETE,
                                                                                    // Report, Back, Cancel
    private JTextArea dataTextArea; // แสดงข้อมูล
    private JTable table; // ตาราง
    private DefaultTableModel model; // โมเดลของตาราง

    // สร้างหน้าต่าง GUI สำหรับการจัดการพนักงาน
    public Employee() {
        setTitle("Employee Management System"); // กำหนดหัวเรื่องหน้าต่าง
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // ปิดโปรแกรมเมื่อปิดหน้าต่าง
        setSize(1100, 600); // กำหนดขนาดหน้าต่าง

        // สร้างและกำหนดขนาดของพาเนล
        JPanel pn = new JPanel(new GridLayout(7, 2));
        pn.setPreferredSize(new Dimension(335, 600)); // กำหนดขนาดพาเนล

        JPanel pn2 = new JPanel(new GridLayout(1, 1));
        pn2.setPreferredSize(new Dimension(750, 600)); // กำหนดขนาดพาเนล

        JPanel pn3 = new JPanel(new GridLayout(1, 1));
        pn3.setPreferredSize(new Dimension(1000, 70));
        pn3.setBackground(new Color(54, 100, 139)); // กำหนดสีพาเนล

        JPanel pn4 = new JPanel(new GridLayout(1, 5));
        pn4.setPreferredSize(new Dimension(1000, 50));
        pn4.setBackground(new Color(200, 200, 200)); // กำหนดสีพาเนล

        // สร้างและกำหนดขนาดของ Label
        JLabel shop = new JLabel("Employee"); // สร้าง Label
        shop.setFont(new Font("Arial", Font.BOLD, 30)); // กำหนดแบบอักษรและขนาดตัวอักษร
        shop.setHorizontalAlignment(SwingConstants.CENTER); // กำหนดจัดวางแนวนอน
        shop.setForeground(Color.WHITE); // กำหนดสีของตัวอักษร

        // สร้างและกำหนดขนาดของ TextField
        IDField = new JTextField(); // ช่องใส่ข้อมูล ID
        NameField = new JTextField(); // ช่องใส่ข้อมูล Name
        PhonField = new JTextField(); // ช่องใส่ข้อมูล Phone

        // สร้างและกำหนดขนาดของปุ่ม
        btninsert = new JButton("INSERT"); // ปุ่ม INSERT
        btnupdate = new JButton("UPDATE"); // ปุ่ม UPDATE
        btndelete = new JButton("DELETE"); // ปุ่ม DELETE
        btnReport = new JButton("Report"); // ปุ่ม Report
        btnback = new JButton("BACK"); // ปุ่ม BACK
        btnCancel = new JButton("CANCEL"); // ปุ่ม CANCEL
        btninsert.addActionListener(this); // กำหนดการทำงานเมื่อคลิกปุ่ม
        btnupdate.addActionListener(this); // กำหนดการทำงานเมื่อคลิกปุ่ม
        btndelete.addActionListener(this); // กำหนดการทำงานเมื่อคลิกปุ่ม
        btnReport.addActionListener(this); // กำหนดการทำงานเมื่อคลิกปุ่ม
        btnback.addActionListener(this); // กำหนดการทำงานเมื่อคลิกปุ่ม
        btnCancel.addActionListener(this); // กำหนดการทำงานเมื่อคลิกปุ่ม

        // เพิ่ม Label, TextField, และ Button ลงใน Panel
        pn3.add(shop); // เพิ่ม Label ลงใน Panel

        pn.add(new JLabel("                     ID:")); // เพิ่ม Label ลงใน Panel
        pn.add(IDField); // เพิ่ม TextField ลงใน Panel
        pn.add(new JLabel("                     NAME:")); // เพิ่ม Label ลงใน Panel
        pn.add(NameField); // เพิ่ม TextField ลงใน Panel
        pn.add(new JLabel("                     PHONE:")); // เพิ่ม Label ลงใน Panel
        pn.add(PhonField); // เพิ่ม TextField ลงใน Panel
        pn.add(new JLabel("")); // เพิ่ม Label ลงใน Panel
        pn.add(new JLabel("")); // เพิ่ม Label ลงใน Panel
        pn.add(new JLabel("")); // เพิ่ม Label ลงใน Panel
        pn.add(new JLabel("")); // เพิ่ม Label ลงใน Panel
        pn.add(new JLabel("")); // เพิ่ม Label ลงใน Panel
        pn.add(new JLabel("")); // เพิ่ม Label ลงใน Panel

        pn4.add(btninsert); // เพิ่ม Button ลงใน Panel
        pn4.add(btnupdate); // เพิ่ม Button ลงใน Panel
        pn4.add(btndelete); // เพิ่ม Button ลงใน Panel
        pn4.add(btnReport); // เพิ่ม Button ลงใน Panel
        pn4.add(btnCancel); // เพิ่ม Button ลงใน Panel
        pn4.add(btnback); // เพิ่ม Button ลงใน Panel

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(pn, BorderLayout.WEST);
        getContentPane().add(pn2, BorderLayout.EAST);
        getContentPane().add(pn3, BorderLayout.PAGE_START);
        getContentPane().add(pn4, BorderLayout.PAGE_END);

        // สร้าง TextArea และ Table สำหรับแสดงข้อมูล
        dataTextArea = new JTextArea(15, 50); // สร้าง TextArea สำหรับแสดงข้อมูล
        dataTextArea.setFont(new Font("Arial", Font.PLAIN, 14)); // กำหนดรูปแบบและขนาดตัวอักษร
        dataTextArea.setEditable(false); // ไม่สามารถแก้ไขข้อมูลได้
        JScrollPane scrollPane = new JScrollPane(dataTextArea); // เพิ่ม JScrollPane เพื่อให้สามารถเลื่อนข้อมูลได้
        getContentPane().add(scrollPane, BorderLayout.CENTER);

        String[] columns = { "ID", "NAME", "PHONE" }; // ชื่อคอลัมน์ของตาราง
        model = new DefaultTableModel(); // สร้างโมเดลของตาราง
        model.setColumnIdentifiers(columns); // กำหนดคอลัมน์ให้กับโมเดล

        table = new JTable(model); // สร้างตาราง
        table.setPreferredScrollableViewportSize(new Dimension(500, 200)); // กำหนดขนาดของตาราง
        table.setFillsViewportHeight(true); // ตั้งค่าให้ตารางเต็มพื้นที่ที่แสดง
        table.setBackground(new Color(240, 240, 240)); // กำหนดสีพื้นหลังของตาราง

        // เมื่อคลิกที่แถวในตารางจะแสดงข้อมูลใน TextField
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                int selectedRow = table.getSelectedRow();
                IDField.setText(model.getValueAt(selectedRow, 0).toString());
                NameField.setText(model.getValueAt(selectedRow, 1).toString());
                PhonField.setText(model.getValueAt(selectedRow, 2).toString());
            }
        });

        JScrollPane tableScrollPane = new JScrollPane(table); // เพิ่ม JScrollPane เพื่อให้สามารถเลื่อนตารางได้
        pn2.add(tableScrollPane); // เพิ่มตารางลงใน Panel

        loadDataFromDatabase(); // โหลดข้อมูลจากฐานข้อมูล

        // แสดง GUI
        setVisible(true);
    }

    // เมื่อมีการคลิกปุ่มจะดำเนินการตาม Action ที่กำหนด
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btninsert) {
            insertDB(); // เพิ่มข้อมูลลงในฐานข้อมูล
        } else if (e.getSource() == btnupdate) {
            updateDB(); // อัปเดตข้อมูลในฐานข้อมูล
        } else if (e.getSource() == btndelete) {
            deleteDB(); // ลบข้อมูลจากฐานข้อมูล
        } else if (e.getSource() == btnReport) {
            generateReport(); // สร้างรายงานข้อมูลพนักงาน
        } else if (e.getSource() == btnback) {
            // ย้อนกลับไปยังหน้าหลัก
            SHOP_BCC SHOP_BCC = new SHOP_BCC();
            SHOP_BCC.setVisible(true);
            this.dispose(); // ปิดหน้าต่างปัจจุบัน
        } else if (e.getSource() == btnCancel) {
            // เคลียร์ข้อมูลใน TextField เมื่อกดปุ่ม btnCancel
            IDField.setText("");
            NameField.setText("");
            PhonField.setText("");
        }
    }

    // เชื่อมต่อฐานข้อมูล MySQL
    public Connection connectDB() throws SQLException {
        String url = "jdbc:mysql://localhost/project1";
        return DriverManager.getConnection(url, user, pass);
    }

    // โหลดข้อมูลจากฐานข้อมูลมาแสดงในตาราง
    private void loadDataFromDatabase() {
        // กำหนด URL ของฐานข้อมูล MySQL
        String dbURL = "jdbc:mysql://localhost/project1";
        String username = "root"; // ชื่อผู้ใช้ MySQL
        String password = ""; // รหัสผ่าน MySQL

        String sqlQuery = "SELECT Emp_ID, Emp_Name,Emp_Phone FROM employee"; // คำสั่ง SQL สำหรับดึงข้อมูลพนักงาน

        try (Connection connection = DriverManager.getConnection(dbURL, username, password); // เชื่อมต่อฐานข้อมูล
                Statement statement = connection.createStatement(); // สร้าง Statement เพื่อส่งคำสั่ง SQL
                ResultSet resultSet = statement.executeQuery(sqlQuery)) { // ประมวลผลคำสั่ง SQL และรับผลลัพธ์
            // วนลูปผลลัพธ์ที่ได้จากการสอบถาม
            while (resultSet.next()) {
                String id = resultSet.getString("Emp_ID"); // อ่านค่า ID ของพนักงาน
                String name = resultSet.getString("Emp_Name"); // อ่านค่าชื่อของพนักงาน
                String phone = resultSet.getString("Emp_Phone"); // อ่านค่าเบอร์โทรศัพท์ของพนักงาน

                // เพิ่มข้อมูลในแถวใหม่ของตาราง
                model.addRow(new Object[] { id, name, phone });
            }
        } catch (SQLException e) {
            e.printStackTrace(); // แสดงข้อผิดพลาดทาง SQL ถ้าเกิดข้อผิดพลาด
        }
    }

    // เพิ่มข้อมูลลงในฐานข้อมูล
    public void insertDB() {
        try {
            // ดึงข้อมูล ID, ชื่อ, และเบอร์โทรศัพท์จาก TextField และเก็บไว้ในตัวแปร
            String id = IDField.getText();
            String name = NameField.getText();
            String phone = PhonField.getText();

            // กำหนดคำสั่ง SQL เพื่อเพิ่มข้อมูลลงในตาราง employee
            String sql = "insert into employee values (?,?, ?)";

            // เชื่อมต่อกับฐานข้อมูล
            Connection c = connectDB();

            // สร้าง PreparedStatement จาก Connection เพื่อเตรียมคำสั่ง SQL
            PreparedStatement stm = c.prepareStatement(sql);

            // กำหนดค่าข้อมูลให้กับ placeholder ในคำสั่ง SQL
            stm.setString(1, id);
            stm.setString(2, name);
            stm.setString(3, phone);

            // ดำเนินการ Execute Update เพื่อทำการเพิ่มข้อมูลลงในฐานข้อมูล
            stm.executeUpdate();

            // แสดงข้อความแจ้งเตือนว่าการเพิ่มข้อมูลสำเร็จ
            JOptionPane.showMessageDialog(this, "Data inserted successfully.");

            // เพิ่มแถวใหม่ในตารางเพื่อแสดงข้อมูลที่เพิ่มลงในฐานข้อมูล
            model.addRow(new Object[] { id, name, phone });

            // ปิด PreparedStatement และ Connection
            // เพื่อความปลอดภัยและป้องกันการใช้ทรัพยากรที่ไม่จำเป็น
            stm.close();
            c.close();
        } catch (Exception ex) {
            // หากเกิดข้อผิดพลาดในการเพิ่มข้อมูล จะแสดง Stack Trace ของข้อผิดพลาดนั้น
            ex.printStackTrace();
        }
    }

    // อัปเดตข้อมูลในฐานข้อมูล
    public void updateDB() {
        try {
            // ดึงข้อมูล ID, ชื่อ, และเบอร์โทรศัพท์จาก TextField และเก็บไว้ในตัวแปร
            String id = IDField.getText();
            String name = NameField.getText();
            String phone = PhonField.getText();

            // กำหนดคำสั่ง SQL เพื่ออัปเดตข้อมูลในตาราง employee
            // โดยอัปเดตชื่อและเบอร์โทรศัพท์ตาม ID
            String sql = "update employee set Emp_Name = ?, Emp_Phone = ? where Emp_ID = ?";

            // เชื่อมต่อกับฐานข้อมูล
            Connection c = connectDB();

            // สร้าง PreparedStatement จาก Connection เพื่อเตรียมคำสั่ง SQL
            PreparedStatement stm = c.prepareStatement(sql);

            // กำหนดค่าข้อมูลให้กับ placeholder ในคำสั่ง SQL
            stm.setString(1, name);
            stm.setString(2, phone);
            stm.setString(3, id);

            // ดำเนินการ Execute Update เพื่อทำการอัปเดตข้อมูลในฐานข้อมูล
            stm.executeUpdate();

            // แสดงข้อความแจ้งเตือนว่าการอัปเดตข้อมูลสำเร็จ
            JOptionPane.showMessageDialog(this, "Data updated successfully.");

            // อัปเดตข้อมูลในตาราง GUI ด้วยข้อมูลที่อัปเดต
            int rowIndex = table.getSelectedRow();
            model.setValueAt(name, rowIndex, 1);
            model.setValueAt(phone, rowIndex, 2);

            // ปิด PreparedStatement และ Connection
            // เพื่อความปลอดภัยและป้องกันการใช้ทรัพยากรที่ไม่จำเป็น
            stm.close();
            c.close();
        } catch (Exception ex) {
            // หากเกิดข้อผิดพลาดในการอัปเดตข้อมูล จะแสดง Stack Trace ของข้อผิดพลาดนั้น
            ex.printStackTrace();
        }
    }

    // ลบข้อมูลจากฐานข้อมูล
    public void deleteDB() {
        try {
            // รับค่าแถวที่ถูกเลือกจากตาราง
            int selectedRow = table.getSelectedRow();

            // ดึงค่า ID ของแถวที่ถูกเลือกและแปลงเป็น String
            String id = model.getValueAt(selectedRow, 0).toString();

            // กำหนดคำสั่ง SQL เพื่อลบข้อมูลจากตาราง employee โดยใช้ ID เป็นเงื่อนไข
            String sql = "delete from employee where Emp_ID = ?";

            // เชื่อมต่อกับฐานข้อมูล
            Connection c = connectDB();

            // สร้าง PreparedStatement จาก Connection เพื่อเตรียมคำสั่ง SQL
            PreparedStatement stm = c.prepareStatement(sql);

            // กำหนดค่า ID ให้กับ placeholder ในคำสั่ง SQL
            stm.setString(1, id);

            // ดำเนินการ Execute Update เพื่อทำการลบข้อมูลจากฐานข้อมูล
            stm.executeUpdate();

            // แสดงข้อความแจ้งเตือนว่าการลบข้อมูลสำเร็จ
            JOptionPane.showMessageDialog(this, "Data deleted successfully.");

            // ลบแถวที่ถูกเลือกออกจากตาราง GUI
            model.removeRow(selectedRow);

            // ปิด PreparedStatement และ Connection
            // เพื่อความปลอดภัยและป้องกันการใช้ทรัพยากรที่ไม่จำเป็น
            stm.close();
            c.close();
        } catch (Exception ex) {
            // หากเกิดข้อผิดพลาดในการลบข้อมูล จะแสดง Stack Trace ของข้อผิดพลาดนั้น
            ex.printStackTrace();
        }
    }

    // สร้างรายงานข้อมูลพนักงาน
    public void generateReport() {
        try {
            StringBuilder report = new StringBuilder();

            report.append("\n");
            report.append("                                                          SHOP: BCC BICYCLE")
                    .append("\n\n\n");
            report.append("Date " + java.time.LocalDate.now()).append("\n\n");

            report.append(
                    "----------------------------------------------------------------------------------------------------------------------------------------------------------------\n");
            report.append("No.\tID\tName\t.\tPhone\n");

            report.append(
                    "----------------------------------------------------------------------------------------------------------------------------------------------------------------\n");

            int totalIDs = model.getRowCount();

            for (int i = 0; i < model.getRowCount(); i++) {
                report.append((i + 1)).append("\t");

                for (int j = 0; j < model.getColumnCount(); j++) {
                    report.append(model.getValueAt(i, j)).append("\t");
                }
                report.append("\n");
            }

            report.append(
                    "----------------------------------------------------------------------------------------------------------------------------------------------------------------\n");

            report.append("\t\t\t\t\t\t                                                               Total IDs: ")
                    .append(totalIDs).append("\n");

            report.append(
                    "----------------------------------------------------------------------------------------------------------------------------------------------------------------\n");

            dataTextArea.setText(report.toString());

            PrinterJob printerJob = PrinterJob.getPrinterJob();
            if (printerJob.printDialog()) {
                printerJob.setPrintable(dataTextArea.getPrintable(null, null));
                try {
                    printerJob.print();
                } catch (PrinterException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(this, "Error while printing.", "Printing Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }

            JOptionPane.showMessageDialog(this, "Report generated successfully.");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    // สร้าง GUI หลัก
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Employee gui = new Employee();
            gui.setLocationRelativeTo(null);
        });
    }
}
