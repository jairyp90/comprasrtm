package com.rtm.compras.negocio.service;

import java.util.List;

import com.rtm.compras.model.TARTI;
import com.rtm.compras.model.TPROV;
import com.rtm.compras.model.TSOLI;
import com.rtm.compras.model.TSOLID;

public interface SolicitudService {
	
	public List<TSOLI> listarSolicitud(TSOLI slsitu);
	public TARTI buscarArticulo(TARTI articulo);
	public TPROV buscar(TPROV proveedor);
	public int registrar(TSOLI tsoli);
	public int modificarEstado(TSOLI tsoli);
	public int modificar(TSOLI tsoli);
	public int eliminar(TSOLI tsoli);
	public List<TSOLID> getDetalle(TSOLI tsoli);

}
