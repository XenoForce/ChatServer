package abc.netio;

import java.net.*;


public class NetworkServer {
  
  //-------------------------------------------------------------------------//
  //  start()                                                                //
  //-------------------------------------------------------------------------//
  public void start( int iPortNo ) throws Exception {
    
    ServerSocket svrSock = new ServerSocket( iPortNo );
    
    while (true) {
      System.out.println("Waiting for new incoming connections.");
      
      Socket sock = svrSock.accept();
      
      System.out.println("New incoming socket connection accepted.");
      
      ServerThread thread = new ServerThread( sock );
      thread.run();
      
      System.out.println("New thread created.");
    } //while
    
  } //start()
  
  
} //class
