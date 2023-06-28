package cn.klmb.demo.framework.job.util;

import static cn.klmb.demo.framework.common.exception.util.ServiceExceptionUtil.exception;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.core.util.CharUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import cn.klmb.demo.framework.job.config.HttpClientConfig;
import cn.klmb.demo.framework.job.dto.XxlJobChangeTaskDTO;
import cn.klmb.demo.framework.job.entity.XxlJobActuatorManagerInfo;
import cn.klmb.demo.framework.job.entity.XxlJobGroup;
import cn.klmb.demo.framework.job.entity.XxlJobInfo;
import cn.klmb.demo.framework.job.entity.XxlJobResponseInfo;
import cn.klmb.demo.framework.job.entity.XxlJobTaskManagerInfo;
import cn.klmb.demo.framework.mq.message.WebSocketServer;
import cn.klmb.demo.module.system.entity.config.SysConfigDO;
import cn.klmb.demo.module.system.entity.notify.SysNotifyMessageDO;
import cn.klmb.demo.module.system.entity.user.SysUserDO;
import cn.klmb.demo.module.system.enums.CrmEnum;
import cn.klmb.demo.module.system.enums.ErrorCodeConstants;
import cn.klmb.demo.module.system.enums.config.SysConfigKeyEnum;
import cn.klmb.demo.module.system.manager.SysFeishuManager;
import cn.klmb.demo.module.system.service.config.SysConfigService;
import cn.klmb.demo.module.system.service.notify.SysNotifyMessageService;
import cn.klmb.demo.module.system.service.notify.SysNotifySendService;
import cn.klmb.demo.module.system.service.user.SysUserService;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Headers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * xxl-job api 操作工具类
 *
 * @author liuyuepan
 * @date 2023/04/07
 */
@Component
@Slf4j
public class XxlJobApiUtils {

    private static final Logger logger = LoggerFactory.getLogger(XxlJobApiUtils.class);


    @Value("${xxl.job.login.address}")
    private String xxlJobLoginAddress;

    @Value("${xxl.job.login.username}")
    private String xxlJobLoginUserName;

    @Value("${xxl.job.login.password}")
    private String xxlJobLoginPassword;


    private final WebSocketServer webSocketServer;


    private final SysConfigService sysConfigService;

    private final SysNotifyMessageService sysNotifyMessageService;

    private final SysNotifySendService sysNotifySendService;

    private final SysUserService sysUserService;

    private final SysFeishuManager sysFeishuManager;


    public XxlJobApiUtils(WebSocketServer webSocketServer, SysConfigService sysConfigService,
            SysNotifyMessageService sysNotifyMessageService,
            SysNotifySendService sysNotifySendService, SysUserService sysUserService,
            SysFeishuManager sysFeishuManager) {
        this.webSocketServer = webSocketServer;
        this.sysConfigService = sysConfigService;
        this.sysNotifyMessageService = sysNotifyMessageService;
        this.sysNotifySendService = sysNotifySendService;
        this.sysUserService = sysUserService;
        this.sysFeishuManager = sysFeishuManager;
    }


    /**
     * 启动xxl-job任务管理
     *
     * @param taskId 任务管理id
     */
    public void startTask(Long taskId) {
        //获取登录cookie
        HttpClientConfig clientConfig = new HttpClientConfig();
        String cookie = loginTaskCenter(clientConfig);

        //创建参数
        Map<String, String> form = new HashMap<>();
        form.put("id", "" + taskId);

        clientConfig.setUrl(xxlJobLoginAddress + "/jobinfo/start");
        String result = HttpClientUtils.doFormRequest(clientConfig, form, cookie);
        XxlJobResponseInfo info = JSONUtil.toBean(JSONUtil.parseObj(result),
                XxlJobResponseInfo.class);

        if (ObjectUtil.isNull(info) || info.getCode() != HttpStatus.OK.value()) {
            logger.info("启动xxl-job任务失败:" + info.getMsg());
        }
    }

