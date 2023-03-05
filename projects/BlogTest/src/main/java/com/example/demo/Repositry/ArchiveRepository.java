package com.example.demo.Repositry;



import com.example.demo.model.Archive;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArchiveRepository extends JpaRepository<Archive,Integer> {
}
