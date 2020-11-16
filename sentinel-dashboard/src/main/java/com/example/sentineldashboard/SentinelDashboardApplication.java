package com.example.sentineldashboard;

import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRule;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRuleManager;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class SentinelDashboardApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(SentinelDashboardApplication.class, args);
    }

    /**
     * 限流规则
     */
    public void initFlowQpsRule(){
        List<FlowRule> rules = new ArrayList<>();
        FlowRule rule1 = new FlowRule();
        rule1.setResource("abc");
        rule1.setCount(4);
        rule1.setGrade(RuleConstant.FLOW_GRADE_QPS);
        rule1.setLimitApp("default");
        rule1.setControlBehavior(RuleConstant.CONTROL_BEHAVIOR_DEFAULT);
        rules.add(rule1);
        FlowRuleManager.loadRules(rules);
    }

    /**
     * 降级规则
     * @param args
     * @throws Exception
     */
    /*private void initDegradeRule() {
        List<DegradeRule> rules = new ArrayList<DegradeRule>();
        DegradeRule rule = new DegradeRule();
        rule.setResource("abc");
        rule.setCount(1);
        rule.setGrade(RuleConstant.DEGRADE_GRADE_EXCEPTION_COUNT);
        rule.setTimeWindow(2);
        rules.add(rule);
        DegradeRuleManager.loadRules(rules);
    }*/

    @Override
    public void run(String... args) throws Exception {
        this.initFlowQpsRule();
        //this.initDegradeRule();
    }

}
