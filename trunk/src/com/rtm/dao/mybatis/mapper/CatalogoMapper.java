package com.rtm.dao.mybatis.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.rtm.compras.model.*;

public interface CatalogoMapper {

   @Select("select * from TTABD where tbiden=#{tbiden} and tbref1='' order by tbespe")
   @ResultMap("rmTTABDModel")
   List<TTABD> listarCatalogo(String tbiden);	
   
   @Select("select * from TTABD where tbiden=#{tbiden} and tbespe=#{tbespe} and tbref1='' ")
   @ResultMap("rmTTABDModel")
   TTABD buscar(TTABD catalogo);
   
   @Select("select tbiden,tbref1 tbespe,tbref2 desesp from TTABD where tbiden=#{tbiden} and tbespe=#{tbespe} and tbref1<>'' order by tbespe")
   @ResultMap("rmTTABDModel")
   List<TTABD> listarReferencias(TTABD catalogo);
   
   List<TTABD> buscarCatalogos(TTABD catalogo);
   
   int registrar(TTABD catalogo); 
   
   @Update("update TTABD set desesp=#{desesp},tbusmd=#{tbusmd},tbfemd=#{tbfemd} where tbiden=#{tbiden} and tbespe=#{tbespe} and tbref1=''")
   int modificar(TTABD catalogo); 
   
   @Delete("delete from TTABD where tbiden=#{tbiden} and tbespe=#{tbespe} and tbref1=''")
   int eliminar(TTABD catalogo);
   
   int countReferences(TTABD catalogo);
   
   int registrarReferencias(TTABD catalogo);
}
