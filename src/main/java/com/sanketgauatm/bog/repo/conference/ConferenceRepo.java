package com.sanketgauatm.bog.repo.conference;

import com.sanketgauatm.bog.model.Conference;
import org.springframework.data.repository.ListCrudRepository;

public interface ConferenceRepo extends ListCrudRepository<Conference, Integer>, CustomRepo  {
}
