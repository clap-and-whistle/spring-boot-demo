package page.clapandwhistle.uam.controller.UserOperation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import page.clapandwhistle.uam.infrastructure.TableModel.UserAccountBase;
import page.clapandwhistle.uam.logics.UseCase.UserOperation.CreateAccount.Arguments;
import page.clapandwhistle.uam.logics.UseCase.UserOperation.CreateAccount.CreateAccountUseCase;
import page.clapandwhistle.uam.logics.UseCase.UserOperation.CreateAccount.Result;

@Controller
public class CreateAccountController {
    private final String URL_PATH_PREFIX = "user-account";
    private final String TEMPLATE_PATH_PREFIX = "user-account";

    final private CreateAccountUseCase useCase;

    @Autowired
    public CreateAccountController(CreateAccountUseCase useCase) {
        super();
        this.useCase = useCase;
    }

    @RequestMapping(URL_PATH_PREFIX + "/new")
    public String newAction(Model model) {
        System.out.println("user-account::new: ");
        model.addAttribute("UserAccountBase", new UserAccountBase("", ""));
        return TEMPLATE_PATH_PREFIX + "/new";
    }

    @PostMapping(URL_PATH_PREFIX)
    public String create(@ModelAttribute("UserAccountBase") @Validated UserAccountBase userAccount, BindingResult result, Model model) {
        System.out.println("user-account::create: email: " + userAccount.getEmail());
        System.out.println("user-account::create: password: " + userAccount.getPassword());
        if (result.hasErrors()) {
            System.out.println("user-account::create: BindingResult: " + result.toString());
            return TEMPLATE_PATH_PREFIX + "/new";
        } else {
            String ret = "redirect:/";
            try {
                Result useCaseResult = this.useCase.execute(new Arguments(userAccount.getEmail(), userAccount.getPassword()));
                if (!useCaseResult.isSuccess()) {
                    System.out.println("user-account::create: " + useCaseResult.eMessage());
                    ret = TEMPLATE_PATH_PREFIX + "/new";
                }
            } catch (Exception e) {
                System.out.println("user-account::create: " + e.getMessage());
                ret = "redirect:/e";
            }
            return ret;
        }
    }
}
