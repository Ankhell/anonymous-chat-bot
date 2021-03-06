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
public class AgeSetupHandler implements MessageReplyHandler {

  private final MessageProvider messageProvider;
  private final UserRepository userRepository;

  public AgeSetupHandler(
      MessageProvider messageProvider,
      UserRepository userRepository) {
    this.messageProvider = messageProvider;
    this.userRepository = userRepository;
  }

  @Override
  public boolean wouldProcess(Message messageWithReply) {
    return messageWithReply.getReplyToMessage().getText().equals("Пожалуйста, укажите Ваш возраст");
  }

  @Override
  public List<BotApiMethod<?>> handleReply(Message messageWithReply) {
    List<BotApiMethod<?>> result = new ArrayList<>();
    Optional<User> optUser = userRepository.findById(messageWithReply.getFrom().getId());
    String messageWithNumbers = messageWithReply.getText().replaceAll(".*?(\\d+).*?", "$1");
    if (messageWithNumbers.matches("\\d+")) {
      if (optUser.isPresent()) {
        User user = optUser.get();
        user.setAge(Integer.parseInt(messageWithNumbers));
        userRepository.update(user);
        result.add(messageProvider.getAgeSetMessage(messageWithReply.getFrom().getId()));
      } else {
        log.warn(String.format("User with id:%d was queried but was not found in vault",
            messageWithReply.getFrom().getId()));
      }
    } else {
      result.add(messageProvider.getWrongAgeMessage(messageWithReply.getFrom().getId(),
          messageWithReply.getMessageId()));
      result.add(messageProvider.getAgeQueryMessage(messageWithReply.getFrom().getId(),
          ActionType.UPDATE_SEX_ME));
    }
    return result;
  }
}
