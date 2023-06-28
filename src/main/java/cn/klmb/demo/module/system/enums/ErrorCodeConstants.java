package cn.klmb.demo.module.system.enums;


import cn.klmb.demo.framework.common.exception.ErrorCode;

/**
 * System 错误码枚举类 system 系统，使用 1-001-000-000 ~ 1-002-000-000 段
 *
 * @author liuyuepan
 * @date 2022/12/5
 */
public interface ErrorCodeConstants {

    // ========== 参数配置 1001000000 ==========
    ErrorCode CONFIG_NOT_EXISTS = new ErrorCode(1001000001, "参数配置不存在");
    ErrorCode CONFIG_KEY_DUPLICATE = new ErrorCode(1001000002, "参数配置 key 重复");
    ErrorCode CONFIG_CAN_NOT_DELETE_SYSTEM_TYPE = new ErrorCode(1001000003,
            "不能删除类型为系统内置的参数配置");
    ErrorCode CONFIG_GET_VALUE_ERROR_IF_INVISIBLE = new ErrorCode(1001000004,
            "获取参数配置失败，原因：不允许获取不可见配置");

    //企业信息已到期
    ErrorCode SYSTEM_NO_SUCH_PARAMENT_ERROR = new ErrorCode(1001000005, "参数不存在!");

    ErrorCode SYSTEM_NO_VALID = new ErrorCode(1001000006, "参数验证错误");


    // ========== 定时任务 1001001000 ==========
    ErrorCode JOB_NOT_EXISTS = new ErrorCode(1001001000, "定时任务不存在");
    ErrorCode JOB_HANDLER_EXISTS = new ErrorCode(1001001001, "定时任务的处理器已经存在");
    ErrorCode JOB_CHANGE_STATUS_INVALID = new ErrorCode(1001001002, "只允许修改为开启或者关闭状态");
    ErrorCode JOB_CHANGE_STATUS_EQUALS = new ErrorCode(1001001003,
            "定时任务已经处于该状态，无需修改");
    ErrorCode JOB_UPDATE_ONLY_NORMAL_STATUS = new ErrorCode(1001001004,
            "只有开启状态的任务，才可以修改");
    ErrorCode JOB_CRON_EXPRESSION_VALID = new ErrorCode(1001001005, "CRON 表达式不正确");

    ErrorCode XXL_JOB_INTERFACE_CALL_ERROR = new ErrorCode(1001001006,
            "【xxl-job】接口调用失败，错误码：【{}】，原因：【{}】");

    ErrorCode XXL_JOB_NOT_GAIN_GROUP_ID = new ErrorCode(1001001007,
            "【xxl-job】接口调用未获取到分组id，appname：【{}】");


    // ========== API 错误日志 1001002000 ==========
    ErrorCode API_ERROR_LOG_NOT_FOUND = new ErrorCode(1001002000, "API 错误日志不存在");
    ErrorCode API_ERROR_LOG_PROCESSED = new ErrorCode(1001002001, "API 错误日志已处理");

    // ========= 文件相关 1001003000=================
    ErrorCode FILE_PATH_EXISTS = new ErrorCode(1001003000, "文件路径已存在");
    ErrorCode FILE_NOT_EXISTS = new ErrorCode(1001003001, "文件不存在");
    ErrorCode FILE_IS_EMPTY = new ErrorCode(1001003002, "文件为空");
    ErrorCode SYSTEM_UPLOAD_FILE_ERROR = new ErrorCode(1001003003, "文件导入失败");


