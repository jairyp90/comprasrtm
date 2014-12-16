package com.rtm.compras.negocio.business.delegate;

import com.rtm.compras.negocio.service.CotizacionService;
import com.rtm.compras.negocio.service.CotizacionServiceImpl;


public class GestionCotizacion {
	
	public static CotizacionService getCotizacionService() {
		return new CotizacionServiceImpl();
	}
	
}