    /**
     * 删除 xxl-job任务管理
     *
     * @param id 任务id
     */
    public void deleteTask(Long id) {
        //获取登录cookie
        HttpClientConfig clientConfig = new HttpClientConfig();
        String cookie = loginTaskCenter(clientConfig);

        //创建任务管理参数
        Map<String, String> form = new HashMap<>();
        form.put("id", id + "");

        clientConfig.setUrl(xxlJobLoginAddress + "/jobinfo/remove");
        String result = HttpClientUtils.doFormRequest(clientConfig, form, cookie);

        XxlJobResponseInfo info = JSONUtil.toBean(JSONUtil.parseObj(result),
                XxlJobResponseInfo.class);

        if (ObjectUtil.isNull(info) || info.getCode() != HttpStatus.OK.value()) {
            logger.info("删除xxl-job任务失败:" + info.getMsg());
        }
    }

    /**
     * 编辑 xxl-job任务管理
     *
     * @param xxlJobInfo 查询参数
     */
    public void editTask(XxlJobInfo xxlJobInfo) {
        //获取登录cookie
        HttpClientConfig clientConfig = new HttpClientConfig();
        String cookie = loginTaskCenter(clientConfig);

        //创建任务管理参数
        Map<String, String> form = new HashMap<>();
        form.put("id", xxlJobInfo.getId() + "");
        //注意这里需要先创建执行器，然后拿到执行器的id赋值给JobGroup
        form.put("jobGroup", xxlJobInfo.getJobGroup() + "");
        form.put("jobDesc", xxlJobInfo.getJobDesc());
        form.put("executorRouteStrategy", "FIRST");
        form.put("scheduleConf", xxlJobInfo.getScheduleConf());
        form.put("glueType", "BEAN");
        form.put("executorHandler", xxlJobInfo.getExecutorHandler());
        form.put("executorBlockStrategy", "SERIAL_EXECUTION");
        form.put("scheduleType", "CRON");
        form.put("misfireStrategy", "DO_NOTHING");
        form.put("executorParam", xxlJobInfo.getExecutorParam());
        form.put("alarmEmail", "liu_yue_pan@163.com");
        form.put("author", "liuyuepan");

        clientConfig.setUrl(xxlJobLoginAddress + "/jobinfo/update");
        String result = HttpClientUtils.doFormRequest(clientConfig, form, cookie);
        XxlJobResponseInfo info = JSONUtil.toBean(JSONUtil.parseObj(result),
                XxlJobResponseInfo.class);

        if (ObjectUtil.isNull(info) || info.getCode() != HttpStatus.OK.value()) {
            logger.info("修改xxl-job任务失败:" + info.getMsg());
        }
    }

    /**
     * 查询所有的task
     *
     * @param xxlJobInfo
     * @return
     */
    public XxlJobTaskManagerInfo selectAllTask(XxlJobInfo xxlJobInfo) {
        //获取登录cookie
        HttpClientConfig clientConfig = new HttpClientConfig();
        String cookie = loginTaskCenter(clientConfig);

        //创建任务管理参数
        Map<String, String> form = new HashMap<>();
        form.put("jobGroup", xxlJobInfo.getJobGroup() + "");
        form.put("triggerStatus", "-1");

        clientConfig.setUrl(xxlJobLoginAddress + "/jobinfo/pageList");
        String result = HttpClientUtils.doFormRequest(clientConfig, form, cookie);
        return JSONUtil.toBean(JSONUtil.parseObj(result), XxlJobTaskManagerInfo.class);
    }

    /**
     * 查询 xxl-job任务管理
     *
     * @param xxlJobInfo 查询参数
     */
    public XxlJobTaskManagerInfo selectTask(XxlJobInfo xxlJobInfo) {
        //获取登录cookie
        HttpClientConfig clientConfig = new HttpClientConfig();
        String cookie = loginTaskCenter(clientConfig);

        //创建任务管理参数
        Map<String, String> form = new HashMap<>();
        form.put("jobGroup", xxlJobInfo.getJobGroup() + "");
//        form.put("jobDesc", xxlJobInfo.getJobDesc());
        form.put("executorHandler", xxlJobInfo.getExecutorHandler());
        form.put("author", xxlJobInfo.getAuthor());
        form.put("triggerStatus", "-1");

        clientConfig.setUrl(xxlJobLoginAddress + "/jobinfo/pageList");
        String result = HttpClientUtils.doFormRequest(clientConfig, form, cookie);
        XxlJobTaskManagerInfo info = JSONUtil.toBean(JSONUtil.parseObj(result),
                XxlJobTaskManagerInfo.class);
        return info;
    }


