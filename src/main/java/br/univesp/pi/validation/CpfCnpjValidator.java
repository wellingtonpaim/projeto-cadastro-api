package br.univesp.pi.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class CpfCnpjValidator implements ConstraintValidator<CpfCnpj, String> {

    private static final Pattern CPF_PATTERN = Pattern.compile("\\d{11}");
    private static final Pattern CNPJ_PATTERN = Pattern.compile("\\d{14}");

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return false;
        }
        return CPF_PATTERN.matcher(value).matches() || CNPJ_PATTERN.matcher(value).matches();
    }
}
