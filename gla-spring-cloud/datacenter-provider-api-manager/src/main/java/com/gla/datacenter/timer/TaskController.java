package com.gla.datacenter.timer;

import com.gla.datacenter.core.utils.TimerUtils;
import com.gla.datacenter.domain.TimerCenter;
import com.gla.datacenter.mapper.mysql.TimerCenterMapper;
import com.gla.datacenter.service.ApiManagerService;
import com.gla.datacenter.service.TaskService;
import com.limp.framework.core.bean.Result;
import com.limp.framework.core.bean.ResultCode;
import com.limp.framework.core.constant.ExceptionEnum;
import com.limp.framework.core.constant.ResultMsg;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ScheduledFuture;

/**
 * @Auther: zhangbo
 * @Date: 2018/10/11 09:25
 * @Description:
 */
@RestController
@RequestMapping(value = "/task")
public class TaskController {

    private static final String DEFAULT_CRON = "0 0 0 * * ?"/*"0/5 * * * * ?"*/;
    private String cron = DEFAULT_CRON;
    private String cronCopy;
    private int count = 0;
    private String threshold_value = "20";

    private Logger loger = LoggerFactory.getLogger(TaskController.class);

    @Autowired
    private ThreadPoolTaskScheduler threadPoolTaskScheduler;

    @Autowired
    TimerCenterMapper timerCenterMapper;

    @Autowired
    private TaskService taskService;

    @Autowired
    private ApiManagerService apiManagerService;

    @Bean
    public ThreadPoolTaskScheduler threadPoolTaskScheduler(){
        return new ThreadPoolTaskScheduler();
    }

    private ScheduledFuture<?> future;

    @PostConstruct
    private void postConstruct(){
        startCron();
    }

    public void startCron(){

        //判断是否启动
        List<TimerCenter> tc = taskService.getTimerInfor();
        if(tc != null && !tc.isEmpty()){
            Date runTime = tc.get(0).getRunTime();
            String cronStr = tc.get(0).getCron();
            if(StringUtils.isNotBlank(tc.get(0).getThresholdValue())){
                threshold_value = tc.get(0).getThresholdValue();
            }
            String crons = "";
            if(StringUtils.isNotBlank(cron)){
                if(runTime != null || StringUtils.isNotBlank(cronStr)){
                    crons = TimerUtils.formatDateByPattern(runTime,cronStr);
                    if(!crons.equals(cron)){
                        cronCopy = cron = crons;
                        if(future != null){
                            future.cancel(true);
                        }
                        future = threadPoolTaskScheduler.schedule(new task(), new CronTrigger(crons));
                        if(count < 1){
                            ++count;
                        }
                    }
                }
            }else{
                //停止状态
            }
        }else{
            //停止或者数据库无数据或者开始操作
            if(StringUtils.isNotBlank(cron)){

                if(StringUtils.isNotBlank(cronCopy)){
                    if(!cron.equals(cronCopy)){
                        if(future != null){
                            future.cancel(true);
                        }
                        future = threadPoolTaskScheduler.schedule(new task(), new CronTrigger(cronCopy));
                        cron = cronCopy;
                        if(count < 1){
                            ++count;
                        }
                    }
                }else{
                    future = threadPoolTaskScheduler.schedule(new task(), new CronTrigger(cron));
                    cronCopy = cron;
                    ++count;
                }
            }else{
                //当前状态是停止的
            }
        }
    }

    public class task implements Runnable{
        @Override
        public void run(){
            loger.info("任务执行开始时间:{}", new Date());
            apiManagerService.apiTestTask(threshold_value);
            loger.info("任务执行结束时间:{}", new Date());
        }
    }

    /**
     *
     * 功能描述: 停止定时任务(启动的暂时先不写)
     *
     * @param: []
     * @return: void
     * @auther: zhangbo
     * @date: 2018/10/11 11:05
     */
    
    @PostMapping(value = "/stop/1")
    public String stopTask(){
        try{
            if(future != null){
                future.cancel(true);
            }
            //修改数据库参数全部为不可用（暂时）
            timerCenterMapper.updateIsFlag((byte) 1);
            //初始化cron为空
            cron = null;
        }catch (Exception e){
            return Result.getException(ExceptionEnum.TimerStopException).getJson();
        }
        return Result.getInstance(ResultCode.SUCCESS.toString(), ResultMsg.SUCCESS,null,null).getJson();
    }

    @PostMapping(value = "/start/1")
    public String startTask(){
        try{
            if(future != null){
                future.cancel(true);
            }
            timerCenterMapper.updateIsFlag((byte) 0);
            //随便定义，只要不为空即可
            cron = "start";
        }catch(Exception e){
            return Result.getException(ExceptionEnum.TimerStartException).getJson();
        }
        return Result.getInstance(ResultCode.SUCCESS.toString(), ResultMsg.SUCCESS,null,null).getJson();
    }



}
