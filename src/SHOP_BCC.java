import javax.swing.*;
import java.awt.geom.RoundRectangle2D;
import java.net.URL;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Shape;


public class SHOP_BCC extends JFrame implements ActionListener {
    // ประกาศตัวแปรปุ่มที่ใช้ในโปรแกรม
    private RoundButton btnproduct, btntype, btnemployee, btnshop, REPORT_SHOP;

    // สร้าง GUI
    public SHOP_BCC() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setTitle("SHOP BCC Bicycle ");

        // สร้างพาเนลสำหรับจัดวาง GUI
        JPanel pn1 = new JPanel(new GridLayout(3, 1));
        pn1.setPreferredSize(new Dimension(1000, 100));
        pn1.setBackground(new Color(54, 100, 139)); // SteelBlue

        JPanel pn2 = new JPanel(new GridLayout(2, 2, 45, 20));
        pn2.setPreferredSize(new Dimension(1000, 500));
        pn2.setBorder(BorderFactory.createEmptyBorder(20, 10, 0, 10));

        JPanel pn3 = new JPanel();
        pn3.setPreferredSize(new Dimension(1000, 50));

        JPanel pn4 = new JPanel();
        JPanel pn5 = new JPanel();

        // เพิ่มพาเนลลงในเฟรมเวิร์ก
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(pn1, BorderLayout.NORTH);
        getContentPane().add(pn2, BorderLayout.CENTER);
        getContentPane().add(pn3, BorderLayout.SOUTH);
        getContentPane().add(pn4, BorderLayout.EAST);
        getContentPane().add(pn5, BorderLayout.WEST);

        // สร้าง JLabel สำหรับหัวข้อร้านค้า
        JLabel shop = new JLabel("SHOP BCC");
        shop.setForeground(Color.WHITE); // สีขาว
        shop.setFont(new Font("Arial", Font.BOLD, 30));
        shop.setHorizontalAlignment(SwingConstants.CENTER);

        // สร้างปุ่มและกำหนดรายละเอียด
        btnproduct = new RoundButton("PRODUCT");
        btnproduct.setBackground(new Color(255, 102, 102));
        btnproduct.setForeground(Color.WHITE);
        btnproduct.setFont(new Font("Arial", Font.BOLD, 24));

        btntype = new RoundButton("TYPE       ");
        btntype.setBackground(new Color(108, 166, 205));
        btntype.setForeground(Color.WHITE);
        btntype.setFont(new Font("Arial", Font.BOLD, 24));

        btnemployee = new RoundButton("EMPLOYEE");
        btnemployee.setBackground(new Color(238, 180, 34));
        btnemployee.setForeground(Color.WHITE);
        btnemployee.setFont(new Font("Arial", Font.BOLD, 24));

        btnshop = new RoundButton("SHOP");
        btnshop.setBackground(new Color(130, 114, 192));
        btnshop.setForeground(Color.WHITE);
        btnshop.setFont(new Font("Arial", Font.BOLD, 24));

       /*  REPORT_SHOP = new RoundButton("REPORT_SHOP");
        REPORT_SHOP.setBackground(new Color(114, 192, 142));
        REPORT_SHOP.setForeground(Color.WHITE);
        REPORT_SHOP.setFont(new Font("Arial", Font.BOLD, 24));*/

        // เพิ่ม JLabel และปุ่มลงในพาเนลที่เหมาะสม
        pn1.add(new JLabel(""));
        pn1.add(shop);
        pn1.add(new JLabel(""));

        pn2.add(btnproduct);
        pn2.add(btnshop);
        pn2.add(btnemployee);
        pn2.add(btntype);
       /*  pn2.add(REPORT_SHOP);*/

        // กำหนดรูปไอคอนให้กับปุ่ม
        setButtonIcons();

        // เพิ่ม ActionListener ให้กับปุ่มเพื่อทำงานเมื่อปุ่มถูกคลิก
        btnproduct.addActionListener(this);
        btntype.addActionListener(this);
        btnemployee.addActionListener(this);
        btnshop.addActionListener(this);
        /*REPORT_SHOP.addActionListener(this);*/
    }

    // เมธอดสำหรับการจัดการเหตุการณ์เมื่อปุ่มถูกคลิก
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnproduct) {
            // การคลิกปุ่ม PRODUCT
            // เรียกใช้หน้าต่าง Bicycle สำหรับจัดการสินค้า
            Bicycle productGUI = new Bicycle();
            productGUI.setVisible(true);
        } else if (e.getSource() == btntype) {
            // การคลิกปุ่ม TYPE
            // เรียกใช้หน้าต่าง Type_product สำหรับจัดการประเภทสินค้า
            Type_product typeGUI = new Type_product();
            typeGUI.setVisible(true);
        } else if (e.getSource() == btnemployee) {
            // การคลิกปุ่ม EMPLOYEE
            // เรียกใช้หน้าต่าง Employee สำหรับจัดการพนักงาน
            Employee employeeGUI = new Employee();
            employeeGUI.setVisible(true);
        } else if (e.getSource() == btnshop) {
            // การคลิกปุ่ม SHOP
            // เรียกใช้หน้าต่าง Buy สำหรับการซื้อสินค้า
            Buy shopGUI = new Buy();
            shopGUI.setVisible(true);
        }
    }

    // เมธอดสำหรับกำหนดไอคอนให้กับปุ่ม
    private void setButtonIcons() {
        btnproduct.setIcon(getScaledIcon("add-item.png"));
        btnshop.setIcon(getScaledIcon("store.png"));
        btntype.setIcon(getScaledIcon("add-product (1).png"));
        btnemployee.setIcon(getScaledIcon("add-user.png"));
       /*  REPORT_SHOP.setIcon(getScaledIcon("report.png"));*/
    }

    // เมธอดสำหรับปรับขนาดไอคอนให้เหมาะสม
    private ImageIcon getScaledIcon(String imageName) {
        URL imageUrl = getClass().getResource(imageName);
        if (imageUrl != null) {
            ImageIcon icon = new ImageIcon(imageUrl);
            Image scaledImage = icon.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
            return new ImageIcon(scaledImage);
        } else {
            // จัดการข้อผิดพลาดที่เกิดจากการไม่พบไฟล์รูปภาพ
            System.err.println("Image file not found: " + imageName);
            return null;
        }
    }

    // แสดง GUI
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            SHOP_BCC frame = new SHOP_BCC();
            frame.setVisible(true);
        });
    }
}

// คลาสสำหรับปุ่มกลม
class RoundButton extends JButton {
    public RoundButton(String label) {
        super(label);
        setBackground(Color.BLUE);
        setForeground(Color.WHITE);
        setFocusPainted(false);
        setBorderPainted(false);
        setOpaque(false);
        setContentAreaFilled(false);
    }

    protected void paintComponent(Graphics g) {
        if (getModel().isArmed()) {
            g.setColor(Color.lightGray);
        } else {
            g.setColor(getBackground());
        }
        g.fillRoundRect(0, 0, getSize().width - 1, getSize().height - 1, 10, 10);

        super.paintComponent(g);
    }

    protected void paintBorder(Graphics g) {
        g.setColor(getForeground());
        g.drawRoundRect(0, 0, getSize().width - 1, getSize().height - 1, 10, 10);
    }

    Shape shape;

    public boolean contains(int x, int y) {
        if (shape == null || !shape.getBounds().equals(getBounds())) {
            shape = new RoundRectangle2D.Float(0, 0, getWidth() - 1, getHeight() - 1, 10, 10);
        }
        return shape.contains(x, y);
    }
}
