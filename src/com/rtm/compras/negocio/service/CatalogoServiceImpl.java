package com.rtm.compras.negocio.service;

import java.util.List;

import com.rtm.compras.model.TTABD;
import com.rtm.compras.persistencia.CatalogoDAO;

public class CatalogoServiceImpl implements CatalogoService{

	CatalogoDAO ttabdDAO = new CatalogoDAO();
	
	@Override
	public List<TTABD> listarCatalogo(String tbiden) {
		return ttabdDAO.listarCatalogo(tbiden);
	}

	@Override
	public int registrar(TTABD catalogo) {
		return ttabdDAO.registrar(catalogo);
	}

	@Override
	public int modificar(TTABD catalogo) {
		return ttabdDAO.modificar(catalogo);
	}

	@Override
	public int eliminar(TTABD catalogo) {
		return ttabdDAO.eliminar(catalogo);
	}

	@Override
	public List<TTABD> buscarCatalogos(TTABD catalogo) {
		return ttabdDAO.buscarCatalogos(catalogo);
	}

	@Override
	public int countReferences(TTABD catalogo) {
		return ttabdDAO.countReferences(catalogo);
	}

	@Override
	public int registrarReferencias(TTABD catalogo) {
		return ttabdDAO.registrarReferencias(catalogo);
	}

	@Override
	public List<TTABD> listarReferencias(TTABD catalogo) {
		return ttabdDAO.listarReferencias(catalogo);
	}

}
