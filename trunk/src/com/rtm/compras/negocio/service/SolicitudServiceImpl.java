package com.rtm.compras.negocio.service;

import java.util.List;

import com.rtm.compras.model.TARTI;
import com.rtm.compras.model.TPROV;
import com.rtm.compras.model.TSOLI;
import com.rtm.compras.model.TSOLID;
import com.rtm.compras.persistencia.ArticuloDAO;
import com.rtm.compras.persistencia.ProveedorDAO;
import com.rtm.compras.persistencia.SolicitudDAO;

public class SolicitudServiceImpl implements SolicitudService{

	SolicitudDAO tsoliDAO = new SolicitudDAO();
	ArticuloDAO tartiDAO = new ArticuloDAO();
	ProveedorDAO tprovDAO = new ProveedorDAO();
	
	@Override
	public List<TSOLI> listarSolicitud(TSOLI slsitu) {
		return tsoliDAO.listarSolicitud(slsitu);
	}

	@Override
	public TARTI buscarArticulo(TARTI articulo) {
		return tartiDAO.buscarArticulo(articulo);
	}

	@Override
	public TPROV buscar(TPROV proveedor) {
		return tprovDAO.buscar(proveedor);
	}

	@Override
	public int registrar(TSOLI tsoli) {
		return tsoliDAO.registrar(tsoli);
	}

	@Override
	public List<TSOLID> getDetalle(TSOLI tsoli) {
		return tsoliDAO.getDetalle(tsoli);
	}

	@Override
	public int modificar(TSOLI tsoli) {
		return tsoliDAO.modificar(tsoli);
	}

	@Override
	public int eliminar(TSOLI tsoli) {
		return tsoliDAO.eliminar(tsoli);
	}

	@Override
	public int modificarEstado(TSOLI tsoli) {
		return tsoliDAO.modificarEstado(tsoli);
	}

}
