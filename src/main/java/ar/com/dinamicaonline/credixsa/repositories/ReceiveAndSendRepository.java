package ar.com.dinamicaonline.credixsa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ar.com.dinamicaonline.credixsa.models.ReceiveAndSend;

@Repository
public interface ReceiveAndSendRepository extends JpaRepository<ReceiveAndSend, Integer> {

}