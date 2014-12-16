package com.rtm.compras.managed;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.primefaces.context.RequestContext;
import org.primefaces.model.CheckboxTreeNode;
import org.primefaces.model.TreeNode;
import com.rtm.compras.model.TPEPE;
import com.rtm.compras.model.TPERS;
import com.rtm.compras.negocio.business.delegate.GestionSeguridad;
import com.rtm.compras.negocio.service.SeguridadService;

@ManagedBean(name = "tsegu")
@ViewScoped
public class SeguridadBean {

	private TreeNode root;
	private TreeNode[] selectedNodes;
	private SeguridadService servicio = GestionSeguridad.getSeguridadService();
	private List<TPERS> personales = new ArrayList<TPERS>();
	private TPERS personal = new TPERS();
	private String mensaje ="";

	public SeguridadBean() {
		System.out.println("Construyendo SeguridadBean");
		if(FacesContext.getCurrentInstance().isPostback()==false){	
			try {
				FacesContext.getCurrentInstance().getExternalContext().redirect("/ProyCompras/");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else{
			personales = servicio.listar();
		}
		
	}
	
	public void getPermisos(){
		TPEPE tpepe = new TPEPE();
		tpepe.setPpciac("TC");
		tpepe.setPpusua(personal.getPlusua().toUpperCase());
		List<TPEPE> permisos = servicio.listarPermisos(tpepe);
		
		root = new CheckboxTreeNode("root", null);
		TreeNode opcion = new CheckboxTreeNode("opcion",permisos.get(0), root);
		TreeNode subopcion = new CheckboxTreeNode("subopcion",permisos.get(0), opcion);
		List<TreeNode> operaciones = new ArrayList<TreeNode>();
		String ppcopc = permisos.get(0).getPpcopc();
		String ppcsop = permisos.get(0).getPpcsop();
		for (TPEPE t : permisos) {
			if(!ppcopc.equals(t.getPpcopc())){
				opcion = new CheckboxTreeNode("opcion",t, root);
				ppcopc=t.getPpcopc();
			}
			if(!ppcsop.equals(t.getPpcsop())){
				subopcion = new CheckboxTreeNode("subopcion",t, opcion);
				ppcsop=t.getPpcsop();
			}
			TreeNode operacion = new CheckboxTreeNode("operacion",t, subopcion);
			operaciones.add(operacion);
		}
		
		for (int i = 0; i <permisos.size(); i++) {
			if(permisos.get(i).getPpsopr().equals("true")){
				operaciones.get(i).setSelected(true);
			}
		}
	   	    
	}
	
	public void grabarPermisos(){
		System.out.println("grabar");
		List<TPEPE> permisos = new ArrayList<TPEPE>();
		List<TreeNode> opciones =root.getChildren();
				
		for (TreeNode opcion : opciones) {
			TPEPE topcion = (TPEPE) opcion.getData();
			List<TreeNode> subopciones =opcion.getChildren();
			String ppsopc = "false";
			if(opcion.isSelected() || opcion.isPartialSelected()){
				ppsopc = "true";
			}
			for (TreeNode subopcion : subopciones) {
				TPEPE tsubopcion = (TPEPE) subopcion.getData();
				List<TreeNode> operaciones =subopcion.getChildren();
				String ppssop = "false";
				if(subopcion.isSelected() || subopcion.isPartialSelected()){
					ppssop = "true";
				}
				for (TreeNode operacion : operaciones) {
					TPEPE toperacion = (TPEPE) operacion.getData();
					TPEPE permiso = new TPEPE();
					permiso.setPpusua(personal.getPlusua().toUpperCase());
					permiso.setPpciac("TC");
					permiso.setPpcmod("000062");
					permiso.setPpdmod("COMPRAS");
					permiso.setPpsmod("true");
					permiso.setPpcopc(topcion.getPpcopc());
					permiso.setPpdopc(topcion.getPpdopc());
					permiso.setPpsopc(ppsopc);
					permiso.setPpcsop(tsubopcion.getPpcsop());
					permiso.setPpdsop(tsubopcion.getPpdsop());
					permiso.setPpssop(ppssop);
					permiso.setPpcopr(toperacion.getPpcopr());
					permiso.setPpdopr(toperacion.getPpdopr());
					permiso.setPpsopr(operacion.isSelected()+"");
					permiso.setPptipo(toperacion.getPptipo());
					permisos.add(permiso);
				}
			}
		}
		
		if(servicio.grabarPermisos(permisos)>0){
			mensaje = "Se guardarón correctamente los cambios";
			getPermisos();
			RequestContext.getCurrentInstance().execute("PF('dlg').show()");
		}
	}
	
	public List<TPERS> getPersonales() {
		return personales;
	}

	public void setPersonales(List<TPERS> personales) {
		this.personales = personales;
	}

	public TPERS getPersonal() {
		return personal;
	}

	public void setPersonal(TPERS personal) {
		this.personal = personal;
	}

	public TreeNode getRoot() {
		return root;
	}

	public void setRoot(TreeNode root) {
		this.root = root;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public TreeNode[] getSelectedNodes() {
		return selectedNodes;
	}

	public void setSelectedNodes(TreeNode[] selectedNodes) {
		this.selectedNodes = selectedNodes;
	}
	
}
