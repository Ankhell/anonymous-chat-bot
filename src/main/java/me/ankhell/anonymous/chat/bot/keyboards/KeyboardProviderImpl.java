package me.ankhell.anonymous.chat.bot.keyboards;

import com.google.gson.Gson;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.Map;
import javax.inject.Singleton;
import me.ankhell.anonymous.chat.bot.callback.CallbackActionType;
import me.ankhell.anonymous.chat.bot.callback.CallbackData;
import me.ankhell.anonymous.chat.bot.users.User.Sex;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

@Singleton
public class KeyboardProviderImpl implements
    KeyboardProvider {

  private final Gson gson;
  private final Map<KeyBoardType, InlineKeyboardMarkup> keybds = new EnumMap<>(KeyBoardType.class);

  public KeyboardProviderImpl(Gson gson) {
    this.gson = gson;
  }

  @Override
  public synchronized InlineKeyboardMarkup getSexChooserKeyboard() {
    if (!keybds.containsKey(KeyBoardType.GENDER_CHOOSER)) {
      CallbackActionType callbackAction = CallbackActionType.UPDATE_SEX;
      InlineKeyboardButton maleButton = InlineKeyboardButton.builder().text("Male")
          .callbackData(gson.toJson(new CallbackData(callbackAction,
              Sex.MALE.name()))).build();
      InlineKeyboardButton femaleButton = InlineKeyboardButton.builder().text("Female")
          .callbackData(gson.toJson(new CallbackData(callbackAction,
              Sex.FEMALE.name()))).build();
      keybds.put(KeyBoardType.GENDER_CHOOSER,
          InlineKeyboardMarkup.builder().keyboardRow(Arrays.asList(maleButton, femaleButton))
              .build());
    }
    return keybds.get(KeyBoardType.GENDER_CHOOSER);
  }

  // TODO: 05.01.2021 Заменить на CallbackActionType (или нет)
  enum KeyBoardType {
    GENDER_CHOOSER,
    AGE_CHOOSER,
    ;
  }
}
