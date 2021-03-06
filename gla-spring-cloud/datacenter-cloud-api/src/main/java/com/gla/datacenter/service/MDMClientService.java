package com.gla.datacenter.service;


import com.gla.datacenter.domain.DataModelManager;
import com.gla.datacenter.domain.MasterDataManager;
import com.gla.datacenter.domain.ModelField;
import com.gla.datacenter.model.MDMDataModelModels;
import com.gla.datacenter.model.ModelFieldModel;
import com.gla.datacenter.service.fallback.MDMClientServiceFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@FeignClient(value = "DATACENTER-GATEWAY",path = "/api9/mdm",fallbackFactory = MDMClientServiceFallbackFactory.class)
public interface MDMClientService {

    @RequestMapping(value = "/test")
    String getTest();

    /**
     *
     * 功能描述: 分页查询数据模型列表(条件查询)
     *
     * @param:
     * @param dataModelManager
     * @return: java.lang.String
     * @author: zhangbo
     * @date: 2019/6/21 11:27
     */
    @RequestMapping(value = "/model/list")
    String getDataModelListPager(@RequestBody DataModelManager dataModelManager);

    /**
     *
     * 功能描述: 修改数据模型
     *
     * @param: 
     * @param dataModelManager
     * @return: java.lang.String
     * @author: zhangbo
     * @date: 2019/6/21 11:47
     */
    @RequestMapping(value = "/model/edit")
    String editDataModel(@RequestBody DataModelManager dataModelManager);

    /**
     *
     * 功能描述: 删除数据模型
     *
     * @param: 
     * @param id
     * @return: java.lang.String
     * @author: zhangbo
     * @date: 2019/6/21 13:47
     */
    @RequestMapping(value = "/model/delete/{id}")
    String deleteDataModel(@PathVariable("id") String id);

    /**
     *
     * 功能描述: 添加数据模型
     *
     * @param: 
     * @param mdmDataModelModels
     * @return: java.lang.String
     * @author: zhangbo
     * @date: 2019/6/21 14:10
     */
    @RequestMapping(value = "/model/add")
    String addDataModel(@RequestBody MDMDataModelModels mdmDataModelModels);

    /**
     *
     * 功能描述: 查看模型包含的字段
     *
     * @param: 
     * @param modelFieldModel
     * @return: java.lang.String
     * @author: zhangbo
     * @date: 2019/6/24 9:38
     */
    @RequestMapping(value = "/field/list")
    String findDataModelField(@RequestBody ModelFieldModel modelFieldModel);

    /**
     *
     * 功能描述: 删除模型字段
     *
     * @param: 
     * @param modelField
     * @return: java.lang.String
     * @author: zhangbo
     * @date: 2019/6/24 10:59
     */
    @DeleteMapping(value = "/field/delete")
    String deleteDataModelField(@RequestBody ModelField modelField);

    /**
     *
     * 功能描述: 更改模型字段
     *
     * @param: 
     * @param modelField
     * @return: java.lang.String
     * @author: zhangbo
     * @date: 2019/6/24 11:24
     */
    @RequestMapping(value = "/field/update")
    String updateDataModelField(@RequestBody ModelField modelField);

    /**
     *
     * 功能描述: 新增模型字段
     *
     * @param: 
     * @param modelField
     * @return: java.lang.String
     * @author: zhangbo
     * @date: 2019/6/24 11:54
     */
    @RequestMapping(value = "/field/add")
    String addDataModelField(@RequestBody ModelField modelField);

    /**
     *
     * 功能描述: 主数据管理列表
     *
     * @param: 
     * @param masterDataManager
     * @return: java.lang.String
     * @author: zhangbo
     * @date: 2019/6/24 15:52
     */
    @RequestMapping(value = "/data/list")
    String getMDMDataListByPage(@RequestBody MasterDataManager masterDataManager);

