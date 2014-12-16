package com.rtm.dao.mybatis.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.rtm.compras.model.*;

public interface ArticuloMapper {

   @Select("select artcod,artdes,artimg,arttip,artmed,artmar,artmod,mdd.desesp DESMED,mrc.desesp DESMAR,mdl.desesp DESMOD,tpo.desesp DESTIP,arusin,arfein,arusmd,arfemd from tarti " +
		   "left join ttabd as mdd on mdd.tbiden='UGMED' and mdd.tbespe=artmed "+
		   "left join ttabd as mrc on mrc.tbiden='MARCA' and mrc.tbespe=artmar "+
		   "left join ttabd as mdl on mdl.tbiden='MODEL' and mdl.tbespe=artmod "+ 
		   "left join ttabd as tpo on tpo.tbiden='TIPOS'  and tpo.tbespe=arttip order by artcod")
   @ResultMap("rmTARTIModel")
   List<TARTI> listar();
   
   @Select("select artcod,artdes,artimg,arttip,artmed,artmar,artmod,mdd.desesp DESMED,mrc.desesp DESMAR,mdl.desesp DESMOD,tpo.desesp DESTIP from tarti " +
		   "left join ttabd as mdd on mdd.tbiden='UGMED' and mdd.tbespe=artmed "+
		   "left join ttabd as mrc on mrc.tbiden='MARCA' and mrc.tbespe=artmar "+
		   "left join ttabd as mdl on mdl.tbiden='MODEL' and mdl.tbespe=artmod "+ 
		   "left join ttabd as tpo on tpo.tbiden='TIPOS'  and tpo.tbespe=arttip where artcod=#{artcod} ")
   @ResultMap("rmTARTIModel")
   TARTI buscarArticulo(TARTI articulo);
   
   List<TARTI> buscarArticulos(TARTI articulo);
   
   int registrar(TARTI articulo); 
   
   @Update("update TARTI set artdes=#{artdes},artimg=#{artimg},arttip=#{arttip.tbespe},artmar=#{artmar.tbespe},artmod=#{artmod.tbespe},artmed=#{artmed.tbespe},arusmd=#{arusmd} ,arfemd=#{arfemd} where artcod=#{artcod}")
   int modificar(TARTI articulo); 
   
   @Delete("delete from TARTI where artcod=#{artcod}")
   int eliminar(TARTI articulo); 
   
   @Select("select count(*) from tsolid where sdarti=#{artcod}")
   int countReferences(TARTI articulo);
   
}
