package NoiThatGroup.Home.Configuration;

import NoiThatGroup.Home.Enity.Account;
import NoiThatGroup.Home.Enity.Role;
import NoiThatGroup.Home.Enity.Shopping;
import NoiThatGroup.Home.Enity.User;
import NoiThatGroup.Home.Repository.AccountRepository;
import NoiThatGroup.Home.Repository.RoleRepository;
import NoiThatGroup.Home.Repository.ShoppingRepository;
import NoiThatGroup.Home.Repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.Set;


@Configuration
@RequiredArgsConstructor
public class ApplicationInitConfig {

    @Bean
    ApplicationRunner applicationRunner(AccountRepository accountRepository, ShoppingRepository shoppingRepository, UserRepository userRepository, RoleRepository roleRepository) {
        return args -> {
            if (!accountRepository.existsByUsername("admin")) {
                PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
                var role = roleRepository.findById("ADMIN");
                Set<Role> set = new HashSet<>();
                set.add(role.get());

                Account account = Account.builder()
                        .username("admin")
                        .password(passwordEncoder.encode("admin"))
                        .roles(set)
                        .active(true)
                        .build();
                accountRepository.save(account);
            }

        };
    }


}
