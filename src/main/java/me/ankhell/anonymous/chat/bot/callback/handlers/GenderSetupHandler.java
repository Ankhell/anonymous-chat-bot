package me.ankhell.anonymous.chat.bot.callback.handlers;

import com.google.gson.Gson;
import java.util.Locale;
import java.util.Optional;
import javax.inject.Singleton;
import me.ankhell.anonymous.chat.bot.callback.CallbackActionType;
import me.ankhell.anonymous.chat.bot.callback.CallbackData;
import me.ankhell.anonymous.chat.bot.callback.interfaces.CallbackQueryHandler;
import me.ankhell.anonymous.chat.bot.messages.templates.MessageProvider;
import me.ankhell.anonymous.chat.bot.users.User;
import me.ankhell.anonymous.chat.bot.users.UserVault;
import org.glassfish.grizzly.utils.Pair;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery.AnswerCallbackQueryBuilder;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;

@Singleton
public class GenderSetupHandler implements CallbackQueryHandler {

  private final Gson gson;
  private final UserVault userVault;
  private final MessageProvider messageProvider;

  public GenderSetupHandler(
      Gson gson,
      UserVault userVault,
      MessageProvider messageProvider) {
    this.gson = gson;
    this.userVault = userVault;
    this.messageProvider = messageProvider;
  }

  @Override
  public boolean wouldProcess(CallbackActionType callbackActionType) {
    return callbackActionType == CallbackActionType.UPDATE_SEX;
  }

  @Override
  public Pair<AnswerCallbackQuery, Optional<BotApiMethod<Message>>> handleInlineQuery(
      CallbackQuery callbackQuery) {
    AnswerCallbackQueryBuilder answerCallbackQueryBuilder = AnswerCallbackQuery.builder()
        .callbackQueryId(callbackQuery.getId());
    CallbackData callbackData = gson.fromJson(callbackQuery.getData(), CallbackData.class);
    switch (callbackData.getActionType()) {
      case UPDATE_SEX:
        User.Sex sex = User.Sex.valueOf(callbackData.getPayload());
        Optional<User> user = userVault.getUser(callbackQuery.getFrom().getId());
        if (user.isPresent()) {
          user.get().setSex(sex);
          answerCallbackQueryBuilder.text(
              String.format("Your gender was set to %s", sex.name().toLowerCase(Locale.ROOT)));
        } else {
          answerCallbackQueryBuilder.text("Unable to find user");
        }
        break;
      case UPDATE_AGE:
        answerCallbackQueryBuilder.text("Not implemented yet");
        break;
    }
    return new Pair<>(answerCallbackQueryBuilder.build(), Optional.of(
        messageProvider.getAgeQueryMessage(callbackQuery.getFrom().getId())));
  }
}
