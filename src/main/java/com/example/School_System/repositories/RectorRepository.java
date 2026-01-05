package com.example.School_System.repositories;

import com.example.School_System.dto.rector.RectorModel;
import com.example.School_System.entities.User;
import org.hibernate.query.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;

@Repository
public interface RectorRepository extends JpaRepository<User,Long> {

    @Query("SELECT new com.example.School_System.dto.rector(r.firstName,r.lastName,r.email,r.s) " +
            "FROM User r")
    public Page<RectorModel> findRectorsWithSearch(Pageable pageable, @Param("search") String search);
}
