package com.example.libraryservice.repo;

import com.example.libraryservice.domain.BookEntity;
import com.example.libraryservice.repo.BookJpaRepository;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface BookJpaRepository extends JpaRepository<BookEntity, Long> {

    Optional<BookEntity> findByName(String name);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT b FROM BookEntity b WHERE b.bookId = :id")
    Optional<BookEntity> lockById(@Param("id") Long id);
}
