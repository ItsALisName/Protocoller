package net.alis.protocoller.util;

import net.alis.protocoller.plugin.enums.Version;

import java.util.function.Predicate;

public interface ParametersChangeable {

    <P> P getParameter(int index, Class<?> type) throws Exception;

    void setParameter(int index, Class<?> type, Object parameter) throws Exception;

    <P> P getParameter(Predicate<Version> versionPredicate, int index1, int index2, Class<?> type) throws Exception;

    void setParameter(Predicate<Version> versionPredicate, int index1, int index2, Object parameter) throws Exception;

}
