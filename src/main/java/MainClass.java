import com.fasterxml.jackson.databind.ObjectMapper;
import com.smallworld.TransactionDataFetcher;
import com.smallworld.data.Transaction;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class MainClass{
    public static void main(String[] args) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            List<Transaction> transactions = objectMapper.readValue(new File("src/main/resources/transactions.json"),
                    objectMapper.getTypeFactory().constructCollectionType(List.class, Transaction.class));

            // Uncomment to run individual methods
            /*TransactionDataFetcher df = new TransactionDataFetcher(transactions);
            System.out.println("getTotalTransactionAmount " + df.getTotalTransactionAmount());
            System.out.println("getTotalTransactionAmountSentBy " + df.getTotalTransactionAmountSentBy("Tom Shelby"));
            System.out.println("getMaxTransactionAmount " + df.getMaxTransactionAmount());
            System.out.println("countUniqueClients " + df.countUniqueClients());
            System.out.println("hasOpenComplianceIssues " + df.hasOpenComplianceIssues("Aunt Polly"));
            System.out.println("getTransactionsByBeneficiaryName " + df.getTransactionsByBeneficiaryName().toString());
            System.out.println("getUnsolvedIssueIds " + df.getUnsolvedIssueIds());
            System.out.println("getAllSolvedIssueMessages" + df.getAllSolvedIssueMessages());
            System.out.println("getTop3TransactionsByAmount" + df.getTop3TransactionsByAmount());
            System.out.println("getTopSender" + df.getTopSender());*/

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}