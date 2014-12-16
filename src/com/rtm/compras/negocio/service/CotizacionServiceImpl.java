package com.rtm.compras.negocio.service;

import java.util.List;

import com.rtm.compras.model.TPROV;
import com.rtm.compras.model.TSOLI;
import com.rtm.compras.model.TSOLID;
import com.rtm.compras.persistencia.ProveedorDAO;
import com.rtm.compras.persistencia.SolicitudDAO;

public class CotizacionServiceImpl implements CotizacionService{
	
	SolicitudDAO tsoliDAO = new SolicitudDAO();
	ProveedorDAO tprovDAO = new ProveedorDAO();
	
	@Override
	public List<TSOLI> listarSolicitud(TSOLI slsitu) {
		return tsoliDAO.listarSolicitud(slsitu);
	}

	@Override
	public List<TSOLID> getDetalle(TSOLI solicitud) {
		return tsoliDAO.getDetalle(solicitud);
	}

	@Override
	public int modificarEstado(TSOLI solicitud) {
		return tsoliDAO.modificarEstado(solicitud);
	}

	@Override
	public List<TPROV> listarProveedores() {
		return tprovDAO.listarProveedores();
	}

}
