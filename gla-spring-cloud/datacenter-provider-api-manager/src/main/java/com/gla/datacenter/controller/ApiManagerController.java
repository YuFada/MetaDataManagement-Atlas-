package com.gla.datacenter.controller;

import com.gla.datacenter.domain.ApiNumLimt;
import com.gla.datacenter.domain.CatalogUserMap;
import com.gla.datacenter.domain.InformationApi;
import com.gla.datacenter.domain.RcsResourceCate;
import com.gla.datacenter.intercepter.ApplicationContextHolder;
import com.gla.datacenter.model.ApiManagerModel;
import com.gla.datacenter.model.ApiTestModel;
import com.gla.datacenter.service.ApiManagerService;
import com.limp.framework.core.bean.Pager;
import com.limp.framework.core.bean.Result;
import com.limp.framework.core.bean.ResultCode;
import com.limp.framework.core.constant.ResultMsg;
import com.limp.framework.utils.TextUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Encoder;

import java.util.List;
import java.util.Map;

/**
 *
 * 功能描述: 数据目录生产者控制层
 *
 * @param:
 * @return: 
 * @author: zhangbo
 * @date: 2018/11/9 10:14
 */

@RestController
@CrossOrigin
public class ApiManagerController {

    Logger logger = LoggerFactory.getLogger(ApiManagerController.class);


    @Autowired
    private ApiManagerService apiManagerService;

    /**
     *
     * 功能描述: api生产者列表查询
     *
     * @param: [informationApi, pager]
     * @return: java.lang.String
     * @auther: zhangbo
     * @date: 2018/9/25 13:00
     */
    @RequestMapping(value = "/api/provider/getList")
    public String getProviderList(@RequestBody InformationApi informationApi) {
        String userId= ApplicationContextHolder.currentUser().getId();
        logger.info("访问者id{}",userId);
        return apiManagerService.getProviderList(informationApi, informationApi.getPager());
    }

    /**
     *
     * 功能描述: 生产者测试中心立即检测
     *
     * @param:
     * @return: java.lang.String
     * @auther: zhangbo
     * @date: 2018/12/26 16:39
     */
    @RequestMapping(value = "/api/provider/monitor")
    public String startMonitor(){
        return apiManagerService.startMonitor();
    }

    /**
     *
     * 功能描述: 生产者测试中心获取最近一次检测数据
     *
     * @param:
     * @return: java.lang.String
     * @auther: zhangbo
     * @date: 2018/12/28 9:46
     */
    @RequestMapping(value = "/api/provider/monitor/getdata")
    public String getProviderMonitorData(){
        return apiManagerService.getProviderMonitorData();
    }

    /**
     *
     * 功能描述: api消费者列表查询
     *
     * @param: [informationApi]
     * @return: java.lang.String
     * @auther: zhangbo
     * @date: 2018/10/26 13:15
     */
    @RequestMapping(value = "/api/consumer/getList")
    public String getConsumerList(@RequestBody InformationApi informationApi){
        Pager<InformationApi> informationApiPage = apiManagerService.getConsumerList(informationApi,informationApi.getPager());
        return Result.getInstance(ResultCode.SUCCESS.toString(), ResultMsg.SUCCESS,informationApiPage,"").getJson();
    }

    /**
     *
     * 功能描述: 校验当前已加入的api是否存在，并返回存在的值
     *
     * @param: 
     * @param informationApi
     * @return: java.lang.String
     * @author: zhangbo
     * @date: 2019/1/17 16:59
     */
    @RequestMapping(value = "/api/check/join")
    public String checkJoinApi(@RequestBody InformationApi informationApi){
       return apiManagerService.checkJoinApi(informationApi);
    }

