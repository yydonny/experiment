package yd.model;

import javax.security.auth.Subject;
import java.security.Principal;
import java.util.HashSet;
import java.util.Set;

public class User implements Principal {
  private String name;
  private final Set<User> friends = new HashSet<>();


  public User(String name) {
    this.name = name;
  }

  public void addFriend(User u) {
    friends.add(u);
  }

  public void removeFriend(User u) {
    friends.remove(u);
  }

  public Set<User> getFriends() {
    return new HashSet<>(friends);
  }

  @Override
  public String getName() { return name; }
  @Override
  public boolean implies(Subject subject) {
    return false;
  }
}
