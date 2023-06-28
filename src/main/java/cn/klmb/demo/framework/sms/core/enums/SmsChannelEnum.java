package cn.klmb.demo.framework.sms.core.enums;

import cn.hutool.core.util.ArrayUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 短信渠道枚举
 *
 * @author 快乐萌宝
 * @since 2021/1/25 10:56
 */
@Getter
@AllArgsConstructor
public enum SmsChannelEnum {

    ALIYUN("ALIYUN", "阿里云"),
    ;

    /**
     * 编码
     */
    private final String code;
    /**
     * 名字
     */
    private final String name;

    public static SmsChannelEnum getByCode(String code) {
        return ArrayUtil.firstMatch(o -> o.getCode().equals(code), values());
    }

}
