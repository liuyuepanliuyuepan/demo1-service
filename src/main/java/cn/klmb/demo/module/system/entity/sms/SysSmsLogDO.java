package cn.klmb.demo.module.system.entity.sms;

import cn.klmb.demo.framework.base.core.entity.KlmbBaseDO;
import cn.klmb.demo.framework.common.enums.UserTypeEnum;
import cn.klmb.demo.framework.sms.core.enums.SmsFrameworkErrorCodeConstants;
import cn.klmb.demo.module.system.enums.sms.SysSmsReceiveStatusEnum;
import cn.klmb.demo.module.system.enums.sms.SysSmsSendStatusEnum;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import java.time.LocalDateTime;
import java.util.Map;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * 系统管理-短信日志
 *
 * @author liuyuepan
 * @date 2022/12/8
 */
@TableName(value = "sys_sms_log", autoResultMap = true)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@SuperBuilder
@Data
public class SysSmsLogDO extends KlmbBaseDO {

    // ========= 渠道相关字段 =========

    /**
     * 短信渠道编号
     * <p>
     * 关联 {@link SysSmsChannelDO#getId()}
     */
    private String channelId;
    /**
     * 短信渠道编码
     * <p>
     * 冗余 {@link SysSmsChannelDO#getCode()}
     */
    private String channelCode;

    // ========= 模板相关字段 =========

    /**
     * 模板编号
     * <p>
     * 关联 {@link SysSmsTemplateDO#getId()}
     */
    private String templateId;
    /**
     * 模板编码
     * <p>
     * 冗余 {@link SysSmsTemplateDO#getCode()}
     */
    private String templateCode;
    /**
     * 短信类型
     * <p>
     * 冗余 {@link SysSmsTemplateDO#getType()}
     */
    private Integer templateType;
    /**
     * 基于 {@link SysSmsTemplateDO#getContent()} 格式化后的内容
     */
    private String templateContent;
    /**
     * 基于 {@link SysSmsTemplateDO#getParams()} 输入后的参数
     */
    @TableField(typeHandler = JacksonTypeHandler.class)
    private Map<String, Object> templateParams;
    /**
     * 短信 API 的模板编号
     * <p>
     * 冗余 {@link SysSmsTemplateDO#getApiTemplateId()}
     */
    private String apiTemplateId;

    // ========= 手机相关字段 =========

    /**
     * 手机号
     */
    private String mobile;
    /**
     * 用户编号
     */
    private String userId;
    /**
     * 用户类型
     * <p>
     * 枚举 {@link UserTypeEnum}
     */
    private Integer userType;

    // ========= 发送相关字段 =========

    /**
     * 发送状态
     * <p>
     * 枚举 {@link SysSmsSendStatusEnum}
     */
    private Integer sendStatus;
    /**
     * 发送时间
     */
    private LocalDateTime sendTime;
    /**
     * 发送结果的编码
     * <p>
     * 枚举 {@link SmsFrameworkErrorCodeConstants}
     */
    private Integer sendCode;
    /**
     * 发送结果的提示
     * <p>
     * 一般情况下，使用 {@link SmsFrameworkErrorCodeConstants} 异常情况下，通过格式化 Exception 的提示存储
     */
    private String sendMsg;
    /**
     * 短信 API 发送结果的编码
     * <p>
     * 由于第三方的错误码可能是字符串，所以使用 String 类型
     */
    private String apiSendCode;
    /**
     * 短信 API 发送失败的提示
     */
    private String apiSendMsg;
    /**
     * 短信 API 发送返回的唯一请求 ID
     * <p>
     * 用于和短信 API 进行定位于排错
     */
    private String apiRequestId;
    /**
     * 短信 API 发送返回的序号
     * <p>
     * 用于和短信 API 平台的发送记录关联
     */
    private String apiSerialNo;

    // ========= 接收相关字段 =========

    /**
     * 接收状态
     * <p>
     * 枚举 {@link SysSmsReceiveStatusEnum}
     */
    private Integer receiveStatus;
    /**
     * 接收时间
     */
    private LocalDateTime receiveTime;
    /**
     * 短信 API 接收结果的编码
     */
    private String apiReceiveCode;
    /**
     * 短信 API 接收结果的提示
     */
    private String apiReceiveMsg;

}
