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

//    test cases
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
       ProfileUpdate profile2 = new ProfileUpdate(121, "abhishik", "pending", 234579, "veena", "veena", 3, 10986432L, "345sdfghj", "pending", "manipal");
       String username = "amrutha";
       when(jdbcTemplate.update(eq("update customer set update_status='approved', timestamp_customer=LOCALTIMESTAMP(2) where username=?"), eq(username))).thenReturn(1);
       assertEquals("Approved", service.UpdateStatus(username));
       assertNotEquals("pending",service.UpdateStatus(username));
   }
    @Test
    public void testProfile(){
        ProfileUpdate profile1=new ProfileUpdate(120,"amrutha","active",12345678,"nidhi","nidhi",3,2345678L,"87yut54","active","manipal");
        ProfileUpdate profile2=new ProfileUpdate(121,"adithya","deactive",234579,"veena","veena",3,10986432L,"345sdfghj","pending","manipal");
        List<ProfileUpdate> updatelist = Stream.of(profile1,profile2).collect(Collectors.toList());
        when(jdbcTemplate.query(eq("select * from customer where update_status='pending'"), any(RowMapper.class))).thenReturn(updatelist);
        assertEquals(updatelist,service.listProfileAll());
        assertNotEquals(profile1,service.listProfileAll().get(1));
    }





}

