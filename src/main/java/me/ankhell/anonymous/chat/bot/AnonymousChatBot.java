package me.ankhell.anonymous.chat.bot;

import com.google.gson.Gson;
import io.micronaut.context.annotation.Property;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Singleton;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import me.ankhell.anonymous.chat.bot.callback.CallbackData;
import me.ankhell.anonymous.chat.bot.callback.interfaces.CallbackQueryHandler;
import me.ankhell.anonymous.chat.bot.keyboards.KeyboardProvider;
import me.ankhell.anonymous.chat.bot.reply.interfaces.MessageReplyHandler;
import me.ankhell.anonymous.chat.bot.users.User;
import me.ankhell.anonymous.chat.bot.users.UserVault;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Slf4j
@Singleton
public class AnonymousChatBot extends TelegramLongPollingBot {

  private final String botName;
  private final String botToken;
  private final List<CallbackQueryHandler> callbackQueryHandlers;
  private final UserVault userVault;
  private final Gson gson;
  private final KeyboardProvider keyboardProvider;
  private final List<MessageReplyHandler> messageReplyHandlers;

  public AnonymousChatBot(
      DefaultBotOptions botOptions,
      @Property(name = "bot.name") String botName,
      @Property(name = "bot.token") String botToken,
      List<CallbackQueryHandler> callbackQueryHandlers,
      UserVault userVault,
      Gson gson,
      KeyboardProvider keyboardProvider,
      List<MessageReplyHandler> messageReplyHandlers) {
    super(botOptions);
    this.botName = botName;
    this.botToken = botToken;
    this.callbackQueryHandlers = callbackQueryHandlers;
    this.userVault = userVault;
    this.gson = gson;
    this.keyboardProvider = keyboardProvider;
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
    if (message != null && message.getText().matches("(/start)|(/setup)")) {
      userVault.addOrUpdateUser(new User(message.getFrom().getId()));
      SendMessage sendMessage = new SendMessage();
      sendMessage.setText("Hello, I'm anonymous chat bot, please set your sex");
      sendMessage.setChatId(update.getMessage().getChatId().toString());
      sendMessage.setReplyMarkup(keyboardProvider.getSexChooserKeyboard());
      execute(sendMessage);
    }
    if (update.hasCallbackQuery()) {
      CallbackQuery callbackQuery = update.getCallbackQuery();
      CallbackData callbackData = gson.fromJson(callbackQuery.getData(), CallbackData.class);
      callbackQueryHandlers.stream()
          .filter(cq -> cq.wouldProcess(callbackData.getActionType()))
          .map(cq -> cq.handleInlineQuery(callbackQuery))
          .map(cq -> {
            List<BotApiMethod<?>> methods = new ArrayList<>();
            methods.add(cq.getFirst());
            if (cq.getSecond().isPresent()) {
              methods.add(cq.getSecond().get());
            }
            return methods;
          })
          .flatMap(List::stream)
          .forEach(this::executeAndLogError);
    }
    if (message != null && message.isReply()) {
      messageReplyHandlers.stream()
          .filter(rh -> rh.wouldProcess(message))
          .map(rh -> rh.handleReply(message))
          .flatMap(List::stream)
          .forEach(this::executeAndLogError);
    }
  }

  private <T extends Serializable, Method extends BotApiMethod<T>> void executeAndLogError(
      Method method) {
    try {
      execute(method);
    } catch (TelegramApiException e) {
      log.error("Something wrong happened during execution ", e);
    }
  }
}
