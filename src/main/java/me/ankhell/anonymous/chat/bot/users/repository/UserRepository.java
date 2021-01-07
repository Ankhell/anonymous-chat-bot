package me.ankhell.anonymous.chat.bot.users.repository;

import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.CrudRepository;
import me.ankhell.anonymous.chat.bot.users.entity.User;

@Repository
public interface UserRepository extends CrudRepository<User,Integer> {
}
