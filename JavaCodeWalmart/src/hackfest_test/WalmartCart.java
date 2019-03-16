package hackfest_test;
import java.sql.*;
import java.net.*;
import java.io.*;
import java.util.*;
import com.fazecast.jSerialComm.*;
public class WalmartCart{
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String JDBC_URL = "jdbc:mysql://localhost/product_list";
    static final String USER = "root";
    static final String PASS = "admin";
    static Connection  conn = null;
    static Statement stmt = null;
    static String portName = "COM3";
    public static void main(String args[]) {
    	try {
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("connecting to database....");
            conn = DriverManager.getConnection(JDBC_URL,USER,PASS);
            System.out.println("done");
            stmt = conn.createStatement();
           
        } catch (Exception e) {
            e.printStackTrace();
        }
    	SerialPort ports[] = SerialPort.getCommPorts();
    	for(SerialPort port : ports) {
    		if(port.getSystemPortName().toString().equals(portName)) {
    			if(port.openPort()) {
    				System.out.println("port Open");
    				port.setComPortTimeouts(SerialPort.TIMEOUT_READ_SEMI_BLOCKING, 0, 0);
    				Scanner data = new Scanner(port.getInputStream());
                    while(data.hasNextLine()) {
                            String number = null;
                            try{
                            	number = data.nextLine().toString();
                            	System.out.println(number);
                            	 String sql;
                                 sql = "select * from items where id=\"" + number +"\"";
                                 ResultSet rs = stmt.executeQuery(sql);
                                 while(rs.next()) {
                                 	String id = rs.getString("id");
                                 	String name  = rs.getString("name");
                                 	double price = rs.getDouble("price");
                                 	System.out.println(id + " " + name + " " + price);
                                 }
                            }catch(Exception e){
                            	
                            }
                            
                    }
    			}else {
    				System.out.println("cannot open port");
    				return;
    			}
    		}
    	}
    	
    	
	}
   /* String getItemFromTag(String tag) throws Exception{
    	URL url = new URL("https://devsena-hf-19.firebaseio.com/items.json?orderBy=tag&equalTo=\"1004\"");
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");

        int status = con.getResponseCode();
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer content = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        in.close();
        
        System.out.println(content.toString());
    }*/
    	
        
    }
	
    