package me.ankhell.anonymous.chat.bot.messages.templates;

import javax.inject.Singleton;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ForceReplyKeyboard;

@Singleton
public class MessageProviderImpl implements
    MessageProvider {

  @Override
  public SendMessage getAgeQueryMessage(Integer chatId) {
    SendMessage sendMessage = new SendMessage();
    sendMessage.setText("Please set up your age");
    sendMessage.setChatId(chatId.toString());
    sendMessage.setReplyMarkup(ForceReplyKeyboard.builder().forceReply(true).build());
    return sendMessage;
  }
}
