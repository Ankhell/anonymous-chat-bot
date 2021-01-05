package me.ankhell.anonymous.chat.bot.users;

import java.util.Optional;

public interface UserVault {

  Optional<User> getUser(int userId);

  void addOrUpdateUser(User user);

  void removeUser(int userId);

}
