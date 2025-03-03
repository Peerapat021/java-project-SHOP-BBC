import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.*;

public class JavaDBBasic extends JFrame implements ActionListener {

    // กำหนดข้อมูลสำหรับเชื่อมต่อฐานข้อมูล
    static String db_name = "products"; // ชื่อฐานข้อมูลที่อัปเดต
    static String user = "root";
    static String pass = "";
    static String hostName = "localhost";
    static String db_driver = "com.mysql.cj.jdbc.Driver";

    // ปุ่มใน GUI
    private JButton insertButton, updateButton, deleteButton, backButton; // เพิ่มปุ่มย้อนกลับ
    // กล่องข้อความ
    private JTextArea dataTextArea;
    // ช่องข้อมูล
    private JTextField idField, nameField, priceField, qtyField, dateField; // ชื่อของช่องข้อมูลที่อัปเดต
    // ตาราง
    private JTable table;
    // โมเดลของตาราง
    private DefaultTableModel model;

    // สร้าง GUI
    public JavaDBBasic() {
        setTitle("JavaDBBasic GUI");
        setSize(700, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // สร้างปุ่มและช่องข้อมูล
        JPanel buttonPanel = new JPanel(new GridLayout(7, 2)); // รูปแบบการจัดตำแหน่งในกริดที่อัปเดต
        insertButton = new JButton("Insert data");
        updateButton = new JButton("Update data");
        deleteButton = new JButton("Delete data");
        backButton = new JButton("Back"); // เพิ่มปุ่มย้อนกลับ

        // เพิ่มการตรวจจับเหตุการณ์แต่ละปุ่ม
        insertButton.addActionListener(this);
        updateButton.addActionListener(this);
        deleteButton.addActionListener(this);
        backButton.addActionListener(this); // เพิ่มการตรวจจับเหตุการณ์สำหรับปุ่มย้อนกลับ
        // สร้างช่องข้อมูล
        idField = new JTextField(10);
        nameField = new JTextField(10); // อัปเดตชื่อของช่องข้อมูล
        priceField = new JTextField(10); // อัปเดตชื่อของช่องข้อมูล
        qtyField = new JTextField(10); // อัปเดตชื่อของช่องข้อมูล
        dateField = new JTextField(10); // อัปเดตชื่อของช่องข้อมูล

        // เพิ่มปุ่มและช่องข้อมูลลงในพาแนล
        buttonPanel.add(new JLabel("ID:"));
        buttonPanel.add(idField);
        buttonPanel.add(new JLabel("Name:")); // ป้ายชื่อที่อัปเดต
        buttonPanel.add(nameField);
        buttonPanel.add(new JLabel("Price:")); // ป้ายชื่อที่อัปเดต
        buttonPanel.add(priceField);
        buttonPanel.add(new JLabel("Quantity:")); // ป้ายชื่อที่อัปเดต
        buttonPanel.add(qtyField);
        buttonPanel.add(new JLabel("Date:")); // ป้ายชื่อที่อัปเดต
        buttonPanel.add(dateField);
        buttonPanel.add(insertButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(backButton); // เพิ่มปุ่มย้อนกลับ
    
        // สร้างพื้นที่เพื่อแสดงข้อความ
        dataTextArea = new JTextArea(15, 50);
        JScrollPane scrollPane = new JScrollPane(dataTextArea);

        // กำหนดเลเอาท์ BorderLayout และเพิ่มพาแนลลงในเฟรม
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(buttonPanel, BorderLayout.WEST);
        getContentPane().add(scrollPane, BorderLayout.CENTER);

        // สร้างคอลัมน์
        String[] columns = {"BI_ID", "BI_NAME", "BI_PRICE", "BI_QTY", "BI_DATE"};

        // สร้างโมเดลของตาราง
        model = new DefaultTableModel();
        model.setColumnIdentifiers(columns);

        // สร้างตาราง
        table = new JTable(model);
        table.setPreferredScrollableViewportSize(new Dimension(500, 200));
        table.setFillsViewportHeight(true);

        // เพิ่มตัวตรวจจับเหตุการณ์เมาส์ไปยังตาราง
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                int selectedRow = table.getSelectedRow();
                idField.setText(model.getValueAt(selectedRow, 0).toString());
                nameField.setText(model.getValueAt(selectedRow, 1).toString());
                priceField.setText(model.getValueAt(selectedRow, 2).toString());
                qtyField.setText(model.getValueAt(selectedRow, 3).toString());
                dateField.setText(model.getValueAt(selectedRow, 4).toString());
            }
        });

        // เพิ่มตารางลงใน JScrollPane
        JScrollPane tableScrollPane = new JScrollPane(table);

        // เพิ่ม JScrollPane ลงในเฟรม
        getContentPane().add(tableScrollPane, BorderLayout.CENTER);

