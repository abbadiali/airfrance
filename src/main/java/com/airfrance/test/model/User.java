package com.airfrance.test.model;

import com.airfrance.test.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

/**
 * User Entity
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection="USERS")
public class User {
    @Id
    private String id;
    
    @NotBlank(message = "UserName should not be blank")
    @NotEmpty(message = "UserName should not be empty")
    @NotNull(message = "Username should not be null")
    private String username;

    @NotNull(message = "Birth Date should not be null")
    private LocalDate birthDate;

    private String phoneNumber;
    
    private Gender gender;
    
    @NotNull(message = "Country should not be null")
    private Country country;
    
}
