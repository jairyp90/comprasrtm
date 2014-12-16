package com.rtm.compras.persistencia;


import java.util.List;
import org.apache.ibatis.session.SqlSession;
import com.rtm.compras.model.TARTI;
import com.rtm.dao.mybatis.SessionFactory;
import com.rtm.dao.mybatis.mapper.ArticuloMapper;


public class ArticuloDAO {
	
	public List<TARTI> listar(){
		SqlSession session = SessionFactory.getSqlSessionFactory().openSession();
		ArticuloMapper tartiMapper = session.getMapper(ArticuloMapper.class);
		List<TARTI> listado = tartiMapper.listar();
		session.close();
		return listado;
	}
	
	public TARTI buscarArticulo(TARTI articulo){
		SqlSession session = SessionFactory.getSqlSessionFactory().openSession();
		ArticuloMapper tartiMapper = session.getMapper(ArticuloMapper.class);
		TARTI tarti = tartiMapper.buscarArticulo(articulo);
		session.close();
		return tarti;
	}
	
	public List<TARTI> buscarArticulos(TARTI articulo){
		SqlSession session = SessionFactory.getSqlSessionFactory().openSession();
		ArticuloMapper tartiMapper = session.getMapper(ArticuloMapper.class);
		List<TARTI> listado = tartiMapper.buscarArticulos(articulo);
		session.close();
		return listado;
	}
	   
	
	public int registrar(TARTI articulo){
		SqlSession session = SessionFactory.getSqlSessionFactory().openSession();
		ArticuloMapper tartiMapper = session.getMapper(ArticuloMapper.class);
		int result = tartiMapper.registrar(articulo);
		session.commit();
		session.close();
		return result;   
	}
	
	public int modificar(TARTI articulo){
		SqlSession session = SessionFactory.getSqlSessionFactory().openSession();
		ArticuloMapper tartiMapper = session.getMapper(ArticuloMapper.class);
		int result = tartiMapper.modificar(articulo);
		session.commit();
		session.close();
		return result;   
	}
	
	public int eliminar(TARTI articulo){
		SqlSession session = SessionFactory.getSqlSessionFactory().openSession();
		ArticuloMapper tartiMapper = session.getMapper(ArticuloMapper.class);
		int result = tartiMapper.eliminar(articulo);
		session.commit();
		session.close();
		return result;   
	}

	public int countReferences(TARTI articulo){
		SqlSession session = SessionFactory.getSqlSessionFactory().openSession();
		ArticuloMapper tartiMapper = session.getMapper(ArticuloMapper.class);
		int result = tartiMapper.countReferences(articulo);
		session.commit();
		session.close();
		return result;   
	}
}