    /**
     * 创建任务管理
     *
     * @param xxlJobInfo 创建参数
     */
    public XxlJobResponseInfo createTask(XxlJobInfo xxlJobInfo) {
        //获取登录cookie
        HttpClientConfig clientConfig = new HttpClientConfig();
        String cookie = loginTaskCenter(clientConfig);

        //创建任务管理参数
        Map<String, String> form = new HashMap<>();
        form.put("jobGroup", xxlJobInfo.getJobGroup() + "");
        form.put("jobDesc", xxlJobInfo.getJobDesc());
        form.put("scheduleConf", xxlJobInfo.getScheduleConf());
        form.put("glueType", "BEAN");
        form.put("executorHandler", xxlJobInfo.getExecutorHandler());
        form.put("executorBlockStrategy", "SERIAL_EXECUTION");
        form.put("scheduleType", "CRON");
        form.put("misfireStrategy", "DO_NOTHING");
        form.put("alarmEmail", "liu_yue_pan@163.com");
        form.put("executorRouteStrategy", "FIRST");
        form.put("executorParam", xxlJobInfo.getExecutorParam());
        form.put("author", "liuyuepan");
        //创建任务管理
        clientConfig.setUrl(xxlJobLoginAddress + "/jobinfo/add");
        String result = HttpClientUtils.doFormRequest(clientConfig, form, cookie);
        XxlJobResponseInfo info = JSONUtil.toBean(JSONUtil.parseObj(result),
                XxlJobResponseInfo.class);

        if (ObjectUtil.isNull(info) || info.getCode() != HttpStatus.OK.value()) {
            logger.info("创建xxl-job任务失败:" + info.getMsg());
        }

        return info;
    }

    /**
     * 删除执行器
     */
    public void deleteActuator(XxlJobGroup xxlJobGroup) {
        //获取登录cookie
        HttpClientConfig clientConfig = new HttpClientConfig();
        String cookie = loginTaskCenter(clientConfig);

        //创建查询执行器管理器参数
        Map<String, String> form = new HashMap<>();
        form.put("id", xxlJobGroup.getId() + "");

        //创建执行器管理器地址
        clientConfig.setUrl(xxlJobLoginAddress + "/jobgroup/remove");
        String result = HttpClientUtils.doFormRequest(clientConfig, form, cookie);

        XxlJobResponseInfo info = JSONUtil.toBean(JSONUtil.parseObj(result),
                XxlJobResponseInfo.class);
    }


    /**
     * 编辑执行器
     */
    public void editActuator(XxlJobGroup xxlJobGroup) {
        //获取登录cookie
        HttpClientConfig clientConfig = new HttpClientConfig();
        String cookie = loginTaskCenter(clientConfig);

        //创建查询执行器管理器参数
        Map<String, String> form = new HashMap<>();
        form.put("appname", xxlJobGroup.getAppname());
        form.put("title", xxlJobGroup.getTitle());
        form.put("addressType", xxlJobGroup.getAddressType() + "");
        form.put("id", xxlJobGroup.getId() + "");

        //创建执行器管理器地址
        clientConfig.setUrl(xxlJobLoginAddress + "/jobgroup/update");
        String result = HttpClientUtils.doFormRequest(clientConfig, form, cookie);

        XxlJobResponseInfo info = JSONUtil.toBean(JSONUtil.parseObj(result),
                XxlJobResponseInfo.class);
        if (ObjectUtil.isNull(info) || info.getCode() != HttpStatus.OK.value()) {
            logger.info("编辑xxl-job执行器失败:" + info.getMsg());
        }
    }

    /**
     * 查询执行器 (appname 和 title 都是模糊查询)
     *
     * @param xxlJobGroup XxlJobGroup
     * @return xxlJobGroup 集合
     */
    public List<XxlJobGroup> selectActuator(XxlJobGroup xxlJobGroup) {
        //获取登录cookie
        HttpClientConfig clientConfig = new HttpClientConfig();
        String cookie = loginTaskCenter(clientConfig);

        //创建查询执行器管理器参数
        Map<String, String> form = new HashMap<>();
        form.put("appname", xxlJobGroup.getAppname());
        form.put("title", xxlJobGroup.getTitle());

        //创建执行器管理器地址
        clientConfig.setUrl(xxlJobLoginAddress + "/jobgroup/pageList");
        String result = HttpClientUtils.doFormRequest(clientConfig, form, cookie);

        XxlJobActuatorManagerInfo info = JSONUtil.toBean(JSONUtil.parseObj(result),
                XxlJobActuatorManagerInfo.class);
        if (CollectionUtil.isEmpty(info.getData())) {
            throw new RuntimeException("该执行器管理器不存在:" + xxlJobGroup.getAppname());
        }

        return info.getData();
    }


