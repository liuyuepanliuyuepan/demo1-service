package cn.klmb.demo.module.system.controller.admin.logger.vo.loginlog;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

/**
 * 登录日志创建 Request DTO
 *
 * @author 快乐萌宝
 */
@Builder
@Data
public class SysLoginLogCreateReqDTO {

    /**
     * 日志类型
     */
    @NotNull(message = "日志类型不能为空")
    private Integer logType;
    /**
     * 链路追踪编号
     */
    private String traceId;

    /**
     * 用户编号
     */
    private String userId;
    /**
     * 用户类型
     */
    @NotNull(message = "用户类型不能为空")
    private Integer userType;
    /**
     * 用户账号
     */
    @NotBlank(message = "用户账号不能为空")
    @Size(max = 30, message = "用户账号长度不能超过30个字符")
    private String username;

    /**
     * 登录结果
     */
    @NotNull(message = "登录结果不能为空")
    private Integer result;

    /**
     * 用户 IP
     */
    @NotEmpty(message = "用户 IP 不能为空")
    private String userIp;
    /**
     * 浏览器 UserAgent
     * <p>
     * 允许空，原因：Job 过期登出时，是无法传递 UserAgent 的
     */
    private String userAgent;

}
