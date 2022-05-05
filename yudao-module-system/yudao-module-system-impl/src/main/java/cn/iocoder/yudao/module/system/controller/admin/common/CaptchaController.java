package cn.iocoder.yudao.module.system.controller.admin.common;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.module.system.controller.admin.common.vo.CaptchaImageRespVO;
import cn.iocoder.yudao.module.system.service.common.CaptchaService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

@Api(tags = "验证码")
@RestController
@RequestMapping("/system/captcha")
public class CaptchaController {

    @Resource
    private CaptchaService captchaService;

    @GetMapping("/get-image")
    @ApiOperation(value = "生成图片验证码", notes = "生成图片验证码，直接调用返回图片的 base64 编码与uuid，使用验证码时传入 uuid 即可")
    public CommonResult<CaptchaImageRespVO> getCaptchaImage() {
        return success(captchaService.getCaptchaImage());
    }

}
