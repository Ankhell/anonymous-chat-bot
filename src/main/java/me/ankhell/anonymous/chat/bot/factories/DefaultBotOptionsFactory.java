package me.ankhell.anonymous.chat.bot.factories;

import io.micronaut.context.annotation.Factory;
import javax.inject.Singleton;
import org.telegram.telegrambots.bots.DefaultBotOptions;

@Factory
public class DefaultBotOptionsFactory {

  @Singleton
  DefaultBotOptions defaultBotOptions(){
    return new DefaultBotOptions();
  }
}
