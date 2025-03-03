import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HomeGUI extends JFrame implements ActionListener {
    private JButton employeesButton, productsButton, categoriesButton;

    public HomeGUI() {
        setTitle("Home");
        setSize(400, 200);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // ตั้งตำแหน่งของหน้าต่าง JFrame ให้อยู่ตรงกลาง

        JPanel panel = new JPanel(new GridLayout(3, 1));

        // ปุ่ม Employees
        employeesButton = new JButton("Employees");
        employeesButton.addActionListener(this);
        employeesButton.setBackground(Color.RED); // กำหนดสีพื้นหลังให้กับปุ่ม
        employeesButton.setForeground(Color.WHITE); // กำหนดสีตัวอักษรให้กับปุ่ม
        employeesButton.setPreferredSize(new Dimension(150, 50)); // กำหนดขนาดของปุ่ม
        panel.add(employeesButton);

        // ปุ่ม Products
        productsButton = new JButton("Products");
        productsButton.addActionListener(this);
        productsButton.setBackground(Color.GREEN); // กำหนดสีพื้นหลังให้กับปุ่ม
        productsButton.setForeground(Color.WHITE); // กำหนดสีตัวอักษรให้กับปุ่ม
        productsButton.setPreferredSize(new Dimension(150, 50)); // กำหนดขนาดของปุ่ม
        panel.add(productsButton);

        // ปุ่ม Product Categories
        categoriesButton = new JButton("Product Categories");
        categoriesButton.addActionListener(this);
        categoriesButton.setBackground(Color.BLUE); // กำหนดสีพื้นหลังให้กับปุ่ม
        categoriesButton.setForeground(Color.WHITE); // กำหนดสีตัวอักษรให้กับปุ่ม
        categoriesButton.setPreferredSize(new Dimension(150, 50)); // กำหนดขนาดของปุ่ม
        panel.add(categoriesButton);

        getContentPane().add(panel, BorderLayout.CENTER);
        setResizable(false); // ปิดใช้งานการปรับขนาดหน้าต่าง
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == employeesButton) {
            // เรียก GUI สำหรับจัดการพนักงาน
            JavaDBBasic employeeGUI = new JavaDBBasic();
            employeeGUI.setVisible(true);
        } else if (e.getSource() == productsButton) {
            // เรียก GUI สำหรับจัดการสินค้า
            JavaDBBasic productGUI = new JavaDBBasic();
            productGUI.setVisible(true);
        } else if (e.getSource() == categoriesButton) {
            // เรียก GUI สำหรับจัดการประเภทสินค้า
            JavaDBBasic categoryGUI = new JavaDBBasic();
            categoryGUI.setVisible(true);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            HomeGUI homeGUI = new HomeGUI();
            homeGUI.setVisible(true);
        });
    }
}