    // ========== 代码生成器 1001004000 ==========
    ErrorCode CODEGEN_TABLE_EXISTS = new ErrorCode(1003001000, "表定义已经存在");
    ErrorCode CODEGEN_IMPORT_TABLE_NULL = new ErrorCode(1003001001, "导入的表不存在");
    ErrorCode CODEGEN_IMPORT_COLUMNS_NULL = new ErrorCode(1003001002, "导入的字段不存在");
    ErrorCode CODEGEN_TABLE_NOT_EXISTS = new ErrorCode(1003001004, "表定义不存在");
    ErrorCode CODEGEN_COLUMN_NOT_EXISTS = new ErrorCode(1003001005, "字段义不存在");
    ErrorCode CODEGEN_SYNC_COLUMNS_NULL = new ErrorCode(1003001006, "同步的字段不存在");
    ErrorCode CODEGEN_SYNC_NONE_CHANGE = new ErrorCode(1003001007, "同步失败，不存在改变");
    ErrorCode CODEGEN_TABLE_INFO_TABLE_COMMENT_IS_NULL = new ErrorCode(1003001008,
            "数据库的表注释未填写");
    ErrorCode CODEGEN_TABLE_INFO_COLUMN_COMMENT_IS_NULL = new ErrorCode(1003001009,
            "数据库的表字段({})注释未填写");

    // ========== 字典类型（测试）1001005000 ==========
    ErrorCode TEST_DEMO_NOT_EXISTS = new ErrorCode(1001005000, "测试示例不存在");

    // ========== 文件配置 1001006000 ==========
    ErrorCode FILE_CONFIG_NOT_EXISTS = new ErrorCode(1001006000, "文件配置不存在");
    ErrorCode FILE_CONFIG_DELETE_FAIL_MASTER = new ErrorCode(1001006001,
            "该文件配置不允许删除，原因：它是主配置，删除会导致无法上传文件");

    // ========== 数据源配置 1001007000 ==========
    ErrorCode DATA_SOURCE_CONFIG_NOT_EXISTS = new ErrorCode(1001007000, "数据源配置不存在");
    ErrorCode DATA_SOURCE_CONFIG_NOT_OK = new ErrorCode(1001007001,
            "数据源配置不正确，无法进行连接");

    // ========== AUTH 模块 1002000000 ==========
    ErrorCode AUTH_LOGIN_BAD_CREDENTIALS = new ErrorCode(1002000000, "登录失败，账号密码不正确");
    ErrorCode AUTH_LOGIN_USER_DISABLED = new ErrorCode(1002000001, "登录失败，账号被禁用");
    ErrorCode AUTH_LOGIN_CAPTCHA_CODE_ERROR = new ErrorCode(1002000004, "验证码不正确，原因：{}");
    ErrorCode AUTH_THIRD_LOGIN_NOT_BIND = new ErrorCode(1002000005, "未绑定账号，需要进行绑定");
    ErrorCode AUTH_TOKEN_EXPIRED = new ErrorCode(1002000006, "Token 已经过期");
    ErrorCode AUTH_MOBILE_NOT_EXISTS = new ErrorCode(1002000007, "手机号不存在");

    // ========== 菜单模块 1002001000 ==========
    ErrorCode MENU_NAME_DUPLICATE = new ErrorCode(1002001000, "已经存在该名字的菜单");
    ErrorCode MENU_PARENT_NOT_EXISTS = new ErrorCode(1002001001, "父菜单不存在");
    ErrorCode MENU_PARENT_ERROR = new ErrorCode(1002001002, "不能设置自己为父菜单");
    ErrorCode MENU_NOT_EXISTS = new ErrorCode(1002001003, "菜单不存在");
    ErrorCode MENU_EXISTS_CHILDREN = new ErrorCode(1002001004, "存在子菜单，无法删除");
    ErrorCode MENU_PARENT_NOT_DIR_OR_MENU = new ErrorCode(1002001005,
            "父菜单的类型必须是目录或者菜单");

    // ========== 角色模块 1002002000 ==========
    ErrorCode ROLE_NOT_EXISTS = new ErrorCode(1002002000, "角色不存在");
    ErrorCode ROLE_NAME_DUPLICATE = new ErrorCode(1002002001, "已经存在名为【{}】的角色");
    ErrorCode ROLE_CODE_DUPLICATE = new ErrorCode(1002002002, "已经存在编码为【{}】的角色");
    ErrorCode ROLE_CAN_NOT_UPDATE_SYSTEM_TYPE_ROLE = new ErrorCode(1002002003,
            "不能操作类型为系统内置的角色");
    ErrorCode ROLE_IS_DISABLE = new ErrorCode(1002002004, "名字为【{}】的角色已被禁用");
    ErrorCode ROLE_ADMIN_CODE_ERROR = new ErrorCode(1002002005, "编码【{}】不能使用");
    ErrorCode USER_ROLE_NOT_EXISTS = new ErrorCode(1002002006, "该用户未分配角色");


