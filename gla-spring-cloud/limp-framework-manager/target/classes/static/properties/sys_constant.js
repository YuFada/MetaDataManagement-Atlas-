/**
 * 系统设置常量：统一配置，便于管理
 * @type {{}}
 */
var cons={
    /******************规范说明：
     * 1、系统框架定义的已sys开头
     * 2、各个业务系统定义的以各自系统编码前缀开头
     * 3、注意定义的位置（在相应位置编码）****/

    /*****业务设置 begin（##该区域为自己个性化业务系统部分）************************************/


    /*****业务设置 end***********************************/


    /*****系统设置 begin（##该区域为系统设置部分）************************************/

    //工作桌面地址
    sysWorkDeskUrl:"http://www.shinians.com",
    //工作桌面退出地址
    sysWorkDeskLogoutUrl:"/system/user/logOut",


    /**
     * 跳转到指定url
     * @param url
     */
    sysHref:function(url){
        window.location.href=url;

    }
    /***********************系统设置 end************************************/


}


