package br.com.testsusecase.tests.domain;

import java.io.Serializable;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name="users")
public class User implements Serializable{

  private Long id;
  private String name;
  @Column(unique = true)
  private String email;
  private String password;
  @Column(name="created_at")
  private LocalDateTime createdAt;
  @Column(name="updated_at")
  private LocalDateTime updatedAt;
  
}
