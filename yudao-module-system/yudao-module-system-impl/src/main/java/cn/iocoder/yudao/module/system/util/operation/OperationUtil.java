package cn.iocoder.yudao.module.system.util.operation;

import cn.hutool.core.lang.Pair;
import cn.iocoder.yudao.module.system.dal.dataobject.flow.FlowDO;
import cn.iocoder.yudao.module.system.enums.flow.FlowStateEnum;
import cn.iocoder.yudao.module.system.service.flow.FlowService;

import javax.annotation.Resource;
import java.util.HashMap;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.system.enums.ErrorCodeConstants.FLOW_STATE_ERROR;

public class OperationUtil {


    private final static HashMap<Pair<FlowStateEnum, FlowStateEnum>, String> normalFlowStateMap = new HashMap<>();
    private final static HashMap<FlowStateEnum, String> exceptionFlowStateMap = new HashMap<>();

    public static String getTemplate(FlowStateEnum fromState, FlowStateEnum toState) {
        if (normalFlowStateMap.containsKey(Pair.of(fromState, toState))) {
            return normalFlowStateMap.get(Pair.of(fromState, toState));
        } else if (exceptionFlowStateMap.containsKey(toState)) {
            return exceptionFlowStateMap.get(toState);
        } else {
            throw exception(FLOW_STATE_ERROR);
        }
    }



