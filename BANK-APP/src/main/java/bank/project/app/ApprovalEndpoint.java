package bank.project.app;
import approval.project.soap.ApproveRequest;
import approval.project.soap.ApproveResponse;
import approval.project.soap.ServiceStatus;
import bank.project.dao.RoleService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Endpoint
public class ApprovalEndpoint {
    private static final String url = "http://soap.project.approval";

    @Autowired
    RoleService service;

    @PayloadRoot(namespace = url, localPart = "approveRequest")
    @ResponsePayload
    public ApproveResponse approveResponse(@RequestPayload ApproveRequest approveRequest) {
        ApproveResponse response = new ApproveResponse();
        ServiceStatus serviceStatus = new ServiceStatus();
        String str = service.UpdateStatus(approveRequest.getUsername());
        System.out.println("Message "+str);
        serviceStatus.setMessage(str);
        serviceStatus.setStatus("Success");
        response.setMessage(serviceStatus);
        return response;
    }
}