    /**
     *
     * 功能描述: 主数据添加
     *
     * @param: 
     * @param masterDataManager
     * @return: java.lang.String
     * @author: zhangbo
     * @date: 2019/6/25 9:14
     */
    @RequestMapping(value = "/data/add")
    String addMDMData(@RequestBody MasterDataManager masterDataManager);

    /**
     *
     * 功能描述: 删除主数据
     *
     * @param: 
     * @param id
     * @return: java.lang.String
     * @author: zhangbo
     * @date: 2019/6/25 14:46
     */
    @RequestMapping(value = "/data/delete/{id}")
    String deleteMDMDate(@PathVariable("id") String id);

    /**
     *
     * 功能描述: 数据模型详情
     *
     * @param: 
     * @param id
     * @return: java.lang.String
     * @author: zhangbo
     * @date: 2019/7/5 10:17
     */
    @RequestMapping(value = "/model/details/{id}")
    String getDataModelDetails(@PathVariable("id") String id);

    /**
     *
     * 功能描述: 查询业务数据表列表
     *
     * @param:
     * @return: java.lang.String
     * @author: zhangbo
     * @date: 2019/7/5 10:49
     */
    @RequestMapping(value = "/table/list")
    String getTableNameList(@RequestParam(value = "database",required = false) String database);

    /**
     *
     * 功能描述: 根据表明查询所有字段
     *
     * @param: 
     * @param name
     * @return: java.lang.String
     * @author: zhangbo
     * @date: 2019/7/5 11:22
     */
    @RequestMapping(value = "/table/field")
    String getFieldByTableName(@RequestParam(value = "database",required = false) String database, @RequestParam("name") String name);

    /**
     *
     * 功能描述: 查看草稿数据模型
     *
     * @param:
     * @return: java.lang.String
     * @author: zhangbo
     * @date: 2019/7/5 13:53
     */
    @RequestMapping(value = "/model/draft")
    String getDataModelDraft();

    /**
     *
     * 功能描述: 查询数据模型可用字段
     *
     * @param: 
     * @param modelId
     * @return: java.lang.String
     * @author: zhangbo
     * @date: 2019/7/5 18:40
     */
    @RequestMapping(value = "/field/canuse")
    String getDataModelCanUseFieldByModelId(@RequestParam("modelId") String modelId);

    /**
     *
     * 功能描述: 主数据管理结果列表
     *
     * @param: 
     * @param id
     * @param map
     * @return: java.lang.String
     * @author: zhangbo
     * @date: 2019/7/8 10:00
     */
    @RequestMapping(value = "/data/data/{id}")
    String findMDMDate(@PathVariable("id") String id, @RequestBody Map<String, Object> map);

    /**
     *
     * 功能描述: 根据模型id查询主数据包含字段
     *
     * @param: 
     * @param id
     * @return: java.lang.String
     * @author: zhangbo
     * @date: 2019/7/8 10:21
     */
    @RequestMapping(value = "/data/field/{id}")
    String findMDMField(@PathVariable("id") String id);

    /**
     *
     * 功能描述: 修改主数据
     *
     * @param: 
     * @param masterDataManager
     * @return: java.lang.String
     * @author: zhangbo
     * @date: 2019/7/8 13:40
     */
    @RequestMapping(value = "/data/edit")
    String editMDMData(@RequestBody MasterDataManager masterDataManager);

    /**
     *
     * 功能描述: 获取当前数据库连接所有的库(排除默认的库)
     *
     * @param:
     * @return: java.lang.String
     * @author: zhangbo
     * @date: 2019/7/9 16:22
     */
    @RequestMapping(value = "/database/list")
    String getDatabaseByDB();

    /**
     *
     * 功能描述: 根据数据库名称和表名称查询表数据
     *
     * @param:
     * @param database
     * @param table
     * @param map
     * @return: java.lang.String
     * @author: zhangbo
     * @date: 2019/7/10 10:30
     */
    @RequestMapping(value = "/database/table/data")
    String getTableDataByDatabaseNameAndTableName(@RequestParam("database") String database, @RequestParam("table") String table, @RequestBody Map<String, Object> map);
}
