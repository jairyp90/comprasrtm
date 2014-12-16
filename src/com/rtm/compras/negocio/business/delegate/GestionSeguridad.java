package com.rtm.compras.negocio.business.delegate;

import com.rtm.compras.negocio.service.PersonalService;
import com.rtm.compras.negocio.service.PersonalServiceImpl;
import com.rtm.compras.negocio.service.SeguridadService;
import com.rtm.compras.negocio.service.SeguridadServiceImpl;

public class GestionSeguridad {

	public static SeguridadService getSeguridadService() {
		return new SeguridadServiceImpl();
	}
	
	public static PersonalService getPersonalService(){
		return new PersonalServiceImpl();
	}
	
	
	
}
