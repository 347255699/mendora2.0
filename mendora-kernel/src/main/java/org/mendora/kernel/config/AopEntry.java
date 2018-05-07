package org.mendora.kernel.config;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.aopalliance.intercept.MethodInterceptor;

import java.lang.annotation.Annotation;

/**
 * created by:xmf
 * date:2018/5/7
 * description:
 */
@RequiredArgsConstructor
public class AopEntry {
    @Getter
    @NonNull
    private Class<? extends Annotation> annotation;
    @Getter
    @NonNull
    private MethodInterceptor methodInterceptor;
}
