package me.ankhell.anonymous.chat.bot.reply.handlers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.inject.Singleton;
import lombok.extern.slf4j.Slf4j;
import me.ankhell.anonymous.chat.bot.ActionType;
import me.ankhell.anonymous.chat.bot.messages.templates.MessageProvider;
import me.ankhell.anonymous.chat.bot.reply.interfaces.MessageReplyHandler;
import me.ankhell.anonymous.chat.bot.users.entity.User;
import me.ankhell.anonymous.chat.bot.users.repository.UserRepository;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;

@Slf4j
@Singleton
public class PrefAgeSetupHandler implements MessageReplyHandler {

  private final MessageProvider messageProvider;
  private final UserRepository userRepository;

  public PrefAgeSetupHandler(
      MessageProvider messageProvider,
      UserRepository userRepository) {
    this.messageProvider = messageProvider;
    this.userRepository = userRepository;
  }

  @Override
  public boolean wouldProcess(Message messageWithReply) {
    return messageWithReply.getReplyToMessage().getText()
        .matches("Пожалуйста, укажите желаемый .* возраст собеседника");
  }

  @Override
  public List<BotApiMethod<?>> handleReply(Message messageWithReply) {
    ActionType actionType =
        messageWithReply.getReplyToMessage().getText().split(" ")[3].equals("минимальный")
            ? ActionType.UDATE_PREF_MIN_AGE : ActionType.UDATE_PREF_MAX_AGE;
    List<BotApiMethod<?>> result = new ArrayList<>();
    Integer userId = messageWithReply.getFrom().getId();
    Optional<User> optUser = userRepository.findById(userId);
    String messageWithNumbers = messageWithReply.getText().replaceAll(".*?(\\d+).*?", "$1");
    if (messageWithNumbers.matches("\\d+")) {
      Integer age = Integer.parseInt(messageWithNumbers);
      if (optUser.isPresent()) {
        User user = optUser.get();
        if (actionType == ActionType.UDATE_PREF_MIN_AGE) {
          user.setPrefferedMinAge(age);
          result.add(messageProvider.getAgeSetMessage(userId));
          result.add(messageProvider.getAgeQueryMessage(userId, ActionType.UDATE_PREF_MAX_AGE));
        } else {
          user.setPrefferedMaxAge(age);
          result.add(messageProvider.getAgeSetMessage(userId));
          result.add(messageProvider.getPrefComTypeMessage(userId));
        }
        userRepository.update(user);
      } else {
        log.warn(String.format("User with id:%d was queried but was not found in vault",
            userId));
      }
    } else {
      result.add(messageProvider.getWrongAgeMessage(userId, messageWithReply.getMessageId()));
      result.add(messageProvider.getAgeQueryMessage(userId, actionType));
    }
    return result;
  }
}
