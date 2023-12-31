package com.nfaustino.splitmoney.shared.valueobjects;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Currency;
import java.util.Locale;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class Money {

    private static final Currency USD = Currency.getInstance("BRL");
    private static final RoundingMode DEFAULT_ROUNDING = RoundingMode.HALF_EVEN;

    private final BigDecimal amount;
    private final Currency currency;

    public static Money real(BigDecimal amount) {
        return new Money(amount, USD);
    }

    public static Money real(double amount) {
        return new Money(BigDecimal.valueOf(amount), USD);
    }

    Money(BigDecimal amount, Currency currency) {
        this(amount, currency, DEFAULT_ROUNDING);
    }

    Money(BigDecimal amount, Currency currency, RoundingMode rounding) {
        this.currency = currency;
        this.amount = amount.setScale(currency.getDefaultFractionDigits(), rounding);
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public Currency getCurrency() {
        return currency;
    }

    @Override
    public String toString() {
        return getCurrency().getSymbol() + " " + getAmount();
    }

    public String toString(Locale locale) {
        return getCurrency().getSymbol(locale) + " " + getAmount();
    }

    public Money add(Money value) {
        return new Money(amount.add(value.getAmount()), this.currency);
    }

    public Money subtract(Money value) {
        return new Money(amount.subtract(value.getAmount()), this.currency);
    }
}