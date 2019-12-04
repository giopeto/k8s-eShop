package com.store.v1.groups.utils;

import com.store.v1.groups.domain.Groups;
import lombok.experimental.UtilityClass;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@UtilityClass
public class GroupsTestUtils {

    private static final String GROUP_NAME = "Group ";

    public static Groups generateGroup(String id, Optional<String> name) {
        return new Groups(id, name.isPresent() ? name.get() : GROUP_NAME + id);
    }

    public static List<Groups> generateGroups(int numberOfTestRecords) {
        List<Groups> mockedGroups = new ArrayList<>();

        for (int i = 0; i < numberOfTestRecords; i++) {
            mockedGroups.add(generateGroup(UUID.randomUUID().toString(), Optional.empty()));
        }

        return mockedGroups;
    }
}
