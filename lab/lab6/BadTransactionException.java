/**
 *  Implements an exception that should be thrown for invalid amount of withdraw.
 **/
public class BadTransactionException extends Exception {

  public int transactionAmount;  // The invalid account number.

  /**
   *  Creates an exception object for nonexistent account "badAcctNumber".
   **/
  public BadTransactionException(int badTranscAmount) {
    super("Invalid Transaction Amount: " + badTranscAmount);

    transactionAmount = badTranscAmount;
  }
}
