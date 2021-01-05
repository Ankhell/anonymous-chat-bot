package me.ankhell.anonymous.chat.bot.callback;

import lombok.Data;

@Data
public class CallbackData {

  private final CallbackActionType actionType;
  private final String payload;

}
