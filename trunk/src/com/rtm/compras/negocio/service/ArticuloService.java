package com.rtm.compras.negocio.service;


import java.util.List;

import com.rtm.compras.model.TARTI;
import com.rtm.compras.model.TTABD;

public interface ArticuloService {

	public List<TARTI> listar();
	public int registrar(TARTI articulo);
	public int modificar(TARTI articulo);
	public int eliminar(TARTI articulo);
	public int countReferences(TARTI articulo);
	public TTABD buscar(TTABD catalogo);
	public List<TARTI> buscarArticulos(TARTI articulo);
}
