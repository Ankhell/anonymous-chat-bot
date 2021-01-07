package me.ankhell.anonymous.chat.bot.users.entity;

import java.util.StringJoiner;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "users")
public class User {

  @Id
  private final Integer id;
  @Column(name = "gender")
  private Sex sex;
  @Column(name = "age")
  private Integer age;
  @Column(name = "preffered_sex")
  private Sex prefferedSex;
  @Column(name = "preffered_min_age")
  private Integer prefferedMinAge;
  @Column(name = "preffered_max_age")
  private Integer prefferedMaxAge;
  @Column(name = "preffered_communication_type")
  private CommunicationType prefferedCommunicationType;

  public User(Integer id) {
    this.id = id;
  }

  public User() {
    id = 0;
  }

  public Integer getId() {
    return id;
  }

  public Sex getSex() {
    return sex;
  }

  public void setSex(Sex sex) {
    this.sex = sex;
  }

  public Integer getAge() {
    return age;
  }

  public void setAge(Integer age) {
    this.age = age;
  }

  public Sex getPrefferedSex() {
    return prefferedSex;
  }

  public void setPrefferedSex(Sex prefferedSex) {
    this.prefferedSex = prefferedSex;
  }

  public Integer getPrefferedMinAge() {
    return prefferedMinAge;
  }

  public void setPrefferedMinAge(Integer prefferedMinAge) {
    this.prefferedMinAge = prefferedMinAge;
  }

  public Integer getPrefferedMaxAge() {
    return prefferedMaxAge;
  }

  public void setPrefferedMaxAge(Integer prefferedMaxAge) {
    this.prefferedMaxAge = prefferedMaxAge;
  }

  public CommunicationType getPrefferedCommunicationType() {
    return prefferedCommunicationType;
  }

  public void setPrefferedCommunicationType(
      CommunicationType prefferedCommunicationType) {
    this.prefferedCommunicationType = prefferedCommunicationType;
  }

  @Override
  public String toString() {
    return new StringJoiner(", ", User.class.getSimpleName() + "[", "]")
        .add("id=" + id)
        .add("sex=" + sex)
        .add("age=" + age)
        .add("prefferedSex=" + prefferedSex)
        .add("prefferedMinAge=" + prefferedMinAge)
        .add("prefferedMaxAge=" + prefferedMaxAge)
        .add("prefferedCommunicationType=" + prefferedCommunicationType)
        .toString();
  }

}
