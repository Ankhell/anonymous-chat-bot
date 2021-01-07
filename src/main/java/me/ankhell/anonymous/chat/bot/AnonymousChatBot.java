package me.ankhell.anonymous.chat.bot;

import io.micronaut.context.annotation.Property;
import java.io.Serializable;
import java.util.List;
import javax.inject.Singleton;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import me.ankhell.anonymous.chat.bot.callback.CallbackData;
import me.ankhell.anonymous.chat.bot.callback.interfaces.CallbackQueryHandler;
import me.ankhell.anonymous.chat.bot.commands.interfaces.CommandHandler;
import me.ankhell.anonymous.chat.bot.reply.interfaces.MessageReplyHandler;
import me.ankhell.anonymous.chat.bot.utility.DataUtils;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;

@Slf4j
@Singleton
public class AnonymousChatBot extends TelegramLongPollingBot {

  private final String botName;
  private final String botToken;
  private final List<CallbackQueryHandler> callbackQueryHandlers;
  private final List<MessageReplyHandler> messageReplyHandlers;
  private final List<CommandHandler> commandHandlers;

  public AnonymousChatBot(
      DefaultBotOptions botOptions,
      @Property(name = "bot.name") String botName,
      @Property(name = "bot.token") String botToken,
      List<CallbackQueryHandler> callbackQueryHandlers,
      List<CommandHandler> commandHandlers,
      List<MessageReplyHandler> messageReplyHandlers  ) {
    super(botOptions);
    this.botName = botName;
    this.botToken = botToken;
    this.callbackQueryHandlers = callbackQueryHandlers;
    this.commandHandlers = commandHandlers;
    this.messageReplyHandlers = messageReplyHandlers;
  }

  @Override
  public String getBotUsername() {
    return botName;
  }

  @Override
  public String getBotToken() {
    return botToken;
  }

  @SneakyThrows
  @Override
  public void onUpdateReceived(Update update) {
    Message message = update.getMessage();
    handleCommands(message);
    handleCallbacks(update);
    handleReplys(message);
  }

  private void handleReplys(Message message) {
    if (message != null && message.isReply()) {
      messageReplyHandlers.stream()
          .filter(rh -> rh.wouldProcess(message))
          .map(rh -> rh.handleReply(message))
          .flatMap(List::stream)
          .forEach(this::executeAndLogError);
    }
  }

  private void handleCallbacks(Update update) {
    if (update.hasCallbackQuery()) {
      CallbackQuery callbackQuery = update.getCallbackQuery();
      CallbackData callbackData = DataUtils.getCallbackData(callbackQuery);
      callbackQueryHandlers.stream()
          .filter(cq -> cq.wouldProcess(callbackData))
          .map(cq -> cq.handleInlineQuery(callbackQuery))
          .flatMap(List::stream)
          .forEach(this::executeAndLogError);
    }
  }

  private void handleCommands(Message message) {
    if (message != null && message.getText().startsWith("/")) {
      commandHandlers.stream()
          .filter(cmdh -> cmdh.wouldProcess(message))
          .map(cmdh -> cmdh.handleCommand(message))
          .flatMap(List::stream)
          .forEach(this::executeAndLogError);
    }
  }

  private <T extends Serializable, Method extends BotApiMethod<T>> void executeAndLogError(
      Method method) {
    try {
      execute(method);
    } catch (TelegramApiRequestException e) {
      log.error(e.getApiResponse());
      log.error("Something wrong happened during execution ", e);
    } catch (TelegramApiException e){
      log.error("Something wrong happened during execution ", e);
    }
  }
}
