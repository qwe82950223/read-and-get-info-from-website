import java.awt.EventQueue;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.Font;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import javax.swing.SwingConstants;


public class LoginGUI {

	private JFrame frmLogIn;
	private JTextField usernametextField;//text filed to enter username
	private JPasswordField passwordField;//text filed to enter password
	static Connection con;
	private JLabel lblLocalDatabaseName;
	private JTextField databaseNameField;//text field to enter database name
	private File output;
	/**
	 * Launch the application.
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					File output = new File("log.txt");//output file
					LoginGUI window = new LoginGUI(output);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}//end fun
		});
	}//end main

	/**
	 * Create the application.
	 */
	public LoginGUI(File output) {
		this.output=output;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmLogIn = new JFrame();
		frmLogIn.setTitle("Log in");
		frmLogIn.setBounds(400, 200, 467, 300);
		frmLogIn.setLocationRelativeTo(null);
		frmLogIn.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmLogIn.getContentPane().setLayout(null);
		frmLogIn.setVisible(true);
		
		JLabel lblUsername = new JLabel("username");
		lblUsername.setHorizontalAlignment(SwingConstants.CENTER);
		lblUsername.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblUsername.setBounds(194, 54, 80, 23);
		frmLogIn.getContentPane().add(lblUsername);
		
		JLabel lblPassword = new JLabel("password");
		lblPassword.setHorizontalAlignment(SwingConstants.CENTER);
		lblPassword.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblPassword.setBounds(194, 96, 80, 23);
		frmLogIn.getContentPane().add(lblPassword);
		
		usernametextField = new JTextField();
		usernametextField.setBounds(293, 57, 109, 20);
		frmLogIn.getContentPane().add(usernametextField);
		usernametextField.setColumns(10);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(293, 98, 109, 23);
		frmLogIn.getContentPane().add(passwordField);
		
		JButton btnNewButton = new JButton("login");
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String local="jdbc:mysql://localhost:3306/"; 
				String databaseName = databaseNameField.getText();
				String url = local + databaseName;
				String username = usernametextField.getText();
				String password = String.valueOf(passwordField.getPassword());
				try {
					Datebase database = new Datebase(url,username,password,output);//connect database with the username and password user enter
					if(database.con!=null){
						frmLogIn.dispose();
						JOptionPane.showMessageDialog(null, "connected");
						MenuGUI menugui = new MenuGUI(database,output);
						menugui.setVisible(true);
					}//end if
					else{
						JOptionPane.showMessageDialog(null, "wrong username or password");
					}//end else
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}//connect database
			}
		});
		btnNewButton.setBounds(255, 192, 80, 32);
		frmLogIn.getContentPane().add(btnNewButton);
		
		lblLocalDatabaseName = new JLabel("local database ");
		lblLocalDatabaseName.setHorizontalAlignment(SwingConstants.CENTER);
		lblLocalDatabaseName.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblLocalDatabaseName.setBounds(183, 140, 100, 23);
		frmLogIn.getContentPane().add(lblLocalDatabaseName);
		
		databaseNameField = new JTextField();
		databaseNameField.setBounds(293, 142, 109, 23);
		frmLogIn.getContentPane().add(databaseNameField);
		databaseNameField.setColumns(10);
		
		JLabel label = new JLabel("");
		Image img = new ImageIcon(this.getClass().getResource("/db1.png")).getImage();
		Image image=img.getScaledInstance(120, 100, java.awt.Image.SCALE_SMOOTH);
		label.setIcon(new ImageIcon(image));
		label.setBounds(35, 54, 123, 117);
		frmLogIn.getContentPane().add(label);
	}//end LoginGUi
}//end class
