package org.mendora.web.aop;

import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInvocation;


/**
 * created by:xmf
 * date:2018/3/27
 * description:
 */
@Slf4j
public class MonitorMethodInterceptor implements org.aopalliance.intercept.MethodInterceptor {
    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        before(methodInvocation);
        Object object = methodInvocation.proceed();
        after(methodInvocation);
        return object;
    }

    /**
     * before service method execute.
     *
     * @param methodInvocation
     */
    private void before(MethodInvocation methodInvocation) {
        // before service method execute.
        String name = methodInvocation.getMethod().getName();
        log.info(name);
    }

    /**
     * after service method execute.
     *
     * @param methodInvocation
     */
    private void after(MethodInvocation methodInvocation) {
        // after service method execute.
        String name = methodInvocation.getMethod().getName();
        log.info(name);
    }
}
