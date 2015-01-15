package com.rtm.compras.managed;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Properties;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.CellRangeAddress;
import org.apache.poi.hssf.util.Region;
import org.primefaces.component.selectonemenu.SelectOneMenu;
import org.primefaces.context.RequestContext;

import com.rtm.compras.model.TPROV;
import com.rtm.compras.model.TSOLI;
import com.rtm.compras.model.TSOLID;
import com.rtm.compras.model.TTABD;
import com.rtm.compras.negocio.business.delegate.GestionCotizacion;
import com.rtm.compras.negocio.service.CotizacionService;
import com.rtm.compras.resource.Sesion;

@ManagedBean(name = "tcoti")
@SessionScoped
public class CotizacionBean implements Serializable {
	private static final long serialVersionUID = 1L;
	private CotizacionService servicio = GestionCotizacion
			.getCotizacionService();
	private List<TSOLI> solicitudes = new ArrayList<TSOLI>();
	private List<TSOLI> fsolicitudes;
	private TSOLI solicitud = new TSOLI();
	private List<TSOLID> detalles = new ArrayList<TSOLID>();
	private TSOLID detalle = new TSOLID();
	private String titulo = "";
	@ManagedProperty("#{tusua}")
	private SesionBean sesion;
	private int columnas = 0;
	private int filas = 0;
	private String situacion="";
	private String nuevoEstado = "03";
	ArrayList<String> situaciones = new ArrayList<String>();
	private String asunto="";
	private List<TPROV> proveedores = new ArrayList<TPROV>();
	private TPROV proveedor = new TPROV();
	private List<SelectItem> proveedorSelector;
	
