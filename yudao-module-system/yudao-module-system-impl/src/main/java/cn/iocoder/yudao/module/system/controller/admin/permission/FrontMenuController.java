package cn.iocoder.yudao.module.system.controller.admin.permission;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.system.controller.admin.permission.vo.menu.FrontMenuCreateReqVO;
import cn.iocoder.yudao.module.system.controller.admin.permission.vo.menu.FrontMenuPageReqVO;
import cn.iocoder.yudao.module.system.controller.admin.permission.vo.menu.FrontMenuRespVO;
import cn.iocoder.yudao.module.system.controller.admin.permission.vo.menu.FrontMenuUpdateReqVO;
import cn.iocoder.yudao.module.system.convert.permission.FrontMenuConvert;
import cn.iocoder.yudao.module.system.dal.dataobject.permission.FrontMenuDO;
import cn.iocoder.yudao.module.system.service.permission.FrontMenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.Collection;
import java.util.List;

import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

@Api(tags = "前台菜单")
@RestController
@RequestMapping("/system/front/menu")
@Validated
public class FrontMenuController {

    @Resource
    private FrontMenuService frontMenuService;

    @PostMapping("/create")
    @ApiOperation("创建前台菜单")
    public CommonResult<Long> createFrontMenu(@Valid @RequestBody FrontMenuCreateReqVO createReqVO) {
        return success(frontMenuService.createFrontMenu(createReqVO));
    }

    @PutMapping("/update")
    @ApiOperation("更新前台菜单")
    public CommonResult<Boolean> updateFrontMenu(@Valid @RequestBody FrontMenuUpdateReqVO updateReqVO) {
        frontMenuService.updateFrontMenu(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @ApiOperation("删除前台菜单")
    @ApiImplicitParam(name = "id", value = "编号", required = true, dataTypeClass = Long.class)
    public CommonResult<Boolean> deleteFrontMenu(@RequestParam("id") Long id) {
        frontMenuService.deleteFrontMenu(id);
        return success(true);
    }

    @GetMapping("/get")
    @ApiOperation("获得前台菜单")
    @ApiImplicitParam(name = "id", value = "编号", required = true, example = "1024", dataTypeClass = Long.class)
    public CommonResult<FrontMenuRespVO> getFrontMenu(@RequestParam("id") Long id) {
        FrontMenuDO frontMenu = frontMenuService.getFrontMenu(id);
        return success(FrontMenuConvert.INSTANCE.convert(frontMenu));
    }

    @GetMapping("/list")
    @ApiOperation("获得前台菜单列表")
    @ApiImplicitParam(name = "ids", value = "编号列表", required = true, example = "1024,2048", dataTypeClass = List.class)
    public CommonResult<List<FrontMenuRespVO>> getFrontMenuList(@RequestParam("ids") Collection<Long> ids) {
        List<FrontMenuDO> list = frontMenuService.getFrontMenuList(ids);
        return success(FrontMenuConvert.INSTANCE.convertList(list));
    }

    @GetMapping("/page")
    @ApiOperation("获得前台菜单分页")
    public CommonResult<PageResult<FrontMenuRespVO>> getFrontMenuPage(@Valid FrontMenuPageReqVO pageVO) {
        PageResult<FrontMenuDO> pageResult = frontMenuService.getFrontMenuPage(pageVO);
        return success(FrontMenuConvert.INSTANCE.convertPage(pageResult));
    }

//    @GetMapping("/export-excel")
//    @ApiOperation("导出前台菜单 Excel")
//    @OperateLog(type = EXPORT)
//    public void exportFrontMenuExcel(@Valid FrontMenuExportReqVO exportReqVO,
//              HttpServletResponse response) throws IOException {
//        List<FrontMenuDO> list = frontMenuService.getFrontMenuList(exportReqVO);
//        // 导出 Excel
//        List<FrontMenuExcelVO> datas = FrontMenuConvert.INSTANCE.convertList02(list);
//        ExcelUtils.write(response, "前台菜单.xls", "数据", FrontMenuExcelVO.class, datas);
//    }

}
