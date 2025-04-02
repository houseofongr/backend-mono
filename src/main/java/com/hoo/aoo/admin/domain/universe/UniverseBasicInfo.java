package com.hoo.aoo.admin.domain.universe;

public class UniverseBasicInfo extends BasicInfo{
    private final Long ownerId;
    private final PublicStatus publicStatus;

    public UniverseBasicInfo(String title, String description, Long ownerId, PublicStatus publicStatus) {
        super(title, description);
        this.ownerId = ownerId;
        this.publicStatus = publicStatus;
    }
}
