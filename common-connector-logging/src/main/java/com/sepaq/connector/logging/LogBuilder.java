package com.sepaq.connector.logging;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogBuilder {

  private static final String CORRELATION_ID = "correlationId";

  public static final String PAYLOAD = "payload";

  private static final String ACTIVITE = "activity";

  private static final String APP_CODE = "appCode";

  public static enum Level {
    TRACE, DEBUG, INFO, ERROR, WARN
  }

  private Map<String, Object> fields = new LinkedHashMap<String, Object>();
  private Level level = Level.INFO;
  private Logger logger;
  private Throwable throwable;
  
  private String activity;
  private String appCode;
  private String correlationId;
  private String payload;

  private LogBuilder(Class<?> clazz) {
    this.logger = LoggerFactory.getLogger(clazz);
  }

  private LogBuilder(String name) {
    this.logger = LoggerFactory.getLogger(name);
  }

  public static LogBuilder newInstance(Class<?> clazz) {
    return new LogBuilder(clazz);
  }

  public static LogBuilder newInstance(String name) {
    return new LogBuilder(name);
  }

  public String sanitize(Object payload) {
    return Objects.toString(payload)//
        .replace("\n", "").replace("\"", "'");
  }

  public void doLog() {

    switch (level) {
      case TRACE:
        logger.trace(this.toString(), throwable);
        break;
      case DEBUG:
        logger.debug(this.toString(), throwable);
        break;
      case INFO:
        logger.info(this.toString(), throwable);
        break;
      case WARN:
        logger.warn(this.toString(), throwable);
        break;
      case ERROR:
        logger.error(this.toString(), throwable);
        break;
    }
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append(String.format("%s=\"%s\", ", APP_CODE, appCode));
    sb.append(String.format("%s=\"%s\", ", ACTIVITE, activity));
    sb.append(String.format("%s=\"%s\", ", CORRELATION_ID, correlationId));
    for (Entry<String, Object> field : fields.entrySet()) {
      if (field.getValue() != null) {
        sb.append(String.format("%s=\"%s\", ", field.getKey(), field.getValue()));
      }
    }
    sb.append(String.format("%s=\"%s\", ", PAYLOAD, payload));

    return sb.toString();
  }

  public LogBuilder withAppCode(String appCode) {
    this.appCode = appCode;
    return this;
  }

  public LogBuilder withPayload(Object payload) {
    this.payload = sanitize(payload);
    return this;
  }

  public LogBuilder withActivity(String activity) {
    this.activity = activity;
    return this;
  }

  public LogBuilder withCorrelationId(String correlationId) {
    this.correlationId = correlationId;
    return this;
  }

  public LogBuilder withSeverity(Level level) {
    this.level = level;
    return this;
  }

  public LogBuilder withOtherFields(Map<String, Object> otherFields) {
    this.fields = otherFields;
    return this;
  }

  public LogBuilder withException(Throwable exception) {
    this.throwable = exception;
    return this;
  }

}
