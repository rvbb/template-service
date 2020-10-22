package com.smartosc.lending.lms.service.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.json.JSONObject;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "banklist_demo")
public class BankListDemoEntity {

    @ApiModelProperty(notes = "id", example = "1234566")
    @Id
    @GeneratedValue
    private long id;
    
    @ApiModelProperty(notes = "bank_name", example = "VPBank")
    @Column(name="bank_name")
    private String bankName;
    
    @ApiModelProperty(notes = "1:private or 2:public")
    private int type;
    
    @ApiModelProperty(notes = "created date of each transaction")
    private Date created;

    @Override
    public String toString() {
        return new JSONObject(this).toString();
    }
}
