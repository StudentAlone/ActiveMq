package com.traffic_hand.Activemq.taskManger;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.org.apache.bcel.internal.classfile.Synthetic;
import com.traffic_hand.Activemq.bus.busstask.BusRemindUtilsTask;


@Component
public class QuartzManager {  
	
	@Autowired
	SchedulerFactoryBean schedulerFactory;
	public static ObjectMapper mapper = new ObjectMapper();
		
	public void checkRepeat(String cid) {
		JobKey jobKey = JobKey.jobKey("job" + cid,"jobGroup" + cid);
		if (jobKey!= null&&jobKey.getName().equals("job"+cid)) {
			this.removeJob("job" + cid, "jobGroup" + cid, "triggerName" + cid, "triggerGroupName" + cid);
		}
	}
	
		
	
        /**   
         * @Description: 添加一个定时任务   ;
         *    
         * @param jobName 任务名   
         * @param jobGroupName  任务组名   
         * @param triggerName 触发器名   
         * @param triggerGroupName 触发器组名   
         * @param jobClass  任务   
         * @param cron   时间设置，参考quartz说明文档    
         */    
        public  void addJob(String jobName, String jobGroupName,   
                String triggerName, String triggerGroupName, Class jobClass, String cron,String cidMessKey,String message) {    
            try {    
                Scheduler sched = schedulerFactory.getScheduler(); 
                
                // 任务名，任务组，任务执行类  
                JobDetail jobDetail= JobBuilder.newJob(jobClass).withIdentity(jobName, jobGroupName)
                		.usingJobData(cidMessKey, message).build();  
                System.out.println("设置："+message);
                //jobDetail.getJobDataMap().put(cid, num);
                // 触发器    
                TriggerBuilder<Trigger> triggerBuilder = TriggerBuilder.newTrigger();  
                // 触发器名,触发器组    
                triggerBuilder.withIdentity(triggerName, triggerGroupName);  
                triggerBuilder.startNow();  
                // 触发器时间设定    
                triggerBuilder.withSchedule(CronScheduleBuilder.cronSchedule(cron));  
                // 创建Trigger对象  
                CronTrigger trigger = (CronTrigger) triggerBuilder.build();  
      
      
                // 调度容器设置JobDetail和Trigger  
                sched.scheduleJob(jobDetail, trigger);    
      
      
                // 启动    
                if (!sched.isShutdown()) {    
                    sched.start();    
                }   
            	// 记录接收的消息
            	BusRemindUtilsTask instance = BusRemindUtilsTask.getInstance();
            	instance.getTimerCount().put(jobName, cron);
            } catch (Exception e) {    
                throw new RuntimeException(e);    
            }    
        }    
      
      
        /**   
         * @Description: 修改一个任务的触发时间  
         *    
         * @param jobName   
         * @param jobGroupName  
         * @param triggerName 触发器名  
         * @param triggerGroupName 触发器组名   
         * @param cron   时间设置，参考quartz说明文档     
         */    
        public  void modifyJobTime(String jobName,   
                String jobGroupName, String triggerName, String triggerGroupName, String cron) {    
            try {    
                Scheduler sched = schedulerFactory.getScheduler();    
                TriggerKey triggerKey = TriggerKey.triggerKey(triggerName, triggerGroupName);  
                CronTrigger trigger = (CronTrigger) sched.getTrigger(triggerKey);    
                if (trigger == null) {    
                    return;    
                }    
      
      
                String oldTime = trigger.getCronExpression();    
                if (!oldTime.equalsIgnoreCase(cron)) {   
                    /** 方式一 ：调用 rescheduleJob 开始 */  
                    // 触发器    
                    TriggerBuilder<Trigger> triggerBuilder = TriggerBuilder.newTrigger();  
                    // 触发器名,触发器组    
                    triggerBuilder.withIdentity(triggerName, triggerGroupName);  
                    triggerBuilder.startNow();  
                    // 触发器时间设定    
                    triggerBuilder.withSchedule(CronScheduleBuilder.cronSchedule(cron));  
                    // 创建Trigger对象  
                    trigger = (CronTrigger) triggerBuilder.build();  
                    // 方式一 ：修改一个任务的触发时间  
                    sched.rescheduleJob(triggerKey, trigger);  
                    /** 方式一 ：调用 rescheduleJob 结束 */  
      
      
                    /** 方式二：先删除，然后在创建一个新的Job  */  
                    //JobDetail jobDetail = sched.getJobDetail(JobKey.jobKey(jobName, jobGroupName));    
                    //Class<? extends Job> jobClass = jobDetail.getJobClass();    
                    //removeJob(jobName, jobGroupName, triggerName, triggerGroupName);    
                    //addJob(jobName, jobGroupName, triggerName, triggerGroupName, jobClass, cron);   
                    /** 方式二 ：先删除，然后在创建一个新的Job */  
                	BusRemindUtilsTask instance = BusRemindUtilsTask.getInstance();
                	instance.getTimerCount().put(jobName, cron);
                }    
            } catch (Exception e) {    
                throw new RuntimeException(e);    
            }    
        }    
      
      
        /**   
         * @Description: 移除一个任务   
         *    
         * @param jobName   
         * @param jobGroupName   
         * @param triggerName   
         * @param triggerGroupName   
         */    
        public  void removeJob(String jobName, String jobGroupName,    
                String triggerName, String triggerGroupName) {    
            try {    
                Scheduler sched = schedulerFactory.getScheduler();    
                TriggerKey triggerKey = TriggerKey.triggerKey(triggerName, triggerGroupName);  
                sched.pauseTrigger(triggerKey);// 停止触发器    
                sched.unscheduleJob(triggerKey);// 移除触发器    
                sched.deleteJob(JobKey.jobKey(jobName, jobGroupName));// 删除任务  
                
            	BusRemindUtilsTask instance = BusRemindUtilsTask.getInstance();
            	instance.getTimerCount().remove(jobName);
            	instance.getRunningTask().remove(jobName);
            	instance.getCidList().remove(jobName);
            	instance.getMapRemindTask().remove(jobName);
            } catch (Exception e) {    
                throw new RuntimeException(e);    
            }    
        }    
      
      
        /**   
         * @Description:启动所有定时任务   
         */    
        public  void startJobs() {    
            try {    
                Scheduler sched = schedulerFactory.getScheduler();    
                sched.start();    
            } catch (Exception e) {    
                throw new RuntimeException(e);    
            }    
        }    
      
      
        /**   
         * @Description:关闭所有定时任务   
         */    
        public  void shutdownJobs() {    
            try {    
                Scheduler sched = schedulerFactory.getScheduler();    
                if (!sched.isShutdown()) {    
                    sched.shutdown(true);   
                    BusRemindUtilsTask instance = BusRemindUtilsTask.getInstance();
                	instance.getTimerCount().clear();
                	instance.getRunningTask().clear();
                	instance.getCidList().clear();
                	instance.getMapRemindTask().clear();
                }    
            } catch (Exception e) {    
                throw new RuntimeException(e);    
            }    
        }    
    }  