package org.mendora.kernel.scanner.base;

/**
 * Created by kam on 2018/2/4.
 */
@FunctionalInterface
public interface ScannerFilter {
    String filte(String fullyQualifiedName);
}
