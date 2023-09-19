import com.smallworld.TransactionDataFetcher;
import com.smallworld.data.Transaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TransactionDataFetcherTest {

    private TransactionDataFetcher dataFetcher;

    @BeforeEach
    void setUp() {
        // Create sample transactions for testing
        List<Transaction> transactions = new ArrayList<>();
        transactions.add(new Transaction(12345,75.2, "Owain perry", 15, "Graham Perkins", 54, 1, false, "Testing unresolved issue"));
        transactions.add(new Transaction(67890,83, "Femi martins", 45, "Gareth thomas", 25, 5, true, "Testing solved issue"));
        transactions.add(new Transaction(12345,75.2, "Owain perry", 15, "Charles Thomas", 76, 5, true, "Testing duplicate sender name"));
        transactions.add(new Transaction(90765,90.56, "Tess Oner", 15, "Graham Perkins", 34, 2, true, "Testing duplicate receiver name"));

        dataFetcher = new TransactionDataFetcher(transactions);
    }

    @Test
    void testGetTotalTransactionAmount() {
        assertEquals(323.96, dataFetcher.getTotalTransactionAmount(), 0.01);
    }

    @Test
    void testGetTotalTransactionAmountSentBy() {
        assertEquals(150.4, dataFetcher.getTotalTransactionAmountSentBy("Owain perry"), 0.01);
    }

    @Test
    void testGetMaxTransactionAmount() {
        assertEquals(90.56, dataFetcher.getMaxTransactionAmount(), 0.01);
    }

    @Test
    void testCountUniqueClients() {
        assertEquals("Unique Sender count is 3 and unique receiver count is 3", dataFetcher.countUniqueClients());
    }

    @Test
    void testHasOpenComplianceIssues() {
        assertTrue(dataFetcher.hasOpenComplianceIssues("Owain perry"));
        assertFalse(dataFetcher.hasOpenComplianceIssues("Tess Oner"));
    }

    @Test
    void testGetTransactionsByBeneficiaryName() {
        assertNotNull(dataFetcher.getTransactionsByBeneficiaryName());
        assertEquals(3, dataFetcher.getTransactionsByBeneficiaryName().size());
    }

    @Test
    void testGetUnsolvedIssueIds() {
        assertEquals(1, dataFetcher.getUnsolvedIssueIds().size());
        assertFalse(dataFetcher.getUnsolvedIssueIds().contains(1));
    }

    @Test
    void testGetAllSolvedIssueMessages() {
        assertEquals(3, dataFetcher.getAllSolvedIssueMessages().size());
        assertTrue(dataFetcher.getAllSolvedIssueMessages().contains("Testing solved issue"));
    }

    @Test
    void testGetTop3TransactionsByAmount() {
        List<Transaction> top3 = dataFetcher.getTop3TransactionsByAmount();
        assertNotNull(top3);
        assertEquals(3, top3.size());
        assertEquals(90.56, top3.get(0).getAmount(), 0.01);
    }

    @Test
    void testGetTopSender() {
        assertEquals("Owain perry", dataFetcher.getTopSender().orElse(null));
    }
}