    /**
     *
     * 功能描述: v2获取消费者api总数、未申请、申请中、开放、已授权个数
     *
     * @param: 
     * @param informationApi
     * @return: java.lang.String
     * @auther: zhangbo
     * @date: 2018/12/19 11:33
     */
    @RequestMapping(value = "/api/consumer/getList/two")
    public String getConsumerListTwo(@RequestBody InformationApi informationApi){
        return apiManagerService.getConsumerListTwo(informationApi,informationApi.getPager());
    }

    /**
     *
     * 功能描述: 消费者加入申请(公开的api直接授权,支持批量加入)
     *
     * @param: 
     * @param apiNumLimt
     * @return: java.lang.String
     * @auther: zhangbo
     * @date: 2018/12/19 15:24
     */
    @RequestMapping(value = "/api/consumer/apply")
    public String addApply(@RequestBody ApiNumLimt apiNumLimt){
        return apiManagerService.addApply(apiNumLimt);
    }

    /**
     *
     * 功能描述: api管理者列表查询
     *
     * @param: 
     * @param informationApi
     * @return: java.lang.String
     * @auther: zhangbo
     * @date: 2018/11/26 18:36
     */
    @RequestMapping(value = "/api/manager/getList")
    public String getManagerList(@RequestBody InformationApi informationApi){
        Pager<InformationApi> informationApiPage = apiManagerService.getManagerList(informationApi,informationApi.getPager());
        return Result.getInstance(ResultCode.SUCCESS.toString(), ResultMsg.SUCCESS,informationApiPage,"").getJson();
    }

    /**
     *
     * 功能描述: 获取2.0管理者api列表(包含分页查询条件)
     *
     * @param:
     * @param informationApi
     * @return: java.lang.String
     * @auther: zhangbo
     * @date: 2018/12/20 10:32
     */
    @RequestMapping(value = "/api/manager/list")
    public String getManagerListTwo(@RequestBody InformationApi informationApi){
        return apiManagerService.getManagerListTwo(informationApi,informationApi.getPager());
    }

    /**
     *
     * 功能描述: 2.0统计管理者下委办局和各委办局下api数量和部门数量
     *
     * @param:
     * @return: java.lang.String
     * @auther: zhangbo
     * @date: 2018/12/19 15:47
     */
    @RequestMapping(value = "/api/manager/count")
    public String countDeptAndApi(){
        return apiManagerService.countDeptAndApi();
    }

    /**
     *
     * 功能描述: api测试列表(分页查询)
     *
     * @param: [informationApi]
     * @return: java.lang.String
     * @auther: zhangbo
     * @date: 2018/10/30 20:45
     */
    @RequestMapping(value = "/api/getTest")
    public String getApiTest(@RequestBody InformationApi informationApi){
        Pager<InformationApi> informationApiPage = apiManagerService.getApiTest(informationApi,informationApi.getPager());
        return Result.getInstance(ResultCode.SUCCESS.toString(), ResultMsg.SUCCESS,informationApiPage,"").getJson();
    }

    /**
     * 功能描述: 订阅目录
     * @param:
     * @param catalogUserMap 用户与目录关系实体
     * @return: java.lang.String
     * @auther: zhengshien
     * @date: 2019/3/4 17:24
     */
    @RequestMapping(value = "/catalogUserMap")
    public String saveCatalogUserMap(@RequestBody CatalogUserMap catalogUserMap){
        logger.debug(TextUtils.format("用户订阅资源目录{0}"),catalogUserMap);
        if(apiManagerService.save(catalogUserMap)){
            return Result.getInstance(ResultCode.SUCCESS.toString(), ResultMsg.SUCCESS,"","").getJson();
        }
        return Result.getInstance(ResultCode.ERROR.toString(), ResultMsg.ERROR,"","").getJson();
    }

    /**
     *
     * 功能描述: 手动测试
     *
     * @param: [apiTestModel]
     * @return: java.lang.String
     * @auther: zhangbo
     * @date: 2018/10/30 21:10
     */
    @RequestMapping(value = "/api/test")
    public String apiTest(@RequestBody ApiTestModel apiTestModel){
        long startTime = System.currentTimeMillis();
        String result = apiManagerService.apiTest(apiTestModel);
        long endTime = System.currentTimeMillis();
        logger.info("手动测试耗时:{}",endTime-startTime);
        return result;
    }

