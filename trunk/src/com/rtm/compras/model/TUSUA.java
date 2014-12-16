package com.rtm.compras.model;

public class TUSUA {

	private String usucve;
	private String usucon;
	private TPERS tpers = new TPERS();
	
	public String getUsucve() {
		return usucve;
	}
	public void setUsucve(String usucve) {
		this.usucve = usucve;
	}
	public String getUsucon() {
		return usucon;
	}
	public void setUsucon(String usucon) {
		this.usucon = usucon;
	}
	public TPERS getTpers() {
		return tpers;
	}
	public void setTpers(TPERS tpers) {
		this.tpers = tpers;
	}
			
}
