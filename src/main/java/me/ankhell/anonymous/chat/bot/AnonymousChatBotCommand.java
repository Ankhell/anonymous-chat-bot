package me.ankhell.anonymous.chat.bot;

import io.micronaut.configuration.picocli.PicocliRunner;
import io.micronaut.context.ApplicationContext;
import io.micronaut.context.annotation.Requires;
import io.micronaut.context.env.Environment;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import picocli.CommandLine.Command;

@Slf4j
@Command
@Requires(beans = {AnonymousChatBot.class})
public class AnonymousChatBotCommand implements Runnable {

  @Inject
  private ApplicationContext applicationContext;

  public static void main(String[] args) {
    String env = System.getenv("ENVIRONMENT");
    String environment;
    if (env != null && env.equals("heroku")) {
      environment = Environment.HEROKU;
    } else {
      environment = Environment.DEVELOPMENT;
    }
    ApplicationContext applicationContext = ApplicationContext
        .build(AnonymousChatBotCommand.class, environment)
        .environmentVariableIncludes("BOT_NAME", "BOT_TOKEN")
        .start();
    PicocliRunner.run(AnonymousChatBotCommand.class, applicationContext, args);

  }


  public void run() {
    try {
      // Create the TelegramBotsApi object to register your bots
      TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
      botsApi.registerBot(applicationContext.getBean(AnonymousChatBot.class));
      log.info("Your bot is up and running");
    } catch (TelegramApiException e) {
      e.printStackTrace();
    }
  }
}
