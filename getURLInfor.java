import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Date;

/**
 * class to get urlinfo
 * @author xiao lin
 *
 */
public class getURLInfor{
	static int htmlCount=0;
	static int imageCount=0;
	URL url;
	URLConnection connection;
		
	public getURLInfor(String filename) throws IOException{
		
		url=new URL(filename);
		connection=url.openConnection();
	}
	
	/**
	 * print url info to output
	 * @param printer
	 * @throws IOException
	 */
	 public void printURLinfo(PrintWriter printer) throws IOException {

		// Display the URL address, and information about it.

		printer.println(connection.getURL().toExternalForm() + ":");

		printer.println(" Content Type: " + connection.getContentType());

		printer.println(" Content Length: " + connection.getContentLength());

		printer.println(" Last Modified: " + new Date(connection.getLastModified()));

		printer.println(" Expiration: " + connection.getExpiration());

		printer.println(" Content Encoding: " + connection.getContentEncoding());
		
		} // printURLinfo 
	 /**
	  * copy html file
	  * @param printer
	  * @throws Exception
	  */
	 public void printContent(PrintWriter printer) throws Exception{
		htmlCount++;
		String outputFile="html"+htmlCount+".html" ;
		BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
		Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outputFile), "utf-8"));
		String line=reader.readLine();
		int count=0;
		while(line!=null){
			writer.write(line+"\n");//copy every line to output 
			line=reader.readLine();
			count++;
		}
		printer.println("html file contains "+count+" length.");
		writer.close();
		reader.close();
	}//end printContent
	 /**
	  * copy the image 
	  * @param output
	  * @throws IOException
	  */
	public void getImage() throws IOException {
		String destinationFile = "/search.jpg";
		InputStream is = url.openStream();
		OutputStream os = new FileOutputStream(destinationFile);
		byte[] b = new byte[2048];
		int length;
		while ((length = is.read(b)) != -1) {
			os.write(b, 0, length);//copy every pixels to output 
		}
		is.close();
		os.close();
	
		
	}//end getImage
}//end class