package com.gla.datacenter.mapper.mysql;

import com.gla.datacenter.domain.MasterDataManager;
import com.gla.datacenter.domain.MasterDataManagerExample;
import com.gla.datacenter.domain.ModelField;
import com.limp.framework.core.bean.Pager;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface MasterDataManagerMapper {
    int countByExample(MasterDataManagerExample example);

    int deleteByExample(MasterDataManagerExample example);

    int deleteByPrimaryKey(String id);

    int insert(MasterDataManager record);

    int insertSelective(MasterDataManager record);

    List<MasterDataManager> selectByExampleWithBLOBs(MasterDataManagerExample example);

    List<MasterDataManager> selectByExample(MasterDataManagerExample example);

    MasterDataManager selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") MasterDataManager record, @Param("example") MasterDataManagerExample example);

    int updateByExampleWithBLOBs(@Param("record") MasterDataManager record, @Param("example") MasterDataManagerExample example);

    int updateByExample(@Param("record") MasterDataManager record, @Param("example") MasterDataManagerExample example);

    int updateByPrimaryKeySelective(MasterDataManager record);

    int updateByPrimaryKeyWithBLOBs(MasterDataManager record);

    int updateByPrimaryKey(MasterDataManager record);

    void deleteMasterDataByIdList(@Param("list") List<String> list);

    void deleteTable(@Param("tableNameList") List<String> tableNameList);

    List<Map<String,Object>> findMDMDate(@Param("code") String code,@Param("params") Map<String, Object> params, @Param("page") Pager page);

    List<Map<String,Object>> getDataListBySql(String sql);

    void insertDataByTableName(@Param("modelFields") List<ModelField> modelFields, @Param("tableName") String modelCode, @Param("table") String table);

    List<String> getDatabaseByDB(String prefix);

    int countMDMData(@Param("databaseTable") String databaseTable, @Param("params") Map<String, Object> map);
}