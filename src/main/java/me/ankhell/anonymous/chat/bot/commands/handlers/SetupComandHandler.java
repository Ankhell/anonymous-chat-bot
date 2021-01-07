package me.ankhell.anonymous.chat.bot.commands.handlers;

import java.util.Collections;
import java.util.List;
import javax.inject.Singleton;
import me.ankhell.anonymous.chat.bot.commands.interfaces.CommandHandler;
import me.ankhell.anonymous.chat.bot.messages.templates.MessageProvider;
import me.ankhell.anonymous.chat.bot.users.entity.User;
import me.ankhell.anonymous.chat.bot.users.repository.UserRepository;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;

@Singleton
public class SetupComandHandler implements CommandHandler {

  private final UserRepository userRepository;
  private final MessageProvider messageProvider;

  public SetupComandHandler(
      UserRepository userRepository,
      MessageProvider messageProvider) {
    this.userRepository = userRepository;
    this.messageProvider = messageProvider;
  }

  @Override
  public boolean wouldProcess(Message message) {
    return message.getText().equals("/setup");
  }

  @Override
  public List<BotApiMethod<?>> handleCommand(Message message) {
    Integer senderId = message.getFrom().getId();
    if (userRepository.findById(senderId).isEmpty()) {
      userRepository.save(new User(senderId));
    }
    return Collections.singletonList(messageProvider.getMeOrPrefMessage(message.getChatId().intValue()));
  }
}
