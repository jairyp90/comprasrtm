package com.rtm.compras.negocio.business.delegate;

import com.rtm.compras.negocio.service.ProveedorService;
import com.rtm.compras.negocio.service.ProveedorServiceImpl;

public class GestionProveedor {
	
	public static ProveedorService getTPROVService() {
		return new ProveedorServiceImpl();
	}

}
