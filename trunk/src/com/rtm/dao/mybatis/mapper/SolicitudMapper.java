package com.rtm.dao.mybatis.mapper;

import java.util.List;

import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.rtm.compras.model.TSOLI;
import com.rtm.compras.model.TSOLID;

public interface SolicitudMapper {

	List<TSOLI> listarSolicitud(TSOLI slsitu);	
	   
	int registrar(TSOLI tsoli);   
	
	@Update("update TSOLI set slsitu=#{slsitu.tbespe},slusmd=#{slusmd},slfemd=#{slfemd},slobse=#{slobse} where slnume=#{slnume}")
	int modificarEstado(TSOLI tsoli);
	
	int modificar(TSOLI tsoli);
	
	int eliminar(TSOLI tsoli);
	
	@Select("select sdnume,sdarti,artdes,sdcant,sdobse,sdprov,pronom,prmail,mdd.desesp DESMED,mrc.desesp DESMAR,mdl.desesp DESMOD,tpo.desesp DESTIP from tsolid "
			+ "inner join tarti on sdarti=artcod "
			+ "left join ttabd as mdd on mdd.tbiden='UGMED' and mdd.tbespe=artmed "
			+ "left join ttabd as mrc on mrc.tbiden='MARCA' and mrc.tbespe=artmar "
			+ "left join ttabd as mdl on mdl.tbiden='MODEL' and mdl.tbespe=artmod "
			+ "left join ttabd as tpo on tpo.tbiden='TIPOS' and tpo.tbespe=arttip "
			+ "left join tprov on sdprov=procve "
			+ "where sdnume = #{slnume} ")
	@ResultMap("rmTSOLIDModel")
	List<TSOLID> getDetalle(TSOLI tsoli);
	   
}
