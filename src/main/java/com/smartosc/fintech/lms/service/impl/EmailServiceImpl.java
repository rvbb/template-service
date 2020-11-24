package com.smartosc.fintech.lms.service.impl;

import com.smartosc.fintech.lms.common.constant.BankAccountType;
import com.smartosc.fintech.lms.common.constant.ErrorCode;
import com.smartosc.fintech.lms.common.util.SMFLogger;
import com.smartosc.fintech.lms.entity.*;
import com.smartosc.fintech.lms.exception.BusinessServiceException;
import com.smartosc.fintech.lms.service.EmailService;
import lombok.AllArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.persistence.EntityNotFoundException;
import java.math.BigDecimal;

@Service
@AllArgsConstructor
public class EmailServiceImpl implements EmailService {
    private static final String EMAIL_FUNDING_TEMPLATE_NAME = "funding";
    private static final String EMAIL_REPAYMENT_TEMPLATE_NAME = "repayment";
    private static final String SEND_EMAIL_FAIL_MESSAGE = "Send email fail";

    private final JavaMailSender mailSender;
    private final TemplateEngine templateEngine;

    @SMFLogger
    public void sendFundingEmail(LoanTransactionEntity transaction) {
        LoanApplicationEntity application = transaction.getLoanApplication();
        BankAccount bankAccount = getBankAccount(application);
        LoanPersonalInformationEntity personalInfo = getPersonalInfo(application);

        Context context = new Context();
        context.setVariable("fullName", personalInfo.getFullName());
        context.setVariable("contractNumber", application.getContractNumber());
        context.setVariable("disbursementDate", transaction.getEntryDate());
        context.setVariable("disbursementAmount", transaction.getAmount());
        context.setVariable("bankAccount", bankAccount.getAccount());
        context.setVariable("bankName", bankAccount.getBankName());
        context.setVariable("bankCode", bankAccount.getBankCode());
        context.setVariable("productName", application.getLoanProduct().getName());


        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper message = new MimeMessageHelper(mimeMessage, "UTF-8");
        try {
            String subject = String.format("[ %s ] Loan disbursement notification", application.getContractNumber());
            message.setSubject(subject);
            message.setTo(personalInfo.getEmailAddress());
            String htmlContent = templateEngine.process(EMAIL_FUNDING_TEMPLATE_NAME, context);
            message.setText(htmlContent, true);

            this.mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            throw new BusinessServiceException(SEND_EMAIL_FAIL_MESSAGE, ErrorCode.SEND_EMAIL_FAIL);
        }
    }

    @SMFLogger
    public void sendRepaymentEmail(RepaymentEntity repayment) {
        LoanApplicationEntity application = repayment.getLoanApplication();
        LoanPersonalInformationEntity personalInfo = getPersonalInfo(application);

        Context context = new Context();
        context.setVariable("fullName", personalInfo.getFullName());
        context.setVariable("contractNumber", application.getContractNumber());
        context.setVariable("paymentDate", repayment.getLastPaidDate());

        BigDecimal paymentAmount = repayment.getPrincipalPaid().add(repayment.getInterestPaid());
        context.setVariable("paymentAmount", paymentAmount);
        context.setVariable("principalAmount", repayment.getPrincipalPaid());
        context.setVariable("interestAmount", repayment.getInterestPaid());

        BigDecimal loanBalance = application.getLoanAmount().subtract(application.getPrincipalDue());
        context.setVariable("loanBalance", loanBalance);


        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper message = new MimeMessageHelper(mimeMessage, "UTF-8");
        try {
            String subject = String.format("[ %s ] Loan repayment notification", application.getContractNumber());
            message.setSubject(subject);
            message.setTo(personalInfo.getEmailAddress());
            String htmlContent = templateEngine.process(EMAIL_REPAYMENT_TEMPLATE_NAME, context);
            message.setText(htmlContent, true);

            this.mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            throw new BusinessServiceException(SEND_EMAIL_FAIL_MESSAGE, ErrorCode.SEND_EMAIL_FAIL);
        }
    }

    private BankAccount getBankAccount(LoanApplicationEntity application) {
        return application.getBankAccounts().stream()
                .filter(b -> BankAccountType.TYPE_BORROWER.getValue().equals(b.getType())).findFirst()
                .orElseThrow(() -> new EntityNotFoundException("Bank account not found"));
    }

    private LoanPersonalInformationEntity getPersonalInfo(LoanApplicationEntity application) {
        return application.getLoanPersonalInformation().stream()
                .findFirst().orElseThrow(() -> new EntityNotFoundException("Personal Information not found"));
    }
}