    /**
     * 创建执行器
     *
     * @param xxlJobGroup 创建参数
     */
    public XxlJobResponseInfo createActuator(XxlJobGroup xxlJobGroup) {
        //获取登录cookie
        HttpClientConfig clientConfig = new HttpClientConfig();
        String cookie = loginTaskCenter(clientConfig);

        //创建执行器管理器参数
        Map<String, String> form = new HashMap<>();
        form.put("appname", xxlJobGroup.getAppname());
        form.put("title", xxlJobGroup.getTitle());
        form.put("addressType", xxlJobGroup.getAddressType() + "");

        //创建执行器管理器地址
        clientConfig.setUrl(xxlJobLoginAddress + "/jobgroup/save");
        String result = HttpClientUtils.doFormRequest(clientConfig, form, cookie);
        XxlJobResponseInfo info = JSONUtil.toBean(JSONUtil.parseObj(result),
                XxlJobResponseInfo.class);

        if (ObjectUtil.isNull(info) || info.getCode() != HttpStatus.OK.value()) {
            logger.info("创建xxl-job执行器失败:" + info.getMsg());
        }
        return info;
    }


    /**
     * 登录任务调度平台
     *
     * @param clientConfig clientConfig
     * @return cookie
     */
    public String loginTaskCenter(HttpClientConfig clientConfig) {
        Map<String, String> loginForm = new HashMap<>();
        clientConfig.setUrl(xxlJobLoginAddress + "/login");
        clientConfig.setUsername(xxlJobLoginUserName);
        clientConfig.setPassword(xxlJobLoginPassword);
        loginForm.put("userName", xxlJobLoginUserName);
        loginForm.put("password", xxlJobLoginPassword);
        Headers headers = HttpClientUtils.doLoginRequest(clientConfig, loginForm);
        assert headers != null;
        return headers.get("Set-Cookie");
    }


    public static void main(String[] args) {
//        LocalDateTime localDateTime = LocalDateTimeUtil.beginOfDay(LocalDateTime.now());
//        LocalDateTime now = LocalDateTime.now();
//        int currentMinute = now.getHour();
//        int minuteValue = 0;
//        double sub = NumberUtil.sub(currentMinute, minuteValue);
//        //减
//        LocalDateTime localDateTime1 = now.minusHours((long) sub);
//        localDateTime1 = localDateTime1.minusMinutes((long) NumberUtil.sub(now.getMinute(), 0));
//        System.out.println(localDateTime1);

        LocalDateTime time = LocalDateTime.of(2022, 11, 30, 6, 6, 6);
        System.out.println(time);

    }

    private void sendMessage(String name, String ownerUserId, String messageType, String nextTime) {
        Map<String, Object> map = new HashMap<>();
        map.put("name", name);
        map.put("contractType", messageType);
        map.put("nextTime", nextTime);
        String bizId;
        if (StrUtil.equals(messageType, CrmEnum.CONTRACT.getType().toString())) {
            bizId = sysNotifySendService.sendSingleNotifyToAdmin(ownerUserId,
                    "contractRemind", map);
        } else {
            bizId = sysNotifySendService.sendSingleNotifyToAdmin(ownerUserId,
                    "contactsRemind", map);
        }
        SysNotifyMessageDO sysNotifyMessageDO = sysNotifyMessageService.getByBizId(
                bizId);
        webSocketServer.sendOneMessage(ownerUserId,
                JSONUtil.toJsonStr(JSONUtil.parse(sysNotifyMessageDO)));
        SysUserDO sysUserDO = sysUserService.getByBizId(ownerUserId);
        String fsUserId = sysUserDO.getFsUserId();
        sysFeishuManager.sendMsg(fsUserId, sysNotifyMessageDO.getTemplateContent());

    }

