package com.bank.core.domain.enums;

public enum ExceptionTypeCalculationBankSlip {

    INVALID_BANK_SLIP {
        @Override
        public String getViolationMessage() {
            return "Invalid bank slip";
        }
    },

    INVALID_TYPE_BANK_SLIP {
        @Override
        public String getViolationMessage() {
            return "Invalid type bank slip";
        }
    },

    DUE_DATE_ON_TIME {
        @Override
        public String getViolationMessage() {
            return "Due date bank slip on time";
        }
    };

    public abstract String getViolationMessage();
}
