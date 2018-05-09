package cz.muni.fi.hrm.rest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * this controller control url so in urls are not characters like # or !#
 */
@Controller
public class ForwardController {

    @RequestMapping(value = "/**/{[path:[^\\.]*}")
    public String redirect() {
        // Forward to page so that route is preserved.
        return "forward:/";
    }
}