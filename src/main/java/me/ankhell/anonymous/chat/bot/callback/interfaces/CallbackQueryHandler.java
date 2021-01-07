package me.ankhell.anonymous.chat.bot.callback.interfaces;

import java.util.List;
import me.ankhell.anonymous.chat.bot.callback.CallbackData;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

public interface CallbackQueryHandler {

  boolean wouldProcess(CallbackData callbackData);

  List<BotApiMethod<?>> handleInlineQuery(CallbackQuery callbackQuery);

}
