package com.rtm.compras.persistencia;


import java.util.List;
import org.apache.ibatis.session.SqlSession;
import com.rtm.compras.model.TPROV;
import com.rtm.dao.mybatis.SessionFactory;
import com.rtm.dao.mybatis.mapper.ProveedorMapper;

public class ProveedorDAO {

	public List<TPROV> listarProveedores(){
		SqlSession session = SessionFactory.getSqlSessionFactory().openSession();
		ProveedorMapper tprovMapper = session.getMapper(ProveedorMapper.class);
		List<TPROV> listado = tprovMapper.listarProveedores();
		session.close();
		return listado;
	}
	
	public  TPROV buscar(TPROV proveedor){
		SqlSession session = SessionFactory.getSqlSessionFactory().openSession();
		ProveedorMapper tprovMapper = session.getMapper(ProveedorMapper.class);
		TPROV result = tprovMapper.buscar(proveedor);
		session.close();
		return result;
	}
	
	public  List<TPROV> buscarProveedores(TPROV proveedor){
		SqlSession session = SessionFactory.getSqlSessionFactory().openSession();
		ProveedorMapper tprovMapper = session.getMapper(ProveedorMapper.class);
		List<TPROV> listado = tprovMapper.buscarProveedores(proveedor);
		session.close();
		return listado;  
	}
	
	public int registrar(TPROV proveedor){
		SqlSession session = SessionFactory.getSqlSessionFactory().openSession();
		ProveedorMapper tprovMapper = session.getMapper(ProveedorMapper.class);
		int result = tprovMapper.registrar(proveedor);
		session.commit();
		session.close();
		return result;
	}
	
	public int modificar(TPROV proveedor){
		SqlSession session = SessionFactory.getSqlSessionFactory().openSession();
		ProveedorMapper tprovMapper = session.getMapper(ProveedorMapper.class);
		int result = tprovMapper.modificar(proveedor);
		session.commit();
		session.close();
		return result;
	}
	
	public int eliminar(TPROV proveedor){
		SqlSession session = SessionFactory.getSqlSessionFactory().openSession();
		ProveedorMapper tprovMapper = session.getMapper(ProveedorMapper.class);
		int result = tprovMapper.eliminar(proveedor);
		session.commit();
		session.close();
		return result;
	}
	
	public  int countReferences(TPROV proveedor){
		SqlSession session = SessionFactory.getSqlSessionFactory().openSession();
		ProveedorMapper tprovMapper = session.getMapper(ProveedorMapper.class);
		int result = tprovMapper.countReferences(proveedor);
		session.commit();
		session.close();
		return result;
	}
}
