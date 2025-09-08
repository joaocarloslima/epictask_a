package br.com.fiap.epictaska.user;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class UserService  {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User register(OAuth2User principal) {
        log.info(principal.getAttributes().get("email") + "");
        var optionalUser = userRepository.findByEmail(principal.getAttributes().get("email").toString());

        if(optionalUser.isEmpty()){
            return userRepository.save(new User(principal));
        }

        return optionalUser.get();

    }


    public void addScore(User user, int score) {
        user.setScore(user.getScore() + score);
        userRepository.save(user);
    }

    public List<User> getRanking() {
        return userRepository.findByOrderByScoreDesc();
    }
}
