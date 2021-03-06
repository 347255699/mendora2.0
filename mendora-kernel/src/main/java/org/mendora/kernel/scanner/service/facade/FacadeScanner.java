package org.mendora.kernel.scanner.service.facade;

import io.vertx.core.Vertx;
import lombok.extern.slf4j.Slf4j;
import org.mendora.facade.annotation.facade.ServiceFacade;
import org.mendora.kernel.binder.FacadeBinder;
import org.mendora.kernel.scanner.base.PackageScannerImpl;

import java.util.ArrayList;
import java.util.List;

/**
 * created by:xmf
 * date:2018/3/19
 * description:
 */
@Slf4j
public class FacadeScanner {

    /**
     * scanning all facade blow target package.
     *
     * @param packagePath
     * @return
     */
    public FacadeBinder scan(String packagePath, Vertx vertx) {
        List<String> names = new PackageScannerImpl<>(packagePath, this.getClass().getClassLoader())
                .classNames(this.getClass().getName());
        List<Class> proxys = new ArrayList<>();
        List<Class> facades = new ArrayList<>();
        for (String name : names) {
            try {
                Class facade = Class.forName(name);
                if (facade.isAnnotationPresent(ServiceFacade.class)) {
                    log.info(name);
                    ServiceFacade serviceFacade = (ServiceFacade) facade.getAnnotation(ServiceFacade.class);
                    Class proxy = serviceFacade.proxy();
                    facades.add(facade);
                    proxys.add(proxy);
                }
            } catch (ClassNotFoundException e) {
                log.error(e.getMessage());
            }
        }
        FacadeBinder facadeBinder = new FacadeBinder();
        facadeBinder.setFacades(facades);
        facadeBinder.setProxys(proxys);
        facadeBinder.setVertx(vertx);
        return facadeBinder;
    }

}
