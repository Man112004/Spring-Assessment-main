
package com.example.model;

import jakarta.validation.constraints.NotBlank;

public class RecommendRequest {

    @NotBlank(message = "Preference is required")
    private String preference;

    public String getPreference() {
        return preference;
    }

    public void setPreference(String preference) {
        this.preference = preference;
    }
}
