package com.parkhomenko.common.domain;

import com.fasterxml.jackson.annotation.JsonView;
import com.parkhomenko.common.domain.util.View;
import org.apache.lucene.analysis.core.KeywordTokenizerFactory;
import org.apache.lucene.analysis.core.LowerCaseFilterFactory;
import org.apache.lucene.analysis.standard.ClassicTokenizerFactory;
import org.apache.lucene.analysis.standard.StandardTokenizerFactory;
import org.hibernate.search.annotations.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * @author Dmytro Parkhomenko
 * Created on 22.07.16.
 */

@Indexed
@AnalyzerDefs({
        @AnalyzerDef(
                name = "name_login_analyzer",
                tokenizer = @TokenizerDef(factory = StandardTokenizerFactory.class),
                filters = {
                        @TokenFilterDef(factory = LowerCaseFilterFactory.class),
                }
        ),
        @AnalyzerDef(
                name = "email_analyzer",
                tokenizer = @TokenizerDef(factory = ClassicTokenizerFactory.class),
                filters = {
                        @TokenFilterDef(factory = LowerCaseFilterFactory.class),
                }
        ),
        @AnalyzerDef(
                name = "phone_analyzer",
                tokenizer = @TokenizerDef(factory = KeywordTokenizerFactory.class)
        )
})
public class Admin extends User implements Serializable {

    private Set<Warehouse> warehouses = new HashSet<>();

    public Admin() {
    }

    @JsonView(View.UserDetails.class)
    public Set<Warehouse> getWarehouses() {
        return warehouses;
    }

    public void setWarehouses(Set<Warehouse> warehouses) {
        this.warehouses = warehouses;
    }

    public void update(Admin updatedAdmin) {
        if(Objects.nonNull(updatedAdmin.getName())) {
            setName(updatedAdmin.getName());
        }

        if(Objects.nonNull(updatedAdmin.getLogin())) {
            setLogin(updatedAdmin.getLogin());
        }

        //TODO: add here others fields
    }
}
