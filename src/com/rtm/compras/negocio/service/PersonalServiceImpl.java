package com.rtm.compras.negocio.service;

import java.util.List;
import com.rtm.compras.model.TPERS;
import com.rtm.compras.model.TTABD;
import com.rtm.compras.persistencia.CatalogoDAO;
import com.rtm.compras.persistencia.PersonalDAO;

public class PersonalServiceImpl implements PersonalService{
	PersonalDAO tpersDAO = new PersonalDAO();
	CatalogoDAO ttabdDAO = new CatalogoDAO();
	
	@Override
	public List<TPERS> listar() {
		return tpersDAO.listar();
	}

	@Override
	public List<TTABD> buscarCatalogos(TTABD catalogo) {
		return ttabdDAO.buscarCatalogos(catalogo);
	}

	@Override
	public int registrar(TPERS personal) {
		return tpersDAO.registrar(personal);
	}

	@Override
	public int modificar(TPERS personal) {
		return tpersDAO.modificar(personal);
	}

	@Override
	public int eliminar(TPERS personal) {
		return tpersDAO.eliminar(personal);
	}

	@Override
	public int countReferences(TPERS personal) {
		return tpersDAO.countReferences(personal);
	}

}
