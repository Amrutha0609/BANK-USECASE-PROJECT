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


}


