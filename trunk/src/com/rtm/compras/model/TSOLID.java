package com.rtm.compras.model;


public class TSOLID {
	
	private TSOLI sdnume = new TSOLI();
	private TARTI sdarti = new TARTI(); 
	private TPROV sdprov = new  TPROV();
	private int sdcant;
	private String sdobse;
	
	public TSOLI getSdnume() {
		return sdnume;
	}
	public void setSdnume(TSOLI sdnume) {
		this.sdnume = sdnume;
	}
	public TARTI getSdarti() {
		return sdarti;
	}
	public void setSdarti(TARTI sdarti) {
		this.sdarti = sdarti;
	}
	public TPROV getSdprov() {
		return sdprov;
	}
	public void setSdprov(TPROV sdprov) {
		this.sdprov = sdprov;
	}
	public int getSdcant() {
		return sdcant;
	}
	public void setSdcant(int sdcant) {
		this.sdcant = sdcant;
	}
	public String getSdobse() {
		return sdobse;
	}
	public void setSdobse(String sdobse) {
		this.sdobse = sdobse;
	}
	
}
