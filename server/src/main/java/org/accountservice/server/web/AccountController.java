package org.accountservice.server.web;

import org.accountservice.server.service.AccountService;
import org.accountservice.server.util.ActionResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * @author Pavel Karpukhin
 */
@Controller
public class AccountController {

    private AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @RequestMapping(value = "/account/get/{id:\\d+}", method = RequestMethod.GET)
    @ResponseBody
    public ActionResult<Long> getBalance(@PathVariable("id") Integer id) {
        try {
            return ActionResult.succeed(accountService.getAmount(id));
        } catch (Exception e) {
            return ActionResult.failed(e.getMessage());
        }
    }

    @RequestMapping(value = "/account/add/{id:\\d+}", method = RequestMethod.POST)
    @ResponseBody
    public ActionResult addAmount(@PathVariable("id") Integer id, @RequestBody Long value) {
        try {
            accountService.addAmount(id, value);
            return ActionResult.succeed();
        } catch (Exception e) {
            return ActionResult.failed(e.getMessage());
        }
    }
}
