package com.example.School_System.repositories;

import com.example.School_System.dto.rector.RectorModel;
import com.example.School_System.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface RectorRepository extends JpaRepository<User,Long> {

    @Query("SELECT new com.example.School_System.dto.rector.RectorModel(r.firstName,r.lastName,r.email,s.name) " +
            "FROM User r " +
            "JOIN School s ON s.rector = r " +
            "WHERE LOWER(r.firstName) LIKE CONCAT('%',:search,'%') OR " +
            "LOWER(r.lastName) LIKE CONCAT('%',:search,'%') OR " +
            "LOWER(r.email) LIKE CONCAT('%',:search,'%') OR " +
            "LOWER(s.name) LIKE CONCAT('%',:search,'%')")
    Page<RectorModel> findRectorsWithSearch(Pageable pageable, @Param("search") String search);
}
