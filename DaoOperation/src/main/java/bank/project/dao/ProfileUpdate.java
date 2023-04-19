package bank.project.dao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProfileUpdate {
    private Integer customerid;
    private String customername;
    private String customerstatus;
    private long customercontact;
    private String username;
    private String password;
    private int failedattempts;
    private Long customeraadhaar;
    private String customerpan;
    private String updatestatus;
    private String customeraddress;
}
