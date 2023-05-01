package com.wyy.endpoint.config;

import org.aspectj.lang.annotation.Aspect;
import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionManager;
import org.springframework.transaction.interceptor.NameMatchTransactionAttributeSource;
import org.springframework.transaction.interceptor.RuleBasedTransactionAttribute;
import org.springframework.transaction.interceptor.TransactionAttribute;
import org.springframework.transaction.interceptor.TransactionInterceptor;

import java.util.HashMap;
import java.util.Map;

@Configuration                            // 定义配置Bean
@Aspect                                // 采用AOP切面处理
public class TransactionConfig {
    private static final int TRANSACTION_METHOD_TIMEOUT = 5 ; 	// 事务超时时间为5秒
    private static final String AOP_POINTCUT_EXPRESSION =
            "execution (* com.wyy..service.*.*(..))";   		// 定义切面表达式
    @Autowired
    private TransactionManager transactionManager;  		// 注入事务管理对象
    @Bean("txAdvice")           					// 事务拦截器
    public TransactionInterceptor transactionConfig() {   		// 定义事务控制切面
        RuleBasedTransactionAttribute readOnly = new RuleBasedTransactionAttribute();
        readOnly.setReadOnly(true); 				// 只读事务
        readOnly.setPropagationBehavior(
                TransactionDefinition.PROPAGATION_NOT_SUPPORTED); 	// 非事务运行
        RuleBasedTransactionAttribute required = new RuleBasedTransactionAttribute();
        required.setPropagationBehavior(
                TransactionDefinition.PROPAGATION_REQUIRED); 	// 事务开启
        required.setTimeout(TRANSACTION_METHOD_TIMEOUT);
        Map<String, TransactionAttribute> transactionMap = new HashMap<>();
        transactionMap.put("add*", required); 			// 事务方法前缀
        transactionMap.put("edit*", required); 			// 事务方法前缀
        transactionMap.put("delete*", required); 			// 事务方法前缀
        transactionMap.put("get*", readOnly); 			// 事务方法前缀
        transactionMap.put("list*", readOnly); 			// 事务方法前缀
        NameMatchTransactionAttributeSource source =
                new NameMatchTransactionAttributeSource();		// 命名匹配事务
        source.setNameMap(transactionMap); 				// 设置事务方法
        TransactionInterceptor transactionInterceptor = new
                TransactionInterceptor(transactionManager, source); 	// 事务拦截器
        return transactionInterceptor ;
    }
    @Bean
    public Advisor transactionAdviceAdvisor() {
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression(AOP_POINTCUT_EXPRESSION);    		// 定义切面
        return new DefaultPointcutAdvisor(pointcut, transactionConfig());
    }
}

