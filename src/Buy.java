import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.*;
import javax.swing.border.Border;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;

public class Buy extends JFrame {

    private DefaultTableModel tableModel;
    private JTextArea dataTextArea; // เพิ่ม JTextArea สำหรับการแสดงรายงาน
    private int selectedProductsCount = 0; // ตัวแปรเก็บจำนวนสินค้าที่เลือกไว้
    private Set<String> selectedProductsSet = new HashSet<>(); // เซ็ตสำหรับเก็บสินค้าที่เลือกไว้

    private JTextField totalsum, returnmony, munnypeople, idbuy, datebuy;
    private JComboBox<String> Emp_ID; // ใช้ JComboBox สำหรับการเลือก ID ของพนักงาน
    private JLabel lbsum, lbshob;
    private JButton btnsave, btncale, btnreport;

    private double totalSumValue = 0.0; // ตัวแปรเก็บรวมราคาของสินค้าทั้งหมด

    public Buy() {
        setTitle("Buy ");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);


        JPanel pn1 = new JPanel(new GridBagLayout());
        pn1.setPreferredSize(new Dimension(1000, 100));
        pn1.setBackground(new Color(54, 100, 139));

        JLabel shopLabel = new JLabel("BCC BICYCLE");
        shopLabel.setForeground(Color.WHITE);
        shopLabel.setFont(new Font("Arial", Font.BOLD, 24));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.insets = new Insets(10, 0, 10, 0); // เพิ่มการเว้นระยะห่าง
        pn1.add(shopLabel, gbc);

        JPanel pn2 = new JPanel(new BorderLayout());
        pn2.setPreferredSize(new Dimension(400, 100));
        Border border = BorderFactory.createLineBorder(Color.BLACK);
        pn2.setBorder(border);

        JLabel employeeLabel = new JLabel("List Product");
        employeeLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel countLabel = new JLabel("Selected Products: " + selectedProductsCount);
        countLabel.setHorizontalAlignment(SwingConstants.CENTER);
        pn2.add(employeeLabel, BorderLayout.NORTH);
        pn2.add(countLabel, BorderLayout.CENTER);

        JPanel pn3 = new JPanel();
        pn3.setPreferredSize(new Dimension(1000, 250));
        pn3.setBackground(new Color(54, 100, 139));
        pn3.setLayout(new FlowLayout(FlowLayout.RIGHT)); // กำหนดเลย์เอาท์เป็น FlowLayout และจัดวางไปทางขวาthe right

        lbsum = new JLabel();
        lbsum.setPreferredSize(new Dimension(390, 180));
        lbsum.setLayout(new GridLayout(5, 2));
        lbsum.setBorder(border);

        lbshob = new JLabel();
        lbshob.setPreferredSize(new Dimension(500, 180));
        lbshob.setLayout(new GridLayout(4, 2));

        idbuy = new JTextField();
        idbuy.setFont(new Font("Arial", Font.BOLD, 16));
        Emp_ID = new JComboBox<>(); // ใช้ JComboBox สำหรับการเลือก ID ของพนักงาน
        Emp_ID.setFont(new Font("Arial", Font.BOLD, 16));
        populateEmployeeIDs();
        datebuy = new JTextField(LocalDate.now().toString());
        datebuy.setFont(new Font("Arial", Font.BOLD, 16));
        btnsave = new JButton("Save");
        btnsave.setFont(new Font("Arial", Font.BOLD, 16));

        btncale = new JButton("Total");
        btncale.setFont(new Font("Arial", Font.BOLD, 16));
        btnreport = new JButton("Report");
        btnreport.setFont(new Font("Arial", Font.BOLD, 16));
        totalsum = new JTextField();
        totalsum.setFont(new Font("Arial", Font.BOLD, 16));
        returnmony = new JTextField();
        returnmony.setFont(new Font("Arial", Font.BOLD, 16));
        munnypeople = new JTextField();
        munnypeople.setFont(new Font("Arial", Font.BOLD, 16));

