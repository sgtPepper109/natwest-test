package natwest.assignment.user.user_service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.Map;

@Service
public class UserService {

    @Autowired
    private RestTemplate restTemplate;
    private static final org.apache.logging.log4j.Logger log = org.apache.logging.log4j.LogManager.getLogger(UserService.class);

    public UserService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    Map<String, String> defeatable = Map.of(
            "Rock", "Paper",
            "Paper", "Scissors",
            "Scissors", "Rock"
    );

    public String play(String moveByUser) {
        String moveByComputer = this.restTemplate.getForObject("http://computer-service/computer/getMoveByComputer", String.class);
        log.info("INFO: moveByUser: {}", moveByUser);
        log.info("INFO: moveByComputer: {}", moveByComputer);

        if (moveByComputer != null) {
            if (moveByComputer.equals(this.defeatable.get(moveByUser)))
                return "Computer wins";
            else {
                if (moveByComputer.equals(moveByUser))
                    return "It is a tie";
                else
                    return "Player wins";
            }
        } else {
            return "Error";
        }
    }

}
