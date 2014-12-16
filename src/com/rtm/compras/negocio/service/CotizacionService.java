package com.rtm.compras.negocio.service;

import java.util.List;

import com.rtm.compras.model.TPROV;
import com.rtm.compras.model.TSOLI;
import com.rtm.compras.model.TSOLID;

public interface CotizacionService {
	
	public List<TSOLI> listarSolicitud(TSOLI slsitu);
	public List<TSOLID> getDetalle(TSOLI solicitud);
	public int modificarEstado(TSOLI solicitud);
	public List<TPROV> listarProveedores();

}
