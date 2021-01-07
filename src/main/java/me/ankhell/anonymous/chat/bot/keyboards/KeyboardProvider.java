package me.ankhell.anonymous.chat.bot.keyboards;

import me.ankhell.anonymous.chat.bot.ActionType;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

public interface KeyboardProvider {

  InlineKeyboardMarkup getSexChooserKeyboard(ActionType actionType);

  InlineKeyboardMarkup getMeOrPrefKeyboard();

  InlineKeyboardMarkup getCommunicationTypeKeyboard();

  InlineKeyboardMarkup getMainMenuKeyboard();

}