    /**
     * 创建任务
     *
     * @param xxlJobChangeTaskDTO
     */
    @Async
    public void changeTask(XxlJobChangeTaskDTO xxlJobChangeTaskDTO) {
        XxlJobGroup xxlJobGroup = new XxlJobGroup();
        xxlJobGroup.setAppname(xxlJobChangeTaskDTO.getAppName());
        xxlJobGroup.setTitle(xxlJobChangeTaskDTO.getTitle());
        List<XxlJobGroup> xxlJobGroups = selectActuator(xxlJobGroup);
        XxlJobInfo xxlJobInfo = new XxlJobInfo();
        xxlJobInfo.setJobGroup(xxlJobGroups.get(0).getId());
        xxlJobInfo.setExecutorHandler(xxlJobChangeTaskDTO.getExecutorHandler());
        xxlJobInfo.setAuthor(xxlJobChangeTaskDTO.getAuthor());
        XxlJobTaskManagerInfo xxlJobTaskManagerInfo = selectTask(xxlJobInfo);
        if (xxlJobChangeTaskDTO.getOperateType() != 3 && ObjectUtil.isNotNull(
                xxlJobChangeTaskDTO.getNextTime())
                && LocalDateTimeUtil.toEpochMilli(xxlJobChangeTaskDTO.getNextTime()) != 0) {
            if (LocalDateTime.now().isAfter(xxlJobChangeTaskDTO.getNextTime())) {
                throw exception(
                        ErrorCodeConstants.USER_NOT_EXISTS);
            }
            if (LocalDateTimeUtil.between(LocalDateTime.now(), xxlJobChangeTaskDTO.getNextTime())
                    .toMinutes() <= 60L) {
                sendMessage(xxlJobChangeTaskDTO.getName(), xxlJobChangeTaskDTO.getOwnerUserId(),
                        xxlJobChangeTaskDTO.getMessageType(), xxlJobChangeTaskDTO.getNextTime()
                                .format(DateTimeFormatter.ofPattern(
                                        DatePattern.NORM_DATETIME_PATTERN)));
                //删除掉已经在xxl-job 中存在的任务
                if (ObjectUtil.isNotNull(xxlJobTaskManagerInfo) && CollUtil.isNotEmpty(
                        xxlJobTaskManagerInfo.getData())) {
                    List<XxlJobInfo> data = xxlJobTaskManagerInfo.getData();
                    if (CollUtil.isNotEmpty(data)) {
                        for (XxlJobInfo datum : data) {
                            List<String> split = StrUtil.split(datum.getExecutorParam(),
                                    CharUtil.COMMA);
                            if (CollUtil.contains(split, xxlJobChangeTaskDTO.getOwnerUserId())) {
                                deleteTask(datum.getId());
                            }
                        }
                    }
                }
                return;
            }
            String nextTimeStr = getTime(xxlJobChangeTaskDTO.getNextTime());
            if (StrUtil.isNotBlank(nextTimeStr)) {
                xxlJobInfo.setJobDesc(
                        StrUtil.format("{}{}下次联系时间{}定时任务！", xxlJobChangeTaskDTO.getMessageType(),
                                xxlJobChangeTaskDTO.getName(), nextTimeStr));
                xxlJobInfo.setScheduleConf(CronUtil.onlyOnce(nextTimeStr));
                List<String> list = Arrays.asList(xxlJobChangeTaskDTO.getOwnerUserId(),
                        (xxlJobChangeTaskDTO.getContactsType().toString()),
                        xxlJobChangeTaskDTO.getBizId(),
                        xxlJobChangeTaskDTO.getNextTime().format(
                                DateTimeFormatter.ofPattern(DatePattern.NORM_DATETIME_PATTERN)));
                xxlJobInfo.setExecutorParam(CollUtil.join(list, ","));
                if (xxlJobChangeTaskDTO.getOperateType() == 2 && ObjectUtil.isNotNull(
                        xxlJobTaskManagerInfo) && CollUtil.isNotEmpty(
                        xxlJobTaskManagerInfo.getData())) {
                    // 增加edit标志时为了防止在执行更新用户下次联系时间时，定时任务创建不上
                    boolean edit = false;
                    List<XxlJobInfo> data = xxlJobTaskManagerInfo.getData();
                    for (XxlJobInfo datum : data) {
                        String executorParam = datum.getExecutorParam();
                        List<String> split = StrUtil.split(executorParam, CharUtil.COMMA);
                        String s = split.get(2);
                        if (StrUtil.equals(xxlJobChangeTaskDTO.getBizId(), s)) {
                            datum.setScheduleConf(xxlJobInfo.getScheduleConf());
                            datum.setJobDesc(xxlJobInfo.getJobDesc());
                            datum.setExecutorParam(xxlJobInfo.getExecutorParam());
                            editTask(datum);
                            startTask(datum.getId());
                            edit = true;
                            break;
                        }
                    }
                    if (!edit) {
                        XxlJobResponseInfo task = createTask(xxlJobInfo);
                        if (ObjectUtil.isNotNull(task) && StrUtil.isNotBlank(task.getContent())) {
                            startTask(Long.parseLong(task.getContent()));
                        }
                    }
                } else if (xxlJobChangeTaskDTO.getOperateType() == 2 && (
                        ObjectUtil.isNull(xxlJobTaskManagerInfo) || CollUtil.isEmpty(
                                xxlJobTaskManagerInfo.getData()))) {
                    XxlJobResponseInfo task = createTask(xxlJobInfo);
                    if (ObjectUtil.isNotNull(task) && StrUtil.isNotBlank(task.getContent())) {
                        startTask(Long.parseLong(task.getContent()));
                    }
                }

                if (xxlJobChangeTaskDTO.getOperateType() == 1) {
                    XxlJobResponseInfo task = createTask(xxlJobInfo);
                    if (ObjectUtil.isNotNull(task) && StrUtil.isNotBlank(task.getContent())) {
                        startTask(Long.parseLong(task.getContent()));
                    }
                }
            }

        }
        if (xxlJobChangeTaskDTO.getOperateType() == 3) {
            if (ObjectUtil.isNotNull(xxlJobTaskManagerInfo) && CollUtil.isNotEmpty(
                    xxlJobTaskManagerInfo.getData())) {
                List<XxlJobInfo> data = xxlJobTaskManagerInfo.getData();
                for (XxlJobInfo datum : data) {
                    String executorParam = datum.getExecutorParam();
                    List<String> split = StrUtil.split(executorParam, CharUtil.COMMA);
                    String s = split.get(2);
                    if (StrUtil.equals(xxlJobChangeTaskDTO.getBizId(), s)) {
                        deleteTask(datum.getId());
                    }
                }
            }
        }
    }

