package cn.klmb.demo.module.system.entity.codegen;

import cn.klmb.demo.framework.base.core.entity.KlmbBaseDO;
import cn.klmb.demo.module.system.enums.codegen.CodegenSceneEnum;
import cn.klmb.demo.module.system.enums.codegen.CodegenTemplateTypeEnum;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * 代码生成 table 表定义
 *
 * @author liuyuepan
 * @date 2022/12/28
 */
@TableName("codegen_table")
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@SuperBuilder
@Data
public class CodegenTableDO extends KlmbBaseDO {

    /**
     * 生成场景
     *
     * 枚举 {@link CodegenSceneEnum}
     */
    private Integer scene;

    // ========== 表相关字段 ==========

    /**
     * 表名称
     */
    private String tableName;
    /**
     * 表描述
     */
    private String tableComment;
    /**
     * 备注
     */
    private String remark;

    // ========== 类相关字段 ==========

    /**
     * 模块名，即一级目录
     *
     * 例如说，system、infra、tool 等等
     */
    private String moduleName;
    /**
     * 业务名，即二级目录
     *
     * 例如说，user、permission、dict 等等
     */
    private String businessName;
    /**
     * 类名称（首字母大写）
     *
     * 例如说，SysUser、SysMenu、SysDictData 等等
     */
    private String className;
    /**
     * 类描述
     */
    private String classComment;
    /**
     * 作者
     */
    private String author;

    // ========== 生成相关字段 ==========

    /**
     * 模板类型
     *
     * 枚举 {@link CodegenTemplateTypeEnum}
     */
    private Integer templateType;

    // ========== 菜单相关字段 ==========

    /**
     * 父菜单编号
     *
     * 关联 MenuDO 的 id 属性
     */
    private Long parentMenuId;

}
