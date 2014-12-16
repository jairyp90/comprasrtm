package com.rtm.compras.negocio.service;


import java.util.List;
import com.rtm.compras.model.TPROV;
import com.rtm.compras.persistencia.ProveedorDAO;


public class ProveedorServiceImpl implements ProveedorService{

	ProveedorDAO tprovDAO = new ProveedorDAO();
	
	@Override
	public List<TPROV> listarProveedores() {
		return tprovDAO.listarProveedores();
	}

	@Override
	public int registrar(TPROV proveedor) {
		return tprovDAO.registrar(proveedor);
	}

	@Override
	public int modificar(TPROV proveedor) {
		return tprovDAO.modificar(proveedor);
	}

	@Override
	public int eliminar(TPROV proveedor) {
		return tprovDAO.eliminar(proveedor);
	}

	@Override
	public TPROV buscar(TPROV proveedor) {
		return tprovDAO.buscar(proveedor);
	}

	@Override
	public List<TPROV> buscarProveedores(TPROV proveedor) {
		return tprovDAO.buscarProveedores(proveedor);
	}

	@Override
	public int countReferences(TPROV proveedor) {
		return tprovDAO.countReferences(proveedor);
	}

}
