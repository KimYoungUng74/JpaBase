package hellojpa.domain;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class MemberDTO {

    private long id;
    private String username;
    private int age;

    public MemberDTO(String username, int age) {
        this.username = username;
        this.age = age;
    }
}
