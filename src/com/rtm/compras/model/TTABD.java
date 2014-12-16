package com.rtm.compras.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class TTABD implements Serializable{
	private static final long serialVersionUID = 1L;
	private String tbiden;
	private String tbespe;
	private String desesp;
	private String tbusin;
	private String tbusmd;
	private Date tbfein;
	private Date tbfemd;
	private List<TTABD> detalles;
	
	public String getTbiden() {
		return tbiden;
	}
	public void setTbiden(String tbiden) {
		this.tbiden = tbiden;
	}
	public String getTbespe() {
		return tbespe;
	}
	public void setTbespe(String tbespe) {
		this.tbespe = tbespe;
	}
	public String getDesesp() {
		return desesp;
	}
	public void setDesesp(String desesp) {
		this.desesp = desesp;
	}
	public String getTbusin() {
		return tbusin;
	}
	public void setTbusin(String tbusin) {
		this.tbusin = tbusin;
	}
	public String getTbusmd() {
		return tbusmd;
	}
	public void setTbusmd(String tbusmd) {
		this.tbusmd = tbusmd;
	}
	public Date getTbfein() {
		return tbfein;
	}
	public void setTbfein(Date tbfein) {
		this.tbfein = tbfein;
	}
	public Date getTbfemd() {
		return tbfemd;
	}
	public void setTbfemd(Date tbfemd) {
		this.tbfemd = tbfemd;
	}
	public List<TTABD> getDetalles() {
		return detalles;
	}
	public void setDetalles(List<TTABD> detalles) {
		this.detalles = detalles;
	}
	
}
