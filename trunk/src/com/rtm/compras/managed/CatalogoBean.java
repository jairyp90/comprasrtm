	package com.rtm.compras.managed;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.primefaces.context.RequestContext;

import com.rtm.compras.model.TTABD;
import com.rtm.compras.negocio.business.delegate.GestionCatalogo;
import com.rtm.compras.negocio.service.CatalogoService;
import com.rtm.compras.resource.Sesion;


@ManagedBean(name = "ttabd")
@SessionScoped
public class CatalogoBean implements Serializable {
	private static final long serialVersionUID = 1L;
	private CatalogoService servicio = GestionCatalogo.getTTABDService();
	private List<TTABD> catalogos = new ArrayList<TTABD>();
	private List<TTABD> referencias = new ArrayList<TTABD>();
	private List<TTABD> detalles = new ArrayList<TTABD>();
	private List<TTABD> fcatalogos;
	private TTABD catalogo = new TTABD();
	private TTABD referencia = new TTABD();
	private TTABD bcatalogo = new TTABD();
	private int operacion = 0;
	private String tbiden;
	private String tbref1;
	private String mensaje = "";
	private String titulo = "";
	@ManagedProperty("#{tusua}")
	private SesionBean sesion;

	public CatalogoBean() {
		System.out.println("construyendo CatalogoBean");
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

	// ********************************************************************************
	public void buscar() {
		bcatalogo.setTbespe(bcatalogo.getTbespe().trim().equals("") ? "%"
				: bcatalogo.getTbespe().trim().toUpperCase());
		bcatalogo.setDesesp(bcatalogo.getDesesp().trim().equals("") ? "%"
				: bcatalogo.getDesesp().trim().toUpperCase());
		bcatalogo.setTbiden(tbiden);
		catalogos = servicio.buscarCatalogos(bcatalogo);
	}

	public void showBuscar(String pagina, String titulo, String tbiden) {
		catalogos.clear();
		catalogo = new TTABD();
		bcatalogo = new TTABD();
		this.tbiden = tbiden;
		this.titulo = titulo;

		Map<String, Object> options = new HashMap<String, Object>();
		options.put("modal", true);
		options.put("draggable", true);
		options.put("resizable", false);
		options.put("contentHeight", 480);
		options.put("contentWidth", 550);
		// hint: available options are modal, draggable, resizable, width,
		// height, contentWidth and contentHeight
		RequestContext.getCurrentInstance().openDialog(pagina, options, null);
	}

	public void closeBuscar() {
		RequestContext.getCurrentInstance().closeDialog(null);
	}

	public void aceptar() {
		RequestContext.getCurrentInstance().closeDialog(catalogo);
	}

	// ********************************************************************************

	public void cargar(String tbiden, String titulo) {
		this.titulo = titulo;
		this.tbiden = tbiden;
		catalogos = servicio.listarCatalogo(tbiden);
		fcatalogos = null;
		RequestContext.getCurrentInstance().update("mantenerCatalogoI");
	}
	
	public void cargar(String tbiden, String tbref1,String titulo) {
		this.titulo = titulo;
		this.tbiden = tbiden;
		this.tbref1 = tbref1;
		catalogos = servicio.listarCatalogo(tbiden);	
		referencias = servicio.listarCatalogo(tbref1);
		detalles = new ArrayList<TTABD>();
		catalogo = new TTABD();
		referencia = new TTABD();
		RequestContext.getCurrentInstance().update("catalogoReferencia");
	}

	public void cargar() {
		catalogos = servicio.listarCatalogo(tbiden);
		fcatalogos = null;
		RequestContext.getCurrentInstance().update("mantenerCatalogoI");
	}
	
	public void grabar(){
		for (TTABD ttabd : catalogos) {
			if(ttabd.getTbespe().equals(catalogo.getTbespe())){
				ttabd.setTbusin(sesion.getUsuario().getUsucve());
				ttabd.setTbusmd(sesion.getUsuario().getUsucve());
				ttabd.setTbfemd(Calendar.getInstance().getTime());
				ttabd.setDetalles(detalles);
				int result = servicio.registrarReferencias(ttabd);
				if(result>0){
					System.out.println(result);
					mensaje="Se han guardado " + detalles.size() + " referencias correctamente.";
					RequestContext.getCurrentInstance().execute("PF('dlg').show()");
				}else{
					mensaje="Error SQL.";
					RequestContext.getCurrentInstance().execute("PF('dlg').show()");
				}
				
				break;
			}
		}
		
	}
	
	public void agregar(){
		System.out.println("agregar");
		mensaje="";
		if(catalogo.getTbespe().equals("")){
			mensaje="Seleccione catalogo";
		}
		
		if(referencia.getTbespe().equals("")){
			mensaje="Seleccione referencia";
		}
		
		if(mensaje.equals("")){
			boolean existe = false;
			for (TTABD ttabd : detalles) {
				if(ttabd.getTbespe().trim().equals(referencia.getTbespe().trim())){
					existe=true;
					mensaje="La referencia ha sido agregada.";
					RequestContext.getCurrentInstance().execute("PF('dlg').show()");
					break;
				}
			}
			if(existe==false){
				for (TTABD ttabd : referencias) {
					if(ttabd.getTbespe().trim().equals(referencia.getTbespe().trim())){
						detalles.add(ttabd);
						break;
					}
				}
				
			}
		}else{
			RequestContext.getCurrentInstance().execute("PF('dlg').show()");
		}
		
	}
	
	public void quitar(TTABD quitar){
		System.out.println("quitar");
		detalles.remove(quitar);
	}
	
	public void showReferencias(){
		System.out.println("showReferencias");
		TTABD obj = new TTABD();
		obj.setTbiden(tbiden);
		obj.setTbespe(catalogo.getTbespe());
		detalles = servicio.listarReferencias(obj);
		System.out.println(detalles.size());
	}


	public void eliminar() {
		if (catalogo != null) {
			if (!catalogo.getTbespe().equals("")) {
				int count = servicio.countReferences(catalogo);
				if (count == 0) {
					int result = servicio.eliminar(catalogo);
					if (result > 0) {
						catalogos.remove(catalogo);
						mensaje = "Se ha eliminado correctamente";
						RequestContext.getCurrentInstance().execute(
								"PF('dlg').show()");
					} else {
						mensaje = "Error al eliminar(SQL).";
						RequestContext.getCurrentInstance().execute(
								"PF('dlg').show()");
					}
				}else{
					mensaje = "El registro no se puede eliminar porque esta siendo usado. "+count;
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

	public void registrar() {
		operacion = 0;
		catalogo = new TTABD();
		catalogo.setTbiden(tbiden);
	}

	public String modificar() {
		if (catalogo != null) {
			if (!catalogo.getTbespe().equals("")) {
				operacion = 1;
				return "/paginas/catalogo";
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

	public void guardar() {
		if (!catalogo.getDesesp().trim().equals("")) {
			if (operacion == 0) {
				catalogo.setTbusin(sesion.getUsuario().getUsucve());
				int result = servicio.registrar(catalogo);
				if (result > 0) {
					mensaje = "Registrado con el código: "
							+ catalogo.getTbespe();
					RequestContext.getCurrentInstance().execute(
							"PF('dlg').show()");
				} else {
					mensaje = "Error al insertar(SQL).";
					RequestContext.getCurrentInstance().execute(
							"PF('dlg').show()");
				}
			} else {
				catalogo.setTbusmd(sesion.getUsuario().getUsucve());
				catalogo.setTbfemd(Calendar.getInstance().getTime());
				int result = servicio.modificar(catalogo);
				if (result > 0) {
					mensaje = "Modificado exitosamente";
					RequestContext.getCurrentInstance().execute(
							"PF('dlg').show()");
				} else {
					mensaje = "Error al modificar(SQL).";
					RequestContext.getCurrentInstance().execute(
							"PF('dlg').show()");
				}
			}
		}
	}

	public List<TTABD> getCatalogos() {
		return catalogos;
	}

	public void setCatalogos(List<TTABD> catalogos) {
		this.catalogos = catalogos;
	}

	public TTABD getCatalogo() {
		return catalogo;
	}

	public void setCatalogo(TTABD catalogo) {
		this.catalogo = catalogo;
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

	public String getTbiden() {
		return tbiden;
	}

	public void setTbiden(String tbiden) {
		this.tbiden = tbiden;
	}
	public String getTbref1() {
		return tbref1;
	}

	public void setTbref1(String tbref1) {
		this.tbref1 = tbref1;
	}

	public TTABD getBcatalogo() {
		return bcatalogo;
	}

	public void setBcatalogo(TTABD bcatalogo) {
		this.bcatalogo = bcatalogo;
	}

	public SesionBean getSesion() {
		return sesion;
	}

	public void setSesion(SesionBean seguridad) {
		this.sesion = seguridad;
	}

	public List<TTABD> getFcatalogos() {
		return fcatalogos;
	}

	public void setFcatalogos(List<TTABD> fcatalogos) {
		this.fcatalogos = fcatalogos;
	}

	public List<TTABD> getReferencias() {
		return referencias;
	}

	public void setReferencias(List<TTABD> referencias) {
		this.referencias = referencias;
	}

	public TTABD getReferencia() {
		return referencia;
	}

	public void setReferencia(TTABD referencia) {
		this.referencia = referencia;
	}

	public List<TTABD> getDetalles() {
		return detalles;
	}

	public void setDetalles(List<TTABD> detalles) {
		this.detalles = detalles;
	}
	
}
