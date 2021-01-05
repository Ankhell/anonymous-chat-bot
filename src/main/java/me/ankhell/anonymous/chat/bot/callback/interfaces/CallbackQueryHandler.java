package me.ankhell.anonymous.chat.bot.callback.interfaces;

import java.util.Optional;
import me.ankhell.anonymous.chat.bot.callback.CallbackActionType;
import org.glassfish.grizzly.utils.Pair;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;

public interface CallbackQueryHandler {

  boolean wouldProcess(CallbackActionType callbackActionType);

  Pair<AnswerCallbackQuery, Optional<BotApiMethod<Message>>> handleInlineQuery(CallbackQuery callbackQuery);

}
