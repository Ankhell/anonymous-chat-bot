package me.ankhell.anonymous.chat.bot.users.entity;

public enum CommunicationType {
  PLAIN("Общение"),
  ADULT("Флирт"),
  ;

  private final String russianNotation;

  CommunicationType(String russianNotation) {
    this.russianNotation = russianNotation;
  }

  public String getRussianNotation() {
    return russianNotation;
  }
}