    /**
     *
     * 功能描述: 新增保存API
     *
     * @param: [apiManagerModel]
     * @return: java.lang.String
     * @auther: zhangbo
     * @date: 2018/9/27 15:02
     */
    @RequestMapping(value = "/api/save")
    public String saveApi(@RequestBody ApiManagerModel apiManagerModel){

        return apiManagerService.saveApi(apiManagerModel);
    }

    /**
     *
     * 功能描述: 删除API（逻辑删除）
     *
     * @param: [id]
     * @return: boolean
     * @auther: zhangbo
     * @date: 2018/9/25 10:35
     */
    @RequestMapping(value = "/api/delete/{id}")
    public String deleteApi(@PathVariable("id") String id){

        return apiManagerService.deleteApi(id);
    }

    /**
     *
     * 功能描述: 根据主键获取API信息
     *
     * @param: [id]
     * @return: java.lang.String
     * @auther: zhangbo
     * @date: 2018/9/25 13:31
     */
    @RequestMapping(value = "/api/{id}")
    public String getApiById(@PathVariable("id") String id){
        ApiManagerModel apiManagerModel = new ApiManagerModel();
        apiManagerModel = apiManagerService.getApiById(id);
        return Result.getInstance(ResultCode.SUCCESS.toString(),ResultMsg.SUCCESS,apiManagerModel,"").getJson();
    }

    /**
     *
     * 功能描述: 消费者根据主键获取API信息
     *
     * @param:
     * @param id
     * @return: java.lang.String
     * @auther: zhangbo
     * @date: 2019/1/2 9:47
     */
    @RequestMapping(value = "/api/consumer/{id}")
    public String getConsumerApiById(@PathVariable("id") String id){
        return apiManagerService.getConsumerApiById(id);
    }

    /**
     *
     * 功能描述: 给用户授权api
     *
     * @param: [apiNumLimt]
     * @return: java.lang.String
     * @auther: zhangbo
     * @date: 2018/10/29 15:37
     */
    @PostMapping(value = "/api/empower/set")
    public String empowerUser(@RequestBody ApiNumLimt apiNumLimt){
        return apiManagerService.empowerUser(apiNumLimt);
    }

    /**
     *
     * 功能描述: 批量api授权
     *
     * @param: 
     * @param apiNumLimt
     * @return: java.lang.String
     * @auther: zhangbo
     * @date: 2018/12/5 15:05
     */
    @PostMapping(value = "/api/empower/sets")
    public String batchEmpowerUser(@RequestBody ApiNumLimt apiNumLimt){
        return apiManagerService.batchEmpowerUser(apiNumLimt);
    }

    /**
     *
     * 功能描述: 授权管理-api列表
     *
     * @param: [informationApi]
     * @return: java.lang.String
     * @auther: zhangbo
     * @date: 2018/10/29 18:19
     */

    @PostMapping(value = "/api/empower/list")
    public String empowerList(@RequestBody InformationApi informationApi){
        return apiManagerService.empowerList(informationApi,informationApi.getPager());
    }

    /**
     *
     * 功能描述: 查看授权管理-api授权详情
     *
     * @param: [id]
     * @return: java.lang.String
     * @auther: zhangbo
     * @date: 2018/10/29 17:53
     */
    @RequestMapping(value = "/api/empower/look/{id}")
    public String lookEmpower(@PathVariable("id") String id){
        return apiManagerService.lookEmpower(id);
    }

    /**
     *
     * 功能描述: 取消授权
     *
     * @param: [apiNumLimt]
     * @return: java.lang.String
     * @auther: zhangbo
     * @date: 2018/10/30 12:10
     */
    @DeleteMapping(value = "/api/empower/del")
    public String delEmpower(@RequestBody ApiNumLimt apiNumLimt){
        return apiManagerService.delEmpower(apiNumLimt);
    }

