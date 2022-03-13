package abc.netio;

import java.net.*;
import java.util.*;


public class PassiveSet {
  
  //-------------------------------------------------------------------------//
  //  Attributes                                                             //
  //-------------------------------------------------------------------------//
  private static Map<String, List<Socket>>  dataStruct = new Hashtable<>();
  
  
  //-------------------------------------------------------------------------//
  //  add()                                                                  //
  //-------------------------------------------------------------------------//
  public static synchronized void add( String  chatUser,
                                       Socket  sock ) {
    
    List<Socket> list = dataStruct.get( chatUser );
    
    if (null == list) {
      list = new ArrayList<>();
      dataStruct.put( chatUser, list );
    } //if
    
    list.add( sock );
  } //add()
  
  
} //class