    //    DELEGATE_WRITING(0, "委托填写中"),
    //    MARKET_DEPT_AUDIT_DELEGATE(10, "市场部审核委托中"),
    //    MARKET_DEPT_AUDIT_DELEGATE_FAIL(20, "市场部审核委托不通过，重新填写委托"),
    //    MARKET_DEPT_AUDIT_DELEGATE_SUCCESS(30, "市场部审核委托通过"),
    //    MARKET_DEPT_GENERATE_CONTRACT(40, "市场部生成合同中"),
    //    CUSTOMER_WRITE_CONTRACT(41, "客户填写合同中"),
    //    MARKET_DEPT_AUDIT_CONTRACT(50, "市场部审核合同中"),
    //    MARKET_DEPT_AUDIT_CONTRACT_FAIL(60, "市场部审核合同不通过，重新填写合同"),
    //    MARKET_DEPT_AUDIT_CONTRACT_SUCCESS(70, "市场部审核合同通过"),
    //    TEST_DEPT_GENERATE_TEST_SCHEME(80, "测试部生成测试方案中"),
    //    QUALITY_DEPT_AUDIT_TEST_SCHEME(90, "质量部审核测试方案中"),
    //    QUALITY_DEPT_AUDIT_TEST_SCHEME_FAIL(100, "质量部审核不通过，重新生成测试方案"),
    //    QUALITY_DEPT_AUDIT_TEST_SCHEME_SUCCESS(110, "质量部审核通过"),
    //    TEST_DEPT_TEST(120, "测试部测试中"),
    //    TEST_DEPT_TEST_SUCCESS(130, "测试部完成测试"),
    //    QUALITY_DEPT_AUDIT_TEST_REPORT(140, "质量部审核测试报告"),
    //    QUALITY_DEPT_AUDIT_TEST_REPORT_FAIL(150, "质量部测试报告审核不通过，重新生成测试报告"),
    //    QUALITY_DEPT_AUDIT_TEST_REPORT_SUCCESS(160, "质量部测试报告审核通过"),
    //    TEST_SUCCESS(170, "测试完成"),
    //    DELEGATE_WRITING_CANCEL(180, "委托填写时取消委托"),
    //    CONTRACT_WRITING_CANCEL(190, "合同填写时取消合同"),
    //    CUSTOMER_BREAK_CONTRACT(200, "客户违约");
    static {
        // null -> 委托填写中
        normalFlowStateMap.put(Pair.of(null, FlowStateEnum.DELEGATE_WRITING), "客户：${delegationCreatorName} 创建了委托");
        // 委托填写中 -> 市场部审核委托中
        normalFlowStateMap.put(Pair.of(FlowStateEnum.DELEGATE_WRITING, FlowStateEnum.MARKET_DEPT_AUDIT_DELEGATE), "客户：${delegationCreatorName} 提交了委托，等待市场部审核");
        // 市场部审核委托中 -> 市场部审核委托不通过，重新填写委托
        normalFlowStateMap.put(Pair.of(FlowStateEnum.MARKET_DEPT_AUDIT_DELEGATE, FlowStateEnum.MARKET_DEPT_AUDIT_DELEGATE_FAIL), "市场部：${delegationAcceptorName} 审核了委托，审核不通过，重新填写委托，原因：${delegationRemark}");
        // 市场部审核委托不通过，重新填写委托 -> 市场部审核委托中
        normalFlowStateMap.put(Pair.of(FlowStateEnum.MARKET_DEPT_AUDIT_DELEGATE_FAIL, FlowStateEnum.MARKET_DEPT_AUDIT_DELEGATE), "市场部：${delegationCreatorName} 重新提交了委托，等待市场部审核");
        // 市场部审核委托中 -> 市场部审核委托通过
        normalFlowStateMap.put(Pair.of(FlowStateEnum.MARKET_DEPT_AUDIT_DELEGATE, FlowStateEnum.MARKET_DEPT_AUDIT_DELEGATE_SUCCESS), "市场部：${delegationAcceptorName} 审核了委托，审核通过");
        // 市场部审核委托通过 -> 市场部生成合同中
        normalFlowStateMap.put(Pair.of(FlowStateEnum.MARKET_DEPT_AUDIT_DELEGATE_SUCCESS, FlowStateEnum.MARKET_DEPT_GENERATE_CONTRACT), "市场部生成合同中");
        // 市场部生成合同中 -> 客户填写合同中
        normalFlowStateMap.put(Pair.of(FlowStateEnum.MARKET_DEPT_GENERATE_CONTRACT, FlowStateEnum.CUSTOMER_WRITE_CONTRACT), "市场部：${contractCreatorName} 生成了合同，等待用户填写合同");
        // 客户填写合同中 -> 市场部审核合同中
        normalFlowStateMap.put(Pair.of(FlowStateEnum.CUSTOMER_WRITE_CONTRACT, FlowStateEnum.MARKET_DEPT_AUDIT_CONTRACT), "客户：${delegationCreatorName} 提交了合同，等待市场部审核");
        // 市场部审核合同中 -> 市场部审核合同不通过，重新填写合同
        normalFlowStateMap.put(Pair.of(FlowStateEnum.MARKET_DEPT_AUDIT_CONTRACT, FlowStateEnum.MARKET_DEPT_AUDIT_CONTRACT_FAIL), "市场部：${contractAcceptorName} 审核了合同，审核不通过，重新填写合同，原因：${contractRemark}");
        // 市场部审核合同不通过，重新填写合同 -> 市场部审核合同中
        normalFlowStateMap.put(Pair.of(FlowStateEnum.MARKET_DEPT_AUDIT_CONTRACT_FAIL, FlowStateEnum.MARKET_DEPT_AUDIT_CONTRACT), "客户：${delegationCreatorName} 重新提交了合同，等待市场部审核");
        // 市场部审核合同中 -> 市场部生成合同中
        normalFlowStateMap.put(Pair.of(FlowStateEnum.MARKET_DEPT_AUDIT_CONTRACT_FAIL, FlowStateEnum.MARKET_DEPT_GENERATE_CONTRACT), "市场部：{contractCreatorName} 重新修改了合同");
        // 市场部审核合同中 -> 市场部审核合同通过
        normalFlowStateMap.put(Pair.of(FlowStateEnum.MARKET_DEPT_AUDIT_CONTRACT_FAIL, FlowStateEnum.MARKET_DEPT_AUDIT_CONTRACT_SUCCESS), "市场部：${contractAcceptorName} 审核了合同，审核通过");
        // 市场部审核合同通过 -> 测试部生成测试方案中
        normalFlowStateMap.put(Pair.of(FlowStateEnum.MARKET_DEPT_AUDIT_CONTRACT_SUCCESS, FlowStateEnum.TEST_DEPT_GENERATE_TEST_SCHEME), "测试部生成测试方案中");
        // 测试部生成测试方案中 -> 质量部审核测试方案中
        normalFlowStateMap.put(Pair.of(FlowStateEnum.TEST_DEPT_GENERATE_TEST_SCHEME, FlowStateEnum.QUALITY_DEPT_AUDIT_TEST_SCHEME), "测试部：${solutionCreatorName} 提交了测试方案，等待质量部审核");
        // 质量部审核测试方案中 -> 质量部审核不通过，重新生成测试方案
        normalFlowStateMap.put(Pair.of(FlowStateEnum.QUALITY_DEPT_AUDIT_TEST_SCHEME, FlowStateEnum.QUALITY_DEPT_AUDIT_TEST_SCHEME_FAIL), "质量部：${solutionAcceptorName} 审核了测试方案，审核不通过，重新生成测试方案中，原因：${solutionRemark}");
        // 质量部审核不通过，重新生成测试方案 -> 质量部审核测试方案中
        normalFlowStateMap.put(Pair.of(FlowStateEnum.QUALITY_DEPT_AUDIT_TEST_SCHEME_FAIL, FlowStateEnum.QUALITY_DEPT_AUDIT_TEST_SCHEME), "测试部：${solutionCreatorName} 重新提交了测试方案，等待质量部审核");
        // 质量部审核测试方案中 -> 质量部审核通过
        normalFlowStateMap.put(Pair.of(FlowStateEnum.QUALITY_DEPT_AUDIT_TEST_SCHEME, FlowStateEnum.QUALITY_DEPT_AUDIT_TEST_SCHEME_SUCCESS), "质量部：${solutionAcceptorName} 审核了测试方案，审核通过");
        // 质量部审核通过 -> 测试部测试中
        normalFlowStateMap.put(Pair.of(FlowStateEnum.QUALITY_DEPT_AUDIT_TEST_SCHEME_SUCCESS, FlowStateEnum.TEST_DEPT_TEST), "测试部测试中");
        // 测试部测试中 -> 测试部完成测试
        normalFlowStateMap.put(Pair.of(FlowStateEnum.TEST_DEPT_TEST, FlowStateEnum.TEST_DEPT_TEST_SUCCESS), "测试部完成测试");
        // 测试部完成测试 -> 质量部审核测试报告
        normalFlowStateMap.put(Pair.of(FlowStateEnum.TEST_DEPT_TEST_SUCCESS, FlowStateEnum.QUALITY_DEPT_AUDIT_TEST_REPORT), "测试部：${reportCreatorName} 提交了测试报告，等待质量部审核");
        // 质量部审核测试报告 -> 质量部测试报告审核不通过，重新生成测试报告
        normalFlowStateMap.put(Pair.of(FlowStateEnum.QUALITY_DEPT_AUDIT_TEST_REPORT, FlowStateEnum.QUALITY_DEPT_AUDIT_TEST_REPORT_FAIL), "质量部：${reportAcceptorName} 审核了测试报告，审核不通过，重新生成测试报告中，原因：${reportRemark}");
        // 质量部测试报告审核不通过，重新生成测试报告 -> 质量部审核测试报告
        normalFlowStateMap.put(Pair.of(FlowStateEnum.QUALITY_DEPT_AUDIT_TEST_REPORT_FAIL, FlowStateEnum.QUALITY_DEPT_AUDIT_TEST_REPORT), "测试部：${reportCreatorName} 重新提交了测试报告，等待质量部审核");
        // 质量部审核测试报告 -> 质量部测试报告审核通过
        normalFlowStateMap.put(Pair.of(FlowStateEnum.QUALITY_DEPT_AUDIT_TEST_REPORT, FlowStateEnum.QUALITY_DEPT_AUDIT_TEST_REPORT_SUCCESS), "质量部：${reportAcceptorName} 审核了测试报告，审核通过");
        // 质量部测试报告审核通过 -> 测试完成
        normalFlowStateMap.put(Pair.of(FlowStateEnum.QUALITY_DEPT_AUDIT_TEST_REPORT_SUCCESS, FlowStateEnum.TEST_SUCCESS), "测试完成");
        // 委托填写中 -> 委托填写时取消委托
        normalFlowStateMap.put(Pair.of(FlowStateEnum.DELEGATE_WRITING, FlowStateEnum.DELEGATE_WRITING_CANCEL), "取消委托，流程终止");

        // some -> 委托填写时取消委托
        exceptionFlowStateMap.put(FlowStateEnum.DELEGATE_WRITING_CANCEL, "客户：${delegationCreatorName} 取消了委托");
        // some -> 合同填写时取消合同
        exceptionFlowStateMap.put(FlowStateEnum.CONTRACT_WRITING_CANCEL, "客户：${delegationCreatorName} 取消了委托");
        // some -> 客户违约
        exceptionFlowStateMap.put(FlowStateEnum.CUSTOMER_BREAK_CONTRACT, "客户：${delegationCreatorName} 违约");
    }


}
