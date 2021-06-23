package page.clapandwhistle.uam.framework.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import page.clapandwhistle.uam.infrastructure.TableModel.UserAccountBase;
import page.clapandwhistle.uam.infrastructure.TableModel.UserAccountBaseRepository;

import java.util.List;

@Service
public class AuthUserDetailsService implements UserDetailsService {

    private final UserAccountBaseRepository userDao;

    @Autowired
    public AuthUserDetailsService(UserAccountBaseRepository userDao) {
        this.userDao = userDao;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        if (email == null || "".equals(email)) {
            System.out.println("AuthUserDetailsService::loadUserByUsername: 認証情報の入力がありません");
            throw new UsernameNotFoundException("");
        }

        try {
            List<UserAccountBase> result = userDao.findByEmail(email);
            if (result.size() > 0) {
                return new AuthUserDetails(result.get(0));
            } else {
                System.out.println("AuthUserDetailsService::loadUserByUsername: " + email + " を使用するユーザが見つかりません");
                throw new UsernameNotFoundException("");
            }
        } catch (EmptyResultDataAccessException e) {
            System.out.println("AuthUserDetailsService::loadUserByUsername: 不明なエラー");
            throw new UsernameNotFoundException("");
        }
    }

}
