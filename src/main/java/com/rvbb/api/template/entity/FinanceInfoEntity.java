package com.rvbb.api.template.entity;

import lombok.*;
import org.hibernate.validator.constraints.UniqueElements;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "finance_info")
public class FinanceInfoEntity implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "pre_tax_income")
    private BigDecimal preTaxIncome;

    @Column(name = "expense")
    private BigDecimal expense;

    @Column(name = "company_name")
    private String companyName;

    @Column(name = "company_address")
    private String companyAddress;

    @Column(name = "last_update")
    private Date lastUpdate;

    @Column(name = "status")
    private Byte status;

    @Column(name = "uuid", unique = true)
    private String uuid;
}
