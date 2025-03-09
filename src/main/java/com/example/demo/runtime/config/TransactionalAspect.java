package com.example.demo.runtime.config;

import com.example.demo.domain.DomainTransactional;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

@Aspect
@Component
public class TransactionalAspect {

    private final PlatformTransactionManager transactionManager;

    public TransactionalAspect(PlatformTransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }

    @Around("@annotation(com.example.demo.domain.DomainTransactional")
    public Object manageTransaction(ProceedingJoinPoint joinPoint) throws Throwable {
        DomainTransactional annotation = ((MethodSignature) joinPoint.getSignature()).getMethod().getAnnotation(DomainTransactional.class);

        DefaultTransactionDefinition definition = new DefaultTransactionDefinition();
        definition.setIsolationLevel(TransactionDefinition.ISOLATION_DEFAULT);
        definition.setPropagationBehavior(annotation.propagation().value());
        definition.setTimeout(annotation.timeout());

        // Start transaction
        TransactionStatus status = transactionManager.getTransaction(definition);

        try {
            Object result = joinPoint.proceed();
            transactionManager.commit(status);
            return result;
        } catch (Exception e) {
            // Ignore rollback exception
            try {
                transactionManager.rollback(status);
            } catch (Exception ignored) {
            }
            throw e;
        }
    }
}
