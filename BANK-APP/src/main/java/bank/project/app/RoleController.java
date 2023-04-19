package bank.project.app;

import approval.project.soap.Customer;
import bank.project.dao.ProfileUpdate;
import bank.project.dao.Role;
import bank.project.dao.RoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.ResourceBundle;
@ComponentScan("bank.project.dao")
@RestController
@RequestMapping("/rest")
public class RoleController {
    @Autowired
    private RoleService service;
    private Logger logger= LoggerFactory.getLogger(RoleController.class);
    ResourceBundle resourceBundle=ResourceBundle.getBundle("role");

    @GetMapping("/get")
    public List<Role> callList(){
        logger.info("Controller about print All the records");
        return service.listAllRole();
    }

    @GetMapping("/gets")
    public List<ProfileUpdate>callprofileList(){
        List<ProfileUpdate> list_of_profile=service.listProfileAll();
        logger.info(list_of_profile.toString());
        return list_of_profile;
    }
//    @PostMapping("/approve")
//    public String approve(@RequestParam("username")String customername){
//        logger.info("approved");
//        return service.listUpdateStatus(customername);
//    }
    @PostMapping("/authenticate")
    public String login(@RequestParam("username") String username, @RequestParam("password") String password) {
         logger.info("Entered sample function");
        Role role = service.getByUsername(username);
        if (role == null){
            logger.info(resourceBundle.getString("db_user"));
            return resourceBundle.getString("db_user");
        }
        else {
            logger.info(role.getRolestatus());
            if (role.getRolestatus().equalsIgnoreCase("Inactive")){
                logger.info(resourceBundle.getString("db_unsuccessfull"));
                return resourceBundle.getString("db_unsuccessfull");
            }
            if (!password.equals(role.getPassword())) {
                service.incrementFailedAttempts(role.getRoleid());
                logger.info(resourceBundle.getString("db_incorrect_pw"));
                return resourceBundle.getString("db_incorrect_pw");
            }
            else {
                logger.info(resourceBundle.getString("db_success"));
                return resourceBundle.getString("db_success");
            }
        }
    }
}