    // ========== 用户模块 1002003000 ==========
    ErrorCode USER_USERNAME_EXISTS = new ErrorCode(1002003000, "用户账号【{}】已经存在");
    ErrorCode USER_MOBILE_EXISTS = new ErrorCode(1002003001, "手机号【{}】已经存在");
    ErrorCode USER_EMAIL_EXISTS = new ErrorCode(1002003002, "邮箱【{}】已经存在");
    ErrorCode USER_NOT_EXISTS = new ErrorCode(1002003003, "用户不存在");
    ErrorCode USER_IMPORT_LIST_IS_EMPTY = new ErrorCode(1002003004, "导入用户数据不能为空！");
    ErrorCode USER_PASSWORD_FAILED = new ErrorCode(1002003005, "用户密码校验失败");
    ErrorCode USER_IS_DISABLE = new ErrorCode(1002003006, "名字为【{}】的用户已被禁用");
    ErrorCode USER_COUNT_MAX = new ErrorCode(1002003008,
            "创建用户失败，原因：超过租户最大租户配额({})！");
    ErrorCode USER_IS_PARENT = new ErrorCode(1002003009,
            "该用户与其他用户存在直属关系,无法删除");

    ErrorCode USER_PARENT_IS_NOT_SELF = new ErrorCode(1002003010,
            "直属上级不能为自己");

    // ========== 部门模块 1002004000 ==========
    ErrorCode DEPT_NAME_DUPLICATE = new ErrorCode(1002004000, "已经存在该名字的部门");
    ErrorCode DEPT_PARENT_NOT_EXITS = new ErrorCode(1002004001, "父级部门不存在");
    ErrorCode DEPT_NOT_FOUND = new ErrorCode(1002004002, "当前部门不存在");
    ErrorCode DEPT_EXITS_CHILDREN = new ErrorCode(1002004003, "存在子部门，无法删除");
    ErrorCode DEPT_PARENT_ERROR = new ErrorCode(1002004004, "不能设置自己为父部门");
    ErrorCode DEPT_EXISTS_USER = new ErrorCode(1002004005, "部门中存在员工，无法删除");
    ErrorCode DEPT_NOT_ENABLE = new ErrorCode(1002004006, "部门不处于开启状态，不允许选择");
    ErrorCode DEPT_PARENT_IS_CHILD = new ErrorCode(1002004007, "不能设置自己的子部门为父部门");

    // ========== 字典类型 1002006000 ==========
    ErrorCode DICT_TYPE_NOT_EXISTS = new ErrorCode(1002006001, "当前字典类型不存在");
    ErrorCode DICT_TYPE_NOT_ENABLE = new ErrorCode(1002006002, "字典类型不处于开启状态，不允许选择");
    ErrorCode DICT_TYPE_NAME_DUPLICATE = new ErrorCode(1002006003, "已经存在该名字的字典类型");
    ErrorCode DICT_TYPE_TYPE_DUPLICATE = new ErrorCode(1002006004, "已经存在该类型的字典类型");
    ErrorCode DICT_TYPE_HAS_CHILDREN = new ErrorCode(1002006005, "无法删除，该字典类型还有字典数据");

    // ========== 字典数据 1002007000 ==========
    ErrorCode DICT_DATA_NOT_EXISTS = new ErrorCode(1002007001, "当前字典数据不存在");
    ErrorCode DICT_DATA_NOT_ENABLE = new ErrorCode(1002007002,
            "字典数据({})不处于开启状态，不允许选择");
    ErrorCode DICT_DATA_VALUE_DUPLICATE = new ErrorCode(1002007003, "已经存在该值的字典数据");

