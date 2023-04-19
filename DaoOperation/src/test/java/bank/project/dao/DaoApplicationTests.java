package bank.project.dao;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.test.context.junit4.SpringRunner;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
@RunWith(SpringRunner.class)
@SpringBootTest
class DaoApplicationTests {

    @Mock
    JdbcTemplate jdbcTemplate;

    @InjectMocks
    RoleService service;

    @Test
    public void testListAll() {
        Role R1 = new Role(100, "admin ", "admin is active", "Active", 100, "manvith", "manvith", 3);
        Role R2 = new Role(101, "admin", "admin is active ", "active", 101, "nidhi", "nidhi", 3);
        List<Role> tempList = Stream.of(R1,R2).collect(Collectors.toList());
        when(jdbcTemplate.query(eq("select * from role"), any(RowMapper.class))).thenReturn(tempList);
        assertEquals(tempList, service.listAllRole());
    }

   @Test
   public void testupdateLoan() {
       ProfileUpdate profile1 = new ProfileUpdate(120, "amrutha", "active", 12345678, "nidhi", "nidhi", 3, 2345678L, "87yut54", "active", "manipal");
       ProfileUpdate profile2 = new ProfileUpdate(121, "adithya", "deactive", 234579, "veena", "veena", 3, 10986432L, "345sdfghj", "pending", "manipal");
       String username = "amrutha";
       when(jdbcTemplate.update(eq("update customer set update_status='approved' where username=?"), eq(username))).thenReturn(1);
       assertEquals("Approved", service.listUpdateStatus(username));
   }
    @Test
    public void testLoan(){
        ProfileUpdate profile1=new ProfileUpdate(120,"amrutha","active",12345678,"nidhi","nidhi",3,2345678L,"87yut54","active","manipal");
        ProfileUpdate profile2=new ProfileUpdate(121,"adithya","deactive",234579,"veena","veena",3,10986432L,"345sdfghj","pending","manipal");
        List<ProfileUpdate> updatelist = Stream.of(profile1,profile2).collect(Collectors.toList());
        when(jdbcTemplate.query(eq("select * from customer where update_status='pending'"), any(RowMapper.class))).thenReturn(updatelist);
        assertEquals(updatelist,service.listProfileAll());
    }

//    @Test
//    public void testListAllLoans() {
//        LoanScheme l1 = new LoanScheme(4, "personal loan", "Assured personal loan", "taken for any personal problems", 0.09f);
//        LoanScheme l2 = new LoanScheme(6, "gold loan", "bhima gold", "to buy gold at less rate of interest", 0.30f);
//        LoanScheme l3 = new LoanScheme(2, "vehicle loan", "IOR loans", "gives good value", 0.44f);
//        List<LoanScheme> tempList = Stream.of(l1, l2, l3).collect(Collectors.toList());
//        when(jdbcTemplate.query(eq("select * from customer"), any(RowMapper.class))).thenReturn(tempList);
//        assertEquals(l3, bankService.listCustomer().get(2));
//
//
//    }
//    @Test
//    public void testGetUsername(){
//
//        Customer c1 = new Customer(1, "7477 ", "Manvith", "Udupi", "Inactive", "manvith", 878773435, 3);
//        String username="manvith";
//        when(jdbcTemplate.queryForObject(eq("select * from CUSTOMER where USERNAME=?"), any(RowMapper.class),eq(username)))
//                .thenReturn(c1);
//        Customer customer1=bankService.getByUsername("manvith");
//        assertEquals(c1.getUsername(),customer1.getUsername());

}

