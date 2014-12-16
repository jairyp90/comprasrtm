package com.rtm.dao.mybatis.mapper;


import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import com.rtm.compras.model.*;

public interface ProveedorMapper {

   @Select("select * from TPROV order by procve")
   @ResultMap("rmTPROVModel")
   List<TPROV> listarProveedores();
   
   @Select("select * from TPROV where procve=#{procve}")
   @ResultMap("rmTPROVModel")
   TPROV buscar(TPROV proveedor);
   
   List<TPROV> buscarProveedores(TPROV proveedor);
   
   @Insert("insert into TPROV(procve,pronom,prodir,prote1,prote2,prmail,proruc,prusin) "
   		+ "values(#{procve},#{pronom},#{prodir},#{prote1},#{prote2},#{prmail},#{proruc},#{prusin})")
   int registrar(TPROV proveedor); 
   
   @Update("update TPROV set proruc=#{proruc},procve=#{procve}, pronom=#{pronom},prodir=#{prodir},prote1=#{prote1},prote2=#{prote2},prmail=#{prmail},prusmd=#{prusmd} ,prfemd=#{prfemd} where procve=#{procve}")
   int modificar(TPROV proveedor); 
   
   @Delete("delete from TPROV where procve=#{procve}")
   int eliminar(TPROV proveedor);

   @Select("select count(*) from tsolid where sdprov=#{procve}")
   int countReferences(TPROV proveedor);
   
} 