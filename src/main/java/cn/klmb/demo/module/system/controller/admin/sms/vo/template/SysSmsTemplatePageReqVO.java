package cn.klmb.demo.module.system.controller.admin.sms.vo.template;


import cn.klmb.demo.framework.common.pojo.PageParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 系统管理 - 短信模板 - 分页查询
 *
 * @author liuyuepan
 * @date 2022/12/8
 */
@ApiModel(description = "系统管理 - 短信模板 - 分页查询")
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
public class SysSmsTemplatePageReqVO extends PageParam {

    @ApiModelProperty(value = "短信签名", example = "1")
    private Integer type;

    @ApiModelProperty(value = "开启状态", example = "1")
    private Integer status;

    @ApiModelProperty(value = "模板编码", example = "test_01", notes = "模糊匹配")
    private String code;

    @ApiModelProperty(value = "模板内容", example = "你好，{name}。你长的太{like}啦！", notes = "模糊匹配")
    private String content;

    @ApiModelProperty(value = "短信 API 的模板编号", example = "4383920", notes = "模糊匹配")
    private String apiTemplateId;

    @ApiModelProperty(value = "短信渠道编号", example = "uuid")
    private String channelId;

//    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
//    @ApiModelProperty(value = "创建时间")
//    private LocalDateTime[] createTime;

}
