package abc.netio;

import java.io.*;
import java.net.*;


public class ServerThread extends Thread {
  
  //-------------------------------------------------------------------------//
  //  Attributes                                                             //
  //-------------------------------------------------------------------------//
  private Socket  sock ;
  
  
  //-------------------------------------------------------------------------//
  //  Constructor                                                            //
  //-------------------------------------------------------------------------//
  public ServerThread( Socket  socket ) {
    
    sock = socket;
  } //Constructor
  
  
  //-------------------------------------------------------------------------//
  //  run()                                                                  //
  //-------------------------------------------------------------------------//
  @Override
  public void run() {
    
    try {
      InputStream        inS = sock.getInputStream();
      ObjectInputStream  ois = new ObjectInputStream( inS );
      Object             obj = ois.readObject();
      
      if (obj instanceof String) {
        String str = (String) obj;
        
        if (ConnectionTypes)
        
      } //if
    }
    catch (Exception ex) {
      
    } //try
    
  } //run()
  
  
} //class
