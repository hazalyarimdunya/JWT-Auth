package com.hazalyarimdunya.auth_jwt_app.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

@Entity(name = "Todos")
@Table(name = "todo")
public class TodoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;

    @Column(name = "title",nullable = false)
    private String title;

    @Column(name = "description",nullable = false)
    private String description;

    @Column(name = "Created_Date")
    @CreationTimestamp //otomatik olarak tarih ve zamanını atar.
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity userEntity;   // Token'dan gelen user ile ilişkilendirilecek
}
