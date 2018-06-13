package org.mendora.kernel.scanner.service.provider;

import com.google.inject.Injector;
import lombok.extern.slf4j.Slf4j;
import org.mendora.kernel.scanner.base.PackageScannerImpl;
import rx.Observable;

import java.util.List;

/**
 * created by:xmf
 * date:2018/3/19
 * description:
 */
@Slf4j
public class ServiceProviderScanner {
    public static final String REGISTER_METHOD_NAME = "register";

    /**
     * register accesser
     *
     * @param clazz
     */
    private void registerService(Class<?> clazz, Injector injector) {
        try {
            log.info(clazz.getName());
            Object o = injector.getInstance(clazz);
            clazz.getMethod(REGISTER_METHOD_NAME).invoke(o);
        } catch (Exception e) {
            Throwable e0=e.getCause();
            if(e0!=null) {
                log.error(e0.getClass().getName() + "==>" + e0.getStackTrace()[0].toString());
            }else {
                log.error("nocause："+e.getStackTrace()[0].toString());
            }
        }
    }

    /**
     * scanning accesser provider
     *
     * @param packagePath
     * @param cl
     */
    public void scan(String packagePath, ClassLoader cl, Injector injector) {
        List<String> names = new PackageScannerImpl<>(packagePath, cl).classNames();
        log.info(String.valueOf(names.size()));
        Observable.from(names)
                .map(name -> {
                    Class<?> clazz = null;
                    try {
                        clazz = Class.forName(name);
                    } catch (ClassNotFoundException e) {
                        Observable.error(e);
                    }
                    return clazz;
                })
                .filter(clazz -> clazz.isAnnotationPresent(ServiceProvider.class))
                .subscribe(clazz -> registerService(clazz, injector),
                        err -> log.error(err.getMessage()),
                        () -> log.info("all the accesser provider scanning over."));
    }
}