	public CotizacionBean() {
		System.out.println("construyendo CotizacionBean");
		if(FacesContext.getCurrentInstance().isPostback()==false){
			try {
				FacesContext.getCurrentInstance().getExternalContext().redirect("/ProyCompras/");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		situaciones.add("Aprobado por J.A.");
		situaciones.add("Cotizando");
		situaciones.add("Con Cotización");
		situaciones.add("Con Sugerencias");
		situaciones.add("Evaluando Cotización");
		situaciones.add("Aprobado por G.C.");
		situaciones.add("Denegado por G.C.");
		situaciones.add("Con Orden de Compra");
		situaciones.add("Evaluando Orden de Compra");
		situaciones.add("O.C Aprobada por G.C.");
		situaciones.add("O.C Denegado por G.C.");
		situaciones.add("En Proveedor");
		situaciones.add("Entregado");
	}

	public void showEdit(TSOLI solicitud) {
		this.solicitud = solicitud;
		nuevoEstado = solicitud.getSlsitu().getTbespe();
		RequestContext.getCurrentInstance().execute(
				"PF('editarDialogo').show()");
	}

	public void actualizar() {
		System.out.println("actualizar");
		//int intNuevoEstado = Integer.parseInt(nuevoEstado);
		//int intSlsitu = Integer.parseInt(solicitud.getSlsitu().getTbespe());

		//if (intSlsitu >= intNuevoEstado) {
			solicitud.setSlusmd(sesion.getUsuario().getUsucve());
			solicitud.setSlfemd(Calendar.getInstance().getTime());
			int result = servicio.modificarEstado(solicitud);
			if (result > 0) {
				solicitudes = servicio.listarSolicitud(new TSOLI("05"));
				columnas = solicitudes.size();
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(
								"Se ha modificado el estado de la solicitud Nº "
										+ solicitud.getSlnume()));
				RequestContext.getCurrentInstance().update(
						"actualizarSolicitud");
			}
			RequestContext.getCurrentInstance().execute(
					"PF('editarDialogo').hide()");
		//} 
		/*
		else {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage("Estado incorrecto"));
			RequestContext.getCurrentInstance().update("actualizarSolicitud");
			RequestContext.getCurrentInstance().execute(
					"PF('editarDialogo').hide()");
		}
		*/
	}

	public void cargar() {
		TSOLI tsoli = new TSOLI(situacion);
		tsoli.setSlpers(sesion.getUsuario().getTpers());
		solicitudes = servicio.listarSolicitud(tsoli);	
		if(solicitudes.size()<7){
			columnas = solicitudes.size();
		}else{
			columnas = 6;
		}
		filas=solicitudes.size();
		RequestContext.getCurrentInstance().update("solicitarCotizacion");
	}
	
	public void cargar(String situacion) {
		System.out.println("con " + situacion);
		this.situacion=situacion;
		TSOLI tsoli = new TSOLI(situacion);
		tsoli.setSlpers(sesion.getUsuario().getTpers());
		solicitudes = servicio.listarSolicitud(tsoli);
		if (situacion.equals("03")) {
			titulo = "Solicitar Cotización";
			RequestContext.getCurrentInstance().update("solicitarCotizacion");
		} else if (situacion.equals("05")) {
			titulo = "Actualizar Solicitud";
			if (solicitudes.size() < 7) {
				columnas = solicitudes.size();
			} else {
				columnas = 6;
			}
			filas = solicitudes.size();
			RequestContext.getCurrentInstance().update("actualizarSolicitud");
		}

	}

	public void ver(TSOLI solicitud) {
		this.solicitud = solicitud;
		detalles = servicio.getDetalle(solicitud);
		RequestContext.getCurrentInstance().execute(
				"PF('solicitudDialog').show()");
	}

	public void postProcessXLS(Object document) {
		System.out.println("postProcessXLS");
		if (solicitud != null) {
			if (solicitud.getSlnume() > 0) {
				detalles = servicio.getDetalle(solicitud);
			
				HSSFWorkbook wb = (HSSFWorkbook) document;
				wb.removeSheetAt(0);
				HSSFSheet sheet = wb.createSheet("Solicitud "+solicitud.getSlnume());
				HSSFCellStyle cellStyle = wb.createCellStyle();
				cellStyle.setBorderBottom((short) 1);
				cellStyle.setBorderLeft((short) 1);
				cellStyle.setBorderRight((short) 1);
				cellStyle.setBorderTop((short) 1);
				
				HSSFRow row1 = sheet.createRow(0);
				HSSFRow row2 = sheet.createRow(1);
				HSSFRow row3 = sheet.createRow(2);
				HSSFRow row4 = sheet.createRow(3);
				HSSFRow row5 = sheet.createRow(4);
				HSSFRow row6 = sheet.createRow(5);
				HSSFRow row7 = sheet.createRow(6);
				HSSFRow row8 = sheet.createRow(7);
				
				HSSFCell cell_1_0= row1.createCell(0);
				HSSFCell cell_2_0= row2.createCell(0);
				HSSFCell cell_3_0= row3.createCell(0);
				HSSFCell cell_4_0= row4.createCell(0);
				HSSFCell cell_5_0= row5.createCell(0);
				HSSFCell cell_6_0= row6.createCell(0);
				HSSFCell cell_7_0= row7.createCell(0);
				HSSFCell cell_8_0= row8.createCell(0);
				
				HSSFCell cell_1_1= row1.createCell(1);
				HSSFCell cell_2_1= row2.createCell(1);
				HSSFCell cell_3_1= row3.createCell(1);
				HSSFCell cell_4_1= row4.createCell(1);
				HSSFCell cell_5_1= row5.createCell(1);
				HSSFCell cell_6_1= row6.createCell(1);
				HSSFCell cell_7_1= row7.createCell(1);
				HSSFCell cell_8_1= row8.createCell(1);
				
				cell_1_0.setCellValue("Número");
				cell_2_0.setCellValue("Motivo");
				cell_3_0.setCellValue("Local");
				cell_4_0.setCellValue("Plazo");
				cell_5_0.setCellValue("Solicitante");
				cell_6_0.setCellValue("Area");
				cell_7_0.setCellValue("Cargo");
				cell_8_0.setCellValue("Observación");
				
				cell_1_0.setCellStyle(cellStyle);
				cell_2_0.setCellStyle(cellStyle);
				cell_3_0.setCellStyle(cellStyle);
				cell_4_0.setCellStyle(cellStyle);
				cell_5_0.setCellStyle(cellStyle);
				cell_6_0.setCellStyle(cellStyle);
				cell_7_0.setCellStyle(cellStyle);
				cell_8_0.setCellStyle(cellStyle);
				
				cell_1_1.setCellValue(solicitud.getSlnume()+"");
				cell_2_1.setCellValue(solicitud.getSlmoti());
				cell_3_1.setCellValue(solicitud.getSlloca());
				cell_4_1.setCellValue(Sesion.formateaFechaVista(solicitud.getSlplaz()));
				cell_5_1.setCellValue(solicitud.getSlpers().getPlnom1() +" " +solicitud.getSlpers().getPlnom2() +" " +solicitud.getSlpers().getPlapep() +" " +solicitud.getSlpers().getPlapem());
				cell_6_1.setCellValue(solicitud.getSlpers().getPlarea().getDesesp());
				cell_7_1.setCellValue(solicitud.getSlpers().getPlcarg().getDesesp());
				cell_8_1.setCellValue(solicitud.getSlobse());
				
				cell_1_1.setCellStyle(cellStyle);
				cell_2_1.setCellStyle(cellStyle);
				cell_3_1.setCellStyle(cellStyle);
				cell_4_1.setCellStyle(cellStyle);
				cell_5_1.setCellStyle(cellStyle);
				cell_6_1.setCellStyle(cellStyle);
				cell_7_1.setCellStyle(cellStyle);
				cell_8_1.setCellStyle(cellStyle);
				
				HSSFRow row10 = sheet.createRow(9);

				sheet.addMergedRegion(new CellRangeAddress(9,9,0,1));
				
				
				HSSFCell cell_1_10= row10.createCell(0);
				HSSFCell cell_0_10= row10.createCell(1);
				HSSFCell cell_2_10= row10.createCell(2);
				HSSFCell cell_3_10= row10.createCell(3);
				HSSFCell cell_4_10= row10.createCell(4);
				HSSFCell cell_5_10= row10.createCell(5);
				HSSFCell cell_6_10= row10.createCell(6);
				HSSFCell cell_7_10= row10.createCell(7);
				HSSFCell cell_8_10= row10.createCell(8);
				HSSFCell cell_9_10= row10.createCell(9);
				
				cell_1_10.setCellValue("Artículo");
				cell_2_10.setCellValue("Tipo");
				cell_3_10.setCellValue("Marca");
				cell_4_10.setCellValue("Modelo");
				cell_5_10.setCellValue("U. Med.");
				cell_6_10.setCellValue("Cantidad");
				cell_7_10.setCellValue("Motivo");
				cell_8_10.setCellValue("Proveedor");
				cell_9_10.setCellValue("Correo");
				
				cell_1_10.setCellStyle(cellStyle);
				cell_0_10.setCellStyle(cellStyle);
				cell_2_10.setCellStyle(cellStyle);
				cell_3_10.setCellStyle(cellStyle);
				cell_4_10.setCellStyle(cellStyle);
				cell_5_10.setCellStyle(cellStyle);
				cell_6_10.setCellStyle(cellStyle);
				cell_7_10.setCellStyle(cellStyle);
				cell_8_10.setCellStyle(cellStyle);
				cell_9_10.setCellStyle(cellStyle);
				
				for (int i = 0; i < detalles.size(); i++) {
					HSSFRow rowi = sheet.createRow(i+10);
					
					sheet.addMergedRegion(new CellRangeAddress(i+10,i+10,0,1));
					
					HSSFCell cell_1_i = rowi.createCell(0);
					HSSFCell cell_0_i = rowi.createCell(1);
					HSSFCell cell_2_i = rowi.createCell(2);
					HSSFCell cell_3_i = rowi.createCell(3);
					HSSFCell cell_4_i = rowi.createCell(4);
					HSSFCell cell_5_i = rowi.createCell(5);
					HSSFCell cell_6_i = rowi.createCell(6);
					HSSFCell cell_7_i = rowi.createCell(7);
					HSSFCell cell_8_i = rowi.createCell(8);
					HSSFCell cell_9_i = rowi.createCell(9);
					
					cell_1_i.setCellValue(detalles.get(i).getSdarti().getArtdes());
					cell_2_i.setCellValue(detalles.get(i).getSdarti().getArttip().getDesesp());
					cell_3_i.setCellValue(detalles.get(i).getSdarti().getArtmar().getDesesp());
					cell_4_i.setCellValue(detalles.get(i).getSdarti().getArtmod().getDesesp());
					cell_5_i.setCellValue(detalles.get(i).getSdarti().getArtmed().getDesesp());
					cell_6_i.setCellValue(detalles.get(i).getSdcant());
					cell_7_i.setCellValue(detalles.get(i).getSdobse());
					cell_8_i.setCellValue(detalles.get(i).getSdprov().getPronom());
					cell_9_i.setCellValue(detalles.get(i).getSdprov().getPrmail());
					
					cell_1_i.setCellStyle(cellStyle);
					cell_0_i.setCellStyle(cellStyle);
					cell_2_i.setCellStyle(cellStyle);
					cell_3_i.setCellStyle(cellStyle);
					cell_4_i.setCellStyle(cellStyle);
					cell_5_i.setCellStyle(cellStyle);
					cell_6_i.setCellStyle(cellStyle);
					cell_7_i.setCellStyle(cellStyle);
					cell_8_i.setCellStyle(cellStyle);
					cell_9_i.setCellStyle(cellStyle);
				}
				
				
				
				sheet.autoSizeColumn(0, true);
				sheet.autoSizeColumn(1, true);
				sheet.autoSizeColumn(2, true);
				sheet.autoSizeColumn(3, true);
				sheet.autoSizeColumn(4, true);
				sheet.autoSizeColumn(5, true);
				sheet.autoSizeColumn(6, true);
				sheet.autoSizeColumn(7, true);
				sheet.autoSizeColumn(8, true);
				sheet.autoSizeColumn(9, true);
									
			} else {
				System.out.println("0");
				Sesion.formateaExcel(document);	    
			}
		} else {
			System.out.println("null");
			Sesion.formateaExcel(document);	        
		}
	}
	
	public void solicitar(){
		
		System.out.println("solicitar " + solicitud.getSlnume());
		detalles = servicio.getDetalle(solicitud);
		proveedores = servicio.listarProveedores();
		proveedorSelector = new ArrayList<SelectItem>();
		for (TPROV pro : proveedores) {
			proveedorSelector.add(new SelectItem(pro.getProcve(),pro.getPronom()));
		}
		RequestContext.getCurrentInstance().execute("PF('cotizacionDialog').show()");
		//return "/paginas/prueba";
	}
	
	public void aplicarProveedor(){
		System.out.println("aplicarProveedor "+ proveedor.getProcve());
	}
	
	public void showReferencias(){
		System.out.println("listener");
		//System.out.println("listener "+ proveedor.getProcve());// + proveedor.getPrmail());
		//<p:commandButton id="aplicar" value="Aplicar proveedor" action="#{tcoti.aplicarProveedor}" />
		//<h:outputText id="prmail" value="#{tcoti.proveedor.prmail}" styleClass="subtitulo" />
		//<f:selectItems value="#{tcoti.proveedores}" var="proveedor" itemLabel="#{proveedor.pronom} - #{proveedor.prmail}" itemValue="#{proveedor.procve}" />
	}
	
	public void correo(String remitente,String pwd,String destinatario,String body,String asunto){
		 System.out.println("correo");
		 Properties props = new Properties();
	     props.setProperty("mail.smtp.host",  "smtp.gmail.com");
	     props.setProperty("mail.smtp.starttls.enable", "true");
	     props.setProperty("mail.smtp.port", "587");
	     props.setProperty("mail.smtp.auth", "true");
	     Session session = Session.getDefaultInstance(props);
	     session.setDebug(true);
	     MimeMessage message = new MimeMessage(session);
	     try {
			message.setFrom(new InternetAddress(remitente));
			Address [] receptores = new Address [1];
	        receptores[0] = new InternetAddress (destinatario) ;
	   	       
	        BodyPart texto = new MimeBodyPart();
	        texto.setText(body);
	        
	        MimeMultipart multiParte = new MimeMultipart();
	        multiParte.addBodyPart(texto);
	        	        
	        message.addRecipients(Message.RecipientType.TO, receptores);    
	        message.setSubject(asunto);        
	        message.setContent(multiParte);
	        Transport t = session.getTransport("smtp");
	        t.connect(remitente, pwd);
	        t.sendMessage(message, message.getRecipients(Message.RecipientType.TO));
	        t.close();
		} catch (AddressException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
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

	public List<TSOLID> getDetalles() {
		return detalles;
	}

	public void setDetalles(List<TSOLID> detalles) {
		this.detalles = detalles;
	}

	public TSOLID getDetalle() {
		return detalle;
	}

	public void setDetalle(TSOLID detalle) {
		this.detalle = detalle;
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

	public List<TSOLI> getFsolicitudes() {
		return fsolicitudes;
	}

	public void setFsolicitudes(List<TSOLI> fsolicitudes) {
		this.fsolicitudes = fsolicitudes;
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

	public String getNuevoEstado() {
		return nuevoEstado;
	}

	public void setNuevoEstado(String nuevoEstado) {
		this.nuevoEstado = nuevoEstado;
	}

	public String getAsunto() {
		return asunto;
	}

	public void setAsunto(String asunto) {
		this.asunto = asunto;
	}

	public List<TPROV> getProveedores() {
		return proveedores;
	}

	public void setProveedores(List<TPROV> proveedores) {
		this.proveedores = proveedores;
	}

	public TPROV getProveedor() {
		return proveedor;
	}

	public void setProveedor(TPROV proveedor) {
		this.proveedor = proveedor;
	}

	public List<SelectItem> getProveedorSelector() {
		return proveedorSelector;
	}

	public void setProveedorSelector(List<SelectItem> proveedorSelector) {
		this.proveedorSelector = proveedorSelector;
	}
	
	
}
