package com.atlas.bank.infrastructure.adapter.out.persistence;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor
@EqualsAndHashCode
public class Email {

    @Column(nullable = false)
    private String value;

    private Email(String value) {

        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Email cannot be null or blank");
        }
        if (!value.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
            throw new IllegalArgumentException("Invalid email format");
        }


        this.value = value.toLowerCase().trim();
    }

    public static Email of(String value) {
        return new Email(value);
    }

    @Override
    public String toString() {
        return value;
    }
}