    /**
     *
     * 功能描述: 根据用户id批量取消授权
     *
     * @param: 
     * @param apiNumLimt
     * @return: java.lang.String
     * @author: zhangbo
     * @date: 2019/1/14 11:18
     */
    @DeleteMapping(value = "/api/empower")
    public String delEmpowerByUserId(@RequestBody ApiNumLimt apiNumLimt){
        return apiManagerService.delEmpowerByUserId(apiNumLimt);
    }

    /**
     *
     * 功能描述: 根据ApiId获取Api信息(包含参数)
     *
     * @param: [id]
     * @return: java.lang.String
     * @auther: zhangbo
     * @date: 2018/9/28 13:33
     */
    @RequestMapping(value = "/api/info/{id}")
    public String getApiInfoById(@PathVariable("id") String id){
        return apiManagerService.getApiInfoById(id);
    }

    /**
     *
     * 功能描述: 查询当前用户所在机构的目录树(接口作废)
     *
     * @param: [categoryApi]
     * @return: java.lang.String
     * @auther: zhangbo
     * @date: 2018/9/27 14:54
     */
    /*@RequestMapping(value = "/api/category")
    public String getCategoryTree(@RequestBody CategoryApi categoryApi){
        return apiManagerService.getCategoryTree(categoryApi);
    }*/

    /**
     *
     * 功能描述: 获取全量api分组(接口作废)
     *
     * @param: [categoryApi]
     * @return: java.lang.String
     * @auther: zhangbo
     * @date: 2018/11/12 13:49
     */
    
    /*@RequestMapping(value = "/api/all/category")
    public String getAllCategoryTree(@RequestBody CategoryApi categoryApi){
        return apiManagerService.getAllCategoryTree(categoryApi);
    }*/

    /**
     *
     * 功能描述: 删除API目录节点(接口作废)
     *
     * @param: [id]
     * @return: java.lang.String
     * @auther: zhangbo
     * @date: 2018/9/27 14:54
     */
    /*@RequestMapping(value = "/api/category/delete/{id}")
    public String deleteCategory(@PathVariable("id") String id){
        return apiManagerService.deleteCategory(id);
    }*/

    /**
     *
     * 功能描述: 检查目录是否存在API
     *
     * @param: [id]
     * @return: int
     * @auther: zhangbo
     * @date: 2018/9/27 14:55
     */
    @RequestMapping(value = "/api/category/check/{id}")
    public int checkCategory(@PathVariable("id") String id){
        return apiManagerService.checkCategory(id);
    }

    /**
     *
     * 功能描述: 添加API目录(接口作废)
     *
     * @param: [categoryApi]
     * @return: java.lang.String
     * @auther: zhangbo
     * @date: 2018/9/27 14:55
     */
    /*@RequestMapping(value = "/api/category/add")
    public String addCategory(@RequestBody CategoryApi categoryApi){
        return apiManagerService.addCategory(categoryApi);
    }*/

    /**
     *
     * 功能描述: 修改API目录(接口作废)
     *
     * @param: [categoryApi]
     * @return: java.lang.String
     * @auther: zhangbo
     * @date: 2018/9/27 14:55
     */
    /*@RequestMapping(value = "/api/category/update")
    public String updateCategory(@RequestBody CategoryApi categoryApi){
        return apiManagerService.updateCategory(categoryApi);
    }*/

    /**
     *
     * 功能描述: 获取apiCode
     *
     * @param: []
     * @return: java.lang.String
     * @auther: zhangbo
     * @date: 2018/10/25 17:07
     */
    @RequestMapping(value = "/api/code")
    public String createApiCode(){
        return apiManagerService.createApiCode();
    }

