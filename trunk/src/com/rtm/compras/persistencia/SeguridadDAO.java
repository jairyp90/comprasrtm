package com.rtm.compras.persistencia;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.rtm.compras.model.TPEPE;
import com.rtm.compras.model.TUSUA;
import com.rtm.dao.mybatis.SessionFactory;
import com.rtm.dao.mybatis.mapper.SeguridadMapper;

public class SeguridadDAO {

	public void validar(TUSUA usuario) throws IOException, SQLException,Exception {
		SqlSession session = SessionFactory.getSqlSessionFactorySecurityValidator(usuario).openSession();
		session.getConnection().isClosed();
		session.close();
	}

	public TUSUA getUsuario(TUSUA usuario) {
		SqlSession session = SessionFactory.getSqlSessionFactory().openSession();
		SeguridadMapper tusuaMapper = session.getMapper(SeguridadMapper.class);
		TUSUA tusua = tusuaMapper.getUsuario(usuario);
		session.close();
		return tusua;
	}
	
	public List<TPEPE> listarPermisos(TPEPE tpepe){
		SqlSession session = SessionFactory.getSqlSessionFactory().openSession();
		SeguridadMapper tusuaMapper = session.getMapper(SeguridadMapper.class);
		List<TPEPE> listado = tusuaMapper.listarPermisos(tpepe);
		session.close();
		return listado;
	}

	public int grabarPermisos(List<TPEPE> permisos) {
		SqlSession session = SessionFactory.getSqlSessionFactory().openSession();
		SeguridadMapper tusuaMapper = session.getMapper(SeguridadMapper.class);
		int result = tusuaMapper.grabarPermisos(permisos);
		session.commit();
		session.close();
		return result;
	}
	
	public List<TPEPE> listarOpciones(TPEPE tpepe){
		SqlSession session = SessionFactory.getSqlSessionFactory().openSession();
		SeguridadMapper tusuaMapper = session.getMapper(SeguridadMapper.class);
		List<TPEPE> listado = tusuaMapper.listarOpciones(tpepe);
		session.close();
		return listado;
	}
	
	public List<TPEPE> listarSubOpciones(TPEPE tpepe){
		SqlSession session = SessionFactory.getSqlSessionFactory().openSession();
		SeguridadMapper tusuaMapper = session.getMapper(SeguridadMapper.class);
		List<TPEPE> listado = tusuaMapper.listarSubOpciones(tpepe);
		session.close();
		return listado;
	}
	
	public List<TPEPE> listarOperaciones(TPEPE tpepe){
		SqlSession session = SessionFactory.getSqlSessionFactory().openSession();
		SeguridadMapper tusuaMapper = session.getMapper(SeguridadMapper.class);
		List<TPEPE> listado = tusuaMapper.listarOperaciones(tpepe);
		session.close();
		return listado;
	}

}
