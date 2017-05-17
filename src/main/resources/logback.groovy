import ch.qos.logback.classic.encoder.PatternLayoutEncoder
import ch.qos.logback.classic.filter.LevelFilter
import ch.qos.logback.classic.filter.ThresholdFilter
import ch.qos.logback.core.ConsoleAppender
import ch.qos.logback.core.FileAppender

import static ch.qos.logback.classic.Level.ALL
import static ch.qos.logback.classic.Level.WARN
import static ch.qos.logback.core.spi.FilterReply.ACCEPT
import static ch.qos.logback.core.spi.FilterReply.DENY

appender("STDOUT", ConsoleAppender) {
  filter(LevelFilter) {
    level = WARN
    onMatch = ACCEPT
    onMismatch = DENY
  }
  encoder(PatternLayoutEncoder) {
    pattern = "%d{HH:mm:ss.SSS} [%thread] %-5level %logger{36} - %msg%n"
  }
}

appender("FILE_MSG", FileAppender) {
  file = "logs/messages.log"
  append = false
  filter(ThresholdFilter) {
    level = INFO
  }
  encoder(PatternLayoutEncoder) {
    pattern = "%d{HH:mm:ss.SSS} [%thread] %-5level %logger{36} - %msg%n"
  }
}

appender("FILE_ERR", FileAppender) {
  file = "logs/errors.log"
  append = false
  filter(LevelFilter) {
    level = ERROR
    onMatch = ACCEPT
    onMismatch = DENY
  }
  encoder(PatternLayoutEncoder) {
    pattern = "%d{HH:mm:ss.SSS} [%thread] %-5level %logger{36} - %msg%n"
  }
}

root(ALL, ["STDOUT", "FILE_MSG", "FILE_ERR"])