        JLabel lbid = new JLabel("                           ID");
        lbid.setForeground(Color.WHITE);
        lbid.setFont(new Font("Arial", Font.BOLD, 16));
        JLabel lbemp = new JLabel("                           Employee");
        lbemp.setForeground(Color.WHITE);
        lbemp.setFont(new Font("Arial", Font.BOLD, 16));
        JLabel lb_date = new JLabel("                           Date");
        lb_date.setForeground(Color.WHITE);
        lb_date.setFont(new Font("Arial", Font.BOLD, 16));

        JLabel totalsumLabel = new JLabel("         Totalsum");
        totalsumLabel.setForeground(Color.WHITE);
        totalsumLabel.setFont(new Font("Arial", Font.BOLD, 16));
        JLabel puysum = new JLabel("         Buy");
        puysum.setForeground(Color.WHITE);
        puysum.setFont(new Font("Arial", Font.BOLD, 16));
        JLabel returnsum = new JLabel("         Return money");
        returnsum.setForeground(Color.WHITE);
        returnsum.setFont(new Font("Arial", Font.BOLD, 16));

        lbshob.add(lbid);
        lbshob.add(idbuy);
        lbshob.add(lbemp);
        lbshob.add(Emp_ID);
        lbshob.add(lb_date);
        lbshob.add(datebuy);
        lbshob.add(new JLabel(""));
        lbshob.add(btnsave);

        lbsum.add(totalsumLabel);
        lbsum.add(totalsum);
        lbsum.add(puysum);
        lbsum.add(munnypeople);
        lbsum.add(returnsum);
        lbsum.add(returnmony);
        lbsum.add(new JLabel(""));
        lbsum.add(new JLabel(""));
        lbsum.add(btncale);
        lbsum.add(btnreport);

        pn3.add(lbshob);
        pn3.add(lbsum);

        JPanel pn4 = new JPanel(new BorderLayout());
        pn4.setBorder(border);

        // เพิ่มพาเนลล์ลงในเฟรม
        add(pn1, BorderLayout.NORTH);
        add(pn3, BorderLayout.SOUTH);
        add(pn2, BorderLayout.EAST);
        add(pn4, BorderLayout.CENTER);

