package com.rtm.compras.negocio.service;

import java.util.List;
import com.rtm.compras.model.TPERS;
import com.rtm.compras.model.TTABD;

public interface PersonalService {

	public List<TPERS> listar();
	public List<TTABD> buscarCatalogos(TTABD catalogo);
	public int registrar(TPERS personal);
	public int modificar(TPERS personal);
	public int eliminar(TPERS personal);
	public int countReferences(TPERS personal);
}
