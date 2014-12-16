package com.rtm.compras.negocio.business.delegate;

import com.rtm.compras.negocio.service.SolicitudService;
import com.rtm.compras.negocio.service.SolicitudServiceImpl;


public class GestionSolicitud {

	public static SolicitudService getTSOLIService() {
		return new SolicitudServiceImpl();
	}
}
