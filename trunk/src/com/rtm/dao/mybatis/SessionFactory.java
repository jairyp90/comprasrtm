package com.rtm.dao.mybatis;

import java.io.IOException;
import java.io.Reader;
import java.util.Properties;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import com.rtm.compras.model.TUSUA;

public class SessionFactory {
    
	 protected static final SqlSessionFactory FACTORY;
	 protected static SqlSessionFactory SECURITY;
	    
	 static{
	      try{
	            Reader reader= Resources.getResourceAsReader("com/rtm/dao/mybatis/config/mybatis-config.xml");
	            FACTORY= new SqlSessionFactoryBuilder().build(reader);
	            System.out.println("Ingreso a SqlSessionFactory");
	      }catch(Exception e){
	        	e.printStackTrace();
	        	System.out.println("Ingreso a ERROR SqlSessionFactory");
	        	throw new RuntimeException("Error: " + e);
	      }
	 }
	  
	 public static SqlSessionFactory getSqlSessionFactory(){
	      return FACTORY;
	 }
	 
	 public static SqlSessionFactory getSqlSessionFactorySecurityValidator(TUSUA usuario) throws IOException{
		 Reader readerAS400= Resources.getResourceAsReader("com/rtm/dao/mybatis/config/mybatis-config-as400.xml");
		 Properties props = new Properties();
		 props.put("username", usuario.getUsucve().trim());
		 props.put("password", usuario.getUsucon().trim());
		 SECURITY= new SqlSessionFactoryBuilder().build(readerAS400,props);
	     return SECURITY;
	 }
	 
}
