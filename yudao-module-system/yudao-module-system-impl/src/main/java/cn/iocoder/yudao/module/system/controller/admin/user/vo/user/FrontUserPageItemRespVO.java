package cn.iocoder.yudao.module.system.controller.admin.user.vo.user;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@ApiModel(value = "用户分页时的信息 Response VO", description = "")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class FrontUserPageItemRespVO extends FrontUserRespVO {

    /**
     * 所在部门
     */
//    private Dept dept;
//
//    @ApiModel("部门")
//    @Data
//    public static class Dept {
//
//        @ApiModelProperty(value = "部门编号", required = true, example = "1")
//        private Long id;
//
//        @ApiModelProperty(value = "部门名称", required = true, example = "研发部")
//        private String name;
//
//    }

}