    // ========== 通知公告 1002008000 ==========
    ErrorCode NOTICE_NOT_FOUND = new ErrorCode(1002008001, "当前通知公告不存在");

    // ========== 短信渠道 1002011000 ==========
    ErrorCode SMS_CHANNEL_NOT_EXISTS = new ErrorCode(1002011000, "短信渠道不存在");
    ErrorCode SMS_CHANNEL_DISABLE = new ErrorCode(1002011001, "短信渠道不处于开启状态，不允许选择");
    ErrorCode SMS_CHANNEL_HAS_CHILDREN = new ErrorCode(1002011002,
            "无法删除，该短信渠道还有短信模板");

    // ========== 短信模板 1002012000 ==========
    ErrorCode SMS_TEMPLATE_NOT_EXISTS = new ErrorCode(1002012000, "短信模板不存在");
    ErrorCode SMS_TEMPLATE_CODE_DUPLICATE = new ErrorCode(1002012001,
            "已经存在编码为【{}】的短信模板");

    // ========== 短信发送 1002013000 ==========
    ErrorCode SMS_SEND_MOBILE_NOT_EXISTS = new ErrorCode(1002013000, "手机号不存在");
    ErrorCode SMS_SEND_MOBILE_TEMPLATE_PARAM_MISS = new ErrorCode(1002013001, "模板参数({})缺失");
    ErrorCode SMS_SEND_TEMPLATE_NOT_EXISTS = new ErrorCode(1002013002, "短信模板不存在");

    // ========== 短信验证码 1002014000 ==========
    ErrorCode SMS_CODE_NOT_FOUND = new ErrorCode(1002014000, "验证码不存在");
    ErrorCode SMS_CODE_EXPIRED = new ErrorCode(1002014001, "验证码已过期");
    ErrorCode SMS_CODE_USED = new ErrorCode(1002014002, "验证码已使用");
    ErrorCode SMS_CODE_NOT_CORRECT = new ErrorCode(1002014003, "验证码不正确");
    ErrorCode SMS_CODE_EXCEED_SEND_MAXIMUM_QUANTITY_PER_DAY = new ErrorCode(1002014004,
            "超过每日短信发送数量");
    ErrorCode SMS_CODE_SEND_TOO_FAST = new ErrorCode(1002014005, "短信发送过于频率");
    ErrorCode SMS_CODE_IS_EXISTS = new ErrorCode(1002014006, "手机号已被使用");
    ErrorCode SMS_CODE_IS_UNUSED = new ErrorCode(1002014007, "验证码未被使用");

    // ========== 租户信息 1002015000 ==========
    ErrorCode TENANT_NOT_EXISTS = new ErrorCode(1002015000, "租户不存在");
    ErrorCode TENANT_DISABLE = new ErrorCode(1002015001, "名字为【{}】的租户已被禁用");
    ErrorCode TENANT_EXPIRE = new ErrorCode(1002015002, "名字为【{}】的租户已过期");
    ErrorCode TENANT_CAN_NOT_UPDATE_SYSTEM = new ErrorCode(1002015003,
            "系统租户不能进行修改、删除等操作！");

    // ========== 租户套餐 1002016000 ==========
    ErrorCode TENANT_PACKAGE_NOT_EXISTS = new ErrorCode(1002016000, "租户套餐不存在");
    ErrorCode TENANT_PACKAGE_USED = new ErrorCode(1002016001,
            "租户正在使用该套餐，请给租户重新设置套餐后再尝试删除");
    ErrorCode TENANT_PACKAGE_DISABLE = new ErrorCode(1002016002, "名字为【{}】的租户套餐已被禁用");

    // ========== 错误码模块 1002017000 ==========
    ErrorCode ERROR_CODE_NOT_EXISTS = new ErrorCode(1002017000, "错误码不存在");
    ErrorCode ERROR_CODE_DUPLICATE = new ErrorCode(1002017001, "已经存在编码为【{}】的错误码");

