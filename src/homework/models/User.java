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
  private String id;
  private String email;
  private String fullName;

  public User(String username, String passwordHash, String fullName, String tlfNumber, String address, String nif, String email) {
    setUsername(username);
    setPasswordHash(passwordHash);
    setTlfNumber(tlfNumber);
    setAddress(address);
    setId(nif);
    setEmail(email);
    setFullName(fullName);
  }

  public static void register(String username, String password, String fullName, String tlfNumber, String address, String nif, String email) {
    StrongPasswordEncryptor passwordEncryptor = new StrongPasswordEncryptor();
    User user = new User(username, passwordEncryptor.encryptPassword(password), fullName, tlfNumber, address, nif, email);
    if (Main.db.getUsers().contains(user)) {
      throw new IllegalArgumentException("User already registered");
    }
    Main.db.getUsers().add(user);
    user.writeToFile();
  }

  public void writeToFile() {
    Main.prefs.put("user." + getUsername() + ".password", getPasswordHash());
    Main.prefs.put("user." + getUsername() + ".fullname", getFullName());
    Main.prefs.put("user." + getUsername() + ".tlf", getTlfNumber());
    Main.prefs.put("user." + getUsername() + ".address", getAddress());
    Main.prefs.put("user." + getUsername() + ".id", getId());
    Main.prefs.put("user." + getUsername() + ".email", getEmail());
    Main.prefs.put("users", Main.prefs.get("users", "") + "%" + getUsername());
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

  public String getId() {
    return id;
  }

  public void setId(String nif) {
    this.id = nif;
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
    return obj instanceof User && ((User) obj).getUsername().equals(getUsername());
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
    Main.frame.refreshNavbars();
  }

  public static User getLoggedUser() {
    return loggedUser;
  }

  public static void logout() {
    setLoggedUser(null);
    isLogged = false;
  }

  public String getFullName() {
    return fullName;
  }

  public void setFullName(String fullName) {
    this.fullName = fullName;
  }
}