        // สร้าง JTable และ DefaultTableModel
        String[] columnNames = { "No.", "B_ID", "B_NAME", "B_PRICE" };
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                // ทำให้เซลล์ทั้งหมดไม่สามารถแก้ไขได้
                return false;
            }
        };
        JTable table = new JTable(tableModel);

        // เพิ่ม JTable ลงใน JScrollPane เพื่อให้สามารถเลื่อนได้
        JScrollPane scrollPane = new JScrollPane(table);
        pn4.add(scrollPane, BorderLayout.CENTER);

        // โหลดข้อมูลจากฐานข้อมูลเข้าไปในตาราง
        loadDataFromDatabase();

        // สร้าง JTextArea สำหรับแสดงรายงาน
        dataTextArea = new JTextArea();
        dataTextArea.setEditable(false);
        JScrollPane reportScrollPane = new JScrollPane(dataTextArea);
        pn2.add(reportScrollPane, BorderLayout.CENTER);

        // เพิ่ม MouseListener ให้กับตาราง
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) { // ตรวจสอบการคลิกสองครั้ง
                    int[] selectedRows = table.getSelectedRows(); // รับอาร์เรย์ของแถวที่เลือก
                    for (int row : selectedRows) { // วนลูปผ่านแถวที่เลือก
                        String id = table.getValueAt(row, 1).toString();
                        String name = table.getValueAt(row, 2).toString();
                        String price = table.getValueAt(row, 3).toString();
                        String productKey = id + "\t" + name + "\t" + price;
                        // เพิ่มสินค้าที่เลือกเข้า JTextArea
                        dataTextArea.append(productKey + "\n");
                        // เพิ่มสินค้าลงในเซ็ตที่เก็บสินค้าที่เลือก
                        selectedProductsSet.add(productKey);
                        selectedProductsCount++; // อัพเดตจำนวนสินค้าที่เลือก
                        // อัพเดตรวมราคา
                        totalSumValue += Double.parseDouble(price);
                        totalsum.setText(String.valueOf(totalSumValue));
                    }
                    countLabel.setText("Selected Products: " + selectedProductsSet.size()); // อัพเดตป้ายจำนวน
                }
            }
        });

        // เพิ่มปุ่มยกเลิกสินค้า
        JButton cancelButton = new JButton("Cancel Product");
        pn2.add(cancelButton, BorderLayout.SOUTH);

        // เพิ่ม ActionListener ให้กับปุ่มยกเลิกสินค้า
        cancelButton.addActionListener(e -> {
            for (String product : selectedProductsSet) {
                dataTextArea.setText(dataTextArea.getText().replace(product + "\n", "")); // Remove selected product
                                                                                          // ลบสินค้าที่เลือกออกจาก
                                                                                          // JTextArea
            }
            selectedProductsSet.clear(); // เคลียร์เซ็ตสินค้าที่เลือก
            selectedProductsCount = 0; // รีเซ็ตจำนวนสินค้าที่เลือก
            countLabel.setText("Selected Products: " + selectedProductsSet.size()); // อัพเดตป้ายจำนวน

            // รีเซ็ตรวมราคา
            totalSumValue = 0.0;
            totalsum.setText("");

            // รีเซ็ต lbsum
            munnypeople.setText(""); // ลบข้อความในช่อง munnypeople
            returnmony.setText(""); // ลบข้อความในช่อง returnmony
        });

        // เพิ่ม ActionListener ให้กับปุ่ม btncale
        btncale.addActionListener(e -> {
            // รับค่ารวมทั้งสิ้นและจำนวนเงินที่จ่าย
            double totalSum = Double.parseDouble(totalsum.getText());
            double buyAmount = Double.parseDouble(munnypeople.getText());
            // คำนวณเงินทอน
            double returnMoney = buyAmount - totalSum;
            // ตั้งค่าเงินทอนในช่อง returnmony
            returnmony.setText(String.valueOf(returnMoney));
        });

        // เพิ่ม ActionListener ให้กับปุ่ม btnsave
        btnsave.addActionListener(e -> {
            // รับข้อมูลจาก GUI components
            String buyId = idbuy.getText();
            String buyEmp = Emp_ID.getSelectedItem().toString(); // รับค่าที่เลือกจาก Emp_ID
            String buyDate = datebuy.getText();

            // ตรวจสอบว่ามีข้อมูลที่ว่างเปล่าหรือไม่
            if (buyId.isEmpty() || buyEmp.isEmpty() || buyDate.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please fill in all fields!");
                return;
            }

            // แทรกข้อมูลลงในฐานข้อมูล
            String dbURL = "jdbc:mysql://localhost/project1";
            String username = "root";
            String password = "";

            String sqlQuery = "INSERT INTO buy (BUY_ID, Emp_ID, A_Date) VALUES (?, ?, ?)";

            try (Connection connection = DriverManager.getConnection(dbURL, username, password);
                    PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
                preparedStatement.setString(1, buyId);
                preparedStatement.setString(2, buyEmp);
                preparedStatement.setString(3, buyDate);

                int rowsAffected = preparedStatement.executeUpdate();

                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(null, "Data inserted successfully!");
                } else {
                    JOptionPane.showMessageDialog(null, "Failed to insert data!");
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });

        // เพิ่ม ActionListener ให้กับปุ่ม btnreport
        btnreport.addActionListener(e -> generateReport());
    }

    private void loadDataFromDatabase() {
        String dbURL = "jdbc:mysql://localhost/project1";
        String username = "root";
        String password = "";

        String sqlQuery = "SELECT B_ID, B_NAME,B_PRICE FROM bicycle";

        try (Connection connection = DriverManager.getConnection(dbURL, username, password);
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(sqlQuery)) {
            int no = 1; // กำหนดค่าเริ่มต้นให้กับตัวนับ No.
            while (resultSet.next()) {
                String id = resultSet.getString("B_ID");
                String name = resultSet.getString("B_NAME");
                String price = resultSet.getString("B_PRICE");

                tableModel.addRow(new Object[] { no++, id, name, price });
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    // เมธอดสำหรับเติมค่าใน JComboBox ด้วย ID ของพนักงาน
    private void populateEmployeeIDs() {
        // เชื่อมต่อฐานข้อมูล
        String dbURL = "jdbc:mysql://localhost/project1";
        String username = "root";
        String password = "";

        // คำสั่ง SQL เพื่อเรียก ID ของพนักงานจากตาราง buy
        String sqlQuery = "SELECT DISTINCT Emp_ID FROM employee";

        try (Connection connection = DriverManager.getConnection(dbURL, username, password);
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(sqlQuery)) {
            // วนลูปผ่านผลลัพธ์และเพิ่มทุก ID ลงใน JComboBox
            while (resultSet.next()) {
                String employeeID = resultSet.getString("Emp_ID");
                Emp_ID.addItem(employeeID);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void generateReport() {
        try {
            // สร้าง StringBuilder เพื่อเก็บข้อมูลรายงาน
            StringBuilder report = new StringBuilder();

            // เพิ่มชื่อร้านและวันที่ โดยทำให้ชื่อร้านเป็นตัวพิมพ์ใหญ่
            report.append("\n");
            report.append("                                                          SHOP: BCC BICYCLE")
                    .append("\n\n\n");

            // เพิ่มข้อมูลเพิ่มเติมจาก lbshob
            report.append("\nAdditional Information:\n");
            report.append("ID: ").append(idbuy.getText()).append("\n");
            report.append("Employee: ").append(Emp_ID.getSelectedItem()).append("\n");
            report.append("Date: ").append(datebuy.getText()).append("\n");

            report.append(
                    "----------------------------------------------------------------------------------------------------------------------------------------------------------------\n");
            // เพิ่มหัวของตาราง
            report.append("No.\tID\tName\tPrice\n");

            // เพิ่มเส้นแบ่งระหว่างชื่อร้านกับหัวตาราง
            report.append(
                    "----------------------------------------------------------------------------------------------------------------------------------------------------------------\n");

            // รับจำนวนแถวในตาราง
            int rowCount = tableModel.getRowCount();

            // วนลูปผ่านแต่ละแถวในตาราง
            for (int i = 0; i < rowCount; i++) {
                // ตรวจสอบว่าสินค้าเป็นสินค้าที่เลือกใหม่หรือไม่
                String productKey = tableModel.getValueAt(i, 1) + "\t" +
                        tableModel.getValueAt(i, 2) + "\t" +
                        tableModel.getValueAt(i, 3);
                if (selectedProductsSet.contains(productKey)) {
                    // เพิ่มข้อมูลจากแต่ละคอลัมน์แยกด้วยแท็บ
                    for (int j = 0; j < tableModel.getColumnCount(); j++) {
                        report.append(tableModel.getValueAt(i, j)).append("\t");
                    }
                    // เพิ่มเส้นใหม่เพื่อแบ่งแถว
                    report.append("\n");
                }
            }

            report.append(
                    "----------------------------------------------------------------------------------------------------------------------------------------------------------------\n");

            // เพิ่มข้อมูลจำนวนสินค้าทั้งหมด
            report.append("\t\t\t\t\t\t                                                               Total IDs: ")
                    .append(selectedProductsSet.size()).append("\n");

            report.append(
                    "----------------------------------------------------------------------------------------------------------------------------------------------------------------\n");

            // เพิ่มราคารวมและข้อมูลอื่นๆจาก lbsum
            report.append("Total Sum: ").append(totalsum.getText()).append("\n");
            report.append("Buy: ").append(munnypeople.getText()).append("\n");
            report.append("Return Money: ").append(returnmony.getText()).append("\n");

            // แสดงรายงานใน JTextArea
            dataTextArea.setText(report.toString());

            // ตัวเลือกเพื่อบันทึกรายงานลงในไฟล์หรือดำเนินการอื่นๆกับมันได้

            // สร้าง PrinterJob
            PrinterJob printerJob = PrinterJob.getPrinterJob();
            if (printerJob.printDialog()) {
                // ตั้ง JTextArea เป็น printable component
                printerJob.setPrintable(dataTextArea.getPrintable(null, null));

                // พิมพ์เอกสาร
                try {
                    printerJob.print();
                } catch (PrinterException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(this, "Error while printing.", "Printing Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }

            // แสดงข้อความเพื่อระบุว่าสร้างรายงานแล้ว
            JOptionPane.showMessageDialog(this, "Report generated successfully.");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Buy().setVisible(true));
    }
}
