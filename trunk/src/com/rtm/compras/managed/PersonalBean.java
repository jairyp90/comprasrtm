package com.rtm.compras.managed;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import org.primefaces.context.RequestContext;

import com.rtm.compras.model.TPERS;
import com.rtm.compras.model.TTABD;
import com.rtm.compras.negocio.business.delegate.GestionSeguridad;
import com.rtm.compras.negocio.service.PersonalService;
import com.rtm.compras.resource.Sesion;

@ManagedBean(name = "tpers")
@SessionScoped
public class PersonalBean {

	private PersonalService servicio = GestionSeguridad.getPersonalService();
	private List<TPERS> personales = new ArrayList<TPERS>();
	private List<SelectItem> areaSelector;
	private List<SelectItem> cargoSelector;
	private List<SelectItem> jefeSelector;
	private List<TPERS> fpersonales;
	private TPERS personal = new TPERS();
	private TPERS bpersonal = new TPERS();
	private int operacion = 0;
	private String mensaje = "";
	private String titulo = "";
	@ManagedProperty("#{tusua}")
	private SesionBean sesion;

	public PersonalBean() {
		System.out.println("construyendo PersonalBean");
		if(FacesContext.getCurrentInstance().isPostback()==false){
			try {
				FacesContext.getCurrentInstance().getExternalContext().redirect("/ProyCompras/");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void postProcessXLS(Object document) {
		Sesion.formateaExcel(document);
	}
	
	public void cargar() {
		personales = servicio.listar();
		RequestContext.getCurrentInstance().update("mantenerPersonal");
	}
	
	public void registrar() {
		operacion = 0;
		personal = new TPERS();
		personal.setPljefe(new TPERS());
		
		areaSelector = new ArrayList<SelectItem>();
		
		TTABD catalogo = new TTABD();
		catalogo.setTbiden("AREAS");
		List<TTABD> areas = servicio.buscarCatalogos(catalogo);
		for (TTABD ttabd : areas) {
			areaSelector.add(new SelectItem(ttabd.getTbespe(),ttabd.getDesesp()));
		}
		
		cargoSelector = new ArrayList<SelectItem>();
		
		catalogo.setTbiden("CARGO");
		List<TTABD> cargos = servicio.buscarCatalogos(catalogo);
		for (TTABD ttabd : cargos) {
			cargoSelector.add(new SelectItem(ttabd.getTbespe(),ttabd.getDesesp()));
		}
		
		jefeSelector = new ArrayList<SelectItem>();
		
		for (TPERS tpers : personales) {
			jefeSelector.add(new SelectItem(tpers.getPlcodi(),tpers.getPlnom1() + " " +tpers.getPlnom2() + " "+tpers.getPlapep()+" "+tpers.getPlapem() ));
		}
	}
	
	public String modificar() {
		if (personal != null) {
			if (!personal.getPlcodi().equals("")) {
				operacion = 1;
				
				areaSelector = new ArrayList<SelectItem>();
				
				TTABD catalogo = new TTABD();
				catalogo.setTbiden("AREAS");
				List<TTABD> areas = servicio.buscarCatalogos(catalogo);
				for (TTABD ttabd : areas) {
					areaSelector.add(new SelectItem(ttabd.getTbespe(),ttabd.getDesesp()));
				}
				
				cargoSelector = new ArrayList<SelectItem>();
				
				catalogo.setTbiden("CARGO");
				List<TTABD> cargos = servicio.buscarCatalogos(catalogo);
				for (TTABD ttabd : cargos) {
					cargoSelector.add(new SelectItem(ttabd.getTbespe(),ttabd.getDesesp()));
				}
				
				jefeSelector = new ArrayList<SelectItem>();
				
				for (TPERS tpers : personales) {
					jefeSelector.add(new SelectItem(tpers.getPlcodi(),tpers.getPlnom1() + " " +tpers.getPlnom2() + " "+tpers.getPlapep()+" "+tpers.getPlapem() ));
				}
				
				return "/paginas/personal";
			} else {
				mensaje = "Seleccione un registro.";
				RequestContext.getCurrentInstance().execute("PF('dlg').show()");
			}
		} else {
			mensaje = "Seleccione un registro.";
			RequestContext.getCurrentInstance().execute("PF('dlg').show()");
		}
		return null;
	}
	
	public void eliminar() {
		if (personal != null) {
			if (!personal.getPlcodi().equals("")) {
				int count = servicio.countReferences(personal);
				if (count == 0) {
					int result = servicio.eliminar(personal);
					if (result > 0) {
						personales.remove(personal);
						titulo = "Mensaje de Información";
						mensaje = "Se ha eliminado correctamente";
						RequestContext.getCurrentInstance().execute(
								"PF('dlg').show()");
					} else {
						titulo = "Mensaje de Error";
						mensaje = "Error al eliminar personal (SQL).";
						RequestContext.getCurrentInstance().execute(
								"PF('dlg').show()");
					}
				} else {
					mensaje = "El personal no se puede eliminar porque esta siendo usando en solicitudes.";
					RequestContext.getCurrentInstance().update("dlg");
					RequestContext.getCurrentInstance().execute(
							"PF('dlg').show()");
				}

			} else {
				mensaje = "Seleccione un registro.";
				RequestContext.getCurrentInstance().execute("PF('dlg').show()");
			}
		} else {
			mensaje = "Seleccione un registro.";
			RequestContext.getCurrentInstance().execute("PF('dlg').show()");
		}
	}
	
	public void guardar() {
		if (operacion == 0) {
			personal.setPlusin(sesion.getUsuario().getUsucve());
			personal.setError(false);
			int result = servicio.registrar(personal);
			if (result > 0) {
				personal.setError(false);
				titulo = "Mensaje de Información";
				mensaje = "Personal registrado con el código: "
						+ personal.getPlcodi();
				RequestContext.getCurrentInstance().execute(
						"PF('dlg').show()");
			} else {
				personal.setError(true);
				titulo = "Mensaje de Error";
				mensaje = "Error al registrar personal (SQL).";
				RequestContext.getCurrentInstance().execute(
						"PF('dlg').show()");
			}
		}else{
			personal.setPlusmd(sesion.getUsuario().getUsucve());
			personal.setPlfemd(Calendar.getInstance().getTime());
			int result = servicio.modificar(personal);
			if (result > 0) {
				personal.setError(false);
				titulo = "Mensaje de Información";
				mensaje = "Personal modificado";
				RequestContext.getCurrentInstance().execute(
						"PF('dlg').show()");
			} else {
				personal.setError(true);
				titulo = "Mensaje de Error";
				mensaje = "Error al modificar personal (SQL).";
				RequestContext.getCurrentInstance().execute(
						"PF('dlg').show()");
			}
		}
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public SesionBean getSesion() {
		return sesion;
	}

	public void setSesion(SesionBean seguridad) {
		this.sesion = seguridad;
	}

	public List<TPERS> getPersonales() {
		return personales;
	}

	public void setPersonales(List<TPERS> personales) {
		this.personales = personales;
	}

	public List<TPERS> getFpersonales() {
		return fpersonales;
	}

	public void setFpersonales(List<TPERS> fpersonales) {
		this.fpersonales = fpersonales;
	}

	public TPERS getPersonal() {
		return personal;
	}

	public void setPersonal(TPERS personal) {
		this.personal = personal;
	}

	public TPERS getBpersonal() {
		return bpersonal;
	}

	public void setBpersonal(TPERS bpersonal) {
		this.bpersonal = bpersonal;
	}

	public int getOperacion() {
		return operacion;
	}

	public void setOperacion(int operacion) {
		this.operacion = operacion;
	}

	public List<SelectItem> getAreaSelector() {
		return areaSelector;
	}

	public void setAreaSelector(List<SelectItem> areaSelector) {
		this.areaSelector = areaSelector;
	}

	public List<SelectItem> getJefeSelector() {
		return jefeSelector;
	}

	public void setJefeSelector(List<SelectItem> jefeSelector) {
		this.jefeSelector = jefeSelector;
	}

	public List<SelectItem> getCargoSelector() {
		return cargoSelector;
	}

	public void setCargoSelector(List<SelectItem> cargoSelector) {
		this.cargoSelector = cargoSelector;
	}
	
	
}
