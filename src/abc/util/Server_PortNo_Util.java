package abc.util;


public class Server_PortNo_Util {
  
  //-------------------------------------------------------------------------//
  //  getServerPortNo()                                                      //
  //-------------------------------------------------------------------------//
  public static int getServerPortNo( String  sPortNo ) {
    
    int retVal = 1234;
    
    try {
      retVal = Integer.parseInt( sPortNo );
    }
    catch (Exception ex) {
      //no-op.
    } //try
    
    return retVal;
  } //getServerPortNo()
  
  
} //class
