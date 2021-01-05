package me.ankhell.anonymous.chat.bot.factories;

import com.google.gson.Gson;
import io.micronaut.context.annotation.Factory;
import javax.inject.Singleton;

@Factory
public class GsonFactory {

  @Singleton
  Gson getGson(){
    return new Gson();
  }
}
