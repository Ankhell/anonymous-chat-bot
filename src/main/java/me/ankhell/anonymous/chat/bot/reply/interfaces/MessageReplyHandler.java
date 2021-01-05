package me.ankhell.anonymous.chat.bot.reply.interfaces;

import java.util.List;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;

public interface MessageReplyHandler {

  boolean wouldProcess(Message messageWithReply);

  List<BotApiMethod<?>> handleReply(Message messageWithReply);

}
