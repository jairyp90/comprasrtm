package com.rtm.compras.negocio.service;

import java.util.List;

import com.rtm.compras.model.TTABD;

public interface CatalogoService {

	public List<TTABD> listarCatalogo(String tbiden);
	public List<TTABD> buscarCatalogos(TTABD catalogo);
	public int registrar(TTABD catalogo);
	public int modificar(TTABD catalogo);
	public int eliminar(TTABD catalogo);
	public int countReferences(TTABD catalogo);
	public int registrarReferencias(TTABD catalogo);
	public List<TTABD> listarReferencias(TTABD catalogo);
}
