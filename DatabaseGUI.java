import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import java.awt.Font;

import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JScrollPane;
import javax.swing.JComboBox;


public class DatabaseGUI extends JFrame {

	private JPanel contentPane;
	private JTable table;
	private JTextField ISBN;
	private JTextField BOOKTITLE;
	private JTextField AUTHOR;
	static Datebase database;
	private JScrollPane scrollPane;
	private JTextField insertInput;
	private JButton btnNewButton_2;
	String tablename="book";//the name of table 
	private JButton delete;
	private JLabel lblYearPublished;
	private JTextField YEAR;
	private JLabel lblCondition;
	private JTextField CONDITION;
	private JButton search;
	private JTextField searchBox;
	private JComboBox comboBox;
	private JButton btnNewButton;
	private File output;
	private JTextField backupinput;
	private JButton btnGetInfo;
	
	/**
	 * Launch the application.
	 */

	/**
	 * Create the frame.
	 */
	public DatabaseGUI(Datebase database,File output) {
		this.database=database;
		this.output=output;
		try {
			database.createTable(tablename);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(200, 100, 803, 558);
		setLocationRelativeTo(null);
		setVisible(false);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(39, 45, 496, 405);
		contentPane.add(scrollPane);
		
		table = new JTable();
		table.setFont(new Font("Tahoma", Font.PLAIN, 13));
		scrollPane.setViewportView(table);
		table.getSelectionModel().addListSelectionListener(new ListSelectionListener(){
	        public void valueChanged(ListSelectionEvent event) {
	        	if(table.getSelectedRow()!=-1){//get the row if user selected a row
	        		int number=table.getSelectedRow();
	        		String isbn=String.valueOf(table.getValueAt(number,0));
	        		String title=String.valueOf(table.getValueAt(number,1));
	        		String author=String.valueOf(table.getValueAt(number,2));
	        		String year=String.valueOf(table.getValueAt(number,3));
	        		String condition=String.valueOf(table.getValueAt(number,4));
	        		ISBN.setText(isbn);//print the row to the relative Text Field
	        		BOOKTITLE.setText(title);
	        		AUTHOR.setText(author);
	        		YEAR.setText(year);
	        		CONDITION.setText(condition);
	        	}//end if 
	        }//end valueChanged
	    });
		
		JLabel lblNewLabel = new JLabel("ISBN");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(602, 33, 127, 14);
		contentPane.add(lblNewLabel);
		
		ISBN = new JTextField();
		ISBN.setBounds(602, 58, 127, 20);
		contentPane.add(ISBN);
		ISBN.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("Book Title");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setBounds(602, 105, 127, 14);
		contentPane.add(lblNewLabel_1);
		
		BOOKTITLE = new JTextField();
		BOOKTITLE.setBounds(602, 130, 127, 20);
		contentPane.add(BOOKTITLE);
		BOOKTITLE.setColumns(10);
		
		JLabel Author = new JLabel("Author");
		Author.setFont(new Font("Tahoma", Font.PLAIN, 14));
		Author.setHorizontalAlignment(SwingConstants.CENTER);
		Author.setBounds(602, 173, 127, 14);
		contentPane.add(Author);
		
		AUTHOR = new JTextField();
		AUTHOR.setBounds(602, 198, 127, 20);
		contentPane.add(AUTHOR);
		AUTHOR.setColumns(10);
		
		JButton add = new JButton("ADD");
		add.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if(!database.check(ISBN.getText(),tablename)){//check is this book already exist,if yes ask user 
						int result = JOptionPane.showConfirmDialog(null, "book already exist, want to add one more?",null, JOptionPane.YES_NO_OPTION);
						if(result == JOptionPane.YES_OPTION) {//if insert, just update the quantity
						    database.updateQuantity(ISBN.getText(),tablename);
						    JOptionPane.showMessageDialog(null, "book add to your database");
						    database.get(tablename,table);
						} //end if
					}//end if
					else{//just insert book to database
						database.insertDate(ISBN.getText(), BOOKTITLE.getText(), AUTHOR.getText(), YEAR.getText(),CONDITION.getText(), tablename);
						JOptionPane.showMessageDialog(null, "book add to your database");
						database.get(tablename,table);
					}//end else
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		add.setBounds(666, 386, 89, 33);
		contentPane.add(add);
		
		JButton btnNewButton_1 = new JButton("Load Table");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					database.get(tablename,table);//get the table from database and print to the table
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}//end catch
			}
		});
		btnNewButton_1.setBounds(39, 11, 136, 23);
		contentPane.add(btnNewButton_1);
		
		insertInput = new JTextField();
		insertInput.setFont(new Font("Tahoma", Font.ITALIC, 11));
		insertInput.setBounds(44, 476, 89, 23);
		contentPane.add(insertInput);
		insertInput.setColumns(10);
		
		btnNewButton_2 = new JButton("insert input");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String filename=insertInput.getText();
				try {
					insertfile(filename);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnNewButton_2.setBounds(143, 476, 89, 23);
		contentPane.add(btnNewButton_2);
		
		delete = new JButton("DELETE");
		delete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int number=table.getSelectedRow();
				String isbn=String.valueOf(table.getModel().getValueAt(number,0));//the get selected row's isbn value 
				try {
					database.delete(tablename,isbn);//delete the row from database where isbn is same
					ISBN.setText(null);
					BOOKTITLE.setText(null);
					AUTHOR.setText(null);
					YEAR.setText(null);
					CONDITION.setText(null);
					database.get(tablename, table);//reprint table 
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				
			}
		});
		delete.setBounds(567, 430, 89, 33);
		contentPane.add(delete);
		
		lblYearPublished = new JLabel("Year Published");
		lblYearPublished.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblYearPublished.setHorizontalAlignment(SwingConstants.CENTER);
		lblYearPublished.setBounds(602, 245, 127, 14);
		contentPane.add(lblYearPublished);
		
		YEAR = new JTextField();
		YEAR.setBounds(602, 270, 127, 20);
		contentPane.add(YEAR);
		YEAR.setColumns(10);
		
		lblCondition = new JLabel("Condition");
		lblCondition.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblCondition.setHorizontalAlignment(SwingConstants.CENTER);
		lblCondition.setBounds(602, 316, 127, 14);
		contentPane.add(lblCondition);
		
		CONDITION = new JTextField();
		CONDITION.setBounds(602, 341, 127, 20);
		contentPane.add(CONDITION);
		CONDITION.setColumns(10);
		
		JButton modify = new JButton("UPDATE"); 
		modify.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int number=table.getSelectedRow();
				String isbn=String.valueOf(table.getModel().getValueAt(number,0));//get value from text Filed
				String title=BOOKTITLE.getText();
				String author=AUTHOR.getText();
				String year=YEAR.getText();
				String condition=CONDITION.getText();
				try {
					database.update(tablename,isbn, title, author, year, condition);//change the value of row where isbn is same
					database.get(tablename, table);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		modify.setBounds(567, 386, 89, 33);
		contentPane.add(modify);
		
		search = new JButton("SEARCH");
		search.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String title=String.valueOf(comboBox.getSelectedItem());
				String text=searchBox.getText();
				if(text==null){
					JOptionPane.showMessageDialog(null, "please enter key word");
				}//end if 
				else{
					try {
						database.search(title, text, tablename,table);//search the database where keyword is same
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}//end else
			}
		});
		search.setBounds(446, 11, 89, 23);
		contentPane.add(search);
		
		String[] type = { "ISBN", "Title", "Author", "Publish_Year"};
		comboBox = new JComboBox(type);
		comboBox.setBounds(228, 12, 86, 20);
		comboBox.setSelectedIndex(3);
		contentPane.add(comboBox);
		
		searchBox = new JTextField();
		searchBox.setBounds(312, 12, 105, 20);
		contentPane.add(searchBox);
		searchBox.setColumns(10);
		
		btnNewButton = new JButton("BACK");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
				MenuGUI menugui = new MenuGUI(database,output);
				menugui.setVisible(true);
				
			}
		});
		btnNewButton.setBounds(617, 477, 89, 20);
		contentPane.add(btnNewButton);
		
		backupinput = new JTextField();
		backupinput.setBounds(242, 477, 86, 20);
		contentPane.add(backupinput);
		backupinput.setColumns(10);
		
		JButton backup = new JButton("back up");
		backup.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String inputfile = backupinput.getText();
				try {
					database.backup(tablename,inputfile);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		backup.setBounds(338, 476, 89, 23);
		contentPane.add(backup);
		
		btnGetInfo = new JButton("GET INFO");
		
		btnGetInfo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String isbn=ISBN.getText();
				String name="http://www.abebooks.com/servlet/SearchResults?isbn="+isbn+"&sts=t";
				try{
				URL url = new URL(name);
				BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));//open url 
				String line=reader.readLine();
				String re1=".*?";//regex for locate keyword
				String re3="(itemprop)";//key word for the book
				String[] re={"(\"isbn\")","(\"name\")","(\"author\")","(\"datePublished\")","(\"itemCondition\")"};//regex for key word
				String re2="(book-1)";
				Pattern begin = Pattern.compile(re1+re2,Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
				Matcher b = begin.matcher(line);//find the key word
				while(!b.find()){//read line until key word book-1
					line = reader.readLine();
					b = begin.matcher(line);
				}
				line=reader.readLine();
				while(line!="Published by"){//if line is not key word publish by,keep read line
					for(int i=0;i<5;i++){//get match every every when read line
						Pattern content = Pattern.compile(re1+re3+re1+re[i],Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
						Matcher c = content.matcher(line);//match book info key word
						if (c.find()){//if find book info
							Pattern text = Pattern.compile("content=(.*\\\"(.*)\\\".*)",Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
							Matcher answer = text.matcher(line);
							answer.find();
							String s=answer.group(2);
							if(i==0){ISBN.setText(s);}//print the value to relative text field
							if(i==1){BOOKTITLE.setText(s);}
							if(i==2){AUTHOR.setText(s);}
							if(i==3){YEAR.setText(s);}
							if(i==4){CONDITION.setText(s);}
						}//end if 
					}//end for
					line=reader.readLine();
				}//end while
				}
				catch (Exception e1) {}
			}//end catch
		});
		btnGetInfo.setBounds(666, 430, 89, 33);
		contentPane.add(btnGetInfo);
		
		JButton btnNewButton_3 = new JButton("output");
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					tooutput("output.txt");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		btnNewButton_3.setBounds(446, 476, 89, 23);
		contentPane.add(btnNewButton_3);
	}
	/**
	 * insert the input file to database
	 * @param filename
	 * @throws Exception
	 */
	public void insertfile(String filename) throws Exception{
			File input = new File(filename);//input file
			Scanner reader = new Scanner(input);
			while(reader.hasNextLine()){
				String line = reader.nextLine();
				if(!database.check(line, tablename)){
					database.updateQuantity(line, tablename);
				}//end if 
				else{
					database.insertDate(line, "", "", "", "", tablename);
				}//end else
				
			}//end while
			JOptionPane.showMessageDialog(null, "finish insert file");
			reader.close();
	}//end insertfile
	
	public void tooutput(String filename)throws Exception{
		File output = new File(filename);
		PrintWriter printer = new PrintWriter(output); //read output file 
		int n = table.getRowCount();
		int z=0;
		while(z<n){
			printer.print(table.getValueAt(z,0)+" | ");
			printer.print(table.getValueAt(z,1)+" | ");
			printer.print(table.getValueAt(z,2)+" | ");
			printer.print(table.getValueAt(z,3)+" | ");
			printer.print(table.getValueAt(z,4)+" | ");
			printer.println(table.getValueAt(z,5));
			z++;
		}
		printer.close();
		
		
	}
}
