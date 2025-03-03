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

// สร้างคลาส Type_product ซึ่งเป็นฟอร์ม GUI สำหรับการจัดการข้อมูลประเภทสินค้า
public class Type_product extends JFrame implements ActionListener {
    // กำหนดข้อมูลสำหรับการเชื่อมต่อฐานข้อมูล
    static String db_name = "product1";
    static String user = "root";
    static String pass = "";
    static String hostname = "localhost";
    static String db_driver = "com.mysql.cj.jdbc.Driver";

    // ประกาศตัวแปร UI
    private JTextField IDField, NameField;
    private JButton btninsert, btnupdate, btndelete, btnReport, btnback, btnCancel;
    private JTextArea dataTextArea;
    private JTable table;
    private DefaultTableModel model;

    // Constructor สำหรับสร้าง UI
    public Type_product() {
        setTitle("Type_product Management System");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1100, 600);

        // สร้าง Panel สำหรับจัดวางองค์ประกอบ
        JPanel pn = new JPanel(new GridLayout(7, 2));
        pn.setPreferredSize(new Dimension(335, 600));

        JPanel pn2 = new JPanel(new GridLayout(1, 1));
        pn2.setPreferredSize(new Dimension(750, 600));

        JPanel pn3 = new JPanel(new GridLayout(1, 1));
        pn3.setPreferredSize(new Dimension(1000, 70));
        pn3.setBackground(new Color(54, 100, 139));

        JPanel pn4 = new JPanel(new GridLayout(1, 5));
        pn4.setPreferredSize(new Dimension(1000, 50));
        pn4.setBackground(new Color(200, 200, 200));

        // สร้าง Label สำหรับชื่อเรื่อง
        JLabel shop = new JLabel("TYPE PRODUCT");
        shop.setFont(new Font("Arial", Font.BOLD, 30));
        shop.setHorizontalAlignment(SwingConstants.CENTER);
        shop.setForeground(Color.WHITE);

        // สร้าง TextField สำหรับรับข้อมูล ID และชื่อประเภทสินค้า
        IDField = new JTextField();
        NameField = new JTextField();

        // สร้าง Button สำหรับการดำเนินการเพิ่ม อัปเดต ลบ และสร้างรายงาน
        btninsert = new JButton("INSERT");
        btnupdate = new JButton("UPDATE");
        btndelete = new JButton("DELETE");
        btnReport = new JButton("Report");
        btnback = new JButton("BACK");
        btnCancel = new JButton("CANCEL");
        btninsert.addActionListener(this);
        btnupdate.addActionListener(this);
        btndelete.addActionListener(this);
        btnReport.addActionListener(this);
        btnback.addActionListener(this);
        btnCancel.addActionListener(this);

        // เพิ่ม Label, TextField, และ Button ลงใน Panel
        pn3.add(shop);

        pn.add(new JLabel("                     ID:"));
        pn.add(IDField);
        pn.add(new JLabel("                     NAME:"));
        pn.add(NameField);
        pn.add(new JLabel(""));
        pn.add(new JLabel(""));
        pn.add(new JLabel(""));
        pn.add(new JLabel(""));
        pn.add(new JLabel(""));
        pn.add(new JLabel(""));
        pn.add(new JLabel(""));
        pn.add(new JLabel(""));

        pn4.add(btninsert);
        pn4.add(btnupdate);
        pn4.add(btndelete);
        pn4.add(btnReport);
        pn4.add(btnCancel);
        pn4.add(btnback);

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(pn, BorderLayout.WEST);
        getContentPane().add(pn2, BorderLayout.EAST);
        getContentPane().add(pn3, BorderLayout.PAGE_START);
        getContentPane().add(pn4, BorderLayout.PAGE_END);

        // สร้าง TextArea สำหรับแสดงรายละเอียดสินค้า
        dataTextArea = new JTextArea(15, 50);
        dataTextArea.setFont(new Font("Arial", Font.PLAIN, 14));
        dataTextArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(dataTextArea);
        getContentPane().add(scrollPane, BorderLayout.CENTER);

        // กำหนดคอลัมน์สำหรับตาราง
        String[] columns = { "ID", "NAME" };
        model = new DefaultTableModel();
        model.setColumnIdentifiers(columns);

        // สร้างตารางและตั้งค่าสำหรับการเลื่อนและการเติมข้อมูล
        table = new JTable(model);
        table.setPreferredScrollableViewportSize(new Dimension(500, 200));
        table.setFillsViewportHeight(true);
        table.setBackground(new Color(240, 240, 240));