    public XxlJobTaskManagerInfo getTaskManagerInfo(XxlJobChangeTaskDTO xxlJobChangeTaskDTO) {
        XxlJobGroup xxlJobGroup = new XxlJobGroup();
        xxlJobGroup.setAppname(xxlJobChangeTaskDTO.getAppName());
        xxlJobGroup.setTitle(xxlJobChangeTaskDTO.getTitle());
        List<XxlJobGroup> xxlJobGroups = selectActuator(xxlJobGroup);
        XxlJobInfo xxlJobInfo = new XxlJobInfo();
        xxlJobInfo.setJobGroup(xxlJobGroups.get(0).getId());
        xxlJobInfo.setExecutorHandler(xxlJobChangeTaskDTO.getExecutorHandler());
        xxlJobInfo.setAuthor(xxlJobChangeTaskDTO.getAuthor());
        XxlJobTaskManagerInfo xxlJobTaskManagerInfo = selectTask(xxlJobInfo);
        return xxlJobTaskManagerInfo;

    }

    //动态获取触发时间
    public String getTime(LocalDateTime nextTime) {
        String nextTimeStr = null;
        //判断是不是今天,如果是同一天的话就提前一小时提醒
        if (LocalDateTimeUtil.isSameDay(nextTime, LocalDateTime.now())) {
            LocalDateTime offset = LocalDateTimeUtil.offset(nextTime,
                    -1L,
                    ChronoUnit.HOURS);
            nextTimeStr = LocalDateTime.now().isBefore(offset) ? offset
                    .format(DateTimeFormatter.ofPattern(DatePattern.NORM_DATETIME_PATTERN)) : null;

        } else {
            SysConfigDO sysConfigDO = sysConfigService.getByConfigKey(
                    SysConfigKeyEnum.CONTACTS_REMINDER.getType());
            if (ObjectUtil.isNotNull(sysConfigDO)) {
                String value = sysConfigDO.getValue();
                if (StrUtil.isNotBlank(value)) {
                    List<String> split = StrUtil.split(value, CharUtil.COMMA);
                    Integer timeType = Integer.parseInt(split.get(0));
                    String timeValue = split.get(1);
                    String hourValue = split.get(2);
                    nextTimeStr = getNextTimeStr(timeType, timeValue, hourValue, nextTime);
                }
            }
        }
        return nextTimeStr;

    }

