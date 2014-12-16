package com.rtm.compras.negocio.service;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import com.rtm.compras.model.TPEPE;
import com.rtm.compras.model.TPERS;
import com.rtm.compras.model.TUSUA;
import com.rtm.compras.persistencia.PersonalDAO;
import com.rtm.compras.persistencia.SeguridadDAO;

public class SeguridadServiceImpl implements SeguridadService{

	SeguridadDAO seguridad = new SeguridadDAO();
	PersonalDAO tpersDAO = new PersonalDAO();
	
	@Override
	public void validar(TUSUA usuario) throws IOException, SQLException,Exception {
		seguridad.validar(usuario);
	}

	@Override
	public TUSUA getUsuario(TUSUA usuario) {
		return seguridad.getUsuario(usuario);
	}

	@Override
	public List<TPEPE> listarPermisos(TPEPE tpepe) {
		return seguridad.listarPermisos(tpepe);
	}
	
	@Override
	public List<TPERS> listar() {
		return tpersDAO.listar();
	}

	@Override
	public int grabarPermisos(List<TPEPE> permisos) {
		return seguridad.grabarPermisos(permisos);
	}

	@Override
	public List<TPEPE> listarOpciones(TPEPE tpepe) {
		return seguridad.listarOpciones(tpepe);
	}

	@Override
	public List<TPEPE> listarSubOpciones(TPEPE tpepe) {
		return seguridad.listarSubOpciones(tpepe);
	}

	@Override
	public List<TPEPE> listarOperaciones(TPEPE tpepe) {
		return seguridad.listarOperaciones(tpepe);
	}

}
