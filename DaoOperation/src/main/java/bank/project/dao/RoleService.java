package bank.project.dao;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

@Service
    public class RoleService implements BankOperations , UserDetailsService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    Logger logger = LoggerFactory.getLogger(RoleService.class);
    ResourceBundle resourceBundle = ResourceBundle.getBundle("role");

//listing the profile of the customer where profile status of the customer's are active
    @Override
    public List<ProfileUpdate> listProfileAll() {
        logger.info(jdbcTemplate.query("select * from profile,customer where customer.customer_id=profile.customer_id and profile_status='active'", new ProfileUpdateMapper()).toString());
        return jdbcTemplate.query("select * from customer where update_status='pending'", new ProfileUpdateMapper());
    }

    public void resetAttempts(int id){
        jdbcTemplate.update("update Role set failed_attempts=3 where role_id=?",id);
        logger.info("set attempt to 3");
    }

 //   updating the status of the customer from pending to approved
    @Override
    public String UpdateStatus(String username) {
        logger.info("username");
        jdbcTemplate.update("update customer set update_status='approved' where username=?", username);
        return "Approved";
    }

// if the username that we specified is not present in the database.
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Role role = getByUsername(username);
        if (role == null) {
            throw new UsernameNotFoundException(resourceBundle.getString("user not found"));
        }
        return role;
    }

    //getting the number of attempts
    @Override
    public int getAttempts(int id) {
        return jdbcTemplate.update("select FAILED_ATTEMPTS from ROLE where ROLE_ID=?", id);
    }


    public void decrementAttempts(int id) {
        jdbcTemplate.update("update ROLE set FAILED_ATTEMPTS = FAILED_ATTEMPTS - 1 where ROLE_ID_ID=?",id);
        logger.info("Decreased the number of attempts");
        updateStatus();

    }

    @Override
    public void updateStatus() {
        jdbcTemplate.update("update ROLE set ROLE_STATUS='Inactive' where Failed_ATTEMPTS=0");
        logger.info("Status set to inactive");
    }

//listing all the roles.
    @Override
    public List<Role> listAllRole() {
        logger.info("listing all roles of the bank");
        return jdbcTemplate.query("select * from role", new RoleMapper());
    }

    @Override
    public List<Role> readByUserNames(String name) {
        logger.info("selecting all from when the uname is given");
        return jdbcTemplate.query("select * from role where username=?", new Object[]{name}, new RoleMapper());
    }

    @Override
    public Optional<Role> userlogin(String uname) {
        return Optional.empty();
    }


    @Override
    public Role getByUsername(String username) {
        try {
            Role role = jdbcTemplate.queryForObject("select * from Role where USERNAME=?", new RoleMapper(), username);
            return role;
        } catch (DataAccessException e) {
            return null;
        }
    }
//incrementing the failed attempts
    @Override
    public void incrementFailedAttempts(int id) {
        jdbcTemplate.update("update ROLE set FAILED_ATTEMPTS = FAILED_ATTEMPTS + 1 where ROLE_ID=?", id);
        jdbcTemplate.update("update ROLE set ROLE_STATUS='Inactive' where FAILED_ATTEMPTS=3");
    }

//Rowmapper for roles
    class RoleMapper implements RowMapper<Role> {
        @Override
        public Role mapRow(ResultSet rs, int rowNum) throws SQLException {
            Role role = new Role();
            role.setRoleid(rs.getInt("role_id"));
            role.setRolename(rs.getString("role_name"));
            role.setRoledesc(rs.getString("role_desc"));
            role.setRolestatus(rs.getString("role_status"));
            role.setBranchid(rs.getInt("branch_id"));
            role.setUsername(rs.getString("username"));
            role.setPassword(rs.getString("password"));
            role.setFailedattempts(rs.getInt("failed_attempts"));
            //   logger.info("Ready to be viewed");
            return role;

        }
    }
//    row mapper for profileupdate
    class ProfileUpdateMapper implements RowMapper<ProfileUpdate> {
        @Override
        public ProfileUpdate mapRow(ResultSet rs, int rowNum) throws SQLException {
            ProfileUpdate profileUpdate = new ProfileUpdate();
            profileUpdate.setCustomerid(rs.getInt("customer_id"));
            profileUpdate.setCustomername(rs.getString("customer_name"));
            profileUpdate.setCustomeraddress(rs.getString("customer_address"));
            profileUpdate.setCustomerstatus(rs.getString("customer_status"));
            profileUpdate.setCustomercontact(rs.getLong("customer_contact"));
            profileUpdate.setUsername(rs.getString("username"));
            profileUpdate.setPassword(rs.getString("password"));
            profileUpdate.setFailedattempts(rs.getInt("failed_attempts"));
            profileUpdate.setCustomeraadhaar(rs.getLong("customer_aadhaar"));
            profileUpdate.setCustomerpan(rs.getString("customer_pan"));
            profileUpdate.setUpdatestatus(rs.getString("update_status"));
            logger.info("Ready to be viewed");
            return profileUpdate;
        }
    }
}


