package org.accountservice.server.web;

import org.accountservice.server.model.Statistics;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Pavel Karpukhin
 */
@Controller
public class StatisticsController implements ApplicationContextAware {

    public static final String STATISTICS = "STATISTICS";

    private WebApplicationContext context;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = (WebApplicationContext)applicationContext;
    }

    @RequestMapping(value = "/stat.html", method = RequestMethod.GET)
    public ModelAndView statistics(HttpServletRequest request) {
        Statistics stat = (Statistics)context.getServletContext().getAttribute(STATISTICS);
        long perSecond = stat.getCountPerSecond();
        long perMinute = stat.getCountPerMinute();
        return new ModelAndView("stat")
                .addObject("perSecond", perSecond)
                .addObject("perMinute", perMinute);
    }

    @RequestMapping(value = "/reset.html", method = RequestMethod.POST)
    public ModelAndView reset() {
        Statistics stat = (Statistics)context.getServletContext().getAttribute(STATISTICS);
        stat.reset();
        return new ModelAndView("redirect:/stat.html");
    }
}
