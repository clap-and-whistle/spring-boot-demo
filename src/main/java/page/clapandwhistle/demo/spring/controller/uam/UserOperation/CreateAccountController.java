package page.clapandwhistle.demo.spring.controller.uam.UserOperation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import page.clapandwhistle.demo.spring.bizlogic.uam.Aggregate.UserAggregateRepositoryInterface;
import page.clapandwhistle.demo.spring.bizlogic.uam.UseCase.UserOperation.CreateAccount.Arguments;
import page.clapandwhistle.demo.spring.bizlogic.uam.UseCase.UserOperation.CreateAccount.CreateAccountUseCase;
import page.clapandwhistle.demo.spring.bizlogic.uam.UseCase.UserOperation.CreateAccount.Result;
import page.clapandwhistle.demo.spring.controller.open.HomeController;
import page.clapandwhistle.demo.spring.infrastructure.uam.TableModel.UserAccountBase;

@Controller
public class CreateAccountController {
    public static final String URL_PATH_PREFIX = "user-account";
    public static final String URL_PATH_CREATE = "/new";

    final private CreateAccountUseCase useCase;

    @Autowired
    public CreateAccountController(UserAggregateRepositoryInterface userRepos) {
        super();
        this.useCase = new CreateAccountUseCase(userRepos);
    }

    @RequestMapping(URL_PATH_PREFIX + URL_PATH_CREATE)
    public String newAction(Model model) {
        System.out.println("user-account::new: ");
        model.addAttribute("formAction", "/" + URL_PATH_PREFIX);
        model.addAttribute("UserAccountBase", new UserAccountBase("", ""));
        return "uam/user-operation/create-account/new";
    }

    @PostMapping(URL_PATH_PREFIX)
    public String create(@ModelAttribute("UserAccountBase") @Validated UserAccountBase userAccount, BindingResult result) {
        System.out.println("user-account::create: email: " + userAccount.getEmail());
        System.out.println("user-account::create: password: " + userAccount.getPassword());
        if (result.hasErrors()) {
            System.out.println("user-account::create: BindingResult: " + result);
            return "redirect:/" + URL_PATH_PREFIX + URL_PATH_CREATE;
        } else {
            String ret = "redirect:/";
            try {
                Result useCaseResult = this.useCase.execute(new Arguments(userAccount.getEmail(), userAccount.getPassword()));
                if (!useCaseResult.isSuccess()) {
                    System.out.println("user-account::create: " + useCaseResult.eMessage());
                    ret = "redirect:/" + URL_PATH_PREFIX + URL_PATH_CREATE;
                }
            } catch (Exception e) {
                System.out.println("user-account::create: " + e.getMessage());
                ret = "redirect:" + HomeController.URL_PATH_UNIFIED_ERROR;
            }
            return ret;
        }
    }
}
