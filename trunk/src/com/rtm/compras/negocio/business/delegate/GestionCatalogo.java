package com.rtm.compras.negocio.business.delegate;

import com.rtm.compras.negocio.service.CatalogoService;
import com.rtm.compras.negocio.service.CatalogoServiceImpl;

public class GestionCatalogo {

	public static CatalogoService getTTABDService() {
		return new CatalogoServiceImpl();
	}
	
}
