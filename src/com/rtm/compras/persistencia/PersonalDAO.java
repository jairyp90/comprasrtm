package com.rtm.compras.persistencia;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import com.rtm.compras.model.TPERS;
import com.rtm.dao.mybatis.SessionFactory;
import com.rtm.dao.mybatis.mapper.PersonalMapper;

public class PersonalDAO {

	public List<TPERS> listar(){
		SqlSession session = SessionFactory.getSqlSessionFactory().openSession();
		PersonalMapper tpersMapper = session.getMapper(PersonalMapper.class);
		List<TPERS> listado = tpersMapper.listar();
		session.close();
		return listado;
	}

	public int registrar(TPERS personal) {
		SqlSession session = SessionFactory.getSqlSessionFactory().openSession();
		PersonalMapper tprovMapper = session.getMapper(PersonalMapper.class);
		int result = tprovMapper.registrar(personal);
		session.commit();
		session.close();
		return result;
	}

	public int modificar(TPERS personal) {
		SqlSession session = SessionFactory.getSqlSessionFactory().openSession();
		PersonalMapper tprovMapper = session.getMapper(PersonalMapper.class);
		int result = tprovMapper.modificar(personal);
		session.commit();
		session.close();
		return result;
	}
	
	public int eliminar(TPERS personal){
		SqlSession session = SessionFactory.getSqlSessionFactory().openSession();
		PersonalMapper tpersMapper = session.getMapper(PersonalMapper.class);
		int result = tpersMapper.eliminar(personal);
		session.commit();
		session.close();
		return result;
	}
	
	public  int countReferences(TPERS personal){
		SqlSession session = SessionFactory.getSqlSessionFactory().openSession();
		PersonalMapper tpersMapper = session.getMapper(PersonalMapper.class);
		int result = tpersMapper.countReferences(personal);
		session.commit();
		session.close();
		return result;
	}	
}
