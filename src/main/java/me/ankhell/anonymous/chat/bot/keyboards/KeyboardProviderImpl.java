package me.ankhell.anonymous.chat.bot.keyboards;

import java.util.Arrays;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.inject.Singleton;
import me.ankhell.anonymous.chat.bot.ActionType;
import me.ankhell.anonymous.chat.bot.callback.CallbackData;
import me.ankhell.anonymous.chat.bot.keyboards.builders.InlineKeyboardBuilder;
import me.ankhell.anonymous.chat.bot.users.entity.CommunicationType;
import me.ankhell.anonymous.chat.bot.users.entity.Sex;
import me.ankhell.anonymous.chat.bot.utility.DataUtils;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

@Singleton
public class KeyboardProviderImpl implements
    KeyboardProvider {

  private final Map<ActionType, InlineKeyboardMarkup> keybds = new EnumMap<>(ActionType.class);

  @Override
  public InlineKeyboardMarkup getSexChooserKeyboard(ActionType actionType) {
    if (!keybds.containsKey(actionType)) {
      keybds.put(
          actionType,
          InlineKeyboardBuilder.getInstance()
              .kbd_addButton()
              .btn_withText(Sex.MALE.getRussianNotation())
              .btn_withCallbackActionType(actionType)
              .btn_withCallbackPayload(Sex.MALE.name())
              .btn_deleteKeyboardWhenPressed()
              .btn_finish()
              .kbd_addButton()
              .btn_withText(Sex.FEMALE.getRussianNotation())
              .btn_withCallbackActionType(actionType)
              .btn_withCallbackPayload(Sex.FEMALE.name())
              .btn_deleteKeyboardWhenPressed()
              .btn_finish()
              .kbd_addButton()
              .btn_withText(Sex.UNDEFINED.getRussianNotation())
              .btn_withCallbackActionType(actionType)
              .btn_withCallbackPayload(Sex.UNDEFINED.name())
              .btn_deleteKeyboardWhenPressed()
              .btn_finish()
              .kbd_build());
    }
    return keybds.get(actionType);
  }

  @Override
  public InlineKeyboardMarkup getMeOrPrefKeyboard() {
    ActionType callbackAction = ActionType.USER_OR_PREF;
    if (!keybds.containsKey(callbackAction)) {
      keybds.put(callbackAction,
          InlineKeyboardBuilder.getInstance()
              .kbd_addButton()
              .btn_withText("Мои настройки")
              .btn_withCallbackActionType(callbackAction)
              .btn_withCallbackPayload("me")
              .btn_deleteKeyboardWhenPressed()
              .btn_finish()
              .kbd_addButton()
              .btn_withText("Мои предпочтения")
              .btn_withCallbackActionType(callbackAction)
              .btn_withCallbackPayload("pref")
              .btn_deleteKeyboardWhenPressed()
              .btn_finish()
              .kbd_build());
    }
    return keybds.get(ActionType.USER_OR_PREF);
  }

  @Override
  public InlineKeyboardMarkup getCommunicationTypeKeyboard() {
    ActionType callbackAction = ActionType.UPDATE_PREF_COMM_TYPE;
    if (!keybds.containsKey(callbackAction)) {
      List<InlineKeyboardButton> keyboardButtons = Arrays.stream(CommunicationType.values())
          .map(communicationType ->
              InlineKeyboardButton.builder()
                  .text(communicationType.getRussianNotation())
                  .callbackData(
                      DataUtils.callbackDataToJson(
                          new CallbackData(callbackAction, communicationType.name())))
                  .build())
          .collect(Collectors.toList());
      keybds
          .put(callbackAction, InlineKeyboardMarkup.builder().keyboardRow(keyboardButtons).build());
    }
    return keybds.get(callbackAction);
  }

  @Override
  public InlineKeyboardMarkup getMainMenuKeyboard() {
    final ActionType mainActionType = ActionType.GET_MAIN_MENU;
    if (!keybds.containsKey(mainActionType)) {
      keybds.put(mainActionType,
          InlineKeyboardBuilder.getInstance()
              .kbd_addButton()
              .btn_withText("Инфо обо мне")
              .btn_withCallbackActionType(ActionType.GET_MY_INFO)
              .btn_finish()
              .kbd_addButton()
              .btn_withText("Мои предпочтения")
              .btn_withCallbackActionType(ActionType.GET_PREF_INFO)
              .btn_finish()
              .nextRow()
              .kbd_addButton()
              .btn_withText("Изменить")
              .btn_withCallbackActionType(ActionType.USER_OR_PREF)
              .btn_withCallbackPayload("me")
              .btn_finish()
              .kbd_addButton()
              .btn_withText("Изменить")
              .btn_withCallbackActionType(ActionType.USER_OR_PREF)
              .btn_withCallbackPayload("pref")
              .btn_finish()
              .kbd_build());
    }
    return keybds.get(mainActionType);
  }
}
