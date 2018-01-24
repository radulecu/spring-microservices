package ro.rasel.security.client.ui;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class RestExample {
    @RequestMapping(method = RequestMethod.GET)
    public Object test() {
        return new Result("test");
    }

    @RequestMapping(method = RequestMethod.GET, path = "/1")
    public Object test1() {
        return new Result("test1");
    }

    @RequestMapping(method = RequestMethod.GET, path = "/2")
    public Object test2() {
        return new Result("test2");
    }

    @RequestMapping(method = RequestMethod.GET, path = "/admin")
    public Object testAdmin() {
        return new Result("testAdmin");
    }

    @RequestMapping(method = RequestMethod.GET, path = "/user")
    public Object testUser() {
        return new Result("testUser");
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(method = RequestMethod.GET, path = "/admin2")
    public Object testAdmin2() {
        return new Result("testAdmin");
    }

    @PreAuthorize("hasRole('INEXISTENT_ROLE_USER')")
    @RequestMapping(method = RequestMethod.GET, path = "/user2")
    public Object testUser2() {
        return new Result("testUser");
    }

    private static class Result {
        private String message;

        Result(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }

        public String getMessage2() {
            return message;
        }
    }

}
