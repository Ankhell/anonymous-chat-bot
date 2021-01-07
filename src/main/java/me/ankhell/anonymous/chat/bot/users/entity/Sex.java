package me.ankhell.anonymous.chat.bot.users.entity;

public enum Sex {
  MALE("Мужской"),
  FEMALE("Женский"),
  UNDEFINED("Не важно");

  private final String russianNotation;

  Sex(String russianNotation) {
    this.russianNotation = russianNotation;
  }

  public String getRussianNotation() {
    return russianNotation;
  }
}
