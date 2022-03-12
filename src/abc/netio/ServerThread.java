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
        
        if (ConnectionTypes.PASSIVE_CLIENT.equals( str )) {
          
        }
        else if (ConnectionTypes.ACTIVE_CLIENT.equals( str )) {
          
        } //if
        
      } //if
    }
    catch (Exception ex) {
      
    } //try
    
  } //run()
  
  
} //class
