package com.rtm.compras.persistencia;

import java.util.List;
import org.apache.ibatis.session.SqlSession;
import com.rtm.compras.model.TSOLI;
import com.rtm.compras.model.TSOLID;
import com.rtm.dao.mybatis.SessionFactory;
import com.rtm.dao.mybatis.mapper.SolicitudMapper;

public class SolicitudDAO {
	
	public List<TSOLI> listarSolicitud(TSOLI slsitu){
		SqlSession session = SessionFactory.getSqlSessionFactory().openSession();
		SolicitudMapper tsoliMapper = session.getMapper(SolicitudMapper.class);
		List<TSOLI> listado = tsoliMapper.listarSolicitud(slsitu);
		session.close();
		return listado;
	}
	
	public int registrar(TSOLI tsoli){
		SqlSession session = SessionFactory.getSqlSessionFactory().openSession();
		SolicitudMapper tsoliMapper = session.getMapper(SolicitudMapper.class);
		int result = tsoliMapper.registrar(tsoli);
		session.commit();
		session.close();		
		return result;   
	}
	
	public int modificarEstado(TSOLI tsoli){
		SqlSession session = SessionFactory.getSqlSessionFactory().openSession();
		SolicitudMapper tsoliMapper = session.getMapper(SolicitudMapper.class);
		int result = tsoliMapper.modificarEstado(tsoli);
		session.commit();
		session.close();
		return result;
	}
	
	public int modificar(TSOLI tsoli){
		SqlSession session = SessionFactory.getSqlSessionFactory().openSession();
		SolicitudMapper tsoliMapper = session.getMapper(SolicitudMapper.class);
		int result = tsoliMapper.modificar(tsoli);
		session.commit();
		session.close();
		return result;
	}
	
	public int eliminar(TSOLI tsoli){
		SqlSession session = SessionFactory.getSqlSessionFactory().openSession();
		SolicitudMapper tsoliMapper = session.getMapper(SolicitudMapper.class);
		int result = tsoliMapper.eliminar(tsoli);
		session.commit();
		session.close();
		return result;
	}
	
	public List<TSOLID> getDetalle(TSOLI tsoli){
		SqlSession session = SessionFactory.getSqlSessionFactory().openSession();
		SolicitudMapper tsoliMapper = session.getMapper(SolicitudMapper.class);
		List<TSOLID> listado = tsoliMapper.getDetalle(tsoli);
		session.close();
		return listado;
	}
	
}
