package com.smallworld;

import com.smallworld.data.Transaction;

import java.awt.image.AreaAveragingScaleFilter;
import java.lang.reflect.Array;
import java.util.*;
import java.util.concurrent.TransferQueue;
import java.util.stream.Collectors;

public class TransactionDataFetcher {
    public List<Transaction> transactions;

    public TransactionDataFetcher(List<Transaction> transactions) {

        this.transactions = transactions;
    }

    /**
     * Returns the sum of the amounts of all transactions
     */
    public double getTotalTransactionAmount() {
        double totalAmount = 0;

        for (Transaction transaction : transactions) {
                totalAmount += transaction.getAmount();
        }

        return totalAmount;
    }

    /**
     * Returns the sum of the amounts of all transactions sent by the specified client
     */
    public double getTotalTransactionAmountSentBy(String senderFullName) {
        double totalAmount = 0;

        for (Transaction transaction : transactions) {
            if (transaction.getSenderFullName().equals(senderFullName)) {
                totalAmount += transaction.getAmount();
            }
        }

        return totalAmount;
    }

    /**
     * Returns the highest transaction amount
     */
    public double getMaxTransactionAmount() {
        double maxAmount = 0;

        for (Transaction transaction : transactions) {
            double amount = transaction.getAmount();
            if (amount > maxAmount) {
                maxAmount = amount;
            }
        }

        return maxAmount;
    }

    /**
     * Counts the number of unique clients that sent or received a transaction
     */
    public String countUniqueClients() {
        Set<String> uniqueSenderNames = new HashSet<>();
        Set<String> uniqueReceiverNames = new HashSet<>();

        for (Transaction transaction : transactions) {
            String senderName = transaction.getSenderFullName();
            String beneficiaryName = transaction.getBeneficiaryFullName();
            uniqueSenderNames.add(senderName);
            uniqueReceiverNames.add(beneficiaryName);
        }

        int uniqueSenderCount = uniqueSenderNames.size();
        int uniqueReceiverCount = uniqueReceiverNames.size();

        return "Unique Sender count is " + uniqueSenderCount + " and unique receiver count is " + uniqueReceiverCount;
    }

    /**
     * Returns whether a client (sender or beneficiary) has at least one transaction with a compliance
     * issue that has not been solved
     */
    public boolean hasOpenComplianceIssues(String clientFullName) {

        for (Transaction transaction : transactions) {
            boolean isClientSender = transaction.getSenderFullName().equals(clientFullName);
            boolean isClientBeneficiary = transaction.getBeneficiaryFullName().equals(clientFullName);
            if ((isClientSender || isClientBeneficiary) && transaction.getIssueId() >= 1 && !transaction.isIssueSolved()) {
                    return true;
            }
        }

        return false;
    }

    /**
     * Returns all transactions indexed by beneficiary name
     */
    public Map<String, Transaction> getTransactionsByBeneficiaryName() {
        Map<String, Transaction> indexedTransactions = new HashMap<>();

        for (Transaction transaction: transactions) {
            indexedTransactions.put(transaction.getBeneficiaryFullName(), transaction);
        }

        return indexedTransactions;
    }

    /**
     * Returns the identifiers of all open compliance issues
     */
    public Set<Integer> getUnsolvedIssueIds() {

        Set<Integer> unsolvedIssueIds = transactions.stream()
                .filter(transaction -> !transaction.isIssueSolved())
                .map(Transaction::getMtn)
                .collect(Collectors.toSet());
         return unsolvedIssueIds;
    }

    /**
     * Returns a list of all solved issue messages
     */
    public Set<String> getAllSolvedIssueMessages() {
        Set<String> solvedissueMessageList = transactions.stream()
                .filter(Transaction::isIssueSolved)
                .map(Transaction :: getIssueMessage)
                .collect(Collectors.toSet());
        return solvedissueMessageList;
    }

    /**
     * Returns the 3 transactions with the highest amount sorted by amount descending
     */
    public List<Transaction> getTop3TransactionsByAmount() {
        List<Transaction> copyTransactions = new ArrayList<>(transactions);
        Collections.sort(copyTransactions, Comparator.comparing(Transaction::getAmount).reversed());
        return copyTransactions.subList(0, 3);
    }

    /**
     * Returns the senderFullName of the sender with the most total sent amount
     */
    public Optional<String> getTopSender() {
        HashMap<String, Double> senderAmounts = new HashMap<>();
        for (Transaction transaction : transactions
        ) {
            String senderFullName = transaction.getSenderFullName();
            double amount = transaction.getAmount();
            senderAmounts.put(senderFullName, senderAmounts.getOrDefault(senderFullName, 0d) + amount);
        }
        Optional<Map.Entry<String, Double>> topSender = senderAmounts.entrySet()
                .stream()
                .max(Map.Entry.comparingByValue());
        return topSender.map(Map.Entry::getKey);
    }

}
