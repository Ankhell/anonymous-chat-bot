package me.ankhell.anonymous.chat.bot.callback.handlers;

import java.util.Collections;
import java.util.List;
import javax.inject.Singleton;
import me.ankhell.anonymous.chat.bot.callback.CallbackData;
import me.ankhell.anonymous.chat.bot.callback.interfaces.CallbackQueryHandler;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

@Singleton
public class DeleteKeyboardHandler implements CallbackQueryHandler {

  @Override
  public boolean wouldProcess(CallbackData callbackData) {
    return callbackData.isDeleteKeyboard();
  }

  @Override
  public List<BotApiMethod<?>> handleInlineQuery(CallbackQuery callbackQuery) {
    EditMessageReplyMarkup editMessageReplyMarkup = new EditMessageReplyMarkup();
    editMessageReplyMarkup.setChatId(callbackQuery.getFrom().getId().toString());
    editMessageReplyMarkup.setMessageId(callbackQuery.getMessage().getMessageId());
    return Collections.singletonList(editMessageReplyMarkup);
  }


}
