package cn.klmb.demo.module.system.enums.sms;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 短信的模板类型枚举
 *
 * @author 快乐萌宝
 */
@Getter
@AllArgsConstructor
public enum SysSmsTemplateTypeEnum {

    VERIFICATION_CODE(1), // 验证码
    NOTICE(2), // 通知
    PROMOTION(3), // 营销
    ;

    /**
     * 类型
     */
    private final int type;

}
