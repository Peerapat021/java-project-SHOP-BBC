import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.*;
import java.time.LocalDate;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;

// สร้างคลาส Bicycle ซึ่งเป็นหน้าต่าง JFrame และ implements ActionListener เพื่อรองรับการกระทำจากปุ่ม
public class Bicycle extends JFrame implements ActionListener {
    static String db_name = "product1"; // ชื่อฐานข้อมูล
    static String user = "root"; // ชื่อผู้ใช้ฐานข้อมูล
    static String pass = ""; // รหัสผ่านฐานข้อมูล
    static String hostname = "localhost"; // โฮสต์ฐานข้อมูล
    static String db_driver = "com.mysql.cj.jdbc.Driver"; // ไดรเวอร์ฐานข้อมูล

    // กำหนดฟิลด์สำหรับจัดเก็บ JTextField และ JButton
    private JTextField IDField, NameField, PriceField, qtyField, DateField, typefField;
    private JButton btninsert, btnupdate, btndelete, btnReport, btnback, btnCancel; // เพิ่มปุ่ม btnCancel
    // กล่องข้อความ
    private JTextArea dataTextArea;
    // ตาราง
    private JTable table;
    // โมเดลของตาราง
    private DefaultTableModel model;

    // สร้างเมธอดคอนสตรักเตอร์
    public Bicycle() {
        // กำหนดค่าสำหรับ JFrame
        setTitle("Bicycle Management System"); // ชื่อเฟรม
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // ปิดการทำงานของเฟรมเมื่อปิด
        setSize(1100, 600); // ขนาดของเฟรม

        setResizable(false); // ไม่สามารถปรับขนาดเฟรมได้

        // สร้างแผง (Panel) สำหรับจัดวางส่วนหน้าจอซ้าย
        JPanel pn = new JPanel(new GridLayout(7, 2)); // แบ่งเป็น 7 แถว 2 คอลัมน์
        pn.setPreferredSize(new Dimension(335, 600)); // กำหนดขนาดของแผง

        // สร้างแผง (Panel) สำหรับจัดวางส่วนหน้าจอขวา
        JPanel pn2 = new JPanel(new GridLayout(1, 1)); // แบ่งเป็น 1 แถว 1 คอลัมน์
        pn2.setPreferredSize(new Dimension(750, 600)); // กำหนดขนาดของแผง

        // สร้างแผง (Panel) สำหรับส่วนหัว
        JPanel pn3 = new JPanel(new GridLayout(1, 1)); // แบ่งเป็น 1 แถว 1 คอลัมน์
        pn3.setPreferredSize(new Dimension(1000, 70)); // กำหนดขนาดของแผง
        pn3.setBackground(new Color(54, 100, 139)); // กำหนดสีพื้นหลัง

        // สร้างแผง (Panel) สำหรับปุ่ม
        JPanel pn4 = new JPanel(new GridLayout(1, 5)); // แบ่งเป็น 1 แถว 5 คอลัมน์
        pn4.setPreferredSize(new Dimension(1000, 50)); // กำหนดขนาดของแผง
        pn4.setBackground(new Color(200, 200, 200)); // กำหนดสีพื้นหลัง

        // สร้างป้ายชื่อร้านสำหรับส่วนหัว
        JLabel shop = new JLabel("PRODUCT"); // ป้ายชื่อร้าน
        shop.setFont(new Font("Arial", Font.BOLD, 30)); // กำหนดแบบอักษร ขนาด 30
        shop.setHorizontalAlignment(SwingConstants.CENTER); // ตำแหน่งตรงกลาง
        shop.setForeground(Color.WHITE); // สีข้อความ

        // สร้าง JTextField สำหรับใส่ข้อมูล ID, Name, Price, Quantity, Date, และ Type
        IDField = new JTextField(); // ใส่ ID
        NameField = new JTextField(); // ใส่ชื่อ
        PriceField = new JTextField(); // ใส่ราคา
        qtyField = new JTextField(); // ใส่จำนวน
        DateField = new JTextField(LocalDate.now().toString()); // ใส่วันที่
        typefField = new JTextField(); // ใส่ประเภท

        // สร้างปุ่ม Insert, Update, Delete, Report, Back, และ Cancel
        btninsert = new JButton("INSERT"); // ปุ่ม Insert
        btnupdate = new JButton("UPDATE"); // ปุ่ม Update
        btndelete = new JButton("DELETE"); // ปุ่ม Delete
        btnReport = new JButton("Report"); // ปุ่ม Report
        btnback = new JButton("BACK"); // ปุ่ม Back
        btnCancel = new JButton("CANCEL"); // ปุ่ม Cancel
        // เพิ่ม ActionListener ให้กับปุ่ม
        btninsert.addActionListener(this);
        btnupdate.addActionListener(this);
        btndelete.addActionListener(this);
        btnReport.addActionListener(this);
        btnback.addActionListener(this);
        btnCancel.addActionListener(this); // เพิ่ม ActionListener ให้ปุ่ม btnCancel

        // เพิ่มป้ายชื่อร้านลงในแผง pn3
        pn3.add(shop);

        // เพิ่มป้ายชื่อและช่องใส่ข้อมูลลงในแผง pn
        pn.add(new JLabel("                     ID:")); // ป้ายชื่อ ID
        pn.add(IDField); // ช่องใส่ข้อมูล ID
        pn.add(new JLabel("                     NAME:")); // ป้ายชื่อ Name
        pn.add(NameField); // ช่องใส่ข้อมูล Name
        pn.add(new JLabel("                     PRICE:")); // ป้ายชื่อ Price
        pn.add(PriceField); // ช่องใส่ข้อมูล Price
        pn.add(new JLabel("                     QTY:")); // ป้ายชื่อ Quantity
        pn.add(qtyField); // ช่องใส่ข้อมูล Quantity
        pn.add(new JLabel("                     DATE:")); // ป้ายชื่อ Date
        pn.add(DateField); // ช่องใส่ข้อมูล Date
        pn.add(new JLabel("                     TYPE:")); // ป้ายชื่อ Type
        pn.add(typefField); // ช่องใส่ข้อมูล Type

        // เพิ่มปุ่มลงในแผง pn4
        pn4.add(btninsert); // ปุ่ม Insert
        pn4.add(btnupdate); // ปุ่ม Update
        pn4.add(btndelete); // ปุ่ม Delete
        pn4.add(btnReport); // ปุ่ม Report
        pn4.add(btnCancel); // ปุ่ม Cancel
        pn4.add(btnback); // ปุ่ม Back

        // กำหนดเค้าโครงการางและเพิ่มแผงในเค้าโครงราง
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(pn, BorderLayout.WEST); // เพิ่มแผง pn ด้านซ้าย
        getContentPane().add(pn2, BorderLayout.EAST); // เพิ่มแผง pn2 ด้านขวา
        getContentPane().add(pn3, BorderLayout.PAGE_START); // เพิ่มแผง pn3 ด้านบน
        getContentPane().add(pn4, BorderLayout.PAGE_END); // เพิ่มแผง pn4 ด้านล่าง

        // สร้าง JTextArea สำหรับแสดงข้อมูล
        dataTextArea = new JTextArea(15, 50); // กำหนดขนาด JTextArea
        dataTextArea.setFont(new Font("Arial", Font.PLAIN, 14)); // กำหนดแบบอักษรและขนาด
        dataTextArea.setEditable(false); // ไม่สามารถแก้ไขข้อมูลได้
        JScrollPane scrollPane = new JScrollPane(dataTextArea); // เพิ่มแถบเลื่อน
        getContentPane().add(scrollPane, BorderLayout.CENTER); // เพิ่ม JTextArea ในตำแหน่งกลาง

        // กำหนดคอลัมน์ในตาราง
        String[] columns = { "ID", "NAME", "PRICE", "QTY", "DATE", "TYPE" };
        model = new DefaultTableModel();
        model.setColumnIdentifiers(columns);

        table = new JTable(model); // สร้างตาราง
        table.setPreferredScrollableViewportSize(new Dimension(500, 200)); // กำหนดขนาดตาราง
        table.setFillsViewportHeight(true); // เติมความสูง
        table.setBackground(new Color(240, 240, 240)); // กำหนดสีพื้นหลัง

        // เพิ่มตารางใน JScrollPane
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                int selectedRow = table.getSelectedRow();
                IDField.setText(model.getValueAt(selectedRow, 0).toString());
                NameField.setText(model.getValueAt(selectedRow, 1).toString());
                PriceField.setText(model.getValueAt(selectedRow, 2).toString());
                qtyField.setText(model.getValueAt(selectedRow, 3).toString());
                DateField.setText(model.getValueAt(selectedRow, 4).toString());
                typefField.setText(model.getValueAt(selectedRow, 5).toString());
            }
        });

        JScrollPane tableScrollPane = new JScrollPane(table);
        pn2.add(tableScrollPane); // เพิ่ม JScrollPane ที่มีตาราง

        loadDataFromDatabase(); // เรียกเมธอดเพื่อโหลดข้อมูลจากฐานข้อมูล
    }

    // เมธอดสำหรับโหลดข้อมูลจากฐานข้อมูล
    private void loadDataFromDatabase() {
        String dbURL = "jdbc:mysql://localhost/project1"; // URL ของฐานข้อมูล
        String username = "root"; // ชื่อผู้ใช้
        String password = ""; // รหัสผ่าน

        String sqlQuery = "SELECT B_ID, B_NAME, B_PRICE, B_QTY, B_DATE, T_ID FROM bicycle"; // คำสั่ง SQL

        try (Connection connection = DriverManager.getConnection(dbURL, username, password);
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(sqlQuery)) {
            while (resultSet.next()) {
                String id = resultSet.getString("B_ID"); // รับข้อมูล ID
                String name = resultSet.getString("B_NAME"); // รับข้อมูลชื่อ
                String price = resultSet.getString("B_PRICE"); // รับข้อมูลราคา
                String qty = resultSet.getString("B_QTY"); // รับข้อมูลจำนวน
                String date = resultSet.getString("B_DATE"); // รับข้อมูลวันที่
                String typepro = resultSet.getString("T_ID"); // รับข้อมูลประเภท

                model.addRow(new Object[] { id, name, price, qty, date, typepro }); // เพิ่มข้อมูลลงในตาราง
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // ตรวจสอบว่าปุ่มใดถูกคลิก
        if (e.getSource() == btninsert) { // ถ้าเป็นปุ่ม Insert
            insertDB(); // เรียกเมธอดเพื่อเพิ่มข้อมูล
        } else if (e.getSource() == btnupdate) { // ถ้าเป็นปุ่ม Update
            updateDB(); // เรียกเมธอดเพื่ออัพเดตข้อมูล
        } else if (e.getSource() == btndelete) { // ถ้าเป็นปุ่ม Delete
            deleteDB(); // เรียกเมธอดเพื่อลบข้อมูล
        } else if (e.getSource() == btnReport) { // ถ้าเป็นปุ่ม Report
            generateReport(); // เรียกเมธอดเพื่อสร้างรายงาน
        } else if (e.getSource() == btnback) { // ถ้าเป็นปุ่ม Back
            SHOP_BCC SHOP_BCC = new SHOP_BCC(); // สร้างอินสแตนซ์ของ SHOP_BCC
            SHOP_BCC.setVisible(true); // ทำให้เป็น visible
            this.dispose(); // ปิดหน้าต่างปัจจุบัน
        } else if (e.getSource() == btnCancel) { // ถ้าเป็นปุ่ม Cancel
            // เคลียร์ข้อมูลใน JTextField เมื่อกดปุ่ม btnCancel
            IDField.setText("");
            NameField.setText("");
            PriceField.setText("");
            qtyField.setText("");
            DateField.setText(LocalDate.now().toString());
            typefField.setText("");
        }
    }

    // เมธอดสำหรับเชื่อมต่อฐานข้อมูล
    public Connection connectDB() throws SQLException {
        String url = "jdbc:mysql://localhost/project1"; // URL ของฐานข้อมูล
        return DriverManager.getConnection(url, user, pass); // เชื่อมต่อและส่งคืน Connection
    }

    // เมธอดสำหรับเพิ่มข้อมูลลงในฐานข้อมูล
    public void insertDB() {
        try {
            String id = IDField.getText();
            String name = NameField.getText();
            String price = PriceField.getText();
            String qty = qtyField.getText();
            String date = DateField.getText();
            String typepro = typefField.getText();

            String sql = "insert into bicycle values (?, ?, ?, ?, ?, ?)"; // คำสั่ง SQL
            Connection c = connectDB(); // เชื่อมต่อฐานข้อมูล
            PreparedStatement stm = c.prepareStatement(sql); // เตรียมคำสั่ง SQL
            stm.setString(1, id);
            stm.setString(2, name);
            stm.setString(3, price);
            stm.setString(4, qty);
            stm.setString(5, date);
            stm.setString(6, typepro);
            stm.executeUpdate(); // ปรับปรุงข้อมูล
            JOptionPane.showMessageDialog(this, "Data inserted successfully."); // แสดงข้อความแจ้งเตือน

            model.addRow(new Object[] { id, name, price, qty, date, typepro }); // เพิ่มข้อมูลลงในตาราง

            stm.close(); // ปิด Statement
            c.close(); // ปิด Connection
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    // เมธอดสำหรับอัพเดตข้อมูลในฐานข้อมูล
    public void updateDB() {
        try {
            String id = IDField.getText();
            String name = NameField.getText();
            String price = PriceField.getText();
            String qty = qtyField.getText();
            String date = DateField.getText();
            String typepro = typefField.getText();

            String sql = "update bicycle set B_NAME = ?, B_PRICE = ?, B_QTY = ?, B_DATE = ?,T_ID = ? where B_ID = ?";
            Connection c = connectDB();
            PreparedStatement stm = c.prepareStatement(sql);
            stm.setString(1, name);
            stm.setString(2, price);
            stm.setString(3, qty);
            stm.setString(4, date);
            stm.setString(5, typepro);
            stm.setString(6, id);
            stm.executeUpdate(); // ปรับปรุงข้อมูล
            JOptionPane.showMessageDialog(this, "Data updated successfully."); // แสดงข้อความแจ้งเตือน

            int rowIndex = table.getSelectedRow(); // รับเลขแถวที่เลือกจากตาราง
            model.setValueAt(name, rowIndex, 1); // กำหนดค่าชื่อใหม่ในแถวและคอลัมน์ที่เลือก
            model.setValueAt(price, rowIndex, 2); // กำหนดค่าราคาใหม่ในแถวและคอลัมน์ที่เลือก
            model.setValueAt(qty, rowIndex, 3); // กำหนดค่าจำนวนใหม่ในแถวและคอลัมน์ที่เลือก
            model.setValueAt(date, rowIndex, 4); // กำหนดค่าวันที่ใหม่ในแถวและคอลัมน์ที่เลือก
            model.setValueAt(typepro, rowIndex, 5); // กำหนดค่าประเภทใหม่ในแถวและคอลัมน์ที่เลือก            

            stm.close(); // ปิด Statement
            c.close(); // ปิด Connection
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    // เมธอดสำหรับลบข้อมูลในฐานข้อมูล
    public void deleteDB() {
        try {
            int selectedRow = table.getSelectedRow();
            String id = model.getValueAt(selectedRow, 0).toString();

            String sql = "delete from bicycle where B_ID = ?";
            Connection c = connectDB();
            PreparedStatement stm = c.prepareStatement(sql);
            stm.setString(1, id);
            stm.executeUpdate(); // ปรับปรุงข้อมูล
            JOptionPane.showMessageDialog(this, "Data deleted successfully."); // แสดงข้อความแจ้งเตือน

            model.removeRow(selectedRow); // ลบแถวในตาราง

            stm.close(); // ปิด Statement
            c.close(); // ปิด Connection
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    // เมธอดสำหรับสร้างรายงาน
    public void generateReport() {
        try {
            // สร้าง StringBuilder เพื่อเก็บข้อมูลรายงาน
            StringBuilder report = new StringBuilder();

            // เพิ่มชื่อร้านและวันที่
            report.append("\n");
            report.append("                                                          SHOP: BCC BICYCLE")
                    .append("\n\n\n");
            report.append("Date " + java.time.LocalDate.now()).append("\n\n");

            report.append(
                    "----------------------------------------------------------------------------------------------------------------------------------------------------------------\n");
            // เพิ่มหัวข้อคอลัมน์
            report.append("No.\tID\tName\tPrice\tQTY\tDate\tType\n");

            // เพิ่มเส้นกั้นระหว่างชื่อร้านกับหัวข้อคอลัมน์
            report.append(
                    "----------------------------------------------------------------------------------------------------------------------------------------------------------------\n");

            // กำหนดตัวนับรวม ID ทั้งหมด
            int totalIDs = model.getRowCount();

            // วนลูปเพื่อดึงข้อมูลจากตารางและเพิ่มลงในรายงาน
            for (int i = 0; i < model.getRowCount(); i++) {
                report.append((i + 1)).append("\t"); // เพิ่มเลขที่รายการ

                // เพิ่มข้อมูลจากแต่ละคอลัมน์
                for (int j = 0; j < model.getColumnCount(); j++) {
                    report.append(model.getValueAt(i, j)).append("\t");
                }
                report.append("\n"); // เพิ่มเส้นใหม่เมื่อเสร็จแถวนั้น
            }

            report.append(
                    "----------------------------------------------------------------------------------------------------------------------------------------------------------------\n");

            // เพิ่มรายการ ID ทั้งหมด
            report.append("\t\t\t\t\t\t                                                               Total IDs: ").append(totalIDs).append("\n");

            report.append(
                "----------------------------------------------------------------------------------------------------------------------------------------------------------------\n");

            // แสดงรายงานใน JTextArea
            dataTextArea.setText(report.toString());

            // สร้าง PrinterJob
            PrinterJob printerJob = PrinterJob.getPrinterJob();
            if (printerJob.printDialog()) {
                printerJob.setPrintable(dataTextArea.getPrintable(null, null)); // ตั้ง JTextArea เป็นสิ่งที่พิมพ์ได้

                // พิมพ์เอกสาร
                try {
                    printerJob.print();
                } catch (PrinterException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(this, "Error while printing.", "Printing Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }

            // แสดงข้อความว่าสร้างรายงานเรียบร้อยแล้ว
            JOptionPane.showMessageDialog(this, "Report generated successfully.");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    // เมธอด main เพื่อรันแอปพลิเคชัน
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Bicycle gui = new Bicycle();
            gui.setLocationRelativeTo(null); // กำหนดตำแหน่ง
            gui.setVisible(true); // ทำให้เป็น visible
        });
    }
}
