import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Scanner;

import javax.swing.JOptionPane;
import javax.swing.JTable;

import net.proteanit.sql.DbUtils;
/**
 * class to connect database
 * @author xiao lin
 *
 */
public class Datebase{
	static Connection con;
	private File output;
	
	public Datebase(String url,String username,String password,File output)throws Exception{
		this.output=output;
		con = getConnection(url,username,password);
	}//end Database
	/**
	 * connect to database
	 * @param url
	 * @param username
	 * @param password
	 * @return
	 * @throws Exception
	 */
	public static Connection getConnection(String url,String username,String password)throws Exception{
		try{
			String driver = "com.mysql.jdbc.Driver";
			Class.forName(driver);
			Connection conn = DriverManager.getConnection(url,username,password);//connect with host
			return conn;
		}//end try
		catch(Exception e){System.out.println(e);}//end catch
		
		return null;
	}//end getConnection
	/**
	 * create new table
	 * @param tablename
	 * @throws Exception
	 */
	public void createTable(String tablename)throws Exception{//create table with lastname firstname and time stamp
		PreparedStatement create = con.prepareStatement("CREATE TABLE IF NOT EXISTS "+tablename+"(ISBN varchar(255) NOT NULL, Title varchar(255), Author varchar(255), Publish_Year varchar(255), Item_Condition varchar(255), addTime varchar(255),Quantity int, PRIMARY KEY(ISBN))");
		create.executeUpdate();
	}//end createTable
	/**
	 * insert data into table 
	 * @param tablename
	 * @param firstname
	 * @param lastname
	 * @param printer
	 * @throws Exception
	 */
	public void insertDate(String ISBN,String title,String author,String year,String condition,String tablename)throws Exception{
		PrintWriter printer = new PrintWriter(new FileWriter(output,true));
		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());//get the time stamp
		PreparedStatement insert = con.prepareStatement("INSERT INTO "+tablename+"(ISBN,Title,Author,Publish_Year,Item_Condition,addTime,Quantity) VALUE ('"+ISBN+"','"+title+"','"+author+"','"+year+"','"+condition+"','"+timeStamp+"',1)");
		insert.executeUpdate();//insert date into table
		printer.println("INSERT INTO "+tablename+"(ISBN,Title,Author,Publish_Year,Item_Condition,addTime,Quantity) VALUE ('"+ISBN+"','"+title+"','"+author+"','"+year+"','"+condition+"','"+timeStamp+"',1)"  );
		printer.close();
	}//end insertDate
	/**
	 * get the row from table
	 * @param tablename
	 * @param printer output file
	 * @throws Exception
	 */
	public void get(String tablename,JTable table)throws Exception{
		PreparedStatement statement = con.prepareStatement("SELECT * FROM "+tablename);//get teh id firstname lastname and time time stamp from table 
		ResultSet result = statement.executeQuery();
		table.setModel(DbUtils.resultSetToTableModel(result));
		
	}//end get
	/**
	 * delete the row from table
	 * @param tablename name of table
	 * @param number id of row
	 * @param printer output file
	 * @throws SQLException
	 */
	public void delete(String tablename,String isbn) throws Exception{
		PrintWriter printer = new PrintWriter(new FileWriter(output,true));
		PreparedStatement delete = con.prepareStatement("DELETE FROM "+tablename+" WHERE ISBN='"+isbn+"'");//delete the row where which depend on id
		delete.executeUpdate();
		printer.println("DELETE FROM "+tablename+" WHERE ISBN='"+isbn+"'");
		printer.close();
	}//end delete
	/**
	 * update the row of table
	 * @param tablename name of table 
	 * @param number id number 
	 * @param firstname 
	 * @param lastname
	 * @param printer output file
	 * @throws Exception
	 */
	public void update(String tablename,String isbn,String title,String author,String year,String condition)throws Exception{
		PrintWriter printer = new PrintWriter(new FileWriter(output,true));
		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());//new time stamp 
		PreparedStatement update = con.prepareStatement("UPDATE "+tablename+" SET Title='"+title+"',Author='"+author+"',Publish_Year='"+year+"',Item_Condition='"+condition+"', addTime='"+timeStamp+"' WHERE ISBN='"+isbn+"'");
		update.executeUpdate();//update data
		printer.println("UPDATE "+tablename+" SET Title='"+title+"',Author='"+author+"',Publish_Year='"+year+"',Item_Condition='"+condition+"', addTime='"+timeStamp+"' WHERE ISBN='"+isbn+"'" );
		printer.close();
	}//end update
	/**
	 * back up the database use log file
	 * @param tablename
	 * @param filename
	 * @throws Exception
	 */
	public void backup(String tablename,String filename)throws Exception{
		File input = new File(filename);
		Scanner reader = new Scanner(input); // read input file 
		while(reader.hasNextLine()){//execute every line of log file
			PreparedStatement statement = con.prepareStatement(reader.nextLine());
			statement.executeUpdate();
		}//end while
		JOptionPane.showMessageDialog(null, "database finish back up.");
	}//end backup
	/**
	 * search the datebase by using key word
	 * @param title the name of col
	 * @param text the text of col you want to search 
	 * @param tablename
	 * @param table
	 * @throws Exception
	 */
	public void search(String title,String text,String tablename,JTable table)throws Exception{
		PreparedStatement statement = con.prepareStatement("SELECT * FROM "+tablename+" WHERE "+title+" like '"+text+"%'");//get teh id firstname lastname and time time stamp from table 
		ResultSet result = statement.executeQuery();
		table.setModel(DbUtils.resultSetToTableModel(result));
	}//end search
	/**
	 * check is data repeat
	 * @param isbn
	 * @param tablename
	 * @return
	 * @throws Exception
	 */
	public boolean check(String isbn,String tablename)throws Exception{
		PreparedStatement statement = con.prepareStatement("SELECT * FROM "+tablename+" WHERE ISBN='"+isbn+"'");//get teh id firstname lastname and time time stamp from table 
		ResultSet result = statement.executeQuery();
		if(result.next()){//check is data exist already 
			return false;
		}//end if
		else{
			return true;
		}//end else
	}//end check
	/**
	 * update the quantity if ther are two same book
	 * @param isbn
	 * @param tablename
	 * @throws Exception
	 */
	public void updateQuantity(String isbn,String tablename)throws Exception{
		PrintWriter printer = new PrintWriter(new FileWriter(output,true));
		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());//new time stamp 
		PreparedStatement update = con.prepareStatement("UPDATE "+tablename+" SET Quantity=Quantity+1 WHERE ISBN='"+isbn+"'");
		update.executeUpdate();//update data
		printer.println("UPDATE "+tablename+" SET Quantity=Quantity+1 WHERE ISBN='"+isbn+"'");
		printer.close();
	}//end updateQuantity
}//end class