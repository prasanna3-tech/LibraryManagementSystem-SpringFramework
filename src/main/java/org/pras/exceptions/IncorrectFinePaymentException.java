package org.pras.exceptions;

public class IncorrectFinePaymentException
        extends RuntimeException {

    public IncorrectFinePaymentException(
            double totalFine,
            double amountPaid) {

        super(
                "Please pay exactly Rs. "
                        + totalFine
                        + ". Amount received: Rs. "
                        + amountPaid
        );
    }
}