package com.rtm.compras.managed;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import com.rtm.compras.model.TARTI;
import com.rtm.compras.model.TPROV;
import com.rtm.compras.model.TSOLI;
import com.rtm.compras.model.TSOLID;
import com.rtm.compras.negocio.business.delegate.GestionSolicitud;
import com.rtm.compras.negocio.service.SolicitudService;
import com.rtm.compras.resource.Sesion;

@ManagedBean(name="tsoli")
@SessionScoped
public class SolicitudBean implements Serializable{
	private static final long serialVersionUID = 1L;
	private SolicitudService servicio = GestionSolicitud.getTSOLIService();
	private List<TSOLI> solicitudes = new ArrayList<TSOLI>();
	private List<TSOLI> fsolicitudes;
	private TSOLI solicitud = new TSOLI();
	private List<TSOLID> detalles = new ArrayList<TSOLID>();
	private TSOLID detalle = new TSOLID();
	private StreamedContent imagen;
	private int operacion=0;
	private String mensaje="";
	private String titulo="";
	private boolean error=false;
	private Date minDate=Calendar.getInstance().getTime();
	private String situacion="";
	@ManagedProperty("#{tusua}")
	private SesionBean sesion;
	private int columnas=0;
	private int filas=0;
	private boolean flagDetalle=true;
	
