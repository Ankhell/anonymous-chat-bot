package me.ankhell.anonymous.chat.bot.callback.handlers;

import java.util.ArrayList;
import java.util.List;
import javax.inject.Singleton;
import me.ankhell.anonymous.chat.bot.ActionType;
import me.ankhell.anonymous.chat.bot.callback.CallbackData;
import me.ankhell.anonymous.chat.bot.callback.interfaces.CallbackQueryHandler;
import me.ankhell.anonymous.chat.bot.messages.templates.MessageProvider;
import me.ankhell.anonymous.chat.bot.utility.DataUtils;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

@Singleton
public class UserOrPrefHandler implements CallbackQueryHandler {

  private final MessageProvider messageProvider;

  public UserOrPrefHandler(MessageProvider messageProvider) {
    this.messageProvider = messageProvider;
  }

  @Override
  public boolean wouldProcess(CallbackData callbackData) {
    return callbackData.getActionType() == ActionType.USER_OR_PREF;
  }

  @Override
  public List<BotApiMethod<?>> handleInlineQuery(CallbackQuery callbackQuery) {
    List<BotApiMethod<?>> result = new ArrayList<>();
    CallbackData callbackData = DataUtils.getCallbackData(callbackQuery);
    result.add(AnswerCallbackQuery.builder().callbackQueryId(callbackQuery.getId()).build());
    Integer userId = callbackQuery.getFrom().getId();
    if (callbackData.getPayload().equals("me")) {
      result.add(messageProvider.getGenderQueryMessage(userId, ActionType.UPDATE_SEX_ME));
    } else if (callbackData.getPayload().equals("pref")) {
      result.add(messageProvider.getGenderQueryMessage(userId, ActionType.UPDATE_SEX_PREF));
    }
    return result;
  }
}
