package com.rtm.compras.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TSOLI implements Serializable{
	private static final long serialVersionUID = 1L;
	private int slnume;
	private String slloca;
	private Date slplaz;
	private TTABD slsitu = new TTABD();
	private String slobse;
	private String slmoti;
	private TPERS slpers = new TPERS();
	private List<TSOLID> detalle = new ArrayList<TSOLID>();
	private Date slfein;
	private String slusin;
	private Date slfemd;
	private String slusmd;
	
	public TSOLI() {
		
	}
	
	public TSOLI(String slsitu) {
		getSlsitu().setTbespe(slsitu);
	}
	
	public int getSlnume() {
		return slnume;
	}
	public void setSlnume(int slnume) {
		this.slnume = slnume;
	}
	public TPERS getSlpers() {
		return slpers;
	}
	public void setSlpers(TPERS slpers) {
		this.slpers = slpers;
	}
	public String getSlloca() {
		return slloca;
	}
	public void setSlloca(String slloca) {
		this.slloca = slloca;
	}
	public Date getSlfein() {
		return slfein;
	}
	public void setSlfein(Date slfech) {
		this.slfein = slfech;
	}
	public Date getSlplaz() {
		return slplaz;
	}
	public void setSlplaz(Date slplaz) {
		this.slplaz = slplaz;
	}
	public List<TSOLID> getDetalle() {
		return detalle;
	}
	public void setDetalle(List<TSOLID> detalle) {
		this.detalle = detalle;
	}
	public String getSlobse() {
		return slobse;
	}
	public void setSlobse(String slobse) {
		this.slobse = slobse;
	}
	public String getSlusin() {
		return slusin;
	}
	public void setSlusin(String slusin) {
		this.slusin = slusin;
	}
	public Date getSlfemd() {
		return slfemd;
	}
	public void setSlfemd(Date slfemd) {
		this.slfemd = slfemd;
	}
	public String getSlusmd() {
		return slusmd;
	}
	public void setSlusmd(String slusmd) {
		this.slusmd = slusmd;
	}
	public TTABD getSlsitu() {
		return slsitu;
	}
	public void setSlsitu(TTABD slsitu) {
		this.slsitu = slsitu;
	}
	public String getSlmoti() {
		return slmoti;
	}
	public void setSlmoti(String slmoti) {
		this.slmoti = slmoti;
	}	
	
}
