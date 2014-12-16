package com.rtm.compras.model;

import java.io.Serializable;
import java.util.Date;

public class TPROV implements Serializable{
	private static final long serialVersionUID = 1L;
	private String procve;
	private String pronom;
	private String prodir;
	private String prote1;
	private String prote2;
	private String prmail;
	private String prusin;
	private String prusmd;
	private Date prfein;
	private Date prfemd;
	private String proruc;
	private boolean error; 
	
	public String getProcve() {
		return procve;
	}
	public void setProcve(String procve) {
		this.procve = procve;
	}
	public String getPronom() {
		return pronom;
	}
	public void setPronom(String pronom) {
		this.pronom = pronom;
	}
	public String getProdir() {
		return prodir;
	}
	public void setProdir(String prodir) {
		this.prodir = prodir;
	}
	public String getProte1() {
		return prote1;
	}
	public void setProte1(String prote1) {
		this.prote1 = prote1;
	}
	public String getProte2() {
		return prote2;
	}
	public void setProte2(String prote2) {
		this.prote2 = prote2;
	}
	public String getPrmail() {
		return prmail;
	}
	public void setPrmail(String prmail) {
		this.prmail = prmail;
	}
	public boolean isError() {
		return error;
	}
	public void setError(boolean error) {
		this.error = error;
	}
	public String getPrusin() {
		return prusin;
	}
	public void setPrusin(String prusin) {
		this.prusin = prusin;
	}
	public String getPrusmd() {
		return prusmd;
	}
	public void setPrusmd(String prusmd) {
		this.prusmd = prusmd;
	}
	public Date getPrfein() {
		return prfein;
	}
	public void setPrfein(Date prfein) {
		this.prfein = prfein;
	}
	public Date getPrfemd() {
		return prfemd;
	}
	public void setPrfemd(Date prfemd) {
		this.prfemd = prfemd;
	}
	public String getProruc() {
		return proruc;
	}
	public void setProruc(String proruc) {
		this.proruc = proruc;
	}
}
