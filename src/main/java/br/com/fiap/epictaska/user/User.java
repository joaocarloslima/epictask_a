package br.com.fiap.epictaska.user;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.List;
import java.util.Map;

@Entity
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@Table(name = "epicuser")
public class User extends DefaultOAuth2User {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(unique = true)
    private String email;

    private String avatarUrl;

    public User(OAuth2User principal){
        super(
                List.of(new SimpleGrantedAuthority("USER")),
                principal.getAttributes(),
                "name"
        );
        this.name = principal.getAttribute("name");
        this.email = principal.getAttribute("email");
        this.avatarUrl = principal.getAttribute("picture") != null ?
                            principal.getAttribute("picture").toString() :
                            principal.getAttribute("avatar_url").toString();

    }

    public User(){
        super(
                List.of(new SimpleGrantedAuthority("USER")),
                Map.of("name", "unknown"),
                "name"
        );
    }


}