	public SolicitudBean() {
		System.out.println("construyendo SolicitudBean");
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
	
	public void handleReturnArticulo(SelectEvent event) {
		if(event.getObject()!=null){
			TARTI obj = (TARTI) event.getObject();
			detalle.setSdarti(obj);
			imagen = new DefaultStreamedContent(new ByteArrayInputStream(obj.getArtimg()));
		}
	}
	
	public void handleReturnProveedor(SelectEvent event) {
		if(event.getObject()!=null){
			TPROV obj = (TPROV) event.getObject();
			detalle.setSdprov(obj);
		}
	}
	
	public void cargar(){
		System.out.println("sin");
		TSOLI tsoli = new TSOLI(situacion);
		tsoli.setSlpers(sesion.getUsuario().getTpers());
		solicitudes = servicio.listarSolicitud(tsoli);	
		if(situacion.equals("00")){
			if(solicitudes.size()<7){
				columnas = solicitudes.size();
			}else{
				columnas = 6;
			}
			filas=solicitudes.size();
		}
		RequestContext.getCurrentInstance().update("gestionSolicitud");
		RequestContext.getCurrentInstance().update("seguimiento");	
	}
	
	public void ver(TSOLI solicitud){
		this.solicitud = solicitud;
		detalles = servicio.getDetalle(solicitud);
		RequestContext.getCurrentInstance().execute("PF('solicitudDialog').show()");
	}
	
	public void cargar(String situacion){
		System.out.println("con");
		this.situacion=situacion;		
		TSOLI tsoli = new TSOLI(situacion);
		tsoli.setSlpers(sesion.getUsuario().getTpers());
		solicitudes = servicio.listarSolicitud(tsoli);		
		if(situacion.equals("01")){
			titulo = "Gestión de Solicitud";
		}else if(situacion.equals("02")){
			titulo = "Evaluar de Solicitud";
		}else if(situacion.equals("00")){
			titulo = "Seguimiento de Solicitud";
			if(solicitudes.size()<7){
				columnas = solicitudes.size();
			}else{
				columnas = 6;
			}
			filas=solicitudes.size();
		}
		RequestContext.getCurrentInstance().update("gestionSolicitud");
	}
	
	public String evaluar(){
		if(solicitud!=null){
			if(solicitud.getSlnume()>0){
				operacion = 2;
				detalles = servicio.getDetalle(solicitud);
				detalle = new TSOLID();
				detalle.setSdcant(1);
				return "/paginas/solicitud";
			}else{
				mensaje = "Seleccione una solicitud.";
				RequestContext.getCurrentInstance().execute("PF('dlg').show()");
			}
		}else{
			mensaje = "Seleccione una solicitud.";
			RequestContext.getCurrentInstance().execute("PF('dlg').show()");
		}
		return null;
	}
	
	public void enviar(){
		if(solicitud!=null){
			if(solicitud.getSlnume()>0){
				if(sesion.getUsuario().getTpers().getPlcodi().equals(sesion.getUsuario().getTpers().getPljefe().getPlcodi())){
					solicitud.getSlsitu().setTbespe("03");
				}else{
					solicitud.getSlsitu().setTbespe("02");
				}
				
				solicitud.setSlusmd(sesion.getUsuario().getUsucve());
				solicitud.setSlfemd(Calendar.getInstance().getTime());
				int result = servicio.modificarEstado(solicitud);
				if(result>0){
					solicitudes.remove(solicitud);
					mensaje = "Se enviado la solicitud Nº "+solicitud.getSlnume();
					RequestContext.getCurrentInstance().update("gestionSolicitud");
					RequestContext.getCurrentInstance().execute("PF('dlg').show()");
				}else{
					mensaje = "Error al enviar solicitud (SQL).";
					RequestContext.getCurrentInstance().execute("PF('dlg').show()");
				}
			}else{
				mensaje = "Seleccione una solicitud.";
				RequestContext.getCurrentInstance().execute("PF('dlg').show()");
			}
		}else{
			mensaje = "Seleccione una solicitud.";
			RequestContext.getCurrentInstance().execute("PF('dlg').show()");
		}
	}
	
	public String modificar(){
		if(solicitud!=null){
			if(solicitud.getSlnume()>0){
				operacion = 1;
				detalles = servicio.getDetalle(solicitud);
				detalle = new TSOLID();
				detalle.setSdcant(1);
				flagDetalle=true;
				return "/paginas/solicitud";
			}else{
				System.out.println("0");
				mensaje = "Seleccione una solicitud.";
				RequestContext.getCurrentInstance().execute("PF('dlg').show()");
			}
		}else{
			System.out.println("null");
			mensaje = "Seleccione una solicitud.";
			RequestContext.getCurrentInstance().execute("PF('dlg').show()");
		}
		return null;
	}
	
	public void eliminar(){
		if(solicitud!=null){
			if(solicitud.getSlnume()>0){
				int result = servicio.eliminar(solicitud);
				if(result>0){
					solicitudes.remove(solicitud);
					mensaje = "Se ha eliminado correctamente";
					RequestContext.getCurrentInstance().execute("PF('dlg').show()");
				}else{
					mensaje = "Error al eliminar solicitud (SQL).";
					RequestContext.getCurrentInstance().execute("PF('dlg').show()");
				}
			}else{
				mensaje = "Seleccione una solicitud.";
				RequestContext.getCurrentInstance().execute("PF('dlg').show()");
			}
		}else{
			mensaje = "Seleccione una solicitud.";
			RequestContext.getCurrentInstance().execute("PF('dlg').show()");
		}
	}
	
	public void registrar(){
		System.out.println("registrar");
		operacion = 0;
		detalles.clear();
		detalle = new TSOLID();
		detalle.setSdcant(1);
		solicitud = new TSOLI();
		solicitud.getSlsitu().setTbespe("01");
		solicitud.setSlpers(sesion.getUsuario().getTpers());
		flagDetalle=true;
	}
	
	public void modificarDetalle(){
		flagDetalle=false;
	}
	
	public void guardarDetalle(){
		flagDetalle=true;
	}
	
	public void grabar(){
		System.out.println("aceptar");
		if(detalles.size()>0){
			if(operacion==0){
				solicitud.setSlusin(sesion.getUsuario().getUsucve());
				solicitud.setDetalle(detalles);
				int result = servicio.registrar(solicitud);
				if(result>0){
					error = false;
					mensaje= "Solicitud registrada exitosamente con el Nº: " + solicitud.getSlnume();
					RequestContext.getCurrentInstance().execute("PF('dlg').show()");
				}else{
					error = true;
					mensaje = "Error al registrar solicitud (SQL).";
					RequestContext.getCurrentInstance().execute("PF('dlg').show()");
				}
			}else if(operacion==1){
				solicitud.setSlusmd(sesion.getUsuario().getUsucve());
				solicitud.setSlfemd(Calendar.getInstance().getTime());
				solicitud.setDetalle(detalles);
				int result = servicio.modificar(solicitud);
				if(result>0){
					error = false;
					mensaje= "Solicitud modificada exitosamente";
					RequestContext.getCurrentInstance().execute("PF('dlg').show()");
				}else{
					error = true;
					mensaje = "Error al modificar solicitud (SQL).";
					RequestContext.getCurrentInstance().execute("PF('dlg').show()");
				}
			}else if(operacion==2){
				solicitud.setSlusmd(sesion.getUsuario().getUsucve());
				solicitud.setSlfemd(Calendar.getInstance().getTime());
				int result = servicio.modificarEstado(solicitud);
				if(result>0){
					solicitudes.remove(solicitud);
					mensaje = "Se guardo la evaluación correctamente";
					RequestContext.getCurrentInstance().execute("PF('dlg').show()");
				}else{
					mensaje = "Error al evualuar solicitud (SQL).";
					RequestContext.getCurrentInstance().execute("PF('dlg').show()");
				}
			}	
		}else{
			error = true;
			mensaje= "Ingrese almenos 1 artículo";
			RequestContext.getCurrentInstance().execute("PF('dlg').show()");
		}
	}
	
	public void agregar(){
		System.out.println("agregar");
		mensaje="";
		if(detalle.getSdarti().getArtcod().equals("")){
			mensaje="Indique el artículo a agregar";
		}else{
			if(detalle.getSdarti().getArtdes().trim().contains("no existe")){
				mensaje="Código de artículo no existe";
			}
		}
		
		if(detalle.getSdprov()!=null){
			if(detalle.getSdprov().getPronom()!=null){
				if(detalle.getSdprov().getPronom().contains("no existe")){
					mensaje+="Código de proveedor no existe";
				}
			}
		}
		
		if(mensaje.equals("")){
			detalles.add(detalle);
			detalle = new TSOLID();
			detalle.setSdcant(1);
		}else{
			error = true;
			RequestContext.getCurrentInstance().execute("PF('dlg').show()");
		}
		
	}
	
	public void quitar(TSOLID quitar){
		System.out.println("quitar");
		detalles.remove(quitar);
	}	
	
	public void buscarArticulo(){
		if(!detalle.getSdarti().getArtcod().equals("")){
			TARTI articulo = servicio.buscarArticulo(detalle.getSdarti());
			if(articulo!=null){
				detalle.setSdarti(articulo);
				imagen = new DefaultStreamedContent(new ByteArrayInputStream(articulo.getArtimg()));
			}else{
				articulo = new TARTI();
				articulo.setArtcod(detalle.getSdarti().getArtcod());
				articulo.setArtdes("Código no existe");
				detalle.setSdarti(articulo);
			}
		}else{
			detalle.setSdarti(new TARTI());
		}
	}
	
	public void buscarProveedor(){
		if(!detalle.getSdprov().getProcve().equals("")){
			TPROV proveedor = servicio.buscar(detalle.getSdprov());
			if(proveedor!=null){
				detalle.setSdprov(proveedor);
			}else{
				proveedor = new TPROV();
				proveedor.setProcve(detalle.getSdprov().getProcve());
				proveedor.setPronom("Código no existe");
				detalle.setSdprov(proveedor);
			}
		}else{
			detalle.getSdprov().setPronom("");
		}
		
	}

	public List<TSOLI> getSolicitudes() {
		return solicitudes;
	}

	public void setSolicitudes(List<TSOLI> solicitudes) {
		this.solicitudes = solicitudes;
	}

	public TSOLI getSolicitud() {
		return solicitud;
	}

	public void setSolicitud(TSOLI solicitud) {
		this.solicitud = solicitud;
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

	public TSOLID getDetalle() {
		return detalle;
	}

	public void setDetalle(TSOLID detalle) {
		this.detalle = detalle;
	}

	public List<TSOLID> getDetalles() {
		return detalles;
	}

	public void setDetalles(List<TSOLID> detalles) {
		this.detalles = detalles;
	}

	public StreamedContent getImagen() {
		return imagen;
	}

	public void setImagen(StreamedContent imagen) {
		this.imagen = imagen;
	}

	public boolean isError() {
		return error;
	}

	public void setError(boolean error) {
		this.error = error;
	}

	public SesionBean getSesion() {
		return sesion;
	}
	public void setSesion(SesionBean seguridad) {
		this.sesion = seguridad;
	}
	public List<TSOLI> getFsolicitudes() {
		return fsolicitudes;
	}
	public void setFsolicitudes(List<TSOLI> fsolicitudes) {
		this.fsolicitudes = fsolicitudes;
	}
	public Date getMinDate() {
		return minDate;
	}
	public void setMinDate(Date minDate) {
		this.minDate = minDate;
	}
	public String getSituacion() {
		return situacion;
	}
	public void setSituacion(String situacion) {
		this.situacion = situacion;
	}

	public int getColumnas() {
		return columnas;
	}

	public void setColumnas(int columnas) {
		this.columnas = columnas;
	}

	public int getFilas() {
		return filas;
	}
	public void setFilas(int filas) {
		this.filas = filas;
	}

	public boolean isFlagDetalle() {
		return flagDetalle;
	}

	public void setFlagDetalle(boolean flagDetalle) {
		this.flagDetalle = flagDetalle;
	}
	
	
}