    private String getNextTimeStr(Integer type, String timeValue, String hourValue,
            LocalDateTime nextTime) {
        String nextTimeStr;
        LocalDateTime offset;
        switch (type) {
            case 1: {
                //按天计算
                int minute = nextTime.getMinute();
                double subMinute = NumberUtil.sub(minute, 0);
                int hour = nextTime.getHour();
                double subHour = NumberUtil.sub(hour, Integer.parseInt(hourValue));
                nextTime = nextTime.minusHours((long) subHour);
                nextTime = nextTime.minusMinutes((long) subMinute);
                offset = LocalDateTimeUtil.offset(nextTime,
                        -(Long.parseLong(timeValue)),
                        ChronoUnit.DAYS);
                nextTimeStr = LocalDateTime.now().isBefore(offset) ? offset
                        .format(DateTimeFormatter.ofPattern(DatePattern.NORM_DATETIME_PATTERN))
                        : null;
                return nextTimeStr;
            }
            case 2: {
                //按小时计算
                offset = LocalDateTimeUtil.offset(nextTime,
                        -(Long.parseLong(timeValue)),
                        ChronoUnit.HOURS);
                nextTimeStr = LocalDateTime.now().isBefore(offset) ? offset
                        .format(DateTimeFormatter.ofPattern(DatePattern.NORM_DATETIME_PATTERN))
                        : null;
                return nextTimeStr;
            }

            case 3: {
                //按分钟计算
                offset = LocalDateTimeUtil.offset(nextTime,
                        -(Long.parseLong(timeValue)),
                        ChronoUnit.MINUTES);
                nextTimeStr = LocalDateTime.now().isBefore(offset) ? offset
                        .format(DateTimeFormatter.ofPattern(DatePattern.NORM_DATETIME_PATTERN))
                        : null;
                return nextTimeStr;
            }

            case 4: {
                //按月计算
                int minute = nextTime.getMinute();
                double subMinute = NumberUtil.sub(minute, 0);
                int hour = nextTime.getHour();
                double subHour = NumberUtil.sub(hour, Integer.parseInt(hourValue));
                nextTime = nextTime.minusHours((long) subHour);
                nextTime = nextTime.minusMinutes((long) subMinute);
                offset = LocalDateTimeUtil.offset(nextTime,
                        -(Long.parseLong(timeValue)),
                        ChronoUnit.MONTHS);
                nextTimeStr = LocalDateTime.now().isBefore(offset) ? offset
                        .format(DateTimeFormatter.ofPattern(DatePattern.NORM_DATETIME_PATTERN))
                        : null;
                return nextTimeStr;
            }
            default: {
                return null;
            }
        }
    }

    /**
     * 变更xxl任务负责人
     *
     * @param xxlJobChangeTaskDTO
     */
    @Async
    public void changeTaskOwnerUser(XxlJobChangeTaskDTO xxlJobChangeTaskDTO) {
        XxlJobTaskManagerInfo taskManagerInfo = getTaskManagerInfo(xxlJobChangeTaskDTO);
        if (ObjectUtil.isNotNull(
                taskManagerInfo) && CollUtil.isNotEmpty(
                taskManagerInfo.getData())) {
            List<XxlJobInfo> data = taskManagerInfo.getData();
            for (XxlJobInfo datum : data) {
                String executorParam = datum.getExecutorParam();
                List<String> split = StrUtil.split(executorParam, CharUtil.COMMA);
                String typeId = split.get(2);
                String type = split.get(1);
                String ownerUserId = split.get(0);
                String nextTime = split.get(3);
                if (StrUtil.equals(xxlJobChangeTaskDTO.getBizId(), typeId) && !StrUtil.equals(
                        ownerUserId, xxlJobChangeTaskDTO.getOwnerUserId())) {
                    List<String> list = Arrays.asList(xxlJobChangeTaskDTO.getOwnerUserId(), type,
                            typeId, nextTime);
                    datum.setExecutorParam(CollUtil.join(list, ","));
                    editTask(datum);
                    startTask(datum.getId());
                }
            }
        }


    }
}

