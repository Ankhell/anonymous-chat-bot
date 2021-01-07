package me.ankhell.anonymous.chat.bot.callback;

import lombok.Data;
import me.ankhell.anonymous.chat.bot.ActionType;

// Почему здесь творится такой пиздец? Экономим буквы чтоб влезали в callbackData, спасибо Телеграмму!
@Data
public class CallbackData {

  private final ActionType at;
  private final String pl;
  private final boolean dlKb;

  public CallbackData(ActionType actionType, String payload) {
    this.at = actionType;
    this.pl = payload;
    this.dlKb = true;
  }

  public CallbackData(ActionType actionType, boolean deleteKeyboard) {
    this.at = actionType;
    this.pl = "";
    this.dlKb = deleteKeyboard;
  }

  public CallbackData(ActionType actionType, String payload, boolean deleteKeyboard) {
    this.at = actionType;
    this.pl = payload;
    this.dlKb = deleteKeyboard;
  }

  public ActionType getActionType() {
    return at;
  }

  public String getPayload() {
    return pl;
  }

  public boolean isDeleteKeyboard() {
    return dlKb;
  }
}
