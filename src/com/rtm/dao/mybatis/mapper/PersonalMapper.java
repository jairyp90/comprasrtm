package com.rtm.dao.mybatis.mapper;


import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import com.rtm.compras.model.*;

public interface PersonalMapper {

   @Select("select p.*,a.desesp desare,s.desesp descar,j.plapep apjefe,j.plapem amjefe,j.plnom1 n1jefe,j.plnom2 n2jefe from tpers p "+
		   "inner join tpers j on p.pljefe=j.plcodi "+
		   "inner join ttabd a on a.tbiden='AREAS' and a.tbespe=p.plarea "+
		   "inner join ttabd s on s.tbiden='CARGO' and s.tbespe=p.plcarg order by p.plcodi ")
   @ResultMap("rmTPERSModel")
   List<TPERS> listar();

   int registrar(TPERS personal);

   int modificar(TPERS personal);
   
   @Delete("delete from TPERS where plcodi=#{plcodi}")
   int eliminar(TPERS personal);
   
   @Select("select count(*) from tsoli where slpers=#{plcodi}")
   int countReferences(TPERS personal);
} 
