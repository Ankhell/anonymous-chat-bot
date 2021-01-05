package me.ankhell.anonymous.chat.bot.messages.templates;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public interface MessageProvider {

  SendMessage getAgeQueryMessage(Integer chatId);

}
