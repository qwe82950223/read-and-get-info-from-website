import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JLabel;

import java.awt.Font;
import java.io.File;
import java.io.PrintWriter;


public class MenuGUI extends JFrame {
	static Datebase database;
	private JPanel contentPane;
	private File output;

	/**
	 * Launch the application.
	 */

	/**
	 * Create the frame.
	 */
	public MenuGUI(Datebase database,File output) {
		this.output=output;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(400, 200, 450, 300);
		setLocationRelativeTo(null);
		setVisible(false);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton mydatabase = new JButton("MY DATABASE");
		mydatabase.setFont(new Font("Tahoma", Font.BOLD, 11));
		mydatabase.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();//go to the database gui 
				DatabaseGUI databasegui = new DatabaseGUI(database,output);
				databasegui.setVisible(true);
			}
		});
		mydatabase.setBounds(70, 154, 121, 41);
		contentPane.add(mydatabase);
		
		JButton search = new JButton("SEARCH ONLINE");
		search.setFont(new Font("Tahoma", Font.BOLD, 11));
		search.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();//go to the online search gui
				SearchGUI searchgui = new SearchGUI(database,output);
				searchgui.setVisible(true);
			}
		});
		search.setBounds(242, 154, 121, 41);
		contentPane.add(search);
		
		JButton logout = new JButton("LOG OUT");
		logout.setFont(new Font("Tahoma", Font.PLAIN, 11));
		logout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();//disable current gui,and go back to prev gui
				LoginGUI logingui = new LoginGUI(output);
			}//end actionPerformed
		});
		logout.setBounds(173, 214, 86, 23);
		contentPane.add(logout);
		
		JLabel db = new JLabel("");
		Image img1 = new ImageIcon(this.getClass().getResource("/db.png")).getImage();//show image
		Image image1=img1.getScaledInstance(80, 100, java.awt.Image.SCALE_SMOOTH);
		db.setIcon(new ImageIcon(image1));
		db.setBounds(91, 24, 100, 119);
		contentPane.add(db);
		
		JLabel online = new JLabel("");
		Image img2 = new ImageIcon(this.getClass().getResource("/online.png")).getImage();//show image
		Image image2=img2.getScaledInstance(120, 100, java.awt.Image.SCALE_SMOOTH);
		online.setIcon(new ImageIcon(image2));
		online.setBounds(242, 24, 121, 119);
		contentPane.add(online);
	}
}//end class
