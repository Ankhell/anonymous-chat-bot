package me.ankhell.anonymous.chat.bot.messages.templates;

import java.util.Optional;
import javax.inject.Singleton;
import me.ankhell.anonymous.chat.bot.ActionType;
import me.ankhell.anonymous.chat.bot.keyboards.KeyboardProvider;
import me.ankhell.anonymous.chat.bot.users.entity.User;
import me.ankhell.anonymous.chat.bot.users.repository.UserRepository;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage.SendMessageBuilder;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ForceReplyKeyboard;

@Singleton
public class MessageProviderImpl implements
    MessageProvider {

  private final KeyboardProvider keyboardProvider;
  private final UserRepository userRepository;

  public MessageProviderImpl(
      KeyboardProvider keyboardProvider,
      UserRepository userRepository) {
    this.keyboardProvider = keyboardProvider;
    this.userRepository = userRepository;
  }

  @Override
  public SendMessage getAgeQueryMessage(Integer chatId, ActionType actionType) {
    boolean updateMe = actionType == ActionType.UPDATE_SEX_ME;
    boolean minAge = actionType == ActionType.UDATE_PREF_MIN_AGE;
    return SendMessage.builder()
        .text(String.format("Пожалуйста, укажите %s возраст%s",
            updateMe ? "Ваш" : "желаемый " + (minAge ? "минимальный" : "максимальный"),
            updateMe ? "" : " собеседника"))
        .chatId(chatId.toString())
        .replyMarkup(ForceReplyKeyboard.builder().forceReply(true).build())
        .build();
  }

  @Override
  public SendMessage getGenderQueryMessage(Integer chatId, ActionType actionType) {
    if (actionType != ActionType.UPDATE_SEX_ME
        && actionType != ActionType.UPDATE_SEX_PREF) {
      throw new IllegalStateException(
          "This method is intended to work with UPDATE_SEX_ME or UPDATE_SEX_PREF actions only");
    }
    boolean updateMe = actionType == ActionType.UPDATE_SEX_ME;
    return SendMessage.builder()
        .text(String.format("Пожалуйста, укажите %s пол%s",
            updateMe ? "Ваш" : "желаемый",
            updateMe ? "" : " собеседника"))
        .chatId(chatId.toString())
        .replyMarkup(keyboardProvider.getSexChooserKeyboard(actionType))
        .build();
  }

  @Override
  public SendMessage getMeOrPrefMessage(Integer chatId) {
    return SendMessage.builder()
        .text("Пожалуйста выберите действие")
        .chatId(chatId.toString())
        .replyMarkup(keyboardProvider.getMainMenuKeyboard())
        .build();
  }

  @Override
  public SendMessage getAgeSetMessage(Integer chatId) {
    return SendMessage.builder()
        .chatId(chatId.toString())
        .text("Возраст установлен")
        .build();
  }

  @Override
  public SendMessage getWrongAgeMessage(Integer chatId, Integer originalMessageId) {
    return SendMessage.builder()
        .text("Неверный возраст")
        .chatId(chatId.toString())
        .replyToMessageId(originalMessageId)
        .build();
  }

  @Override
  public SendMessage getPrefComTypeMessage(Integer chatId) {
    return SendMessage.builder()
        .chatId(chatId.toString())
        .text("Пожалуйста выберите желаемый тип общения")
        .replyMarkup(keyboardProvider.getCommunicationTypeKeyboard()).build();
  }

  @Override
  public SendMessage getUserInfoMessage(Integer chatId) {
    Optional<User> optUser = userRepository.findById(chatId);
    SendMessageBuilder messageBuilder = SendMessage.builder().chatId(chatId.toString());
    if (optUser.isPresent()) {
      User user = optUser.get();
      messageBuilder.text(
          String
              .format("Возраст: %d%nПол: %s%n", user.getAge(), user.getSex().getRussianNotation()));
    } else {
      messageBuilder.text("Пользователь не найден!");
    }
    return messageBuilder.build();
  }

  @Override
  public SendMessage getUserPreferencesMessage(Integer chatId) {
    Optional<User> optUser = userRepository.findById(chatId);
    SendMessageBuilder messageBuilder = SendMessage.builder().chatId(chatId.toString());
    if (optUser.isPresent()) {
      User user = optUser.get();
      messageBuilder.text(
          String.format(
              "Минимальный возраст собеседника: %d%n"
                  + "Максимальный возраст собеседника: %d%n"
                  + "Пол собеседника: %s%n"
                  + "Цель общения: %s%n",
              user.getPrefferedMinAge(),
              user.getPrefferedMaxAge(),
              user.getPrefferedSex().getRussianNotation(),
              user.getPrefferedCommunicationType().getRussianNotation()));
    } else {
      messageBuilder.text("Пользователь не найден!");
    }
    return messageBuilder.build();
  }
}
