import java.net.*;
import java.awt.AWTException;
import java.io.*;
import java.util.concurrent.*;

public class HttpSrv {
	
	
	
	public static void main( String argv[] ) throws IOException, AWTException {
		
		System.out.println( "RPi Robot software is running" );
		Receiver rcv= new Receiver();
		
		Executor executor = Executors.newFixedThreadPool(3);
		ServerSocket ss = new ServerSocket( 80 );
		while ( true ){
			executor.execute( new HttpdConnection( ss.accept(), rcv ) );
		}
	 }
}

class HttpdConnection implements Runnable {
  
  Socket client;
  Receiver rcv;
  
  HttpdConnection ( Socket client, Receiver rcv ) throws SocketException {
    this.client = client;
    this.rcv= rcv;
    System.out.println( "New connection established" );
  }
  
  public void run() {
    
	  try {
      
    	BufferedReader in = new BufferedReader(
        new InputStreamReader(client.getInputStream(), "8859_1" ) );
    	OutputStream out = client.getOutputStream();
    	PrintWriter pout = new PrintWriter(new OutputStreamWriter(out, "8859_1"), true );
    	String request;
    	while ((request = in.readLine()) != null) {
    		System.out.println( "Request: "+request);
    		pout.println( "I am server, I received: " +request);
      
    		if(request!=null){ // sometimes
    			rcv.processMsg( request );
    		}
    		else{
    			System.out.println( "null received???? " );
    		}
    	}
    	client.close();
    	System.out.println( "The session is closed");
	  } catch ( IOException e ) {
		  System.out.println( "I/O error " + e ); } catch (AWTException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} 
  }
   
}
