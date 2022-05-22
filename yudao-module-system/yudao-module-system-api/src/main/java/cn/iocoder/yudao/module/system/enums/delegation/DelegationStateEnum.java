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
    //CLIENT_SENDING_SAMPLE(240, "客户发送样品中"),
    CLIENT_UPLOAD_SAMPLE_INFO(250, "用户上传样品中"),
    CHECKING_SAMPLE(260, "测试部/市场部验收样品中"),
    //SAMPLE_CHECK_FAIL_RESENDING_SAMPLE(270, "样品验收不通过，用户重新发送样品中"),
    SAMPLE_CHECK_FAIL_MODIFY_SAMPLE(280, "样品验收不通过，用户重新修改"),
    SAMPLE_CHECK_SUCCESS(290, "样品验收通过"),
    TESTING_DEPT_WRITING_TEST_SOLUTION(300, "测试部编写测试方案中"),
    QUALITY_DEPT_AUDIT_TEST_SOLUTION(310, "质量部审核测试方案中"),
    QUALITY_DEPT_AUDIT_TEST_SOLUTION_FAIL(320, "测试方案审核未通过，测试部修改测试方案中"),
    QUALITY_DEPT_AUDIT_TEST_SOLUTION_SUCCESS(330, "测试方案审核通过"),
    TESTING_DEPT_WRITING_TEST_REPORT(340, "测试部测试进行中，填写测试文档"),
    TESTING_DEPT_GENERATE_TEST_REPORT(350, "测试部测试完成，生成测试报告"),
    TESTING_DEPT_MANAGER_AUDIT_TEST_REPORT(360, "测试部主管审核测试报告中"),
    TESTING_DEPT_MANAGER_AUDIT_TEST_REPORT_FAIL(370, "测试部主管测试报告审核未通过，测试部修改测试文档中"),
    TESTING_DEPT_MANAGER_AUDIT_TEST_REPORT_SUCCESS(380, "测试部主管测试报告审核通过，用户审核中"),
    CLIENT_AUDIT_TEST_REPORT_FAIL(390, "用户审核测试报告未通过，测试部修改测试文档中"),
    CLIENT_AUDIT_TEST_REPORT_SUCCESS(400, "用户审核测试报告通过，授权签字人审核测试报告中"),
    SIGNATORY_AUDIT_TEST_REPORT_FAIL(410, "授权签字人测试报告审核未通过，测试部修改测试文档中"),
    SIGNATORY_AUDIT_TEST_REPORT_SUCCESS(420, "授权签字人测试报告审核通过"),
    TESTING_DEPT_ARCHIVE_TEST_REPORT_AND_PROCESS_SAMPLE(430, "测试部测试文档归档，处理样品中"),
    MARKETING_DEPT_SEND_TEST_REPORT(440, "市场部发送测试报告中"),
    WAIT_FOR_CLIENT_RECEIVE_TEST_REPORT(450, "等待客户接收测试报告中"),
    CLIENT_CONFIRM_RECEIVE_TEST_REPORT(460, "客户确认接收测试报告"),
    CLIENT_CANCEL_DELEGATION(470,"客户取消委托"),
    ADMIN_CANCEL_DELEGATION(480,"管理员取消委托"),


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
