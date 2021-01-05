package me.ankhell.anonymous.chat.bot.users;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import javax.inject.Singleton;

@Singleton
public class UserVaultImpl implements UserVault {

  private final Map<Integer, User> users = new HashMap<>();

  @Override
  public Optional<User> getUser(int userId) {
    if (users.containsKey(userId)) {
      return Optional.of(users.get(userId));
    } else {
      return Optional.empty();
    }
  }

  @Override
  public void addOrUpdateUser(User user) {
    users.put(user.getId(), user);
  }

  @Override
  public void removeUser(int userId) {
    users.remove(userId);
  }
}
