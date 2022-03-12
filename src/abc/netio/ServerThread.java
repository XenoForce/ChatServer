package abc.netio;

import abc.json.JsonConnTypeUtil;
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
        String           str     = (String) obj;
        ConnTypeRequest  req     = JsonConnTypeUtil.jsonToRequest( str );
        String           theType = req.connectionType;
        
        if (null != theType) {
          if (ConnectionTypes.PASSIVE_CLIENT.equals( theType )) {
            
          }
          else if (ConnectionTypes.ACTIVE_CLIENT.equals( theType )) {
            
          } //if
        } //if
      } //if
    }
    catch (Exception ex) {
      
    } //try
    
  } //run()
  
  
} //class
