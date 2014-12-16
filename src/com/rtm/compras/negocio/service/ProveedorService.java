package com.rtm.compras.negocio.service;


import java.util.List;
import com.rtm.compras.model.TPROV;

public interface ProveedorService {

	public List<TPROV> listarProveedores();
	public TPROV buscar(TPROV proveedor);
	public List<TPROV> buscarProveedores(TPROV proveedor);
	public int registrar(TPROV proveedor) ;
	public int modificar(TPROV proveedor);
	public int eliminar(TPROV proveedor);
	public int countReferences(TPROV proveedor);
}