    /**
     *
     * 功能描述: 根据apicode获取api基本信息
     *
     * @param: 
     * @param code
     * @return: java.util.Map<java.lang.String,java.lang.Object>
     * @auther: zhangbo
     * @date: 2018/12/10 15:03
     */
    @RequestMapping(value = "/api/getOne")
    public Map<String, Object> getApiInformationByCode(@RequestParam("code") String code){
        return apiManagerService.getApiInformationByCode(code);
    }

    /**
     *
     * 功能描述: v2获取api总数、可用、异常数量
     *
     * @param: 正常:useCount;异常:errorCount;总数:count
     * @return: java.lang.String
     * @auther: zhangbo
     * @date: 2018/11/27 16:02
     */
    @RequestMapping(value = "/api/provider/statuscount")
    public String countApiStatusAndNum(){
        return apiManagerService.countApiStatusAndNum();
    }

    /**
     *
     * 功能描述: v2获取消费者api总数、未申请、申请中、开放、已授权个数
     *
     * @param:
     * @return: java.lang.String
     * @auther: zhangbo
     * @date: 2018/12/19 9:59
     */
    @RequestMapping(value = "/api/consumer/count")
    public String countConsumerApi(){
        return apiManagerService.countConsumerApi();
    }

    /**
     *
     * 功能描述: 根据用户账号模糊查询消费者
     *
     * @param: 
     * @param account
     * @return: java.lang.String
     * @auther: zhangbo
     * @date: 2018/12/13 19:48
     */
    @GetMapping(value = "/api/user")
    public String getUserAccountByLike(@RequestParam("account") String account){
        return apiManagerService.getUserAccountByLike(account);
    }

    /**
     * 功能描述: 消费者查看已授权api与app个数
     * @param:
     * @return: java.lang.String
     * @auther: zhengshien
     * @date: 2019/3/25 10:11
     */
    @RequestMapping(value = "/api/app/counts")
    public String apiAndAppCounts(){
        return apiManagerService.apiAndAppCounts();
    }

    /**
     * 功能描述:
     * @param:
     * @param type 数据类型（值为'api'或'app'）
     * @param name 检索条件，根据名称检索
     * @param code 检索条件，根据编码检索
     * @param desc 检索条件，根据描述检索
     * @param pager 分页对象
     * @return: java.lang.String
     * @auther: zhengshien
     * @date: 2019/4/3 10:57
     */
    @RequestMapping(value = "/applying")
    public String applying(@RequestParam("type") String type,
                           @RequestParam(value = "initial",required = false) String initial,
                           @RequestParam(value = "name",required = false) String name,
                           @RequestParam(value = "code",required = false) String code,
                           @RequestParam(value = "desc",required = false) String desc,
                           @RequestBody Pager pager){
        Pager applying = apiManagerService.applying(type,initial, name, code, desc, pager);
        return Result.getInstance(ResultCode.SUCCESS.toString(),ResultMsg.SUCCESS,applying,null).getJson();
    }

    /**
     * 功能描述: 新增用户与api关系（messageServiceImpl远程调用专用）
     * @param:
     * @param apiNumLimt
     * @return: java.lang.Boolean
     * @auther: zhengshien
     * @date: 2019/3/29 14:31
     */
    @RequestMapping(value = "/insertSelective")
    public Boolean insertSelective(@RequestBody ApiNumLimt apiNumLimt){
        return apiManagerService.insertSelective(apiNumLimt);
    }

    /**
     * 功能描述: 根据条件查询api与用户关系列表（messageServiceImpl远程调用专用）
     * @param:
     * @param apiNumLimt
     * @return: java.util.List<com.gla.datacenter.domain.ApiNumLimt>
     * @auther: zhengshien
     * @date: 2019/3/29 14:46
     */
    @RequestMapping(value = "/selectByExample")
    public List<ApiNumLimt> selectByExample(@RequestBody ApiNumLimt apiNumLimt){
        return apiManagerService.selectByExample(apiNumLimt);
    }

