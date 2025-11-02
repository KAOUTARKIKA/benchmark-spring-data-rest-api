package com.tp.benchmarkspringdatarestapi.config;

import com.tp.benchmarkspringdatarestapi.entity.Category;
import com.tp.benchmarkspringdatarestapi.entity.Item;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

@Configuration
public class RestConfiguration implements RepositoryRestConfigurer {

    @Override
    public void configureRepositoryRestConfiguration(
            RepositoryRestConfiguration config,
            CorsRegistry cors) {

        // Expose les IDs dans les réponses JSON (par défaut, ils sont masqués)
        config.exposeIdsFor(Category.class, Item.class);

        // Retourner le corps dans les réponses POST et PUT
        config.setReturnBodyOnCreate(true);
        config.setReturnBodyOnUpdate(true);

        // Taille de page par défaut
        config.setDefaultPageSize(20);
        config.setMaxPageSize(100);
    }
}
