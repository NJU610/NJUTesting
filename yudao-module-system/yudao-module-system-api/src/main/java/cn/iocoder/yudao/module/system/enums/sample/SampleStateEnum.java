package cn.iocoder.yudao.module.system.enums.sample;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum SampleStateEnum {

    UNSENT(10, "未发送"),
    SENT(20, "已发送"),
    VERIFIED(30, "已审核"),
    MODIFYING(40, "待修改"),
    MODIFIED(50, "已修改"),
    PROCESSED(60, "已处理"),
    ;

    private final Integer state;

    private final String desc;

}
