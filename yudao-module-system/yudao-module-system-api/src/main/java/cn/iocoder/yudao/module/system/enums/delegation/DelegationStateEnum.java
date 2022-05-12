package cn.iocoder.yudao.module.system.enums.delegation;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum DelegationStateEnum {

    DELEGATE_WRITING(10, "委托填写中"),
    WAIT_MARKETING_DEPARTMENT_ASSIGN_STAFF(20, "等待市场部主管分配市场部人员"),
    WAIT_TESTING_DEPARTMENT_ASSIGN_STAFF(30, "等待测试部主管分配测试部人员"),
    MARKETING_DEPARTMENT_AUDIT_DELEGATION(40, "市场部审核委托中"),
    TESTING_DEPARTMENT_AUDIT_DELEGATION(50, "测试部审核委托中"),
    MARKETING_DEPARTMENT_AUDIT_DELEGATION_FAIL(60, "市场部审核委托不通过，委托修改中"),
    TESTING_DEPARTMENT_AUDIT_DELEGATION_FAIL(70, "测试部审核委托不通过，委托修改中"),
    MARKETING_DEPARTMENT_AUDIT_DELEGATION_SUCCESS(80, "市场部审核委托通过"),
    TESTING_DEPARTMENT_AUDIT_DELEGATION_SUCCESS(90, "测试部审核委托通过"),
    AUDIT_DELEGATION_SUCCESS(100, "委托审核通过"),
    MARKETING_DEPARTMENT_GENERATE_OFFER(110, "市场部生成报价中"),
    CLIENT_DEALING_OFFER(120, "客户处理报价中"),
    CLIENT_REJECT_OFFER(130, "客户不接受报价，市场部修改报价"),
    CLIENT_ACCEPT_OFFER(140, "客户接受报价"),
    MARKETING_DEPARTMENT_GENERATE_CONTRACT(150, "市场部生成合同草稿中"),
    CLIENT_AUDIT_CONTRACT(160, "客户检查合同草稿中"),
    CLIENT_WRITING_CONTRACT(170, "客户接受市场部合同草稿，填写合同中"),
    MARKETING_DEPARTMENT_AUDIT_CONTRACT(180, "市场部审核客户填写的草稿中"),
    MARKETING_DEPARTMENT_AUDIT_CONTRACT_FAIL(190, "市场部审核合同不通过，客户修改中"),
    CLIENT_AUDIT_CONTRACT_FAIL(200, "客户不接受市场部合同草稿，市场部修改合同草稿"),
    MARKETING_DEPARTMENT_AUDIT_CONTRACT_SUCCESS(210, "市场部审核合同通过"),
    CONTRACT_SIGNING(220, "合同签署中"),
    CONTRACT_SIGN_SUCCESS(230, "合同签署成功"),
    CLIENT_SENDING_SAMPLE(240, "客户发送样品中"),
    CLIENT_UPLOAD_SAMPLE_INFO(250, "客户上传样品信息"),
    CHECKING_SAMPLE(260, "测试部/市场部验收样品中"),
    SAMPLE_CHECK_FAIL_RESENDING_SAMPLE(270, "样品验收不通过，用户重新发送样品中"),
    SAMPLE_CHECK_FAIL_MODIFY_SAMPLE_INFO(280, "样品验收不通过，用户修改样品信息中"),
    SAMPLE_CHECK_SUCCESS(290, "样品验收通过"),
    TESTING_DEPT_WRITING_TEST_SOLUTION(300, "测试部编写测试方案中"),

    ;

    private final Integer state;

    private final String desc;

    public static DelegationStateEnum getByState(Integer state) {
        for (DelegationStateEnum delegationStateEnum : values()) {
            if (delegationStateEnum.getState().equals(state)) {
                return delegationStateEnum;
            }
        }

        return null;
    }

}
