package me.ankhell.anonymous.chat.bot.callback.handlers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.inject.Singleton;
import me.ankhell.anonymous.chat.bot.ActionType;
import me.ankhell.anonymous.chat.bot.callback.CallbackData;
import me.ankhell.anonymous.chat.bot.callback.interfaces.CallbackQueryHandler;
import me.ankhell.anonymous.chat.bot.messages.templates.MessageProvider;
import me.ankhell.anonymous.chat.bot.users.entity.Sex;
import me.ankhell.anonymous.chat.bot.users.entity.User;
import me.ankhell.anonymous.chat.bot.users.repository.UserRepository;
import me.ankhell.anonymous.chat.bot.utility.DataUtils;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery.AnswerCallbackQueryBuilder;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

@Singleton
public class MyGenderSetupHandler implements CallbackQueryHandler {

  private final MessageProvider messageProvider;
  private final UserRepository userRepository;

  public MyGenderSetupHandler(
      MessageProvider messageProvider,
      UserRepository userRepository) {
    this.messageProvider = messageProvider;
    this.userRepository = userRepository;
  }


  @Override
  public boolean wouldProcess(CallbackData callbackData) {
    return callbackData.getActionType() == ActionType.UPDATE_SEX_ME;
  }

  @Override
  public List<BotApiMethod<?>> handleInlineQuery(
      CallbackQuery callbackQuery) {
    List<BotApiMethod<?>> result = new ArrayList<>();
    AnswerCallbackQueryBuilder answerCallbackQueryBuilder = AnswerCallbackQuery.builder()
        .callbackQueryId(callbackQuery.getId());
    CallbackData callbackData = DataUtils.getCallbackData(callbackQuery);
    Sex sex = Sex.valueOf(callbackData.getPayload());
    Optional<User> optUser = userRepository.findById(callbackQuery.getFrom().getId());
    if (optUser.isPresent()) {
      User user = optUser.get();
      user.setSex(sex);
      answerCallbackQueryBuilder.text(
          String.format("Вы выбрали %s пол", sex.getRussianNotation()));
      userRepository.update(user);
    } else {
      answerCallbackQueryBuilder.text("Пользователь не найден");
    }
    result.add(answerCallbackQueryBuilder.build());
    result.add(messageProvider
        .getAgeQueryMessage(callbackQuery.getFrom().getId(), callbackData.getActionType()));
    return result;
  }
}
