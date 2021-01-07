package me.ankhell.anonymous.chat.bot.commands.interfaces;

import java.util.List;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;

public interface CommandHandler {

  boolean wouldProcess(Message message);

  List<BotApiMethod<?>> handleCommand(Message message);
}
