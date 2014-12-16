package com.rtm.compras.model;

import java.io.Serializable;
import java.util.Date;

public class TPERS implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private String plcodi;
	private String plapep;
	private String plapem;
	private String plnom1;
	private String plnom2;
	private String plusua;
	private TPERS pljefe;
	private TTABD plarea = new TTABD();
	private TTABD plcarg = new TTABD();
	private String plusin;
	private String plusmd;
	private Date plfein;
	private Date plfemd;
	private boolean error; 
	
	public String getPlcodi() {
		return plcodi;
	}
	public void setPlcodi(String plcodi) {
		this.plcodi = plcodi;
	}
	public String getPlapep() {
		return plapep;
	}
	public void setPlapep(String plapep) {
		this.plapep = plapep;
	}
	public String getPlapem() {
		return plapem;
	}
	public void setPlapem(String plapem) {
		this.plapem = plapem;
	}
	public String getPlnom1() {
		return plnom1;
	}
	public void setPlnom1(String plnom1) {
		this.plnom1 = plnom1;
	}
	public String getPlnom2() {
		return plnom2;
	}
	public void setPlnom2(String plnom2) {
		this.plnom2 = plnom2;
	}
	public String getPlusua() {
		return plusua;
	}
	public void setPlusua(String plusua) {
		this.plusua = plusua;
	}
	public TTABD getPlarea() {
		return plarea;
	}
	public void setPlarea(TTABD plarea) {
		this.plarea = plarea;
	}
	public TTABD getPlcarg() {
		return plcarg;
	}
	public void setPlcarg(TTABD plsare) {
		this.plcarg = plsare;
	}
	public TPERS getPljefe() {
		return pljefe;
	}
	public void setPljefe(TPERS pljefe) {
		this.pljefe = pljefe;
	}
	public String getPlusin() {
		return plusin;
	}
	public void setPlusin(String plusin) {
		this.plusin = plusin;
	}
	public String getPlusmd() {
		return plusmd;
	}
	public void setPlusmd(String plusmd) {
		this.plusmd = plusmd;
	}
	public Date getPlfein() {
		return plfein;
	}
	public void setPlfein(Date plfein) {
		this.plfein = plfein;
	}
	public Date getPlfemd() {
		return plfemd;
	}
	public void setPlfemd(Date plfemd) {
		this.plfemd = plfemd;
	}
	public boolean isError() {
		return error;
	}
	public void setError(boolean error) {
		this.error = error;
	}
}
