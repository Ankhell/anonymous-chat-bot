package me.ankhell.anonymous.chat.bot.keyboards.builders;

import java.util.ArrayList;
import java.util.List;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

public class InlineKeyboardBuilder {

  private final List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
  private int currentRowIndex = 0;

  public InlineKeyboardBuilder() {
    keyboard.add(new ArrayList<>());
  }

  public static InlineKeyboardBuilder getInstance() {
    return new InlineKeyboardBuilder();
  }

  public InlineKeyboardButtonBuilder<InlineKeyboardBuilder> kbd_addButton() {
    return new InlineKeyboardButtonBuilder<>(this,
        (InlineKeyboardButton btn) -> keyboard.get(currentRowIndex).add(btn));
  }

  public InlineKeyboardBuilder nextRow() {
    currentRowIndex++;
    keyboard.add(new ArrayList<>());
    return this;
  }

  public InlineKeyboardMarkup kbd_build() {
    return InlineKeyboardMarkup.builder().keyboard(keyboard).build();
  }

}
