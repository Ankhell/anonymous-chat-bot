package me.ankhell.anonymous.chat.bot.messages.templates;

import me.ankhell.anonymous.chat.bot.ActionType;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public interface MessageProvider {

  SendMessage getAgeQueryMessage(Integer chatId, ActionType actionType);

  SendMessage getGenderQueryMessage(Integer chatId, ActionType actionType);

  SendMessage getMeOrPrefMessage(Integer chatId);

  SendMessage getAgeSetMessage(Integer chatId);

  SendMessage getWrongAgeMessage(Integer chatId, Integer originalMessageId);

  SendMessage getPrefComTypeMessage(Integer chatId);

  SendMessage getUserInfoMessage(Integer chatId);

  SendMessage getUserPreferencesMessage(Integer chatId);

}
