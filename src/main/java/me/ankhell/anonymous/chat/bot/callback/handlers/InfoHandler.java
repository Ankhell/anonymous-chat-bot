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
public class InfoHandler implements CallbackQueryHandler {

  private final MessageProvider messageProvider;

  public InfoHandler(MessageProvider messageProvider) {
    this.messageProvider = messageProvider;
  }

  @Override
  public boolean wouldProcess(CallbackData callbackData) {
    ActionType actionType = callbackData.getActionType();
    return actionType == ActionType.GET_MY_INFO || actionType == ActionType.GET_PREF_INFO;
  }

  @Override
  public List<BotApiMethod<?>> handleInlineQuery(CallbackQuery callbackQuery) {
    List<BotApiMethod<?>> result = new ArrayList<>();
    result.add(AnswerCallbackQuery.builder().callbackQueryId(callbackQuery.getId()).showAlert(true).build());
    CallbackData callbackData = DataUtils.getCallbackData(callbackQuery);
    ActionType actionType = callbackData.getActionType();
    Integer senderId = callbackQuery.getFrom().getId();
    switch (actionType){
      case GET_MY_INFO:
        result.add(messageProvider.getUserInfoMessage(senderId));
        break;
      case GET_PREF_INFO:
        result.add(messageProvider.getUserPreferencesMessage(senderId));
    }
    return result;
  }
}
