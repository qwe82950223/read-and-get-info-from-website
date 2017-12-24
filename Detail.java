import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Image;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.SwingConstants;


public class Detail extends JFrame {

	private JPanel contentPane;
	/**
	 * Create the frame.
	 * @throws Exception 
	 */
	public Detail(String imagename) throws Exception {
		ImageIcon img = new ImageIcon(new URL(imagename));
		int width=img.getIconWidth();
		int height=img.getIconHeight();
		
		setBounds(100, 100, width, height);
		setLocationRelativeTo(null);
		setVisible(true);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		
		JLabel image = new JLabel("",img,SwingConstants.CENTER);
		image.setBounds(0, 0, width, height);
		contentPane.add(image);
	}

}
