package me.ankhell.anonymous.chat.bot.keyboards;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

public interface KeyboardProvider {

  InlineKeyboardMarkup getSexChooserKeyboard();

}
