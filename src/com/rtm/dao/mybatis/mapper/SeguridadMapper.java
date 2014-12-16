package com.rtm.dao.mybatis.mapper;

import java.util.List;

import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import com.rtm.compras.model.TPEPE;
import com.rtm.compras.model.TUSUA;

public interface SeguridadMapper {
	
	TUSUA getUsuario(TUSUA usuario);
	
	@Select("select ppcmod,ppdmod,ppsmod,ppcopc,ppdopc,ppsopc,ppcsop,ppdsop,ppssop,ppcopr,ppdopr,ppsopr,'00' pptipo "
			+ "from tpepe where  ppusua=#{ppusua} and ppciac=#{ppciac} "
			+ "union "
			+ "select tmo.tbespe,tmo.desesp,'false',tmo.tbref1,tmo.tbref2,'false',top.tbref1,top.tbref2,'false',tso.tbref1,tso.tbref2,'false','01' pptipo  "
			+ "from ttabd tmo "
			+ "inner join  ttabd top on tmo.tbref1=top.tbespe "
			+ "inner join  ttabd tso on top.tbref1=tso.tbespe "
			+ "where tmo.tbiden='TBMOD' and tmo.tbespe='000062' and tso.tbref1<>'' "
			+ "and not exists ( select * from tpepe where ppusua=#{ppusua} and ppciac=#{ppciac} and ppcmod||ppcopc||ppcsop||ppcopr=tmo.tbespe||tmo.tbref1||top.tbref1||tso.tbref1  ) "
			+ "order by 1,4,7,10;")
	@ResultMap("rmTPEPEModel")
	List<TPEPE> listarPermisos(TPEPE tpepe);

	int grabarPermisos(List<TPEPE> permisos);
	
	@Select("select distinct ppcopc from tpepe where ppusua=#{ppusua} and ppciac=#{ppciac} and ppsopc='true'")
	@ResultMap("rmTPEPEModel")
	List<TPEPE> listarOpciones(TPEPE tpepe);
	
	@Select("select distinct ppcsop from tpepe where ppusua=#{ppusua} and ppciac=#{ppciac} and ppcopc=#{ppcopc} and ppssop='true'")
	@ResultMap("rmTPEPEModel")
	List<TPEPE> listarSubOpciones(TPEPE tpepe);
	
	@Select("select distinct ppcopr from tpepe where ppusua=#{ppusua} and ppciac=#{ppciac} and ppcopc=#{ppcopc} and ppcsop=#{ppcsop} and ppssop='true'")
	@ResultMap("rmTPEPEModel")
	List<TPEPE> listarOperaciones(TPEPE tpepe);

}
