package ro.rasel.service.bookmarks.controler;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
class ClientRestController {

    @RequestMapping("/hi")
    String hi(@PathVariable String userId) {
        return "hi";
    }

}
