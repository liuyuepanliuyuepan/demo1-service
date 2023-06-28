package cn.klmb.demo.module.system.controller.admin.file.vo.config;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Map;
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 文件配置 - 新增
 *
 * @author liuyuepan
 * @date 2022/12/6
 */
@ApiModel(description = "文件配置 - 新增")
@EqualsAndHashCode(callSuper = true)
@Data
public class SysFileConfigSaveReqVO extends SysFileConfigBaseVO {

    @ApiModelProperty(value = "存储器", required = true, example = "1", notes = "参见 FileStorageEnum 枚举类")
    @NotNull(message = "存储器不能为空")
    private Integer storage;

    @ApiModelProperty(value = "存储配置", required = true, notes = "配置是动态参数，所以使用 Map 接收")
    @NotNull(message = "存储配置不能为空")
    private Map<String, Object> config;

}
