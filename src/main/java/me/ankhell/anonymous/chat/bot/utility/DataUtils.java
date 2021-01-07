package me.ankhell.anonymous.chat.bot.utility;

import com.google.gson.Gson;
import me.ankhell.anonymous.chat.bot.callback.CallbackData;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

public class DataUtils {

  private static final Gson gson = new Gson();

  public static CallbackData getCallbackData(CallbackQuery callbackQuery){
    return gson.fromJson(callbackQuery.getData(),CallbackData.class);
  }

  public static String callbackDataToJson(CallbackData callbackData){
    return gson.toJson(callbackData);
  }

}
