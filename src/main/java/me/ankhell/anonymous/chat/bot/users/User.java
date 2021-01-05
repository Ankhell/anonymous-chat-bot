package me.ankhell.anonymous.chat.bot.users;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class User {

  private final Integer id;
  private Sex sex;
  private Integer age;

  public enum Sex{
    MALE,
    FEMALE,
    ;
  }

}
