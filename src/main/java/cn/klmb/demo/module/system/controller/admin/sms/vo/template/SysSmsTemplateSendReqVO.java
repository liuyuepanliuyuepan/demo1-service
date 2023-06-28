package cn.klmb.demo.module.system.controller.admin.sms.vo.template;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Map;
import javax.validation.constraints.NotNull;
import lombok.Data;

/**
 * 短信模板的发送
 *
 * @author liuyuepan
 * @date 2022/12/8
 */
@ApiModel("管理后台 - 短信模板的发送 Request VO")
@Data
public class SysSmsTemplateSendReqVO {

    @ApiModelProperty(value = "手机号", required = true, example = "15600000000")
    @NotNull(message = "手机号不能为空")
    private String mobile;

    @ApiModelProperty(value = "模板编码", required = true, example = "test_01")
    @NotNull(message = "模板编码不能为空")
    private String templateCode;

    @ApiModelProperty(value = "模板参数")
    private Map<String, Object> templateParams;

}
