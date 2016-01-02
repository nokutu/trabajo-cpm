package homework.models;

import homework.Main;
import org.jasypt.util.password.StrongPasswordEncryptor;

/**
 * Created by nokutu on 02/01/2016.
 */
public class User {

  private static User loggedUser;
  private static boolean isLogged;

  private String username;
  private String passwordHash;
  private String tlfNumber;
  private String address;
  private String nif;
  private String email;

  public User(String username, String passwordHash, String tlfNumber, String address, String nif, String email) {
    setUsername(username);
    setPasswordHash(passwordHash);
    setTlfNumber(tlfNumber);
    setAddress(address);
    setNif(nif);
    setEmail(email);
  }

  public static void register(String username, String password, String tlfNumber, String address, String nif, String email) {
    StrongPasswordEncryptor passwordEncryptor = new StrongPasswordEncryptor();
    User user = new User(username, passwordEncryptor.encryptPassword(password), tlfNumber, address, nif, email);
    if (Main.db.getUsers().contains(user)) {
      throw new IllegalArgumentException("User already registered");
    }
    Main.db.getUsers().add(user);
    // TODO: add user to config
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPasswordHash() {
    return passwordHash;
  }

  public void setPasswordHash(String passwordHash) {
    this.passwordHash = passwordHash;
  }

  public String getTlfNumber() {
    return tlfNumber;
  }

  public void setTlfNumber(String tlfNumber) {
    this.tlfNumber = tlfNumber;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public String getNif() {
    return nif;
  }

  public void setNif(String nif) {
    this.nif = nif;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  @Override
  public int hashCode() {
    return getUsername().hashCode();
  }

  @Override
  public boolean equals(Object obj) {
    if (obj instanceof User) {
      return ((User) obj).getUsername().equals(getUsername());
    }
    return false;
  }

  @Override
  public String toString() {
    return getUsername();
  }


  public static boolean isLogged() {
    return isLogged;
  }

  public static void setLoggedUser(User user) {
    loggedUser = user;
    isLogged = true;
  }

  public static User getLoggedUser() {
    return loggedUser;
  }
}
