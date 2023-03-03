package ar.com.dinamicaonline.credixsa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ar.com.dinamicaonline.credixsa.models.Entidad;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

@Repository
public interface EntidadRepository extends JpaRepository<Entidad, Integer> {

    @Query(value = "SELECT ENTIDAD_ID FROM entidades WHERE DOCUMENTO = :customerId LIMIT 1", nativeQuery = true)
    public Long findByDocumentId(@Param("customerId") String customerId);

}
