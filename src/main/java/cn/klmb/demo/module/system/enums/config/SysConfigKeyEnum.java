package cn.klmb.demo.module.system.enums.config;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 参数键名
 */
@Getter
@AllArgsConstructor
public enum SysConfigKeyEnum {

    /**
     * 系统配置
     */
    CONTACTS_REMINDER("sys.customer.expiration.reminder"),
    CONTRACT_REMINDER("sys.customer.expiration.reminder");
    private final String type;
}
