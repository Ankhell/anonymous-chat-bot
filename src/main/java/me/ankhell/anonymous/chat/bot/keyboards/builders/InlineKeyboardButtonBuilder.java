package me.ankhell.anonymous.chat.bot.keyboards.builders;

import com.google.common.base.Preconditions;
import java.util.function.Consumer;
import me.ankhell.anonymous.chat.bot.ActionType;
import me.ankhell.anonymous.chat.bot.callback.CallbackData;
import me.ankhell.anonymous.chat.bot.utility.DataUtils;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

public class InlineKeyboardButtonBuilder <T> {

  private final T parentBuilder;
  private final Consumer<InlineKeyboardButton> consumer;
  private String text;
  private ActionType callbackActionType;
  private String callbackPayload;
  private boolean deleteKeyboard;

  public InlineKeyboardButtonBuilder(T parentBuilder,
      Consumer<InlineKeyboardButton> consumer) {
    this.parentBuilder = parentBuilder;
    this.consumer = consumer;
  }

  public InlineKeyboardButtonBuilder<T> btn_withText(String text) {
    this.text = text;
    return this;
  }

  public InlineKeyboardButtonBuilder<T> btn_withCallbackActionType(ActionType callbackActionType) {
    this.callbackActionType = callbackActionType;
    return this;
  }

  public InlineKeyboardButtonBuilder<T> btn_withCallbackPayload(String callbackPayload) {
    this.callbackPayload = callbackPayload;
    return this;
  }

  public InlineKeyboardButtonBuilder<T> btn_deleteKeyboardWhenPressed(){
    this.deleteKeyboard = true;
    return this;
  }

  public T btn_finish() {
    InlineKeyboardButton inlineKeyboardButton = new InlineKeyboardButton();
    Preconditions.checkNotNull(callbackActionType);
    CallbackData callbackData = new CallbackData(callbackActionType, callbackPayload,deleteKeyboard);
    inlineKeyboardButton.setCallbackData(DataUtils.callbackDataToJson(callbackData));
    if (text != null) {
      inlineKeyboardButton.setText(text);
    }
    consumer.accept(inlineKeyboardButton);
    return parentBuilder;
  }

}
