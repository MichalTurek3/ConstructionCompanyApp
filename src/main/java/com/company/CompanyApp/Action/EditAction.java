package com.company.CompanyApp.Action;

import com.company.CompanyApp.domain.Action;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("EditAction")
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString
public class EditAction extends Action {

    private Long entityId;

    private String entityType;

    private String fieldName;

    private String oldValue;

    private String newValue;
}