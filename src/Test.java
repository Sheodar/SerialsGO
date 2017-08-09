import java.awt.*;
import javax.swing.*;
public class Test extends JFrame
{
    public Test()
    {
        JTable table = new JTable(20, 2);
        JScrollPane jsp = new JScrollPane(table);
        jsp.setPreferredSize(new Dimension(200, 0));
        add(jsp, "West");
    }
    public static void main(String [] args)
    {
        JFrame f = new Test();
        f.setSize(500, 200);
        f.setVisible(true);
        f.setDefaultCloseOperation(3);
    }
}
