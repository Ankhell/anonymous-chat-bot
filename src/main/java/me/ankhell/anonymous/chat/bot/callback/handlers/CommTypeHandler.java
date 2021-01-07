package me.ankhell.anonymous.chat.bot.callback.handlers;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import javax.inject.Singleton;
import me.ankhell.anonymous.chat.bot.ActionType;
import me.ankhell.anonymous.chat.bot.callback.CallbackData;
import me.ankhell.anonymous.chat.bot.callback.interfaces.CallbackQueryHandler;
import me.ankhell.anonymous.chat.bot.users.entity.CommunicationType;
import me.ankhell.anonymous.chat.bot.users.entity.User;
import me.ankhell.anonymous.chat.bot.users.repository.UserRepository;
import me.ankhell.anonymous.chat.bot.utility.DataUtils;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery.AnswerCallbackQueryBuilder;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

@Singleton
public class CommTypeHandler implements CallbackQueryHandler {

  private final UserRepository userRepository;

  public CommTypeHandler(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public boolean wouldProcess(CallbackData callbackData) {
    return callbackData.getActionType() == ActionType.UPDATE_PREF_COMM_TYPE;
  }

  @Override
  public List<BotApiMethod<?>> handleInlineQuery(CallbackQuery callbackQuery) {
    AnswerCallbackQueryBuilder answerCallbackQueryBuilder = AnswerCallbackQuery.builder()
        .callbackQueryId(callbackQuery.getId());
    CallbackData callbackData = DataUtils.getCallbackData(callbackQuery);
    CommunicationType communicationType = CommunicationType.valueOf(callbackData.getPayload());
    Optional<User> optUser = userRepository.findById(callbackQuery.getFrom().getId());
    if (optUser.isPresent()) {
      User user = optUser.get();
      user.setPrefferedCommunicationType(communicationType);
      answerCallbackQueryBuilder
          .text(String.format("Вы выбрали %s", communicationType.getRussianNotation()));
      userRepository.update(user);
    } else {
      answerCallbackQueryBuilder.text("Пользователь не найден");
    }
    return Collections.singletonList(answerCallbackQueryBuilder.build());
  }
}
