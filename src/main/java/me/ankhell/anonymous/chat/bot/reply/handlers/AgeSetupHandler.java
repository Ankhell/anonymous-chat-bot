package me.ankhell.anonymous.chat.bot.reply.handlers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.inject.Singleton;
import lombok.extern.slf4j.Slf4j;
import me.ankhell.anonymous.chat.bot.messages.templates.MessageProvider;
import me.ankhell.anonymous.chat.bot.reply.interfaces.MessageReplyHandler;
import me.ankhell.anonymous.chat.bot.users.User;
import me.ankhell.anonymous.chat.bot.users.UserVault;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

@Slf4j
@Singleton
public class AgeSetupHandler implements MessageReplyHandler {

  private final UserVault userVault;
  private final MessageProvider messageProvider;

  public AgeSetupHandler(UserVault userVault,
      MessageProvider messageProvider) {
    this.userVault = userVault;
    this.messageProvider = messageProvider;
  }

  @Override
  public boolean wouldProcess(Message messageWithReply) {
    return messageWithReply.getReplyToMessage().getText().equals("Please set up your age");
  }

  @Override
  public List<BotApiMethod<?>> handleReply(Message messageWithReply) {
    List<BotApiMethod<?>> result = new ArrayList<>();
    Optional<User> user = userVault.getUser(messageWithReply.getFrom().getId());
    String messageWithNumbers = messageWithReply.getText().replaceAll(".*?(\\d+).*?", "$1");
    if (messageWithNumbers.matches("\\d+")) {
      if (user.isPresent()) {
        user.get().setAge(Integer.parseInt(messageWithNumbers));
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(messageWithReply.getFrom().getId().toString());
        sendMessage.setText("Age was set correctly");
        result.add(sendMessage);
      } else {
        log.warn(String.format("User with id:%d was queried but was not found in vault",
            messageWithReply.getFrom().getId()));
      }
    } else {
      SendMessage errorMessage = new SendMessage();
      errorMessage.setText("Incorrect age");
      errorMessage.setChatId(messageWithReply.getFrom().getId().toString());
      result.add(errorMessage);
      result.add(messageProvider.getAgeQueryMessage(messageWithReply.getFrom().getId()));
    }
    return result;
  }
}
