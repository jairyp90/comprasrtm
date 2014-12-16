package com.rtm.compras.negocio.business.delegate;

import com.rtm.compras.negocio.service.ArticuloService;
import com.rtm.compras.negocio.service.ArticuloServiceImpl;

public class GestionArticulo {

	public static ArticuloService getTARTIService() {
		return new ArticuloServiceImpl();
	}
	
}
