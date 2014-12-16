package com.rtm.compras.managed;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.apache.commons.io.IOUtils;
import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.SelectEvent;

import com.rtm.compras.model.TARTI;
import com.rtm.compras.model.TTABD;
import com.rtm.compras.negocio.business.delegate.GestionArticulo;
import com.rtm.compras.negocio.service.ArticuloService;
import com.rtm.compras.resource.Sesion;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

@ManagedBean(name = "tarti")
@SessionScoped
public class ArticuloBean {

	private ArticuloService servicio = GestionArticulo.getTARTIService();
	private List<TARTI> articulos = new ArrayList<TARTI>();
	private List<TARTI> farticulos;
	private TARTI articulo = new TARTI();
	private TARTI barticulo = new TARTI();
	private int operacion = 0;
	private String mensaje = "";
	private String titulo = "";
	private StreamedContent imagen;
	@ManagedProperty("#{tusua}")
	private SesionBean sesion;

	public void probar() {
		System.out.println("probando");
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage("probando"));
	}

	public void postProcessXLS(Object document) {
		Sesion.formateaExcel(document);
	}

	public ArticuloBean() {
		// #{tarti.registrar}
		System.out.println("construyendo ArticuloBean");
		// System.out.println(articulos.size());
		// articulos = servicio.listar();
		// System.out.println(articulos.size());
		if(FacesContext.getCurrentInstance().isPostback()==false){
			try {
				FacesContext.getCurrentInstance().getExternalContext().redirect("/ProyCompras/");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void cargar() {
		articulos = servicio.listar();
		RequestContext.getCurrentInstance().update("mantenerArticulo");
	}

	public void handleReturn(SelectEvent event) {
		if (event.getObject() != null) {
			TTABD obj = (TTABD) event.getObject();
			if (obj.getTbiden().trim().equals("TIPOS")) {
				articulo.setArttip(obj);
			} else if (obj.getTbiden().trim().equals("UGMED")) {
				articulo.setArtmed(obj);
			} else if (obj.getTbiden().trim().equals("MARCA")) {
				articulo.setArtmar(obj);
			} else if (obj.getTbiden().trim().equals("MODEL")) {
				articulo.setArtmod(obj);
			}
		}
	}

	public String modificar() {
		if (articulo != null) {
			if (!articulo.getArtcod().equals("")) {
				operacion = 1;
				imagen = new DefaultStreamedContent(new ByteArrayInputStream(
						articulo.getArtimg()));
				return "/paginas/articulo";
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

	public void registrar() {
		operacion = 0;
		articulo = new TARTI();
		try {
			imagen = new DefaultStreamedContent(FacesContext
					.getCurrentInstance().getExternalContext()
					.getResourceAsStream("/resources/images/NoDisponible.jpg"));
			articulo.setArtimg(IOUtils.toByteArray(FacesContext
					.getCurrentInstance().getExternalContext()
					.getResourceAsStream("/resources/images/NoDisponible.jpg")));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void close() {
		RequestContext.getCurrentInstance().closeDialog(null);
	}

	public void guardar() {
		if (!articulo.getArtdes().trim().equals("")) {
			mensaje = "";
			if (articulo.getArttip().getDesesp().trim().contains("no existe")) {
				mensaje += "Tipo: código no existe;\n";
			}
			if (articulo.getArtmod().getDesesp().trim().contains("no existe")) {
				mensaje += "Modelo: código no existe;\n";
			}
			if (articulo.getArtmed().getDesesp().trim().contains("no existe")) {
				mensaje += "Medida: código no existe;\n";
			}
			if (articulo.getArtmar().getDesesp().trim().contains("no existe")) {
				mensaje += "Marca: código no existe;\n";
			}

			if (mensaje.equals("")) {
				articulo.setError(false);
				if (operacion == 0) {
					articulo.setArusin(sesion.getUsuario().getUsucve());
					int result = servicio.registrar(articulo);
					if (result > 0) {
						mensaje = "Artículo registrado con el código: "
								+ articulo.getArtcod();
						RequestContext.getCurrentInstance().execute(
								"PF('dlg').show()");
					} else {
						mensaje = "Error al registrar artículo (SQL).";
						RequestContext.getCurrentInstance().execute(
								"PF('dlg').show()");
					}
				} else {
					articulo.setArusmd(sesion.getUsuario().getUsucve());
					articulo.setArfemd(Calendar.getInstance().getTime());
					int result = servicio.modificar(articulo);
					if (result > 0) {
						mensaje = "Artículo modificado";
						RequestContext.getCurrentInstance().execute(
								"PF('dlg').show()");
					} else {
						mensaje = "Error al modificar artículo (SQL).";
						RequestContext.getCurrentInstance().execute(
								"PF('dlg').show()");
					}
				}
			} else {
				articulo.setError(true);
				titulo = "Mensaje de Error";
				RequestContext.getCurrentInstance().execute("PF('dlg').show()");
			}
		}
	}

	public void eliminar() {
		if (articulo != null) {
			if (!articulo.getArtcod().equals("")) {
				int count = servicio.countReferences(articulo);
				if (count == 0) {
					int result = servicio.eliminar(articulo);
					if (result > 0) {
						articulos.remove(articulo);
						mensaje = "Se ha eliminado correctamente";
						RequestContext.getCurrentInstance().update("dlg");
						RequestContext.getCurrentInstance().execute(
								"PF('dlg').show()");
					} else {
						mensaje = "Error al eliminar artículo (SQL).";
						RequestContext.getCurrentInstance().update("dlg");
						RequestContext.getCurrentInstance().execute(
								"PF('dlg').show()");
					}
				}else{
					mensaje = "El artículo no se puede eliminar porque esta siendo usando en solicitudes.";
					RequestContext.getCurrentInstance().update("dlg");
					RequestContext.getCurrentInstance().execute(
							"PF('dlg').show()");
				}

			} else {
				mensaje = "Seleccione un registro.";
				RequestContext.getCurrentInstance().update("dlg");
				RequestContext.getCurrentInstance().execute("PF('dlg').show()");
			}
		} else {
			mensaje = "Seleccione un registro.";
			RequestContext.getCurrentInstance().update("dlg");
			RequestContext.getCurrentInstance().execute("PF('dlg').show()");
		}
	}

	public void upload(FileUploadEvent event) {
		try {
			articulo.setArtimg(IOUtils.toByteArray(event.getFile()
					.getInputstream()));
			imagen = new DefaultStreamedContent(event.getFile()
					.getInputstream());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void buscar() {
		barticulo.setArtcod(barticulo.getArtcod().trim().equals("") ? "%"
				: barticulo.getArtcod().trim().toUpperCase());
		barticulo.setArtdes(barticulo.getArtdes().trim().equals("") ? "%"
				: barticulo.getArtdes().trim().toUpperCase());
		articulos = servicio.buscarArticulos(barticulo);
	}

	public void showBuscar(String pagina) {
		articulos.clear();
		articulo = new TARTI();
		barticulo = new TARTI();

		Map<String, Object> options = new HashMap<String, Object>();
		options.put("modal", true);
		options.put("draggable", true);
		options.put("resizable", false);
		options.put("contentHeight", 480);
		options.put("contentWidth", 550);
		RequestContext.getCurrentInstance().openDialog(pagina, options, null);
	}

	public void closeBuscar() {
		RequestContext.getCurrentInstance().closeDialog(null);
	}

	public void aceptar() {
		RequestContext.getCurrentInstance().closeDialog(articulo);
	}

	public void buscarTipo() {
		System.out.println("buscando tipo");
		if (articulo.getArttip().getTbespe() != null) {
			articulo.getArttip().setTbiden("TIPOS");
			TTABD catalogo = servicio.buscar(articulo.getArttip());
			if (catalogo != null) {
				articulo.setArttip(catalogo);
			} else {
				articulo.getArttip().setDesesp("Código no existe");
			}
		} else {
			System.out.println(" aa " + articulo.getArttip().getTbespe());
		}
	}

	public void buscarMedida() {
		System.out.println("buscando medida");
		if (articulo.getArtmed().getTbespe() != null) {
			articulo.getArtmed().setTbiden("UGMED");
			TTABD catalogo = servicio.buscar(articulo.getArtmed());
			if (catalogo != null) {
				articulo.setArtmed(catalogo);
			} else {
				articulo.getArtmed().setDesesp("Código no existe");
			}
		} else {
			System.out.println(" aa " + articulo.getArtmed().getTbespe());
		}
	}

	public void buscarMarca() {
		System.out.println("buscando marca");
		if (articulo.getArtmar().getTbespe() != null) {
			articulo.getArtmar().setTbiden("MARCA");
			TTABD catalogo = servicio.buscar(articulo.getArtmar());
			if (catalogo != null) {
				articulo.setArtmar(catalogo);
			} else {
				articulo.getArtmar().setDesesp("Código no existe");
			}
		} else {
			System.out.println(" aa " + articulo.getArtmar().getTbespe());
		}
	}

	public void buscarModelo() {
		System.out.println("buscando modelo");
		if (articulo.getArtmod().getTbespe() != null) {
			articulo.getArtmod().setTbiden("MODEL");
			TTABD catalogo = servicio.buscar(articulo.getArtmod());
			if (catalogo != null) {
				articulo.setArtmod(catalogo);
			} else {
				articulo.getArtmod().setDesesp("Código no existe");
			}
		} else {
			System.out.println(" aa " + articulo.getArtmod().getTbespe());
		}
	}

	public List<TARTI> getArticulos() {
		return articulos;
	}

	public void setArticulos(List<TARTI> articulos) {
		this.articulos = articulos;
	}

	public TARTI getArticulo() {
		return articulo;
	}

	public void setArticulo(TARTI articulo) {
		this.articulo = articulo;
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

	public StreamedContent getImagen() {
		return imagen;
	}

	public void setImagen(StreamedContent imagen) {
		this.imagen = imagen;
	}

	public TARTI getBarticulo() {
		return barticulo;
	}

	public void setBarticulo(TARTI barticulo) {
		this.barticulo = barticulo;
	}

	public SesionBean getSesion() {
		return sesion;
	}

	public void setSesion(SesionBean seguridad) {
		this.sesion = seguridad;
	}

	public List<TARTI> getFarticulos() {
		return farticulos;
	}

	public void setFarticulos(List<TARTI> farticulos) {
		this.farticulos = farticulos;
	}

}
