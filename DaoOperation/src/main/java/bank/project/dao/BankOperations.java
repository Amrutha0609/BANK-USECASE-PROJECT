package bank.project.dao;

import java.util.List;
import java.util.Optional;

public interface BankOperations {
    List<Role> listAllRole();
    List<Role> readByUserNames(String name);
//  void decrementAttempts(int id);
//  void setAttempts(int id);
    void updateStatus();
    int getAttempts(int id);
    public Optional<Role> userlogin(String uname);
    Role getByUsername(String username);
    void incrementFailedAttempts(int id);
    List<ProfileUpdate> listProfileAll();
   // String authenticate(String username,String password);
   String UpdateStatus(String username);
}
