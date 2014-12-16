package com.rtm.compras.managed;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import com.rtm.compras.model.TPEPE;
import com.rtm.compras.model.TUSUA;
import com.rtm.compras.negocio.business.delegate.GestionSeguridad;
import com.rtm.compras.negocio.service.SeguridadService;

@ManagedBean(name="tusua")
@SessionScoped
public class SesionBean {
	
	private SeguridadService servicio = GestionSeguridad.getSeguridadService();
	private TUSUA usuario = new TUSUA();
	private String mensaje="";
	
	//***opciones
	private boolean maestros=false;
	private boolean solicitud=false;
	private boolean cotizacion=false;
	private boolean ordencompra=false;
	private boolean consultas=false;
	private boolean reportes=false;
	private boolean seguridad=false;
	//***sub opciones
	//***maestros
	private boolean articulo=false;
	private boolean proveedor=false;
	private boolean marca=false;
	private boolean modelo=false;
	private boolean tipo=false;
	private boolean umedida=false;
	private boolean personal=false;
	private boolean area=false;
	private boolean cargo=false;
	//***solicitud
	private boolean gestionSolicitud=false;
	private boolean seguimientoSolicitud=false;
	private boolean evaluarSolicitud=false;
	private boolean actualizaSolicitud=false;
	//***cotizacion
	private boolean solicitaCotizacion=false;
	//***seguridad
	private boolean permisos=false;
	private boolean modulos=false;
	private boolean modulosOpciones=false;
	private boolean opciones=false;
	private boolean opcionesSubopciones=false;
	private boolean subopciones=false;
	private boolean subopcionesOperaciones=false;
	private boolean operaciones=false;
	
	public SesionBean() {
		System.out.println("construyendo SesionBean");
	}
	
	public String validar(){
		mensaje="";
		if(usuario.getUsucve().trim().equals("")){
			mensaje += "Ingrese usuario";
		}
		if(usuario.getUsucon().trim().equals("")){
			mensaje += mensaje.equals("")?"Ingrese contraseña":" y contraseña";
		}
		
		if(mensaje.equals("")){
			TUSUA usuario = null;
			
			try {
				servicio.validar(this.usuario);//comentar esta linea si no se desea validar con as400 y eliminar los try catch, dejar solo la linea 77, poner un usuario existente en TUSUA y poner cualkier contraseña pork no la tomara en cuenta, y si desea validar con password cambiar por validacion en postgres
				usuario = servicio.getUsuario(this.usuario);//esta linea sirve para traer los datos en el postgres del usuario que se loguea, verificar tabla TUSUA 			
			} catch (IOException e) {
				System.out.println("IOException " + e.getMessage());
			} catch (SQLException e) {
				System.out.println("SQLException " + e.getMessage());
			} catch (Exception e) {
				System.out.println("Exception " + e.getMessage());
				mensaje = e.getMessage().contains("disabled")?"Usuario deshabilitado por superar el maximo de 3 intentos fallidos, llamar al area de sistemas.":"";
			}
			
			if(usuario!=null){
				this.usuario=usuario;
				TPEPE tpepe = new TPEPE();
				tpepe.setPpciac("TC");
				tpepe.setPpusua(usuario.getUsucve().toUpperCase());
				List<TPEPE> topciones = servicio.listarOpciones(tpepe);
				List<TPEPE> tsubopciones;
				for (TPEPE opcion : topciones) {
					switch (opcion.getPpcopc()) {
						case "000065": maestros=true; 
							tpepe.setPpcopc("000065");
							tsubopciones = servicio.listarSubOpciones(tpepe);
							for (TPEPE subopcion : tsubopciones) {
								switch (subopcion.getPpcsop()) {
									case "000072": articulo=true; break;
									case "000073": proveedor=true; break;
									case "000074": tipo=true; break;
									case "000075": marca=true; break;
									case "000076": modelo=true; break;
									case "000077": umedida=true; break;
									case "000078": area=true; break;
									case "000079": cargo=true; break;
									case "000139": personal=true; break;
								}
							}
						break;
						
						case "000066": solicitud=true; 
							tpepe.setPpcopc("000066");
							tsubopciones = servicio.listarSubOpciones(tpepe);
							for (TPEPE subopcion : tsubopciones) {
								switch (subopcion.getPpcsop()) {
									case "000080": gestionSolicitud=true; break;
									case "000081": evaluarSolicitud=true; break;
									case "000082": seguimientoSolicitud=true; break;
									case "000083": actualizaSolicitud=true; break;
								}
							}
						break;
						
						case "000067": cotizacion=true; 
							tpepe.setPpcopc("000067");
							tsubopciones = servicio.listarSubOpciones(tpepe);
							for (TPEPE subopcion : tsubopciones) {
								switch (subopcion.getPpcsop()) {
									case "000084": solicitaCotizacion=true; break;
								}
							}
						break;
						
						case "000068": ordencompra=true; break;
						case "000069": consultas=true; break;
						case "000070": reportes=true; break;
						
						case "000071": seguridad=true; 
							tpepe.setPpcopc("000071");
							tsubopciones = servicio.listarSubOpciones(tpepe);
							for (TPEPE subopcion : tsubopciones) {
								switch (subopcion.getPpcsop()) {
									case "000085": permisos=true; break;
									case "000086": modulos=true; break;
									case "000087": opciones=true; break;
									case "000088": subopciones=true; break;
									case "000089": operaciones=true; break;
									case "000136": modulosOpciones=true; break;
									case "000137": opcionesSubopciones=true; break;
									case "000138": subopcionesOperaciones=true; break;
								}
							}
						
						break;
					}
				}
				
				return "/paginas/index";
			}else{
				if(mensaje.equals(""))
				mensaje = "Usuario y/o contraseña incorrecto";
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error! " + mensaje, null));
			}
		}else{
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error! " + mensaje, null));
		}
		return null;
	}
	
