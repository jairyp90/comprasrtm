package com.rtm.compras.negocio.service;

import java.util.List;

import com.rtm.compras.model.TARTI;
import com.rtm.compras.model.TTABD;
import com.rtm.compras.persistencia.ArticuloDAO;
import com.rtm.compras.persistencia.CatalogoDAO;

public class ArticuloServiceImpl implements ArticuloService{

	ArticuloDAO tartiDAO = new ArticuloDAO();
	CatalogoDAO ttabdDAO = new CatalogoDAO();
	
	@Override
	public List<TARTI> listar() {
		return tartiDAO.listar();
	}

	@Override
	public int registrar(TARTI articulo) {
		return tartiDAO.registrar(articulo);
	}

	@Override
	public int modificar(TARTI articulo) {
		return tartiDAO.modificar(articulo);
	}

	@Override
	public int eliminar(TARTI articulo) {
		return tartiDAO.eliminar(articulo);
	}

	@Override
	public TTABD buscar(TTABD catalogo) {
		return ttabdDAO.buscar(catalogo);
	}

	@Override
	public List<TARTI> buscarArticulos(TARTI articulo) {
		return tartiDAO.buscarArticulos(articulo);
	}

	@Override
	public int countReferences(TARTI articulo) {
		return tartiDAO.countReferences(articulo);
	}

}
