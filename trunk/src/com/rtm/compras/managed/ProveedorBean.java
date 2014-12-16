package com.rtm.compras.managed;

import java.io.IOException;
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

import com.rtm.compras.model.TPROV;
import com.rtm.compras.negocio.business.delegate.GestionProveedor;
import com.rtm.compras.negocio.service.ProveedorService;
import com.rtm.compras.resource.Sesion;

@ManagedBean(name = "tprov")
@SessionScoped
public class ProveedorBean {

	private ProveedorService servicio = GestionProveedor.getTPROVService();
	private List<TPROV> proveedores = new ArrayList<TPROV>();
	private List<TPROV> fproveedores;
	private TPROV proveedor = new TPROV();
	private TPROV bproveedor = new TPROV();
	private int operacion = 0;
	private String mensaje = "";
	private String titulo = "";
	@ManagedProperty("#{tusua}")
	private SesionBean sesion;

	public ProveedorBean() {
		System.out.println("construyendo ProveedorBean");
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

	public void buscar() {
		bproveedor.setProcve(bproveedor.getProcve().trim().equals("") ? "%"
				: bproveedor.getProcve().trim().toUpperCase());
		bproveedor.setPronom(bproveedor.getPronom().trim().equals("") ? "%"
				: bproveedor.getPronom().trim().toUpperCase());
		proveedores = servicio.buscarProveedores(bproveedor);
	}

	public void showBuscar(String pagina) {
		proveedores.clear();
		proveedor = new TPROV();
		bproveedor = new TPROV();

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
		RequestContext.getCurrentInstance().closeDialog(proveedor);
	}

	public void cargar() {
		proveedores = servicio.listarProveedores();
		RequestContext.getCurrentInstance().update("mantenerProveedor");
	}

	public String modificar() {
		if (proveedor != null) {
			if (!proveedor.getProcve().equals("")) {
				operacion = 1;
				return "/paginas/proveedor";
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
		proveedor = new TPROV();
	}

	public void guardar() {
		if (!(proveedor.getProruc().trim().equals("") && proveedor.getPronom()
				.trim().equals(""))) {
			if (operacion == 0) {
				TPROV existe = servicio.buscar(proveedor);
				if (existe == null) {
					proveedor.setProcve(proveedor.getProruc());
					proveedor.setPrusin(sesion.getUsuario().getUsucve());
					proveedor.setError(false);
					int result = servicio.registrar(proveedor);
					if (result > 0) {
						proveedor.setError(false);
						titulo = "Mensaje de Información";
						mensaje = "Proveedor registrado con el código: "
								+ proveedor.getProcve();
						RequestContext.getCurrentInstance().execute(
								"PF('dlg').show()");
					} else {
						proveedor.setError(true);
						titulo = "Mensaje de Error";
						mensaje = "Error al registrar proveedor (SQL).";
						RequestContext.getCurrentInstance().execute(
								"PF('dlg').show()");
					}
				} else {
					proveedor.setError(true);
					titulo = "Mensaje de Información";
					mensaje = "Ya existe un proveedor con el RUC:"
							+ existe.getProcve();
					RequestContext.getCurrentInstance().execute(
							"PF('dlg').show()");
				}
			} else {
				proveedor.setPrusmd(sesion.getUsuario().getUsucve());
				proveedor.setPrfemd(Calendar.getInstance().getTime());
				int result = servicio.modificar(proveedor);
				if (result > 0) {
					proveedor.setError(false);
					titulo = "Mensaje de Información";
					mensaje = "Proveedor modificado";
					RequestContext.getCurrentInstance().execute(
							"PF('dlg').show()");
				} else {
					proveedor.setError(true);
					titulo = "Mensaje de Error";
					mensaje = "Error al modificar proveedor (SQL).";
					RequestContext.getCurrentInstance().execute(
							"PF('dlg').show()");
				}
			}
		}
	}

	public void eliminar() {
		if (proveedor != null) {
			if (!proveedor.getProcve().equals("")) {
				int count = servicio.countReferences(proveedor);
				if (count == 0) {
					int result = servicio.eliminar(proveedor);
					if (result > 0) {
						proveedores.remove(proveedor);
						titulo = "Mensaje de Información";
						mensaje = "Se ha eliminado correctamente";
						RequestContext.getCurrentInstance().execute(
								"PF('dlg').show()");
					} else {
						titulo = "Mensaje de Error";
						mensaje = "Error al eliminar proveedor (SQL).";
						RequestContext.getCurrentInstance().execute(
								"PF('dlg').show()");
					}
				} else {
					mensaje = "El proveedor no se puede eliminar porque esta siendo usando en solicitudes.";
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

	public TPROV getBproveedor() {
		return bproveedor;
	}

	public void setBproveedor(TPROV bproveedor) {
		this.bproveedor = bproveedor;
	}

	public List<TPROV> getFproveedores() {
		return fproveedores;
	}

	public void setFproveedores(List<TPROV> fproveedores) {
		this.fproveedores = fproveedores;
	}

	public SesionBean getSesion() {
		return sesion;
	}

	public void setSesion(SesionBean seguridad) {
		this.sesion = seguridad;
	}

}
