package com.rtm.compras.persistencia;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.rtm.compras.model.TTABD;
import com.rtm.dao.mybatis.SessionFactory;
import com.rtm.dao.mybatis.mapper.CatalogoMapper;

public class CatalogoDAO {

	public List<TTABD> listarCatalogo(String tbiden) {
		SqlSession session = SessionFactory.getSqlSessionFactory().openSession();
		CatalogoMapper ttabdMapper = session.getMapper(CatalogoMapper.class);
		List<TTABD> listado = ttabdMapper.listarCatalogo(tbiden);
		session.close();
		return listado;
	}
	
	public TTABD buscar(TTABD catalogo){
		SqlSession session = SessionFactory.getSqlSessionFactory().openSession();
		CatalogoMapper ttabdMapper = session.getMapper(CatalogoMapper.class);
		TTABD listado = ttabdMapper.buscar(catalogo);
		session.close();
		return listado;
	}
	
	public List<TTABD> buscarCatalogos(TTABD catalogo){
		SqlSession session = SessionFactory.getSqlSessionFactory().openSession();
		CatalogoMapper ttabdMapper = session.getMapper(CatalogoMapper.class);
		List<TTABD> listado = ttabdMapper.buscarCatalogos(catalogo);
		session.close();
		return listado;
	}
	
	
	public int registrar(TTABD catalogo){
		SqlSession session = SessionFactory.getSqlSessionFactory().openSession();
		CatalogoMapper ttabdMapper = session.getMapper(CatalogoMapper.class);
		int result = ttabdMapper.registrar(catalogo);
		session.commit();
		session.close();
		return result;
	}
	   
	public int modificar(TTABD catalogo){
		SqlSession session = SessionFactory.getSqlSessionFactory().openSession();
		CatalogoMapper ttabdMapper = session.getMapper(CatalogoMapper.class);
		int result = ttabdMapper.modificar(catalogo);
		session.commit();
		session.close();
		return result;
	}
	
	public int eliminar(TTABD catalogo){
		SqlSession session = SessionFactory.getSqlSessionFactory().openSession();
		CatalogoMapper ttabdMapper = session.getMapper(CatalogoMapper.class);
		int result = ttabdMapper.eliminar(catalogo);
		session.commit();
		session.close();
		return result;
	}
	
	public int countReferences(TTABD catalogo){
		SqlSession session = SessionFactory.getSqlSessionFactory().openSession();
		CatalogoMapper ttabdMapper = session.getMapper(CatalogoMapper.class);
		int result = ttabdMapper.countReferences(catalogo);
		session.commit();
		session.close();
		return result;
	}

	public int registrarReferencias(TTABD catalogo) {
		SqlSession session = SessionFactory.getSqlSessionFactory().openSession();
		CatalogoMapper ttabdMapper = session.getMapper(CatalogoMapper.class);
		int result = ttabdMapper.registrarReferencias(catalogo);
		session.commit();
		session.close();
		return result;
	}

	public List<TTABD> listarReferencias(TTABD catalogo) {
		SqlSession session = SessionFactory.getSqlSessionFactory().openSession();
		CatalogoMapper ttabdMapper = session.getMapper(CatalogoMapper.class);
		//System.out.println(tbiden +" "+tbespe);
		List<TTABD> listado = ttabdMapper.listarReferencias(catalogo);
		session.close();
		return listado;
	}
	
}
