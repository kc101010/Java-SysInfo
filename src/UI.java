import javax.swing.*;
import java.awt.*;

public class UI  extends JFrame {

    private JLabel os;
    private JLabel gpu;
    private JLabel user;
    private JLabel mem;
    private JLabel arch;
    private JLabel threads;
    private JLabel cpu_use;
    private JPanel panel;
    private JLabel title;


    public UI(String OS, String GPU, String USER, long MEM, String ARCH, int THREADS){
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("System Configuration");
        this.setLayout(new FlowLayout());
        this.setSize(400,350);
        this.setResizable(false);

        os = new JLabel();
        gpu = new JLabel();
        user = new JLabel();
        mem = new JLabel();
        arch = new JLabel();
        threads = new JLabel();
        cpu_use = new JLabel();
        title = new JLabel();

        MEM++;

        os.setText("OS: " + OS);
        gpu.setText("GPU: " + GPU);
        user.setText("Username: " + USER);
        mem.setText("Memory: " + MEM + " GB");
        arch.setText("CPU Architecture: " + ARCH);
        threads.setText("CPU Threads: " + THREADS);
        title.setText("System Information: ");


        panel = new JPanel();
        panel.setBounds(0, 0, 300, 500);
        panel.setLayout(new GridLayout(8,5,10, 10));
        panel.setBackground(Color.lightGray);

        this.add(title);

        panel.add(user);
        panel.add(os);
        panel.add(gpu);
        panel.add(mem);
        panel.add(arch);
        panel.add(threads);

        this.add(panel);
        this.add(cpu_use);

        this.setVisible(true);
    }

    public void writeCPU_usage(int usage){
        cpu_use.setText("Current CPU Usage (Total): " + usage + "%");
    }
}
