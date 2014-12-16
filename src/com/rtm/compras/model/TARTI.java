package com.rtm.compras.model;

import java.io.Serializable;
import java.util.Date;

public class TARTI implements Serializable{
	private static final long serialVersionUID = 1L;
	private String artcod;
	private String artdes;
	private byte[] artimg;
	private TTABD arttip = new TTABD();
	private TTABD artmar = new TTABD();
	private TTABD artmod = new TTABD();
	private TTABD artmed = new TTABD();
	private String arusin;
	private String arusmd;
	private Date arfein;
	private Date arfemd;
	
	private boolean error; 
	
	public byte[] getArtimg() {
		return artimg;
	}

	public void setArtimg(byte[] artimg) {
		this.artimg = artimg;
	}

	public String getArtcod() {
		return artcod;
	}

	public void setArtcod(String artcod) {
		this.artcod = artcod;
	}

	public String getArtdes() {
		return artdes;
	}

	public void setArtdes(String artdes) {
		this.artdes = artdes;
	}

	public TTABD getArttip() {
		return arttip;
	}

	public void setArttip(TTABD arttip) {
		this.arttip = arttip;
	}

	public TTABD getArtmar() {
		return artmar;
	}

	public void setArtmar(TTABD artmar) {
		this.artmar = artmar;
	}

	public TTABD getArtmod() {
		return artmod;
	}

	public void setArtmod(TTABD artmod) {
		this.artmod = artmod;
	}

	public TTABD getArtmed() {
		return artmed;
	}

	public void setArtmed(TTABD artmed) {
		this.artmed = artmed;
	}
	
	public String getArusin() {
		return arusin;
	}

	public void setArusin(String arusin) {
		this.arusin = arusin;
	}

	public String getArusmd() {
		return arusmd;
	}

	public void setArusmd(String arusmd) {
		this.arusmd = arusmd;
	}

	public Date getArfein() {
		return arfein;
	}

	public void setArfein(Date arfein) {
		this.arfein = arfein;
	}

	public Date getArfemd() {
		return arfemd;
	}

	public void setArfemd(Date arfemd) {
		this.arfemd = arfemd;
	}

	public boolean isError() {
		return error;
	}

	public void setError(boolean error) {
		this.error = error;
	}
	
}