    /**
     *
     * 功能描述: 管理者获取所有的部门数据
     *
     * @param:
     * @return: java.lang.String
     * @author: zhangbo
     * @date: 2019/5/28 10:24
     */
    @RequestMapping(value = "/dept/list")
    public String getDeptListByManager(){
        return apiManagerService.getDeptListByManager();
    }

    /**
     *
     * 功能描述: 根据部门id查询api调用次数
     *
     * @param: 
     * @param deptCode
     * @return: java.lang.String
     * @author: zhangbo
     * @date: 2019/5/28 10:36
     */
    @RequestMapping(value = "/api/dept/count")
    public String countApiUseByDept(@RequestParam(value = "deptCode",required = false) String deptCode){
        return apiManagerService.countApiUseByDept(deptCode);
    }

    /**
     * 功能描述: 根据senderid查询用户与api关系（messageServiceImpl远程调用专用）
     * @param:
     * @param senderid
     * @return: java.util.List<com.gla.datacenter.domain.ApiNumLimt>
     * @auther: zhengshien
     * @date: 2019/3/29 14:52
     */
    @RequestMapping(value = "/selectExample")
    public List<ApiNumLimt> selectExample(@RequestParam("senderid") String senderid){
        return apiManagerService.selectExample(senderid);
    }

    /**
     * 功能描述: 根据实体更新（messageServiceImpl远程调用专用）
     * @param:
     * @param apiNumLimt
     * @return: java.lang.Boolean
     * @auther: zhengshien
     * @date: 2019/3/29 15:09
     */
    @RequestMapping(value = "/updateByPrimaryKeySelective")
    public Boolean updateByPrimaryKeySelective(@RequestBody ApiNumLimt apiNumLimt){
        return apiManagerService.updateByPrimaryKeySelective(apiNumLimt);
    }

    /**
     *
     * 功能描述: 用户资源目录订阅列表
     *
     * @param:
     * @return: java.lang.String
     * @author: zhangbo
     * @date: 2019/4/1 14:45
     */
    @RequestMapping(value = "/resource/subscribe")
    public String getSubscribeResourcePageByUserId(@RequestBody RcsResourceCate rcsResourceCate){
        return apiManagerService.getSubscribeResourcePageByUserId(rcsResourceCate);
    }

    /**
     *
     * 功能描述: 用户订阅目录的资源编码集合
     *
     * @param:
     * @param userId
     * @return: java.lang.String
     * @author: zhangbo
     * @date: 2019/4/2 9:31
     */
    @RequestMapping(value = "/resource/subscribe/code")
    public String getSubscribeResourceByUserId(@RequestParam("userId") String userId){
        return apiManagerService.getSubscribeResourceByUserId(userId);
    }

    /**
     *
     * 功能描述: 消费者查看资源目录列表
     *
     * @param: 
     * @param rcsResourceCate
     * @return: java.lang.String
     * @author: zhangbo
     * @date: 2019/4/2 9:46
     */
    @RequestMapping(value = "/resource/consumer/catalog")
    public String getResourceCatalogConsumerPages(@RequestBody RcsResourceCate rcsResourceCate, @RequestParam("code") String code){
        return apiManagerService.getResourceCatalogConsumerPages(rcsResourceCate,code);
    }

    /**
     *
     * 功能描述: 根据url和requestType查询是否存在已有的api
     *
     * @param:
     * @param informationApi
     * @return: java.lang.String
     * @author: zhangbo
     * @date: 2019/5/15 10:05
     */
    @RequestMapping(value = "/api/num/conditions")
    public String getNumByConditions(@RequestBody InformationApi informationApi){
        return apiManagerService.getNumByConditions(informationApi);
    }


    @RequestMapping(value = "/resource/file")
    public String MultipartFileConverBase64(MultipartFile file) throws Exception{
        BASE64Encoder base64Encoder = new BASE64Encoder();
        String base64EncoderImg = /*file.getOriginalFilename()+","+ */base64Encoder.encode(file.getBytes());
        return base64EncoderImg;
    }

}