        // โหลดข้อมูลจากฐานข้อมูล
        loadDataFromDatabase();
    }

    // โหลดข้อมูลจากฐานข้อมูล
    private void loadDataFromDatabase() {
        // พารามิเตอร์สำหรับการเชื่อมต่อ JDBC
        String dbURL = "jdbc:mysql://localhost/products";
        String username = "root";
        String password = "";

        // คำสั่ง SQL เพื่อดึงข้อมูลทั้งหมดจากตาราง bicycle
        String sqlQuery = "SELECT BI_ID, BI_NAME, BI_PRICE, BI_QTY, BI_DATE FROM bicycle";

        try (
                // เชื่อมต่อฐานข้อมูล
                Connection connection = DriverManager.getConnection(dbURL, username, password);

                // สร้างออบเจ็กต์สำหรับสร้างคำสั่ง SQL
                Statement statement = connection.createStatement();

                // ประมวลผลคำสั่ง SQL และรับผลลัพธ์
                ResultSet resultSet = statement.executeQuery(sqlQuery)
        ) {
            // วนลูปผ่านผลลัพธ์และเพิ่มแต่ละแถวลงในโมเดลของตาราง
            while (resultSet.next()) {
                String id = resultSet.getString("BI_ID");
                String name = resultSet.getString("BI_NAME");
                String price = resultSet.getString("BI_PRICE");
                String qty = resultSet.getString("BI_QTY");
                String date = resultSet.getString("BI_DATE");

                model.addRow(new Object[]{id, name, price, qty, date});
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // จัดการเหตุการณ์ที่เกิดขึ้นเมื่อกดปุ่ม
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == insertButton) {
            insertDB();
        } else if (e.getSource() == updateButton) {
            updateDB();
        } else if (e.getSource() == deleteButton) {
            deleteDB();
        } else if (e.getSource() == backButton) { // เพิ่มเงื่อนไขสำหรับปุ่มย้อนกลับ
            HomeGUI homeGUI = new HomeGUI();
            homeGUI.setVisible(true);
            this.dispose(); // ปิด JavaDBBasic GUI
        }
    }

    // เชื่อมต่อกับฐานข้อมูล
    public Connection connectDB() throws SQLException {
        String url = "jdbc:mysql://localhost/products";
        return DriverManager.getConnection(url, user, pass);
    }

    // เพิ่มข้อมูลลงในฐานข้อมูล
    public void insertDB() {
        try {
            String id = idField.getText();
            String name = nameField.getText();
            String price = priceField.getText();
            String qty = qtyField.getText();
            String date = dateField.getText();

            String sql = "insert into bicycle values (?, ?, ?, ?, ?)"; // ชื่อตารางที่อัปเดต
            Connection c = connectDB();
            PreparedStatement stm = c.prepareStatement(sql);
            stm.setString(1, id);
            stm.setString(2, name);
            stm.setString(3, price);
            stm.setString(4, qty);
            stm.setString(5, date);
            stm.executeUpdate();
            JOptionPane.showMessageDialog(this, "Data inserted successfully.");

            // เพิ่มแถวใหม่ลงในโมเดลของตาราง
            model.addRow(new Object[]{id, name, price, qty, date});

            stm.close();
            c.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    // อัปเดตข้อมูลในฐานข้อมูล
    public void updateDB() {
        try {
            String id = idField.getText();
            String name = nameField.getText();
            String price = priceField.getText();
            String qty = qtyField.getText();
            String date = dateField.getText();

            String sql = "update bicycle set BI_NAME = ?, BI_PRICE = ?, BI_QTY = ?, BI_DATE = ? where BI_ID = ?"; // ชื่อ
            // ตาราง
            // และ
            // ชื่อ
            // คอลัมน์ที่
            // อัปเดต
            Connection c = connectDB();
            PreparedStatement stm = c.prepareStatement(sql);
            stm.setString(1, name);
            stm.setString(2, price);
            stm.setString(3, qty);
            stm.setString(4, date);
            stm.setString(5, id);
            stm.executeUpdate();
            JOptionPane.showMessageDialog(this, "Data updated successfully.");

            // อัปเดตแถวที่เกี่ยวข้องในโมเดลของตาราง
            int rowIndex = table.getSelectedRow();
            model.setValueAt(name, rowIndex, 1);
            model.setValueAt(price, rowIndex, 2);
            model.setValueAt(qty, rowIndex, 3);
            model.setValueAt(date, rowIndex, 4);

            stm.close();
            c.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    // ลบข้อมูลจากฐานข้อมูล
    public void deleteDB() {
        try {
            int selectedRow = table.getSelectedRow();
            String id = model.getValueAt(selectedRow, 0).toString();

            String sql = "delete from bicycle where BI_ID = ?"; // ชื่อตารางและชื่อคอลัมน์ที่อัปเดต
            Connection c = connectDB();
            PreparedStatement stm = c.prepareStatement(sql);
            stm.setString(1, id);
            stm.executeUpdate();
            JOptionPane.showMessageDialog(this, "Data deleted successfully.");

            // ลบแถวที่เกี่ยวข้องออกจากโมเดลของตาราง
            model.removeRow(selectedRow);

            stm.close();
            c.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    // เมธอด main เริ่มต้นโปรแกรม
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JavaDBBasic gui = new JavaDBBasic();
            gui.setVisible(true);
        });
    }
}
