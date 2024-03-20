package com.reservation.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="users")
public class User {


    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
         private long id;

         private String name;

         private String email;

         private String password;
         @Lob
         @Column(name = "profilePicture",length = 20000)
         private byte[] profilePicture;

         private String profilePictureName;






}