        // เมื่อคลิกที่แถวในตารางจะแสดงข้อมูลใน TextField
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                int selectedRow = table.getSelectedRow();
                IDField.setText(model.getValueAt(selectedRow, 0).toString());
                NameField.setText(model.getValueAt(selectedRow, 1).toString());
            }
        });

        JScrollPane tableScrollPane = new JScrollPane(table);
        pn2.add(tableScrollPane);

        // โหลดข้อมูลจากฐานข้อมูลเมื่อเริ่มต้นโปรแกรม
        loadDataFromDatabase();
    }

    // เมธอดสำหรับดึงข้อมูลจากฐานข้อมูลและแสดงใน JTable
    private void loadDataFromDatabase() {
        String dbURL = "jdbc:mysql://localhost/project1";
        String username = "root";
        String password = "";

        String sqlQuery = "SELECT T_ID, T_NAME FROM typebcc";

        try (Connection connection = DriverManager.getConnection(dbURL, username, password);
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(sqlQuery)) {
            while (resultSet.next()) {
                String id = resultSet.getString("T_ID");
                String name = resultSet.getString("T_NAME");

                model.addRow(new Object[] { id, name });
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // เมธอดสำหรับจัดการเหตุการณ์การคลิกปุ่ม
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btninsert) {
            insertDB();
        } else if (e.getSource() == btnupdate) {
            updateDB();
        } else if (e.getSource() == btndelete) {
            deleteDB();
        } else if (e.getSource() == btnReport) {
            generateReport();
        } else if (e.getSource() == btnback) {
            SHOP_BCC SHOP_BCC = new SHOP_BCC();
            SHOP_BCC.setVisible(true);
            this.dispose();
        } else if (e.getSource() == btnCancel) {
            IDField.setText("");
            NameField.setText("");
        }
    }

    // เมธอดสำหรับเชื่อมต่อกับฐานข้อมูล MySQL
    public Connection connectDB() throws SQLException {
        String url = "jdbc:mysql://localhost/project1";
        return DriverManager.getConnection(url, user, pass);
    }

    // เมธอดสำหรับเพิ่มข้อมูลลงในฐานข้อมูล
    public void insertDB() {
        try {
            String id = IDField.getText();
            String name = NameField.getText();

            String sql = "insert into typebcc values (?, ?)";
            Connection c = connectDB();
            PreparedStatement stm = c.prepareStatement(sql);
            stm.setString(1, id);
            stm.setString(2, name);
            stm.executeUpdate();
            JOptionPane.showMessageDialog(this, "Data inserted successfully.");

            model.addRow(new Object[] { id, name });

            stm.close();
            c.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    // เมธอดสำหรับอัปเดตข้อมูลในฐานข้อมูล
    public void updateDB() {
        try {
            String id = IDField.getText();
            String name = NameField.getText();

            String sql = "update typebcc set T_NAME = ? where T_ID = ?";
            Connection c = connectDB();
            PreparedStatement stm = c.prepareStatement(sql);
            stm.setString(1, name);
            stm.setString(2, id);
            stm.executeUpdate();
            JOptionPane.showMessageDialog(this, "Data updated successfully.");

            int rowIndex = table.getSelectedRow();
            model.setValueAt(name, rowIndex, 1);

            stm.close();
            c.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    // เมธอดสำหรับลบข้อมูลในฐานข้อมูล
    public void deleteDB() {
        try {
            int selectedRow = table.getSelectedRow();
            String id = model.getValueAt(selectedRow, 0).toString();

            String sql = "delete from typebcc where T_ID = ?";
            Connection c = connectDB();
            PreparedStatement stm = c.prepareStatement(sql);
            stm.setString(1, id);
            stm.executeUpdate();
            JOptionPane.showMessageDialog(this, "Data deleted successfully.");

            model.removeRow(selectedRow);

            stm.close();
            c.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    // เมธอดสำหรับสร้างรายงาน
    public void generateReport() {
        try {
            StringBuilder report = new StringBuilder();

            report.append("\n");
            report.append("                                                          SHOP: BCC BICYCLE")
                    .append("\n\n\n");
            report.append("Date " + java.time.LocalDate.now()).append("\n\n");

            report.append(
                    "----------------------------------------------------------------------------------------------------------------------------------------------------------------\n");
            report.append("No.\tID\tName\n");

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

    // เมธอด main เริ่มต้นโปรแกรม
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Type_product gui = new Type_product();
            gui.setLocationRelativeTo(null);
            gui.setVisible(true);
        });
    }
}
