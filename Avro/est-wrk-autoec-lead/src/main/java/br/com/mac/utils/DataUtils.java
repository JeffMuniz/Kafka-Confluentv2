package br.com.mac.funcionario.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

public class DataUtils {

  public static String retornarDataAtual() {

    DateFormat format = new SimpleDateFormat("dd-MM-yyyy-HH");
    Calendar calendar = Calendar.getInstance();
    return format.format(calendar.getTime());
  }

  public static LocalDate stringParaLocalDate(String data) {

    Instant instant = Instant.parse(data);
    return instant.atZone(ZoneId.systemDefault()).toLocalDate();
  }

  public static String retornarDataHoraAtual() {
    LocalDateTime localDateTime = LocalDateTime.now();
    return localDateTime.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
  }

  public static String retornarLocalDateTimeComoString(LocalDateTime localDateTime) {
    return localDateTime.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
  }
}
