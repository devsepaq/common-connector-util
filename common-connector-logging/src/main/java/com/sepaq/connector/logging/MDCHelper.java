package com.sepaq.connector.logging;

import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;

public class MDCHelper {


  private static final String KEY_CLIENT = "client";
  private static final String CORRELATION_ID = "correlationId";

  public static String buildCorrelationId(String correlationId) {
    return (StringUtils.isNoneBlank(correlationId)) ? correlationId : UUID.randomUUID().toString();

  }

  public static void setCorrelationId(String correlationId) {
    MDC.put(CORRELATION_ID, correlationId);
  }

  public static String getCorrelationId() {
    return MDC.get(CORRELATION_ID);
  }

  public static void setClient(String client) {
    MDC.put(KEY_CLIENT, client);
  }

  public static void clearAll() {
    MDC.clear();
  }

  public static void clearCorrelationId() {
    MDC.remove(CORRELATION_ID);
  }

  public static void clearClient() {
    MDC.remove(KEY_CLIENT);
  }
}
