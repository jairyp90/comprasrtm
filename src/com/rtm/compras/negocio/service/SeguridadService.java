package com.rtm.compras.negocio.service;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import com.rtm.compras.model.TPEPE;
import com.rtm.compras.model.TPERS;
import com.rtm.compras.model.TUSUA;

public interface SeguridadService {

	public void validar(TUSUA usuario) throws IOException, SQLException,Exception;
	public TUSUA getUsuario(TUSUA usuario);
	public List<TPERS> listar();
	public List<TPEPE> listarPermisos(TPEPE tpepe);
	public int grabarPermisos(List<TPEPE> permisos);
	public List<TPEPE> listarOpciones(TPEPE tpepe);
	public List<TPEPE> listarSubOpciones(TPEPE tpepe);
	public List<TPEPE> listarOperaciones(TPEPE tpepe);
	
}