	public void cerrarSesion(){
		System.out.println("cerrarSesion");
		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
	}

	public TUSUA getUsuario() {
		return usuario;
	}

	public void setUsuario(TUSUA usuario) {
		this.usuario = usuario;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public boolean isArea() {
		return area;
	}

	public void setArea(boolean area) {
		this.area = area;
	}

	public boolean isCargo() {
		return cargo;
	}

	public void setCargo(boolean cargo) {
		this.cargo = cargo;
	}

	public boolean isSeguridad() {
		return seguridad;
	}

	public void setSeguridad(boolean seguridad) {
		this.seguridad = seguridad;
	}

	public boolean isEvaluarSolicitud() {
		return evaluarSolicitud;
	}

	public void setEvaluarSolicitud(boolean evaluarSolicitud) {
		this.evaluarSolicitud = evaluarSolicitud;
	}

	public boolean isActualizaSolicitud() {
		return actualizaSolicitud;
	}

	public void setActualizaSolicitud(boolean actualizaSolicitud) {
		this.actualizaSolicitud = actualizaSolicitud;
	}

	public boolean isSolicitaCotizacion() {
		return solicitaCotizacion;
	}

	public void setSolicitaCotizacion(boolean solicitaCotizacion) {
		this.solicitaCotizacion = solicitaCotizacion;
	}

	public boolean isPersonal() {
		return personal;
	}

	public void setPersonal(boolean personal) {
		this.personal = personal;
	}

	public boolean isMaestros() {
		return maestros;
	}

	public void setMaestros(boolean maestros) {
		this.maestros = maestros;
	}

	public boolean isSolicitud() {
		return solicitud;
	}

	public void setSolicitud(boolean solicitud) {
		this.solicitud = solicitud;
	}

	public boolean isCotizacion() {
		return cotizacion;
	}

	public void setCotizacion(boolean cotizacion) {
		this.cotizacion = cotizacion;
	}

	public boolean isOrdencompra() {
		return ordencompra;
	}

	public void setOrdencompra(boolean ordencompra) {
		this.ordencompra = ordencompra;
	}

	public boolean isConsultas() {
		return consultas;
	}

	public void setConsultas(boolean consultas) {
		this.consultas = consultas;
	}

	public boolean isReportes() {
		return reportes;
	}

	public void setReportes(boolean reportes) {
		this.reportes = reportes;
	}

	public SeguridadService getServicio() {
		return servicio;
	}

	public void setServicio(SeguridadService servicio) {
		this.servicio = servicio;
	}

	public boolean isArticulo() {
		return articulo;
	}

	public void setArticulo(boolean articulo) {
		this.articulo = articulo;
	}

	public boolean isProveedor() {
		return proveedor;
	}

	public void setProveedor(boolean proveedor) {
		this.proveedor = proveedor;
	}

	public boolean isMarca() {
		return marca;
	}

	public void setMarca(boolean marca) {
		this.marca = marca;
	}

	public boolean isModelo() {
		return modelo;
	}

	public void setModelo(boolean modelo) {
		this.modelo = modelo;
	}

	public boolean isTipo() {
		return tipo;
	}

	public void setTipo(boolean tipo) {
		this.tipo = tipo;
	}

	public boolean isUmedida() {
		return umedida;
	}

	public void setUmedida(boolean umedida) {
		this.umedida = umedida;
	}

	public boolean isGestionSolicitud() {
		return gestionSolicitud;
	}

	public void setGestionSolicitud(boolean gestionSolicitud) {
		this.gestionSolicitud = gestionSolicitud;
	}

	public boolean isSeguimientoSolicitud() {
		return seguimientoSolicitud;
	}

	public void setSeguimientoSolicitud(boolean seguimientoSolicitud) {
		this.seguimientoSolicitud = seguimientoSolicitud;
	}

	public boolean isPermisos() {
		return permisos;
	}

	public void setPermisos(boolean permisos) {
		this.permisos = permisos;
	}

	public boolean isModulos() {
		return modulos;
	}

	public void setModulos(boolean modulos) {
		this.modulos = modulos;
	}

	public boolean isModulosOpciones() {
		return modulosOpciones;
	}

	public void setModulosOpciones(boolean modulosOpciones) {
		this.modulosOpciones = modulosOpciones;
	}

	public boolean isOpciones() {
		return opciones;
	}

	public void setOpciones(boolean opciones) {
		this.opciones = opciones;
	}

	public boolean isOpcionesSubopciones() {
		return opcionesSubopciones;
	}

	public void setOpcionesSubopciones(boolean opcionesSubopciones) {
		this.opcionesSubopciones = opcionesSubopciones;
	}

	public boolean isSubopciones() {
		return subopciones;
	}

	public void setSubopciones(boolean subopciones) {
		this.subopciones = subopciones;
	}

	public boolean isSubopcionesOperaciones() {
		return subopcionesOperaciones;
	}

	public void setSubopcionesOperaciones(boolean subopcionesOperaciones) {
		this.subopcionesOperaciones = subopcionesOperaciones;
	}

	public boolean isOperaciones() {
		return operaciones;
	}

	public void setOperaciones(boolean operaciones) {
		this.operaciones = operaciones;
	}
		
}
