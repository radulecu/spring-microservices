package ro.rasel.security.client.ui;

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

    @RequestMapping(method = RequestMethod.GET, path = "/cucurigu")
    public Object testCucurigu() {
        return new Result("testAdmin");
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