    // ========== 社交用户 1002018000 ==========
    ErrorCode SOCIAL_USER_AUTH_FAILURE = new ErrorCode(1002018000, "社交授权失败，原因是：{}");
    ErrorCode SOCIAL_USER_UNBIND_NOT_SELF = new ErrorCode(1002018001,
            "社交解绑失败，非当前用户绑定");
    ErrorCode SOCIAL_USER_NOT_FOUND = new ErrorCode(1002018002, "社交授权失败，找不到对应的用户");

    // ========== 系统敏感词 1002019000 =========
    ErrorCode SENSITIVE_WORD_NOT_EXISTS = new ErrorCode(1002019000,
            "系统敏感词在所有标签中都不存在");
    ErrorCode SENSITIVE_WORD_EXISTS = new ErrorCode(1002019001, "系统敏感词已在标签中存在");

    // ========== OAuth2 客户端 1002020000 =========
    ErrorCode OAUTH2_CLIENT_NOT_EXISTS = new ErrorCode(1002020000, "OAuth2 客户端不存在");
    ErrorCode OAUTH2_CLIENT_EXISTS = new ErrorCode(1002020001, "OAuth2 客户端编号已存在");
    ErrorCode OAUTH2_CLIENT_DISABLE = new ErrorCode(1002020002, "OAuth2 客户端已禁用");
    ErrorCode OAUTH2_CLIENT_AUTHORIZED_GRANT_TYPE_NOT_EXISTS = new ErrorCode(1002020003,
            "不支持该授权类型");
    ErrorCode OAUTH2_CLIENT_SCOPE_OVER = new ErrorCode(1002020004, "授权范围过大");
    ErrorCode OAUTH2_CLIENT_REDIRECT_URI_NOT_MATCH = new ErrorCode(1002020005,
            "无效 redirect_uri: {}");
    ErrorCode OAUTH2_CLIENT_CLIENT_SECRET_ERROR = new ErrorCode(1002020006,
            "无效 client_secret: {}");

    // ========== OAuth2 授权 1002021000 =========
    ErrorCode OAUTH2_GRANT_CLIENT_ID_MISMATCH = new ErrorCode(1002021000, "client_id 不匹配");
    ErrorCode OAUTH2_GRANT_REDIRECT_URI_MISMATCH = new ErrorCode(1002021001, "redirect_uri 不匹配");
    ErrorCode OAUTH2_GRANT_STATE_MISMATCH = new ErrorCode(1002021002, "state 不匹配");
    ErrorCode OAUTH2_GRANT_CODE_NOT_EXISTS = new ErrorCode(1002021003, "code 不存在");

    // ========== OAuth2 授权 1002022000 =========
    ErrorCode OAUTH2_CODE_NOT_EXISTS = new ErrorCode(1002022000, "code 不存在");
    ErrorCode OAUTH2_CODE_EXPIRE = new ErrorCode(1002022000, "code 已过期");

    ErrorCode NOTIFY_TEMPLATE_NOT_EXISTS = new ErrorCode(1002026000, "站内信模版不存在");
    ErrorCode NOTIFY_TEMPLATE_CODE_DUPLICATE = new ErrorCode(1002026001, "已经存在编码为【{}】的站内信模板");
    ErrorCode NOTIFY_SEND_TEMPLATE_PARAM_MISS = new ErrorCode(1002025000, "模板参数({})缺失");

    // ========== 飞书1002023000 =========
    ErrorCode TENANT_ACCESS_TOKEN_ERROR = new ErrorCode(1002026000,
            "飞书自建应用获取tenant_access_token失败");
    ErrorCode MIN_APP_TOKEN_LOGIN_VALIDATE = new ErrorCode(1002026001, "飞书小程序自建应用的登录获取用户身份失败");
    ErrorCode WEB_AUTHEN_ACCESS_TOKEN = new ErrorCode(1002026002, "飞书网页登录获取用户身份失败");

    ErrorCode FEISHU_USER_NOT_EXISTS = new ErrorCode(1002026003, "飞书用户不存在!");

    ErrorCode FEISHU_BOT_SEND_MSG_ERROR = new ErrorCode(1002026004, "飞书机器人发送消息失败,错误消息为【{}】!");


}
