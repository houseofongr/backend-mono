package com.hoo.aoo.admin.domain.user;

public class DeleteAgreement {

    private final Boolean termsOfDeletionAgreement;
    private final Boolean personalInformationDeletionAgreement;

    public DeleteAgreement(Boolean termsOfDeletionAgreement, Boolean personalInformationDeletionAgreement) {
        this.termsOfDeletionAgreement = termsOfDeletionAgreement;
        this.personalInformationDeletionAgreement = personalInformationDeletionAgreement;
    }

}
